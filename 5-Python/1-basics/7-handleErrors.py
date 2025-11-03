# --------------------------------------------------------------------
# Tratamento de erros e exceções em Python
#
# Por que usar try/except?
# - Para capturar falhas previstas (ex.: entrada inválida, divisão por zero)
# - Para manter o programa estável e dar mensagens úteis ao usuário
#
# Blocos importantes:
#   try:     onde colocamos o código que pode falhar
#   except:  onde tratamos exceções específicas
#   else:    executa SÓ se NADA deu errado no try
#   finally: executa SEMPRE (dando erro ou não) — útil p/ liberar recursos
#
# Exemplos comuns de exceções:
#   NameError         -> variável não definida
#   TypeError         -> operação com tipos incompatíveis
#   RuntimeError      -> erro de execução genérico
#   SyntaxError       -> erro de sintaxe (pego na hora de interpretar o código)
#   ZeroDivisionError -> divisão por zero
#   IndexError        -> índice fora do intervalo
#   ValueError        -> valor com tipo/conteúdo inválido (ex.: float('abc'))
#   KeyboardInterrupt -> usuário interrompeu (Ctrl+C)
# --------------------------------------------------------------------

# Exemplos comentados de erros (NÃO execute):
# print(nome_nao_definido)          # NameError
# print(10 / 0)                      # ZeroDivisionError
# print(2.3 / "cachorro")            # TypeError
# numeros = [1, 2, 3]
# print(numeros[5])                  # IndexError
# int("dois")                        # ValueError

# --------------------------------------------------------------------
# EXERCÍCIO GUIADO
# 1) Crie uma lista vazia e leia 2 floats do usuário.
# 2) Armazene-os nas posições 0 e 1 da lista.
# 3) Divida o primeiro pelo segundo.
#
# Tratamentos exigidos:
#   - ValueError: se o usuário digitar caracteres inválidos (ex.: "abc")
#   - ZeroDivisionError: se o segundo número for zero
#   - IndexError: se tentar acessar índice que não existe
#   - KeyboardInterrupt: se o usuário interromper a execução (Ctrl+C)
#
# Boas práticas aplicadas:
#   - Capturar exceções ESPECÍFICAS
#   - Usar 'else' para o fluxo de sucesso
#   - Usar 'finally' para um encerramento previsível
# --------------------------------------------------------------------

lista = []

try:
    # Leitura com conversão: ValueError pode ocorrer aqui
    primeiro = float(input('Digite o dividendo (número real): '))
    segundo  = float(input('Digite o divisor (número real): '))

    # Coloca nas posições 0 e 1
    lista.append(primeiro)
    lista.append(segundo)

    # Divisão: ZeroDivisionError pode ocorrer aqui
    # IndexError ocorreria se tentássemos acessar posições inexistentes
    resultado = lista[0] / lista[1]

except ValueError:
    # Quando a conversão para float falha (ex.: "dez", "xyz")
    print('Entrada inválida: por favor, digite apenas números (use ponto para decimais).')

except ZeroDivisionError:
    # Quando o divisor é zero
    print('Operação inválida: divisão por zero não é permitida.')

except IndexError:
    # Quando tentamos acessar uma posição que não existe na lista
    # (aqui não deve acontecer com o fluxo normal, mas tratamos por segurança)
    print('Erro interno: posição inválida na lista. Verifique o tamanho da lista.')

except KeyboardInterrupt:
    # Quando o usuário interrompe com Ctrl+C
    print('\nOperação cancelada pelo usuário.')

else:
    # Executa somente se NENHUMA exceção ocorreu no bloco try
    print(f'Resultado: {lista[0]} / {lista[1]} = {resultado}')

finally:
    # Sempre executa — útil para liberar recursos, fechar arquivos, etc.
    print('Fim do processamento (bloco finally executado).')

# --------------------------------------------------------------------
# DICAS EXTRAS
# - Evite "except:" sem tipo — ele captura TUDO (até erros que você não quer).
# - Se precisar logar a exceção, use "except Exception as e:" e registre e.
# - Valide dados antes de operar (ex.: teste divisor == 0 antes de dividir).
# --------------------------------------------------------------------