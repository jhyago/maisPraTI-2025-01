package exercicioPraticaCollection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("João", 3500, "Desenvolvedor Júnior", 23));
        funcionarios.add(new Funcionario("Maria", 4500, "Desenvolvedora Pleno", 25));
        funcionarios.add(new Funcionario("Jucilei", 7500, "Desenvolvedor Sênior", 30));
        funcionarios.add(new Funcionario("Adrian", 500, "Vampiro", 1000));
        funcionarios.add(new Funcionario("Alucard", 13500, "Elfo", 2000));
        funcionarios.add(new Funcionario("Cloud", 1500, "Soldier", 23));
        funcionarios.add(new Funcionario("Arthur Morgan", 15000, "Fora da lei", 34));
        funcionarios.add(new Funcionario("Peter Parker", 1200, "Fotógrafo", 23));
        funcionarios.add(new Funcionario("Bruce Wayne", 23500, "CEO", 40));
        funcionarios.add(new Funcionario("Mary Jane", 2000, "Atriz", 23));

        //Filtre e exiba os nomes e cargos dos funcionários com mais de 30 anos.
        System.out.println("Funcionários com mais de 30 anos: ");
        funcionarios
                .stream()
                .filter(f -> f.getIdade() > 30)
                .forEach(f -> System.out.println(f.toString()));
        //Ordene os funcionários por salário em ordem crescente e exiba os nomes e salários.
        System.out.println("\nFuncionários ordenados por salário: ");
        funcionarios.stream()
                .sorted(Comparator.comparingDouble(Funcionario::getSalario))
                .forEach(f -> System.out.println(f.getNome() + " - " + f.getSalario()));
        //Calcule e exiba a média salarial dos funcionários.
        double mediaSalarial = funcionarios.stream()
                .mapToDouble(Funcionario::getSalario)
                .average()
                .orElse(0.0);
        System.out.println("\nMédia Salarial dos Funcionários é: Dinheiros$ " + mediaSalarial);

        //Exiba o total de funcionários com salário superior a R$ 4.000,00.
        long totAcima4000 = funcionarios.stream()
                .filter(f -> f.getSalario() > 4000)
                .count();
        System.out.println("\nTotal de funcionários com salário superior a R$ 40000: " + totAcima4000);

        //Crie uma lista com os nomes dos funcionários e a exiba.
        List<String> nomes = funcionarios.stream()
                .map(Funcionario::getNome)
                .collect(Collectors.toList());
        System.out.println("\nNomes dos funcionários: " + nomes);
        nomes.forEach(System.out::println);
    }
}
