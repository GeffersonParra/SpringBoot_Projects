import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Bootstrap.css';
import 'boxicons'

const ListStyle = {
    backgroundColor: "#023047",
    fontFamily: "Edu AU VIC WA NT Pre, serif2",
    color: "white"
}

const ClientList = () => {
    const [clients, setClients] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetch('http://localhost:8080/api/clients')
            .then(response => response.json())
            .then(data => {
                console.log(data);
                setClients(data);
                setLoading(false);
            })
            .catch(error => {
                console.error("Error al obtener las categorías:", error);
                setLoading(false);
            });
    }, []);

    if (loading) {
        return <div className='text-center mt-5 col-7 mx-auto h-auto rounded-4' style={ListStyle}>Cargando...</div>;
    }

    const handleDelete = async (id) => {
        const response = await fetch(`http://localhost:8080/api/clients/${id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ _method: 'DELETE' }),
        });

        if (response.ok) {
            window.location.reload();
        } else {
            console.error("Hubo un error al eliminar el cliente.");
        }
    };

    return (
        <div className='mt-5 col-7 mx-auto h-auto rounded-4' style={ListStyle}>
            <div className='d-flex'>
                <h1 className='text-end col-8 mt-3'>Lista de Clientes</h1>
                <a href='clients/new' className='btn btn-new col-3 mx-auto h-25 mt-4'>
                    Nuevo cliente
                </a>
            </div>
            <ul style={{ listStyle: 'none' }} className='d-flex flex-column justify-content-center mt-4'>
                {clients.length > 0 ? (
                    clients.map((client, index) => (
                        <div className='col-8 text-center card mb-3 mx-auto rounded-5 d-flex flex-row justify-content-center' style={{ backgroundColor: "#ffb703", height: "fit-content" }} key={index}>
                            <div className='d-flex flex-column col-1'>
                                <a href={`/clients/edit/${client.id}`} className='btn btn-success align-self-start col-12 mt-4'><box-icon name='edit-alt' type='solid' ></box-icon></a>
                                <button onClick={() => handleDelete(client.id)} className='btn btn-danger align-self-start col-12 mt-4'><box-icon name='trash' type='solid' ></box-icon></button>
                            </div>
                            <li className='d-flex flex-column col-10 mb-3' style={{ color: "black" }}>
                                <h4 className='col-12 mt-2'>{client.name}</h4>
                                <h5>{client.email}</h5>
                                <h5>{client.address}</h5>
                                <h5>{client.tel_number}</h5>
                                <h5 className='mt-3'>Órdenes:</h5>
                                {client.orders && client.orders.length > 0 ? (
                                    <div>
                                        {client.orders.map(order => (
                                            <div className='d-flex flex-column'>
                                                <a key={order.id} href={`/orders/view/${order.id}`} className='btn btn-success col-6 mx-auto mb-3'>
                                                    <b>${order.total}</b> - {order.date}
                                                </a>
                                            </div>
                                        ))}
                                    </div>
                                ) : (
                                    <p className='mt-2'>Este cliente no tiene órdenes.</p>
                                )}
                            </li>
                        </div>
                    ))
                ) : (
                    <div className='text-center'>No hay categorías disponibles.</div>
                )}
            </ul>
        </div>
    );
};

export default ClientList;