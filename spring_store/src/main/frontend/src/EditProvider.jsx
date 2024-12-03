import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";

const EditProviderForm = () => {
    const { id } = useParams();
    const [name, setName] = useState('');
    const [address, setAddress] = useState('');
    const [email, setEmail] = useState('');
    const [tel_number, setTel_number] = useState('');
    const [provider, setProvider] = useState(null);
    const navigate = useNavigate();

    const FormStyle = {
        backgroundColor: "#023047",
        fontFamily: "Edu AU VIC WA NT Pre, serif2",
        color: "white"
    };

    useEffect(() => {
        const fetchProvider = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/providers/${id}`);
                setProvider(response.data)
                setName(response.data.name);
                setAddress(response.data.address);
                setEmail(response.data.email)
                setTel_number(response.data.tel_number)
            } catch (error) {
                console.error("Error al obtener al proveedor: ", error);
            }
        };
        
        fetchProvider();
    }, [id]);

    const handleSubmit = async (event) => {
        event.preventDefault();

        const providerData = {
            name: name,
            address: address,
            tel_number: tel_number,
            email: email,
        };

        try {
            await axios.put(`http://localhost:8080/api/providers/${id}`, providerData, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            navigate("/providers");
        } catch (error) {
            console.error("Error al actualizar el proveedor:", error);
        }
    };

    return (
        <div className='mt-5 col-7 mx-auto h-auto rounded-4' style={FormStyle}>
            {provider ? (
                <>
            <h1 className="text-center mt-3">Editar un proveedor</h1>
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
                <button type="submit" className="btn btn-new mx-auto mt-5 mb-5">Actualizar Proveedor</button>
            </form>
            </>
            ) : (
                <h3 className="text-center">Ups... El proveedor que solicitaste no pudo ser encontrado.</h3>
            )}
        </div>
    );
};

export default EditProviderForm;
