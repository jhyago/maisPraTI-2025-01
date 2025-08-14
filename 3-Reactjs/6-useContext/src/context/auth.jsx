import { createContext, useContext, useState, useMemo } from 'react'

const AuthContext = createContext(null)

export function AuthProvider({ children }) {
    const [user, setUser] = useState(null)

    const login = (name) => setUser({ name })
    const logout = () => setUser(null)

    const value = useMemo (() => ({ user, login, logout }), [user])

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export function useAuth() {
    const ctx = useContext(AuthContext)
    if(!ctx) throw new Error("useAuth deve ser usado dentro de <AuthProvider>")
    return ctx
}

// Crie um mini-app que use Context API para controlar o tema “light/dark”. Forneça um ThemeProvider e um hook useTheme; um componente alterna o tema e outro mostra o tema atual — sem passar props entre eles. Use useMemo para estabilizar o value.