import config from 'config';
import sha256 from 'sha256';
import { authHeader } from '../_helpers';

export const reportService = {
    getAll
};

function getAll(sessionId) {
    const requestOptions = {
        method: 'GET'
    };

    return fetch(`${config.apiUrl}/getReports?sessionId=` + sessionId).then(handleResponse);
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