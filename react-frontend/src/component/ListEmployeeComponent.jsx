import React,{useEffect, useState} from 'react'
import EmployeeService from '../service/EmployeeService'

const ListEmployeeComponent = () => {

    const [employees, setEmployees] = useState([])

    useEffect(() => {
        EmployeeService.getAllEmployees().then((response) => {
            setEmployees(response.data)
            console.log(response.data)
        }).catch(error => {
            console.log(error)
        })
    }, [])

  return (
    <div class="container">
      <h2 class="text-centre">List of Employees</h2>
      <table class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>Employee Id</th>
                <th>Employee First Name</th>
                <th>Employee Last Name</th>
                <th>Employee Email Id</th>
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
