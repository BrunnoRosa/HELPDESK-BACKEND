import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { chamadoApi } from '../../services/api';
import './style.css';

const initialForm = {
  tituloChamado: '',
  ocorrenciaChamado: 'INCIDENTE',
  descricaoChamado: '',
  prioridadeChamado: 'MEDIA'
};

export default function NovoChamado() {
  const [form, setForm] = useState(initialForm);
  const [erro, setErro] = useState('');
  const [salvando, setSalvando] = useState(false);
  const navigate = useNavigate();

  function handleChange(event) {
    const { name, value } = event.target;
    setForm((current) => ({ ...current, [name]: value }));
  }

  async function handleSubmit(event) {
    event.preventDefault();
    setErro('');
    setSalvando(true);
    try {
      await chamadoApi.criar(form);
      navigate('/chamados');
    } catch (error) {
      setErro(error.message);
    } finally {
      setSalvando(false);
    }
  }

  return (
    <section className="novo-chamado">
      <h1 className="page-title">Novo chamado</h1>
      <p className="page-subtitle">Abertura inicial com classificação de ocorrência e criticidade.</p>
      {erro && <div className="error-box">{erro}</div>}

      <form className="novo-chamado__form" onSubmit={handleSubmit}>
        <label>
          Título
          <input name="tituloChamado" value={form.tituloChamado} onChange={handleChange} required />
        </label>
        <label>
          Ocorrência
          <select name="ocorrenciaChamado" value={form.ocorrenciaChamado} onChange={handleChange}>
            <option value="INCIDENTE">Incidente</option>
            <option value="REQUISICAO">Requisição</option>
            <option value="PROBLEMA">Problema</option>
            <option value="DUVIDA">Dúvida</option>
          </select>
        </label>
        <label>
          Prioridade
          <select name="prioridadeChamado" value={form.prioridadeChamado} onChange={handleChange}>
            <option value="URGENTE">Urgente</option>
            <option value="ALTA">Alta</option>
            <option value="MEDIA">Média</option>
            <option value="BAIXA">Baixa</option>
          </select>
        </label>
        <label className="novo-chamado__full">
          Descrição
          <textarea name="descricaoChamado" value={form.descricaoChamado} onChange={handleChange} rows="8" required />
        </label>
        <div className="novo-chamado__actions">
          <button type="button" className="secondary-button" onClick={() => navigate('/chamados')}>Cancelar</button>
          <button type="submit" className="primary-button" disabled={salvando}>{salvando ? 'Salvando...' : 'Abrir chamado'}</button>
        </div>
      </form>
    </section>
  );
}
