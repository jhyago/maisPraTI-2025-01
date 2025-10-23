import { apiFetch } from '../../utils/apiFetch.js'

export function listCourses(token) {
    return apiFetch('/courses', { token })
}

export function createCourse(data, token) {
    return apiFetch('/courses', { method: 'POST', data, token })
}