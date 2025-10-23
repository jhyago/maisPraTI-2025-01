import { apiFetch } from '../../shared/api/client.js' 


export function registerUser({ name, username, password }) {
    return apiFetch('/auth/register', {
        method: 'POST',
        data: { name, username, password },
    })
}

export function loginUser({ username, password }) {
    return apiFetch('/auth/login', {
        method: 'POST',
        data: { username, password },
    })
}