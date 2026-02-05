import axios from 'axios';

const EMPLOYEE_SERVICE_BASE_URL = `${process.env.REACT_APP_EMPLOYEE_SERVICE_BASE_URL}`; // Using the .env file for the base variable

class EmployeeService {

    getEmployee(){
        return axios.get(EMPLOYEE_SERVICE_BASE_URL + "/");
    }

    getAllEmployees(){
        return axios.get(EMPLOYEE_SERVICE_BASE_URL);
    }

    createEmployee(employee){
        return axios.post(EMPLOYEE_SERVICE_BASE_URL, employee);
    }

    getEmployee(employeeId){
        return axios.get(EMPLOYEE_SERVICE_BASE_URL + '/' + employeeId);
    }

    updateEmployee(employeeId, employee){
        return axios.put(EMPLOYEE_SERVICE_BASE_URL + '/' + employeeId, employee);
    }

    deleteEmployee(employeeId){
        return axios.delete(EMPLOYEE_SERVICE_BASE_URL + '/' + employeeId);
    }
}

export default new EmployeeService();