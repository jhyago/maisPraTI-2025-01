from datetime import date

from models.livro import Livro
from models.filme import Filme
from models.revista import Revista
from models.pessoa import Membro
from services.emprestimos import EmprestimoService

def main():
    # --- criar acervo (polimorfismo: todos são ItemMidia) ---
    livro = Livro("Clean Code", 2008, autor="Robert C. Martin", paginas=464)
    filme = Filme("A Origem", 2010, diretor="Christopher Nolan", duracao_min=148)
    revista = Revista("Ciência Hoje", 2024, edicao="Set/2024")

    print(livro.resumo())
    print(filme.resumo())
    print(revista.resumo())
    print("---")

    # --- criar membros ---
    maria = Membro("Maria de Souza", "111.222.333-44", limite_emprestimos=2)
    joao = Membro("João Almeida", "55566677788", limite_emprestimos=1)

    print(maria.resumo())
    print(joao.resumo())
    print("---")

    # --- serviço de empréstimos ---
    service = EmprestimoService()

    e1 = service.emprestar(maria, livro, date(2025, 10, 20))
    e2 = service.emprestar(maria, revista, date(2025, 10, 21))
    print(e1)
    print(e2)
    print("Abertos:", service.listar_abertos())
    print(maria.resumo())
    print("---")

    # João tenta pegar o mesmo livro (deve falhar)
    try:
        service.emprestar(joao, livro, date(2025, 10, 22))
    except RuntimeError as err:
        print("Falha esperada (livro indisponível):", err)

    # João pega o filme
    e3 = service.emprestar(joao, filme, date(2025, 10, 22))
    print(e3)
    print(joao.resumo())
    print("---")

    # Maria devolve a revista
    service.devolver(e2)
    print("Após devolução da revista:")
    print("Abertos:", service.listar_abertos())
    print(maria.resumo())
    print("---")

    # Agora João tenta pegar a revista — deve funcionar
    e4 = service.emprestar(joao, revista, date(2025, 10, 23))
    print(e4)
    print(joao.resumo())
    print("---")

    # listagem final
    print("TODOS:", service.listar_todos())

if __name__ == "__main__":
    main()