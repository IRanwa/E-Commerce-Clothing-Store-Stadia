import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import { Redirect } from "react-router-dom";
import { Fade } from 'react-slideshow-image';
import React, { Component } from 'react';
const axios = require("axios");

class ProductList extends Component{
    constructor(props){
        super(props);
        this.state={
            mainCat:props.mainCat,
            subCat:props.subCat,
            sortBy:"Date_Newest",
            pageNo:0,
            noOfPages:0,
            prodList:[]
        }
        this.retrieveProductList = this.retrieveProductList.bind(this);
    }

    componentDidMount(){
        this.retrieveProductList(this.state.pageNo);
    }

    retrieveProductList(pageNo){
        const that = this;
        if(this.state.mainCat===0){
            const count = axios.get("http://localhost:8080/Product/Pages",{})
            .then(function(res){
                if(res.data>0){
                    that.setState({
                        noOfPages:res.data
                    })
                    return res.data;
                }
            })

            count.then(data=>{
                if(data>0){
                    axios.post("http://localhost:8080/Product/"+pageNo,{sortBy:this.state.sortBy})
                    .then(function(res){
                        console.log(res.data)
                        that.setState({
                            prodList:res.data,
                            pageNo:pageNo
                        })
                        console.log("Product Data Received!");
                    }).catch(function(error){
                        console.log("Product data error ",error);
                    })
                }
            })
        }else{

        }
    }

    render(){
        return(
            <div className="container">
                <div className="row">
                {
                    this.state.prodList.map(product=>{
                        return <ProductView product={product} key={product.id}/>
                    })
                }
                </div>
            </div>
        )
    }
}

class ProductView extends Component{
    
    constructor(props){
        super(props);
        this.state={
            selectedId:0,
            product:props.product,
            redirectToDetails:false
        }
        this.viewProductDetails = this.viewProductDetails.bind(this);
    }

    viewProductDetails(id){
        this.setState({
            selectedId:id,
            redirectToDetails:true
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
        return(
            
            <div className="row">
                {
                    this.state.redirectToDetails?(
                        <Redirect to={{
                            pathname: '/productdetails',
                            state: { id: this.state.selectedId }
                          }}/>
                    ):("")
                }
                <div className="card product-container column">
                    <div className="slide-container">
                        <Fade {...properties}>
                        {
                            this.state.product.productImages.map((image,index)=>{
                                return(
                                    <div className="each-fade" key={index}>
                                        <div>
                                        <img className="product-img" src={image.path} alt={image.id} onClick={()=>this.viewProductDetails(this.state.product.id)}/>
                                        </div>
                                    </div>
                                );
                            })
                        }
                        </Fade>
                    </div>
                    
                    <div className="card-body">
                        <h5 className="product-title set-cursor" onClick={()=>this.viewProductDetails(this.state.product.id)}>{this.state.product.title}</h5>
                        <p className="product-price">Rs. {this.state.product.price}</p>
                    </div>
                </div>
                
            </div>
        );
    }
}

export default ProductList;