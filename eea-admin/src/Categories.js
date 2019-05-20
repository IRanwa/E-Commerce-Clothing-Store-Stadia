import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

class CategoriesApp extends Component{
    render(){
        return(
            <div className="main_container font_AgencyFB">
                <AddNew/> 
                <div>
                    <h5 className="font_weight_bold float-left">Categories</h5>
                    <input className="btn main_color text-white button_hover container_top_btn" type="button" value="Add New"/>
                    <table className="text-center text-white main_color maintable float-left">
                        <thead>
                        
                        <tr>
                            <td className="border-bottomright-line bg-white text-center font_Britannic button_hover selected_col_left ">
                                <a href="/register" className="greycolor_text regColumn">Main Category</a>
                            </td>
                            <td className="border-bottomright-line   text-center font_Britannic button_hover not_selected_col_right">
                                <a href="/register" className="text-white regColumn">Sub Category</a>
                            </td>
                        </tr>
                        </thead>
                        <tbody>
                        
                        <tr>
                           <td colSpan="2">
                                <div className="card main_lightcolor_bg text-left filter">
                                    <div className="filter_container">
                                        <p className="float-left "> Availability</p>
                                        <select className="card">
                                            <option value="default">All</option>>
                                            <option value="Hide">Hide</option>>
                                            <option value="Show">Show</option>>
                                        </select>
                                    </div>
                                    <div className="filter_container float-right">
                                        <p className="float-left">Search Keyword</p>
                                        <input className="card" type="text" placeholder="search category"/>
                                    </div>
                                </div>
                            </td> 
                        </tr>
                        </tbody>
                    </table>
                    <table className="subtable">
                    <thead>
                        <tr>
                            <th>
                                <p className="m-0">All</p>
                                <input className="" type="checkbox" value="All" placeholder="All"/>
                            </th>
                            <th>Title</th>
                            <th>Description</th>
                            <th>Gender</th>
                            <th>Image</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td></td>
                            <td>1</td>
                            <td>1</td>
                            <td>1</td>
                            <td><img src="https://image.flaticon.com/icons/png/512/107/107831.png" alt="message"/></td>
                            <td>1</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}

class AddNew extends Component{
    constructor(props){
        super(props);
        this.state={
            newMainCat:true,
            newSubCat:true
        };
        this.mainCategoryRadioBtn = this.mainCategoryRadioBtn.bind(this);
        this.subCategoryRadioBtn = this.subCategoryRadioBtn.bind(this);
        this.getMainFormView = this.getMainFormView.bind(this);
        this.getSubFormView = this.getSubFormView.bind(this);
    }

    getMainFormView(){
        let mainCatForm;
        //Main Category Form
        if(this.state.newMainCat){
            mainCatForm = (
            <div className="mx-2 my-2 ">
                <p className="m-1">Title</p>
                <input type="text" className="w-100 card m-0"/>
                <p className="m-1">Description</p>
                <textarea className="w-100 m-0 card"></textarea>
                <p className="m-1">Upload Image</p>
                <input type="file" className="w-100 card m-0"/>
                <p className="m-1">Gender</p>
                <select className="card w-100 m-0">
                    <option value="Male">Male</option>
                    <option value="Female">Female</option>
                </select>
            </div>
            )
        }else{
            mainCatForm = (
                <div>
                    <p className="m-1">Category</p>
                    <select className="card w-100 m-0">
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                    </select>
                </div>
            )
        }
        return mainCatForm;
    }

    getSubFormView(){
        let subCatForm;
        //Sub category Form
        if(this.state.newSubCat){
            subCatForm = (
                <div>
                    <p className="m-1">Title</p>
                    <input type="text" className="w-100 card m-0"/>
                    <p className="m-1">Description</p>
                    <textarea className="w-100 m-0 card"></textarea>
                    <p className="m-1">Upload Image</p>
                    <input type="file" className="w-100 card m-0"/>
                </div>
            )
        }else{
            subCatForm = (
                <div></div>
            )
        }
        return subCatForm;
    }

    mainCategoryRadioBtn(event){
        if(event.target.value==="newMainCat"){
            this.setState({newMainCat:true});
        }else{
            this.setState({newMainCat:false});
        }
        //this.setFormView(event);
    }

    subCategoryRadioBtn(event){
        if(event.target.value==="newSubCat"){
            this.setState({newSubCat:true});
        }else{
            this.setState({newSubCat:false});
        }
        //this.setFormView(event);
    }

    render(){
        const mainCatForm = this.getMainFormView();
        const subCatForm = this.getSubFormView();
        return(
            <div className="modal  font_AgencyFB" >
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                           <h4>New category</h4>
                        </div>
                        <div className="modal-body card m-2">
                            <input type="radio" name="mainCatRadio" value="newMainCat" onClick={(event)=>this.mainCategoryRadioBtn(event)} 
                                defaultChecked={this.state.newMainCat?"Checked":""} />New Main Category<br></br>
                            
                            <input type="radio" name="mainCatRadio" value="existingMainCat" onClick={(event)=>this.mainCategoryRadioBtn(event)} 
                            defaultChecked={this.state.newMainCat?"":"Checked"}/>Existing Main Category
                            <hr className="m-2"></hr>
                            {mainCatForm}
                        </div>
                        <div className="modal-body card m-2">
                            <input type="radio" name="subCatRadio" value="newSubCat" onClick={(event)=>this.subCategoryRadioBtn(event)} 
                                defaultChecked={this.state.newSubCat?"Checked":""} />New Sub Category<br></br>
                            
                            <input type="radio" name="subCatRadio" value="existingSubCat" onClick={(event)=>this.subCategoryRadioBtn(event)} 
                            defaultChecked={this.state.newSubCat?"":"Checked"}/>Existing Sub Category
                            {subCatForm}
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-primary">Save changes</button>
                            <button type="button" className="btn btn-secondary" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                 </div>
            </div>
        );
    }
}

export default CategoriesApp;