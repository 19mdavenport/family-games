import "./index.css";
import React from "react";
import {createRoot} from "react-dom/client";
import App from "./App";
import UserInfoProvider from "./hooks/userInfo/UserInfoProvider";
import ToastProvider from "./hooks/toaster/ToastProvider";

const container = document.getElementById("root")!;
const root = createRoot(container);

root.render(
  // <React.StrictMode>
    <UserInfoProvider>
      <ToastProvider>
        <App />
      </ToastProvider>
    </UserInfoProvider>
  // </React.StrictMode>
);
