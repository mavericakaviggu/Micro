import React, { Component } from 'react';
import EmployeeService from '../service/EmployeeService';

// The EmployeeComponent class is a React component that displays the details of an employee, department, and organization.
//Class-based react component that extents Component class from react package, meaning it can use lifecycle methods(componentDidMount, render)
class EmployeeComponent extends Component {

    constructor(props) {
        super(props);

        //The setState() method updated the state with the fetched data
        this.state = {
            employee: {},
            department: {},
            organization: {}
        }
    }    

    // The componentDidMount() method is called after the component is rendered.
    componentDidMount() {
        EmployeeService.getEmployee().then((res) => {
            this.setState({ employee: res.data.employeeDto, department: res.data.departmentDto, organization: res.data.organizationDto });
          
            console.log(this.state.employee);
            console.log(this.state.department);
            console.log(this.state.organization);
        });
    }

    //The render() method is required, and it is the method that actually outputs HTML to the DOM.
    //The render() method is called each time the component is updated.
    render() {
        return (
            <div> <br/><br/>
                <div className="container concard col-md-6 offset-md-3"> 
                    <h3 className="text-center card-header">View Employee Details</h3>
                    <div className="card-body">
                        <div className="row">
                            <p><strong>Employee First Name:</strong></p>
                            <div> {this.state.employee.firstName} </div>
                        </div>
                        <div className="row">
                            <p><strong>Employee Last Name:</strong> </p>
                            <div> {this.state.employee.lastName} </div>
                        </div>
                        <div className="row">
                            <p><strong>Employee Email:</strong></p>
                            <div> {this.state.employee.email} </div>
                        </div>
                    </div>  
                    <h3 className="text-center card-header">View Employee Details</h3>
                    <div className="card-body"> 
                        <div className="row">
                            <p><strong>Department Name:</strong></p>
                            <div> {this.state.department.departmentName} </div>
                        </div>
                        <div className="row">
                            <p><strong>Department Description:</strong></p>
                            <div> {this.state.department.departmentDescription} </div>
                        </div>
                        <div className="row">
                            <p><strong>Department Code:</strong></p>
                            <div> {this.state.department.departmentCode} </div>
                        </div>
                    </div>
                    <h3 className="text-center card-header">View Organization Details</h3>
                    <div className="card-body"> 
                        <div className="row">
                            <p><strong>Organization Name:</strong></p>
                            <div> {this.state.organization.organizationName} </div>
                        </div>
                        <div className="row">
                            <p><strong>Organization Description:</strong></p>
                            <div> {this.state.organization.organizationDescription} </div>
                        </div>
                        <div className="row">
                            <p><strong>Organization Code:</strong></p>
                            <div> {this.state.organization.organizationCode} </div>
                        </div>
                    </div>                
                </div>
            </div>
        );
    }
}

export default EmployeeComponent;