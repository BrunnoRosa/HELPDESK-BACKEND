import React, { useEffect, useMemo, useState } from 'react';
import ChamadoCard from '../../components/ChamadoCard';
import { atendimentoApi, chamadoApi } from '../../services/api';
import './style.css';

export default function Dashboard() {
  const [chamados, setChamados] = useState([]);
  const [atendimentos, setAtendimentos] = useState([]);
  const [erro, setErro] = useState('');

  useEffect(() => {
    Promise.all([chamadoApi.listar(), atendimentoApi.listar()])
      .then(([listaChamados, listaAtendimentos]) => {
        setChamados(listaChamados);
        setAtendimentos(listaAtendimentos);
      })
      .catch((error) => setErro(error.message));
  }, []);

  const porChamado = useMemo(() => Object.fromEntries(atendimentos.map((item) => [item.chamadoId, item])), [atendimentos]);
  const totalAbertos = atendimentos.filter((item) => !['RESOLVIDO', 'FECHADO'].includes(item.status)).length;
  const urgentes = chamados.filter((item) => item.prioridadeChamado === 'URGENTE').length;
  const resolvidos = atendimentos.filter((item) => item.status === 'RESOLVIDO').length;

  return (
    <section>
      <h1 className="page-title">Painel operacional</h1>
      <p className="page-subtitle">Visão consolidada do atendimento técnico N1, N2 e N3.</p>
      {erro && <div className="error-box">{erro}</div>}

      <div className="dashboard__stats">
        <article><strong>{chamados.length}</strong><span>Total de chamados</span></article>
        <article><strong>{totalAbertos}</strong><span>Em fluxo</span></article>
        <article><strong>{urgentes}</strong><span>Urgentes</span></article>
        <article><strong>{resolvidos}</strong><span>Resolvidos</span></article>
      </div>

      <h2>Chamados recentes</h2>
      <div className="dashboard__grid">
        {chamados.slice(-6).reverse().map((chamado) => (
          <ChamadoCard key={chamado.id} chamado={chamado} atendimento={porChamado[chamado.id]} />
        ))}
      </div>
    </section>
  );
}
