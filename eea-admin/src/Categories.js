import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import ReactFileReader from 'react-file-reader';
const axios = require("axios");

class CategoriesApp extends Component{
    constructor(props){
        super(props);
        this.state={
            mainCat:true,
            addCat:false,
            tableData:[],
            pageNo:0,
            noOfPages:0
        }
        this.changeCategory = this.changeCategory.bind(this);
        this.addCatOnClick = this.addCatOnClick.bind(this);
        this.modalClose = this.modalClose.bind(this);
        this.addSubCategory = this.addSubCategory.bind(this);
        this.addMainCategory = this.addMainCategory.bind(this);
        this.retrieveMainCat = this.retrieveMainCat.bind(this);
        this.retrieveSubCat = this.retrieveSubCat.bind(this);
        this.changePage = this.changePage.bind(this);
    }

    componentDidMount(){
        this.retrieveMainCat(this.state.pageNo);
    }

    changeCategory(status){
        this.setState({
            mainCat:status,
            addCat:false,
            noOfPages:0,
            pageNo:0,
            tableData:[],
        })
        if(status){
            this.retrieveMainCat(0);
        }else{
            this.retrieveSubCat(0);
        }
    }

    changePage(pageNo){
        this.setState({
            tableData:[]
        })
        if(this.state.mainCat){
            this.retrieveMainCat(pageNo);
        }else{
            this.retrieveSubCat(pageNo);
        }
    }

