package controller;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import model.Cargo;
import model.Funcionario;

public class ArquivoFuncionario {
    private File caminhoArquivo;

    public ArquivoFuncionario() {
        try {
            File diretorio = new File("arquivos");
            if (!diretorio.exists()) {
                diretorio.mkdir();
            }
            caminhoArquivo = new File(diretorio, "funcionarios.csv");
            if (!caminhoArquivo.exists()) {
                caminhoArquivo.createNewFile();
            }
            if (caminhoArquivo.exists()) {
                System.out.println("Arquivo criado!");
            }
        } catch (IOException erro) {
            System.out.println("ERRO AO CRIAR ARQUIVO!");
            erro.printStackTrace();
            System.exit(1);
        }
    }

    public List<String> listar() {
        List<String> linhasConsulFunc = new ArrayList<String>();
        try (FileReader fr = new FileReader(this.caminhoArquivo);
            BufferedReader buffer = new BufferedReader(fr)) {
            String linha = buffer.readLine();
            while (linha != null) {
                Funcionario funcionario = extrairLinhaCSV(linha);
                linhasConsulFunc.add(formatarLinha(funcionario));
                linha = buffer.readLine();
            }
            return linhasConsulFunc;
        } catch (IOException erro) {
            erro.printStackTrace();
            return null;
        }
    }

   public static String formatarLinha(Funcionario funcionario) {
        return String.format("%s | %d | %s",
            funcionario.getNome(),
            funcionario.getChaveFuncional(),
            funcionario.getCargo().getNome()
        );
   }

    public static Funcionario extrairLinhaCSV(String linhaCSV) {
         String[] camposLinha = linhaCSV.split(";");
         int chaveFuncional = Integer.parseInt(camposLinha[0].trim());
         String nome = camposLinha[1].trim();
         int idCargo = 0;
         double salario = 0;
         try {
             idCargo = Integer.parseInt(camposLinha[2].trim());
             salario = Double.parseDouble(camposLinha[3].trim());
         } catch (NumberFormatException erro) {
            erro.printStackTrace();
         }
         return new Funcionario(nome, chaveFuncional, salario, Cargo.fromIdCargo(idCargo));
    }

    public void inserirRegistro(Funcionario funcionario) {

        String linhaCSV = funcionario.gerarLinhaCSV();

        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(caminhoArquivo, true)); PrintWriter print = new PrintWriter(buffer)) {
            print.append(linhaCSV + "\n");
        } catch (IOException erro) {
            System.out.println("ERRO AO GRAVAR DADOS DO FUNCIONÁRIO!");
            erro.printStackTrace();
        }
    }

    public void excluir(int chaveFuncionario) {
        try {
            Path caminho = Paths.get("arquivos", "funcionarios.csv");
            List<String> linhas = Files.readAllLines(caminho);
            String novoConteudo = "";
            for (int i = 0; i < linhas.size(); i++) {
                String chaveExtraida = linhas.get(i).trim().split(";")[0];
                int chaveExtraidaInt = Integer.parseInt(chaveExtraida);
                if (chaveExtraidaInt != chaveFuncionario) {
                    novoConteudo += linhas.get(i).trim() + "\n";
                }
            }
            Files.delete(caminho);
            Files.writeString(caminho, novoConteudo);
        } catch (IOException erro) {
            erro.printStackTrace();
            System.out.println("Erro ao excluir registro!");
            System.exit(1);
        }
    }

    public void atualizarRegistro(Funcionario funcionario) {
        try {
            Path caminho = Paths.get("arquivos", "funcionarios.csv");
            List<String> linhas = Files.readAllLines(caminho);
            String novoConteudo = "";
            for (int i = 0; i < linhas.size(); i++) {
                String chaveExtraida = linhas.get(i).trim().split(";")[0];
                int chaveExtraidaInt = Integer.parseInt(chaveExtraida);
                if (chaveExtraidaInt != funcionario.getChaveFuncional()) {
                    novoConteudo += linhas.get(i).trim() + "\n";
                } else {
                    novoConteudo += funcionario.gerarLinhaCSV() + "\n";
                }
            }
            Files.delete(caminho);
            Files.writeString(caminho, novoConteudo);
        } catch (IOException erro) {
            erro.printStackTrace();
            System.out.println("Erro ao alterar registro!");
            System.exit(1);
        }
    }
}
