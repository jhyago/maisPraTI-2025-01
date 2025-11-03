from .base import ItemMidia

class Filme(ItemMidia):
    def __init__(self, titulo: str, ano: int, diretor: str, duracao_min: int):
        super().__init__(titulo, ano)
        self.diretor = diretor.strip()
        self.duracao_min = int(duracao_min)

    def prazo_devolucao_em_dias(self) -> int:
        return 5  # filmes: 5 dias

    def resumo(self) -> str:
        return f"[{self.id_interno}] Filme: {self.titulo} ({self.ano}) — Dir.: {self.diretor}, {self.duracao_min} min"
