import axios from 'axios';

const API_BASE = 'http://localhost:8080/students';

export const studentService = {
  // GET /students/all - Récupérer tous les étudiants
  getAll: async () => {
    const res = await axios.get(`${API_BASE}/all`);
    return res.data;
  },

  // GET /students/count - Compter le nombre total d'étudiants
  getCount: async () => {
    const res = await axios.get(`${API_BASE}/count`);
    return res.data;
  },

  // GET /students/byYear - Obtenir la répartition par année de naissance
  getByYear: async () => {
    const res = await axios.get(`${API_BASE}/byYear`);
    return res.data;
  },

  // POST /students/save - Créer ou modifier un étudiant
  save: async (student) => {
    const res = await axios.post(`${API_BASE}/save`, student);
    return res.data;
  },

  // DELETE /students/delete/{id} - Supprimer un étudiant par son ID
  delete: async (id) => {
    const res = await axios.delete(`${API_BASE}/delete/${id}`);
    return res.data;
  }
};
