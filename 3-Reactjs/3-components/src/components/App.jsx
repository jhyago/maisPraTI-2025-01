function Button(props) {
  return <button onClick={props.onClick}>Clique aqui!</button>
}

function App() {

  const handleClick = () => {
    alert('Botão Clicado!')
  }
 
  return (
    <>
      <Button onClick={handleClick}>Clique aqui!</Button>
    </>
  )
}

export default App
