import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import React, { Component } from 'react';
import { Redirect } from "react-router-dom";
import { Fade } from 'react-slideshow-image';
const axios = require("axios");
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

class Orders extends Component{
    constructor(props){
        super(props);
        this.state={
            orders:[],
            redirectToHome:false,
            redirectToViewOrder:false,
            viewOrderId:0
        }
        this.viewOrder = this.viewOrder.bind(this);
    }

    componentDidMount(){
        const that = this;
        console.log(localStorage.email)
        axios.post("http://localhost:8080/ListOrders",{email:localStorage.email},config)
        .then(function(res){
            console.log(res);
            that.setState({orders:res.data});
        }).catch(function(error){
            console.log(error.response);
            if(error.response!=null){
                if(error.response.status===401){
                    localStorage.removeItem("token");
                    that.setState({
                        redirectToHome:true
                    })
                }
            }
        });
    }

    viewOrder(id){
        this.setState({
            redirectToViewOrder:true,
            viewOrderId:id
        })
    }

    render(){
        console.log("orders ",this.state.orders);
        return(
            <div>
                {
                    this.state.redirectToHome?(
                        <Redirect to="/"/>
                    ):("")
                }
                {
                    this.state.redirectToViewOrder?(
                        <Redirect to={{pathname:"/orderdetails",state:{id:this.state.viewOrderId}}}/>
                    ):("")
                }
                <div className="card register-container">
                    <div>
                        <h4>Order Details</h4>
                    </div>
                    <hr></hr>
                    <div>
                        {
                            this.state.orders.map((order,index)=>{
                                const status = order.status;
                                let purchaseDate="";
                                let deliverDate="";
                                let orderCompleteDate="";
                                switch(status){
                                    case "Cancelled":
                                    case "Completed":
                                        orderCompleteDate = new Date(order.orderCompleteDate).toLocaleString();
                                    case "Delivered":
                                        if(order.deliverDate!==null){
                                            deliverDate = new Date(order.deliverDate).toLocaleString();
                                        }
                                    case "Pending":
                                        console.log(index," ",order.purchasedDate)
                                        purchaseDate = new Date(order.purchasedDate).toLocaleString();
                                        break;
                                }

                                let address = order.billingAddress;
                                const billingAdd = address.address+", "+address.city+", "+address.province+", "+address.zipCode+", "+address.country;

                                address = order.shippingAddress;
                                const shippingAdd = address.address+", "+address.city+", "+address.province+", "+address.zipCode+", "+address.country;

                                const orderProds = order.orderProducts;
                                let images = [];
                                for(var x=0;x<orderProds.length;x++){
                                    const prodImages = orderProds[x].productSizes.product.productImages;
                                    for(var y=0;y<prodImages.length;y++){
                                        images.push(prodImages[y]);
                                    }
                                    
                                }

                                return(
                                    <div className="row m-2 mb-4" key={index}>
                                        <div className="col">
                                            <div className="slide-container-img-order">
                                                {
                                                    <Fade {...properties}>
                                                    {
                                                        images.map((image,index)=>{
                                                            return(
                                                                <div className="each-fade" key={index}>
                                                                    <div>
                                                                        <img className="product-img-order" src={image.path} alt={image.id}/>
                                                                    </div>
                                                                </div>
                                                            );
                                                        })
                                                    }
                                                    </Fade>
                                                }
                                            </div>
                                        </div>
                                        <div className="col">
                                            <div>
                                                Status : {status}
                                            </div>
                                            <div>
                                                    Purchase Date : {purchaseDate!==""?(purchaseDate):("Not set")}
                                            </div>
                                            {
                                                deliverDate!==""?(
                                                    <div>
                                                        Deliver Date : 
                                                        <label> {deliverDate}</label>
                                                    </div>
                                                    
                                                ):("")
                                            }
                                             {
                                                orderCompleteDate!==""?(
                                                    <div>
                                                        {
                                                            status==="Completed"?(
                                                                "Complete Date : "
                                                            ):(
                                                                "Cancel Date : "
                                                            )
                                                        }
                                                        <label> {orderCompleteDate}</label>
                                                    </div>
                                                    
                                                ):("")
                                            }
                                            <div>
                                                Payment Method : {order.paymentMethod}
                                            </div>
                                            <div>
                                                Shipping Address : {shippingAdd}
                                            </div>
                                            <div>
                                                Billing Address : {billingAdd}
                                            </div>
                                            <div>
                                                <input type="submit" className="btn-sm btn-success float-right" value="View Order Details" onClick={()=>this.viewOrder(order.id)}/>
                                            </div>
                                        </div>
                                    </div>
                                );
                            })
                        }
                        
                    </div>
                </div>
            </div>
        );
    }
}

export default Orders;