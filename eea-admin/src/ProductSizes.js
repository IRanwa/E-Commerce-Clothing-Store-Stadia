import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
const axios = require("axios");

class ProductSizesApp extends Component{
    constructor(props){
        super(props);
        this.state={
            tableData:[],
            pageNo:0,
            noOfPages:0,
            addStatus:false
        }
        this.addStatusChange = this.addStatusChange.bind(this);
        this.btnUpdate = this.btnUpdate.bind(this);
        this.btnDelete = this.btnDelete.bind(this);
        this.retrieveProdSize = this.retrieveProdSize.bind(this);
        this.changePage = this.changePage.bind(this);
        this.modalClose = this.modalClose.bind(this);
        this.addProdSize = this.addProdSize.bind(this);
    }

    componentDidMount(){
        this.retrieveProdSize(this.state.pageNo);
    }

    retrieveProdSize(pageNo){
        const that = this;
        const count = axios.get("http://localhost:8080/Sizes/Pages")
        .then(function(res){
            if(res.data>0){
                that.setState({
                    noOfPages:res.data
                })
                
                return res.data;
            }
            console.log(res.data)
        }).catch(function(error){
            alert("Sizes pages error ",error);
        })

        count.then(data=>{
            if(data>0){
                axios.get("http://localhost:8080/Sizes/"+pageNo)
                .then(function(res){
                    console.log(res.data);
                    that.setState({
                        tableData:res.data,
                        pageNo:pageNo
                    })
                    console.log("Sizes Data Received!");
                }).catch(function(error){
                    console.error("Sizes data error ",error);
                    alert("Sizes data error ",error);
                })
            }
        })
        
    }

    addStatusChange(status){
        this.setState({
            addStatus:status
        })
        
    }

    btnUpdate(){

    }

    btnDelete(){

    }

    changePage(){

    }

    addProdSize(url,data){
        const that = this;
        axios.post(url,data)
        .then(function(res){
            alert("Product Added Successfully!");
            that.addStatusChange(false);
            that.retrieveProdSize(that.state.pageNo);
        }).catch(function(error){
            console.error("Add Product Size ",error);
            alert("Product Size Adding Un-Successful!");
        })
        
    }

    modalClose(){
        const that = this;
        const modal = document.getElementsByClassName("modal")[0];
        window.onclick = function(event) {
            if (event.target === modal) {
              that.setState({
                  addStatus:false
              })
            }
        }
    }

    render(){
        let pagesContent=[];
        if(this.state.noOfPages>10){
            for (let i = 0; i < 10; ++i) {
                pagesContent.push(<div className={this.state.pageNo===i?"paging-selected col-sm":"paging-not-selected col-sm"} key={i} onClick={()=>this.changePage(i)}>{i+1}</div>);
            }
        }else{
            for (let i = 0; i < this.state.noOfPages; ++i) {
                pagesContent.push(<div className={this.state.pageNo===i?"paging-selected col-sm":"paging-not-selected col-sm"} key={i} onClick={()=>this.changePage(i)}>{i+1}</div>);
            }
        }
        return(
            <div className="card w-90 p-3 m-5">
                {
                    this.state.addStatus?(
                        <AddSize modalClose={this.modalClose} addProdSize={this.addProdSize}/>
                    ):("")
                }
                <div className="w-100 container text-center category-menu">
                    <div className="row mt-2 text-center">
                        <h5 className="w-100 m-0">
                            Product Sizes Details
                        </h5>
                    </div>
                    <div className="row mb-1">
                        <button className="btn btn-sm addBtn" onClick={this.addStatusChange}>
                            Add Product Size
                        </button>                        
                    </div>
                    <div className="row">
                        <table className="table category-table ">
                            <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Size</th>
                                    <th>Description</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.tableData.map(data=>{
                                        return (
                                            <tr key={data.id}>
                                                <td>{data.id}</td>
                                                <td>{data.size}</td>
                                                <td>{data.description}</td>
                                                <td>
                                                    <button className="btn btn-sm btn-update w-50 my-1 mr-1" onClick={()=>this.btnUpdate(data.id)}>Update</button>
                                                    <button className="btn btn-sm btn-delete w-50 my-1 mr-1" onClick={()=>this.btnDelete(data.id)}>Delete</button>
                                                </td>
                                            </tr>
                                        );
                                    })
                                }
                            </tbody>
                        </table>
                    </div>
                    <div className="row">
                         <div className="row paging-container">
                             {
                                 pagesContent.map(item=>{
                                     return <div key={item.key}>{item}</div>;
                                 })
                             }
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

class AddSize extends Component{
    constructor(props){
        super(props);
        this.state={
            size:"",
            desc:""
        }
        this.inputChnage = this.inputChnage.bind(this);
        this.btnReset = this.btnReset.bind(this);
        this.addProdSize = this.addProdSize.bind(this);
    }

    inputChnage(target){
        const name = target.name;
        const value = target.value;
        switch(name){
            case "size":
                this.setState({
                    size:value
                })
                break;
            case "desc":
                this.setState({
                    desc:value
                })
                break;
            default:
                console.error("Product Size Input Change Error!");
        }
    }

    addProdSize(){
        const url =  "http://localhost:8080/AddSizes";
        const data = {
            size:this.state.size,
            description:this.state.desc
        }
        this.props.addProdSize(url,data);
    }

    btnReset(){
        this.setState({
            size:"",
            desc:""
        })
    }

    render(){
        return(
            <div className="modal" onClick={this.props.modalClose}>
                <div className="card modal-content  add-cat-popup">
                    <div className="modal-header">
                        <h5>Add Product Size</h5>
                    </div>
                    <div className="modal-body add-cat-popup-body">
                        <div className="add-cat-form">
                            <h6>Size</h6>
                            <input className="w-100" type="text" name="size" value={this.state.size} onChange={(event)=>this.inputChnage(event.target)}/>
                            <h6>Description</h6>
                            <textarea name="desc" className="w-100" value={this.state.desc} onChange={(event)=>this.inputChnage(event.target)}></textarea>
                        </div>
                    </div>
                    <div className="modal-footer">
                        <button className="btn btn-success"  onClick={this.addProdSize}>Add Product Size</button>
                        <button className="btn btn-danger" onClick={this.btnReset}>Reset</button>
                    </div>
                </div>
            </div>
        );
    }
}

export default ProductSizesApp;