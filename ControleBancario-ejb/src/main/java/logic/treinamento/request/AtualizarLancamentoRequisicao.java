package logic.treinamento.request;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AtualizarLancamentoRequisicao")
@XmlAccessorType(XmlAccessType.FIELD)
public class AtualizarLancamentoRequisicao implements Serializable {

    @XmlElement
    private long id;

    @XmlElement
    private String nomeAtualizado;

    @XmlElement
    private BigDecimal valorAtualizado;

    @XmlElement
    private int idTipoLancamentoAtualizado;

    @XmlElement
    private String dataAtualizada;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeAtualizado() {
        return nomeAtualizado;
    }

    public void setNomeAtualizado(String nomeAtualizado) {
        this.nomeAtualizado = nomeAtualizado;
    }

    public BigDecimal getValorAtualizado() {
        return valorAtualizado;
    }

    public void setValorAtualizado(BigDecimal valorAtualizado) {
        this.valorAtualizado = valorAtualizado;
    }

    public int getIdTipoLancamentoAtualizado() {
        return idTipoLancamentoAtualizado;
    }

    public void setIdTipoLancamentoAtualizado(int idTipoLancamentoAtualizado) {
        this.idTipoLancamentoAtualizado = idTipoLancamentoAtualizado;
    }

    public String getDataAtualizada() {
        return dataAtualizada;
    }

    public void setDataAtualizada(String dataAtualizada) {
        this.dataAtualizada = dataAtualizada;
    }
}
