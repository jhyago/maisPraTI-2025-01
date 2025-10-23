import { apiFetch } from "../../shared/api/client"

export function getMe(token) {
    return apiFetch('/users/me', { token })
}