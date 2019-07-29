import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import ReactFileReader from 'react-file-reader';
const axios = require("axios");


class ProductsApp extends Component{
    constructor(props){
        super(props);
        this.state={
            pageNo:0,
            noOfPages:0,
            tableData:[],
            addStatus:false,
            updateStatus:false,
            updateId:0,
            deleteStatus:false,
            deleteId:0
        }

        this.retrieveData = this.retrieveData.bind(this);
        this.changePage = this.changePage.bind(this);
        this.genPagesContent = this.genPagesContent.bind(this);
        this.modalClose = this.modalClose.bind(this);
        this.statusChange = this.statusChange.bind(this);
        this.addProd = this.addProd.bind(this);
    }

    componentDidMount(){
        this.retrieveData(this.state.pageNo);
    }

    retrieveData(pageNo){
        const that = this;
        const count = axios.get("http://localhost:8080/Product/Pages")
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
                axios.get("http://localhost:8080/Product/"+pageNo)
                .then(function(res){
                    console.log(res.data)
                    that.setState({
                        tableData:res.data,
                        pageNo:pageNo
                    })
                    console.log("Product Data Received!");
                }).catch(function(error){
                    console.log("Product data error ",error);
                })
            }
        })
    }

    changePage(pageNo){
        this.setState({
            tableData:[]
        })
        if(this.state.mainCat){
            this.retrieveData(pageNo);
        }else{
            this.retrieveData(pageNo);
        }
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
              });
            }
        }
    }

    addProd(url,data,status){
        console.log("data ",data)
        const that = this;
        if(status==="add"){
            axios.post(url,data)
            .then(function(res){
                alert("Product Added Successfully!");
                console.log("Product Added Successfully!");
                that.setState({
                    addStatus:false
                })
                that.retrieveData(that.state.pageNo);
            })
        }else if(status==="delete"){
            axios.delete(url)
            .then(function(res){
                alert("Product Deleted Successfully!");
                console.log("Product Deleted Successfully!");
                that.setState({
                    deleteStatus:false,
                    deleteId:0,
                    tableData:[]
                })
                that.retrieveData(that.state.pageNo);
            })
        }
    }

    genPagesContent(){
        let pagesContent=[];
        if(this.state.noOfPages>10){
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
                        <PopupWindow windowStatus="add" modalClose={this.modalClose} addProd={this.addProd}/>
                    ):(
                        ""
                    )
                }
                {
                    this.state.deleteStatus?(
                        <PopupWindow windowStatus="delete" modalClose={this.modalClose} id={this.state.deleteId} addProd={this.addProd}/>
                    ):(
                        ""
                    )
                }
                <div className="w-100 container text-center category-menu">
                    <div className="row mt-2 text-center">
                        <h5 className="w-100 m-0">
                            Product Details
                        </h5>
                    </div>
                    <div className="row mb-1">
                        <button className="btn btn-sm addBtn" onClick={()=>this.statusChange("add",true)}>
                            Add Product
                        </button>                        
                    </div>
                    <div className="row">
                        <table className="table category-table ">
                            <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Title</th>
                                    <th>Description</th>
                                    <th>Price</th>
                                    <th>Sizes</th>
                                    <th>Image</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.tableData.map(data=>{
                                        return (
                                            <tr key={data.id}>
                                                <td>{data.id}</td>
                                                <td>{data.title}</td>
                                                <td>{data.description}</td>
                                                <td>{data.price}</td>
                                                <td>
                                                    <div>
                                                    {
                                                        data.productSizes.length!==0?(
                                                            data.productSizes.map((item,index)=>{
                                                                return(
                                                                    <div key={index}>
                                                                        <div>Size - {item.sizes.size}</div>
                                                                        <div>Available Qty - {item.quantity}</div>
                                                                    </div>
                                                                );
                                                            })
                                                        ):(<div>No Record!</div>)
                                                    }
                                                    </div>
                                                </td>
                                                <td>
                                                    {
                                                        data.productImages.length!==0?(
                                                            <img src={data.productImages[0].path} alt={data.id} className="category-img"/>
                                                        ):(<div>No Record!</div>)
                                                    }
                                                </td>
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

class PopupWindow extends Component{
    constructor(props){
        super(props);
        if(this.props.id===undefined){
            this.state={
                title:"",
                desc:"",
                price:"",
                mainCat:[],
                mainSelectedCat:0,
                subCat:[],
                subSelectedCat:0,
                mainSubCat:0,
                images:[],
                imagesName:[],
                sizes:[],
                selectedSize:"0",
                tempSizeQty:"0",
                selectedSizes:[],
                selectedSizesQty:[],
                windowStatus:this.props.windowStatus
            }
        }else if(this.props.id!==undefined && this.props.windowStatus==="delete"){
            this.state={
                id:this.props.id,
                windowStatus:this.props.windowStatus
            }
        }
        this.inputChnage = this.inputChnage.bind(this);
        this.inputPrice = this.inputPrice.bind(this);
        this.inputSizeQty = this.inputSizeQty.bind(this);

        this.addSize = this.addSize.bind(this);
        this.removeSize = this.removeSize.bind(this);

        this.uploadFiles = this.uploadFiles.bind(this);
        this.removeImage = this.removeImage.bind(this);


        this.addProd = this.addProd.bind(this);
        
        this.btnReset = this.btnReset.bind(this);
    }

    componentDidMount(){
        if(this.state.windowStatus==="add" || this.state.windowStatus==="update"){
            const that = this
            axios.get("http://localhost:8080/MainCategory")
            .then(function(res){
                that.setState({
                    mainCat:res.data
                })
            })

            axios.get("http://localhost:8080/Sizes/"+(-1))
            .then(function(res){
                that.setState({
                    sizes:res.data
                })
            })
        }
    }

    inputPrice(event){
        if(event.keyCode===69 || event.keyCode===187 || event.keyCode===189){
            event.preventDefault()
        }
    }

    inputSizeQty(event){
        console.log(event.keyCode)
        if(event.keyCode===69 || event.keyCode===187 || event.keyCode===189 || event.keyCode===190){
            event.preventDefault()
        }
    }

    uploadFiles(files){
        console.log(files)
        this.setState(state=>{
            const uploadFiles = files.base64;
            let imagesList = state.images;
            let imagesNameList = state.imagesName;
            for(var file in uploadFiles){
                imagesList.push(uploadFiles[file]);
                imagesNameList.push(files.fileList[file].name);
            }

            return{
                imagesList,
                imagesNameList
            }
        });
    }

    inputChnage(target){
        const name = target.name;
        const value = target.value;
        const that = this;
        switch(name){
            case "title":
                this.setState({
                    title:value
                })
                break;
            case "desc":
                this.setState({
                    desc:value
                })
                break;
            case "price":
                this.setState({
                    price:value
                })
                break;
            case "mainSelectedCat":
                console.log(value)
                this.setState({
                    mainSelectedCat:value
                })
                if(value!==""){
                    axios.get("http://localhost:8080/MainSubCategory/"+value)
                    .then(function(res){
                        that.setState({
                            subCat:res.data
                        })
                    })
                }
                break;
            case "subSelectedCat":
                this.setState({
                    subSelectedCat:value
                })
                break;
            case "selectedSize":
                this.setState({
                    selectedSize:value
                })
                break;
            case "tempSizeQty":
                console.log("temp qty",value)
                this.setState({
                    tempSizeQty:value
                })
                break;
            default:
                console.error("Product Size Input Change Error!");
        }
    }

    addSize(){
        const size = this.state.selectedSize;
        const sizeQty = this.state.tempSizeQty;
        if(size!=="0" && sizeQty!=="0"){

            this.setState(state=>{
                const sizesList = state.selectedSizes.push(size);
                const sizesQtyList = state.selectedSizesQty.push(sizeQty);

                return{
                    sizesList,
                    sizesQtyList
                }
            });
        }
    }

    removeSize(index){
        this.setState(state=>{
            const sizesList = state.selectedSizes.splice(index,1);
            const sizesQtyList = state.selectedSizesQty.splice(index,1);

            return{
                sizesList,
                sizesQtyList
            }
        });
    }

    removeImage(index){
        this.setState(state=>{
            const imagesList = state.images.splice(index,1);
            const imagesNameList = state.imagesName.splice(index,1);

            return{
                imagesList,
                imagesNameList
            }
        });
    }

    addProd(){
        let url;
        let data;
        if(this.state.windowStatus==="add"){
            url =  "http://localhost:8080/AddProduct";

            let prodImages=[];
            let prodSizes=[];
            for(var i=0;i<this.state.images.length;i++){
                prodImages.push({path:this.state.images[i]});
            }
            for(i=0;i<this.state.selectedSizes.length;i++){
                prodSizes.push(
                    {
                        sizes:{size:this.state.selectedSizes[i]},
                        quantity:this.state.selectedSizesQty[i]
                    }
                )
            }
            data = {
                title:this.state.title,
                description:this.state.desc,
                price:this.state.price,
                mainSubCategory:{
                    mainCategory:{id:this.state.mainSelectedCat},
                    subCategory:{id:this.state.subSelectedCat}
                },
                productImages:prodImages,
                productSizes:prodSizes
                
            }
        }else if(this.state.windowStatus==="update"){
            url =  "http://localhost:8080/UpdateSizes/"+this.state.id;
            data = {
                size:this.state.size,
                description:this.state.desc
            }
        }else if(this.state.windowStatus==="delete"){
            url =  "http://localhost:8080/DeleteProduct/"+this.state.id;
        }
        this.props.addProd(url,data,this.state.windowStatus);
    }

    btnReset(){
        this.setState({
            title:"",
            desc:"",
            price:"",
            mainSelectedCat:0,
            subSelectedCat:0,
            mainSubCat:0,
            images:[],
            imagesName:[],
            selectedSize:"0",
            tempSizeQty:"0",
            selectedSizes:[],
            selectedSizesQty:[]
        })
    }

    render(){
        let header;
        let footer;
        if(this.state.windowStatus==="add"){
            header = "Add Product";
            footer = "Add";
        }else if(this.state.windowStatus==="delete"){
            header = "Delete Product";
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
                                    <h6>Title</h6>
                                    <input className="w-100" type="text" name="title" value={this.state.title} onChange={(event)=>this.inputChnage(event.target)}/>
                                    <h6>Description</h6>
                                    <textarea name="desc" className="w-100" value={this.state.desc} onChange={(event)=>this.inputChnage(event.target)}></textarea>
                                    <h6>Price</h6>
                                    <input className="w-100" type="number" name="price" value={this.state.price} onKeyDown={(event)=>this.inputPrice(event)} 
                                    onChange={(event)=>this.inputChnage(event.target)}/>
                                    <h6>Main category</h6>
                                    <select className="w-100" name="mainSelectedCat" value={this.state.mainSelectedCat} onChange={(event)=>this.inputChnage(event.target)}>
                                        <option value="0"></option>
                                        {
                                            this.state.mainCat.map(item=>{
                                                return <option value={item.id} key={item.id}>{item.mainCatTitle}</option>
                                            })
                                        }
                                    </select>
                                    {
                                        this.state.mainSelectedCat.toString()!=="0"?(
                                            <div>
                                                <h6>Sub category</h6>
                                                <select className="w-100" name="subSelectedCat" value={this.state.subSelectedCat} onChange={(event)=>this.inputChnage(event.target)}>
                                                    <option value="0"></option>
                                                    {
                                                        this.state.subCat.map(item=>{
                                                            return <option value={item.id} key={item.id}>{item.subCatTitle}</option>
                                                        })
                                                    }
                                                </select>
                                            </div>
                                        ):("")
                                    }
                                    <h6>Images</h6>
                                    <div className="card w-100 my-2 p-2">
                                        <div className="row" style={{margin:"0px"}}>
                                            <input type="text" editable="false" name="prod-img" className="col-lg card" value={this.state.mainImageName} onChange={(event)=>event.preventDefault()}/>
                                            <ReactFileReader fileTypes={[".png",".jpeg",".jpg"]} base64={true} multipleFiles={true} handleFiles={this.uploadFiles}>
                                                <button className='btn btn-primary'>Upload</button>
                                            </ReactFileReader>
                                        </div>
                                        <hr></hr>
                                        <div className="row ml-1">
                                            {
                                                this.state.images.map((item,index)=>{
                                                    return(
                                                        <div key={item} className="column text-center m-1">
                                                            <img  src={item} alt={this.state.imagesName[index]} className="category-img"/><br></br>
                                                            <button className="btn-sm btn-danger my-1" onClick={()=>this.removeImage(index)}>Remove</button>
                                                        </div>
                                                    );
                                                })
                                            }
                                        </div>
                                    </div>
                                    <h6>Product Sizes</h6>
                                    <div className="card w-100 my-2 p-2">
                                        <h6>Sizes</h6>
                                        <select className="w-100" name="selectedSize" value={this.state.selectedSize} onChange={(event)=>this.inputChnage(event.target)}>
                                            <option value="0"></option>
                                            {
                                                this.state.sizes.map(item=>{
                                                    return <option value={item.size} key={item.id}>{item.size}</option>
                                                })
                                            }
                                        </select>
                                        <h6>Quantity</h6>
                                        <input className="w-100" type="number" name="tempSizeQty" value={this.state.tempSizeQty} onKeyDown={(event)=>this.inputSizeQty(event)} 
                                        onChange={(event)=>this.inputChnage(event.target)}/>
                                        <button className="btn btn-success w-100 my-1" onClick={this.addSize} >Add</button>
                                        <hr/>
                                        <ul>
                                            {
                                                
                                                this.state.selectedSizes!==undefined && this.state.selectedSizes.length!==0?(
                                                    this.state.selectedSizes.map((item,index)=>{
                                                        return(
                                                            <li key={item} >
                                                                <div>Size : {item}</div>
                                                                <div>Qty : {this.state.selectedSizesQty[index]}</div>
                                                                <button className="btn-sm btn-danger" onClick={()=>this.removeSize(index)}>Remove</button>
                                                            </li>
                                                        );
                                                    })
                                                    
                                                ):(<li>No Record</li>)
                                            }
                                        </ul>
                                    </div>
                                    
                                </div>
                            )
                        }
                        
                    </div>
                    <div className="modal-footer">
                        <button className="btn btn-success"  onClick={this.addProd}>
                            {footer}
                        </button>
                        {
                            this.state.windowStatus!=="delete"?(
                                <button className="btn btn-danger" onClick={this.btnReset}>Reset</button>
                            ):("")
                        }
                        
                    </div>
                </div>
            </div>
        );
    }
}

export default ProductsApp;