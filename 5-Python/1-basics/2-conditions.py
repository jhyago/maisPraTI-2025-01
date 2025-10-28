# Operadores Lógicos
#
# Usamos operadores lógicos para combinar ou negar expressões booleanas:
#   and  -> True se TODAS forem verdadeiras
#   or   -> True se PELO MENOS UMA for verdadeira
#   not  -> inverte o valor lógico (True -> False, False -> True)

a = True
b = False

print(a, b)

c = a and b
print("a AND b resulta em:", c)

d = a or b
# Atenção: '|' é OR bit a bit; em bools, funciona parecido com 'or',
# mas aqui usamos só para demonstrar a diferença.
print("OR bit a bit (a | b):", a | b)
print("a OR b resulta em:", d)

print("NOT a:", not a)
print("NOT b:", not b)

# ----------------------------------------------------
# Operadores Relacionais
# Produzem valores booleanos a partir de comparações
# ----------------------------------------------------

print(10 > 7)    # maior que
print(10 < 7)    # menor que
print(10 >= 10)  # maior ou igual
print(10 <= 9)   # menor ou igual
print(10 == 10)  # igualdade
print(10 != 3)   # diferença

# ----------------------------------------------------
# Operadores Condicionais (if / elif / else)
# ----------------------------------------------------

if 8 > 2:
    print('8 é maior que 2')

if 3 > 4:
    print('3 é maior')
else:
    print('3 não é maior que 4')

n = 12
if n == 1:
    print('n é igual a 1')
elif n == 2:
    print('n é igual a 2')
else:
    print('n não é 1 nem 2')

x = 2
y = 8
if (x > 1) or (y % 2 == 0):
    print('x é maior que 1 OU y é par (pelo menos uma é verdadeira)')
else:
    print('Nenhuma das condições foi satisfeita')

# ----------------------------------------------------
# Exercícios Básicos
# ----------------------------------------------------

# 1) Leia a idade do usuário e classifique-a em:
#    - Criança – 0 a 12 anos
#    - Adolescente – 13 a 17 anos
#    - Adulto – 18 a 59 anos
#    - Idoso – 60 anos ou mais
#    Se o usuário digitar um número negativo, mostrar que a idade é inválida.

idade = int(input('Digite sua idade: '))

if idade < 0:
    print('Idade inválida!')
elif 0 <= idade <= 12:
    print('Você é uma criança!')
elif 13 <= idade <= 17:
    print('Você é um adolescente!')
elif 18 <= idade <= 59:
    print('Você é um adulto!')
else:
    print('Você é um idoso!')

# 2) Calcular a média de um aluno na disciplina de Programação I
#    a partir das notas N1, N2 e N3 (média aritmética).
#    Após calcular, anunciar:
#      - Reprovado: 0.0 <= média <= 4.0
#      - Exame:    4.0 <  média <  6.0
#      - Aprovado: média >= 6.0
#    Se ficar de exame, ler a nota do exame e:
#      - Aprovado no exame se nota_exame >= 6.0
#      - Reprovado no exame caso contrário

N1 = float(input('Digite a N1: '))
N2 = float(input('Digite a N2: '))
N3 = float(input('Digite a N3: '))

media = (N1 + N2 + N3) / 3
print(f'Média: {media:.2f}')

if 0.0 <= media <= 4.0:
    print('Aluno reprovado!')
elif 4.0 < media < 6.0:
    print('Aluno ficou de exame.')
    nota_exame = float(input('Digite a nota do exame: '))
    if nota_exame >= 6.0:
        print('Aprovado após o exame!')
    else:
        print('Reprovado após o exame.')
else:  # media >= 6.0
    print('Aluno aprovado!')