import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const NewCategoryForm = () => {
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');

    const navigate = useNavigate();

    const FormStyle = {
        backgroundColor: "#023047",
        fontFamily: "Edu AU VIC WA NT Pre, serif2",
        color: "white"
    }

    const handleSubmit = async (event) => {
        event.preventDefault();

        const categoryData = {
            name: name,
            description: description,
            products: []
        };
            const response = await axios.post('http://localhost:8080/api/categories', categoryData, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            setName('');
            setDescription('');
            navigate("/categories")
    };

    return (
        <div className='mt-5 col-7 mx-auto h-auto rounded-4' style={FormStyle}>
            <h1 className="text-center mt-3">Crear una nueva categoría</h1>
            <form onSubmit={handleSubmit} className="mt-4 d-flex flex-column">
                <div className="d-flex flex-column">
                    <label for="name" className="text-center">Nombre:</label>
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
                    <label for="description" className="text-center">Descripción:</label>
                    <textarea
                        className="col-6 mx-auto"
                        id="description"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="btn btn-new mx-auto mt-5 mb-5">Crear Categoría</button>
            </form>
        </div>
    )
}

export default NewCategoryForm;