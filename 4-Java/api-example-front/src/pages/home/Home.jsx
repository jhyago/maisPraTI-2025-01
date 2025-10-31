import { useNavigate } from 'react-router-dom'
import { useAuth } from '../../app/state/auth.jsx'

function HomePage() {
    const { logout } = useAuth()
    const navigate = useNavigate()

    const handleLogout = () => {
        logout()
        navigate('/login') 
    }

    return (
        <div >
            <h1>Bem-vindo à página inicial!</h1>
            <button onClick={handleLogout} className="logout-btn">Logout</button>
        </div>
    )
}

export default HomePage
