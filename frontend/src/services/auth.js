const AUTH_KEY = 'helpdesk_auth';

export function getAuthData() {
  try {
    const raw = localStorage.getItem(AUTH_KEY);
    return raw ? JSON.parse(raw) : null;
  } catch {
    return null;
  }
}

export function saveAuthData(data) {
  localStorage.setItem(AUTH_KEY, JSON.stringify(data));
}

export function clearAuthData() {
  localStorage.removeItem(AUTH_KEY);
}

export function getToken() {
  return getAuthData()?.token || null;
}
