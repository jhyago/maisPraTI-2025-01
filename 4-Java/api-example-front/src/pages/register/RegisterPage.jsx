import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../../app/state/auth.jsx'

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
        <div>
            <h1>Registrar</h1>
            <form onSubmit={onSubmit}>
                <div>
                    <label>Nome:</label>
                    <input name='name' value={form.name} onChange={onChange} />
                </div>

                 <div>
                    <label>username:</label>
                    <input name='name' value={form.username} onChange={onChange} />
                </div>

                 <div>
                    <label>Senha:</label>
                    <input type='password' name='name' value={form.password} onChange={onChange} />

                    <button>Criar Conta</button>
                </div>
            </form>
        </div>
    )

}