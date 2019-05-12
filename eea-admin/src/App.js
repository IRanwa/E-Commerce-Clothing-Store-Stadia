import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import { BrowserRouter as Router, Route } from "react-router-dom";
import HomepageApp from './Homepage.js';
import CategoriesApp from './Categories.js';

class App extends Component{
  render(){
    return(
      <Router>
        <div>
          <Route exact path="/" component={Index} />
          <Route path="/homepage" component={Homepage} />
          <Route path="/categories" component={Categories} />
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

class Homepage extends Component{
  render(){
    return(
      <div>
        <NavBar/>
        <HomepageApp/>
      </div>
    );
  }
}

class Categories extends Component{
  render(){
    return(
      <div>
        <NavBar />
        <CategoriesApp/>
      </div>
    );
  }
}

class NavBar extends Component{
  render(){
    return(
      <div>
        <div className="navbar main_color font_AgencyFB font_weight_bold">
          <div>
            <div className="navbar_header">
              <a href="index.html">Homepage</a>
            </div>
            <div>
              <div>
                <a href="index.html">Categories</a>
              </div>
              <div>
                <a href="index.html">Product Sizes</a>
              </div>
              <div className="navbar_collapsed">
                <a href="index.html">Products</a>
                <div>
                  <div className="navbar_sub">
                    <a href="index.html">Questions</a>
                  </div>
                  <div className="navbar_sub">
                    <a href="index.html">Ratings</a>
                  </div>
                </div>
              </div>
              <div>
                <a href="index.html">Orders</a>
              </div>
            </div>
            <div className="navbar_bottom">
              <a href="index.html">Log Out</a>
            </div>
          </div>
        </div>
        <div className="loggedin_text font_AgencyFB">
          <p>Login as imeshranwa2@hotmail.com</p>
        </div>
        <hr/>
      </div>
    );
  }
}

export default App;
