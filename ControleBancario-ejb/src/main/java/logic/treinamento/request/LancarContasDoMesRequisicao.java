package logic.treinamento.request;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAttribute;

public class LancarContasDoMesRequisicao implements Serializable {

    
    @XmlAttribute(required = true)
    private String nome;
    @XmlAttribute(required = true)
    private BigDecimal valor;
    @XmlAttribute(required = true)
    private int idTipoLancamento;
    @XmlAttribute(required = true)
    private String data;

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

    public int getIdTipoLancamento() {
        return idTipoLancamento;
    }

    public void setIdTipoLancamento(int idTipoLancamento) {
        this.idTipoLancamento = idTipoLancamento;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
