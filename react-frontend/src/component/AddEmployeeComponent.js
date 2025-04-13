import React, { useState } from 'react'
import EmployeeService from '../service/EmployeeService';
import { useNavigate } from 'react-router-dom'

function AddEmployeeComponent() {

  const [firstName,setFirstname] = useState('')
  const [lastName,setLastname] = useState('')
  const [email,setEmail] = useState('') 
  const [departmentCode,setDepartmentCode] = useState('')
  const [organizationCode,setOrganizationCode] = useState('')

  const navigate = useNavigate();

  function saveEmployee(e){
    e.preventDefault()
    const employee = {firstName,lastName,email,departmentCode,organizationCode}
    console.log(employee)
    EmployeeService.createEmployee(employee).then((response) => {
      console.log(response.data);
      navigate('/employees');
  })
  }

  return (
    <div className='container'>
      <br/><br/>
      <div className='row'>
        <div className='card col-md-6 offset-md-3 offset-md-3'>
          <h2 className='text-center'>Add Employee</h2>
          <div className='card-body'>
            <form>
              <div className='form-group mb-2'>
                <label className='form-label'>First Name: </label>
                <input type='text' placeholder='First Name' name='firstName' className='form-control' 
                value={firstName} onChange={(e) => setFirstname(e.target.value)} />
              </div>
              <div className='form-group'>
                <label> Last Name: </label>
                <input type='text' placeholder='Last Name' name='lastName' className='form-control' 
                value={lastName} onChange={(e) => setLastname(e.target.value)} />
              </div>
              <div className='form-group'>
                <label> Email: </label>
                <input type='text' placeholder='Email' name='email' className='form-control' 
                value={email} onChange={(e) => setEmail(e.target.value)} />
              </div>
              <div className='form-group'>
                <label> Department Code: </label>
                <input type='text' placeholder='Department Code' name='departmentCode' className='form-control' 
                value={departmentCode} onChange={(e) => setDepartmentCode(e.target.value)} />
              </div>
              <div className='form-group'>
                <label> Organization Code: </label>
                <input type='text' placeholder='Organization Code' name='organizationCode' className='form-control' 
                value={organizationCode} onChange={(e) => setOrganizationCode(e.target.value)} />
              </div>

              <button className='btn btn-success mx-5' onClick={saveEmployee}>Save</button>
              <button className='btn btn-danger mx-5'>Cancel</button>
            </form>
        </div> 
      </div>
      </div>  
    </div>
  )
}

export default AddEmployeeComponent
