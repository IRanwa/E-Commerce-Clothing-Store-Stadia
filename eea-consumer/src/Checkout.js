import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import { Link } from "react-router-dom";
import { Fade } from 'react-slideshow-image';
import React, { Component } from 'react';
import {Redirect} from 'react-router-dom';
const axios = require("axios");
const card_valid = require('card-validator');

const config = {
    headers:{
        Authorization:"Bearer "+localStorage.token
    }
}

const properties = {
    duration: 5000,
    transitionDuration: 500,
    infinite: true,
    indicators: true,
    arrows: false,
}

class Checkout extends Component{
    constructor(props){
        super(props);
        this.state={
            redirectToHome:false,
            addressList:[],
            orderProdList:[],
            orderProdQtyEmptyList:[],
            shippingAddress:"",
            billingAddress:"",
            paymentMethod:"",

            cardno:"",
            cardExpireDate:"",
            cardCVV:"",
            cardValidation:false,
            cardSecureNo:""
        }

        this.changeAddress = this.changeAddress.bind(this);
        this.changePaymentMethod = this.changePaymentMethod.bind(this);
        this.placeOrder = this.placeOrder.bind(this);
        this.saveCard = this.saveCard.bind(this);
        this.cardInputChange = this.cardInputChange.bind(this);
    }

    componentWillMount(){
        const that = this;
        axios.get("http://localhost:8080/GetAddressList/"+localStorage.email,config)
        .then(function(res){
            if(res.data.length>0){
                that.setState({
                    addressList:res.data,
                    shippingAddress:res.data[0].id,
                    billingAddress:res.data[0].id
                })
            }
        }).catch(function(error){
            if(error.response.status===401){
                localStorage.removeItem("token");
                localStorage.removeItem("email");
                localStorage.removeItem("name");
                that.setState({
                    redirectToHome:true
                })
            }
            console.log(error.response.status);
        });

        let grandTotal=0;
        axios.post("http://localhost:8080/ViewCart",{
            email:localStorage.email
        },config)
        .then(function(res){
            if(res.data.length>0){
                that.setState({
                    orderProdList:res.data
                })
                for(var i=0;i<res.data.length;i++){
                    const orderItem = res.data[i];
                    if(orderItem.quantity>0){
                        grandTotal += orderItem.productSizes.product.price * orderItem.quantity;
                    }else{
                        this.state.orderProdQtyEmptyList.push(orderItem);
                    }
                }
            }
            
        }).catch(function(error){
            if(error.response.status===401){
                localStorage.removeItem("token");
                localStorage.removeItem("email");
                localStorage.removeItem("name");
                that.setState({
                    redirectToHome:true
                })
            }
            console.log(error.response.status);
        });

        


        const script = document.createElement("script");
        script.src = "https://www.paypal.com/sdk/js?client-id=Afg7tGUieASZi-aQx6PoKRqA90G4YB8xqtclKgJgawptPhCOHF_0Hp4CHccCwi9Iutg-22u_7GamMJhy";
        script.onload=()=>{
            window.paypal.Buttons({
                    style: {
                      layout:  'horizontal',
                      color:   'gold',
                      shape:   'pill',
                    },
                  
                createOrder: function(data, actions) {
                    const price = (grandTotal/180.0).toFixed(2);
                  return actions.order.create({
                    purchase_units: [{
                      amount: {
                        value: price
                      }
                    }]
                  });
                },
                onApprove: function(data, actions) {
                  return actions.order.capture().then(function(details) {
                      console.log(details)
                    alert('Transaction completed by ' + details.payer.name.given_name);
                    const data = {
                        paymentMethod:"Paypal",
                        user:{
                            email:localStorage.email
                        },
                        billingAddress:{
                            id:that.state.billingAddress
                        },
                        shippingAddress:{
                            id:that.state.shippingAddress
                        },
                        orderProducts:that.state.orderProdQtyEmptyList
                    }
        
                    axios.post("http://localhost:8080/PlaceOrder",data,config)
                    .then(function(res){
                        console.log("Order placed successfully!");
                        alert("Order placed successfully!");
                        that.setState({
                            redirectToHome:true
                        })
                    }).catch(function(error){
                        console.log("Order placing un-successful!");
                        alert("Order placing un-successful!");
                    })
                  });
                },
                onCancel: function (data,actions) {
                    console.log("actions ",actions)
                    // Show a cancel page, or return to cart
                  },
                onError:function(data,actions){
                    console.log("Paypal error");
                }
              }).render('#cwppButton');
        }
        script.async = true;

        document.body.appendChild(script);

    }

