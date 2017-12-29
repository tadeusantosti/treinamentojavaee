package logic.treinamento.model;

import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import logic.treinamento.dao.TipoLancamentoEnum;

@Entity
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome, dataGUI;
    private Date data;
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private TipoLancamentoEnum tipoLancamento;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public TipoLancamentoEnum getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(TipoLancamentoEnum tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDataGUI() {
        return dataGUI;
    }

    public void setDataGUI(String dataGUI) {
        this.dataGUI = dataGUI;
    }
}
