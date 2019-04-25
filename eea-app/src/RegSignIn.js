import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import React, { Component } from 'react';



const sitename = "Stadia";

class RegSignIn extends Component{
    constructor(props){
      super(props);
      this.state={
        signin:this.props.button_title, 
        status:false,
        email:"",
        pass:"",
        conPass:"",
        fName:"",
        lName:"",

        addfName:"",
        addlName:"",
        contactNo:"",
        address:"",
        country:"",
        province:"",
        city:"",    
        zipCode:""
      };

      this.handleTextChange = this.handleTextChange.bind(this);
      this.handleSubmitBtnClick = this.handleSubmitBtnClick.bind(this);
      
    }

    handleTextChange(event){
      switch(event.target.name){
        case "email":
          this.setState({email:event.target.value});
          break;
        case "pass":
          this.setState({pass:event.target.value});
          break;
        case "conPass":
          this.setState({conPass:event.target.value});
          break;
        case "fName":
          this.setState({fName:event.target.value});
          break;
        case "lName":
          this.setState({lName:event.target.value});
          break;
        case "addFName":
          this.setState({addfName:event.target.value});
          break;
        case "addLName":
          this.setState({addlName:event.target.value});
          break;
        case "contactNo":
          this.setState({contactNo:event.target.value});
          break;
        case "address":
          this.setState({address:event.target.value});
          break;
        case "country":
          this.setState({country:event.target.value});
          break;
        case "province":
          this.setState({province:event.target.value});
          break;
        case "city":
          this.setState({city:event.target.value});
          break;
        case "zipCode":
          this.setState({zipCode:event.target.value});
          break;
        default:
          console.log("Error Not Found Input Name");
      }
    }
  
    handleSubmitBtnClick(event){
      if(this.state.signin==="SIGN UP"){
        let address = {
          fname:this.state.addfName,
          lname:this.state.addlName,
          contactno:this.state.contactNo,
          address:this.state.address,
          city:this.state.city,
          province:this.state.province,
          zipcode:this.state.zipCode,
          country:this.state.country
        }

        let user = {
          email:this.state.email,
          login:{
            email:this.state.email,
            pass:this.state.pass,
            fname:this.state.fName,
            lname:this.state.lName,
          },
          address:[
            address
          ]
          

          
        };

        fetch("http://localhost:8080/Register",{
          method:'POST',
          headers:{
            'Accept': 'application/json',
            'Content-Type':'application/json'
          },
          body: JSON.stringify(user)
          
        })
        .then(function (res){
         res.json().then(function(data){
            console.log(data["email"]);
          })
        });

      }else{
  
      }
    }
    