    changeAddress(target){
        const name = target.name;
        const value = target.value;
        switch(name){
            case "shipping":
                this.setState({
                    shippingAddress:value
                })
                break;
            case "billing":
                this.setState({
                    billingAddress:value
                })
                break;
        }
    }

    changePaymentMethod(value){
        if(value==="Paypal"){
            document.getElementById('cwppButton').hidden = false;
            document.getElementById('place-order').hidden = true;
        }else{
            document.getElementById('cwppButton').hidden = true;
            document.getElementById('place-order').hidden = false;
        }
        this.setState({
            paymentMethod:value
        })
    }

    placeOrder(){
        const that = this;
        if(this.state.paymentMethod!==""){
            const data = {
                paymentMethod:this.state.paymentMethod,
                user:{
                    email:localStorage.email
                },
                billingAddress:{
                    id:this.state.billingAddress
                },
                shippingAddress:{
                    id:this.state.shippingAddress
                },
                orderProducts:this.state.orderProdQtyEmptyList
            }

            axios.post("http://localhost:8080/PlaceOrder",data,config)
            .then(function(res){
                console.log("Order placed successfully!");
                alert("Order placed successfully!");
                that.setState({
                    redirectToHome:true
                })
            }).catch(function(error){
                console.log("Order placing un-successful!");
                alert("Order placing un-successful!");
            })

        }
        
    }

    cardInputChange(target){
        const name = target.name;
        const value = target.value;
        switch(name){
            case "card-no":
                this.setState({
                    cardno:value
                })
                break;
            case "card-expire":
                this.setState({
                    cardExpireDate:value
                })
                break;
            case "card-cvv":
                this.setState({
                    cardCVV:value
                })
                break;
        }
    }

    saveCard(){
        let status = false;
        const numberValidation = card_valid.number(this.state.cardno);
        if (numberValidation.isPotentiallyValid && numberValidation.isValid) {
            const expiredateValidate = card_valid.expirationDate(this.state.cardExpireDate);
            if(expiredateValidate.isPotentiallyValid && expiredateValidate.isValid){
                const cvvValidate = card_valid.cvv(this.state.cardCVV);
                if(cvvValidate.isPotentiallyValid && cvvValidate.isValid){
                    status = true;
                }
            }
        }

        if(status===true){
            const numberList = this.state.cardno;
            const firstDigits = this.state.cardno.substring(0,4);
            let cardNo = firstDigits;
            for(var i=0;i<this.state.cardno.length-4;i++){
                if(i/4===0 || i/4===1){
                    cardNo += "-";
                }
                cardNo += "X";
            }
            this.setState({
                cardValidation:true,
                cardSecureNo:cardNo
            })
            alert("Card details saved successfully!");
        }else{
            this.setState({
                cardValidation:false,
                cardno:"",
                cardExpireDate:"",
                cardCVV:""
            })
            alert("Invalid Card Details!");
        }

        
        
        
    }

