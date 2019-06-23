import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
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
    this.state = {
      login:false,
      menSelected:true,
      selectedMainCat:0,
      selectedSubCat:1,
      mainCatList:[]
    }
    this.handleLoginBtnClick = this.handleLoginBtnClick.bind(this);
    this.genderSelectBtnClick = this.genderSelectBtnClick.bind(this);
    this.getCategories = this.getCategories.bind(this);
    this.setMainCat = this.setMainCat.bind(this);
  }

  componentDidMount(){
    let gender;
    if(this.state.menSelected){
      gender = "Men";
    }else{
      gender = "Women";
    }
    this.getCategories(gender);
  }

  setMainCat(mainCat){
    console.log("updated child",mainCat);
  }

  genderSelectBtnClick(type){
    if(type==="Men"){
      this.setState({
        menSelected:true
      });
    }else{
      this.setState({
        menSelected:false
      });
    }
    this.getCategories(type);
    
  }

  getCategories(gender){
    const that = this;
    axios.get("http://localhost:8080/MainCategory/"+gender)
    .then(function(res){
        let categoryList = [];
        categoryList = res.data.map(mainCat=>{
          const category = {
            id:mainCat["id"],
            title:mainCat["mainCatTitle"]
          }
         return  category;
        });
        //that.state.mainCatList.push(categoryList);
        that.setState({
          mainCatList:categoryList
        })
    });
  }

  handleLoginBtnClick(){
    this.setState({login:!this.state.login});
  }

  render(){
    return(
      <div>
        <nav className="main_color navbar_background navbar">
          <div className="mr-auto">
            <div className="navbar_title button_hover">
              <Link className="text-white"  to="/">{sitename}</Link>
            </div>
            <div className={this.state.menSelected?"navbar_categories button_hover navbar_category_active ":"navbar_categories button_hover"}>
              <Link  onClick={()=>this.genderSelectBtnClick("Men")} to="#">Men</Link>
            </div>
            <div className={this.state.menSelected?"navbar_categories button_hover ":"navbar_categories button_hover navbar_category_active"}>
              <Link onClick={()=>this.genderSelectBtnClick("Women")} to="#">Women</Link>
            </div>
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
            <button id="profileId" className="btn my-2 my-sm-0 button_hover mx-4" type="submit" onClick={this.handleLoginBtnClick}>
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
        <MainCategories mainCatList={this.state.mainCatList} setMainCatMethod={this.setMainCat}/>
        <ProductList mainCat={this.state.selectedMainCat} subCat={this.state.selectedSubCat}/>
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
