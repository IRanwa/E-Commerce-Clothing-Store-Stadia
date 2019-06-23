import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import React, { Component } from 'react';
const axios = require("axios");

class ProductList extends Component{
    constructor(props){
        super(props);
        this.state={
            mainCat:props.mainCat,
            subCat:props.subCat,
            prodList:[]
        }
    }

    componentDidMount(){
        const that = this;
        if(this.state.mainCat===0){
            axios.get("http://localhost:8080/Product/"+this.state.subCat+"/"+true)
            .then(function(res){
                const productList = res.data.map(item=>{
                    const product = {
                        id:item["id"],
                        title:item["title"],
                        price:item["price"],
                        image:item["productImages"][0]["path"]
                    };
                    return product;
                });
                //that.state.prodList.push(productList);
                that.setState({
                    prodList:productList
                })
            });
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
            product:props.product
        }
    }

    render(){
        return(
            <div className="card m-2 container_column font_AgencyFB">
                <div>
                    <img className="prod_image" src={this.state.product.image}  alt={this.state.product.image}/>
                </div>
                <div>
                    <label className="prod_title">{this.state.product.title}</label>
                </div>
                <div>
                    <label className="prod_price">Rs. {this.state.product.price}</label>
                </div>
            </div>
        );
    }
}

export default ProductList;