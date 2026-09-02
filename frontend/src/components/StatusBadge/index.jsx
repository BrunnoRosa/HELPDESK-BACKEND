import React from 'react';
import './style.css';

const LABELS = {
  ABERTO: 'Aberto',
  EM_TRIAGEM: 'Em triagem',
  EM_ATENDIMENTO: 'Em atendimento',
  PENDENTE_EVIDENCIA: 'Pendente evidência',
  RESOLVIDO: 'Resolvido',
  FECHADO: 'Fechado'
};

export default function StatusBadge({ status }) {
  if (!status) return null;
  return <span className={`status-badge status-badge--${status.toLowerCase()}`}>{LABELS[status] || status}</span>;
}
