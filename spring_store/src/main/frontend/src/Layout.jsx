import React from "react";
import Header from "./Header";
import Footer from "./Footer";

const LayoutStyle = {
    backgroundColor:"#219ebc",
    minHeight:"100vh",
    display: "flex",
    flexDirection: "column",  
}

const Layout = ({children}) => {
    return(
        <div style={LayoutStyle}>
        <Header/>
        <main className="d-flex flex-column flex-grow-1 w-100 p-5">{children}</main>
        <br />
        <Footer />
        </div>
    )
}

export default Layout;