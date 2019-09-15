import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import React, { Component } from 'react';
import { Redirect } from "react-router-dom";
import { Fade } from 'react-slideshow-image';
import StarRatings from 'react-star-ratings';
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
class OrderDetails extends Component{
    constructor(props){
        super(props);
        this.state={
            id:props.id,
            order:""
        }
        this.updateOrder = this.updateOrder.bind(this);
        this.changeRating = this.changeRating.bind(this);
        this.changeComment = this.changeComment.bind(this);
        this.saveRating = this.saveRating.bind(this);
    }

    componentDidMount(){
        const that = this;
        axios.get("http://localhost:8080/GetOrder/"+this.state.id,config)
        .then(function(res){
            console.log(res.data)
            that.setState({
                order:res.data
            })
        }).catch(function(error){
            console.log(error);
            if(error.response!==undefined && error.response.status===404){
                alert("Order not found!");
            }
        })
    }

    updateOrder(status){
        axios.put("http://localhost:8080/UpdateOrderStatus",{id:this.state.id,status:status},config)
        .then(function(res){
            console.log(res);
            if(res.data){
                if(status==="Completed"){
                    alert("Order mark as completed succssful!");
                }else{
                    alert("Order mark as cancelled succssful!");
                }
                window.location.reload();
            }else{
                if(status==="Completed"){
                    alert("Order mark as completed un-succssful!");
                }else{
                    alert("Order mark as cancelled un-succssful!");
                }
            }
        }).catch(function(error){
            console.log(error);
            if(status==="Completed"){
                alert("Order mark as completed un-succssful!");
            }else{
                alert("Order mark as cancelled un-succssful!");
            }
        })
    }

    changeRating(newRating, name){
        console.log(newRating)
        console.log(name)
        let order = this.state.order;  
        let orderProducts = order.orderProducts;
        orderProducts[name].rating.rating = newRating;
        this.setState({
            order
        })
    }

    changeComment(index,event){
        let order = this.state.order;  
        let orderProducts = order.orderProducts;
        orderProducts[index].rating.comment = event.target.value;
        this.setState({
            order
        })
    }

