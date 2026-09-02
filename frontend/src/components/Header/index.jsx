import React from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import './style.css';

export default function Header() {
  const { usuario, logout } = useAuth();
  const navigate = useNavigate();

  function sair() {
    logout();
    navigate('/login', { replace: true });
  }

  return (
    <header className="header">
      <div className="header__inner">
        <NavLink to="/" className="header__brand">Helpdesk GLPI</NavLink>

        <nav className="header__nav" aria-label="Navegação principal">
          <NavLink to="/" end className={({ isActive }) => isActive ? 'header__link header__link--active' : 'header__link'}>
            Dashboard
          </NavLink>

          <NavLink to="/chamados" className={({ isActive }) => isActive ? 'header__link header__link--active' : 'header__link'}>
            Chamados
          </NavLink>

          <NavLink to="/chamados/novo" className="header__new">Novo chamado</NavLink>

          {usuario?.perfil === 'ADMINISTRADOR' && (
            <NavLink to="/admin" className={({ isActive }) => isActive ? 'header__link header__link--active' : 'header__link'}>
              Administração
            </NavLink>
          )}
        </nav>

        <div className="header__account">
          <div>
            <strong>{usuario?.nome}</strong>
            <span>{usuario?.perfil}</span>
          </div>
          <button type="button" onClick={sair}>Sair</button>
        </div>
      </div>
    </header>
  );
}
