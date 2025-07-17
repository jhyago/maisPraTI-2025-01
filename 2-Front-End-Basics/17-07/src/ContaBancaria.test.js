const ContaBancaria = require('./ContaBancaria')

describe('Testes da class ContaBancaria', () => {
    let conta 

    beforeEach(() => {
        conta = new ContaBancaria()
    })

    test('Saldo inicial deve ser 0', () => {
        expect(conta.consultarSaldo()).toBe(0)
    })

    test('Depositar 100 deve resultar em saldo 100', () => {
        conta.depositar(100)
        expect(conta.consultarSaldo()).toBe(100)
    })

    //Depositar um valor inválido.
    test('Deve lançar um erro ao tentar depositar valor inválido', () => {
        expect(() => conta.depositar(0)).toThrow('O valor depositado deve ser maior que zero')
        expect(() => conta.depositar(-50)).toThrow('O valor depositado deve ser maior que zero')
    })

    //Sacar um valor inválido.
    test('Deve lança um erro ao tentar sacar valor inválido', () => {
        expect(() => conta.sacar(0).toThrow('O valor do saque deve ser maior que zero'))
    })
})