    saveRating(index){
        console.log(index);
        let order = this.state.order;  
        let orderProducts = order.orderProducts;
        console.log(orderProducts[index]);
        let orderProd = orderProducts[index];
        orderProd.orders.user = {email:localStorage.email}

        axios.post("http://localhost:8080/AddRating",orderProd,config)
        .then(function(res){
            console.log(res)
            alert("Rating saved successful!");
            window.location.reload();
        }).catch(function(error){
            console.log(error);
            alert("Rating save error!");
        })
    }
    render(){
        const order = this.state.order;
        let status;
        let purchaseDate="";
        let deliverDate="";
        let orderCompleteDate="";
        let billingAdd="";
        let shippingAdd="";
        let orderProducts = [];
        let totalPrice = 0;
        if(order!==""){
            status = order.status;
            switch(status){
                case "Cancelled":
                case "Completed":
                    orderCompleteDate = new Date(order.orderCompleteDate).toLocaleString();
                case "Delivered":
                    if(order.deliverDate!==null){
                        deliverDate = new Date(order.deliverDate).toLocaleString();
                    }
                    
                case "Pending":
                    purchaseDate = new Date(order.purchasedDate).toLocaleString();
                    break;
            }

            let address = order.billingAddress;
            billingAdd = address.address+", "+address.city+", "+address.province+", "+address.zipCode+", "+address.country;

            address = order.shippingAddress;
            shippingAdd = address.address+", "+address.city+", "+address.province+", "+address.zipCode+", "+address.country;

            orderProducts = order.orderProducts;

            for(var x=0;x<orderProducts.length;x++){
                totalPrice += orderProducts[x].productSizes.product.price * orderProducts[x].quantity;
            }
        }

        
        return(
            <div>
                <div className="card register-container">
                    <div>
                        <h4>Order Details</h4>
                    </div>
                    <hr></hr>
                    <div>
                        <div>
                            <div>
                                <div>
                                    Status : {status}
                                </div>
                                <div>
                                        Purchase Date : {purchaseDate!==""?(purchaseDate):("Not set")}
                                </div>
                                {
                                    deliverDate!==""?(
                                        <div>
                                            Deliver Date : {deliverDate}
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
                                            {orderCompleteDate}
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
                                    Total Price : {totalPrice}
                                </div>
                                <div className="m-3 text-center">
                                    {
                                        status==="Pending"?(
                                            <input type="submit" className="btn-sm btn-danger" value="Cancel Order" onClick={()=>this.updateOrder("Cancelled")}/>
                                        ):(
                                            status==="Delivered"?(
                                                <div>
                                                    <input type="submit" className="btn-sm btn-success mr-3" value="Confirm Order Received" onClick={()=>this.updateOrder("Completed")}/>
                                                    <input type="submit" className="btn-sm btn-danger" value="Cancel Order" onClick={()=>this.updateOrder("Cancelled")}/>
                                                </div>
                                            ):(
                                                ""
                                            )
                                        )
                                    }
                                </div>
                            </div>
                            <div>
                                {
                                   orderProducts.map((orderProd,index)=>{
                                       if(orderProd.rating===null){
                                        orderProd.rating = {
                                            rating:0,
                                            comment:""
                                        };
                                       }
                                        return(
                                            <div key={index} className="row mt-4">
                                                <div className="col">
                                                    <div className="slide-container-img">
                                                        {
                                                            <Fade {...properties}>
                                                            {
                                                                orderProd.productSizes.product.productImages.map((image,index)=>{
                                                                    return(
                                                                        <div className="each-fade" key={index}>
                                                                            <div>
                                                                                <img className="product-img-cart" src={image.path} alt={image.id}/>
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
                                                        {orderProd.productSizes.product.title}
                                                    </div>
                                                    <div>
                                                        Size : {orderProd.productSizes.sizes.size}
                                                    </div>
                                                    <div>
                                                        Quantity : {orderProd.quantity}
                                                    </div>
                                                    <div>
                                                        Price : {orderProd.productSizes.product.price}
                                                    </div>
                                                </div>
                                                <div className="col-4">
                                                    {
                                                        orderProd.rating.id===undefined?(
                                                            <div>
                                                                <div>
                                                                    <StarRatings
                                                                        rating={orderProd.rating.rating}
                                                                        starRatedColor="blue"
                                                                        changeRating={this.changeRating}
                                                                        numberOfStars={5}
                                                                        name={index.toString()}
                                                                        starDimension="20px"
                                                                        starSpacing="15px"
                                                                        />
                                                                </div>
                                                                <div className="mt-3">
                                                                    <h6 className="font-weight-bold">Comment</h6>
                                                                    <textarea className="w-100 h-100" onChange={(event)=>this.changeComment(index,event)} value={orderProd.rating.comment}></textarea>
                                                                </div>
                                                                <div>
                                                                    <input type="submit" className="btn-sm btn-success float-right" value="submit" onClick={()=>this.saveRating(index)}/>
                                                                </div>
                                                            </div>
                                                        ):(
                                                            <div>
                                                                <div>
                                                                    <StarRatings
                                                                        rating={orderProd.rating.rating}
                                                                        starRatedColor="blue"
                                                                        numberOfStars={5}
                                                                        starDimension="20px"
                                                                        starSpacing="15px"
                                                                        />
                                                                </div>
                                                                <div className="mt-3">
                                                                    <h6 className="font-weight-bold">Comment</h6>
                                                                    <label>{orderProd.rating.comment}</label>
                                                                </div>

                                                            </div>
                                                        )
                                                    }
                                                </div>
                                            </div>
                                        )
                                   }) 
                                }
                            </div>
                        </div>
                        
                    </div>
                </div>
            </div>
        )
    }
}

export default OrderDetails;