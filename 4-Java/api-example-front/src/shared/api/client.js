const VITE_API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

function buildHeaders(token, extra = {}) {
    const headers = {
        'Accepted': 'application/json',
        ...extra,
    }
    
    if(token) headers['Authorization'] = `Bearer ${token}`

    return headers
}

async function handleResponse(response) {
    const contentType = response.headers.get('Content-Type')
    let data = null
    if(contentType && contentType.includes('application/json')) {
        data = await response.json()
    } else {
        const text = await response.text()
        data = text
    }

    if(!response.ok) {
        const error = new Error(data?.message || response.statusText)
        error.status = response.status
        error.body = data
        throw error
    }

    return data
}

export async function apiFetch(path, { method = 'GET', token, body, headers = {}, signal } = {}) {
    const init = {
        method, 
        credentials: 'include',
        headers: buildHeaders(token, headers),
        signal,
    }

    if(body !== undefined) {
        if(body instanceof FormData) {
            init.body = body
        } else {
            init.headers['Content-Type'] = 'application/json'
            init.body = JSON.stringify(body)
        }
    } 

    const response = await fetch(`${VITE_API_BASE_URL}${path}`, init)
    return handleResponse(response)
}

export { VITE_API_BASE_URL}