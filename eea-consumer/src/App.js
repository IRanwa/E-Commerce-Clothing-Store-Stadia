import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import './images.css';
import { BrowserRouter as Router, Route,Redirect } from "react-router-dom";
//import {Register,SignIn} from './RegSignIn';
import ProductList from './ProductList';
import ProductDetails from './Details';
import Cart from './Cart';
import SignIn from './SignIn.jsx';
import Register from './Register';
const axios = require("axios");

const sitename = "Stadia";

class App extends Component{
  render(){
    return(
      <Router>
        <div>
          <Route exact path="/" component={Index} />
          {/* <Route path="/register" component={Register} /> */}
          <Route path="/login" component={SignInPage} />
          <Route path="/register" component={RegisterPage} />
          <Route path="/productdetails" component={ProductDetailsPage} />
          <Route path="/cart" component={CartPage} />
        </div>
      </Router>
    );
  }
}

function SignInPage(){
  return(
    <div>
      <NavBar/>
      <SignIn/>
    </div>
  );
}

class RegisterPage extends Component{
  constructor(props){
    super(props);
  }
  
  render(){
    return(
      <div>
        <NavBar/>
        <Register email={this.props.location.state.email} name={this.props.location.state.name}/>
      </div>
    );
  }
}

class CartPage extends Component{
  render(){
    return(
      <div>
        <NavBar />
        <Cart />
    </div>
    );
  }
}
class ProductDetailsPage extends Component{
  constructor(props){
    super(props);
    this.state={
      id:this.props.location.state.id
    }
  }
  render(){
    return(
      <div>
        <NavBar />
        <ProductDetails id={this.state.id}/>
    </div>
    );
  }
}

class Index extends Component{
  constructor(props){
    super(props);
    this.state={
      mainCat:0,
      subCat:0
    }
    this.changeCatSelection = this.changeCatSelection.bind(this);
  }

  changeCatSelection(name,value){
    if(name==="Main Cat"){
      this.setState({
        mainCat:value
      })
    }else if(name==="Sub Cat"){
      this.setState({
        subCat:value
      })
    }
  }

  render(){
    return(
      <div>
        <NavBar />
        <ProductList mainCat={this.state.mainCat} subCat={this.state.subCat}/>
    </div>
    );
  }
}

class NavBar extends Component{
  constructor(props){
    super(props);
    this.state={
      showNav:false,
      mouseMove:false,
      mainCat:[],
      subCatView:true
    }
    this.toggleClick = this.toggleClick.bind(this);
    this.getMainCat = this.getMainCat.bind(this);
    this.signOut = this.signOut.bind(this);
  }

  toggleClick(){
    this.setState({
      showNav:!this.state.showNav
    })
  }

  getMainCat(type){
    const that = this;
    if(!this.state.mouseMove){
      this.setState({
        mouseMove:true
      })
      axios.get("http://localhost:8080/GetMainCatByType/"+type)
      .then(function(res){
        //console.log(res.data)
        that.setState({
          mainCat:res.data,
          mouseMove:false
        });
      })
    }
  }

  signOut(){
    localStorage.removeItem("token")
    localStorage.removeItem("email")
  }

