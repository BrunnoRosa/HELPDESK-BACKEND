import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import StatusBadge from '../../components/StatusBadge';
import { useAuth } from '../../context/AuthContext';
import { adminApi, atendimentoApi, chamadoApi } from '../../services/api';
import './style.css';

const NEXT_STATUS = {
  ABERTO: ['EM_TRIAGEM'],
  EM_TRIAGEM: ['EM_ATENDIMENTO'],
  EM_ATENDIMENTO: ['PENDENTE_EVIDENCIA', 'RESOLVIDO'],
  PENDENTE_EVIDENCIA: ['EM_ATENDIMENTO', 'RESOLVIDO'],
  RESOLVIDO: ['FECHADO', 'EM_ATENDIMENTO'],
  FECHADO: []
};

export default function ChamadoDetalhe() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { usuario } = useAuth();
  const podeAtender = ['TECNICO', 'ADMINISTRADOR'].includes(usuario?.perfil);
  const admin = usuario?.perfil === 'ADMINISTRADOR';

  const [chamado, setChamado] = useState(null);
  const [atendimento, setAtendimento] = useState(null);
  const [tecnicos, setTecnicos] = useState([]);
  const [descricaoAtualizacao, setDescricaoAtualizacao] = useState('');
  const [statusDestino, setStatusDestino] = useState('');
  const [nivelSuporte, setNivelSuporte] = useState('N1');
  const [usuarioVinculado, setUsuarioVinculado] = useState('');
  const [equipamentoVinculado, setEquipamentoVinculado] = useState('');
  const [tecnicoResponsavelId, setTecnicoResponsavelId] = useState('');
  const [erro, setErro] = useState('');
  const [mensagem, setMensagem] = useState('');

  async function carregar() {
    try {
      const [dadosChamado, dadosAtendimento] = await Promise.all([
        chamadoApi.buscar(id),
        atendimentoApi.buscarPorChamado(id)
      ]);

      setChamado(dadosChamado);
      setAtendimento(dadosAtendimento);
      setNivelSuporte(dadosAtendimento.nivelSuporte);
      setUsuarioVinculado(dadosAtendimento.usuarioVinculado || '');
      setEquipamentoVinculado(dadosAtendimento.equipamentoVinculado || '');
      setTecnicoResponsavelId(dadosAtendimento.tecnicoResponsavelId || '');

      const proximos = NEXT_STATUS[dadosAtendimento.status] || [];
      setStatusDestino(proximos[0] || dadosAtendimento.status);

      if (admin) {
        const listaTecnicos = await adminApi.listarUsuarios();
        setTecnicos(listaTecnicos.filter(item => ['TECNICO', 'ADMINISTRADOR'].includes(item.perfil)));
      }
    } catch (error) {
      setErro(error.message);
    }
  }

  useEffect(() => {
    carregar();
  }, [id, admin]);

  async function salvarAtualizacao(event) {
    event.preventDefault();
    if (!descricaoAtualizacao.trim()) return;
    setErro('');
    setMensagem('');

    try {
      await chamadoApi.atualizar(id, {
        id: Number(id),
        tituloChamado: chamado.tituloChamado,
        ocorrenciaChamado: chamado.ocorrenciaChamado,
        descricaoChamado: descricaoAtualizacao,
        prioridadeChamado: chamado.prioridadeChamado
      });
      setDescricaoAtualizacao('');
      setMensagem('Histórico atualizado com sucesso.');
      await carregar();
    } catch (error) {
      setErro(error.message);
    }
  }

  async function moverFluxo(event) {
    event.preventDefault();
    setErro('');
    setMensagem('');

    try {
      await atendimentoApi.atualizar({
        chamadoId: Number(id),
        status: statusDestino,
        nivelSuporte,
        usuarioVinculado,
        equipamentoVinculado,
        tecnicoResponsavelId: tecnicoResponsavelId ? Number(tecnicoResponsavelId) : null
      });
      setMensagem('Fluxo de atendimento atualizado.');
      await carregar();
    } catch (error) {
      setErro(error.message);
    }
  }

  async function deletar() {
    if (!window.confirm('Deseja realmente deletar este chamado?')) return;
    try {
      await chamadoApi.deletar(id);
      navigate('/chamados');
    } catch (error) {
      setErro(error.message);
    }
  }

  if (!chamado || !atendimento) {
    return <section>{erro ? <div className="error-box">{erro}</div> : <p>Carregando...</p>}</section>;
  }

  const proximos = NEXT_STATUS[atendimento.status] || [];

  return (
    <section>
      <div className="detalhe__heading">
        <div>
          <span className="detalhe__id">Chamado #{chamado.id}</span>
          <h1 className="page-title">{chamado.tituloChamado}</h1>
        </div>
        <StatusBadge status={atendimento.status} />
      </div>

      {erro && <div className="error-box">{erro}</div>}
      {mensagem && <div className="success-box">{mensagem}</div>}

      <div className="detalhe__layout">
        <article className="detalhe__panel">
          <h2>Dados do chamado</h2>
          <dl className="detalhe__data">
            <div><dt>Ocorrência</dt><dd>{chamado.ocorrenciaChamado}</dd></div>
            <div><dt>Prioridade</dt><dd>{chamado.prioridadeChamado}</dd></div>
            <div><dt>Nível</dt><dd>{atendimento.nivelSuporte}</dd></div>
            <div><dt>Solicitante</dt><dd>{atendimento.solicitanteNome}</dd></div>
            <div><dt>Usuário vinculado</dt><dd>{atendimento.usuarioVinculado || 'Não vinculado'}</dd></div>
            <div><dt>Equipamento</dt><dd>{atendimento.equipamentoVinculado || 'Não vinculado'}</dd></div>
            <div><dt>Técnico responsável</dt><dd>{atendimento.tecnicoResponsavelNome || 'Não atribuído'}</dd></div>
          </dl>

          <h3>Histórico / descrição</h3>
          <pre className="detalhe__history">{chamado.descricaoChamado}</pre>
        </article>

        <aside className="detalhe__side">
          {!podeAtender && (
            <div className="detalhe__panel detalhe__readonly">
              <h2>Acompanhamento</h2>
              <p>Seu perfil é de solicitante. Você pode acompanhar o chamado, mas as ações técnicas são restritas à equipe de suporte.</p>
            </div>
          )}

          {podeAtender && (
            <>
              <form className="detalhe__panel" onSubmit={salvarAtualizacao}>
                <h2>Adicionar atualização</h2>
                <textarea rows="6" value={descricaoAtualizacao} onChange={event => setDescricaoAtualizacao(event.target.value)} placeholder="Descreva diagnóstico, evidência ou solução" />
                <button className="primary-button" type="submit">Registrar no histórico</button>
              </form>

              <form className="detalhe__panel" onSubmit={moverFluxo}>
                <h2>Fluxo de suporte</h2>

                <label>
                  Status destino
                  <select value={statusDestino} onChange={event => setStatusDestino(event.target.value)} disabled={proximos.length === 0}>
                    {proximos.length === 0
                      ? <option value={atendimento.status}>{atendimento.status}</option>
                      : proximos.map(item => <option key={item} value={item}>{item}</option>)}
                  </select>
                </label>

                <label>
                  Nível
                  <select value={nivelSuporte} onChange={event => setNivelSuporte(event.target.value)}>
                    <option value="N1">N1 - Triagem</option>
                    <option value="N2">N2 - Especializado</option>
                    <option value="N3">N3 - Sênior</option>
                  </select>
                </label>

                <label>
                  Usuário vinculado
                  <input value={usuarioVinculado} onChange={event => setUsuarioVinculado(event.target.value)} />
                </label>

                <label>
                  Equipamento vinculado
                  <input value={equipamentoVinculado} onChange={event => setEquipamentoVinculado(event.target.value)} />
                </label>

                {admin && (
                  <label>
                    Técnico responsável
                    <select value={tecnicoResponsavelId} onChange={event => setTecnicoResponsavelId(event.target.value)}>
                      <option value="">Sem técnico atribuído</option>
                      {tecnicos.map(tecnico => <option key={tecnico.id} value={tecnico.id}>{tecnico.nome} - {tecnico.perfil}</option>)}
                    </select>
                  </label>
                )}

                <button className="primary-button" type="submit" disabled={proximos.length === 0}>Atualizar fluxo</button>
              </form>

              {admin && <button className="danger-button detalhe__delete" onClick={deletar}>Deletar chamado</button>}
            </>
          )}
        </aside>
      </div>
    </section>
  );
}
