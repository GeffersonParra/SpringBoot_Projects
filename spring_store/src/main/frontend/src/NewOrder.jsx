import React, { useState } from "react";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const NewOrderForm = () => {
    const [date, setDate] = useState('');
    const [total, setTotal] = useState('');
    const [id_client_id, setid_client_id] = useState('');

    const [clients, setClients] = useState([]);
    const navigate = useNavigate();

    const FormStyle = {
        backgroundColor: "#023047",
        fontFamily: "Edu AU VIC WA NT Pre, serif2",
        color: "white"
    }

    useEffect(() => {
        axios.get('http://localhost:8080/api/clients')
            .then(response => {
                setClients(response.data);
            })
            .catch(error => {
                console.error("Error al obtener los clientes", error);
            });
    }, []);

    const handleSubmit = async (event) => {
        event.preventDefault();
        const orderData = {
            date: date,
            total: total,
            clientId: id_client_id,
        };

        const response = await axios.post('http://localhost:8080/api/orders', orderData, {
            headers: {
                'Content-Type': 'application/json',
            },
        });

        setDate('');
        setTotal('');
        setid_client_id('');
        navigate(`/orders`)
    };

    return (
        <div className='mt-5 col-7 mx-auto h-auto rounded-4' style={FormStyle}>
            <h1 className="text-center mt-3">Crear una nueva órden</h1>
            <form onSubmit={handleSubmit} className="mt-4 d-flex flex-column">
                <div className="d-flex flex-column">
                    <label htmlFor="date" className="text-center">Fecha:</label>
                    <input
                        className="col-6 mx-auto"
                        type="date"
                        id="datetime"
                        value={date}
                        onChange={(e) => setDate(e.target.value)}
                        required
                    />
                </div>
                <div className="d-flex flex-column mt-4">
                    <label htmlFor="total" className="text-center">Total:</label>
                    <input
                        type="number"
                        className="col-6 mx-auto"
                        id="total"
                        value={total}
                        onChange={(e) => setTotal(e.target.value)}
                        required
                    />
                </div>
                <div className="d-flex flex-column mt-4">
                    <label htmlFor="id_client_id" className="text-center">Cliente:</label>
                    <select
                        className="col-6 mx-auto"
                        id="id_client_id"
                        value={id_client_id}
                        onChange={(e) => setid_client_id(e.target.value)}
                        required
                    >
                        <option value="">Selecciona un cliente</option>
                        {clients.map((client) => (
                            <option key={client.id} value={client.id}>
                                {client.name}
                            </option>
                        ))}
                    </select>
                </div>
                <button type="submit" className="btn btn-new mx-auto mt-5 mb-5">Crear Producto</button>
            </form>
        </div>
    )
}

export default NewOrderForm;