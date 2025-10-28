from datetime import date, timedelta
from typing import Dict, List

from ..models.base import ItemMidia
from ..models.pessoa import Membro

class Emprestimo:
    """
    Entidade simples representando um empréstimo.
    """
    def __init__(self, membro: Membro, item: ItemMidia, data: date, prazo_dias: int):
        self.membro = membro
        self.item = item
        self.data = data
        self.data_devolucao = data + timedelta(days=prazo_dias)
        self.devolvido = False

    def marcar_devolvido(self):
        self.devolvido = True

    def __repr__(self) -> str:
        status = "Devolvido" if self.devolvido else "Em aberto"
        return f"<Emprestimo {self.item.etiqueta()} → {self.membro.nome} | {status} | até {self.data_devolucao}>"

class EmprestimoService:
    """
    Serviço de regras de empréstimo.
    - Composição: usa Membro e ItemMidia, mas não herda deles.
    - Encapsula a lista interna de empréstimos.
    """
    def __init__(self):
        self._emprestimos: List[Emprestimo] = []

    def emprestar(self, membro: Membro, item: ItemMidia, quando: date) -> Emprestimo:
        if not membro.pode_emprestar():
            raise RuntimeError("Membro não pode emprestar: limite atingido.")
        if not item.disponivel:
            raise RuntimeError("Item indisponível para empréstimo.")

        prazo = item.prazo_devolucao_em_dias()
        emp = Emprestimo(membro, item, quando, prazo)

        membro.registrar_emprestimo()
        item._marcar_indisponivel()  # uso consciente de atributo "protegido" (regra centralizada no serviço)
        self._emprestimos.append(emp)
        return emp

    def devolver(self, emprestimo: Emprestimo) -> None:
        if emprestimo.devolvido:
            return
        emprestimo.membro.registrar_devolucao()
        emprestimo.item._marcar_disponivel()
        emprestimo.marcar_devolvido()

    def listar_abertos(self) -> List[Emprestimo]:
        return [e for e in self._emprestimos if not e.devolvido]

    def listar_todos(self) -> List[Emprestimo]:
        return list(self._emprestimos)
