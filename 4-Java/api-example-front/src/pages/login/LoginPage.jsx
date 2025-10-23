import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../../app/state/auth.jsx'

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

    if(isAuthenticated) return <div>Você já está autenticado - <Link to='/home'>Ir para página inicial</Link></div>

    return (
        <div>
            <form onSubmit={onSubmit}>

                <label>Username</label>
                <input type="text" name='username' value={form.username} onChange={onChange}/>

                <label>Senha</label>
                <input type="password" name='password' value={form.password} onChange={onChange}/>

                <button>Login!</button>
            </form>

            {/* <div>
                <a href={`${VITE_API_BASE_URL}/oauth2/authorization/github`}>Entrar com GitHub</a>
            </div> */}
        </div>
    )
}

export default LoginPage