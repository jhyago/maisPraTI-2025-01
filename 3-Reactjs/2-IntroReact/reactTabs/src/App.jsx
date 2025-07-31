import { useState } from 'react'

const content = [
  {
    label: "Por que React?",
    items: [
      "React é muito popular",
      "Facilita a o trabalho de desenvolvedor",
      "React é declarativo",
      "O Grêmio é tri campeão da libertadores"
    ]
  },

  {
    label: "Por que React?",
    items: [
      "React é muito popular",
      "Facilita a o trabalho de desenvolvedor",
      "React é declarativo",
      "O Grêmio é tri campeão da libertadores"
    ]
  },

  {
    label: "Por que React?",
    items: [
      "React é muito popular",
      "Facilita a o trabalho de desenvolvedor",
      "React é declarativo",
      "O Grêmio é tetra campeão da libertadores"
    ]
  }
]

function App() {
  const [activeContentIndex, setActiveContentIndex] = useState(0)

  


  return (
    <>
      <div className='container'>
          <header>
            <img src="react-logo-xs" alt="" />
            <div>
              <h1>React.js</h1>
              <p>Estou usando React</p>
            </div>
          </header>


          <div id='tabs'>
            <menu>
              {content.map((tab, index) => (
                <button 
                key={tab.label}
                className={activeContentIndex === index ? "active" : ""}
                onClick={() => setActiveContentIndex(index)}>
                  {tab.label}
                </button>
              ))}
            </menu>

            <div id='tab-content'>
              <ul>
                  {content[activeContentIndex].items.map((item) => (
                    <li key={item}>{item}</li>
                  ))}
              </ul>
            </div>
          </div>
      </div>
    </>
  )
}

export default App
