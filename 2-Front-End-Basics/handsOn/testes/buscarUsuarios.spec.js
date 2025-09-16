const buscarUsuario = require("./buscarUsuarios")
const axios = require("axios")


// mockar para não chamar excessivamente minha api
jest.mock('axios')


// usar o describe do jest
describe("Testando a busca do usuario", () => {

    // criar o test
    it('Buscando pelo user lázaro', async () => {

        // dados ficticios para o test
        const dadosFalsosDoUsuario = {
            name: 'Lazaro Kaua de mentirinha',
            id: 12345
        };

        // chamada ficticia para comparar os resultados
        axios.get.mockResolvedValue({ data: dadosFalsosDoUsuario })

        const result = await buscarUsuario('Lazarokaua')

        // verificando se é o resultado esperado
        expect(result).toEqual(dadosFalsosDoUsuario)

    })


})

