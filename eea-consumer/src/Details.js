import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import { Redirect } from "react-router-dom";
import { Fade } from 'react-slideshow-image';
import React, { Component } from 'react';
const axios = require("axios");

const config = {
    headers:{
        Authorization:"Bearer "+localStorage.token
    }
}

class ProductDetails extends Component{
    constructor(props){
        super(props);
        this.state = {
            id:props.id,
            product:"",
            stockLevel:0,
            selectedSize:0,
            selectQty:1,

            redirectToLogin:false
        }
        this.changeSize = this.changeSize.bind(this);
        this.changeQty = this.changeQty.bind(this);
        this.addToCart = this.addToCart.bind(this);
    }

    componentDidMount(){
        const that = this;
        axios.get("http://localhost:8080/GetProduct/"+this.state.id)
        .then(function(res){
            const data = res.data;
            that.setState({
                product:data
            })
            if(data!==null){
                that.setState({
                    stockLevel:data.productSizes[0].quantity,
                    selectedSize:data.productSizes[0].id
                })
            }
        })
    }

    changeSize(index){
        this.setState({
            stockLevel:this.state.product.productSizes[index].quantity,
            selectedSize:this.state.product.productSizes[index].id
        })
    }

    changeQty(value){
        this.setState({
            selectQty:value
        })
    }

    addToCart(){
        if(localStorage.email!==undefined){
            console.log("selected size ",this.state.selectedSize," ",this.state.selectQty)
            axios.post("http://localhost:8080/AddToCart",{
                productSizes:{
                    id:this.state.selectedSize,
                    quantity:this.state.selectQty
            },
            orders:{
                user:{
                    email:localStorage.email 
                } 
            } 
            },config).then(function(res){
                alert("Product added to cart successfully!");
                console.log("Product added to cart successfully!");
            }).catch(function(error){
                alert("Product added to cart un-successfully!");
                console.log("Product added to cart un-successfully!");
            });
        }else{
            this.setState({
                redirectToLogin:true
            })
        }
        
    }

    render(){
        const properties = {
            duration: 5000,
            transitionDuration: 500,
            infinite: true,
            indicators: true,
            arrows: false,
        }

        let qty;
        if(this.state.stockLevel>0){
            let x=1;
            qty=[]
            for(;x<=this.state.stockLevel;x++){
                qty.push(x);
            }
        }else{
            qty = <p className="out-of-stock">Out of Stock!</p>;
        }
        console.log(qty)
        return(
            <div className="card details-container">
                {
                    this.state.redirectToLogin?(
                        <Redirect to="/login"/>
                    ):("")
                }
                <div className="row">
                    <div className="column slide-container slide-container-details ">
                        {
                            this.state.product!==""?(
                                <Fade {...properties}>
                                {
                                    this.state.product.productImages.map((image,index)=>{
                                        return(
                                            <div className="each-fade" key={index}>
                                                <div>
                                                <img className="product-img-details card" src={image.path} alt={image.id} onClick={()=>this.viewProductDetails(this.state.product.id)}/>
                                                </div>
                                            </div>
                                        );
                                    })
                                }
                                </Fade>
                            ):("")
                        }
                    </div>
                    <div className="column vertical-line"></div>
                    <div className="column w-50">
                        <div className="card-body">
                            <h5 className="product-title-details">{this.state.product.title}</h5>
                            <h5 className="product-desc">{this.state.product.description}</h5>
                            <p className="product-price">Rs. {this.state.product.price}</p>
                            <p className="product-desc">Sizes</p>
                            <select className="w-50 card product-sizes" onChange={(event)=>this.changeSize(event.target.value)}>
                                {
                                    
                                    this.state.product!=="" && this.state.product.productSizes.map((size,index)=>{
                                        return(
                                            <option key={index} value={index}>{size.sizes.size}</option>
                                        );
                                    })
                                }
                            </select>
                            
                            {
                                this.state.stockLevel>0?(
                                    <div>
                                        <p className="product-desc">Quantity</p>
                                        <select className="w-50 card product-sizes my-1" onChange={(event)=>this.changeQty(event.target.value)}>
                                            {
                                                qty.map((quantity)=>{
                                                    return(
                                                        <option value={quantity} key={quantity}>{quantity}</option>
                                                    );
                                                })
                                            }
                                        </select>
                                        <div className="row my-3">
                                            <button className="btn-sm btn-success column m-1">Buy Now</button>
                                            <button className="btn-sm btn-danger column m-1" onClick={this.addToCart}>Add to Cart</button>
                                        </div>
                                    </div>
                                ):(
                                    qty
                                )
                            }
                            
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default ProductDetails;