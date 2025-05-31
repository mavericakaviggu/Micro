import axios from 'axios';

const ORGANIZATION_SERVICE_BASE_URL = "http://localhost:8083/api/organizations"; // Update if needed

class OrganizationService {

    getOrganizations() {
        return axios.get(ORGANIZATION_SERVICE_BASE_URL);
    }
}

export default new OrganizationService();