import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
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

class HomepageApp extends Component{
    constructor(props){
        super(props);
        this.state={
            orders:[]
        }
        this.updateStatusBtn = this.updateStatusBtn.bind(this);
    }

    componentDidMount(){
        const that = this;
        console.log(localStorage.token)
        axios.get("http://localhost:8080/GetRecentOrders",config)
        .then(function(res){
            console.log(res);
            that.setState({orders:res.data});
        }).catch(function(error){
            console.log(error.response);
        });
    }

    updateStatusBtn(status,id){
        console.log(status);
        console.log(id);

        axios.put("http://localhost:8080/UpdateOrderStatus",{id:id,status:status},config)
        .then(function(res){
            console.log(res);
            if(res.data===true){
                window.location.reload();
            }
        }).catch(function(error){
            console.log(error);
        });
    }
    

    render(){
        console.log(this.state.orders);
        return(
            <div>
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
                                    case "Completed" || "Cancelled":
                                        orderCompleteDate = new Date(order.orderCompleteDate).toLocaleTimeString();
                                    case "Delivered":
                                        deliverDate = new Date(order.deliverDate).toLocaleTimeString();
                                    case "Pending":
                                        purchaseDate = new Date(order.purchasedDate).toLocaleString();
                                        break;
                                }

                                let address = order.billingAddress;
                                const billingAdd = address.address+", "+address.city+", "+address.province+", "+address.zipCode+", "+address.country;

                                address = order.shippingAddress;
                                const shippingAdd = address.address+", "+address.city+", "+address.province+", "+address.zipCode+", "+address.country;
                                console.log(billingAdd);

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
                                                            console.log("image",image)
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
                                            <div className="row mt-2">
                                                <div className="col">
                                                    <input type="submit" className="btn-sm btn-success" value="Mark as delivered" onClick={()=>this.updateStatusBtn("Delivered",order.id)}/>
                                                </div>
                                                <div className="col">
                                                    <input type="submit" className="btn-sm btn-danger" value="Mark as cancelled" onClick={()=>this.updateStatusBtn("Cancelled",order.id)}/>
                                                </div>
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

export default HomepageApp;
