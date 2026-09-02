import React, { useState } from 'react';
import { Link, Navigate, useNavigate } from 'react-router-dom';
import { authApi } from '../../services/api';
import { useAuth } from '../../context/AuthContext';
import './style.css';

export default function Register() {
  const { autenticado } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState({
    nome: '',
    email: '',
    senha: '',
    confirmarSenha: '',
    perfil: 'USUARIO'
  });
  const [erro, setErro] = useState('');
  const [carregando, setCarregando] = useState(false);

  if (autenticado) {
    return <Navigate to="/" replace />;
  }

  function handleChange(event) {
    setForm(current => ({ ...current, [event.target.name]: event.target.value }));
  }

  async function handleSubmit(event) {
    event.preventDefault();
    setErro('');

    if (form.senha !== form.confirmarSenha) {
      setErro('As senhas não coincidem ❌');
      return;
    }

    setCarregando(true);

    try {
      await authApi.register({
        nome: form.nome,
        email: form.email,
        senha: form.senha,
        perfil: form.perfil
      });
      navigate('/login', { replace: true, state: { cadastroConcluido: true } });
    } catch (error) {
      setErro(error.message);
    } finally {
      setCarregando(false);
    }
  }

  return (
    <main className="register-page">
      <section className="register-card">
        <div className="register-card__brand">Helpdesk GLPI</div>
        <h1>Criar conta</h1>
        <p>Cadastre o usuário e defina o perfil de acesso ao sistema.</p>

        {erro && <div className="register-error">{erro}</div>}

        <form onSubmit={handleSubmit}>
          <label>
            Nome
            <input name="nome" value={form.nome} onChange={handleChange} required autoComplete="name" />
          </label>

          <label>
            E-mail
            <input name="email" type="email" value={form.email} onChange={handleChange} required autoComplete="email" />
          </label>

          <label>
            Perfil de acesso
            <select name="perfil" value={form.perfil} onChange={handleChange}>
              <option value="USUARIO">USUARIO - Cliente/Solicitante</option>
              <option value="TECNICO">TECNICO - Atendente/Suporte</option>
              <option value="ADMINISTRADOR">ADMINISTRADOR - Gestão Global</option>
            </select>
          </label>

          <div className="register-card__passwords">
            <label>
              Senha
              <input name="senha" type="password" minLength="6" value={form.senha} onChange={handleChange} required autoComplete="new-password" />
            </label>

            <label>
              Confirmar senha
              <input name="confirmarSenha" type="password" minLength="6" value={form.confirmarSenha} onChange={handleChange} required autoComplete="new-password" />
            </label>
          </div>

          <button type="submit" disabled={carregando}>
            {carregando ? 'Cadastrando...' : 'Cadastrar usuário'}
          </button>
        </form>

        <p className="register-card__footer">
          Já possui conta? <Link to="/login">Entrar</Link>
        </p>
      </section>
    </main>
  );
}
