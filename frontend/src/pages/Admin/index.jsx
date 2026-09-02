import React, { useEffect, useState } from 'react';
import { adminApi } from '../../services/api';
import './style.css';

const PERFIS = ['USUARIO', 'TECNICO', 'ADMINISTRADOR'];

export default function Admin() {
  const [usuarios, setUsuarios] = useState([]);
  const [resumo, setResumo] = useState(null);
  const [erro, setErro] = useState('');
  const [mensagem, setMensagem] = useState('');

  async function carregar() {
    try {
      const [lista, dadosResumo] = await Promise.all([
        adminApi.listarUsuarios(),
        adminApi.resumo()
      ]);
      setUsuarios(lista);
      setResumo(dadosResumo);
    } catch (error) {
      setErro(error.message);
    }
  }

  useEffect(() => {
    carregar();
  }, []);

  async function alterarPerfil(id, perfil) {
    setErro('');
    try {
      await adminApi.atualizarPerfil(id, perfil);
      setMensagem('Perfil atualizado com sucesso.');
      await carregar();
    } catch (error) {
      setErro(error.message);
    }
  }

  async function excluir(id) {
    if (!window.confirm('Deseja excluir este usuário?')) return;
    setErro('');
    try {
      await adminApi.deletarUsuario(id);
      setMensagem('Usuário removido.');
      await carregar();
    } catch (error) {
      setErro(error.message);
    }
  }

  return (
    <section>
      <h1 className="page-title">Administração</h1>
      <p className="page-subtitle">Gestão global de usuários e visão resumida dos chamados.</p>

      {erro && <div className="error-box">{erro}</div>}
      {mensagem && <div className="success-box">{mensagem}</div>}

      {resumo && (
        <div className="admin__stats">
          <article><strong>{resumo.totalUsuarios}</strong><span>Usuários</span></article>
          <article><strong>{resumo.totalChamados}</strong><span>Chamados</span></article>
          <article><strong>{resumo.porStatus?.EM_ATENDIMENTO || 0}</strong><span>Em atendimento</span></article>
          <article><strong>{resumo.porStatus?.RESOLVIDO || 0}</strong><span>Resolvidos</span></article>
        </div>
      )}

      <div className="admin__table-wrap">
        <table className="admin__table">
          <thead>
            <tr>
              <th>ID</th><th>Nome</th><th>E-mail</th><th>Perfil</th><th>Ações</th>
            </tr>
          </thead>
          <tbody>
            {usuarios.map(usuario => (
              <tr key={usuario.id}>
                <td>{usuario.id}</td>
                <td>{usuario.nome}</td>
                <td>{usuario.email}</td>
                <td>
                  <select value={usuario.perfil} onChange={event => alterarPerfil(usuario.id, event.target.value)}>
                    {PERFIS.map(perfil => <option key={perfil} value={perfil}>{perfil}</option>)}
                  </select>
                </td>
                <td>
                  <button className="admin__delete" type="button" onClick={() => excluir(usuario.id)}>Excluir</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </section>
  );
}