    retrieveMainCat(pageNo){
        const that = this;
        const count = axios.get("http://localhost:8080/MainCategory/Pages")
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
                axios.get("http://localhost:8080/MainCategory/"+pageNo)
                .then(function(res){
                    console.log(res.data);
                    that.setState({
                        tableData:res.data,
                        pageNo:pageNo
                    })
                    console.log("Main Category Data Received!");
                }).catch(function(error){
                    console.log("Main Category data error ",error);
                })
            }
        })
    }

    retrieveSubCat(pageNo){
        const that = this;
        const count = axios.get("http://localhost:8080/SubCategory/Pages")
        .then(function(res){
            if(res.data>0){
                that.setState({
                    noOfPages:res.data
                })
                console.log("sub no of pages ",res.data)
                return res.data;
            }
        }).catch(function(error){
            console.log("sub pages error ",error)
        })

        count.then(data=>{
            if(data>0){
                axios.get("http://localhost:8080/SubCategory/"+pageNo)
                .then(function(res){
                    console.log("sub cat data ",res.data);
                    that.setState({
                        tableData:res.data,
                        pageNo:pageNo
                    })
                    console.log("Sub Category Data Received!");
                }).catch(function(error){
                    console.log("Sub category data error ",error);
                })
            }
        })
    }

    addCatOnClick(){
        this.setState({
            addCat:!this.state.addCat
        })
    }

    modalClose(){
        const that = this;
        const modal = document.getElementsByClassName("modal")[0];
        window.onclick = function(event) {
            if (event.target === modal) {
              that.addCatOnClick();
            }
        }
    }

    addSubCategory(url,data){
        const that = this;
        if(this.state.addCat){
            axios.post(url,data)
            .then(function(res){
               console.log("Sub Category Added Successfully!");
               alert("Category Added Successfully!");
               that.setState({
                   addCat:false
               })
               that.changeCategory(false);
            }).catch(function(error){
                console.log(error);
                console.log("Sub Category Adding Un-Successful!");
               alert("Category Adding Un-Successfully!");
            });
        }
    }

    addMainCategory(url,data){
        const that = this;
        if(this.state.mainCat && this.state.addCat){
            return axios.post(url,data)
            .then(function(res){
               console.log("Main Category Added Successfully!");
               //alert("Category Added Successfully!");
               alert("Category Added Successfully!");
               that.setState({
                   addCat:false
               })
               that.changeCategory(true);
            }).catch(function(error){
               console.log(error);
               console.log("Main Category Adding Un-Successful!");
               alert("Category Adding Un-Successfully!");
            });
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
                console.log("count ",this.state.pageNo);
                console.log(i)
                pagesContent.push(<div className={this.state.pageNo===i?"paging-selected col-sm":"paging-not-selected col-sm"} key={i} onClick={()=>this.changePage(i)}>{i+1}</div>);
            }
        }
        console.log(pagesContent)
        return(
            <div className="card w-90 p-3 m-5">
                {
                    this.state.addCat?(
                        this.state.mainCat?(
                            <AddMainCategory modalClose={this.modalClose} addSubCategory={this.addSubCategory} addMainCategory={this.addMainCategory}/>
                        ):(
                            <AddSubCategory modalClose={this.modalClose} addSubCategory={this.addSubCategory} />
                        )
                    ):("")
                }
                <div className="w-100 container text-center category-menu">
                    <div className="row">
                        <a className={this.state.mainCat?("col-sm category-menu-item-active"):("col-sm category-menu-item-deactive")} href="/" 
                        onClick={(e)=>{e.preventDefault(); this.changeCategory(true)}} >Main Category</a>
                        <a className={this.state.mainCat?("col-sm category-menu-item-deactive"):("col-sm category-menu-item-active")} href="/" 
                        onClick={(e)=>{e.preventDefault(); this.changeCategory(false)}}>Sub category</a>
                    </div>
                    <div className="row mt-2 text-center">
                        <h4 className="w-100 m-0">
                            {
                                this.state.mainCat?("Main Category Details"):("Sub category Details")
                            }
                        </h4>
                    </div>
                    <div className="row mb-1">
                        <button className="btn btn-sm addBtn" onClick={this.addCatOnClick}>
                            {
                                this.state.mainCat?("Add Main Category"):("Add Sub Category")
                            }
                        </button>                        
                    </div>
                    <div className="row">
                        <table className="table category-table ">
                            <thead>
                                {
                                    this.state.mainCat?(
                                        <tr>
                                            <th>Id</th>
                                            <th>Title</th>
                                            <th>Description</th>
                                            <th>Image</th>
                                            <th>Gender Type</th>
                                            <th></th>
                                        </tr>
                                    ):(
                                        <tr>
                                            <th>Id</th>
                                            <th>Title</th>
                                            <th>Description</th>
                                            <th>Image</th>
                                            <th></th>
                                        </tr>
                                    )
                                }
                                
                            </thead>
                            <tbody>
                                {
                                    this.state.mainCat?(
                                        this.state.tableData.map(data=>{
                                            return (
                                                <tr key={data.id}>
                                                    <td>{data.id}</td>
                                                    <td>{data.mainCatTitle}</td>
                                                    <td>{data.mainCatDesc}</td>
                                                    <td><img src={data.mainCatImg} alt={data.id} className="category-img"/></td>
                                                    <td>{data.type}</td>
                                                    <td>
                                                        <button className="btn btn-sm btn-update w-50 my-1 mr-1">Update</button>
                                                        <button className="btn btn-sm btn-delete w-50 my-1 mr-1">Delete</button>
                                                    </td>
                                                </tr>
                                            );
                                        })
                                    ):(
                                        this.state.tableData.map(data=>{
                                            return (
                                                <tr key={data.id}>
                                                    <td>{data.id}</td>
                                                    <td>{data.subCatTitle}</td>
                                                    <td>{data.subCatDesc}</td>
                                                    <td><img src={data.subCatImg} alt={data.id} className="category-img"/></td>
                                                    <td>
                                                        <button className="btn btn-sm btn-update w-50 my-1 mr-1">Update</button>
                                                        <button className="btn btn-sm btn-delete w-50 my-1 mr-1">Delete</button>
                                                    </td>
                                                </tr>
                                            );
                                        })
                                    )
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

class AddMainCategory extends Component{
    constructor(props){
        super(props);
        this.state={
            newMainCat:true,
            newSubCat:true,
            addMainCatData:[],
            addSubCatData:[],
            mainId:"",
            mainTitle:"",
            mainDesc:"",
            mainImage:"",
            mainType:"",
            subId:"",
            subTitle:"",
            subDesc:"",
            subImage:"",
            mainImageName:"",
            subImageName:""
        }
        this.changeMainCat = this.changeMainCat.bind(this);
        this.changeSubCat = this.changeSubCat.bind(this);
        this.inputChnage = this.inputChnage.bind(this);
        this.btnReset = this.btnReset.bind(this);
        this.addCategory = this.addCategory.bind(this);
        this.mainCatFileUpload = this.mainCatFileUpload.bind(this);
        this.subCatFileUpload = this.subCatFileUpload.bind(this);
    }

    changeSubCat(value){
        const that = this;
        if(value==="New-Cat"){
            this.setState({
                newSubCat:true,
                addSubCatData:[],
                subId:""
            })
        }else{
            this.setState({
                newSubCat:false
            })
            
            axios.get("http://localhost:8080/SubCategory/-1")
            .then(function(res){
                that.setState({
                    addSubCatData:res.data
                })
                if(res.data.length>0){
                    that.setState({
                        subId:res.data[0].id
                    })
                }
            }).catch(function(error){
                console.log("Sub Category Data Retrieve Error!");
            });

            
        }
    }

    changeMainCat(value){
        const that = this;
        if(value==="New-Cat"){
            this.setState({
                newMainCat:true,
                addMainCatData:[],
                mainId:""
            })
        }else{
            this.setState({
                newMainCat:false
            })

            axios.get("http://localhost:8080/MainCategory")
            .then(function(res){
                that.setState({
                    addMainCatData:res.data
                })
                if(res.data.length>0){
                    that.setState({
                        mainId:res.data[0].id
                    })
                }
            }).catch(function(error){
                console.log("Main Category Data Retrieve Error!");
            });
        }

    }

    inputChnage(target){
        const name = target.name;
        const value = target.value;
        if(name==="main-title"){
            this.setState({
                mainTitle:value
            })
        }else if(name==="main-desc"){
            this.setState({
                mainDesc:value
            })
        }else if(name==="main-img"){
            this.setState({
                mainImage:target.files[0]
            })
        }else if(name==="main-type"){
            this.setState({
                mainType:value
            })
        }else if(name==="main-id"){
            this.setState({
                mainId:value
            })
        }else if(name==="sub-title"){
            this.setState({
                subTitle:value
            })
        }else if(name==="sub-desc"){
            this.setState({
                subDesc:value
            })
        }else if(name==="sub-img"){
            this.setState({
                subImage:target.files[0]
            })
        }else if(name==="sub-id"){
            this.setState({
                subId:value
            })
        }
    }

    btnReset(){
        this.setState({
            newMainCat:true,
            newSubCat:true,
            addMainCatData:[],
            addSubCatData:[],
            mainId:"",
            mainTitle:"",
            mainDesc:"",
            mainImage:"",
            mainType:"",
            subId:"",
            subTitle:"",
            subDesc:"",
            subImage:"",
            mainImageName:"",
            subImageName:""
        });
    }

    mainCatFileUpload(files){
        this.setState({
            mainImage:files.base64,
            mainImageName:files.fileList[0].name
        })
    }

    subCatFileUpload(files){
        this.setState({
            subImage:files.base64,
            subImageName:files.fileList[0].name
        })
    }

    addCategory(){
        const mainUrl =  "http://localhost:8080/AddMainCategory";
        let mainData ;
        if(this.state.mainId===""){
            mainData = {
                id:this.state.mainId,
                mainCatTitle:this.state.mainTitle,
                mainCatDesc:this.state.mainDesc,
                type:this.state.mainType,
                mainCatImg:this.state.mainImage,
                subCategory:[
                    {
                        id:this.state.subId,
                        subCatTitle:this.state.subTitle,
                        subCatDesc:this.state.subDesc,
                        subCatImg:this.state.subImage,
                    }
                ]
            }
        }else{
            mainData = {
                id:this.state.mainId,
                subCategory:[
                    {
                        id:this.state.subId,
                        subCatTitle:this.state.subTitle,
                        subCatDesc:this.state.subDesc,
                        subCatImg:this.state.subImage,
                    }
                ]
            }
        }
        
        console.log(mainData);

        this.props.addMainCategory(mainUrl,mainData);
    }

    render(){
        return(
            <div className="modal" onClick={this.props.modalClose}>
                <div className="card modal-content  add-cat-popup">
                    <div className="modal-header">
                        <h5>Add Main Category</h5>
                    </div>
                    <div className="modal-body add-cat-popup-body">
                        <div className="card w-100 p-2">
                            <input type="radio" name="maincat" value="New-Cat" checked={this.state.newMainCat?(true):("")} onChange={(event)=>this.changeMainCat(event.target.value)}/>New Main Category <br></br>
                            <input type="radio" name="maincat" value="Existing-Cat" checked={this.state.newMainCat?(""):(true)} onChange={(event)=>this.changeMainCat(event.target.value)}/>Existing Main Category
                        </div>
                        <div className="card w-100 my-2 p-2">
                        {
                            this.state.newMainCat?(
                                <div className="add-cat-form">
                                    <h6>Title</h6>
                                    <input className="w-100" type="text" name="main-title" value={this.state.mainTitle} onChange={(event)=>this.inputChnage(event.target)}/>
                                    <h6>Description</h6>
                                    <textarea name="main-desc" className="w-100" value={this.state.mainDesc} onChange={(event)=>this.inputChnage(event.target)}></textarea>
                                    <h6>Image</h6>
                                    <div className="">
                                        <div className="row" style={{margin:"0px"}}>
                                            <input type="text" editable="false" name="main-img" className="col-lg card" value={this.state.mainImageName} onChange={(event)=>event.preventDefault()}/>
                                            <ReactFileReader fileTypes={[".png",".jpeg",".jpg"]} base64={true} multipleFiles={false} handleFiles={this.mainCatFileUpload}>
                                                <button className='btn btn-primary'>Upload</button>
                                            </ReactFileReader>
                                        </div>
                                    </div>
                                    <h6>Type</h6>
                                    <select name="main-type" className="w-100" onChange={(event)=>this.inputChnage(event.target)} value={this.state.mainType}>
                                        <option value=""></option>
                                        <option value="M">Male</option>
                                        <option value="F">Female</option>
                                    </select>
                                </div>
                            ):(
                                <div className="add-cat-form">
                                    <h6>Main Categories</h6>
                                    <select name="main-id" className="w-100" onChange={(event)=>this.inputChnage(event.target)} value={this.state.mainId}>
                                        {
                                            this.state.addMainCatData.map(item=>{
                                                return <option value={item.id} key={item.id}>{item.mainCatTitle}</option>
                                            })
                                        }
                                    </select>
                                </div>
                            )
                        }
                        </div>
                    </div>

                    <SubcategoryBody inputChnage={this.inputChnage} subTitle={this.state.subTitle} subDesc={this.state.subDesc} subImage={this.state.subImage}
                     subImageName={this.state.subImageName}  newSubCat={this.state.newSubCat} 
                     changeSubCat={this.changeSubCat} fileUpload={this.subCatFileUpload} addSubCatData={this.state.addSubCatData} subId={this.state.subId}/>
                    
                    
                    <div className="modal-footer">
                        <button className="btn btn-success"  onClick={this.addCategory}>Add Category</button>
                        <button className="btn btn-danger" onClick={this.btnReset}>Reset</button>
                    </div>
                </div>
            </div>
        );
    }
}

class AddSubCategory extends Component{
    constructor(props){
        super(props);
        this.state={
            addMainCatData:[],
            mainId:"",
            subTitle:"",
            subDesc:"",
            subImage:"",
            subImageName:""
        }
        this.inputChnage = this.inputChnage.bind(this);
        this.fileUpload = this.fileUpload.bind(this);
        this.btnReset = this.btnReset.bind(this);
        this.addCategory = this.addCategory.bind(this);
    }

    componentDidMount(){
        const that = this;
        axios.get("http://localhost:8080/MainCategory")
        .then(function(res){
            that.setState({
                addMainCatData:res.data
            })
            if(res.data.length>0){
                that.setState({
                    mainId:res.data[0].id
                })
            }
        }).catch(function(error){
            console.log("Main Category Data Retrieve Error!");
        });
    }

    inputChnage(target){
        const name = target.name;
        const value = target.value;
        if(name==="sub-title"){
            this.setState({
                subTitle:value
            })
        }else if(name==="sub-desc"){
            this.setState({
                subDesc:value
            })
        }else if(name==="main-id"){
            this.setState({
                mainId:value
            })
        }
    }

    fileUpload(files){
        this.setState({
            subImage:files.base64,
            subImageName:files.fileList[0].name
        })
    }

    btnReset(){
        this.setState({
            subTitle:"",
            subDesc:"",
            subImage:"",
            subImageName:"",
            mainId:"",
            addMainCatData:[]
        })
    }

    addCategory(){
        const url = "http://localhost:8080/AddSubCategory/"+this.state.mainId;
        const data = {
            subCatTitle:this.state.subTitle,
            subCatDesc:this.state.subDesc,
            subCatImg:this.state.subImage,
        }

       this.props.addSubCategory(url,data);
        
    }

    render(){
        return(
            <div className="modal" onClick={this.props.modalClose}>
                <div className="card modal-content  add-cat-popup">
                    <div>
                    `    <div className="modal-header">
                            <h5>Add Main Category</h5>
                        </div>
                        <div className="modal-body add-cat-popup-body">
                            <div className="card w-100 my-2 p-2 add-cat-form">
                                <h6>Main Categories</h6>
                                <select name="main-id" className="w-100" onChange={(event)=>this.inputChnage(event.target)} value={this.state.mainId}>
                                    {
                                        this.state.addMainCatData.map(item=>{
                                            return <option value={item.id} key={item.id}>{item.mainCatTitle}</option>
                                        })
                                    }
                                </select>
                            </div>
                        </div>
                    </div>
                    <SubcategoryBody inputChnage={this.inputChnage} subTitle={this.state.subTitle} subDesc={this.state.subDesc} subImage={this.state.subImage} 
                    subImageName={this.state.subImageName} 
                    changeSubCat={this.changeSubCat} fileUpload={this.fileUpload}/>

                    <div className="modal-footer">
                        <button className="btn btn-success"  onClick={this.addCategory}>Add Category</button>
                        <button className="btn btn-danger" onClick={this.btnReset}>Reset</button>
                    </div>
                </div>
            </div>
        );
    }
}

class SubcategoryBody extends Component{
    constructor(props){
        super(props);
        if(this.props.inputChnage!==undefined){
            this.subCatInputChnage = this.props.inputChnage;
            this.subCatFileUpload = this.props.fileUpload;
            this.changeSubCat = this.props.changeSubCat;
        }
    }

    render(){
        console.log("add sub cat ",this.props.addSubCatData)
        return(
            <div>
                <div className="modal-header">
                    <h5>Add Sub Category</h5>
                </div>
                <div className="modal-body add-cat-popup-body">
                    {
                        this.props.newSubCat!==undefined?(
                            <div className="card w-100 p-2">
                                <input type="radio" name="subcat" id="subcat-main" value="New-Cat" checked={this.props.newSubCat?(true):("")} onChange={(event)=>this.changeSubCat(event.target.value)}/>New Sub Category <br></br>
                                <input type="radio" name="subcat" value="Existing-Cat" checked={this.props.newSubCat?(""):(true)} onChange={(event)=>this.changeSubCat(event.target.value)}/>Existing Sub Category
                            </div>
                        ):(
                            ""
                        )
                    }
                    <div className="card w-100 my-2 p-2">
                        {
                            this.props.addSubCatData!==undefined && this.props.addSubCatData.length>0?(
                                <div className="add-cat-form">
                                    <h6>Sub Categories</h6>
                                    <select name="sub-id" className="w-100" onChange={(event)=>this.subCatInputChnage(event.target)} value={this.props.subId}>
                                        {
                                            this.props.addSubCatData.map(item=>{
                                                return <option value={item.id} key={item.id}>{item.subCatTitle}</option>
                                            })
                                        }
                                    </select>
                                </div>
                            ):(
                                <div className="add-cat-form">
                                    <h6>Title</h6>
                                    <input className="w-100" type="text" name="sub-title" value={this.props.subTitle} onChange={(event)=>this.subCatInputChnage(event.target)}/>
                                    <h6>Description</h6>
                                    <textarea name="sub-desc" className="w-100" value={this.props.subDesc} onChange={(event)=>this.subCatInputChnage(event.target)}></textarea>
                                    <h6>Image</h6>
                                    <div className="">
                                        <div className="row" style={{margin:"0px"}}>
                                            <input type="text" editable="false" name="sub-img" className="col-lg card" value={this.props.subImageName} onChange={(event)=>event.preventDefault()}/>
                                            <ReactFileReader fileTypes={[".png",".jpeg",".jpg"]} base64={true} multipleFiles={false} handleFiles={this.subCatFileUpload}>
                                                <button className='btn btn-primary'>Upload</button>
                                            </ReactFileReader>
                                        </div>
                                    </div>
                                </div>
                            )
                        }
                        
                    </div>
                </div>
            </div>
        );
    }
}

export default CategoriesApp;