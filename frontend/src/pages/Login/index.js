import React, { useState } from 'react';
import { Link, Navigate, useLocation, useNavigate } from 'react-router-dom';
import { authApi } from '../../services/api';
import { useAuth } from '../../context/AuthContext';
import './style.css';

export default function Login() {
  const { autenticado, usuario, login } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const [form, setForm] = useState({ email: '', senha: '' });
  const [erro, setErro] = useState('');
  const [carregando, setCarregando] = useState(false);

  if (autenticado) {
    return <Navigate to={usuario?.perfil === 'USUARIO' ? '/chamados' : '/'} replace />;
  }

  function handleChange(event) {
    setForm(current => ({ ...current, [event.target.name]: event.target.value }));
  }

  async function handleSubmit(event) {
    event.preventDefault();
    setErro('');
    setCarregando(true);

    try {
      const dados = await authApi.login(form);
      login(dados);
      const destinoOriginal = location.state?.from;
      const destinoPerfil = dados.perfil === 'USUARIO' ? '/chamados' : '/';
      navigate(destinoOriginal || destinoPerfil, { replace: true });
    } catch (error) {
      setErro(error.message);
    } finally {
      setCarregando(false);
    }
  }

  return (
    <main className="auth-page">
      <section className="auth-card">
        <div className="auth-card__brand">Helpdesk GLPI</div>
        <h1>Entrar</h1>
        <p>Acesse seus chamados e recursos de suporte conforme seu perfil.</p>

        {erro && <div className="auth-error">{erro}</div>}

        <form onSubmit={handleSubmit}>
          <label>
            E-mail
            <input name="email" type="email" value={form.email} onChange={handleChange} required autoComplete="email" />
          </label>

          <label>
            Senha
            <input name="senha" type="password" value={form.senha} onChange={handleChange} required autoComplete="current-password" />
          </label>

          <button type="submit" disabled={carregando}>
            {carregando ? 'Entrando...' : 'Entrar'}
          </button>
        </form>

        <p className="auth-card__footer">
          Não possui conta? <Link to="/register">Cadastre-se</Link>
        </p>
      </section>
    </main>
  );
}
