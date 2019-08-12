import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import React, { Component } from 'react';
import { Link } from "react-router-dom";
const axios = require("axios");


class SignIn extends Component{
    constructor(props){
        super(props);
        this.responseFacebook = this.responseFacebook.bind(this);
        this.responseGoogle = this.responseGoogle.bind(this);
        this.loginFacebook = this.loginFacebook.bind(this);
        
    }

    componentDidMount(){
        const googleScript = document.createElement("script");
        googleScript.src="https://apis.google.com/js/platform.js";
        googleScript.onload=()=>{
            
            
            window.gapi.signin2.render('my-signin2', {
                'scope': 'profile email',
                'width':'0',
                'longtitle': true,
                'theme': 'dark',
                'onsuccess': onSuccess,
                'onfailure': onFailure
            });

            function onSuccess(response){
                console.log("success ",response)
            }
        
            function onFailure(response){
                console.log("failed ",response)
            }
        }

        window.fbAsyncInit = function() {
            window.FB.init({
              appId      : '2353806751334233',
              cookie     : true,
              xfbml      : true,
              version    : 'v4.0'
            });
              
            window.FB.AppEvents.logPageView();   
              
        };
        
        (function(d, s, id){
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) {return;}
            js = d.createElement(s); js.id = id;
            js.src = "https://connect.facebook.net/en_US/sdk.js";
            fjs.parentNode.insertBefore(js, fjs);
        }(document, 'script', 'facebook-jssdk'));

        document.body.appendChild(googleScript);
    }

    loginFacebook(){
        window.FB.login(
            response => {this.responseFacebook(response)},
            {scope : 'email,public_profile'}
        );
    }
    

    responseFacebook(response){
        console.log("res facebook ",response)
        if(response.status === 'connected'){
            window.FB.api('/me',{ locale: 'en_US', fields: 'name, email' },function(response){
                console.log(response.name)
                console.log(response)
            });
        }
    }

    responseGoogle(response){
        console.log("res ",response)
    }
    

    render(){
        return(
            <div>
               
               <div className="card signin-container">
                    <h6 className="signin-text">Email</h6>
                    <input type="text" className="card"/>
                    <h6 className="signin-text my-2">Password</h6>
                    <input type="password" className="card"/>
                    <div className="row text-nowrap">
                        <div className="col-lg-6 col-md-12 text-center p-2">
                            <button className="btn-sm btn-danger w-75">Reset</button>
                        </div>
                        <div className="col-lg-6 col-md-12 text-center p-2">
                            <button className="btn-sm btn-success w-75">Sign In</button>
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