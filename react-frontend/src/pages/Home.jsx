import HeaderComponent from "../component/HeaderComponent";
import Menubar from "../component/Menubar"

const Home = () => {
  return (
    <div className="flex flex-col items-center justify-content-center min-vh-100">
        <Menubar />
        <HeaderComponent />
    </div>
  );
}

export default Home;