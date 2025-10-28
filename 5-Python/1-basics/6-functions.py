# ----------------------------------------------------------------------
# Funções
#
#  - São blocos nomeados que podem ser reutilizados em vários pontos.
#  - Benefícios: reutilização, modularidade, legibilidade e manutenção.
#  - Em Python, definimos funções com 'def' e podemos:
#      * receber parâmetros (entrada)
#      * retornar valores (saída)
#      * definir parâmetros com valores padrão (opcionais)
#      * documentar com docstrings (help)
# ----------------------------------------------------------------------

# Função sem parâmetros e sem retorno
def mensagem():
    """Imprime um texto fixo apenas para demonstrar chamada de função."""
    print('Bem-vindo(a) ao módulo de funções!')

mensagem()
mensagem()
mensagem()

# -------------------------------------------------------------
# Mesma ideia, agora COM passagem de parâmetro
# -------------------------------------------------------------
def mensagem(texto):
    """Recebe um texto e imprime na tela."""
    print(texto)

mensagem('Primeira chamada com argumento')
mensagem('Segunda chamada com argumento')
mensagem('Terceira chamada com argumento')

# -------------------------------------------------------------
# Função que apenas IMPRIME a soma (sem retorno)
# (Note que redefinir um nome de função substitui a anterior.)
# -------------------------------------------------------------
def soma(a, b):
    """Imprime a soma de a e b (não retorna o resultado)."""
    print(a + b)

soma(10, 5)
soma(2, 3)

# -------------------------------------------------------------
# Agora, definimos outra 'soma' que RETORNA o valor.
# Isso sobrescreve a definição anterior de 'soma'.
# -------------------------------------------------------------
def soma(a, b):
    """Retorna a soma de a e b (não imprime)."""
    return a + b

# A função retorna; podemos guardar em variável ou usar direto
resultado = soma(7, 4)
print('Resultado da nova soma:', resultado)

# -------------------------------------------------------------
# Parâmetro OPCIONAL (valor padrão) + docstring
# Exemplo: Energia Potencial Gravitacional: E = m * g * h
# -------------------------------------------------------------
def energia_potencial_gravitacional(massa: float, altura: float, g: float = 9.81) -> float:
    """
    Calcula a energia potencial gravitacional (em Joules).

    Parâmetros:
      massa  (float): massa do corpo (kg)
      altura (float): altura em relação ao nível de referência (m)

    Parâmetro opcional:
      g      (float): aceleração da gravidade (m/s^2). Padrão = 9.81

    Retorna:
      float: valor de energia potencial gravitacional (J)
    """
    return massa * g * altura

print('Epg (g padrão):', energia_potencial_gravitacional(50, 2.5))
print('Epg (g=10.0):', energia_potencial_gravitacional(50, 2.5, g=10.0))

# 'help' lê a docstring e mostra a documentação da função
help(energia_potencial_gravitacional)

# ======================================================================
# Exercícios
# ======================================================================

# 1) Conversão de temperatura (Celsius -> Fahrenheit)
#    Fórmula: F = (9 * C + 160) / 5
#    - Função para ler a temperatura (sem parâmetros; retorna float)
#    - Função para converter (recebe C; retorna F)
#    - Função para mostrar o resultado (recebe F; apenas imprime)
def ler_temperatura_c():
    """Lê uma temperatura em °C do usuário e retorna como float."""
    return float(input('Digite a temperatura em °C: '))

def celsius_para_fahrenheit(celsius: float) -> float:
    """Converte °C para °F usando F = (9 * C + 160) / 5."""
    return (9 * celsius + 160) / 5

def mostrar_fahrenheit(fahrenheit: float) -> None:
    """Mostra a temperatura em °F."""
    print(f'Temperatura em Fahrenheit: {fahrenheit:.2f} °F')

# Descomente para executar:
# c = ler_temperatura_c()
# f = celsius_para_fahrenheit(c)
# mostrar_fahrenheit(f)

# 2) Consumo de combustível em viagem
#    - Carro faz 12 km/L (padrão), mas deixaremos isso como PARÂMETRO opcional
#      para reforçar o conceito.
#    - Ler tempo (h) e velocidade média (km/h)
#    - Distância = tempo * velocidade
#    - Litros usados = distância / consumo_km_por_litro
#    - Imprimir velocidade, tempo, distância e litros
def ler_dados_viagem():
    """Lê tempo (h) e velocidade média (km/h) e retorna ambos."""
    tempo_h = float(input('Tempo da viagem (h): '))
    velocidade_kmh = float(input('Velocidade média (km/h): '))
    return tempo_h, velocidade_kmh

def calcular_distancia(tempo_h: float, velocidade_kmh: float) -> float:
    """Retorna a distância percorrida em km."""
    return tempo_h * velocidade_kmh

def calcular_litros(distancia_km: float, consumo_km_por_litro: float = 12.0) -> float:
    """Retorna os litros consumidos, dado o consumo (km/L)."""
    return distancia_km / consumo_km_por_litro

def imprimir_relatorio_viagem(velocidade_kmh: float, tempo_h: float, distancia_km: float, litros: float) -> None:
    """Imprime um resumo da viagem."""
    print('--- Relatório da Viagem ---')
    print(f'Velocidade média: {velocidade_kmh:.1f} km/h')
    print(f'Tempo gasto:      {tempo_h:.2f} h')
    print(f'Distância:        {distancia_km:.2f} km')
    print(f'Combustível:      {litros:.2f} L (consumo padrão 12 km/L)')

# Descomente para executar:
# tempo, velocidade = ler_dados_viagem()
# distancia = calcular_distancia(tempo, velocidade)
# litros = calcular_litros(distancia)  # usa consumo padrão=12 km/L
# imprimir_relatorio_viagem(velocidade, tempo, distancia, litros)