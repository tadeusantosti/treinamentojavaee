package logic.treinamento.request;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "LancarContasDoMesRequisicao")
@XmlAccessorType(XmlAccessType.FIELD)
public class LancarContasDoMesRequisicao implements Serializable {

    
    @XmlElement
    private String nome;
    
    @XmlElement
    private BigDecimal valor;
    
    @XmlElement
    private int idTipoLancamento;
    
    @XmlElement
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
