package logic.treinamento.bean;

import java.util.List;
import javax.ejb.Local;
import logic.treinamento.model.Lancamento;
import logic.treinamento.request.AtualizarLancamentoRequisicao;
import logic.treinamento.request.LancarContasDoMesRequisicao;

@Local
public interface InterfaceGestaoContas {

    public void lancarContasDoMes(LancarContasDoMesRequisicao lancarContasDoMesRequisicao) throws Exception;
    
    public List<Lancamento> pesquisarLancamentoPorTipoDeLancamento(int idtipolancamento) throws Exception;

    public List<Lancamento> pesquisarLancamentoPorNome(String nome) throws Exception;

    public List<Lancamento> pesquisarLancamentoPorPeriodo(String dataInicial, String dataFinal) throws Exception;

    public void excluirLancamento(int idLancamento) throws Exception;

    public void atualizarLancamento(AtualizarLancamentoRequisicao atualizarLancamentoRequisicao) throws Exception;

    public String validarCamposObrigatorios(Lancamento lanc);


}
