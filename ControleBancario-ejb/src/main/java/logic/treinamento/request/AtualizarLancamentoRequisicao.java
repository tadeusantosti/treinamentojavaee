package logic.treinamento.request;

import javax.xml.bind.annotation.XmlAttribute;

public class AtualizarLancamentoRequisicao extends LancarContasDoMesRequisicao{
    
    @XmlAttribute(required = true)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
