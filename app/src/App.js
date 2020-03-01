import React, { Component } from 'react';

import {
  Route,
  Switch,
  Redirect,
  withRouter
} from "react-router-dom"

import './App.scss';

import Login from './components/Login/Login'

class App extends Component {
  render() {

    return (
      <div className="App">
        <Switch>
          <Route path='/login' component={Login} />
          <Redirect from='/' to='/login'/>
        </Switch>
      </div>
    );
  }
}

export default withRouter(App)