import React, { useState } from "react";
import { useParams } from "react-router-dom";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const EditOrderDetailForm = () => {
    const { id } = useParams();
    const [orderId, setOrderId] = useState('');
    const [productId, setProductId] = useState('');
    const [quantity, setQuantity] = useState('');
    const [subtotal, setSubtotal] = useState('');

    const [orders, setOrders] = useState([]);
    const [products, setProducts] = useState([]);
    const navigate = useNavigate();

    const FormStyle = {
        backgroundColor: "#023047",
        fontFamily: "Edu AU VIC WA NT Pre, serif2",
        color: "white"
    }

    useEffect(() => {
        const fetchOrderDetails = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/orderdetails/${id}`);
                setOrderId(response.data.orderId);
                setProductId(response.data.productId);
                setQuantity(response.data.quantity);
                setSubtotal(response.data.subtotal)
            } catch (error) {
                console.error("Error al obtener la orden: ", error);
            }
        };

        fetchOrderDetails(id);
    }, [id]);

    useEffect(() => {
        axios.get('http://localhost:8080/api/orders')
            .then(response => {
                setOrders(response.data);
            })
            .catch(error => {
                console.error("Error al obtener las órdenes", error);
            });
    }, []);

    useEffect(() => {
        axios.get('http://localhost:8080/api/products')
            .then(response => {
                setProducts(response.data);
            })
            .catch(error => {
                console.error("Error al obtener los productos", error);
            });
    }, []);

    const handleSubmit = async (event) => {
        event.preventDefault();
        const orderData = {
            orderId: orderId,
            productId: productId,
            quantity: quantity,
            subtotal: subtotal,
        };

        try {
            await axios.put(`http://localhost:8080/api/orderdetails/${id}`, orderData, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            setOrderId('');
            setProductId('');
            setQuantity('');
            setSubtotal('');
            navigate(`/orders/view/${orderId}`)
        } catch (error) {
            console.error("Error al actualizar el detalle:", error);
        }
    };

    return (
        <div className='mt-5 col-7 mx-auto h-auto rounded-4' style={FormStyle}>
            <h1 className="text-center mt-3">Editar un detalle</h1>
            <form onSubmit={handleSubmit} className="mt-4 d-flex flex-column">
                <div className="d-flex flex-column mt-4">
                    <label htmlFor="orderId" className="text-center">Orden:</label>
                    <select
                        className="col-6 mx-auto"
                        id="orderId"
                        value={orderId}
                        onChange={(e) => setOrderId(e.target.value)}
                        required
                    >
                        <option value="">Selecciona una orden</option>
                        {orders.map((order) => (
                            <option key={order.id} value={order.id}>
                                {order.id}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="d-flex flex-column">
                    <label htmlFor="productId" className="text-center">Producto:</label>
                    <select
                        className="col-6 mx-auto"
                        id="productId"
                        value={productId}
                        onChange={(e) => setProductId(e.target.value)}
                        required
                    >
                        <option value="">Selecciona un producto:</option>
                        {products.map((product) => (
                            <option key={product.id} value={product.id}>
                                {product.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="d-flex flex-column">
                    <label htmlFor="quantity" className="text-center">Cantidad:</label>
                    <input
                        className="col-6 mx-auto"
                        type="quantity"
                        id="number"
                        value={quantity}
                        onChange={(e) => setQuantity(e.target.value)}
                        required
                    />
                </div>
                <div className="d-flex flex-column">
                    <label htmlFor="subtotal" className="text-center">Subtotal:</label>
                    <input
                        type="number"
                        className="col-6 mx-auto"
                        id="subtotal"
                        value={subtotal}
                        onChange={(e) => setSubtotal(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="btn btn-new mx-auto mt-5 mb-5">Actualizar Detale</button>
            </form>
        </div>
    )
}

export default EditOrderDetailForm;