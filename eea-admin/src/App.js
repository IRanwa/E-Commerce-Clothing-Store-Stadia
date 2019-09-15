import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import './images.css';
import { BrowserRouter as Router, Route } from "react-router-dom";
import HomepageApp from './Homepage.js';
import CategoriesApp from './Categories.js';
import ProductSizesApp from './ProductSizes.js';
import ProductsApp from './Products';
const axios = require("axios");

class App extends Component{
  render(){
    return(
      <Router>
        <div >
          <Route exact path="/" component={Index} />
          <Route path="/homepage" component={Homepage} />
          <Route path="/categories" component={Categories} /> 
          <Route path="/product_sizes" component={ProductSizes} /> 
          <Route path="/products" component={Products} /> 
        </div>
      </Router>
    );
  }
}

class Index extends Component{
  constructor(props){
    super(props);
    this.state={
      fields:{
        email:"",
        pass:""
      }
    }
    this.loginBtn = this.loginBtn.bind(this);
  }

  inputChnage(event){
    let fields = this.state.fields;
    fields[event.target.name] = event.target.value;
    this.setState({
      fields
    })
  }

  loginBtn(){
    axios.post("http://localhost:8080/authenticate/",this.state.fields)
    .then(function(res){
      console.log(res);
      const data = res.data;
      localStorage.setItem("token",data.jwttoken);
      localStorage.setItem("email",this.state.fields.email);
      localStorage.setItem("name",(data.fname+" "+data.lname));
    }).catch(function(error){
      console.log(error.response);
    })
  }

  render(){
    return(
      <div className="login-form">
          <div className="main_color card p-sm-2 shadow">
            <div className="input_container">
              <h6 className="font_weight_bold">Email</h6>
              <input className="card font_14px" type="email" placeholder="Email" name="email" value={this.state.fields.name} onChange={this.inputChnage.bind(this)}/>
            </div>
            <div className="input_container">
              <h6 className="font_weight_bold">Password</h6>
              <input className="card font_14px" type="password" placeholder="password" name="pass" value={this.state.fields.password} onChange={this.inputChnage.bind(this)}/>
            </div>
            <div className="input_container text-center">
              <input className="btn btn-lg shadow btnSignIn" type="submit" value="Sign In" onClick={this.loginBtn}/>
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

class ProductSizes extends Component{
  render(){
    return(
      <div>
        <NavBar />
        <ProductSizesApp/>
      </div>
    );
  }
}

class Products extends Component{
  render(){
    return(
      <div>
        <NavBar/>
        <ProductsApp/>
      </div>
    )
  }
}

class NavBar extends Component{
  constructor(props){
    super(props);
    this.state={
      showNav:false
    }
    this.toggleClick = this.toggleClick.bind(this);
  }

  toggleClick(){
    this.setState({
      showNav:!this.state.showNav
    })
  }

  render(){
    return(
      <div>
        <nav className="navbar navbar-expand-lg bg-light">
          <a className="navbar-brand" href="/homepage">Homepage</a>
          
          <button className="navbar-toggler" type="button" onClick={this.toggleClick} aria-controls="navbarNavAltMarkup" aria-expanded={this.state.showNav}>
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" style={{display:this.state.showNav?("block"):("")}}  id="navbarNavAltMarkup">
            
            <div className="navbar-nav w-100">
              <ul className="navbar-nav mr-auto">
                <a className="nav-item nav-link nav-sub" href="/categories">Categories</a>

                <div className="dropdown">
                  <a className="nav-item nav-link" href="/">Products</a>
                  <div className="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a className="nav-item nav-link" href="/products">Products</a>
                    <a className="nav-item nav-link" href="/product_sizes">Products Size</a>
                  </div>
                </div>
                
                <a className="nav-item nav-link nav-sub" href="/">Orders</a>
              </ul>
              <a className="nav-item nav-link mr-3" href="/">Log out</a>
              
            </div>
          </div>
        </nav>
      </div>
    );
  }
}

export default App;
