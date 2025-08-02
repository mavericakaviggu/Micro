import axios from 'axios';

const DEPARTMENT_SERVICE_BASE_URL = "http://localhost:8080/api/departments"; // Update if needed

class DepartmentService {

    getDepartments() {
        return axios.get(DEPARTMENT_SERVICE_BASE_URL, {
        withCredentials: true
        });
    }
}

export default new DepartmentService();
