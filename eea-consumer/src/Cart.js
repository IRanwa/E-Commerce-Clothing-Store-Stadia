import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import { Link } from "react-router-dom";
import { Fade } from 'react-slideshow-image';
import React, { Component } from 'react';
import {Redirect} from 'react-router-dom';
const axios = require("axios");

const config = {
    headers:{
        Authorization:'Bearer '+localStorage.token
    }
}

class Cart extends Component{
    constructor(props){
        super(props);
        this.state={
            orderProducts:[],
            cartStatus:true,
            selectedRows:[],
            remoteItemBtn:false,
            redirectToHome:false,
            redirectToCheckout:false,
        }
        this.changeQty = this.changeQty.bind(this);
        this.removeItem = this.removeItem.bind(this);
        this.checkBoxSelect = this.checkBoxSelect.bind(this);
        this.removeSelectedItems = this.removeSelectedItems.bind(this);
        this.checkoutBtn = this.checkoutBtn.bind(this);
    }

    componentDidMount(){
        const that = this;
        axios.post("http://localhost:8080/ViewCart",{
           email:localStorage.email
            
        },config).then(function(res){
            console.log("View cart data retreive sucessfully!");
            if(res.data!=null && res.data.length>0){
                that.setState({
                    orderProducts:res.data
                })
            }else{
                that.setState({
                    cartStatus: false
                })
                
            }
        }).catch(function(error){
            console.log(error.response)
            if(error.response.status===401 && localStorage.token!==undefined){
                localStorage.removeItem("token");
                localStorage.removeItem("email");
                localStorage.removeItem("name");
                window.location.reload();
            }

            console.log("View cart data retreive un-sucessfully!");
            if(error.data!=null && error.data.length==0){
                alert("Server Error");
                console.log("Server error in cart!");
            }else{
                
                that.setState({
                    cartStatus: false
                })
                
            }
        })
    }

    checkBoxSelect(index,checked){
        if(checked){
            if(index>=0){
                this.setState(state=>{
                    state.selectedRows.push(index);
                    if(state.selectedRows.length>0){
                        return(
                            state.selectedRows,
                            state.remoteItemBtn=true
                        );
                    }else{
                        return(
                            state.selectedRows,
                            state.remoteItemBtn=false
                        );
                    }
                })
                
            }else{
                for(let i=0;i<this.state.orderProducts.length;i++){
                    this.setState(state=>{
                        state.selectedRows.push(state.orderProducts[i].productSizes.id);
                        if(state.selectedRows.length>0){
                            return(
                                state.selectedRows,
                                state.remoteItemBtn=true
                           );
                        }else{
                            return(
                                state.selectedRows,
                                state.remoteItemBtn=false
                            );
                        }
                    })
                    
                }
            }
        }else{
            if(index>=0){
                let position = 0;
                for(var i=0;i<this.state.selectedRows.length;i++){
                    if(this.state.selectedRows[i]===index){
                        position = i;
                        break;
                    }
                }
                this.setState(state=>{
                    state.selectedRows.splice(position,1);
                    if(state.selectedRows.length>0){
                        return(
                            state.selectedRows,
                            state.remoteItemBtn=true
                        );
                    }else{
                        return(
                            state.selectedRows,
                            state.remoteItemBtn=false
                        );
                    }
                })
                
            }else{
                this.setState({
                    selectedRows:[],
                    remoteItemBtn:false
                })
            }
        }
    }

    removeSelectedItems(){
        const that = this;
        let prodSizeIds = [];
        for(var i=0;i<this.state.selectedRows.length;i++){
            prodSizeIds.push({
                productSizes:{
                    id:this.state.selectedRows[i]
                }
            });
        }
        const data = {
            id:this.state.orderProducts[0].orders.id,
            orderProducts:prodSizeIds
        };
        console.log(data)
        axios.post("http://localhost:8080/DeleteCartItems",data,config)
        .then(function(res){
            console.log("Cart selected items removed successfully!");
            alert("Cart selected items removed successfully!");
            window.location.reload();
        }).catch(function(error){
            if(error.status===401){
                localStorage.removeItem("token");
                localStorage.removeItem("email");
                localStorage.removeItem("name");
                that.setState({
                    redirectToHome:true
                })
            }
            console.log("Cart selected items remove un-successful!\nError : ",error.response);
            alert("Cart selected items remove un-successful!");
        });
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
        },config).then(function(res){
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
        const that = this;
        axios.delete("http://localhost:8080/DeleteCartItem/"+orderId+"/"+prodSizeId,config)
        .then(function(res){
            console.log("Cart item removed successfully!");
            alert("Cart item removed successfully!");
            window.location.reload();
        }).catch(function(error){
            if(error.status===401){
                localStorage.removeItem("token");
                localStorage.removeItem("email");
                localStorage.removeItem("name");
                that.setState({
                    redirectToHome:true
                })
            }
            console.log("Cart item removed un-successful!\nError : ",error.response);
            alert("Cart item removed un-successful!");
        });
    }

    checkoutBtn(){
        this.setState({
            redirectToCheckout:true
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
                {
                    this.state.redirectToHome?(
                        <Redirect to="/"/>
                    ):("")
                }
                {
                    this.state.redirectToCheckout?(
                        <Redirect to="/checkout"/>
                    ):("")
                }
                 <div className="w-100 text-center ">

                    
                        {
                            this.state.cartStatus?(
                                <div>
                                    <div className="text-center">
                                        <table className="table  table-striped cart-table">
                                            <thead className="thead-dark">
                                                <tr>
                                                    <th scope="col"><input type="checkbox" onClick={(event)=>this.checkBoxSelect(-1,event.target.checked)}/></th>
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
                                                                <td><input type="checkbox" onClick={(event)=>this.checkBoxSelect(orderProd.productSizes.id,event.target.checked)}/></td>
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
                                            </tbody>
                                        </table>
                                    </div>
                                    <div>
                                        {
                                            this.state.remoteItemBtn?(
                                                <button className="btn-sm btn-danger float-left m-2" onClick={this.removeSelectedItems}>Remove Selected</button>
                                            ):("")
                                        }
                                        <button className="btn-sm btn-success float-right m-2" onClick={this.checkoutBtn}>Checkout</button>
                                    </div>
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