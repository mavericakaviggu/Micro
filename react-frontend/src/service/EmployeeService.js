import axios from 'axios';

const EMPLOYEE_SERVICE_BASE_URL = "http://localhost:8081/api/employees";

const EMPLOYEE_ID = 4;

class EmployeeService {

    getEmployee(){
        return axios.get(EMPLOYEE_SERVICE_BASE_URL + "/" + EMPLOYEE_ID);
    }

    getAllEmployees(){
        return axios.get(EMPLOYEE_SERVICE_BASE_URL);
    }

    createEmployee(employee){
        return axios.post(EMPLOYEE_SERVICE_BASE_URL, employee);
    }

    getEmployeeById(employeeId){
        return axios.get(EMPLOYEE_SERVICE_BASE_URL + '/' + employeeId);
    }

    updateEmployee(employee, employeeId){
        return axios.put(EMPLOYEE_SERVICE_BASE_URL + '/' + employeeId, employee);
    }

    deleteEmployee(employeeId){
        return axios.delete(EMPLOYEE_SERVICE_BASE_URL + '/' + employeeId);
    }
}

export default new EmployeeService();