import { useParams } from 'react-router-dom';
import { useEffect } from 'react';
import { Link } from 'react-router-dom';
import React, { useState } from "react";
import './Bootstrap.css';
import 'boxicons'

const ListStyle = {
    backgroundColor: "#023047",
    fontFamily: "Edu AU VIC WA NT Pre, serif2",
    color: "white"
}

const CategoryProducts = () => {
    const { id } = useParams();
    const [products, setProducts] = useState('');
    const [categoryName, setCategoryname] = useState();
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetch(`http://localhost:8080/api/categories/${id}`)
            .then(response => response.json())
            .then(data => {
                console.log(data); 
                setCategoryname(data[0].category_name);
                setProducts(data); 
                setLoading(false);
            })
            .catch(error => {
                console.error("Error al obtener las categorías:", error);
                setLoading(false);
            });
    }, [id]);

    if (loading) {
        return <div className='text-center mt-5 col-7 mx-auto h-auto rounded-4' style={ListStyle}>Cargando...</div>;
    }

    const handleDelete = async (id) => {
        const deleteResponse = await fetch(`http://localhost:8080/api/products/${id}`, {
          method: 'DELETE',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({ _method: 'DELETE' }),
        })
        if (deleteResponse.ok) {
            window.location.reload();
          } else {
            console.error("Hubo un error al eliminar la categoría.");
          }
    };

    return (
        <div className='mt-5 col-7 mx-auto h-auto rounded-4' style={ListStyle}>
            <h1 className='text-center mt-4'>{categoryName}</h1>
            <div className='d-flex'>
                <h1 className='text-end col-8 mt-3'>Lista de Productos</h1>
                <Link to={'/products/new'} className='btn btn-new col-3 mx-auto h-25 mt-4'>
                    Nuevo producto
                </Link>
            </div>
            <ul style={{ listStyle: 'none' }} className='d-flex flex-column justify-content-center mt-4'>
                {products.length > 0 ? (
                    products.map((product, index) => (
                        <div className='col-8 text-center card mb-3 mx-auto rounded-5 d-flex flex-row justify-content-center' style={{ backgroundColor: "#ffb703", height: "10rem", overflow: "hidden" }} key={index}>
                            <div className='d-flex flex-column col-1'>
                                <a href={`/products/edit/${product.id}`} className='btn btn-success align-self-start col-12 mt-4'><box-icon name='edit-alt' type='solid' ></box-icon></a>
                                <button onClick={() => handleDelete(product.id)} className='btn btn-danger align-self-start col-12 mt-4'><box-icon name='trash' type='solid' ></box-icon></button>
                            </div>
                            <a className='col-10 d-flex' style={{ textDecoration: "none" }}>
                                <li className='d-flex flex-column col-12 mt-4 mx-auto' style={{ color: "black" }}>
                                    <h4 className='col-12'>{product.name}</h4>
                                    <h5>{product.price}</h5>
                                    <h5>{product.provider_name}</h5>
                                </li>
                            </a>
                        </div>
                    ))
                ) : (
                    <div className='text-center'>No hay productos disponibles.</div>
                )}
            </ul>
        </div>
    );
};

export default CategoryProducts;
