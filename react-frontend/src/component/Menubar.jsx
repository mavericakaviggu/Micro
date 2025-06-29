import { useNavigate } from "react-router-dom";
import { assets } from "../assets/assets";

const Menubar = () => {
  const navigate = useNavigate();
  return (
    <nav className="navbar fixed-top bg-white px-5 py-4 d-flex justify-between items-center shadow-md">
      <div className="d-flex align-items-center gap-2">
        <img src={assets.logo} alt=" Logo" />
        <span className="fw-bold fs-4 text-dark">Authify</span>
      </div>
      <div className="btn btn-outline-dark rounded-pill px-3" onClick={() => navigate('/login')}>
        Login <i className="bi bi-arrow-right ms-2"></i>
      </div>
    </nav>
  )
}

export default Menubar;
