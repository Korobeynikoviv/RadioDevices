import React from 'react';

export default class SortedTable extends React.Component {
  static propTypes = {
    data: React.PropTypes.array,
  };

  constructor(props) {
    super(props);

    this.state = {
      data: [],
      columns: [],
    }
  }

  componentWillMount() {
    const { data } = this.props;
    this.setState({ data })
  }

  componentWillReceiveProps(nextProps) {
    const { data } = nextProps;
    this.setState({ data })
  }

  render() {
    return (
      <div></div>
    );
  }
}