import "./MainLayout.css";
import {Outlet} from "react-router-dom";
import AppNavbar from "../appNavbar/AppNavbar";
import {NavbarPresenter} from "../../presenter/appNavbar/NavbarPresenter";

const MainLayout = () => {
  return (
    <>
      <AppNavbar presenterGenerator={(view) => new NavbarPresenter(view)}/>
      <Outlet/>
    </>
  );
};

export default MainLayout;
