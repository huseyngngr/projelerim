// Admin hesap bilgileri (gerçek uygulamada bu bilgiler güvenli bir veritabanında saklanmalıdır)
export const ADMIN_CREDENTIALS = {
  email: 'admin@arackirala.com',
  password: 'admin123'
};

// Kullanıcı oturumu kontrolü
export function isLoggedIn(): boolean {
  if (typeof window === 'undefined') return false;
  return localStorage.getItem('isLoggedIn') === 'true';
}

// Admin oturumu kontrolü
export function isAdmin(): boolean {
  if (typeof window === 'undefined') return false;
  return localStorage.getItem('isAdmin') === 'true';
}

// Giriş işlemi
export function login(email: string, password: string): boolean {
  if (email === ADMIN_CREDENTIALS.email && password === ADMIN_CREDENTIALS.password) {
    localStorage.setItem('isLoggedIn', 'true');
    localStorage.setItem('isAdmin', 'true');
    return true;
  }
  return false;
}

// Çıkış işlemi
export function logout(): void {
  localStorage.removeItem('isLoggedIn');
  localStorage.removeItem('isAdmin');
} 