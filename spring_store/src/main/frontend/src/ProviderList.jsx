import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Bootstrap.css';
import 'boxicons'

const ListStyle = {
  backgroundColor: "#023047",
  fontFamily: "Edu AU VIC WA NT Pre, serif2",
  color: "white"
}

const ProvidersList = () => {
  const [providers, setProviders] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch('http://localhost:8080/api/providers')
      .then(response => response.json())
      .then(data => {
        setProviders(data);
        setLoading(false);
      })
      .catch(error => {
        console.error("Error al obtener los proveedores:", error);
        setLoading(false);
      });
  }, []);

  if (loading) {
    return <div className='text-center mt-5 col-7 mx-auto h-auto rounded-4' style={ListStyle}>Cargando...</div>;
  }

  const handleDelete = async (id) => {
    const response = await fetch(`http://localhost:8080/api/providers/${id}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ _method: 'DELETE' }),
    });

    if (response.ok) {
      window.location.reload();
    } else {
      console.error("Hubo un error al eliminar el proveedor.");
    }
  };

  return (
    <div className='mt-5 col-7 mx-auto h-auto rounded-4' style={ListStyle}>
      <div className='d-flex'>
        <h1 className='text-end col-8 mt-3'>Lista de Proveedores</h1>
        <a href='providers/new' className='btn btn-new col-3 mx-auto h-25 mt-4'>
          Nuevo proveedor
        </a>
      </div>
      <ul style={{ listStyle: 'none' }} className='d-flex flex-column justify-content-center mt-4'>
        {providers.length > 0 ? (
          providers.map((provider, index) => (
            <div className='col-8 text-center card mb-3 mx-auto rounded-5 d-flex flex-row justify-content-center' style={{ backgroundColor: "#ffb703", height: "12rem" }} key={index}>
              <div className='d-flex flex-column col-1'>
                <a href={`/providers/edit/${provider.id}`} className='btn btn-success align-self-start col-12 mt-4'><box-icon name='edit-alt' type='solid' ></box-icon></a>
                <button onClick={() => handleDelete(provider.id)} className='btn btn-danger align-self-start col-12 mt-4'><box-icon name='trash' type='solid' ></box-icon></button>
              </div>
                <li className='d-flex flex-column col-10' style={{ color: "black" }}>
                  <h2 className='mt-3'>{provider.name}</h2>
                  <h4>{provider.email}</h4>
                  <h4>{provider.address}</h4>
                  <h4>{provider.tel_number}</h4>
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

export default ProvidersList;
