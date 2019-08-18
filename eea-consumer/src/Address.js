import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import React, { Component } from 'react';
import {Redirect} from 'react-router-dom';
let csc = require('country-state-city').default;
const axios = require("axios");

const config = {
    headers:{
        Authorization:"Bearer "+localStorage.token
    }
}

class Address extends Component{
    constructor(props){
        super(props);
        this.state={
            addressList:[],
            remoteItemBtn:false,
            popupWindowOpen:false,
            redirectToHome:false,
            address:"",
            command:""
        }

        this.checkBoxSelect = this.checkBoxSelect.bind(this);
        this.removeSelectedItems = this.removeSelectedItems.bind(this);
        this.openPopupWindow = this.openPopupWindow.bind(this);
        this.modalClose = this.modalClose.bind(this);
    }

    componentDidMount(){
        const that = this;
        axios.get("http://localhost:8080/GetAddressList/"+localStorage.email,config)
        .then(function(res){
            console.log(res.data)
            that.setState({
                addressList:res.data
            })
        }).catch(function(error){
            if(error.response.status===401){
                localStorage.removeItem("token");
                localStorage.removeItem("email");
                localStorage.removeItem("name");
                that.setState({
                    redirectToHome:true
                })
            }
            console.log("Address retreieve error!\nError : "+error.response.message);
        })
    }

    checkBoxSelect(index,checked){

    }

    removeSelectedItems(){

    }

    openPopupWindow(command,address){
        this.setState({
            popupWindowOpen:true,
            command:command,
            address:address
        })
    }

    modalClose(){
        const that = this;
        const modal = document.getElementsByClassName("modal")[0];
        const btnClose = document.getElementsByName("btn-close")[0];
        window.onclick = function(event) {
            if (event.target === modal || event.target === btnClose) {
              that.setState({
                command:"",
                popupWindowOpen:false,
              });
            }
        }
    }

