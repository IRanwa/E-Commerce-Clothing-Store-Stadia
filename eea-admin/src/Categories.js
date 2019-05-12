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
                        <tr>
                            <td className="border-bottomright-line bg-white text-center font_Britannic button_hover selected_col_left ">
                                <a href="/register" className="greycolor_text regColumn">Main Category</a>
                            </td>
                            <td className="border-bottomright-line   text-center font_Britannic button_hover not_selected_col_right">
                                <a href="/register" className="text-white regColumn">Sub Category</a>
                            </td>
                        </tr>
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
                    </table>
                    <table className="subtable">
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
                        <tr>
                            <td></td>
                            <td>1</td>
                            <td>1</td>
                            <td>1</td>
                            <td><img src="https://image.flaticon.com/icons/png/512/107/107831.png" alt="message"/></td>
                            <td>1</td>
                        </tr>
                    </table>
                </div>
            </div>
        );
    }
}

class AddNew extends Component{
    render(){
        return(
            <div class="modal font_AgencyFB" >
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                           <h4>New category</h4>
                        </div>
                        <div class="modal-body card m-2">
                            <input type="radio" />New Main Category
                            <div className="card p-2">
                                <div>
                                    <p>Name</p>
                                    <input type="text"/>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary">Save changes</button>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                 </div>
            </div>
        );
    }
}

export default CategoriesApp;