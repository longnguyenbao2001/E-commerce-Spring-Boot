import axios from 'axios';

const endpoints = {
    'signin': '/auth/signin',
    'signup': '/auth/signup',
    'listproduct': '/products',
    'productDetail': (productId) => `/products/${productId}`,
}

const userApi = axios.create({
    baseURL: 'http://localhost:8081'
});

const productApi = axios.create({
    baseURL: 'http://localhost:8082'
});

export {
    endpoints,
    userApi,
    productApi,
};
