import './App.css';
import AddEmployeeComponent from './component/AddEmployeeComponent';
import EmployeeComponent from './component/EmployeeComponent';
import FooterComponent from './component/FooterComponent';
import HeaderComponent from './component/HeaderComponent';
import ListEmployeeComponent from './component/ListEmployeeComponent';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import EmailVerify from './pages/EmailVerify';
import Login from './pages/Login';
import ResetPassword from './pages/ResetPassword';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Menubar from './component/Menubar';
import Home from './pages/Home';

function App() {
  return (
    <div className="App">
    

      <ToastContainer/>
        <Routes>
          {/* <Route path="/" element={<ListEmployeeComponent />}></Route> */}
          <Route path="/" element={<Home />}></Route>
          <Route path="/login" element={<Login />}></Route>
          <Route path="/email-verify" element={<EmailVerify />}></Route>
          <Route path="/reset-password" element={<ResetPassword />}></Route>
          <Route path="/employees" element={<ListEmployeeComponent />}></Route>
          <Route path="/add-employee" element={<AddEmployeeComponent />}></Route>
          <Route path="/edit-employee/:id" element={<AddEmployeeComponent />}></Route>
        </Routes>
        <FooterComponent />     
    </div>
  );
}

export default App;
