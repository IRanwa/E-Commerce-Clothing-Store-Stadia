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

// class RegSignIn extends Component{
//     constructor(props){
//       super(props);
//       this.state={signin:this.props.button_title, status:false};
//       this.handleSubmitBtnClick = this.handleSubmitBtnClick.bind(this);
      
//     }
  
//     handleSubmitBtnClick(){
//       if(this.state.signin==="SIGN UP"){
//         let regForm1 = document.getElementById("regForm1");
//         let regForm2 = document.getElementById("regForm2");
  
//         let regForm1Data = new FormData(regForm1);
//         let regForm2Data = new FormData(regForm2);
  
//       }else{
  
//       }
//     }
    
//     render(){
//       let new_account_text;
//       let already_account_text;
//       let form_titles;
//       let form;
//       if(this.props.newaccount){
//         new_account_text=(
//           <td className="border-topright-line text-center  bg-white font_Britannic button_hover">
//             <Link to="/register" className="greycolor_text regColumn">New Account</Link>
//           </td>
          
//         );
//         already_account_text=(
//           <td className="border-bottomleft-line text-center font_Britannic button_hover " >
//             <Link to="/signin" className="text-white regColumn">Already Registered?</Link>
//           </td>
//         );
//         form_titles=(
//           <tr>
//             <td className="border-right-line">
//               <h5 className="text-center font_AgencyFB">Personal Information</h5>
//             </td>
//             <td>
//               <h5 className="text-center font_AgencyFB">Delivery Address Information(Optional)</h5>
//             </td>
//           </tr>
//         );
//         form=(
//           <tr>
//             <td className="border-right-line regForm-Container" >
//               <form id="regForm1" className="regForm text-left font_AgencyFB" >
//                 <label>Email</label><br/>
//                 <input className="form-control mr-sm-2" type="email" 
//                 placeholder="Email"/>
//                 <label>Password</label><br/>
//                 <input className="form-control mr-sm-2" type="password" placeholder="Password"/>
//                 <label>Confirm Password</label><br/>
//                 <input className="form-control mr-sm-2" type="password" placeholder="Confirm Password"/>
//                 <label>First Name</label><br/>
//                 <input className="form-control mr-sm-2" type="text" placeholder="First Name"/>
//                 <label>Last Name</label><br/>
//                 <input className="form-control mr-sm-2" type="text" placeholder="Last Name"/>
//               </form>
//             </td>
//             <td>
//             <form id="regForm2" className="regForm text-left font_AgencyFB">
//                 <label>First Name</label><br/>
//                 <input className="form-control mr-sm-2" type="text" placeholder="First Name"/>
//                 <label>Last Name</label><br/>
//                 <input className="form-control mr-sm-2" type="text" placeholder="Last Name"/>
//                 <label>Contact No</label><br/>
//                 <input className="form-control mr-sm-2" type="number" placeholder="Contact No"/>
//                 <label>Address</label><br/>
//                 <input className="form-control mr-sm-2" type="text" placeholder="Address"/>
//                 <label>Country</label><br/>
//                 <input className="form-control mr-sm-2" type="text" placeholder="Country"/>
//                 <label>Province</label><br/>
//                 <input className="form-control mr-sm-2" type="text" placeholder="Province"/>
//                 <label>City</label><br/>
//                 <input className="form-control mr-sm-2" type="text" placeholder="City"/>
//                 <label>Zip Code/Postal</label><br/>
//                 <input className="form-control mr-sm-2" type="text" placeholder="Zip Code/Postal"/>
//               </form>
//             </td>
//           </tr>
//         );
//       }else{
//         new_account_text=(
//           <td className="border-bottomright-line   text-center font_Britannic button_hover">
//             <Link to="/register" className="text-white regColumn">New Account</Link>
//           </td>
          
//         );
//         already_account_text=(
//           <td className="border-topleft-line text-center  bg-white font_Britannic button_hover" >
//             <Link to="/signin" className="greycolor_text regColumn">Already Registered?</Link>
//           </td>
//         );
//         form=(
//           <tr>
//             <td className="" colSpan="2">
//               <form id="signForm" className="regForm-SignIn-Container text-left font_AgencyFB ">
//                 <label>Email</label><br/>
//                 <input className="form-control mr-sm-2" type="email" placeholder="Email"/>
//                 <label>Password</label><br/>
//                 <input className="form-control mr-sm-2" type="password" placeholder="Password"/>
//               </form>
//             </td>
//           </tr>
//         );
//       }
//       return(
//         <div>
//           <h1 className="text-center font_Britannic greycolor_text">{sitename}</h1>
//           <table className="text-center text-white main_lightcolor_bg regTable">
//             <tr >
//               {new_account_text}
//               {already_account_text}
//             </tr>
//             <tr>
//               <td className="text-center " colSpan="2">
//                 <h2 className="font_AgencyFB">{this.props.main_title}</h2>
//               </td>
//             </tr>
//             {form_titles}
//             {form}
//             <tr>
//               <td colSpan="2">
//                 <input className="btn btn-lg bg-white popup-button font_AgencyFB w-30" type="button" value={this.props.button_title}
//                   onClick={this.handleSubmitBtnClick}></input>
//               </td>
//             </tr>
//           </table>
          
//           </div>
//       );
//     }
//   }

// class Register extends Component{
//   render(){
//     return(
//       <RegSignIn newaccount={true} main_title={"SIGN UP USING YOUR EMAIL ADDRESS"}
//       button_title={"SIGN UP"}/>
//     );
//   }
// }

// class SignIn extends Component{
//   render(){
//     return(
//       <RegSignIn newaccount={false} main_title={"SIGN IN WITH EMAIL"}
//       button_title={"SIGN IN"}/>
//     );
//   }
// }

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
