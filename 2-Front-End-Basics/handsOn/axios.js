// const axios = require('axios/dist/browser/axios.cjs'); // browser

async function buscarComentarios() {
    const response = await axios.get("https://jsonplaceholder.typicode.com/comments")
    console.log(response)
}


buscarComentarios()
