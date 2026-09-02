import React from 'react';
import { Link } from 'react-router-dom';
import StatusBadge from '../StatusBadge';
import './style.css';

export default function ChamadoCard({ chamado, atendimento }) {
  return (
    <article className="chamado-card">
      <div className="chamado-card__top">
        <span className="chamado-card__id">#{chamado.id}</span>
        <StatusBadge status={atendimento?.status} />
      </div>
      <h3>{chamado.tituloChamado}</h3>
      <p className="chamado-card__desc">{chamado.descricaoChamado}</p>
      <div className="chamado-card__meta">
        <span>{chamado.ocorrenciaChamado}</span>
        <span>{chamado.prioridadeChamado}</span>
        <span>{atendimento?.nivelSuporte || 'N1'}</span>
      </div>
      <Link className="chamado-card__link" to={`/chamados/${chamado.id}`}>Abrir chamado</Link>
    </article>
  );
}
