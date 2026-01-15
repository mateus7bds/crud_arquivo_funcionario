package model;
import java.util.Locale;

public class Funcionario {
     private String nome;
     private int chaveFuncional;
     private double salario;
     private Cargo cargo;
     
     public Funcionario() {}

     public Funcionario(String nome, int chaveFuncional, double salario, Cargo cargo) {
        this.nome = nome;
        this.chaveFuncional = chaveFuncional;
        this.salario = salario;
        this.cargo = cargo;
     }

     public String getNome() {
        return this.nome;
     }
     public void setNome(String nome) {
        this.nome = nome;
     }
     public int getChaveFuncional() {
        return this.chaveFuncional;
     }
     public void setChaveFuncional(int chaveFuncional) {
        this.chaveFuncional = chaveFuncional;
     }
     public double getSalario() {
        return this.salario;
     }
     public void setSalario(double salario) {
        this.salario = salario;
     }
     public Cargo getCargo() {
        return this.cargo;
     }
     public void setCargo(int idCargo) {
        if (!(idCargo == 1 || idCargo == 2 || idCargo == 3)) {
            System.out.println("Cargo inválido! Erro ao gravar id do funcionário!");
            this.cargo = Cargo.fromIdCargo(0);
            return;
        }
        this.cargo = Cargo.fromIdCargo(idCargo);
     }
     public String gerarLinhaCSV() {
         return String.format(Locale.US,
            "%d;%s;%d;%.2f",
             this.chaveFuncional,
             this.nome.trim(),
             this.cargo.getIdCargo(),
             this.salario);
     }
}
