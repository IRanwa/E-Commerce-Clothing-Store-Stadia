import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import { BrowserRouter as Router, Route } from "react-router-dom";

class App extends Component{
  render(){
    return(
      <Router>
        <div>
          <Route exact path="/" component={Index} />
        </div>
      </Router>
    );
  }
}

class Index extends Component{
  render(){
    return(
      <div className="login-form">
      <h2 className="font_Britannic greycolor_text text-center">Login</h2>
        <div className="main_color card p-sm-2 text-white font_AgencyFB shadow-lg">
          <div className="input_container">
            <h5>Email</h5>
            <input className="card" type="email" placeholder="Email" name="email"/>
          </div>
          <div className="input_container">
            <h5>Password</h5>
            <input className="card" type="password" placeholder="Password" name="password"/>
          </div>
          <div className="input_container text-center">
            <input className="btn btn-lg bg-white button font_AgencyFB shadow-sm" type="submit" value="SIGN IN"/>
          </div>
        </div>
      </div>
    );
  }
}

export default App;
