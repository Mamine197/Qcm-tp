import React, { useState, useEffect } from 'react';
import { studentService } from './services/studentService';
import StudentTable from './components/StudentTable';
import StudentChart from './components/StudentChart';
import StudentFormModal from './components/StudentFormModal';

export default function App() {
  const [students, setStudents] = useState([]);
  const [count, setCount] = useState(0);
  const [byYear, setByYear] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState('');
  const [modalOpen, setModalOpen] = useState(false);
  const [studentToEdit, setStudentToEdit] = useState(null);
  const [toast, setToast] = useState(null);

  const showToast = (message, type = 'success') => {
    setToast({ message, type });
    setTimeout(() => setToast(null), 3500);
  };

  const loadData = async () => {
    setLoading(true);
    try {
      const [allData, countData, byYearData] = await Promise.all([
        studentService.getAll(),
        studentService.getCount(),
        studentService.getByYear()
      ]);
      setStudents(allData);
      setCount(countData);
      setByYear(byYearData);
    } catch (err) {
      console.error(err);
      showToast('Erreur lors du chargement des données API', 'error');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleSave = async (studentPayload) => {
    try {
      await studentService.save(studentPayload);
      showToast(studentPayload.id ? 'Étudiant modifié !' : 'Étudiant ajouté !', 'success');
      setModalOpen(false);
      setStudentToEdit(null);
      loadData();
    } catch (err) {
      showToast("Erreur lors de l'enregistrement", 'error');
    }
  };

  const handleDelete = async (id, name) => {
    if (!window.confirm(`Supprimer définitivement l'étudiant ${name} (#${id}) ?`)) return;
    try {
      await studentService.delete(id);
      showToast(`Étudiant #${id} supprimé avec succès`, 'success');
      loadData();
    } catch (err) {
      showToast('Erreur lors de la suppression', 'error');
    }
  };

  const filtered = students.filter(s => {
    const q = search.toLowerCase();
    return (
      (s.nom && s.nom.toLowerCase().includes(q)) ||
      (s.prenom && s.prenom.toLowerCase().includes(q)) ||
      (s.id && s.id.toString().includes(q))
    );
  });

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 min-h-screen bg-slate-50">
      {/* Toast */}
      {toast && (
        <div className={`fixed top-5 right-5 z-50 px-4 py-3 rounded-xl shadow-lg border ${
          toast.type === 'success' ? 'bg-emerald-50 text-emerald-800 border-emerald-200' : 'bg-rose-50 text-rose-800 border-rose-200'
        }`}>
          {toast.message}
        </div>
      )}

      {/* Header */}
      <header className="flex flex-col md:flex-row md:items-center justify-between pb-8 mb-8 border-b border-slate-200 gap-4">
        <div>
          <h1 className="text-2xl font-black text-slate-900 tracking-tight">Student Management — React Client</h1>
          <p className="text-xs text-slate-500 font-medium">Connecté à l'API Spring Boot & Swagger (port 8080)</p>
        </div>
        <div className="flex items-center gap-3">
          <a
            href="http://localhost:8080/swagger-ui.html"
            target="_blank"
            rel="noreferrer"
            className="px-3.5 py-2 bg-slate-900 hover:bg-slate-800 text-white text-xs font-semibold rounded-lg shadow-sm"
          >
            Ouvrir Swagger UI ↗
          </a>
        </div>
      </header>

      {/* KPI Stats */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
        <div className="bg-white p-6 rounded-2xl border border-slate-200/80 shadow-sm">
          <p className="text-xs font-bold text-slate-400 uppercase">Total Étudiants (GET /students/count)</p>
          <p className="text-4xl font-black text-slate-900 mt-2">{count}</p>
        </div>
        <div className="bg-white p-6 rounded-2xl border border-slate-200/80 shadow-sm">
          <p className="text-xs font-bold text-slate-400 uppercase">Nombre d'années distinctes</p>
          <p className="text-4xl font-black text-blue-600 mt-2">{byYear.length}</p>
        </div>
      </div>

      {/* Chart Section */}
      <div className="bg-white p-6 rounded-2xl border border-slate-200/80 shadow-sm mb-8">
        <h2 className="text-base font-bold text-slate-900 mb-1">Distribution par Année (GET /students/byYear)</h2>
        <p className="text-xs text-slate-500 mb-4">Statistiques agrégées côté backend via Spring Data JPA</p>
        <StudentChart byYearData={byYear} />
      </div>

      {/* Table Section */}
      <div className="bg-white rounded-2xl border border-slate-200/80 shadow-sm overflow-hidden">
        <div className="p-6 border-b border-slate-200 flex flex-col sm:flex-row sm:items-center justify-between gap-4">
          <div className="flex items-center gap-2">
            <h2 className="text-lg font-bold text-slate-900">Liste des Étudiants (GET /students/all)</h2>
            <span className="text-xs font-semibold bg-slate-100 text-slate-700 px-2 py-0.5 rounded-full">{filtered.length}</span>
          </div>

          <div className="flex items-center gap-3">
            <input
              type="text"
              placeholder="Filtrer..."
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              className="px-3.5 py-2 border border-slate-200 rounded-xl text-xs focus:ring-2 focus:ring-blue-500"
            />
            <button
              onClick={() => { setStudentToEdit(null); setModalOpen(true); }}
              className="px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white text-xs font-semibold rounded-xl shadow-sm"
            >
              + Nouveau
            </button>
          </div>
        </div>

        <StudentTable
          students={filtered}
          onEdit={(st) => { setStudentToEdit(st); setModalOpen(true); }}
          onDelete={handleDelete}
          loading={loading}
        />
      </div>

      <StudentFormModal
        isOpen={modalOpen}
        onClose={() => { setModalOpen(false); setStudentToEdit(null); }}
        onSave={handleSave}
        studentToEdit={studentToEdit}
      />
    </div>
  );
}
