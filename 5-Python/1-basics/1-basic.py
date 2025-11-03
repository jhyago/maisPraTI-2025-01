# Variáveis e constantes
#
#     Por meio de variáveis que um algoritmo “guarda” os dados do problema
#     Todo dado que tem a possibilidade de ser alterado no decorrer do tempo deverá ser tratado como uma variável
#     Quando um dado não tem nenhuma possibilidade de variar no decorrer do tempo, deverá ser tratado como constante
#
# Exemplo: calcular a área de um triângulo. Sabemos que a fórmula para o cálculo da área de um triângulo é BASE * ALTURA / 2.
# Base e altura são dados que irão variar no decorrer do “tempo de execução”. O número 2 da fórmula é um dado constante,
# pois sempre terá o mesmo valor.

# Tipo de variáveis
#
#     Inteiros: valores positivos ou negativos, que não possuem uma parte fracionária. Exemplos: 2, 15, -9, 0
#     Float (real): valores positivos ou negativos, que podem possuir uma parte fracionária (também podem ser inteiros). Exemplos: 2.5, -7.1, 100.0
#     Caracteres (char ou string): qualquer elemento presente no teclado. Exemplos: "Ana", "Lucas", 'X', '0'
#     Lógico (booleano): verdadeiro ou falso. Exemplos: True, False, 1, 0

# ---------------------------------------
# Variáveis Inteiras
# ---------------------------------------

quantidade_produtos = -8
rodadas_jogo = 22
convidados_confirmados = 9

print(quantidade_produtos)
print(rodadas_jogo, convidados_confirmados)

# ---------------------------------------
# Variáveis Float
# ---------------------------------------

pi_aproximado = 3.1416
numero_euler = 2.7182
temperatura_media = -3.25

print(pi_aproximado)
print(numero_euler)
print(temperatura_media)

# ---------------------------------------
# String e Chars
# ---------------------------------------

letra = 'Z'
area_estudo = "Ciência de Dados"
linguagem = "Python"

print(letra, area_estudo, linguagem)

print('Estou estudando', area_estudo)
print('A linguagem escolhida é', linguagem)

# ---------------------------------------
# Input de Dados
# ---------------------------------------

idade = int(input('Digite sua idade: '))
print('Sua idade é', idade)

pH = float(input('Qual o pH do solo durante a última medição? '))
print('O pH medido foi', pH)

nome = str(input('Qual seu nome: '))
print('Seu nome é', nome)

# ---------------------------------------
# Manipulação de Strings
# ---------------------------------------

a = 'mochila'
print(a)

maiuscula = a.upper()
print(maiuscula)

minuscula = maiuscula.lower()
print(minuscula)

# Primeira Maiúscula
capital = a.capitalize()
print(capital)

# Baseado na posição (slicing)
metade_palavra = a[0:5]   # do índice 0 ao 4
print(metade_palavra)

ultimas_letras = a[4:]    # do índice 4 até o fim
print(ultimas_letras)

# Substituições
b = a.replace('chi', 'la')
print(a)
print(b)

c = a.replace('a', 'o')
print(c)

# Retorna a posição ou -1
print(c.find('c'))
print(c.find('a'))  # pode retornar -1, já que trocamos 'a' por 'o'
print(c.find('z'))

# Comprimento (len) e remoção de espaços
e = '   mochila '
print(len(e))

f = e.strip()
print(f)

print(len(f))

n1 = 21
n2 = 8

# Interpolação (f-strings)
print(f'Dividindo {n1} por {n2} o resultado é {n1/n2}')

# ---------------------------------------
# Operações Matemáticas
# ---------------------------------------

a = 12
b = 5
print(a, b)

print('A soma é', a + b)
print('A subtração é', a - b)
print('A divisão é', a / b)
print('A multiplicação é', a * b)
print('O resto da divisão é', 17 % 4)

print('12 elevado a 3 é', 12 ** 3)

import math

raiz = math.sqrt(144)
print('A raiz quadrada de 144 é', raiz)

# ---------------------------------------
# Arredondamento
# ---------------------------------------

casos_doenca = 257
numero_habitantes = 50890
casos_por_habitante = casos_doenca / numero_habitantes
print(casos_por_habitante)

print('Arredondado para 6 casas:', round(casos_por_habitante, 6))

print('O número de casos por habitante é de', round(casos_por_habitante, 4))

# ---------------------------------------
# Exercícios Simples
# ---------------------------------------

# 1. Ler dois números inteiros e mostrar o resultado das seguintes operações:
#    adição, subtração, multiplicação e divisão
primeiro_valor = int(input('Digite o primeiro valor inteiro: '))
segundo_valor  = int(input('Digite o segundo valor inteiro: '))

print('A soma é', primeiro_valor + segundo_valor)
print('A subtração é', primeiro_valor - segundo_valor)
print('A multiplicação é', primeiro_valor * segundo_valor)
print('A divisão é', primeiro_valor / segundo_valor)

# 2. Efetuar o cálculo da quantidade de litros de combustível gasto em uma viagem,
# utilizando um automóvel que faz 12 Km por litro.
# Para obter o cálculo, o usuário deve fornecer o tempo gasto na viagem e a velocidade média durante ela.
# Desta forma, será possível obter a distância percorrida com a fórmula DISTANCIA = TEMPO * VELOCIDADE.
# Tendo o valor da distância, basta calcular a quantidade de litros de combustível
# utilizada na viagem, com a fórmula: LITROS_USADOS = DISTANCIA / 12.
# O programa deve apresentar os valores da velocidade média, tempo gasto na viagem,
# a distância percorrida e a quantidade de litros utilizada na viagem.

tempo_gasto = float(input('Digite o tempo gasto na viagem (em horas): '))
velocidade_media = float(input('Digite a velocidade média durante a viagem (em km/h): '))

distancia_percorrida = tempo_gasto * velocidade_media
litros_usados = distancia_percorrida / 12

print(f'A velocidade média foi {velocidade_media} km/h, o tempo gasto foi {tempo_gasto} h, '
      f'a distância percorrida foi {distancia_percorrida} km e a quantidade de litros '
      f'utilizada na viagem foi {round(litros_usados, 2)} L.')
