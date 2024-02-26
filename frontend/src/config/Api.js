import axios from 'axios';

const endpoints = {
    'signin': '/auth/signin',
    // 'route-detail': (routeId) => `/routes/${routeId}/`,
}

const userApi = axios.create({
    baseURL: 'http://localhost:8081'
});

export {
    endpoints,
    userApi,
};
