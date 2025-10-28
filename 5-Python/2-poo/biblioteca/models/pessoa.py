class Pessoa:
    """
    Superclasse para pessoas.
    Encapsula um identificador sensível (documento) com name mangling.
    """
    def __init__(self, nome: str, documento: str):
        self.nome = nome.strip()
        self.__documento = documento.strip()  # "privado": _Pessoa__documento

    @property
    def documento_parcial(self) -> str:
        """Exposição parcial (encapsulamento)."""
        tail = self.__documento[-3:] if len(self.__documento) >= 3 else self.__documento
        return f"***{tail}"

    # Poderíamos adicionar validações reais aqui
    def _raw_documento(self) -> str:
        return self.__documento

    def __repr__(self) -> str:
        return f"Pessoa(nome='{self.nome}', doc='{self.documento_parcial}')"


class Membro(Pessoa):
    """
    Subclasse de Pessoa, representa usuários do acervo.
    Encapsula status/limites e oferece operações de alto nível.
    """
    def __init__(self, nome: str, documento: str, limite_emprestimos: int = 3):
        super().__init__(nome, documento)
        self._limite_emprestimos = int(limite_emprestimos)
        self._ativos = 0  # quantos empréstimos ativos

    @property
    def limite_emprestimos(self) -> int:
        return self._limite_emprestimos

    def pode_emprestar(self) -> bool:
        return self._ativos < self._limite_emprestimos

    def registrar_emprestimo(self) -> None:
        if not self.pode_emprestar():
            raise RuntimeError("Limite de empréstimos atingido.")
        self._ativos += 1

    def registrar_devolucao(self) -> None:
        if self._ativos > 0:
            self._ativos -= 1

    def resumo(self) -> str:
        return f"Membro: {self.nome} ({self.documento_parcial}) — Ativos: {self._ativos}/{self._limite_emprestimos}"