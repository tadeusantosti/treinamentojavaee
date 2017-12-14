package logic.treinamento.bean;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.sql.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import logic.treinamento.dao.InterfaceLancamentoDao;
import logic.treinamento.model.Lancamento;
import logic.treinamento.dao.TipoLancamentoEnum;
import logic.treinamento.request.AtualizarLancamentoRequisicao;
import logic.treinamento.request.LancarContasDoMesRequisicao;
import utilitarios.Formatadores;

@Stateless
public class GestaoContas implements InterfaceGestaoContas {

    @EJB
    private InterfaceLancamentoDao lancamentoDao;

    @Override
    public void lancarContasDoMes(LancarContasDoMesRequisicao lancarContasDoMesRequisicao) throws Exception {

        Lancamento lanc = new Lancamento();
        lanc.setNome(lancarContasDoMesRequisicao.getNome());
        lanc.setValor(lancarContasDoMesRequisicao.getValor());
        lanc.setIdTipoLancamento(lancarContasDoMesRequisicao.getIdTipoLancamento());
        lanc.setData(Formatadores.validarDatasInformadas(lancarContasDoMesRequisicao.getData()).get(0));
        
        String retornoValidacao = validarCamposObrigatorios(lanc);

        if (retornoValidacao.isEmpty()) {
            lancamentoDao.salvarContasDoMes(lanc);            
        }
    }

    @Override
    public void atualizarLancamento(AtualizarLancamentoRequisicao atualizarLancamentoRequisicao) throws Exception {
        Lancamento lanc = new Lancamento();
        lanc.setId(atualizarLancamentoRequisicao.getId());
        lanc.setNome(atualizarLancamentoRequisicao.getNomeAtualizado());
        lanc.setValor(atualizarLancamentoRequisicao.getValorAtualizado());
        lanc.setIdTipoLancamento(atualizarLancamentoRequisicao.getIdTipoLancamentoAtualizado());
        lanc.setData(Formatadores.validarDatasInformadas(atualizarLancamentoRequisicao.getDataAtualizada()).get(0));
        
        String retornoValidacao = validarCamposObrigatoriosAtualizacao(lanc);

        if (retornoValidacao.equals("")) {
            lancamentoDao.atualizarLancamento(lanc);         
        }

    }

    @Override
    public void excluirLancamento(int idLancamento) throws SQLException {
        if (idLancamento >= 0) {
            lancamentoDao.excluirLancamento(idLancamento);
            System.out.println("Lancamento Excluido com Sucesso!");
        } else {
            System.out.println("E necessario informar o codigo do lancamento!");
        }
    }

    @Override
    public List<Lancamento> pesquisarLancamentoPorPeriodo(String dataInicial, String dataFinal) throws Exception {
        List<Date> datas = Formatadores.validarDatasInformadas(dataInicial, dataFinal);
        return lancamentoDao.pesquisarLancamentoPorPeriodo(datas.get(0), datas.get(1));
    }

    @Override
    public List<Lancamento> pesquisarLancamentoPorNome(String nome) throws SQLException {
        if (!nome.isEmpty()) {
            return lancamentoDao.pesquisarLancamentoPorNome(nome);
        } else {
            return null;
        }
    }

    @Override
    public List<Lancamento> pesquisarLancamentoPorTipoDeLancamento(int idtipolancamento) throws SQLException {
        if (!validarTipoLancamentoInformado(idtipolancamento)) {
            return lancamentoDao.pesquisarLancamentoPorTipoDeLancamento(idtipolancamento);
        } else {
            return null;
        }
    }

    @Override
    public String validarCamposObrigatorios(Lancamento lanc) {

        if (lanc.getNome() == null || lanc.getNome().isEmpty()) {
            return "E necessario informar o nome !";
        } else if (lanc.getValor() == null || lanc.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            return "E necessario informar um valor !";
        } else if (lanc.getIdTipoLancamento() != TipoLancamentoEnum.DEPOSITO.getId()
                && lanc.getIdTipoLancamento() != TipoLancamentoEnum.SAQUE.getId()
                && lanc.getIdTipoLancamento() != TipoLancamentoEnum.TRANSFERENCIA.getId()) {
            return "E necessario informar um tipo de lancamento Valido !";
        } else if (lanc.getData() == null) {
            return "E necessario informar a data do lancamento !";
        } else {
            return "";
        }

    }

    private String validarCamposObrigatoriosAtualizacao(Lancamento lanc) {

        if (lanc.getId() < 0) {
            return "E necessario informar o codigo do lancamento !";
        } else if (lanc.getNome().isEmpty()) {
            return "E necessario informar o nome !";
        } else if (lanc.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            return "E necessario informar um valor !";
        } else if (validarTipoLancamentoInformado(lanc.getIdTipoLancamento())) {
            return "E necessario informar um tipo de lancamento !";
        } else if (lanc.getData() == null) {
            return "E necessario informar a data do lancamento !";
        } else {
            return "";
        }

    }

    private boolean validarDatasInformadas(Date dataInicial, Date dataFinal) throws ParseException {
        if (dataInicial != null || dataFinal != null) {
            return false;
        } else if (false) {
            return false;
        } else {
            return true;
        }
    }

    private boolean validarTipoLancamentoInformado(int idtipolancamento) {
        if (idtipolancamento < 0) {
            return true;
        } else {
            return false;
        }
    }

}
