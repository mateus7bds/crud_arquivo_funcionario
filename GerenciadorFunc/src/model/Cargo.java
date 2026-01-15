package model;
public enum Cargo {

    INDEFINIDO(0, "Indefinido"),
    ASSESSOR(1, "Assessor"),
    GERENTE(2, "Gerente"),
    ESPECIALISTA(3, "Especialista");

    private final int idCargo;
    private final String nome;

    private Cargo(int idCargo, String nome) {
        this.idCargo = idCargo;
        this.nome = nome;
    }

    public int getIdCargo() {
        return idCargo;
    }

    public String getNome() {
        return nome;
    }

    public static Cargo fromIdCargo(int idCargo) {
        for (Cargo cargo: values()) {
            if (cargo.idCargo == idCargo) {
                return cargo;
            }
        }
        throw new IllegalArgumentException("ID de cargo inválido (" + idCargo + ")");
    }
}
