const axios = require("axios")


async function buscarUsuario(userID) {

    try {
        const response = await axios.get(`https://api.github.com/users/${userID}`)
        console.log(response.data)
        return response.data
    } catch (e) {
        console.log(e)
        return null
    }

}

buscarUsuario("Lazarokaua")


module.exports = buscarUsuario
