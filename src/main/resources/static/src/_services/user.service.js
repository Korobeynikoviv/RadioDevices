import config from 'config';
import sha256 from 'sha256';
import { authHeader } from '../_helpers';

export const userService = {
    login,
    logout,
    getAll
};

function login(login, password) {
    let passHash = sha256(password);
    const requestOptions = {
        method: 'POST',
//        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ login, passHash})
    };

    return fetch(`${config.apiUrl}/login?login=`+login+`&passHash=`+passHash, requestOptions)
        .then(handleResponse)
        .then(user => {
            // login successful if there's a user in the response
            if (user) {
                // store user details and basic auth credentials in local storage 
                // to keep user logged in between page refreshes
                user.authdata = window.btoa(login + ':' + password);
                localStorage.setItem('user', JSON.stringify(user));
                return user;
            }


        });
}

function logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('user');
}

function getAll() {
    const requestOptions = {
        method: 'GET',
        headers: authHeader()
    };

    return fetch(`${config.apiUrl}/users`, requestOptions).then(handleResponse);
}

function handleResponse(response) {
    return response.text().then(text => {
        const data = text && JSON.parse(text);
        if (!response.ok) {
            if (response.status === 401) {
                // auto logout if 401 response returned from api
                logout();
                location.reload(true);
            }

            const error = (data && data.message) || response.statusText;
            return Promise.reject(error);
        }

        return data;
    });
}