export const API_URL = import.meta.env.VITE_API_URL || '/api/v1';

export const ENDPOINTS = {
    books: `${API_URL}/books`,
    authors: `${API_URL}/authors`,
    series: `${API_URL}/series`,
    genres: `${API_URL}/genres`,
    tools:    `${API_URL}/tools`,
    //brands: `${API_URL}/brands`,
    users:    `${API_URL}/users`,
    places: `${API_URL}/places`,
    pdfbooks: `${API_URL}/pdfbooks`,
    authorsByGenre: (id) => `${API_URL}/genres/${id}/authors`,
};