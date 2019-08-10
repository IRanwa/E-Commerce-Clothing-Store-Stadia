import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import { Link } from "react-router-dom";
import { Fade } from 'react-slideshow-image';
import React, { Component } from 'react';
const axios = require("axios");

class Cart extends Component{
    constructor(props){
        super(props);
        this.state={
            orderProducts:[],
            cartStatus:true
        }
        this.changeQty = this.changeQty.bind(this);
        this.removeItem = this.removeItem.bind(this);
    }

    componentDidMount(){
        const that = this;
        axios.post("http://localhost:8080/ViewCart",{
           email:"imesh"
            
        }).then(function(res){
            that.setState({
                orderProducts:res.data
            })
        }).catch(function(res){
            console.log(res)
            if(res.data!=null && res.data.length==0){
                alert("Server Error");
                console.log("Server error in cart!");
            }else{
                that.setState({
                    cartStatus: false
                })
                
            }
        })
    }

    changeQty(value,orderId,prodSizeId,index){
        const that = this;
        axios.post("http://localhost:8080/UpdateCartQty",{
            quantity:value,
            orders:{
                id:orderId
            },
            productSizes:{
                id:prodSizeId
            }
        }).then(function(res){
            
            that.setState(state=>{
                const orderProdList = state.orderProducts;
                let orderProd = orderProdList[index];
                
                orderProd.quantity = value;
                orderProdList.splice(index, 1, orderProd)
                return(
                    orderProdList
                )
            })
        })
    }

    removeItem(orderId,prodSizeId){
        axios.delete("http://localhost:8080/DeleteCartItem/"+orderId+"/"+prodSizeId)
        .then(function(res){
            console.log("Cart deleted successfully!");
            alert("Cart deleted successfully!");
            window.location.reload();
        })
    }

    render(){
        const properties = {
            duration: 5000,
            transitionDuration: 500,
            infinite: true,
            indicators: true,
            arrows: false,
        }
        console.log(this.state.orderProducts)
        return(
            <div className="card w-90 p-1 m-5">
                 <div className="w-100 text-center ">

                    
                        {
                            this.state.cartStatus?(
                                    <div>
                                        <table className="table  table-striped cart-table">
                                            <thead className="thead-dark">
                                                <tr>
                                                    <th scope="col">#</th>
                                                    <th scope="col"></th>
                                                    <th scope="col"></th>
                                                    <th scope="col"></th>
                                                    <th scope="col"></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                {
                                                    this.state.orderProducts.length>0 && this.state.orderProducts.map((orderProd,index)=>{
                                                        let qtyList = [];
                                                        for(var x=1;x<=orderProd.productSizes.quantity;x++){
                                                            qtyList.push(x);
                                                        }
                                                        return(
                                                            <tr key={index}>
                                                                <th scope="row">
                                                                    {index+1}
                                                                </th>
                                                                <td>
                                                                    <div className="slide-container-img">
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
                                                                </td>
                                                                <td >
                                                                    {orderProd.productSizes.product.title}
                                                                </td>
                                                                <td>
                                                                    <p>{orderProd.productSizes.sizes.size}</p>
                                                                    <select className="browser-default custom-select" value={orderProd.quantity} 
                                                                    onChange={(event)=>this.changeQty(event.target.value,orderProd.orders.id, orderProd.productSizes.id,index)}>
                                                                        {
                                                                            qtyList.map((qty,index)=>{
                                                                                return <option key={index} value={qty}>{qty}</option>;
                                                                            })
                                                                        }
                                                                    </select>
                                                                </td>
                                                                <td>
                                                                    <div>
                                                                        <button className="btn-sm btn-danger my-3 w-75" onClick={()=>this.removeItem(orderProd.orders.id,orderProd.productSizes.id)}>Remove</button>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                        );
                                                    })
                                                }
                                                <tr>
                                                    
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                            ):(
                                <div>
                                    <img className="cart-empty" src="https://iticsystem.com/img/empty-cart.png" alt="cart-empty"/><br></br>
                                    <a href="/" className="continue-shop-text">Continue Shopping</a>
                                </div>
                            )
                        }
                        
                    
                </div>
            </div>
        );
    }
}

export default Cart;