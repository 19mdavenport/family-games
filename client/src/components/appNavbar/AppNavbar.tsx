import "./AppNavbar.css";
import {Container, Nav, Navbar} from "react-bootstrap";
import {NavLink, useLocation, useNavigate} from "react-router-dom";
import Image from "react-bootstrap/Image";
import {useState} from "react";
import useUserInfo from "../../hooks/userInfo/UserInfoHook";
import {NavbarPresenter, NavbarView} from "../../presenter/appNavbar/NavbarPresenter";
import useToastListener from "../../hooks/toaster/ToastListenerHook";

interface Props {
  presenterGenerator: (view: NavbarView) => NavbarPresenter;
}

const AppNavbar = (props: Props) => {
  const location = useLocation();
  const { authToken, clearUserInfo } = useUserInfo();
  const navigate = useNavigate();
  const { displayInfoMessage, displayErrorMessage, clearLastInfoMessage } = useToastListener();

  const listener: NavbarView = {
    clearLastInfoMessage: clearLastInfoMessage,
    clearUserInfo: clearUserInfo,
    displayErrorMessage: displayErrorMessage,
    displayInfoMessage: displayInfoMessage,
    navigate: navigate
  };

  const [presenter] = useState(props.presenterGenerator(listener));

  const logOut = () => {
    presenter.logOut(authToken!);
  }

  return (
    <Navbar
      collapseOnSelect
      className="mb-4"
      expand="md"
    >
      <Container>
        <Navbar.Brand>
          <div className="d-flex flex-row">
            <div className="p-2">
              <NavLink className="brand-link" to="/">
                <Image src={"./bird-white-32.png"} alt="" />
              </NavLink>
            </div>
            <div id="brand-title" className="p-3">
              <NavLink className="brand-link" to="/">
                <b>Family Games</b>
              </NavLink>
            </div>
          </div>
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="ml-auto container-fluid">
            {/*<Nav.Item>*/}
            {/*  <NavLink to="/feed">Feed</NavLink>*/}
            {/*</Nav.Item>*/}
            {/*<Nav.Item>*/}
            {/*  <NavLink to="/story">Story</NavLink>*/}
            {/*</Nav.Item>*/}
            {/*<Nav.Item>*/}
            {/*  <NavLink to="/following">Following</NavLink>*/}
            {/*</Nav.Item>*/}
            {/*<Nav.Item>*/}
            {/*  <NavLink to="/followers">Followers</NavLink>*/}
            {/*</Nav.Item>*/}
            <Nav.Item className="ms-auto">
              <NavLink id="logout" onClick={logOut} to={location.pathname}>
                Logout
              </NavLink>
            </Nav.Item>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default AppNavbar;
