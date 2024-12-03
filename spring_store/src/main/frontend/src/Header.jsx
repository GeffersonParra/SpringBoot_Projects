import React from "react";
import './Bootstrap.css'

const HeaderStyle = {
    backgroundColor: "#8ecae6",
    fontFamily: "Edu AU VIC WA NT Pre, serif2",
    height: "4rem",
    position:"fixed",
    zIndex: "100"
}

const a = {
    textDecoration:"none",
    color:"black"
}

const Header = () =>{
    return(
        <header style={HeaderStyle} className="d-flex align-items-center col-12">
            <h1 className="col-4 text-center">
                SpringStore
            </h1>
            <a href="/categories" className="col-2" style={a}><h2 className="col-12 text-start">Categorías</h2></a>
            <a href="/clients" className="col-2" style={a}><h2 className="col-2 text-start">Clientes</h2></a>
            <a href="/orders" className="col-2" style={a}><h2 className="col-2 text-start">Órdenes</h2></a>
            
        </header>
    )
}

export default Header;