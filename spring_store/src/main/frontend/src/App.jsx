import React, { useEffect, useState } from 'react';
import './App.css';
import Layout from './Layout';
import CategoryList from './CategoryList';
import NewCategoryForm from './NewCategory';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import EditCategoryForm from './EditCategory';
import CategoryProducts from './CategoryPRoducts';
import NewProductForm from './NewProduct';
import EditProductForm from './EditProduct';
import ClientList from './ClientList';
import NewClientForm from './NewClient';
import EditClientForm from './EditClient';
import ProvidersList from './ProviderList';
import NewProviderForm from './NewProvider';
import EditProviderForm from './EditProvider';
import OrdersList from './OrdersList';
import NewOrderForm from './NewOrder';
import EditOrderForm from './EditOrder';
import OrderDetailsList from './OrderDetailsView';
import NewOrderDetailForm from './NewOrderDetail';
import EditOrderDetailForm from './EditOrderDetail';

function App() {
  return (
    <Routes>
      <Route path="/" element={<Layout />} />
      <Route path="/categories" element={<Layout ><CategoryList /></Layout>} />
      <Route path="/categories/new" element={<Layout ><NewCategoryForm /></Layout>} />
      <Route path="/categories/edit/:id" element={<Layout ><EditCategoryForm /> </Layout>} />
      <Route path="/categories/view/:id" element={<Layout ><CategoryProducts /> </Layout>} />
      <Route path="/products/new" element={<Layout ><NewProductForm /> </Layout>} />
      <Route path='/products/edit/:id' element={<Layout><EditProductForm /> </Layout>} />
      <Route path='/clients' element={<Layout><ClientList/> </Layout>} />
      <Route path='/clients/new' element={<Layout><NewClientForm/> </Layout>} />
      <Route path='/clients/edit/:id' element={<Layout><EditClientForm/> </Layout>} />
      <Route path='/providers' element={<Layout><ProvidersList /> </Layout>} />
      <Route path='/providers/new' element={<Layout><NewProviderForm /> </Layout>} />
      <Route path='/providers/edit/:id' element={<Layout><EditProviderForm /> </Layout>} />
      <Route path='/orders' element={<Layout><OrdersList /></Layout>} />
      <Route path='/orders/new' element={<Layout><NewOrderForm /></Layout>} />
      <Route path='/orders/edit/:id' element={<Layout><EditOrderForm /></Layout>} />
      <Route path='/orders/view/:id' element={<Layout><OrderDetailsList /></Layout>} />
      <Route path='/orderdetails/new' element={<Layout><NewOrderDetailForm /></Layout>} />
      <Route path='/orderdetails/edit/:id' element={<Layout><EditOrderDetailForm /></Layout>} />
    </Routes>
  );
}

export default App;