    render(){
        let grandTotal=0;
        return(
            <div className="card details-container p-3 placeorder-container">
                {
                    this.state.redirectToHome?(
                        <Redirect to="/"/>
                    ):("")
                }
                <div className="row">
                    <div className="col card p-2 m-2">
                        <h5 style={{fontWeight:"bold"}}>Address</h5>
                        <hr></hr>
                        <h6>Shipping Address</h6>
                        <select className="card register-input-text" name="shipping" value={this.state.shippingAddress} onChange={(event)=>this.changeAddress(event.target)}>
                            {
                                this.state.addressList.map((address,index)=>{
                                    return <option value={address.id} key={index}>{address.address}, {address.city}, {address.province}, {address.zipCode}, {address.country}</option>
                                })
                            }
                        </select>
                        <h6>Billing Address</h6>
                        <select className="card register-input-text" name="billing" value={this.state.billingAddress} onChange={(event)=>this.changeAddress(event.target)}>
                            {
                                this.state.addressList.map((address,index)=>{
                                    return <option value={address.id} key={index}>{address.address}, {address.city}, {address.province}, {address.zipCode}, {address.country}</option>
                                })
                            }
                        </select>
                    </div>
                </div>

                <div className="row">
                    <div className="col card p-2 m-2">
                        <h5 style={{fontWeight:"bold"}}>Products</h5>
                        <hr></hr>
                        {
                            this.state.orderProdList.map((orderProd,index)=>{
                                const total = orderProd.productSizes.product.price * orderProd.quantity;
                                grandTotal += total; 
                                return (
                                    <div key={index}>
                                        <div className="row p-0 m-0 my-2">
                                            <div className="checkout-img-slider p-0">
                                                {
                                                    <Fade {...properties}>
                                                    {
                                                        orderProd.productSizes.product.productImages.map((image,index)=>{
                                                            return(
                                                                <div className="each-fade" key={index}>
                                                                    <div>
                                                                        <img className="product-img-cart" src={image.path} alt={image.id} 
                                                                        onClick={()=>this.viewProductDetails(this.state.product.id)}/>
                                                                    </div>
                                                                </div>
                                                            );
                                                        })
                                                    }
                                                    </Fade>
                                                }
                                            </div>
                                            <div className="checkout-prod-details w-75 p-0">
                                                <label><label className="checkout-header6">Quantity : </label> {orderProd.quantity}</label>
                                                <br></br>
                                                <label><label className="checkout-header6">Size : </label> {orderProd.productSizes.sizes.size}</label>
                                                <br></br>
                                                <label><label className="checkout-header6">Price per Item : </label> Rs. {orderProd.productSizes.product.price}</label>
                                                <br></br>
                                                <label className="float-right mr-3"><label className="checkout-header6">Total Price : </label> Rs. {total}</label>
                                            </div>
                                        </div>
                                        <hr></hr>
                                    </div>
                                );
                            })
                        }
                    </div>
                </div>

                <div className="row">
                    <div className="col card p-2 m-2">
                        <h5 style={{fontWeight:"bold"}}>Payment Method</h5>
                        <hr></hr>
                        {
                            
                        }
                        <div>
                            <input type="radio" name="payment-method" value="Cash" onChange={(event)=>this.changePaymentMethod(event.target.value)} disabled={grandTotal>2000?(true):(false)}/>
                            <img src="http://www.iconarchive.com/download/i104399/custom-icon-design/flatastic-11/Cash.ico" className="checkout-payment-icons mx-2"/>
                            {
                                grandTotal>2000?(
                                    <label className="text-danger font-weight-bold">Order total price is over Rs. 2000.</label>
                                ):("")
                            }
                            
                        </div>
                        <div>
                            <input type="radio" name="payment-method" value="Card" onChange={(event)=>this.changePaymentMethod(event.target.value)}/>
                            <img src="https://cdn0.iconfinder.com/data/icons/cash-card-add-on-colored/48/JD-11-512.png" className="mx-1" style={{width:"60px"}}/>
                            {
                                this.state.cardValidation?(
                                    <div>
                                        <label><label className="checkout-header6">Card No : </label>{this.state.cardSecureNo}</label>
                                    </div>
                                ):(
                                    this.state.paymentMethod==="card"?(
                                        <div className="card m-3 p-3 w-50">
                                            <div className="add-cat-form">
                                                <div>
                                                    <img src="https://cdn0.iconfinder.com/data/icons/flat-design-business-set-3/24/payment-method-visa-512.png" style={{width:"30px"}}/>
                                                    <img src="https://icon-library.net/images/master-card-icon/master-card-icon-13.jpg" style={{width:"30px"}}/>
                                                </div>
                                                <h6>Card Number</h6>
                                                <input className="w-100 mb-2" type="text" name="card-no" value={this.state.cardno} onChange={(event)=>this.cardInputChange(event.target)}/>
                                                <h6>Expiration Date</h6>
                                                <input className="w-100 mb-2" type="text" name="card-expire" value={this.state.cardExpireDate} onChange={(event)=>this.cardInputChange(event.target)} placeholder="MM/YY"/>
                                                <h6>CVV</h6>
                                                <input className="w-100 mb-2" type="text" name="card-cvv" value={this.state.cardCVV} onChange={(event)=>this.cardInputChange(event.target)}/>
                                                <button className="btn-sm btn-success" onClick={this.saveCard}>Save</button>
                                            </div>
                                        </div>
                                    ):(
                                        ""
                                    )
                                    
                                )
                            }
                        </div>
                        <div>
                            <input type="radio" name="payment-method" value="Paypal" onChange={(event)=>this.changePaymentMethod(event.target.value)}/>
                            <img src="https://cdn.worldvectorlogo.com/logos/paypal-icon.svg" className="checkout-payment-icons mx-2"/>
                        </div>
                    </div>
                </div>

                <div className="row">
                    <div className="col card p-2 m-2">
                        <div className="row">
                            <div className="col">
                                <label className="float-right"><label className="checkout-header6">Grand Total : </label> Rs. {grandTotal}</label>
                            </div>
                            
                        </div>
                        <div className="row">
                            <div className="col">
                                <button id="place-order" className="btn-sm btn-success float-right" onClick={this.placeOrder}>Place Order</button>
                                <div id='cwppButton' className="float-right" hidden></div>
                            </div>
                        </div>
                        
                    </div>
                </div>
            </div>
        );
    }
}

export default Checkout;