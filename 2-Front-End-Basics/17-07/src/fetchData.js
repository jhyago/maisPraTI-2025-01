function fetchData() {
    return new Promise((resolve) => {
        setTimeout(() => {
            resolve('Dados Recebidos')
        }, 2000)
    })
}

module.exports = fetchData