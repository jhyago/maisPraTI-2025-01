# --------------------------------------------------------------------
# Científico e Processamento Estatístico com NumPy
# --------------------------------------------------------------------
# NumPy é a base do ecossistema científico em Python.
# Principais vantagens:
#  - Arrays n-dimensionais eficientes em memória e velocidade
#  - Operações vetorizadas (dispensam loops explícitos na maioria dos casos)
#  - Funções matemáticas/estatísticas otimizadas (sum, mean, std, etc.)
# --------------------------------------------------------------------
import numpy as np

# ============================================================
# ARRAYS 2D (matrizes) – criação, inspeção e indexação
# ============================================================

# Exemplo: matriz 2 x 3
matriz = np.array([[10,  2, -1],
                   [ 4,  9,  7]])

print('Matriz:\n', matriz)

# shape -> (linhas, colunas)
print('Formato (shape):', matriz.shape)   # (2, 3)
# ndim -> número de dimensões
print('Dimensões (ndim):', matriz.ndim)   # 2
# dtype -> tipo dos elementos
print('Tipo dos dados (dtype):', matriz.dtype)

# Indexação (0-based):
print('Primeira linha:', matriz[0])       # [10  2 -1]
print('Segunda linha:', matriz[1])        # [4 9 7]
print('Elemento [0, 2]:', matriz[0, 2])   # -1
print('Elemento [1, 1]:', matriz[1, 1])   # 9

# Slices (fatiamento)
print('Todas as linhas, colunas 0..1:\n', matriz[:, 0:2])  # duas primeiras colunas
print('Linha 0, todas as colunas:', matriz[0, :])

# Iteração por linhas e colunas
for i in range(matriz.shape[0]):         # para cada linha
    print('Linha', i, ':', matriz[i])
    for j in range(matriz.shape[1]):     # para cada coluna
        print(f'  matriz[{i},{j}] =', matriz[i, j])

# Operações vetorizadas (sem loops explícitos):
# soma de todos os elementos
print('Soma (np.sum):', np.sum(matriz))
# média de todos os elementos
print('Média (np.mean):', np.mean(matriz))
# soma por coluna
print('Soma por coluna:', np.sum(matriz, axis=0))
# soma por linha
print('Soma por linha:', np.sum(matriz, axis=1))

# ============================================================
# EXERCÍCIOS BÁSICOS
# ============================================================

# 1) LISTA: Ler 5 inteiros, guardar em uma lista e somar os valores.
# Objetivos didáticos:
#  - Praticar append e iteração
#  - Comparar somatório manual vs. built-in sum()
# ------------------------------------------------------------
# lista = []
# for _ in range(5):
#     valor = int(input('Digite um inteiro: '))
#     lista.append(valor)
#
# soma = 0
# for i in range(len(lista)):
#     soma += lista[i]
# print('Soma (laço):', soma)
# print('Soma (sum):', sum(lista))

# 2) DICIONÁRIO: Ler nome e nota de 3 alunos, armazenar em dict e calcular média.
# Objetivos didáticos:
#  - Mapeamento nome → nota
#  - Iteração sobre .values()
# ------------------------------------------------------------
# alunos = {}
# for _ in range(3):
#     nome = input('Nome do aluno: ')
#     nota = float(input('Nota: '))
#     alunos[nome] = nota
#
# print('Notas:', list(alunos.values()))
#
# soma_notas = 0.0
# for nota in alunos.values():
#     soma_notas += nota
# media = soma_notas / 3
# print('Média da turma =', media)

# 3) MATRIZ: Somar todos os elementos de uma matriz 2x3
# Objetivos didáticos:
#  - Praticar shape e acesso por índices
#  - Mostrar alternativa vetorizada com np.sum
# ------------------------------------------------------------
matriz2 = np.array([[ 3,  4,  1],
                    [ 6, -2,  5]])

soma_total = 0
for i in range(matriz2.shape[0]):
    for j in range(matriz2.shape[1]):
        soma_total += matriz2[i, j]

print('Soma total (laço):', soma_total)
print('Soma total (np.sum):', np.sum(matriz2))  # equivalente e mais simples

# Extras úteis para prática:
# - Criar arrays rápidos:
#   np.zeros((2,3)), np.ones((3,3)), np.full((2,2), 7), np.eye(3)
# - Estatística básica:
#   np.min(matriz2), np.max(matriz2), np.std(matriz2), np.var(matriz2)
# - Operações elemento a elemento:
#   matriz2 * 2, matriz2 + 10, matriz2 ** 2
# - Broadcasting (ex.: somar vetor a cada linha/coluna):
#   matriz2 + np.array([1, 2, 3])