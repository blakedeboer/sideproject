import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {
  state = {
      isLoading: true,
      employees: []
  }

  async componentDidMount() {
      const response = await fetch('/api/employees');
      const body = await response.json();
      console.log(body._embedded.employees)
      this.setState({ employees: body._embedded.employees, isLoading: false });
  }
  render() {
      const {employees, isLoading} = this.state;

      if (isLoading) {
          return <p>Loading...</p>;
      }

    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <div className="App-intro">

              <table>
                  <tbody>
                  <tr>
                      <th>First Name</th>
                      <th>Last Name</th>
                      <th>Description</th>
                  </tr>
                  {employees.map(employee =>
                      <tr key={employee._links.self.href}>
                          <td>{employee.firstName}</td>
                          <td>{employee.lastName}</td>
                          <td>{employee.description}</td>
                      </tr>
                  )}
                  </tbody>
              </table>
          </div>
        </header>
      </div>
    );
  }
}

export default App;
