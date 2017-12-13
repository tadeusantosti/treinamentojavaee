package logic.treinamento;

import logic.treinamento.request.LancarContasDoMesRequisicao;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.ws.rs.core.Response;
import logic.treinamento.bean.InterfaceGestaoContas;
import logic.treinamento.model.Lancamento;
import logic.treinamento.request.AtualizarLancamentoRequisicao;

@WebService(name = "gestaoContas")
public class WebServiceGestaoContas {

    @EJB
    private InterfaceGestaoContas gestaoContaBean;

    @WebMethod(operationName = "lancarContasDoMes")
    @WebResult(name = "resultado")
    public String lancarContasDoMes(@WebParam(name = "requisicao") LancarContasDoMesRequisicao lancarContasDoMesRequisicao) throws Exception {
        gestaoContaBean.lancarContasDoMes(lancarContasDoMesRequisicao);
        return Response.Status.OK.getReasonPhrase();
    }

    @WebMethod(operationName = "atualizarLancamento")
    @WebResult(name = "Lancamento")
    public String atualizarLancamento(@WebParam(name = "nome") AtualizarLancamentoRequisicao atualizarLancamentoRequisicao) throws Exception {
        gestaoContaBean.atualizarLancamento(atualizarLancamentoRequisicao);
        return Response.Status.OK.getReasonPhrase();
    }

    @WebMethod(operationName = "pesquisarPorTipoLancamento")
    @WebResult(name = "Lancamento")
    public List<Lancamento> pesquisarPorTipoLancamento(@WebParam(name = "tipoLancamento") int idtipolancamento) throws Exception {
        return gestaoContaBean.pesquisarLancamentoPorTipoDeLancamento(idtipolancamento);
    }

    @WebMethod(operationName = "excluirLancamento")
    @WebResult(name = "Lancamento")
    public String excluirLancamento(@WebParam(name = "idLancamento") int idLancamento) throws Exception {
        gestaoContaBean.excluirLancamento(idLancamento);
        return Response.Status.OK.getReasonPhrase();
    }


    @WebMethod(operationName = "pesquisarLancamentoPeriodo")
    @WebResult(name = "Lancamento")
    public List<Lancamento> pesquisarLancamentoPorPeriodo(@WebParam(name = "dataInicial") String dataInicial, @WebParam(name = "dataFinal") String dataFinal) throws Exception {
        return gestaoContaBean.pesquisarLancamentoPorPeriodo(dataInicial, dataFinal);
    }

    @WebMethod(operationName = "pesquisarLancamentoPorNome")
    @WebResult(name = "Lancamento")
    public List<Lancamento> pesquisarLancamentoPorNome(@WebParam(name = "nome") String nome) throws Exception {
        return gestaoContaBean.pesquisarLancamentoPorNome(nome);
    }
}
