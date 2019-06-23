import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import React, { Component } from 'react';

const sitename = "Stadia";

class RegSignIn extends Component{
    constructor(props){
      super(props);
      this.state={
        signin:this.props.button_title, 
        passMatch:true,
        emailValidate:true,
        mainValidation:true,
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

    checkPasswordMatch(event){
      if(event.target.name==="pass"){
        if( this.state.conPass===event.target.value){
          this.setState({passMatch:true})
        }else{
          this.setState({passMatch:false})
        }
      }else{
        if(this.state.pass!=="" && this.state.pass===event.target.value){
          this.setState({passMatch:true})
        }else{
          this.setState({passMatch:false})
        }
      }
    }

    validateEmail(event){
      let emailvalid = event.target.value.match(/^([\w.%+-]+)@([\w-]+\.)+([\w]{2,})$/i);
      if(event.target.value==="" || emailvalid){
        this.setState({emailValidate:true});
      }else{
        this.setState({emailValidate:false});
      }
    }

    handleTextChange(event){
      switch(event.target.name){
        case "email":
          this.validateEmail(event);
          this.setState({email:event.target.value});
          break;
        case "pass":
          this.checkPasswordMatch(event);
          this.setState({pass:event.target.value});
          
          break;
        case "conPass":
          this.checkPasswordMatch(event);
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
        if(this.state.email.trim()==="" ||this.state.pass.trim()==="" || this.state.conPass.trim()===""
        || this.state.fName.trim()==="" || this.state.lName.trim()===""){
          this.setState({mainValidation:false});
        }else{
          let user = {
            email:this.state.email,
            login:{
              email:this.state.email,
              pass:this.state.pass,
              fname:this.state.fName,
              lname:this.state.lName,
            },
            address:[

            ]
          };

          let address = {
            fname:this.state.addfName,
            lname:this.state.addlName,
            contactNo:this.state.contactNo,
            address:this.state.address,
            city:this.state.city,
            province:this.state.province,
            zipCode:this.state.zipCode,
            country:this.state.country
          }

          if(address["fname"]!=="" && address["lname"]!=="" 
          && address["contactNo"]!=="" && address["address"]!==""
          && address["city"]!=="" && address["province"]!==""
          && address["zipCode"]!=="" && address["country"]!==""){
            user.address.push(address);
          }

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
              console.log(data["Status"]);
            })
          });
        }
      }else{
  
      }
    }
    
    registerNewAccoutText(){
      return(
        <td className="border-topright-line text-center  bg-white font_Britannic button_hover">
          <a href="/register" className="greycolor_text regColumn">New Account</a>
        </td>
      );
    }

    registerAlreadyAccountText(){
      return(
        <td className="border-bottomleft-line text-center font_Britannic button_hover " >
          <a href="/signin" className="text-white regColumn">Already Registered?</a>
        </td>
      );
    }

    registerFormTitle(){
      return(
        <tr>
          <td className="border-right-line">
            <h5 className="text-center font_AgencyFB">Personal Information</h5>
          </td>
          <td>
            <h5 className="text-center font_AgencyFB">Delivery Address Information(Optional)</h5>
          </td>
        </tr>
      );
    }
    

    registerForm(){
      let passMatchText;
      let emailValidateText;
      let mainValidationText;
      if(!this.state.passMatch){
        passMatchText=(
          <p className="Red-Text m-0 ">*Password not matched!</p>
        );
      }
      if(!this.state.emailValidate){
        emailValidateText=(
          <p className="Red-Text m-0">*Email Address not matched!</p>
        );
      }
      if(!this.state.mainValidation){
        mainValidationText=(
          <p className="Red-Text m-0">*Please filled the field!</p>
        );
      }
      return(
        <tr>
          <td className="border-right-line regForm-Container" >
            <form id="regForm1" className="regForm text-left font_AgencyFB" >
              <label>Email</label><br/>
              <input className="form-control mr-sm-2" type="email" 
              placeholder="Email" name="email" maxLength="80"
              value={this.state.email} 
              onChange={this.handleTextChange}/>
              {emailValidateText}
              {mainValidationText}
              <label>Password</label><br/>
              <input className="form-control mr-sm-2" type="password" 
              placeholder="Password" name="pass" maxLength="15" 
              value={this.state.pass}
              onChange={this.handleTextChange}/>
              {mainValidationText}
              <label>Confirm Password</label><br/>
              <input className="form-control mr-sm-2" type="password" 
              placeholder="Confirm Password" name="conPass" maxLength="15"
              value={this.state.conPass}
              onChange={this.handleTextChange}/>
              {passMatchText}
              {mainValidationText}
              <label>First Name</label><br/>
              <input className="form-control mr-sm-2" type="text" 
              placeholder="First Name" name="fName" maxLength="15"
              value={this.state.fName}
              onChange={this.handleTextChange}/>
              {mainValidationText}
              <label>Last Name</label><br/>
              <input className="form-control mr-sm-2" type="text" 
              placeholder="Last Name" name="lName" maxLength="15"
              value={this.state.lName}
              onChange={this.handleTextChange}/>
              {mainValidationText}
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
    }

    signInNewAccountText(){
      return(
        <td className="border-bottomright-line   text-center font_Britannic button_hover">
          <a href="/register" className="text-white regColumn">New Account</a>
        </td>
      );
    }

    signInAlreadyAccountText(){
      return(
        <td className="border-topleft-line text-center  bg-white font_Britannic button_hover" >
          <a href="/signin" className="greycolor_text regColumn">Already Registered?</a>
        </td>
      );
    }

    signInForm(){
      return(
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

    render(){
      let new_account_text;
      let already_account_text;
      let form_titles;
      let form;
      if(this.props.newaccount){
        new_account_text=this.registerNewAccoutText();
        already_account_text=this.registerAlreadyAccountText();
        form_titles=this.registerFormTitle();
        form=this.registerForm();
      }else{
        new_account_text=this.signInNewAccountText();
        already_account_text=this.signInAlreadyAccountText();
        form=this.signInForm();
      }
      return(
        <div>
          <h1 className="text-center font_Britannic greycolor_text">{sitename}</h1>
          <table className="text-center text-white main_color regTable">
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
        <RegSignIn newaccount={true} main_title={"SIGN UP USING YOUR EMAIL ADDRESS"}
    button_title={"SIGN UP"}/>     
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