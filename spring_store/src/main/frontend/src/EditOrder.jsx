import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";

const EditOrderForm = () => {
    const { id } = useParams();
    const [date, setDate] = useState('');
    const [total, setTotal] = useState('');
    const [id_client_id, setid_client_id] = useState('');
    const [order, setOrder] = useState(null);

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

    useEffect(() => {
        const fetchOrder = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/orders/${id}`);
                setOrder(response.data);
                setDate(response.data.date);
                setTotal(response.data.total);
                setid_client_id(response.data.clientId)
            } catch (error) {
                console.error("Error al obtener la orden: ", error);
            }
        };

        fetchOrder(id);
    }, [id]);

    const handleSubmit = async (event) => {
        event.preventDefault();

        const orderData = {
            date: date,
            total: total,
            clientId: id_client_id,
        };

        try {
            await axios.put(`http://localhost:8080/api/orders/${id}`, orderData, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            navigate("/orders");
        } catch (error) {
            console.error("Error al actualizar la orden:", error);
        }
    };

    return (
        <div className='mt-5 col-7 mx-auto h-auto rounded-4' style={FormStyle}>
            {order ? (
                <>
                    <h1 className="text-center mt-3">Editar una órden</h1>
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
                        <button type="submit" className="btn btn-new mx-auto mt-5 mb-5">Actualizar Orden</button>
                    </form>
                </>
            ) : (
                <h3 className="text-center">Ups... La orden que solicitaste no pudo ser encontrada.</h3>
            )};
        </div>
    );

};

export default EditOrderForm;
