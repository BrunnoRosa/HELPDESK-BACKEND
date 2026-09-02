import { clearAuthData, getToken } from './auth';

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';

async function request(path, options = {}, authenticated = true) {
  const token = getToken();
  const headers = {
    'Content-Type': 'application/json',
    ...(options.headers || {})
  };

  if (authenticated && token) {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(`${API_URL}${path}`, {
    ...options,
    headers
  });

  const text = await response.text();
  let body = null;

  if (text) {
    try {
      body = JSON.parse(text);
    } catch {
      body = { Mensagem: text };
    }
  }

  if (!response.ok) {
    if (response.status === 401 && authenticated) {
      clearAuthData();
      window.dispatchEvent(new Event('helpdesk-auth-expired'));
    }

    const message = body?.Mensagem || body?.message || 'Erro ao processar a requisição';
    const validation = body?.erros ? `: ${Object.values(body.erros).join(' | ')}` : '';
    throw new Error(`${message}${validation}`);
  }

  return body;
}

export const authApi = {
  login: (payload) => request('/auth/login', {
    method: 'POST',
    body: JSON.stringify(payload)
  }, false),

  register: (payload) => request('/auth/register', {
    method: 'POST',
    body: JSON.stringify(payload)
  }, false)
};

export const chamadoApi = {
  listar: () => request('/chamados'),
  buscar: (id) => request(`/chamados/${id}`),
  criar: (payload) => request('/chamados', {
    method: 'POST',
    body: JSON.stringify(payload)
  }),
  atualizar: (id, payload) => request(`/chamados/${id}`, {
    method: 'PUT',
    body: JSON.stringify(payload)
  }),
  deletar: (id) => request(`/chamados/${id}`, { method: 'DELETE' })
};

export const atendimentoApi = {
  listar: () => request('/atendimentos'),
  buscarPorChamado: (chamadoId) => request(`/atendimentos/chamado/${chamadoId}`),
  atualizar: (payload) => request('/atendimentos', {
    method: 'PUT',
    body: JSON.stringify(payload)
  })
};

export const adminApi = {
  listarUsuarios: () => request('/admin/usuarios'),
  listarTecnicos: () => request('/admin/tecnicos'),
  atualizarPerfil: (id, perfil) => request(`/admin/usuarios/${id}/perfil`, {
    method: 'PUT',
    body: JSON.stringify({ perfil })
  }),
  deletarUsuario: (id) => request(`/admin/usuarios/${id}`, { method: 'DELETE' }),
  resumo: () => request('/admin/relatorios/resumo')
};
