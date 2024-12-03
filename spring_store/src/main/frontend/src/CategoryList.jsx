import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Bootstrap.css';
import 'boxicons'

const ListStyle = {
  backgroundColor: "#023047",
  fontFamily: "Edu AU VIC WA NT Pre, serif2",
  color: "white"
}

const CategoryList = () => {
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch('http://localhost:8080/api/categories')
      .then(response => response.json())
      .then(data => {
        setCategories(data);
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
    const response = await fetch(`http://localhost:8080/api/categories/${id}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ _method: 'DELETE' }),
    });

    if (response.ok) {
      window.location.reload();
    } else {
      console.error("Hubo un error al eliminar la categoría.");
    }
  };

  return (
    <div className='mt-5 col-7 mx-auto h-auto rounded-4' style={ListStyle}>
      <div className='d-flex'>
        <h1 className='text-end col-8 mt-3'>Lista de Categorías</h1>
        <a href='categories/new' className='btn btn-new col-3 mx-auto h-25 mt-4'>
          Nueva categoría
        </a>
      </div>
      <ul style={{ listStyle: 'none' }} className='d-flex flex-column justify-content-center mt-4'>
        {categories.length > 0 ? (
          categories.map((category, index) => (
            <div className='col-8 text-center card mb-3 mx-auto rounded-5 d-flex flex-row justify-content-center' style={{ backgroundColor: "#ffb703", height: "15rem" }} key={index}>
              <div className='d-flex flex-column col-1'>
                <a href={`/categories/edit/${category.id}`} className='btn btn-success align-self-start col-12 mt-4'><box-icon name='edit-alt' type='solid' ></box-icon></a>
                <button onClick={() => handleDelete(category.id)} className='btn btn-danger align-self-start col-12 mt-2'><box-icon name='trash' type='solid' ></box-icon></button>
              </div>
              <a href={`/categories/view/${category.id}`} className='col-10' style={{textDecoration: "none"}}>
              <li className='d-flex flex-column col-10' style={{ color: "black" }}>
                <img src='https://aguacatec.es/wp-content/uploads/2023/10/e5a978b8-6772-4c85-a50e-15581af7d483.png' className='w-50 mt-4 mx-auto mb-1 rounded-3' style={{ objectFit: "cover", height: "8rem" }} />
                <h2>{category.name}</h2>
                <h4>{category.description}</h4>
              </li>
              </a>
            </div>
          ))
        ) : (
          <div className='text-center'>No hay categorías disponibles.</div>
        )}
      </ul>
    </div>
  );
};

export default CategoryList;
