import React, { useState } from "react";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const NewProductForm = () => {
    const [name, setName] = useState('');
    const [price, setPrice] = useState('');
    const [provider, setProvider] = useState('');
    const [category, setCategory] = useState('');
    const [quantity, setQuantity] = useState('');

    const [categories, setCategories] = useState([]);
    const [providers, setProviders] = useState([]);
    const navigate = useNavigate();

    const FormStyle = {
        backgroundColor: "#023047",
        fontFamily: "Edu AU VIC WA NT Pre, serif2",
        color: "white"
    }

    useEffect(() => {
        axios.get('http://localhost:8080/api/categories')
            .then(response => {
                setCategories(response.data);
            })
            .catch(error => {
                console.error("Error al obtener las categorías", error);
            });

        axios.get('http://localhost:8080/api/providers')
            .then(response => {
                setProviders(response.data);
            })
            .catch(error => {
                console.error("Error al obtener los proveedores", error);
            });
    }, []);

    const handleSubmit = async (event) => {
        event.preventDefault();
        const productData = {
            name: name,
            price: price,
            id_provider: provider,
            id_category: category,
            quantity: quantity
        };

        const response = await axios.post('http://localhost:8080/api/products', productData, {
            headers: {
                'Content-Type': 'application/json',
            },
        });

        setName('');
        setPrice('');
        setProvider('');
        setCategory('');
        setQuantity('');
        navigate(`/categories/view/${category}`)
    };

    return (
        <div className='mt-5 col-7 mx-auto h-auto rounded-4' style={FormStyle}>
            <h1 className="text-center mt-3">Crear un nuevo producto</h1>
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
                    <label htmlFor="price" className="text-center">Precio:</label>
                    <input
                        type="number"
                        className="col-6 mx-auto"
                        id="price"
                        value={price}
                        onChange={(e) => setPrice(e.target.value)}
                        required
                    />
                </div>
                <div className="d-flex flex-column mt-4">
                    <label htmlFor="provider" className="text-center">Proveedor:</label>
                    <select
                        className="col-6 mx-auto"
                        id="provider"
                        value={provider}
                        onChange={(e) => setProvider(e.target.value)}
                        required
                    >
                        <option value="">Selecciona un proveedor</option>
                        {providers.map((provider) => (
                            <option key={provider.id} value={provider.id}>
                                {provider.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="d-flex flex-column mt-4">
                    <label htmlFor="category" className="text-center">Categoría:</label>
                    <select
                        className="col-6 mx-auto"
                        id="category"
                        value={category}
                        onChange={(e) => setCategory(e.target.value)}
                        required
                    >
                        <option value="">Selecciona una categoría</option>
                        {categories.map((category) => (
                            <option key={category.id} value={category.id}>
                                {category.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="d-flex flex-column mt-4">
                    <label htmlFor="quantity" className="text-center">Cantidad:</label>
                    <input
                        className="col-6 mx-auto"
                        id="quantity"
                        value={quantity}
                        onChange={(e) => setQuantity(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="btn btn-new mx-auto mt-5 mb-5">Crear Producto</button>
            </form>
        </div>
    )
}

export default NewProductForm;