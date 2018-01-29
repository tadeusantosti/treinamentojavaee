package logic.treinamento.dao;

public enum TipoLancamentoEnum {
    TRANSFERENCIA(1),
    SAQUE(2),
    DEPOSITO(3);

    private final int id;

    TipoLancamentoEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
