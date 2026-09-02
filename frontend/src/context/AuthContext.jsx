import React, { createContext, useContext, useMemo, useState } from 'react';
import { clearAuthData, getAuthData, saveAuthData } from '../services/auth';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [usuario, setUsuario] = useState(() => getAuthData());

  const login = (dados) => {
    saveAuthData(dados);
    setUsuario(dados);
  };

  const logout = () => {
    clearAuthData();
    setUsuario(null);
  };

  const value = useMemo(() => ({
    usuario,
    autenticado: Boolean(usuario?.token),
    login,
    logout
  }), [usuario]);

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  return useContext(AuthContext);
}
