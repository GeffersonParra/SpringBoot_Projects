import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Link } from 'react-router-dom';
import axios from "axios";

const ListStyle = {
    backgroundColor: "#023047",
    fontFamily: "Edu AU VIC WA NT Pre, serif2",
    color: "white"
  }

const OrderDetailsList = () => {
    const { id } = useParams();
    const [order, setOrder] = useState(null);
    const [orderDetails, setOrderDetails] = useState(null);
    const navigate = useNavigate();

    const FormStyle = {
        backgroundColor: "#023047",
        fontFamily: "Edu AU VIC WA NT Pre, serif2",
        color: "white"
    }

    useEffect(() => {
        axios.get(`http://localhost:8080/api/orders/${id}`)
            .then(response => {
                setOrder(response.data);
                setOrderDetails(response.data.orderDetails);
            })
            .catch(error => {
                console.error("Error al obtener los clientes", error);
            });
    }, []);

    const handleDelete = async (id) => {
        const response = await fetch(`http://localhost:8080/api/orderdetails/${id}`, {
          method: 'DELETE',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({ _method: 'DELETE' }),
        });
    
        if (response.ok) {
          window.location.reload();
        } else {
          console.error("Hubo un error al eliminar el detalle.");
        }
      };

    return (
        <div className='mt-5 col-7 mx-auto h-auto rounded-4' style={FormStyle}>
            {order ? (
                <>
                    <div className='mt-5 col-12 mx-auto h-auto rounded-4' style={ListStyle}>
                        <div className='d-flex'>
                            <h1 className='text-end col-8 mt-3'>Detalles de compra</h1>
                            <Link to={'/orderdetails/new'} className='btn btn-new col-3 mx-auto h-25 mt-4'>
                                Adjuntar productos
                            </Link>
                        </div>
                        <div className='mt-5 col-11 mx-auto h-auto rounded-4 d-flex flex-column' style={{ backgroundColor: "#c1121f", height: "11rem" }}>
                            <h3 className="text-center mt-4">Fecha:  {order.date}</h3>
                            <h3 className="text-center">Total:  ${order.total}</h3>
                            <h3 className="text-center mb-4">Orden hecha por: {order.clientName}</h3>
                        </div>
                        <ul style={{ listStyle: 'none' }} className='d-flex flex-column justify-content-center mt-4'>
                            {orderDetails.length > 0 ? (
                                orderDetails.map((orderDetail, index) => (
                                    <div className='col-8 text-center card mb-3 mx-auto rounded-5 d-flex flex-row justify-content-center' style={{ backgroundColor: "#ffb703", height: "10rem" }} key={index}>
                                        <div className='d-flex flex-column col-1'>
                                            <a href={`/orderdetails/edit/${orderDetail.id}`} className='btn btn-success align-self-start col-12 mt-4'><box-icon name='edit-alt' type='solid' ></box-icon></a>
                                            <button onClick={() => handleDelete(orderDetail.id)} className='btn btn-danger align-self-start col-12 mt-2'><box-icon name='trash' type='solid' ></box-icon></button>
                                        </div>
                                        <a className='col-10' style={{ textDecoration: "none" }}>
                                            <li className='d-flex flex-column col-12' style={{ color: "black" }}>
                                                <h6 className="mt-4">Producto: {orderDetail.product_name}</h6>
                                                <h6>Cantidad: {orderDetail.quantity}</h6>
                                                <h6>Precio unitario:  ${orderDetail.product_price}</h6>
                                                <h6>Subtotal:  ${orderDetail.subtotal}</h6>
                                            </li>
                                        </a>
                                    </div>
                                ))
                            ) : (
                                <div className='text-center'>No hay detalles disponibles.</div>
                            )}
                        </ul>
                    </div>
                </>
            ) : (
                <h3 className="text-center">Ups... La orden que solicitaste no pudo ser encontrada.</h3>
            )};
        </div>
    );

};

export default OrderDetailsList;
