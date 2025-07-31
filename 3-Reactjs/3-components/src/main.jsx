import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './components/App.jsx'
import Greeting from './components/Greeting.jsx'
import ClassComponent from './components/ClassComponent.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <ClassComponent />
  </StrictMode>,
)
