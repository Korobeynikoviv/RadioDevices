import React from 'react';
import { Link } from 'react-router-dom';

import { reportService } from '../_services';

import SortableTable from '../_components';

const data1 = [
    {"reportJson":{},"localDateTime": null, "user":null},
    {"reportJson":{},"localDateTime": null, "user":null},
    {"reportJson":{},"localDateTime": null, "user":null}
]

const TABLE_COLUMNS = [
  {
    label: 'Name',
    sort: 'default',
  },{
    label: 'ID',
    sort: 'default',
  },{
    label: 'Count',
    sort: 'default',
  }
];

const SortableHeader = (props) => {
  return(
    <thead>
      <tr>
        {TABLE_COLUMNS.map((element, index) =>
          <th key={index}>{element.label}</th>
        )}
      </tr>
    </thead>
  )
}

const SortableBody = ({data}) => {
    if (!data)
        return <tbody/>
    else
      return(
        <tbody>
          {data.map((element, index) =>
            <tr key={index}>
              {element.map((item, i) =>
                <td key={i}>{item}</td>
              )}
            </tr>
          )}
        </tbody>
      )
}

class Report extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            session: {},
            reports: [],
            data: [],
            column: TABLE_COLUMNS
        };
    }

    componentDidMount() {
        this.setState({
            session: localStorage.getItem('sessionId'),
            reports: { loading: true },
            data: data1,
            column: TABLE_COLUMNS

        });
        reportService.getAll(localStorage.getItem('sessionId')).then(reports => this.setState({ reports }));
    }

    render() {
        const { session, reports } = this.state;
        return (
            <div className="col-md-6 col-md-offset-3">

                <p>You're logged in with React & Basic HTTP Authentication!!</p>

                <table>
                  <SortableHeader columns={this.state.columns} />

                </table>

                <p>
                    <Link to="/login">Logout</Link>
                </p>
            </div>
        );
    }
}

export { Report };