  render(){
    return(
      <div>
        <nav className="navbar navbar-expand-lg bg-light">
          <a className="navbar-brand" href="/">Stadia</a>
          
          <button className="navbar-toggler" type="button" onClick={this.toggleClick} aria-controls="navbarNavAltMarkup" aria-expanded={this.state.showNav}>
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" style={{display:this.state.showNav?("block"):("")}}  id="navbarNavAltMarkup">
            
            <div className="navbar-nav w-100">
              <div className="navbar-nav mr-auto">

              <div className="dropdown dropdown-categories">
                  <label className="nav-item nav-link " onMouseMoveCapture={()=>this.getMainCat("M")}>Men</label>
                  <div className="dropdown-menu dropdown-menu-lg ">
                     <div className="row d-flex">
                      <div className="col-lg-4 col-sm-4"> 
                        {
                          this.state.mainCat.map((item,index)=>{
                            return(
                              <div key={index}>
                                <label className="dropdown-item" ><h6 className="dropdown-header">{item.mainCatTitle} </h6></label> 
                                <ul className="mt-10">
                                  {
                                    item.subCategoryDTO.map((subItem,index)=>{
                                      return(
                                        <li key={index}> <a className="dropdown-item" href="/templates/angular">{subItem.subCatTitle}</a></li> 
                                      )
                                    })
                                  }
                                </ul>
                              </div>
                            )
                          })
                        }
                      </div>
                    </div> 
                  </div>
                </div>

                <div className="dropdown dropdown-categories">
                  <label className="nav-item nav-link"  onMouseMoveCapture={()=>this.getMainCat("F")}>Women</label>
                  <div className="dropdown-menu dropdown-menu-lg ">
                     <div className="row d-flex">
                      <div className="col-lg-4 col-sm-4"> 
                        {
                          this.state.mainCat.map((item,index)=>{
                            return(
                              <div key={index}>
                                <a className="dropdown-item" href="/"><h6 className="dropdown-header">{item.mainCatTitle} </h6></a> 
                                <ul className="mt-10">
                                  {
                                    item.subCategoryDTO.map((subItem,index)=>{
                                      return(
                                        <li key={index}> <a className="dropdown-item" href="/templates/angular">{subItem.subCatTitle}</a></li> 
                                      )
                                    })
                                  }
                                </ul>
                              </div>
                            )
                          })
                        }
                      </div>
                    </div> 
                  </div>
                </div>


                <a className="nav-item nav-link nav-sub" href="/">Orders</a>
              </div>
              {
                localStorage.email!=undefined?(
                  <div className="cart-icon-container">
                    <a className="nav-item nav-link" href="/cart">
                      <img src="https://static.thenounproject.com/png/16757-200.png" className="cart-img"/>
                      <label className="cart-text">1</label>
                    </a>
                    
                  </div>
                ):("")
              }
             
              <div>
                <div className="dropdown">
                    <a className="nav-item nav-link profile-icon-container">
                      <img src="https://cdn4.iconfinder.com/data/icons/standard-free-icons/139/Profile01-512.png" alt="profile-icon" className="profile-icon"/>
                        
                    </a>
                    <div className="dropdown-menu profile-icon-subcontainer row">
                      {
                        localStorage.email!=undefined?(
                          <div>
                            <div className="column w-100">
                              <h6>Welcome back {localStorage.name}</h6>
                            </div>
                            <div className="column w-100 my-1">
                              <a className="profile-icon-item" href="/" onClick={this.signOut}>
                                <img src="https://icon-library.net/images/icon-logout/icon-logout-22.jpg" className="login-sub-icon mr-3"/>Sign Out
                              </a>
                            </div>
                            <hr></hr>

                            <div className="column w-100">
                              <a className="profile-icon-item">
                                <img src="https://icon-library.net/images/my-profile-icon-png/my-profile-icon-png-3.jpg" className="profile-sub-icon mr-3"/>My Profile
                              </a>
                            </div>

                            <div className="column w-100">
                              <a className="profile-icon-item">
                                <img src="http://cdn.onlinewebfonts.com/svg/img_440180.png" className="profile-sub-icon mr-3"/>My Address
                              </a>
                            </div>
                          </div>
                        ):(
                          <div>
                            <div className="column w-100 my-1">
                              <a className="profile-icon-item" href="/login">
                                <img src="https://image.flaticon.com/icons/svg/1286/1286825.svg" className="login-sub-icon mr-3"/>Login
                              </a>
                            </div>

                            <div className="column w-100 my-1">
                              <a className="profile-icon-item" href="/register">
                                <img src="https://cdn0.iconfinder.com/data/icons/cosmetic-store/25/Register-512.png" className="login-sub-icon mr-3"/>Register
                              </a>
                            </div>
                          </div>
                        )
                      }
                    </div>
                </div>
              </div>
              {/* <a className="nav-item nav-link mr-3" href="/">Log out</a> */}
            </div>
          </div>
        </nav>
      </div>
    );
  }
}

//Nav Bar Main Category


export default App;
