import './styles/base.css'
import './styles/utilities.css'
import './styles/components.css'

import { Button } from './components/Button/Button'
import { Card } from './components/Card/Card'

import { ButtonModuleExample } from './components/Button/ButtonModuleExample'

import { Themed } from './components/Button/ButtonStyled'
import { GlobalStyle } from './theme/GlobalStyle'
import { ButtonStyled } from './components/Button/ButtonStyled'


function App() {
  
  return (
      <main className="container">
        <Card title="CSS - Exemplo Global">
          <p>Estilos Vindos de CSS Global - Arquivos Globais</p>
          <Button>Enviar</Button>
          <ButtonModuleExample>Send Message</ButtonModuleExample>
          <ButtonStyled variant='ghost'>Ghost</ButtonStyled>
        </Card>
      </main>
  )
}

export default App
