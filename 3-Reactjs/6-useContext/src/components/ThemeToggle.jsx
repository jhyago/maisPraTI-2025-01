function ThemeToggle() {
    const { theme, toggleTheme } = useTheme()

    return(
        <button onClick={toggleTheme}>
            Alternar para {theme === "light" ? "dark" : "light"}
        </button>
    )
}