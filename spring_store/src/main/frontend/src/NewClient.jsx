import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const NewClientForm = () => {
    const [name, setName] = useState('');
    const [address, setAddress] = useState('');
    const [email, setEmail] = useState('');
    const [tel_number, setTel_number] = useState('');

    const navigate = useNavigate();

    const FormStyle = {
        backgroundColor: "#023047",
        fontFamily: "Edu AU VIC WA NT Pre, serif2",
        color: "white"
    }

    const handleSubmit = async (event) => {
        event.preventDefault();

        const clientData = {
            name: name,
            address: address,
            tel_number: tel_number,
            email: email,
        };
            const response = await axios.post('http://localhost:8080/api/clients', clientData, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            setName('');
            setEmail('');
            setTel_number('');
            setAddress('');
            navigate("/clients")
    };

    return (
        <div className='mt-5 col-7 mx-auto h-auto rounded-4' style={FormStyle}>
            <h1 className="text-center mt-3">Crear un nuevo cliente</h1>
            <form onSubmit={handleSubmit} className="mt-4 d-flex flex-column">
                <div className="d-flex flex-column">
                    <label htmlFor="name" className="text-center">Nombre:</label>
                    <input
                        className="col-6 mx-auto"
                        type="text"
                        id="name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />
                </div>
                <div className="d-flex flex-column mt-4">
                    <label htmlFor="email" className="text-center">E-mail:</label>
                    <input
                        className="col-6 mx-auto"
                        id="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div className="d-flex flex-column mt-4">
                    <label htmlFor="tel_number" className="text-center">Teléfono:</label>
                    <input
                        className="col-6 mx-auto"
                        id="tel_number"
                        value={tel_number}
                        onChange={(e) => setTel_number(e.target.value)}
                        required
                    />
                </div>
                <div className="d-flex flex-column mt-4">
                    <label htmlFor="address" className="text-center">Dirección:</label>
                    <input
                        className="col-6 mx-auto"
                        id="address"
                        value={address}
                        onChange={(e) => setAddress(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="btn btn-new mx-auto mt-5 mb-5">Crear Cliente</button>
            </form>
        </div>
    )
}

export default NewClientForm;