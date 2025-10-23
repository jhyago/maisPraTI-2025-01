import { apiFetch } from '../../utils/apiFetch.js'

export function listEnrollments(token) {
    return apiFetch('/enrollments', { token })
}

export function createEnrollment(data, token) {
    return apiFetch('/enrollments', { method: 'POST', data, token })
}

export function listUserIdsByCourseNative(courseId, token) {
    return apiFetch(`/by-course/${courseId}/users`, { token })
}