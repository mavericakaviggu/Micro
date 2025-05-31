import React, { useEffect, useState } from 'react'
import EmployeeService from '../service/EmployeeService';
import DepartmentService from '../service/DepartmentService';
import OrganizationService from '../service/OrganizationService';
import { useNavigate, useParams } from 'react-router-dom'

function AddEmployeeComponent() {

  const [firstName,setFirstName] = useState('')
  const [lastName,setLastName] = useState('')
  const [email,setEmail] = useState('') 
  const [departmentCode,setDepartmentCode] = useState('')
  const [organizationCode,setOrganizationCode] = useState('')
  const [departments, setDepartments] = useState([]);
  const [organizations, setOrganizations] = useState([]);

  const [errors,setErrors] = useState({
    firstName: '',
    lastName:'',
    email: ''
  })

  //to get the id from the URL
  //useParams is a hook that returns an object of key/value pairs of URL parameters
  const {id} = useParams();

  const navigate = useNavigate();

  useEffect(() => {
    // Fetch department list
    DepartmentService.getDepartments()
    .then((response) => {
      setDepartments(response.data);
    })
    .catch((error) => {
      console.error("Error fetching departments", error);
    });

    // Fetch organization list
    OrganizationService.getOrganizations()
    .then((response) => {
      setOrganizations(response.data);
    })
    .catch((error) => {
      console.error("Error fetching organizations", error);
    });

    if(id){
      EmployeeService.getEmployee(id).then((response) => {
        setFirstName(response.data.employeeDto.firstName)
        setLastName(response.data.employeeDto.lastName)
        setEmail(response.data.employeeDto.email)
        setDepartmentCode(response.data.employeeDto.departmentCode)
        setOrganizationCode(response.data.employeeDto.organizationCode)
      }).catch(error => {
        console.log(error)
      })
    }
  },[id])

  function saveOrUpdateEmployee(e){
    e.preventDefault()
    if(validateForm()){
      const employee = {firstName,lastName,email,departmentCode,organizationCode}
  
      if(id){
        //update employee
        EmployeeService.updateEmployee(id, employee).then((response) => {
          console.log(response.data);
          navigate('/employees');
      }).catch(error => {
        console.error(error)
      })
      }else{
      //create employee      
        EmployeeService.createEmployee(employee).then((response) => {
        console.log(response.data);
        navigate('/employees');
        }).catch(error => {
        console.error(error)
        }  )  
      }
}}

  function validateForm(){
    let valid = true;
    const errorsCopy = {...errors};
    if(firstName.trim() === ''){
      errorsCopy.firstName = 'First Name is required';
      valid = false;
    }
    if(lastName.trim() === ''){
      errorsCopy.lastName = 'Last Name is required';
      valid = false;
    }
    if(email.trim() === ''){
      errorsCopy.email = 'Email is required';
      valid = false;
  }
    setErrors(errorsCopy);
    return valid;

  }

  function pageTitle(){
    if(id){
      return <h2 className='text-center'>Update Employee</h2>
    }else{
      return <h2 className='text-center'>Add Employee</h2>
    }
  }

  return (
    <div className='container'>
      <br/><br/>
      <div className='row'>
        <div className='card col-md-6 offset-md-3 offset-md-3'>
          {
            pageTitle()
                      }
          <div className='card-body'>
            <form>
              <div className='form-group mb-2'>
                <label className='form-label'>First Name: </label>
                <input type='text' placeholder='First Name' name='firstName' className={`form-control ${errors.firstName ? 'is-invalid' : ''}`}
                value={firstName} onChange={(e) => setFirstName(e.target.value)} />
                {errors.firstName && <div className='invalid-feedback'>{errors.firstName}</div>}
              </div>
              <div className='form-group'>
                <label> Last Name: </label>
                <input type='text' placeholder='Last Name' name='lastName' className={`form-control ${errors.lastName ? 'is-invalid' : ''}`}
                value={lastName} onChange={(e) => setLastName(e.target.value)} />
                {errors.lastName && <div className='invalid-feedback'>{errors.lastName}</div>}
              </div>
              <div className='form-group'>
                <label> Email: </label>
                <input type='email' placeholder='Email' name='email' className={`form-control ${errors.email ? 'is-invalid' : ''}`}
                value={email} onChange={(e) => setEmail(e.target.value)} />
                {errors.email && <div className='invalid-feedback'>{errors.email}</div>}
              </div>
              <div className='form-group'>
                <label> Department Code: </label>
                <select className='form-control' value={departmentCode} onChange={(e) => setDepartmentCode(e.target.value)}>
                  <option value="">-- Select Department --</option>
                  {departments.map((dept) => (
                    <option key={dept.id} value={dept.departmentCode}>
                  {dept.departmentName} ({dept.departmentCode})
                  </option>
                  ))}     
                </select>
              </div>
              <div className='form-group'>
                <label> Organization Code: </label>
                <select className='form-control' value={organizationCode} onChange={(e) => setOrganizationCode(e.target.value)}>
                  <option value="">-- Select Organization --</option>
                  {organizations.map((org) => (
                    <option key={org.id} value={org.organizationCode}>
                  {org.organizationName} ({org.organizationCode})
                  </option>
                  ))}     
                </select>
              </div>

              <button className='btn btn-success mx-5' onClick={saveOrUpdateEmployee}>Save</button>
              <button className='btn btn-danger mx-5' onClick={()=>navigate('/employees')} >Cancel</button>
            </form>
        </div> 
      </div>
      </div>  
    </div>
  )
}

export default AddEmployeeComponent