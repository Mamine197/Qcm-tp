import React from 'react';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
} from 'chart.js';
import { Bar } from 'react-chartjs-2';

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

export default function StudentChart({ byYearData }) {
  if (!byYearData || byYearData.length === 0) {
    return (
      <div className="h-64 flex items-center justify-center text-slate-400 text-sm">
        Aucune statistique disponible pour l'instant.
      </div>
    );
  }

  const labels = byYearData.map(item => Array.isArray(item) ? `Année ${item[0]}` : `Année ${item.year}`);
  const counts = byYearData.map(item => Array.isArray(item) ? item[1] : item.count);

  const data = {
    labels,
    datasets: [
      {
        label: "Nombre d'étudiants",
        data: counts,
        backgroundColor: 'rgba(59, 130, 246, 0.75)',
        borderColor: 'rgb(37, 99, 235)',
        borderWidth: 1.5,
        borderRadius: 8
      }
    ]
  };

  const options = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: { display: false }
    },
    scales: {
      y: {
        beginAtZero: true,
        ticks: { stepSize: 1, precision: 0 }
      }
    }
  };

  return (
    <div className="h-64 w-full">
      <Bar data={data} options={options} />
    </div>
  );
}
