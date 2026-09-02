import React, { useEffect, useMemo, useState } from 'react';
import ChamadoCard from '../../components/ChamadoCard';
import { atendimentoApi, chamadoApi } from '../../services/api';
import './style.css';

export default function Chamados() {
  const [chamados, setChamados] = useState([]);
  const [atendimentos, setAtendimentos] = useState([]);
  const [busca, setBusca] = useState('');
  const [status, setStatus] = useState('TODOS');
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
  const filtrados = chamados.filter((chamado) => {
    const texto = `${chamado.id} ${chamado.tituloChamado} ${chamado.descricaoChamado}`.toLowerCase();
    const correspondeBusca = texto.includes(busca.toLowerCase());
    const correspondeStatus = status === 'TODOS' || porChamado[chamado.id]?.status === status;
    return correspondeBusca && correspondeStatus;
  });

  return (
    <section>
      <h1 className="page-title">Chamados</h1>
      <p className="page-subtitle">Consulta, triagem e acompanhamento do ciclo completo.</p>
      {erro && <div className="error-box">{erro}</div>}

      <div className="chamados__filters">
        <input value={busca} onChange={(event) => setBusca(event.target.value)} placeholder="Buscar por ID, título ou descrição" />
        <select value={status} onChange={(event) => setStatus(event.target.value)}>
          <option value="TODOS">Todos os status</option>
          <option value="ABERTO">Aberto</option>
          <option value="EM_TRIAGEM">Em triagem</option>
          <option value="EM_ATENDIMENTO">Em atendimento</option>
          <option value="PENDENTE_EVIDENCIA">Pendente evidência</option>
          <option value="RESOLVIDO">Resolvido</option>
          <option value="FECHADO">Fechado</option>
        </select>
      </div>

      <div className="chamados__grid">
        {filtrados.map((chamado) => (
          <ChamadoCard key={chamado.id} chamado={chamado} atendimento={porChamado[chamado.id]} />
        ))}
      </div>
    </section>
  );
}
