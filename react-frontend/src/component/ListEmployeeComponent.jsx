import React,{useEffect, useState} from 'react'
import EmployeeService from '../service/EmployeeService'
import { useNavigate } from 'react-router-dom'

const ListEmployeeComponent = () => {

    const [employees, setEmployees] = useState([])


    const navigate = useNavigate()

    useEffect(() => {
        getAllEmployees();
    }, [])

    function getAllEmployees(){
        EmployeeService.getAllEmployees().then((response) => {
            setEmployees(response.data)
            console.log(response.data)
        }).catch(error => {
            console.log(error)
        })
    }

    function addNewEmployee() {
        navigate('/add-employee')
    }

    function updateEmployee(id) {
        navigate(`/edit-employee/${id}`)
    }

    function removeEmployee(id) {
        console.log("Removing employee with id:", id)
        return EmployeeService.deleteEmployee(id).then((response) => {
            getAllEmployees();
            console.log("Employee deleted successfully:", response.data)
        }).catch(error => {
            console.error("There was an error deleting the employee!", error)
        })    
    }

  return (
    <div className="container">
      <h2 className="text-centre">List of Employees</h2>
      <button className="btn btn-primary my-2" style={{marginBottom: "10px"}} onClick={addNewEmployee}>Add Employee</button>
      <table className="table table-striped table-bordered">
        <thead>
            <tr>
                <th>Employee Id</th>
                <th>Employee First Name</th>
                <th>Employee Last Name</th>
                <th>Employee Email Id</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            {
                employees.map(
                    employee => (
                        <tr key={employee.id}>
                            <td>{employee.id}</td>
                            <td>{employee.firstName}</td>
                            <td>{employee.lastName}</td>
                            <td>{employee.email}</td>
                            <td>
                                <button className='btn btn-info' onClick={() =>updateEmployee(employee.id)}>Update</button>
                                <button className='btn btn-danger' style={{marginLeft: "10px"}} onClick={() => removeEmployee(employee.id).then(() => {
                                    setEmployees(employees.filter(emp => emp.id !== employee.id))
                                })}>Delete</button>
                            </td>
                        </tr>
                    )
                )
            }
        </tbody>
      </table>
    </div>
  )
}

export default ListEmployeeComponent
