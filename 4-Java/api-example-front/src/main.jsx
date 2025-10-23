import { createRoot } from 'react-dom/client'
import { StrictMode } from 'react'
import AppRouter from './app/providers/router.jsx'

createRoot(document.getElementById('root')).render(
<StrictMode>
    <AppRouter />
</StrictMode>)