    render(){
        return(
            <div>
                {
                    this.state.redirectToHome?(
                        <Redirect to="/"/>
                    ):("")
                }
                {
                    this.state.popupWindowOpen?(
                        <PopupWindow modalClose={this.modalClose} address={this.state.address} command={this.state.command}/>
                    ):("")
                }
                <div className="card w-90 p-1 m-5">
                    <div className="m-3">
                        <button className="btn-sm btn-success" onClick={()=>this.openPopupWindow("add")}>New Address</button>
                    </div>
                    <div className="w-100 text-center ">
                        <div>
                            <div className="text-center" >
                                <table className="table  table-striped cart-table">
                                    <thead className="thead-dark">
                                        <tr>
                                            <th scope="col"><input type="checkbox" onClick={(event)=>this.checkBoxSelect(-1,event.target.checked)}/></th>
                                            <th scope="col">#</th>
                                            <th scope="col">First Name</th>
                                            <th scope="col">Last Name</th>
                                            <th scope="col">Conatct No</th>
                                            <th scope="col">Address</th>
                                            <th scope="col">City</th>
                                            <th scope="col">Province</th>
                                            <th scope="col">Zip Code</th>
                                            <th scope="col">Country</th>
                                            <th scope="col">Type</th>
                                            <th scope="col"></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {
                                            this.state.addressList.length>0 && this.state.addressList.map((address,index)=>{
                                                return(
                                                    <tr key={index}>
                                                        <td><input type="checkbox" onClick={(event)=>this.checkBoxSelect(address.id,event.target.checked)}/></td>
                                                        <th scope="row">
                                                            {index+1}
                                                        </th>
                                                        <td>{address.fname}</td>
                                                        <td>{address.lname}</td>
                                                        <td>{address.contactNo}</td>
                                                        <td>{address.address}</td>
                                                        <td>{address.city}</td>
                                                        <td>{address.province}</td>
                                                        <td>{address.zipCode}</td>
                                                        <td>{address.country}</td>
                                                        <td>{address.addType}</td>
                                                        <td>
                                                            <button className="btn-sm btn-success w-100 my-2" onClick={()=>this.openPopupWindow("update",address)} >Update</button>
                                                            <button className="btn-sm btn-danger w-100 my-2" onClick={()=>this.openPopupWindow("delete",address)}>Delete</button>
                                                        </td>
                                                    </tr>
                                                )
                                            })
                                        }
                                    </tbody>
                                </table>
                            </div>
                            <div>
                                {
                                    this.state.remoteItemBtn?(
                                        <button className="btn-sm btn-danger float-left m-2" onClick={this.removeSelectedItems}>Remove Selected</button>
                                    ):("")
                                }
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
        if(props.command=="update" || props.command=="add"){
            this.state = {
                command:props.command,
                email:localStorage.email,
                id:"",
                fname:"",
                lname:"",
                contactno:"",
                address:"",
                city:"",
                province:"",
                zipcode:"",
                country:"",
                type:"Shipping",

                countriesList: csc.getAllCountries(),
                statesList:[],
                cityList:[],
            }
            this.selectCountry = this.selectCountry.bind(this);
            this.selectState = this.selectState.bind(this);
            this.selectCity = this.selectCity.bind(this);
            
            this.inputChange = this.inputChange.bind(this);
            this.updateWindowRender = this.updateWindowRender.bind(this);
        }else{
            this.state={
                command:props.command,
                email:localStorage.email,
                address:props.address
            }
            this.deleteWindowRender = this.deleteWindowRender.bind(this);
        }
        
        this.btnSubmit = this.btnSubmit.bind(this);
        this.btnReset = this.btnReset.bind(this);
        
    }

    componentDidMount(){
        if(this.state.command==="update"){
            const countryList = this.state.countriesList;
            let countryId;
            let statesList;
            let state;
            let cityList;
            let city;

            for(var i=0;i<countryList.length;i++){
                if(this.props.address.country===countryList[i].name){
                    countryId = countryList[i].id;
                    break;
                }
            }
            
            statesList = csc.getStatesOfCountry(countryList[i].id);
            for(var i=0;i<statesList.length;i++){
                if(this.props.address.province === statesList[i].name){
                    state = statesList[i].id;
                    break;
                }
            }

            cityList = csc.getCitiesOfState(state);
            for(var i=0;i<cityList.length;i++){
                if(this.props.address.city === cityList[i].name){
                    city = cityList[i].id;
                    break;
                }
            }

            this.setState({
                statesList:statesList,
                cityList:cityList,

                country:countryId,
                province:state,
                city:city,

                id:this.props.address.id,
                fname:this.props.address.fname,
                lname:this.props.address.lname,
                contactno:this.props.address.contactNo,
                address:this.props.address.address,
                zipcode:this.props.address.zipCode,
                type:this.props.address.addType,
            })
        }
    }

    selectCountry (val) {
        const statesList = csc.getStatesOfCountry(val);
        this.setState({ 
            country: val,
            statesList:statesList,
            state:statesList[0].id
        });
    }

    selectState (val) {
        const cityList = csc.getCitiesOfState(val);
        this.setState({ 
            province: val,
            cityList:cityList,
            city:cityList[0].id
        });
    }

    selectCity(val){
        this.setState({ 
            city: val
        });
    }

    inputChange(target){
        console.log(this.state.fname)
        const name = target.name;
        const value = target.value;
        switch(name){
            case "fname":
                this.setState({
                    fname:value
                })
                break;
            case "lname":
                this.setState({
                    lname:value
                })
                break;
            case "contactno":
                this.setState({
                    contactno:value
                })
                break;
            case "address":
                this.setState({
                    address:value
                })
                break;
            case "zipcode":
                this.setState({
                    zipcode:value
                })
                break;
            case "type":
                this.setState({
                    type:value
                })
                break;
            
        }
    }

    deleteWindowRender(){
        return(
            <div className="modal-body add-cat-popup-body">
                <p>Are you sure you want to delete ({this.state.address.address}) this address?</p>
            </div>
        )
    }

    updateWindowRender(){
        return(
            <div className="modal-body add-cat-popup-body" >
                <div className="add-cat-form">
                     <h6>First Name</h6>
                    <input className="w-100" type="text" name="fname" value={this.state.fname!==undefined?(this.state.fname):("")} onChange={(event)=>this.inputChange(event.target)}/>
                    <h6>Last Name</h6>
                    <input className="w-100" type="text" name="lname" value={this.state.lname!==undefined?(this.state.lname):("")} onChange={(event)=>this.inputChange(event.target)}/>
                    <h6>Contact No</h6>
                    <input className="w-100" type="number" name="contactno" value={this.state.contactno!==undefined?(this.state.contactno):("")} onChange={(event)=>this.inputChange(event.target)}/>
                    <h6>Address</h6>
                    <input className="w-100" type="text" name="address" value={this.state.address!==undefined?(this.state.address):("")} onChange={(event)=>this.inputChange(event.target)}/>

                    <h6 className="register-text">Country</h6>
                    <select className="card register-input-text" value={this.state.country} onChange={(event)=>this.selectCountry(event.target.value)}>
                        {
                            this.state.countriesList.map((country)=>{
                                return <option key={country.id} value={country.id}>{country.name}</option>  
                            })
                        }
                    </select>

                    <h6 className="register-text">States</h6>
                    <select className="card register-input-text" value={this.state.province} onChange={(event)=>this.selectState(event.target.value)}>
                        {
                            this.state.statesList.map((state)=>{
                                return <option key={state.id} value={state.id}>{state.name}</option>  
                            })
                        }
                    </select>

                    <h6 className="register-text">City</h6>
                    <select className="card register-input-text" value={this.state.city} onChange={(event)=>this.selectCity(event.target.value)}>
                        {
                            this.state.cityList.map((city)=>{
                                return <option key={city.id} value={city.id}>{city.name}</option>  
                            })
                        }
                    </select>
                    
                    <h6>Zip Code/Postal Code</h6>
                    <input className="w-100" type="text" name="zipcode" value={this.state.zipcode!==undefined?(this.state.zipcode):("")} onChange={(event)=>this.inputChange(event.target)}/>
                    
                    <h6 className="register-text">Address Type</h6>
                    <select className="card register-input-text" name="type" value={this.state.type} onChange={(event)=>this.inputChange(event.target)}>
                        <option value="Shipping">Shipping</option>
                        <option value="Billing">Billing</option>
                    </select>
                </div>
            </div>
        );
    }


    btnSubmit(){
        if(this.state.command==="update" || this.state.command==="add"){
            const data = {
                id:this.state.id,
                fname:this.state.fname,
                lname:this.state.lname,
                contactNo:this.state.contactno,
                address:this.state.address,
                country:csc.getCountryById(this.state.country).name,
                city:csc.getCityById(this.state.city).name,
                province:csc.getStateById(this.state.province).name,
                zipCode:this.state.zipcode,
                addType:this.state.type,
                user:{
                    email:this.state.email
                }
            }
            console.log(data)
            this.state.command==="update"?(
                axios.put("http://localhost:8080/UpdateAddress",data,config)
                .then(function(res){
                    console.log("Address updated successfully!");
                    alert("Address updated successfully!");
                    window.location.reload();
                }).catch(function(error){
                    if(error.status===401){
                        localStorage.removeItem("token");
                        localStorage.removeItem("email");
                        localStorage.removeItem("name");
                        window.location.reload();
                    }
                    console.log("Address updating un-successful");
                    alert("Address updating un-successful");
                    
                })
            ):(
                axios.post("http://localhost:8080/NewAddress",data,config)
                .then(function(res){
                    console.log("Address added successfully!");
                    alert("Address added successfully!");
                    window.location.reload();
                }).catch(function(error){
                    if(error.status===401){
                        localStorage.removeItem("token");
                        localStorage.removeItem("email");
                        localStorage.removeItem("name");
                        window.location.reload();
                    }
                    console.log("Address adding un-successful");
                    alert("Address adding un-successful");
                    
                })
            )
        }else{
            axios.delete("http://localhost:8080/DeleteAddress/"+this.state.address.id,config)
            .then(function(res){
                console.log("Address deleted successfully!");
                alert("Address deleted successfully!");
                window.location.reload();
            }).catch(function(error){
                if(error.status===401){
                    localStorage.removeItem("token");
                    localStorage.removeItem("email");
                    localStorage.removeItem("name");
                    window.location.reload();
                }
                console.log("Address deleting un-successful");
                alert("Address deleting un-successful");
                
            })
        }
    }

    btnReset(){
        console.log(this.state.command);
        if(this.state.command==="update" || this.state.command==="add"){
            this.setState({
                fname:"",
                lname:"",
                contactno:"",
                address:"",
                city:"",
                province:"",
                zipcode:"",
                country:"",
                type:"",
                countriesList: csc.getAllCountries(),
                statesList:[],
                cityList:[],
            })
        }else{
            this.props.modalClose();
        }
    }


    render(){
        let header;
        let footer;
        let btnCloseText;
        if(this.state.command==="add"){
            header = "New Address";
            footer = "Save";
            btnCloseText = "Reset";
        }
        else if(this.state.command==="update"){
            header = "Update Address";
            footer = "Update";
            btnCloseText = "Reset";
        }else{
            header = "Delete Address";
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
                        this.state.command==="add" || this.state.command==="update"?(
                            <this.updateWindowRender/>
                        ):(
                            <this.deleteWindowRender/>
                        )
                    }
                    <div className="modal-footer">
                        <button className="btn btn-success" onClick={this.btnSubmit}>
                            {footer}
                        </button>
                        {
                            this.state.command==="update" || this.state.command==="add"?(
                                <button className="btn btn-danger" onClick={this.btnReset}>{btnCloseText}</button>
                            ):(
                                <button className="btn btn-danger" name="btn-close" onClick={this.btnReset}>{btnCloseText}</button>
                            )
                        }
                        
                    </div>
                </div>
            </div>
        );
    }
}

export default Address;