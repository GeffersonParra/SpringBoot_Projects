import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";

const EditCategoryForm = () => {
    const { id } = useParams();
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [category, setCategory] = useState(null);
    const navigate = useNavigate();

    const FormStyle = {
        backgroundColor: "#023047",
        fontFamily: "Edu AU VIC WA NT Pre, serif2",
        color: "white"
    };

    useEffect(() => {
        const fetchCategory = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/categories/view/${id}`);
                setCategory(response.data);
                setName(response.data.name);
                setDescription(response.data.description);
            } catch (error) {
                console.error("Error al obtener la categoría:", error);
            }
        };
        
        fetchCategory();
    }, [id]);

    const handleSubmit = async (event) => {
        event.preventDefault();

        const categoryData = {
            name: name,
            description: description,
            products: []
        };

        try {
            await axios.put(`http://localhost:8080/api/categories/${id}`, categoryData, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            navigate("/categories");
        } catch (error) {
            console.error("Error al actualizar la categoría:", error);
        }
    };

    return (
        <div className='mt-5 col-7 mx-auto h-auto rounded-4' style={FormStyle}>
            {category ? (
                <>
                    <h1 className="text-center mt-3">Editar la categoría: {category.name}</h1>
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
                            <label htmlFor="description" className="text-center">Descripción:</label>
                            <textarea
                                className="col-6 mx-auto"
                                id="description"
                                value={description}
                                onChange={(e) => setDescription(e.target.value)}
                                required
                            />
                        </div>
                        <button type="submit" className="btn btn-new mx-auto mt-5 mb-5">Actualizar Categoría</button>
                    </form>
                </>
            ) : (
                <h3 className="text-center">Ups... La categoría que solicitaste no pudo ser encontrada.</h3>
            )}
        </div>
    );
};

export default EditCategoryForm;
