import axios from 'axios';

// URL base del backend
const API_URL = "https://proyecto-hotel-casa-azul-production.up.railway.app";

export const api = axios.create({
  baseURL: API_URL,
  headers: {
    "Content-Type": "application/json"
  }
});