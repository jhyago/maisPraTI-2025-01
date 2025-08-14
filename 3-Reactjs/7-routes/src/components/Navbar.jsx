import { NavLink } from "react-router-dom"

const linkStyle = ({ isActive }) => ({
    marginRight: 8,
    textDecoration: "none",
    padding: "6px 10px",
    borderRadius: 6,
    border: "1px solid #ddd",
    background: isActive ? "#111" : "#fff",
    color: isActive ? "#fff": "#111"
})

export default function NavBar() {
    return(
        <nav style={{
            display: "flex",
            alignItems: "center",
            gap: 8,
            padding: 12,
            borderBottom: "1px, solid #eee"
        }}>
            <NavLink to="/" style={linkStyle}>Home</NavLink>
            <NavLink to="/sobre" style={linkStyle}>Sobre</NavLink>
            <NavLink to="/posts/123" style={linkStyle}>Post 123</NavLink>
        </nav>  
    )
}