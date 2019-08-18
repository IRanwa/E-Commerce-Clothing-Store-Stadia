import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import React, { Component } from 'react';
import { Link, Redirect } from "react-router-dom";
const axios = require("axios");


class SignIn extends Component{
    constructor(props){
        super(props);

        this.state = {
            email:"",
            password:"",
            name:"",
            redirectToHome:false,
            redirectToRegister:false
        }

        this.loginFacebook = this.loginFacebook.bind(this);
        
        this.changeInput = this.changeInput.bind(this);
        this.signBtn = this.signBtn.bind(this);
        this.socialLogin = this.socialLogin.bind(this);
    }

    componentDidMount(){
        console.log("localStorage ",localStorage)
        const that = this;
        const googleScript = document.createElement("script");
        googleScript.src="https://apis.google.com/js/platform.js";
        googleScript.onload=()=>{
            
            
            window.gapi.signin2.render('my-signin2', {
                // 'scope': 'profile email',
                'scope': 'https://www.googleapis.com/auth/plus.login',
                'width':'0',
                'longtitle': false,
                'theme': 'dark',
                'onsuccess': onSuccess,
                'onfailure': onFailure
            });

            function onSuccess(response){
                console.log("Google Sign In Successful!");
                const data = {
                    email:response.w3.U3,
                    name:response.w3.ig
                }
                that.socialLogin(data);
            }
        
            function onFailure(response){
                console.log("Google Sign In un-Successful!");
            }
        }
        document.body.appendChild(googleScript);

        window.fbAsyncInit = function() {
            window.FB.init({
              appId      : '2353806751334233',
              cookie     : true,
              xfbml      : true,
              version    : 'v4.0'
            });
              
            window.FB.AppEvents.logPageView();   
              
        };

        
        // Load the SDK asynchronously
        (function(d, s, id) {
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) return;
            js = d.createElement(s); js.id = id;
            js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.8&appId=YOUR-APP'S-ID";
            fjs.parentNode.insertBefore(js, fjs);
          }(document, 'script', 'facebook-jssdk'));

        
    }

    loginFacebook(){
        const that = this;
        window.FB.login(
            response => {
                //this.responseFacebook(response)
                if(response.status === 'connected'){
                    console.log("Facebook login successful!");
                    window.FB.api('/me',{ locale: 'en_US', fields: 'name, email' },function(response){
                        that.socialLogin(response)
                    });
                }else{
                    console.log("Facebook login un-successful!");
                }
            },
            {scope : 'public_profile,email'}
        );
    }

    socialLogin(response){
        
        const that = this;
        axios.post("http://localhost:8080/CheckUser",{
            email:response.email
        })
        .then(function(res){
            console.log("facebook signin ",res.data)
            axios.post("http://localhost:8080/authenticate/SocialMedia",res.data)
            .then(function(res){
                const data = res.data;
                console.log(data);
                localStorage.setItem("token",data.jwttoken);
                localStorage.setItem("email",response.email);
                localStorage.setItem("name",(data.fname+" "+data.lname));
                that.setState({
                    redirectToHome:true
                })

            })
        }).catch(function(error){
            console.log(error);
            const res = error.response;
            if(res.data==="" && res.status===404){
                that.setState({
                    email:response.email,
                    name:response.name,
                    redirectToRegister:true
                });
            }else{
                alert("Server Erro!");
            }
        })
    }

    changeInput(event){
        const name = event.target.name;
        const value = event.target.value;
        switch(name){
            case "email":
                this.setState({
                    email:value
                })
                break;
            case "password":
                this.setState({
                    password:value
                })
                break;
        }
    }

    signBtn(){
        const login = {
            email:this.state.email,
            pass:this.state.password
        }

        const that = this;
        axios.post("http://localhost:8080/authenticate/",login)
        .then(function(res){
            const data = res.data;
            console.log(data)
            localStorage.setItem("token",data.jwktoken);
            localStorage.setItem("email",that.state.email);
            localStorage.setItem("name",(data.fname+" "+data.lname));
            that.setState({
                redirectToHome:true
            })

        })
    }

    render(){
        console.log(this.state.email)
        return(
            <div>
               {
                   this.state.redirectToHome?(
                       <Redirect to="/"/>
                   ):("")
               }
               {
                   
                   this.state.redirectToRegister?(
                       <Redirect to={{pathname:"/register",state:{email:this.state.email, name:this.state.name}}}/>
                   ):("")
               }
               <div className="card signin-container">
                    <h6 className="signin-text" >Email</h6>
                    <input type="text" className="card" value={this.state.email} onChange={(event)=>this.changeInput(event)} name="email"/>
                    <h6 className="signin-text my-2">Password</h6>
                    <input type="password" className="card" value={this.state.password} onChange={(event)=>this.changeInput(event)} name="password"/>
                    <div className="row text-nowrap">
                        <div className="col-lg-6 col-md-12 text-center p-2">
                            <button className="btn-sm btn-danger w-75">Reset</button>
                        </div>
                        <div className="col-lg-6 col-md-12 text-center p-2">
                            <button className="btn-sm btn-success w-75" onClick={this.signBtn}>Sign In</button>
                        </div>
                        
                    </div>
                    <hr></hr>
                    <div className="socialLoginBtn-container">
                        <div className="row">
                            <div className="col-lg-6 col-md-12 text-center p-2">
                                <img className="w-100 facebook-btn" src={require('./facebook-login-icon-10-new.jpg')} onClick={this.loginFacebook}/>
                            </div>
                            <div className="col-lg-6 col-md-12 text-center p-2"> 
                                <div className="w-100" data-longtitle="false" id="my-signin2"></div>
                            </div>
                        </div>
                    </div>
                    <div className="text-center my-1">
                        <p>Not registered? <Link to="/register" className="register-text">Register Now</Link></p>
                    </div>
               </div>
            </div>
        );
    }
}

function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
    console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
  }

export default SignIn;