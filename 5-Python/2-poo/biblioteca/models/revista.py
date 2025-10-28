from .base import ItemMidia

class Revista(ItemMidia):
    def __init__(self, titulo: str, ano: int, edicao: str):
        super().__init__(titulo, ano)
        self.edicao = edicao.strip()

    def prazo_devolucao_em_dias(self) -> int:
        return 7  # revistas: 7 dias

    def resumo(self) -> str:
        return f"[{self.id_interno}] Revista: {self.titulo} ({self.ano}) — Edição {self.edicao}"
