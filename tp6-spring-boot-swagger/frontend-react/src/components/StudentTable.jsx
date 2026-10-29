import React from 'react';

export default function StudentTable({ students, onEdit, onDelete, loading }) {
  if (loading) {
    return (
      <div className="py-12 text-center text-slate-400">
        <div className="inline-block animate-spin rounded-full h-6 w-6 border-b-2 border-blue-600 mb-2"></div>
        <p className="text-xs">Chargement des étudiants...</p>
      </div>
    );
  }

  if (students.length === 0) {
    return (
      <div className="py-12 text-center text-slate-400">
        <p className="text-sm font-medium">Aucun étudiant dans la base de données.</p>
        <p className="text-xs mt-1">Utilisez le bouton « Ajouter un étudiant » pour en créer un nouveau.</p>
      </div>
    );
  }

  return (
    <div className="overflow-x-auto">
      <table className="w-full text-left border-collapse">
        <thead>
          <tr className="bg-slate-50 text-slate-500 text-[11px] font-bold uppercase tracking-wider border-b border-slate-200">
            <th className="py-3.5 px-6">ID</th>
            <th className="py-3.5 px-6">Étudiant</th>
            <th className="py-3.5 px-6">Date de Naissance</th>
            <th className="py-3.5 px-6">Année</th>
            <th className="py-3.5 px-6 text-right">Actions</th>
          </tr>
        </thead>
        <tbody className="divide-y divide-slate-100 text-sm">
          {students.map((st) => {
            const formattedDate = st.dateNaissance ? st.dateNaissance.split('T')[0] : 'N/A';
            const year = st.dateNaissance ? new Date(st.dateNaissance).getFullYear() : 'N/A';
            return (
              <tr key={st.id} className="hover:bg-slate-50/80 transition-colors">
                <td className="py-4 px-6 font-mono text-xs text-slate-400">#{st.id}</td>
                <td className="py-4 px-6">
                  <div className="flex items-center gap-3">
                    <div className="h-8 w-8 rounded-full bg-gradient-to-tr from-blue-500 to-indigo-500 text-white flex items-center justify-center font-bold text-xs uppercase shadow-sm">
                      {st.prenom ? st.prenom[0] : ''}{st.nom ? st.nom[0] : ''}
                    </div>
                    <div>
                      <p className="font-semibold text-slate-900">{st.prenom} {st.nom}</p>
                      <p className="text-[11px] text-slate-400">ID #{st.id}</p>
                    </div>
                  </div>
                </td>
                <td className="py-4 px-6 text-slate-600 font-medium text-xs">
                  {formattedDate}
                </td>
                <td className="py-4 px-6">
                  <span className="inline-block px-2.5 py-0.5 rounded-full text-[11px] font-semibold bg-blue-50 text-blue-700 border border-blue-200">
                    {year}
                  </span>
                </td>
                <td className="py-4 px-6 text-right">
                  <div className="inline-flex items-center gap-2">
                    <button
                      onClick={() => onEdit(st)}
                      className="px-2.5 py-1 text-xs font-semibold text-blue-600 hover:text-blue-800 hover:bg-blue-50 rounded-lg transition-colors"
                    >
                      Modifier
                    </button>
                    <button
                      onClick={() => onDelete(st.id, `${st.prenom} ${st.nom}`)}
                      className="px-2.5 py-1 text-xs font-semibold text-rose-600 hover:text-rose-800 hover:bg-rose-50 rounded-lg transition-colors"
                    >
                      Supprimer
                    </button>
                  </div>
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
}
