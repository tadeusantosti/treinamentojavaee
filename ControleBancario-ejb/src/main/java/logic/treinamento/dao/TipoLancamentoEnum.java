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

    public static TipoLancamentoEnum getByCodigo(int codigoID) {
        if (codigoID <= 0) {
            return null;
        }
        for (TipoLancamentoEnum tipoLanc : TipoLancamentoEnum.values()) {
            if (tipoLanc.id == codigoID) {
                return tipoLanc;
            }
        }
        return null;
    }
}
