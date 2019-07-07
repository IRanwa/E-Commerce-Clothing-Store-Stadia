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
            addStatus:false,
            updateStatus:false,
            updateId:0,
            deleteStatus:false,
            deleteId:0
        }
        this.statusChange = this.statusChange.bind(this);
        this.retrieveProdSize = this.retrieveProdSize.bind(this);
        this.changePage = this.changePage.bind(this);
        this.modalClose = this.modalClose.bind(this);
        this.genPagesContent = this.genPagesContent.bind(this);
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

    statusChange(status,value,id){
        if(status==="add"){
            this.setState({
                addStatus:value
            })
        }else if(status==="update"){
            this.setState({
                updateStatus:value,
                updateId:id
            })
        }else if(status==="delete"){
            this.setState({
                deleteStatus:value,
                deleteId:id
            })
        }
        
    }

    changePage(pageNo){
        console.log("change pageb , ",pageNo)
        this.setState({
            tableData:[]
        })
        this.retrieveProdSize(pageNo);
    }

    addProdSize(url,data,status){
        const that = this;
        if(status==="add"){
            axios.post(url,data)
            .then(function(res){
                alert("Product Size Added Successfully!");
                that.statusChange("add",false);
                that.retrieveProdSize(that.state.pageNo);
            }).catch(function(error){
                console.error("Add Product Size ",error);
                alert("Product Size Adding Un-Successful!");
            })
        }else if(status==="update"){
            axios.put(url,data)
            .then(function(res){
                alert("Product Size Updated Successfully!");
                that.statusChange("update",false);
                that.retrieveProdSize(that.state.pageNo);
            }).catch(function(error){
                console.error("Update Product Size ",error);
                alert("Product Size Updating Un-Successful!");
            })
        }
        else if(status==="delete"){
            axios.delete(url)
            .then(function(res){
                alert("Product Size Deleted Successfully!");
                that.statusChange("delete",false);
                that.retrieveProdSize(that.state.pageNo);
            }).catch(function(error){
                console.error("Delete Product Size ",error);
                alert("Product Size Deleting Un-Successful!");
            })
        }
        
    }

    modalClose(){
        const that = this;
        const modal = document.getElementsByClassName("modal")[0];
        window.onclick = function(event) {
            if (event.target === modal) {
              that.setState({
                  addStatus:false,
                  updateStatus:false,
                  updateId:0,
                  deleteStatus:false,
                  deleteId:0
              })
            }
        }
    }

    genPagesContent(){
        let pagesContent=[];
        if(this.state.noOfPages>10){
            console.log(this.state.noOfPages)
            console.log(this.state.pageNo)
            let maxNoOfPages = this.state.noOfPages-(this.state.pageNo+1);
            if(maxNoOfPages>10){
                maxNoOfPages = this.state.pageNo+10;
                if(this.state.pageNo>0){
                    pagesContent.push(<div className="paging-not-selected col-sm" key={this.state.pageNo-1} onClick={()=>this.changePage(this.state.pageNo-1)}>{this.state.pageNo}</div>);
                    maxNoOfPages -= 1;
                }
                for (let i = this.state.pageNo; i <maxNoOfPages; ++i) {
                    pagesContent.push(<div className={this.state.pageNo===i?"paging-selected col-sm":"paging-not-selected col-sm"} key={i} onClick={()=>this.changePage(i)}>{i+1}</div>);
                }
            }else if(maxNoOfPages===10){
                maxNoOfPages = this.state.noOfPages;
                for (let i = this.state.noOfPages-12; i <maxNoOfPages; ++i) {
                    pagesContent.push(<div className={this.state.pageNo===i?"paging-selected col-sm":"paging-not-selected col-sm"} key={i} onClick={()=>this.changePage(i)}>{i+1}</div>);
                }
            }else{
                
                maxNoOfPages = this.state.noOfPages;
                for (let i = maxNoOfPages-11; i <maxNoOfPages; ++i) {
                    pagesContent.push(<div className={this.state.pageNo===i?"paging-selected col-sm":"paging-not-selected col-sm"} key={i} onClick={()=>this.changePage(i)}>{i+1}</div>);
                }
            }
        }else{
            for (let i = 0; i < this.state.noOfPages; ++i) {
                pagesContent.push(<div className={this.state.pageNo===i?"paging-selected col-sm":"paging-not-selected col-sm"} key={i} onClick={()=>this.changePage(i)}>{i+1}</div>);
            }
        }
        return pagesContent;
    }

    render(){
        const pagesContent = this.genPagesContent();
        return(
            <div className="card w-90 p-3 m-5">
                {
                    this.state.addStatus?(
                        <SizePopupWindow modalClose={this.modalClose} addProdSize={this.addProdSize} windowStatus="add"/>
                    ):("")
                }
                {
                    this.state.updateStatus?(
                        <SizePopupWindow modalClose={this.modalClose} addProdSize={this.addProdSize} id={this.state.updateId} windowStatus="update"/>
                    ):("")
                }
                {
                    this.state.deleteStatus?(
                        <SizePopupWindow modalClose={this.modalClose} addProdSize={this.addProdSize} id={this.state.deleteId} windowStatus="delete"/>
                    ):("")
                }
                <div className="w-100 container text-center category-menu">
                    <div className="row mt-2 text-center">
                        <h5 className="w-100 m-0">
                            Product Sizes Details
                        </h5>
                    </div>
                    <div className="row mb-1">
                        <button className="btn btn-sm addBtn" onClick={()=>this.statusChange("add",true)}>
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
                                                    <button className="btn btn-sm btn-update w-50 my-1 mr-1" onClick={()=>this.statusChange("update",true,data.id)}>Update</button>
                                                    <button className="btn btn-sm btn-delete w-50 my-1 mr-1" onClick={()=>this.statusChange("delete",true,data.id)}>Delete</button>
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

class SizePopupWindow extends Component{
    constructor(props){
        super(props);
        if(this.props.id===undefined){
            this.state={
                size:"",
                desc:"",
                windowStatus:this.props.windowStatus
            }
        }else{
            this.state={
                id:this.props.id,
                size:"",
                desc:"",
                windowStatus:this.props.windowStatus
            }
        }
        this.inputChnage = this.inputChnage.bind(this);
        this.btnReset = this.btnReset.bind(this);
        this.addProdSize = this.addProdSize.bind(this);
    }

    componentDidMount(){
        if(this.state.windowStatus==="update"){
            const that = this;
            axios.get("http://localhost:8080//GetSize/"+this.state.id)
            .then(function(res){
                const data = res.data;
                that.setState({
                    size:data["size"],
                    desc:data["description"],
                })
                console.log("Size retrieve successful!");
            }).catch(function(error){
                console.error("Size retrieve error ",error);
                alert("Size retrieve error ",error);
            })
        }
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
        let url;
        let data;
        if(this.state.windowStatus==="add"){
            url =  "http://localhost:8080/AddSizes";
            data = {
                size:this.state.size,
                description:this.state.desc
            }
        }else if(this.state.windowStatus==="update"){
            url =  "http://localhost:8080/UpdateSizes/"+this.state.id;
            data = {
                size:this.state.size,
                description:this.state.desc
            }
        }else if(this.state.windowStatus==="delete"){
            url =  "http://localhost:8080/DeleteSizes/"+this.state.id;
        }
        this.props.addProdSize(url,data,this.state.windowStatus);
    }

    btnReset(){
        this.setState({
            size:"",
            desc:""
        })
    }

    render(){
        let header;
        let footer;
        if(this.state.windowStatus==="add"){
            header = "Add Product Size";
            footer = "Add";
        }else if(this.state.windowStatus==="update"){
            header = "Update Product Size";
            footer = "Update";
        }else if(this.state.windowStatus==="delete"){
            header = "Delete Product Size";
            footer = "Delete";
        }
        return(
            <div className="modal" onClick={this.props.modalClose}>
                <div className="card modal-content  add-cat-popup">
                    <div className="modal-header">
                        <h5>{header}</h5>
                    </div>
                    <div className="modal-body add-cat-popup-body">
                        {
                            this.state.windowStatus==="delete"?(
                                <div>
                                    <label>
                                        Are you sure you want to delete product size id - {this.state.id} ?
                                    </label> 
                                </div>
                            ):(
                                <div className="add-cat-form">
                                    <h6>Size</h6>
                                    <input className="w-100" type="text" name="size" value={this.state.size} onChange={(event)=>this.inputChnage(event.target)}/>
                                    <h6>Description</h6>
                                    <textarea name="desc" className="w-100" value={this.state.desc} onChange={(event)=>this.inputChnage(event.target)}></textarea>
                                </div>
                            )
                        }
                        
                    </div>
                    <div className="modal-footer">
                        <button className="btn btn-success"  onClick={this.addProdSize}>
                            {footer}
                        </button>
                        <button className="btn btn-danger" onClick={this.btnReset}>Reset</button>
                    </div>
                </div>
            </div>
        );
    }
}

export default ProductSizesApp;