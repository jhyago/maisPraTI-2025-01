import { createContext, useState, useEffect, useMemo, useContext } from 'react'
import * as authApi from '../../entities/auth/api.js'
import { getMe } from '../../entities/user/api.js'

const AuthContext = createContext()

const TOKEN_KEY = 'jwt_token'

export function AuthProvider({ children }) {
    const [token, setToken] = useState(() => {
        return localStorage.getItem(TOKEN_KEY)
    })
    const [user, setUser] = useState(null)
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        if(token) localStorage.setItem(TOKEN_KEY, token)
        else localStorage.removeItem(TOKEN_KEY)
    }, [token])

    useEffect(() => {
        async function init() {
            try {
                const me = await getMe(token)
                setUser(me)
            } catch (error) {
                setUser(null)
            } finally {
                setLoading(false)
            }
        }   
        init()
    }, [token])


    const register = async (data) => {
        const { token: t } = await authApi.registerUser(data)
        setToken(t) 
        const me = await getMe(t)
        setUser(me)
    }

    const login = async (username, password) => {
        const { token: t } = await authApi.loginUser({ username, password })
        setToken(t)
        const me = await getMe(t)
        setUser(me)
    }

    const value = useMemo(() => ({
        user,
        setUser,
        token,
        setToken,
        loading,
        isAuthenticated: Boolean(user),
        register,
        login,
    }), [user, token, loading])

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export function useAuth() {
    const ctx = useContext(AuthContext)
    if(!ctx) throw new Error('useAuth must be used within an AuthProvider')
    return ctx
}