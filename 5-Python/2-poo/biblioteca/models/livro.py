from .base import ItemMidia

class Livro(ItemMidia):
    def __init__(self, titulo: str, ano: int, autor: str, paginas: int):
        super().__init__(titulo, ano)
        self.autor = autor.strip()
        self.paginas = int(paginas)

    def prazo_devolucao_em_dias(self) -> int:
        return 14  # livros: 14 dias

    def resumo(self) -> str:
        return f"[{self.id_interno}] Livro: {self.titulo} ({self.ano}) — {self.autor}, {self.paginas} págs"
