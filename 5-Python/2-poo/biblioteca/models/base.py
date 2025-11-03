from abc import ABC, abstractmethod
from datetime import date
from typing import Optional

class ItemMidia(ABC):
    """
    Superclasse abstrata para itens de acervo (livro, filme, revista).
    Encapsulamento:
      - __id_interno: "privado" (name mangling), não deve ser acessado diretamente.
      - _disponivel: "protegido" (convenção), usado internamente/por subclasses.
    """

    _proximo_id = 1  # contador simples para gerar IDs

    def __init__(self, titulo: str, ano: int):
        self.titulo = titulo.strip()
        self.ano = int(ano)
        self._disponivel = True
        self.__id_interno = ItemMidia._proximo_id
        ItemMidia._proximo_id += 1

    # Propriedade somente-leitura para o ID "privado"
    @property
    def id_interno(self) -> int:
        return self.__id_interno

    # Disponibilidade com property (poderíamos validar regras no futuro)
    @property
    def disponivel(self) -> bool:
        return self._disponivel

    def _marcar_indisponivel(self) -> None:
        self._disponivel = False

    def _marcar_disponivel(self) -> None:
        self._disponivel = True

    # Polimorfismo: cada tipo de mídia define o prazo de devolução
    @abstractmethod
    def prazo_devolucao_em_dias(self) -> int:
        ...

    # Ganchos opcionais para relatórios
    def resumo(self) -> str:
        return f"[{self.id_interno}] {self.titulo} ({self.ano})"

    # Exemplo de método “comportamental” genérico
    def etiqueta(self) -> str:
        return f"{self.__class__.__name__.upper()} • {self.titulo} • ID {self.id_interno}"