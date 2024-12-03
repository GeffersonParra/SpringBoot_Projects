import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Bootstrap.css';
import 'boxicons'

const ListStyle = {
  backgroundColor: "#023047",
  fontFamily: "Edu AU VIC WA NT Pre, serif2",
  color: "white"
}

const OrdersList = () => {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch('http://localhost:8080/api/orders')
      .then(response => response.json())
      .then(data => {
        console.log(data);
        setOrders(data);
        setLoading(false);
      })
      .catch(error => {
        console.error("Error al obtener las órdenes:", error);
        setLoading(false);
      });
  }, []);

  if (loading) {
    return <div className='text-center mt-5 col-7 mx-auto h-auto rounded-4' style={ListStyle}>Cargando...</div>;
  }

  const handleDelete = async (id) => {
    const response = await fetch(`http://localhost:8080/api/orders/${id}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ _method: 'DELETE' }),
    });

    if (response.ok) {
      window.location.reload();
    } else {
      console.error("Hubo un error al eliminar la órden.");
    }
  };

  return (
    <div className='mt-5 col-7 mx-auto h-auto rounded-4' style={ListStyle}>
      <div className='d-flex'>
        <h1 className='text-end col-8 mt-3'>Lista de Órdenes</h1>
        <a href='orders/new' className='btn btn-new col-3 mx-auto h-25 mt-4'>
          Nueva órden
        </a>
      </div>
      <ul style={{ listStyle: 'none' }} className='d-flex flex-column justify-content-center mt-4'>
        {orders.length > 0 ? (
          orders.map((order, index) => (
            <div className='col-8 text-center card mb-3 mx-auto rounded-5 d-flex flex-row justify-content-center' style={{ backgroundColor: "#ffb703", height: "11rem" }} key={index}>
              <div className='d-flex flex-column col-1'>
                <a href={`/orders/edit/${order.id}`} className='btn btn-success align-self-start col-12 mt-4'><box-icon name='edit-alt' type='solid' ></box-icon></a>
                <button onClick={() => handleDelete(order.id)} className='btn btn-danger align-self-start col-12 mt-2'><box-icon name='trash' type='solid' ></box-icon></button>
              </div>
              <a href={`/orders/view/${order.id}`} className='col-10' style={{textDecoration: "none"}}>
              <li className='d-flex flex-column col-12' style={{ color: "black" }}>
                <h4 className='mt-3'>{order.date}</h4>
                <h4>Total: {order.total}</h4>
                <h4>Orden hecha por: {order.clientName}</h4>
                <h4>con id:  {order.clientId}</h4>
              </li>
              </a>
            </div>
          ))
        ) : (
          <div className='text-center'>No hay órdenes disponibles.</div>
        )}
      </ul>
    </div>
  );
};

export default OrdersList;
