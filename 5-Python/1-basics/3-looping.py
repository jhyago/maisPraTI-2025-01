# ------------------------------------------------------------
# Impressões simples (sem laço)
# Para perceber repetição manual e motivar o uso de loops.
# ------------------------------------------------------------
print(10)
print(11)
print(12)
print(13)
print(14)

# ------------------------------------------------------------
# for + range(início, fim_exclusivo[, passo])
# Gera uma sequência de inteiros. O 'fim' não é incluído.
# ------------------------------------------------------------
for numero in range(10, 15):   # 10, 11, 12, 13, 14
    print(numero)

# Contagem regressiva com passo negativo
for numero in range(14, 9, -1):  # 14, 13, 12, 11, 10
    print(numero)

# ------------------------------------------------------------
# Acúmulo (somatório) em laço for
# Padrão clássico: iniciar acumulador e somar a cada iteração.
# ------------------------------------------------------------
soma = 0
for numero in range(1, 11):   # soma de 1 a 10
    soma = soma + numero      # equivalente a: soma += numero
    # Dica: descomente para ver a evolução passo a passo
    # print(soma)

print('Soma de 1 a 10 =', soma)

# ------------------------------------------------------------
# Iterando sobre strings (strings são iteráveis)
# Cada iteração retorna um caractere.
# ------------------------------------------------------------
palavra = 'abacaxi'
for letra in palavra:
    # Dica: descomente a linha abaixo para ver cada letra
    # print(letra)
    if letra == 'x':
        print('Achou a letra "x"')

# ------------------------------------------------------------
# Laços aninhados (loop dentro de loop)
# Exemplo: "linhas" (i) e "colunas" (j).
# ------------------------------------------------------------
for i in range(0, 3):   # 0, 1, 2
    print('linha i =', i)
    print('---')
    for j in range(0, 2):  # 0, 1
        print('  coluna j =', j)
    print()  # linha em branco para separar blocos

# ============================================================
# While (enquanto)
# Use quando não souber previamente QUANTAS vezes irá repetir,
# mas tiver uma CONDIÇÃO de parada.
# ============================================================

# Contagem crescente
numero = 2
while numero < 7:   # imprime 2, 3, 4, 5, 6
    print(numero)
    numero += 1
print('---')

# Contagem decrescente
numero = 6
while numero > 1:   # imprime 6, 5, 4, 3, 2
    print(numero)
    numero -= 1

# Somatório com while
soma = 0
numero = 1
while numero <= 10:
    soma += numero
    numero += 1
print('Soma (while) de 1 a 10 =', soma)

# Validação de entrada com while
# Continua pedindo até o usuário digitar dentro do intervalo.
numero = 0
while numero < 1 or numero > 5:
    numero = int(input('Digite um número de 1 a 5: '))
print('Você digitou:', numero)

# ============================================================
# Exercícios (exemplos guiados)
# ============================================================

# 1) Ler 4 notas e informar a média (duas soluções)
# Objetivos didáticos:
# - Mostrar acúmulo com for e com while.
# - Mostrar cálculo da média.

# --- Solução com for ---
# nota_total = 0.0
# for i in range(1, 5):
#     nota = float(input(f'Digite a {i}ª nota: '))
#     nota_total += nota
# media = nota_total / 4
# print('Média (for) =', media)

# --- Solução com while ---
# nota_total = 0.0
# i = 1
# while i <= 4:
#     nota = float(input(f'Digite a {i}ª nota: '))
#     nota_total += nota
#     i += 1
# media = nota_total / 4
# print('Média (while) =', media)

# 2) Imprimir a tabuada do número 7 (7 x 1 = 7 ; ... ; 7 x 10 = 70)
# Objetivos didáticos:
# - Praticar range e formatação de strings (f-strings).

for i in range(1, 11):
    print(f'7 x {i} = {7 * i}')

print('---')

# Mesma tabuada usando while
fator = 1
while fator <= 10:
    print(f'7 x {fator} = {7 * fator}')
    fator += 1

# ------------------------------------------------------------
# DICAS EXTRAS
# - Use 'break' para sair de um laço antes do fim.
# - Use 'continue' para pular a iteração atual e ir para a próxima.
# (Exemplo rápido)
# for n in range(1, 8):
#     if n == 5:
#         continue  # pula o 5
#     if n == 7:
#         break     # encerra no 7
#     print(n)
# ------------------------------------------------------------
