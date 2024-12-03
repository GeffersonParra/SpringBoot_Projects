import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";

const EditClientForm = () => {
    const { id } = useParams();
    const [name, setName] = useState('');
    const [address, setAddress] = useState('');
    const [email, setEmail] = useState('');
    const [tel_number, setTel_number] = useState('');
    const [client, setClient] = useState(null);
    const navigate = useNavigate();

    const FormStyle = {
        backgroundColor: "#023047",
        fontFamily: "Edu AU VIC WA NT Pre, serif2",
        color: "white"
    };

    useEffect(() => {
        const fetchClient = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/clients/${id}`);
                setClient(response.data)
                setName(response.data.name);
                setAddress(response.data.address);
                setEmail(response.data.email)
                setTel_number(response.data.tel_number)
            } catch (error) {
                console.error("Error al obtener la categoría:", error);
            }
        };
        
        fetchClient();
    }, [id]);

    const handleSubmit = async (event) => {
        event.preventDefault();

        const clientData = {
            name: name,
            address: address,
            tel_number: tel_number,
            email: email,
        };

        try {
            await axios.put(`http://localhost:8080/api/clients/${id}`, clientData, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            navigate("/clients");
        } catch (error) {
            console.error("Error al actualizar la categoría:", error);
        }
    };

    return (
        <div className='mt-5 col-7 mx-auto h-auto rounded-4' style={FormStyle}>
            {client ? (
                <>
            <h1 className="text-center mt-3">Editar un cliente</h1>
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
                <button type="submit" className="btn btn-new mx-auto mt-5 mb-5">Actualizar Cliente</button>
            </form>
            </>
            ) : (
                <h3 className="text-center">Ups... El cliente que solicitaste no pudo ser encontrado.</h3>
            )}
        </div>
    );
};

export default EditClientForm;
