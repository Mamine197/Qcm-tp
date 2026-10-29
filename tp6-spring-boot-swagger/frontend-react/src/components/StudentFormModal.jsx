import React, { useState, useEffect } from 'react';

export default function StudentFormModal({ isOpen, onClose, onSave, studentToEdit }) {
  const [nom, setNom] = useState('');
  const [prenom, setPrenom] = useState('');
  const [dateNaissance, setDateNaissance] = useState('');

  useEffect(() => {
    if (studentToEdit) {
      setNom(studentToEdit.nom || '');
      setPrenom(studentToEdit.prenom || '');
      setDateNaissance(studentToEdit.dateNaissance ? studentToEdit.dateNaissance.split('T')[0] : '');
    } else {
      setNom('');
      setPrenom('');
      setDateNaissance('');
    }
  }, [studentToEdit, isOpen]);

  if (!isOpen) return null;

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!nom.trim() || !prenom.trim() || !dateNaissance) return;

    onSave({
      ...(studentToEdit && studentToEdit.id ? { id: studentToEdit.id } : {}),
      nom: nom.trim(),
      prenom: prenom.trim(),
      dateNaissance
    });
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-slate-900/50 backdrop-blur-sm p-4">
      <div className="bg-white rounded-2xl max-w-md w-full p-6 shadow-2xl border border-slate-100">
        <div className="flex items-center justify-between pb-4 border-b border-slate-100 mb-5">
          <h3 className="text-lg font-bold text-slate-900">
            {studentToEdit ? `Modifier l'étudiant #${studentToEdit.id}` : 'Ajouter un étudiant'}
          </h3>
          <button onClick={onClose} className="text-slate-400 hover:text-slate-600 text-xl font-bold">
            &times;
          </button>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-xs font-bold text-slate-700 uppercase mb-1">Nom</label>
            <input
              type="text"
              required
              value={nom}
              onChange={(e) => setNom(e.target.value)}
              placeholder="Ex: LACHGAR"
              className="w-full px-3.5 py-2 text-sm border border-slate-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-xs font-bold text-slate-700 uppercase mb-1">Prénom</label>
            <input
              type="text"
              required
              value={prenom}
              onChange={(e) => setPrenom(e.target.value)}
              placeholder="Ex: Mohamed"
              className="w-full px-3.5 py-2 text-sm border border-slate-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-xs font-bold text-slate-700 uppercase mb-1">Date de Naissance</label>
            <input
              type="date"
              required
              value={dateNaissance}
              onChange={(e) => setDateNaissance(e.target.value)}
              className="w-full px-3.5 py-2 text-sm border border-slate-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div className="pt-4 flex items-center justify-end gap-3 border-t border-slate-100 mt-6">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 text-xs font-semibold text-slate-600 hover:bg-slate-100 rounded-xl transition-colors"
            >
              Annuler
            </button>
            <button
              type="submit"
              className="px-5 py-2 text-xs font-semibold text-white bg-blue-600 hover:bg-blue-700 rounded-xl transition-colors shadow-sm shadow-blue-500/20"
            >
              {studentToEdit ? 'Enregistrer les modifications' : 'Créer l\'étudiant'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
