import React from "react";

const FooterStyles = {
    position: "fixed",
    bottom: "0",
    left: "0",
    width: "100%",
    height:"6vh",
    textAlign: "center",
    fontFamily: "Edu AU VIC WA NT Pre, serif2",
    backgroundColor:"#8ecae6"
}

const Footer = () => {
    return (
        <footer style={FooterStyles}>
            <h4 className="mt-2">© SpringStore | 2024</h4>
        </footer>
    )
}

export default Footer