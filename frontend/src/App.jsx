import React, { useEffect } from 'react';
import { Navigate, Outlet, Route, Routes, useNavigate } from 'react-router-dom';
import Header from './components/Header';
import ProtectedRoute from './components/ProtectedRoute';
import { useAuth } from './context/AuthContext';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import Chamados from './pages/Chamados';
import NovoChamado from './pages/NovoChamado';
import ChamadoDetalhe from './pages/ChamadoDetalhe';
import Admin from './pages/Admin';

function AppLayout() {
  return (
    <div className="app-shell">
      <Header />
      <main className="app-content"><Outlet /></main>
    </div>
  );
}

function AuthExpirationListener() {
  const { logout } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    const handler = () => {
      logout();
      navigate('/login', { replace: true });
    };
    window.addEventListener('helpdesk-auth-expired', handler);
    return () => window.removeEventListener('helpdesk-auth-expired', handler);
  }, [logout, navigate]);

  return null;
}

export default function App() {
  return (
    <>
      <AuthExpirationListener />
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        <Route element={<ProtectedRoute />}>
          <Route element={<AppLayout />}>
            <Route path="/" element={<Dashboard />} />
            <Route path="/chamados" element={<Chamados />} />
            <Route path="/chamados/novo" element={<NovoChamado />} />
            <Route path="/chamados/:id" element={<ChamadoDetalhe />} />

            <Route element={<ProtectedRoute perfis={['ADMINISTRADOR']} />}>
              <Route path="/admin" element={<Admin />} />
            </Route>
          </Route>
        </Route>

        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </>
  );
}
