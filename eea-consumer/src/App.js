import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import {Register,SignIn} from './RegSignIn'

const sitename = "Stadia";

class App extends Component{
  render(){
    return(
      <Router>
        <div>
          <Route exact path="/" component={Index} />
          <Route path="/register" component={Register} />
          <Route path="/signin" component={SignIn} />
        </div>
      </Router>
    );
  }
}

class Index extends Component{
  render(){
    return(
      <div>
      <NavBar />
    </div>
    );
  }
}

class NavBar extends Component{
  constructor(props){
    super(props);
    this.state = {login:false}
    this.handleLoginBtnClick = this.handleLoginBtnClick.bind(this);
  }

  handleLoginBtnClick(){
    this.setState({login:!this.state.login});
  }

  render(){
    return(
      <div>
        <nav className="main_color navbar_background navbar">
          <div className="mr-auto">
            <Link className="navbar_title text-white button_hover"  to="/">{sitename}</Link>
            <Link className="navbar_categories button_hover " to="/about">Men</Link>
            <a className="navbar_categories button_hover " href="https://www.google.com/search?q=css+hyperlink+hover+underline+no&oq=css+hyperlink+hover+underline+no&aqs=chrome..69i57j0.17461j0j7&sourceid=chrome&ie=UTF-8">Women</a>
          </div>
          <div className="form-inline">
            <input className="form-control mr-sm-2" id="search_bar" type="search" placeholder="Search for items" aria-label="Search"></input>
            <button className="btn my-2 my-sm-0 button_hover" type="submit">
              <span className="navbar-toggler-icon search_icon"></span>
            </button>
          </div>
          <div className="ml-auto" >
            <button className="btn my-2 my-sm-0 button_hover mr-auto" type="submit">
                <span className="navbar-toggler-icon cart_icon"></span>
            </button>
            <button id="profileId" className="btn my-2 my-sm-0 button_hover" type="submit" onClick={this.handleLoginBtnClick}>
                <span className="navbar-toggler-icon account_icon"></span>
            </button>
            <div className="popup_container main_color card" style={{display:this.state.login?"block":"none"}}>
              <p className="font_Gabriola h5 font_weight_bold">Email</p>
              <input className="form-control font_Gabriola popup_input_text" type="email" placeholder="Email" aria-label="Email"></input>
              <p className="font_Gabriola h5 font_weight_bold">Password</p>
              <input className="form-control font_Gabriola popup_input_text" type="password" placeholder="Password" aria-label="Password"></input>
              <br/>
              <Link to="/register">
                <input className="btn btn-lg bg-white popup-button font_AgencyFB w-30" type="button" value="Register"></input>
              </Link>
              <input className="btn btn-lg bg-white popup-button font_AgencyFB w-30 mr-auto" type="submit" value="Sign In"></input>
            </div>
          </div>
        </nav>
        <SubCategories/>
        
      </div>
    );
  }
}

class SubCategories extends Component{
  render(){
    return(
      <div className="mr-auto main_lightcolor_bg">
        <a className="navbar_subcategories" href="https://www.google.com/search?q=css+hyperlink+hover+underline+no&oq=css+hyperlink+hover+underline+no&aqs=chrome..69i57j0.17461j0j7&sourceid=chrome&ie=UTF-8">New Arrivals</a>
        <a className="navbar_subcategories" href="https://www.google.com/search?q=css+hyperlink+hover+underline+no&oq=css+hyperlink+hover+underline+no&aqs=chrome..69i57j0.17461j0j7&sourceid=chrome&ie=UTF-8">Clothing</a>
        <a className="navbar_subcategories" href="https://www.google.com/search?q=css+hyperlink+hover+underline+no&oq=css+hyperlink+hover+underline+no&aqs=chrome..69i57j0.17461j0j7&sourceid=chrome&ie=UTF-8">Shoes</a>
        <a className="navbar_subcategories" href="https://www.google.com/search?q=css+hyperlink+hover+underline+no&oq=css+hyperlink+hover+underline+no&aqs=chrome..69i57j0.17461j0j7&sourceid=chrome&ie=UTF-8">Accessories</a>
        <a className="navbar_subcategories" href="https://www.google.com/search?q=css+hyperlink+hover+underline+no&oq=css+hyperlink+hover+underline+no&aqs=chrome..69i57j0.17461j0j7&sourceid=chrome&ie=UTF-8">Active Wear</a>
      </div>
    );
  }
}

export default App;
