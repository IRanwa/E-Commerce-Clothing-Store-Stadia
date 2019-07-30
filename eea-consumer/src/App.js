import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import './images.css';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import {Register,SignIn} from './RegSignIn';
import ProductList from './ProductList';
const axios = require("axios");

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
    this.state={
      showNav:false,
      mouseMove:false,
      mainCat:[],
      subCatView:true,
    }
    this.toggleClick = this.toggleClick.bind(this);
    this.getMainCat = this.getMainCat.bind(this);
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
                  <a className="nav-item nav-link" href="/" onMouseMoveCapture={()=>this.getMainCat("M")}>Men</a>
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

                <div className="dropdown dropdown-categories">
                  <a className="nav-item nav-link" href="/" onMouseMoveCapture={()=>this.getMainCat("F")}>Women</a>
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
              <a className="nav-item nav-link mr-3" href="/">Log out</a>
            </div>
          </div>
        </nav>
      </div>
    );
  }
}

//Nav Bar Main Category
class MainCategories extends Component{
  constructor(props){
    super(props);
    this.state={
      mainCatList:props.mainCatList
    }
  }

  componentWillReceiveProps(value){
    this.setState({
      mainCatList:value.mainCatList
    })
  }


  render(){
    //console.log(this.state.mainCatList);
    return(
      <div className="mr-auto main_lightcolor_bg">
        <a className="navbar_subcategories" href="/about">New Arrivals</a>
        {
          this.state.mainCatList.map(mainCat=>{
            return <Link className="navbar_subcategories" to="#" key={mainCat["id"]} onClick={()=>this.props.setMainCatMethod(mainCat["id"])}>{mainCat["title"]}</Link>;
          })
        }
      </div>
    );
  }
}

export default App;
