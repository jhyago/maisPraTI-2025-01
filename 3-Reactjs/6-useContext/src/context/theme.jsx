import { createContext, useContext, useMemo, useCallback, useState } from "react"

const ThemeContext = createContext(null)

export function ThemeProvider({ children }) {
    const [theme, setTheme] = useState("light")

    const toggleTheme = useCallback(() => {
        setTheme((t) => (t === "light" ? "dark" : "light"))
    }, [])

    const value = useMemo(() => ({ theme, toggleTheme }), [theme, toggleTheme])

    return <ThemeContext.Provider value={value}>{children}</ThemeContext.Provider>
}

export function useTheme() {
    const ctx = useContext(ThemeContext)
    if(!ctx) throw new Error("useTheme deve estar dentro do Provider")
    return ctx
}