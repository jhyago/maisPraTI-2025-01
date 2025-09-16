import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.util.List;

public class TrabalhandoComArquivos {
    public static void main(String[] args) throws IOException {
//        Path arquivo = Paths.get("teste.txt");
//
//        try {
//            if(!Files.exists(arquivo)) {
//               Files.createFile(arquivo);
//            }
//
//            String conteudo = "Olá, eu sou o conteúdo do arquivo de teste";
//            Files.write(arquivo, conteudo.getBytes());
//
//            System.out.println("Arquivo criado com sucesso e conteúdo gravado.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Path arquivo2 = Paths.get("Arquivo.txt");
//
//        try {
//            List<String> linhas = Files.readAllLines(arquivo2);
//            for(String linha : linhas) {
//                System.out.println(linha);
//            }
//
//            String conteudo2 = Files.readString(arquivo2);
//            System.out.println("Conteúdo do arquivo: " + conteudo2);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        Path diretorio = Paths.get("diretorioExemplo");
//
//        try {
//            Files.list(diretorio).forEach(path -> {
//                System.out.println(path);
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Path origem = Paths.get("Arquivo.txt");
//        Path destino = Paths.get("mudeiNome.txt");
//
//        Files.move(origem, destino);
        Path path = Paths.get("mudeiNome.txt");
        try {
            BufferedReader br = Files.newBufferedReader(path);
            BufferedWriter bw = Files.newBufferedWriter(path);

            String linha = br.readLine();

            while (linha != null) {
                System.out.println(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
