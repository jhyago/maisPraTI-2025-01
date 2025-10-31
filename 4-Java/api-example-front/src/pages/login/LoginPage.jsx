import { useState, useEffect } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { useAuth } from '../../app/state/auth.jsx'
import './LoginPage.css'

function LoginPage() {
    const { login, isAuthenticated } = useAuth()
    const [form, setForm] = useState({ username: '', password: '' })
    const navigate = useNavigate()

    const onChange = (event) => setForm({ ...form, [event.target.name]: event.target.value })

    const onSubmit = async (event) => {
        event.preventDefault()
        try {
            await login(form.username, form.password)
            navigate('/home')
        } catch (error) {
            alert('Erro ao fazer login: ' + error.message)
        }
    }

    useEffect(() => {
        if (isAuthenticated) {
            navigate('/home') 
        }
    }, [isAuthenticated, navigate])

    if (isAuthenticated) {
        return (
            <div>
                Você já está autenticado.{' '}
                <Link to="/home">Ir para a página inicial</Link>
            </div>
        )
    }

    return (
        <div className="login-page">
            <div className="login-container">
                <h1>Entrar</h1>
                <form onSubmit={onSubmit} className="login-form">
                    <div className="form-group">
                        <label>Username</label>
                        <input 
                            type="text" 
                            name='username' 
                            value={form.username} 
                            onChange={onChange} 
                            className="form-input"
                        />
                    </div>

                    <div className="form-group">
                        <label>Senha</label>
                        <input 
                            type="password" 
                            name='password' 
                            value={form.password} 
                            onChange={onChange} 
                            className="form-input"
                        />
                    </div>

                    <button className="submit-btn">Login!</button>
                </form>
            </div>
        </div>
    )
}

export default LoginPage
