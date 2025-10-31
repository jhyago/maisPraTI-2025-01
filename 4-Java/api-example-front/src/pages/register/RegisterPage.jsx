import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../../app/state/auth.jsx'
import './RegisterPage.css'

export default function RegisterPage() {
    const { register } = useAuth()
    const [form, setForm] = useState({ name: '', username: '', password: '' })
    const navigate = useNavigate()

    const onChange = (event) => setForm({ ...form, [event.target.name]: event.target.value })

    const onSubmit = async (event) => {
        event.preventDefault()
        try {
            await register(form)
            navigate('/home')
        } catch (error) {
            alert('Erro ao criar conta: ' + error.message)
        }
    }

    return (
        <div className="register-page">
            <div className="register-container">
                <h1>Registrar</h1>
                <form onSubmit={onSubmit} className="register-form">
                    <div className="form-group">
                        <label>Nome:</label>
                        <input 
                            name='name' 
                            value={form.name} 
                            onChange={onChange} 
                            className="form-input"
                        />
                    </div>

                    <div className="form-group">
                        <label>Username:</label>
                        <input 
                            name='username' 
                            value={form.username} 
                            onChange={onChange} 
                            className="form-input"
                        />
                    </div>

                    <div className="form-group">
                        <label>Senha:</label>
                        <input 
                            type='password' 
                            name='password' 
                            value={form.password} 
                            onChange={onChange} 
                            className="form-input"
                        />
                    </div>

                    <button className="submit-btn">Criar Conta</button>
                </form>
            </div>
        </div>
    )
}