    render(){
      let new_account_text;
      let already_account_text;
      let form_titles;
      let form;
      if(this.props.newaccount){
        new_account_text=(
          <td className="border-topright-line text-center  bg-white font_Britannic button_hover">
            <a href="/register" className="greycolor_text regColumn">New Account</a>
          </td>
          
        );
        already_account_text=(
          <td className="border-bottomleft-line text-center font_Britannic button_hover " >
            <a href="/signin" className="text-white regColumn">Already Registered?</a>
          </td>
        );
        form_titles=(
          <tr>
            <td className="border-right-line">
              <h5 className="text-center font_AgencyFB">Personal Information</h5>
            </td>
            <td>
              <h5 className="text-center font_AgencyFB">Delivery Address Information(Optional)</h5>
            </td>
          </tr>
        );
        form=(
          <tr>
            <td className="border-right-line regForm-Container" >
              <form id="regForm1" className="regForm text-left font_AgencyFB" >
                <label>Email</label><br/>
                <input className="form-control mr-sm-2" type="email" 
                placeholder="Email" name="email" value={this.state.email} 
                onChange={this.handleTextChange}/>
                <label>Password</label><br/>
                <input className="form-control mr-sm-2" type="password" 
                placeholder="Password" name="pass" value={this.state.pass}
                onChange={this.handleTextChange}/>
                <label>Confirm Password</label><br/>
                <input className="form-control mr-sm-2" type="password" 
                placeholder="Confirm Password" name="conPass" value={this.state.conPass}
                onChange={this.handleTextChange}/>
                <label>First Name</label><br/>
                <input className="form-control mr-sm-2" type="text" 
                placeholder="First Name" name="fName" value={this.state.fName}
                onChange={this.handleTextChange}/>
                <label>Last Name</label><br/>
                <input className="form-control mr-sm-2" type="text" 
                placeholder="Last Name" name="lName" value={this.state.lName}
                onChange={this.handleTextChange}/>
              </form>
            </td>
            <td>
            <form id="regForm2" className="regForm text-left font_AgencyFB">
                <label>First Name</label><br/>
                <input className="form-control mr-sm-2" type="text" 
                placeholder="First Name" name="addFName" 
                value={this.state.addfName} onChange={this.handleTextChange}/>
                <label>Last Name</label><br/>
                <input className="form-control mr-sm-2" type="text" 
                placeholder="Last Name" name="addLName"
                value={this.state.addlName} onChange={this.handleTextChange}/>
                <label>Contact No</label><br/>
                <input className="form-control mr-sm-2" type="number" 
                placeholder="Contact No" name="contactNo" 
                value={this.state.contactNo} onChange={this.handleTextChange}/>
                <label>Address</label><br/>
                <input className="form-control mr-sm-2" type="text" 
                placeholder="Address" name="address"
                value={this.state.address} onChange={this.handleTextChange}/>
                <label>Country</label><br/>
                <input className="form-control mr-sm-2" type="text" 
                placeholder="Country" name="country"
                value={this.state.country} onChange={this.handleTextChange}/>
                <label>Province</label><br/>
                <input className="form-control mr-sm-2" type="text" 
                placeholder="Province" name="province"
                value={this.state.province} onChange={this.handleTextChange}/>
                <label>City</label><br/>
                <input className="form-control mr-sm-2" type="text" 
                placeholder="City" name="city"
                value={this.state.city} onChange={this.handleTextChange}/>
                <label>Zip Code/Postal</label><br/>
                <input className="form-control mr-sm-2" type="text" 
                placeholder="Zip Code/Postal" name="zipCode" 
                value={this.state.zipCode} onChange={this.handleTextChange}/>
              </form>
            </td>
          </tr>
        );
      }else{
        new_account_text=(
          <td className="border-bottomright-line   text-center font_Britannic button_hover">
            <a href="/register" className="text-white regColumn">New Account</a>
          </td>
          
        );
        already_account_text=(
          <td className="border-topleft-line text-center  bg-white font_Britannic button_hover" >
            <a href="/signin" className="greycolor_text regColumn">Already Registered?</a>
          </td>
        );
        form=(
          <tr>
            <td className="" colSpan="2">
              <form id="signForm" className="regForm-SignIn-Container text-left font_AgencyFB ">
                <label>Email</label><br/>
                <input className="form-control mr-sm-2" type="email" placeholder="Email"/>
                <label>Password</label><br/>
                <input className="form-control mr-sm-2" type="password" placeholder="Password"/>
              </form>
            </td>
          </tr>
        );
      }
      return(
        <div>
          <h1 className="text-center font_Britannic greycolor_text">{sitename}</h1>
          <table className="text-center text-white main_lightcolor_bg regTable">
            <tr >
              {new_account_text}
              {already_account_text}
            </tr>
            <tr>
              <td className="text-center " colSpan="2">
                <h2 className="font_AgencyFB">{this.props.main_title}</h2>
              </td>
            </tr>
            {form_titles}
            {form}
            <tr>
              <td colSpan="2">
                <input className="btn btn-lg bg-white popup-button font_AgencyFB w-30" type="button" value={this.props.button_title}
                  onClick={this.handleSubmitBtnClick}></input>
              </td>
            </tr>
          </table>
          
          </div>
      );
    }
  }

  class Register extends Component{
    render(){
      return(
        <div>
          
            <RegSignIn newaccount={true} main_title={"SIGN UP USING YOUR EMAIL ADDRESS"}
    button_title={"SIGN UP"}/>
</div>        
      );
    }
  }
  
  class SignIn extends Component{
    render(){
      return(
        <RegSignIn newaccount={false} main_title={"SIGN IN WITH EMAIL"}
        button_title={"SIGN IN"}/>
      );
    }
  }

export {Register,SignIn};