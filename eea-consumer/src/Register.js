import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import React, { Component } from 'react';
// import { Redirect } from "react-router-dom";
let csc = require('country-state-city').default;
const axios = require("axios");

class Register extends Component{
    constructor(props){
        super(props);

        this.state = { 
            countriesList: [],
            selectedCountry: "",
            statesList:[],
            selectedState:"",
            cityList:[],
            selectedCity:"",

            email:"",
            firstName:"",
            lastName:"",
            password:"",
            conPassword:"",
            contactNo:"",
            address:"",
            country:"",
            state:"",
            city:"",
            zipCode:""
        };

        

        this.selectCountry = this.selectCountry.bind(this);
        this.selectState = this.selectState.bind(this);
        this.selectCity = this.selectCity.bind(this);

        this.changeInputText = this.changeInputText.bind(this);
        this.registerUser = this.registerUser.bind(this);
    }

    componentDidMount(){
        console.log(this.props)
        if(this.props!=={}){
            const email = this.props.email;
            const fullName = this.props.name;
            const nameList = fullName.split(" ");
            this.setState({
                email:email,
                firstName:nameList[0],
                lastName:nameList[nameList.length-1],
                countriesList:csc.getAllCountries()
            })
        }
        
    }

    changeInputText(event){
        const name = event.target.name;
        const value = event.target.value;
       switch(name){
           case "email":
               this.setState({
                   email:value
               })
               break;
            case "firstName":
                this.setState({
                    firstName:value
                })
                break;
            case "lastName":
                this.setState({
                    lastName:value
                })
                break;
            case "password":
                this.setState({
                    password:value
                })
                break;
            case "conPassword":
                this.setState({
                    conPassword:value
                })
                break;
            case "contactNo":
                this.setState({
                    contactNo:value
                })
                break;
            case "address":
                this.setState({
                    address:value
                })
                break;
            case "zipCode":
                this.setState({
                    zipCode:value
                })
                break;
       }
    }

    selectCountry (val) {
        this.setState({ 
            selectedCountry: val,
            statesList:csc.getStatesOfCountry(val)
        });
    }

    selectState (val) {
        this.setState({ 
            selectedState: val,
            cityList:csc.getCitiesOfState(val)
        });
    }

    selectCity(val){
        this.setState({ 
            selectedCity: val
        });
    }

    registerUser(){
        const address = [{
            fname:this.state.firstName,
            lname:this.state.lastName,
            contactNo:this.state.contactNo,
            address:this.state.address,
            city:csc.getCityById(this.state.selectedCity).name,
            province:csc.getStateById(this.state.selectedState).name,
            zipCode:this.state.zipCode,
            country:csc.getCountryById(this.state.selectedCountry).name,
            addType:"Shipping"
        }]

        const login = {
            email:this.state.email,
            pass:this.state.password,
            fname:this.state.firstName,
            lname:this.state.lastName,
            role:"Consumer"
        };

        const user = {
            login :login,
            email:this.state.email,
            address:address
        }

        axios.post("http://localhost:8080/Register",user)
        .then(function(res){
            alert("Registered Successfully!");
            console.log(login);
            axios.post("http://localhost:8080/authenticate/",login)
            .then(function(res){
                localStorage.setItem("email",login.email);
                localStorage.setItem("token",res.data.jwttoken);
            });
        })
    }

    render(){
        return(
            <div>
                <div className="card register-container">
                    <div className="row">
                        <div className="column register-subcontainer">
                            <div>
                                <h5 className="text-center">User Details</h5>
                                <hr></hr>
                                <h6 className="register-text">Email</h6>
                                <input type="text" className="card register-input-text" value={this.state.email} onChange={(event)=>this.changeInputText(event)}
                                name="email"/>
                                <h6 className="register-text">First Name</h6>
                                <input type="text" className="card register-input-text" value={this.state.firstName} onChange={(event)=>this.changeInputText(event)}
                                name="firstName"/>
                                <h6 className="register-text">Last Name</h6>
                                <input type="text" className="card register-input-text" value={this.state.lastName} onChange={(event)=>this.changeInputText(event)}
                                name="lastName"/>
                                <h6 className="register-text">Password</h6>
                                <input type="password" className="card register-input-text" value={this.state.password} onChange={(event)=>this.changeInputText(event)}
                                name="password"/>
                                <h6 className="register-text">Confirm Password</h6>
                                <input type="password" className="card register-input-text" value={this.state.conPassword} onChange={(event)=>this.changeInputText(event)}
                                name="conPassword"/>
                            </div>
                        </div>
                        <div className="column register-subcontainer">
                            <div>
                                <h5 className="text-center">Address Details (Optional)</h5>
                                <hr></hr>
                                <h6 className="register-text">Contact No</h6>
                                <input type="number" className="card register-input-text" value={this.state.contactNo} onChange={(event)=>this.changeInputText(event)}
                                name="contactNo"/>
                                <h6 className="register-text">Address</h6>
                                <input type="text" className="card register-input-text" value={this.state.address} onChange={(event)=>this.changeInputText(event)}
                                name="address"/>

                                <h6 className="register-text">Country</h6>
                                <select className="card register-input-text" value={this.state.selectedCountry} onChange={(event)=>this.selectCountry(event.target.value)}>
                                    {
                                        this.state.countriesList.map((country)=>{
                                            return <option key={country.id} value={country.id}>{country.name}</option>  
                                        })
                                    }
                                </select>

                                <h6 className="register-text">States</h6>
                                <select className="card register-input-text" value={this.state.selectedState} onChange={(event)=>this.selectState(event.target.value)}>
                                    {
                                        this.state.statesList.map((state)=>{
                                            return <option key={state.id} value={state.id}>{state.name}</option>  
                                        })
                                    }
                                </select>

                                <h6 className="register-text">City</h6>
                                <select className="card register-input-text" value={this.state.selectedCity} onChange={(event)=>this.selectCity(event.target.value)}>
                                    {
                                        this.state.cityList.map((city)=>{
                                            return <option key={city.id} value={city.id}>{city.name}</option>  
                                        })
                                    }
                                </select>
                                
                                <h6 className="register-text">Zip Code/Postal Code</h6>
                                <input type="text" className="card register-input-text" value={this.state.zipCode} onChange={(event)=>this.changeInputText(event)}
                                name="zipCode"/>

                            </div>
                        </div>
                    </div>
                    <div className="text-center">
                        <button className="btn btn-success" onClick={this.registerUser} >Register</button>
                    </div>
                </div>
            </div>
        );
    }
}

export default Register;