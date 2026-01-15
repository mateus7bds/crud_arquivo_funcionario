package view;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import model.Funcionario;
import controller.*;

public class ConsoleArqFunc {

    private InputStreamReader input;
    private static BufferedReader buffer;
    private static ArquivoFuncionario arquivoInterface;

    public ConsoleArqFunc() {
        this.input = new InputStreamReader(System.in);
        buffer = new BufferedReader(input);
        arquivoInterface = new ArquivoFuncionario();
    }

    public static void main(String[] args) {
        ConsoleArqFunc console = new ConsoleArqFunc();
        while (true) {
            System.out.println("""
                ----------------------------------------------
                Gerenciador de funcionários:
                ----------------------------------------------
                    Opções:
                    - 1 Listar
                    - 2 Cadastrar
                    - 3 Atualizar cadastro
                    - 4 Excluir cadastro
                    - 5 Sair
                ----------------------------------------------
            """);
            boolean querSair = false;
            int opcao;
            try {
                System.out.print("Opção: ");
                opcao = Integer.parseInt(buffer.readLine().trim());
            } catch (NumberFormatException | IOException erro) {
                System.out.println("ENTRADA INVÁLIDA! TENTE NOVAMENTE!");
                continue;
            }
            switch (opcao) {
                case 1:
                    listarFuncionarios("Lista de funcionários: ");
                    break;
                case 2:
                    cadastrarFunc();
                    break;
                case 3:
                    atualizarFunc();
                    break;
                case 4:
                    excluirFuncio();
                    break;
                case 5:
                    querSair = true;
                    break;
                default:
                    System.out.println("OPÇÃO INVÁLIDA! TENTE NOVAMENTE");
                    continue;
            }
            if (querSair) {
                break;
            }
        }
    }

    public static void listarFuncionarios(String mensagem) {
        System.out.println("-------------------------------");
        System.out.println(mensagem);
        System.out.println("-------------------------------");
        List<String> funcionarios = arquivoInterface.listar();
        for (int i = 1; i <= funcionarios.size(); i++) {
            System.out.println(i + " | " + funcionarios.get(i - 1));
        }
    }

    public static void cadastrarFunc() {
        Funcionario funcionario = gerarInterfaceEntd(false, 0);
        arquivoInterface.inserirRegistro(funcionario);
        System.out.println("Funcionário cadastrado com sucesso!!!");
    }

    public static void atualizarFunc() {
        listarFuncionarios("Funcionários cadastrados no sistema:");
        System.out.println("------------------------");
        int chaveFunc = receberInteiro("Chave do funcionário a ser alterado: ");
        Funcionario funcionarioAtualizado = gerarInterfaceEntd(true, chaveFunc);
        arquivoInterface.atualizarRegistro(funcionarioAtualizado);
        System.out.println("Funcionário atualizado com sucesso!!!");
    }

    public static void excluirFuncio() {
        listarFuncionarios("Funcionários cadastrados no sistema:");
        System.out.println("------------------------");
        int chaveFunc = receberInteiro("Chave do funcionário a ser alterado: ");
        arquivoInterface.excluir(chaveFunc);
        System.out.println("Funcionário excluído com sucesso!!!");
    }

    public static Funcionario gerarInterfaceEntd(boolean ehAtualizacao, int chaveFuncionalEntd) {
        Funcionario funcionario = new Funcionario();
        String mensagemInicial = "";
        if (ehAtualizacao) {
            mensagemInicial = "Formulário Atualização Funcionário";
        } else {
            mensagemInicial = "Formulário Cadastro Funcionário";
        }
        System.out.println("-------------------------------------------");
        System.out.println(mensagemInicial);
        System.out.println("-------------------------------------------");
        int chaveFuncionalAtual = 0;
        if (ehAtualizacao) {
            chaveFuncionalAtual = chaveFuncionalEntd;
        } else {
            chaveFuncionalAtual = receberInteiro("Chave Funcional (7 dígitos): ");
        }
        funcionario.setChaveFuncional(chaveFuncionalAtual);
        funcionario.setNome(receberString("Nome: "));
        funcionario.setSalario(receberDouble("Salário (Caracter-decimal=.): "));
        System.out.println("""
        ----------------------
        Cargos:
            1 - Assessor
            2 - Gerente
            3 - Especialista
        ----------------------
        """);
        funcionario.setCargo(receberInteiro("Cargo do funcionário: "));
        return funcionario;
    }
    public static String receberString(String label) {
        try {
            System.out.print(label);
            return buffer.readLine().trim();
        } catch (IOException erro) {
            System.out.println("Erro ao receber texto!!!");
            erro.printStackTrace();
            return "";
        }
    }
    public static int receberInteiro(String label) {
        try {
            System.out.print(label);
            return Integer.parseInt(buffer.readLine().trim());
        } catch (IOException | NumberFormatException erro) {
            System.out.println("Erro ao receber inteiro!!!");
            erro.printStackTrace();
            return 0;
        }
    }
    public static double receberDouble(String label) {
        try {
            System.out.print(label);
            return Double.parseDouble(buffer.readLine().trim());
        } catch (IOException | NumberFormatException erro) {
            System.out.println("Erro ao receber número de ponto flutuante!!!");
            erro.printStackTrace();
            return 0d;
        } 
    }
}
