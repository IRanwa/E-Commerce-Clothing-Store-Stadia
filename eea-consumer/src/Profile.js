import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import React, { Component } from 'react';
import {Redirect} from 'react-router-dom';
const axios = require("axios");

class Profile extends Component{
    constructor(props){
        super(props);
        this.state={
            login:"",
            redirectToHome:false,
            command:""
        }

        this.popupWindowOpen = this.popupWindowOpen.bind(this);
        this.modalClose = this.modalClose.bind(this);
    }

    componentDidMount(){
        const that = this;
        //console.log(localStorage)
        const token = 'Bearer '+localStorage.token;
        const headersInfo = {
            'Content-Type':'application/json',
            'Access-Control-Allow-Origin': '*',
            "Access-Control-Allow-Headers": "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With",
            "Access-Control-Allow-Methods": "DELETE, POST, GET, OPTIONS",
            useCredentails: true,
            Authorization:token
        }
        const data = {
            email:localStorage.email
        }
        axios.post("http://localhost:8080/GetUser",data,{
            headers:headersInfo
        }).then(function(res){
            console.log(res.data)
            that.setState({
                login:res.data
            })
        }).catch(function(error){
            console.log(error.response);
            //alert("Error : ",error.status)
            if(error.response.status===401){
                localStorage.removeItem("token");
                localStorage.removeItem("email");
                localStorage.removeItem("name");
                that.setState({
                    redirectToHome:true
                })
            }
        })
    }

    modalClose(command){
        
        const that = this;
        const modal = document.getElementsByClassName("modal")[0];
        const btnClose = document.getElementsByName("btn-close")[0];
        window.onclick = function(event) {
            if (event.target === modal || event.target === btnClose) {
              that.setState({
                command:""
              });
            }
        }
    }

    popupWindowOpen(value){
        
        this.setState({
            command:value
        })
    }

    render(){
        let lastLogin = "";
        if(this.state.login!==""){
            const date = new Date(this.state.login.login.lastLogin);
            lastLogin = date.toLocaleString();
        }
        return(
            <div className="profile-container">
                {
                    this.state.redirectToHome?(
                        <Redirect to="/"/>
                    ):("")
                }
                {
                    this.state.command!==""?(
                        <PopupWindow modalClose={this.modalClose} login={this.state.login} command={this.state.command}/>
                    ):("")
                }
                <div className="row">
                    <div className="col-4 profile-subcontainer-1">
                        <div className="card ">
                            <div className="row">
                                <div className="col-2">
                                    <img src="https://icon-library.net/images/my-profile-icon-png/my-profile-icon-png-3.jpg" className="mr-3 w-100"/>
                                </div>
                                <div className="col-6">
                                    <label>
                                        Welcome {localStorage.name}
                                    </label>
                                </div>
                            </div>
                            <hr/>
                            {
                                this.state.login!==""?(
                                    <div>
                                        <div >
                                            <h6 className="profile-header">Email</h6>
                                            <p> 
                                                {
                                                    this.state.login.email
                                                }
                                            </p>
                                        </div>
                                        <div >
                                            <h6 className="profile-header">Main Contact No</h6>
                                            <p> 
                                                {
                                                    this.state.login.contactNo!==null?(
                                                    this.state.login.contactNo
                                                    ):(
                                                        "Not Set"
                                                    )
                                                }
                                            </p>
                                        </div>
                                        <div >
                                            <h6 className="profile-header">Date of Birth</h6>
                                            <p> 
                                                {
                                                    this.state.login.dob!==null?(
                                                        this.state.login.dob
                                                    ):(
                                                        "Not Set"
                                                    )
                                                }
                                            </p>
                                        </div>
                                        <div >
                                            <h6 className="profile-header">Last Login</h6>
                                            <p> 
                                                {
                                                    
                                                    lastLogin
                                                }
                                            </p>
                                        </div>
                                        <hr/>
                                        <div className="row text-center my-3">
                                            <div className="col">
                                                <button className="btn btn-success" onClick={()=>this.popupWindowOpen("update")}>Change Details</button>
                                            </div>
                                            <div className="col">
                                                <button className="btn btn-danger" onClick={()=>this.popupWindowOpen("delete")}>Delete Account</button>
                                            </div>
                                        </div>
                                    </div>
                                ):("")
                            }
                            

                        </div>
                    </div>
                    <div className="col profile-subcontainer-2">
                        <div className="card">
                            <div className="row">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

class PopupWindow extends Component{
    constructor(props){
        super(props);
        if(props.command=="update"){
            
            this.state = {
                command:props.command,
                email:props.login.email,
                contactNo:props.login.contactNo,
                dob:props.login.dob,
                fName:props.login.login.fname,
                lName:props.login.login.lname
            }
            this.inputChange = this.inputChange.bind(this);
            this.updateWindowRender = this.updateWindowRender.bind(this);
        }else{
            this.state={
                command:props.command,
                email:props.login.email
            }
            this.deleteWindowRender = this.deleteWindowRender.bind(this);
        }
        
        this.btnSubmit = this.btnSubmit.bind(this);
        this.btnReset = this.btnReset.bind(this);
        
    }

    componentDidMount(){
        
    }

    inputChange(){

    }

    deleteWindowRender(){
        return(
            <div className="modal-body add-cat-popup-body">
            </div>
        )
    }

    updateWindowRender(){
        return(
            <div className="modal-body add-cat-popup-body" >
                <div className="add-cat-form">
                     <h6>First Name</h6>
                    <input className="w-100" type="text" name="fname" value={this.state.fName} onChange={(event)=>this.inputChange(event.target)}/>
                    <h6>Last Name</h6>
                    <input className="w-100" type="text" name="lname" value={this.state.lName} onChange={(event)=>this.inputChange(event.target)}/>
                    <h6>Contact No</h6>
                    <input className="w-100" type="text" name="contactno" value={this.state.contactNo!==null?(this.state.contactNo):("")} onChange={(event)=>this.inputChange(event.target)}/>
                    <h6>Date of Birth</h6>
                    <input className="w-100" type="text" name="dob" value={this.state.dob!==null?(this.state.contactNo):("")} onChange={(event)=>this.inputChange(event.target)}/>
                </div>
            </div>
        );
    }


    btnSubmit(){

    }

    btnReset(event){
        if(this.state.command==="update"){
            this.setState({
                contactNo:"",
                dob:"",
                fName:"",
                lName:""
            })
        }else{
            this.props.modalClose();
        }
    }


    render(){
        let header;
        let footer;
        let btnCloseText;
        if(this.state.command==="update"){
            header = "Update Profile Details";
            footer = "Update";
            btnCloseText = "Reset";
        }else{
            header = "Delete Account";
            footer = "Delete";
            btnCloseText = "Cancel";
        }
        return(
            <div className="modal" onClick={this.props.modalClose}>
                <div className="card modal-content  add-cat-popup">
                    <div className="modal-header">
                        <h5>{header}</h5>
                    </div>
                    {
                        this.state.command==="update"?(
                            <this.updateWindowRender/>
                        ):(
                            <this.deleteWindowRender/>
                        )
                    }
                    <div className="modal-footer">
                        <button className="btn btn-success"  onClick={this.btnSubmit}>
                            {footer}
                        </button>
                        <button className="btn btn-danger" name="btn-close" onClick={(event)=>this.btnReset(event)}>{btnCloseText}</button>
                    </div>
                </div>
            </div>
        );
    }
}

export default Profile;