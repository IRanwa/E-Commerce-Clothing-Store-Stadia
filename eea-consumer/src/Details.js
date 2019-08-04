import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import { Redirect } from "react-router-dom";
import { Fade } from 'react-slideshow-image';
import React, { Component } from 'react';
const axios = require("axios");

class ProductDetails extends Component{
    constructor(props){
        super(props);
        this.state = {
            id:props.id,
            product:"",
            stockLevel:0,
            selectedSize:0,
            selectQty:0
        }
        this.changeSize = this.changeSize.bind(this);
        this.changeQty = this.changeQty.bind(this);
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

    changeQty(qty){
        this.setState({
            selectQty:qty
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
                                                qty.map((value)=>{
                                                    return(
                                                        <option value={value} key={value}>{value}</option>
                                                    );
                                                })
                                            }
                                        </select>
                                    </div>
                                ):(
                                    qty
                                )
                            }
                            <div className="row my-3">
                                <button className="btn-sm btn-success column m-1">Buy Now</button>
                                <button className="btn-sm btn-danger column m-1">Add to Cart</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            // <div className="container-fluid">
            //     <div className="content-wrapper">	
            //         <div className="item-container">	
            //             <div className="container">	
            //                 <div className="slide-container-details">
            //                     {
            //                         this.state.product!==""?(
            //                             <Fade {...properties}>
            //                             {
            //                                 this.state.product.productImages.map((image,index)=>{
            //                                     return(
            //                                         <div className="each-fade" key={index}>
            //                                             <div>
            //                                             <img className="product-img-details" src={image.path} alt={image.id} onClick={()=>this.viewProductDetails(this.state.product.id)}/>
            //                                             </div>
            //                                         </div>
            //                                     );
            //                                 })
            //                             }
            //                             </Fade>
            //                         ):("")
            //                     }
            //                 </div>
                                
            //                 <div className="col-md-7">
            //                     <div className="product-title">Corsair GS600 600 Watt PSU</div>
            //                     <div className="product-desc">The Corsair Gaming Series GS600 is the ideal price/performance choice for mid-spec gaming PC</div>
            //                     <div className="product-rating"><i className="fa fa-star gold"></i> <i className="fa fa-star gold"></i> <i className="fa fa-star gold"></i> <i className="fa fa-star gold"></i> <i className="fa fa-star-o"></i> </div>
            //                     <hr/>
            //                     <div className="product-price">$ 1234.00</div>
            //                     <div className="product-stock">In Stock</div>
            //                     <hr/>
            //                     <div className="btn-group cart">
            //                         <button type="button" className="btn btn-success">
            //                             Add to cart 
            //                         </button>
            //                     </div>
            //                     <div className="btn-group wishlist">
            //                         <button type="button" className="btn btn-danger">
            //                             Add to wishlist 
            //                         </button>
            //                     </div>
            //                 </div>
            //             </div> 
            //         </div>
            //         <div className="container-fluid">		
            //             <div className="col-md-12 product-info">
            //                     <ul id="myTab" className="nav nav-tabs nav_tabs">
                                    
            //                         <li className="active"><a href="#service-one" data-toggle="tab">DESCRIPTION</a></li>
            //                         <li><a href="#service-two" data-toggle="tab">PRODUCT INFO</a></li>
            //                         <li><a href="#service-three" data-toggle="tab">REVIEWS</a></li>
                                    
            //                     </ul>
            //                 <div id="myTabContent" className="tab-content">
            //                         <div className="tab-pane fade in active" id="service-one">
                                    
            //                             <section className="container product-info">
            //                                 The Corsair Gaming Series GS600 power supply is the ideal price-performance solution for building or upgrading a Gaming PC. A single +12V rail provides up to 48A of reliable, continuous power for multi-core gaming PCs with multiple graphics cards. The ultra-quiet, dual ball-bearing fan automatically adjusts its speed according to temperature, so it will never intrude on your music and games. Blue LEDs bathe the transparent fan blades in a cool glow. Not feeling blue? You can turn off the lighting with the press of a button.

            //                                 <h3>Corsair Gaming Series GS600 Features:</h3>
            //                                 <li>It supports the latest ATX12V v2.3 standard and is backward compatible with ATX12V 2.2 and ATX12V 2.01 systems</li>
            //                                 <li>An ultra-quiet 140mm double ball-bearing fan delivers great airflow at an very low noise level by varying fan speed in response to temperature</li>
            //                                 <li>80Plus certified to deliver 80% efficiency or higher at normal load conditions (20% to 100% load)</li>
            //                                 <li>0.99 Active Power Factor Correction provides clean and reliable power</li>
            //                                 <li>Universal AC input from 90~264V — no more hassle of flipping that tiny red switch to select the voltage input!</li>
            //                                 <li>Extra long fully-sleeved cables support full tower chassis</li>
            //                                 <li>A three year warranty and lifetime access to Corsair’s legendary technical support and customer service</li>
            //                                 <li>Over Current/Voltage/Power Protection, Under Voltage Protection and Short Circuit Protection provide complete component safety</li>
            //                                 <li>Dimensions: 150mm(W) x 86mm(H) x 160mm(L)</li>
            //                                 <li>MTBF: 100,000 hours</li>
            //                                 <li>Safety Approvals: UL, CUL, CE, CB, FCC className B, TÜV, CCC, C-tick</li>
            //                             </section>
                                                    
            //                         </div>
            //                     <div className="tab-pane fade" id="service-two">
                                    
            //                         <section className="container">
                                            
            //                         </section>
                                    
            //                     </div>
            //                     <div className="tab-pane fade" id="service-three">
                                                            
            //                     </div>
            //                 </div>
            //                 <hr/>
            //             </div>
            //         </div>
            //     </div>
            // </div>
        );
    }
}

export default ProductDetails;