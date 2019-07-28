import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import './images.css';
import { BrowserRouter as Router, Route } from "react-router-dom";
import HomepageApp from './Homepage.js';
import CategoriesApp from './Categories.js';
import ProductSizesApp from './ProductSizes.js';
import ProductsApp from './Products';

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
  render(){
    return(
      <div className="login-form">
          <div className="main_color card p-sm-2 shadow">
            <div className="input_container">
              <h6 className="font_weight_bold">Email</h6>
              <input className="card font_14px" type="email" placeholder="Email" name="email"/>
            </div>
            <div className="input_container">
              <h6 className="font_weight_bold">Password</h6>
              <input className="card font_14px" type="password" placeholder="Password" name="password"/>
            </div>
            <div className="input_container text-center">
              <input className="btn btn-lg shadow btnSignIn" type="submit" value="Sign In"/>
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
