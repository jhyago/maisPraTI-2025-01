# ---------------------------------------------------------------------------------
# COLEÇÕES EM PYTHON: TUPLA, LISTA, DICIONÁRIO E CONJUNTO (SET)
# ---------------------------------------------------------------------------------
# Tuplas são IMUTÁVEIS: depois de criadas, não é possível alterar, adicionar
# ou remover elementos. Podem conter tipos variados (ints, strings, floats, outras tuplas).
#
# Listas são MUTÁVEIS: permitem inserir, remover e alterar elementos.
# Em Python, ao contrário de algumas linguagens, listas podem conter tipos diferentes.
#
# Dicionários mapeiam CHAVE -> VALOR e são mutáveis.
#
# Conjuntos (set) armazenam valores ÚNICOS (sem repetição) e não garantem ordem.
# ---------------------------------------------------------------------------------


# =========================================
# TUPLAS (imutáveis)
# =========================================
tupla = ('Maçã', 42, 3.14, ('aninhada', True))

# Acesso por índice (começa em 0)
print(tupla[0])        # 'Maçã'
print(tupla[3][0])     # 'aninhada'

# Buscar a posição de um valor (ValueError se não encontrar)
print(tupla.index(42))  # 1

# Iterar sobre tupla
for elemento in tupla:
    print('Tupla contém ->', elemento)


# =========================================
# LISTAS (mutáveis)
# =========================================
l1 = ['notebook', 'smartphone', 'tablet']
l2 = ['smartwatch', 'e-reader']

# Concatenar listas (gera uma nova)
l3 = l1 + l2
print('l3 =', l3)

# Repetir lista (repete a sequência)
l2_dup = l2 * 2
print('l2_dup =', l2_dup)

# Indexação e fatiamento (slice)
print('Primeiro de l1:', l1[0])
print('Fatiamento l1[0:2]:', l1[0:2])  # índices 0 e 1

# Inserir elementos
l1.append('desktop')                 # adiciona ao fim
l1.insert(1, 'camera')               # insere na posição 1
l1.extend(['console', 'drone'])      # estende com outra sequência
print('Após inserções:', l1)

# Remover elementos
l1.remove('tablet')                  # remove a primeira ocorrência do valor
removido = l1.pop()                  # remove e retorna o último elemento
print('Removido com pop():', removido)
print('Lista após remoções:', l1)

# Remover por índice específico
del l1[0]                            # apaga o elemento no índice 0
print('Após del l1[0]:', l1)

# Atenção: del l1 (sem índice) apaga a VARIÁVEL inteira.
# Depois disso, usá-la causaria NameError.
# Exemplo (NÃO execute se quiser continuar usando l1):
# del l1

# Percorrer lista
for item in l2_dup:
    print('Item de l2_dup ->', item)


# =========================================
# DICIONÁRIOS (CHAVE -> VALOR)
# =========================================
estoque = {
    'Teclado mecânico': 15,
    'Mouse óptico': 27,
    'Monitor 24"': 8
}

# Acesso por chave
print('Qtd teclados:', estoque['Teclado mecânico'])

# Adicionar/atualizar valores
estoque['Headset USB'] = 12
print('Após adicionar Headset:', estoque)

# Apagar uma chave específica (KeyError se não existir)
del estoque['Mouse óptico']
print('Após remover Mouse óptico:', estoque)

# Obter visão de itens, chaves e valores
print('items():', list(estoque.items()))
print('keys():', list(estoque.keys()))
print('values():', list(estoque.values()))

# Unir/atualizar dicionários
estoque2 = {
    'Webcam FullHD': 10,
    'Monitor 27"': 5
}
print('estoque2:', estoque2)

# Opção 1: update (modifica estoque in-place)
estoque.update(estoque2)
print('Após update com estoque2:', estoque)

# Opção 2 (Python 3.9+): operador de união |
# novo_estoque = estoque | {'Mouse gamer': 6}
# print('Novo estoque (sem modificar o original):', novo_estoque)

# Iterar sobre pares (chave, valor)
for produto, quantidade in estoque.items():
    print(f'Produto: {produto} | Quantidade em estoque: {quantidade}')


# =========================================
# CONJUNTOS (SET) – valores únicos, sem ordem
# =========================================
# Exemplo: eliminar duplicatas de uma sequência
tags = ('tech', 'games', 'tech', 'mobile', 'cloud', 'games', 'cloud')
print('tags (tupla com repetidos):', tags)

conjunto_tags = set(tags)  # remove duplicatas
print('conjunto_tags (sem repetidos):', conjunto_tags)

# Operações entre conjuntos
c1 = {'python', 'javascript', 'go', 'rust'}
c2 = {'go', 'kotlin', 'typescript', 'python'}

# Interseção: elementos comuns aos dois
c_inter = c1.intersection(c2)
print('Interseção c1 ∩ c2 =', c_inter)

# Diferença: elementos que estão em c1 mas não em c2 (e vice-versa)
print('Diferença c1 - c2 =', c1.difference(c2))
print('Diferença c2 - c1 =', c2.difference(c1))

# União: todos os elementos de ambos (sem repetição)
print('União c1 ∪ c2 =', c1.union(c2))

# Diferença simétrica: elementos que estão em um OU em outro, mas não nos dois
print('Diferença simétrica c1 △ c2 =', c1.symmetric_difference(c2))

# Modificações pontuais
conjunto_tags.add('ai')        # adiciona (se já existir, não duplica)
conjunto_tags.discard('games') # remove se existir (sem erro se não houver)
print('conjunto_tags atualizado:', conjunto_tags)


# =========================================
# TABELA COMPARATIVA (comentários)
# -----------------------------------------
# Tipo Python   | Descrição resumida                                 | Analogia em C                               | Analogia em JavaScript
# --------------|-----------------------------------------------------|---------------------------------------------|------------------------
# Lista         | Sequência ordenada, mutável, permite duplicatas     | Vetor dinâmico (malloc/realloc)             | Array (push, pop, splice)
# Tupla         | Sequência ordenada, imutável, permite duplicatas    | Vetor/struct constante (conteúdo fixo)      | Object.freeze([...]) / tuple (TS)
# Dicionário    | Mapeamento (chave → valor), mutável                 | Tabela hash (struct + função de hash)       | Objeto {} ou Map (ES6)
# Conjunto (set)| Coleção de valores únicos, sem ordem                | Tabela hash para conjunto / bitset           | Set (ES6)
# -----------------------------------------
# Observações:
# - Em Python, listas e dicionários são estruturas de alto nível prontas, não
#   exigindo implementação manual de hash/realocação.
# - Escolha a coleção conforme a necessidade: ordem + mutabilidade (lista),
#   imutabilidade (tupla), chave→valor (dict), unicidade (set).