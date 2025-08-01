import { Component } from 'react'

class ComponenteClasseSuper extends Component {
    constructor(props) {
        super(props)
        this.state = {
            nome: this.props.nome 
        }
    }

    render() {
        return <h1>Olá, {this.state.nome}</h1>
    }
}

function App() {
    return <ComponenteClasseSuper nome = 'Taís' />
}