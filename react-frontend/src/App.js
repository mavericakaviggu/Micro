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
import ProtectedRoute from './component/ProtectedRoute';
import MainLayout from './layout/MainLayout';

function App() {
  return (
    <div className="App">
    

      <ToastContainer/>
        <Routes>
          <Route element={<MainLayout />}>
          <Route path="/" element={<Home />}></Route>
          <Route path="/email-verify" element={<EmailVerify />}></Route>
          <Route path="/reset-password" element={<ResetPassword />}></Route>
          <Route path="/employees" element={<ProtectedRoute> <ListEmployeeComponent /></ProtectedRoute>}></Route>
          <Route path="/add-employee" element={<AddEmployeeComponent />}></Route>
          <Route path="/edit-employee/:id" element={<AddEmployeeComponent />}></Route>
          {/* other protected or public pages */}
          </Route>

          {/* Optional: standalone routes without navbar */}
          <Route path="/login" element={<Login />}></Route>

        </Routes>
        <FooterComponent />     
    </div>
  );
}

export default App;
