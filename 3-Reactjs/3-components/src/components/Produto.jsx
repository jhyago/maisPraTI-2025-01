// Criar um componente que exibe as informações de um produto. O componente pai (App) vai passar os dados do produto (nome, preço, descrição) como props para o componente filho (Produto), e o componente filho vai exibir essas informações.

function Produto(props) {
    return (
        <div>
            <h2>{props.nome}</h2>
            <h2>{props.preco}</h2>
            <h2>{props.descricao}</h2>
        </div>
    )
}

function Aplicativo() {
    const produto = {
        nome: "Camiseta",
        preco: 79.90,
        descricao: "Camiseta muita maneira do Kansas"
    }

    return (
        <div>
            <Produto nome={produto.nome} preco={produto.preco} descricao={produto.descricao}/>
        </div>
    )
}

export default Aplicativo