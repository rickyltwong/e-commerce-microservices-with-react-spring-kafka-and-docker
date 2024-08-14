const isDevelopment = import.meta.env.MODE === 'development';

export const baseURL = isDevelopment
  ? 'http://localhost:5173'
  : 'http://gateway-service:8080';
