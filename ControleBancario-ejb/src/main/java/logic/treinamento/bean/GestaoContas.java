package logic.treinamento.bean;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.sql.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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

    @Resource
    private SessionContext context;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void lancarContasDoMes(LancarContasDoMesRequisicao lancarContasDoMesRequisicao) throws Exception {
        try {

            Lancamento lanc = new Lancamento();
            lanc.setNome(lancarContasDoMesRequisicao.getNome());
            lanc.setValor(lancarContasDoMesRequisicao.getValor());
            lanc.setTipoLancamento(TipoLancamentoEnum.getByCodigo(lancarContasDoMesRequisicao.getIdTipoLancamento()));
            lanc.setData(Formatadores.validarDatasInformadas(lancarContasDoMesRequisicao.getData()).get(0));

            String retornoValidacao = validarCamposObrigatorios(lanc);

            if (retornoValidacao.isEmpty()) {
                lancamentoDao.salvarContasDoMes(lanc);
            }
        } catch (Exception e) {
            context.setRollbackOnly();
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void atualizarLancamento(AtualizarLancamentoRequisicao atualizarLancamentoRequisicao) throws Exception {
        try {
            Lancamento lanc = new Lancamento();
            lanc.setId(atualizarLancamentoRequisicao.getId());
            lanc.setNome(atualizarLancamentoRequisicao.getNomeAtualizado());
            lanc.setValor(atualizarLancamentoRequisicao.getValorAtualizado());
            lanc.setTipoLancamento(TipoLancamentoEnum.getByCodigo(atualizarLancamentoRequisicao.getIdTipoLancamentoAtualizado()));
            lanc.setData(Formatadores.validarDatasInformadas(atualizarLancamentoRequisicao.getDataAtualizada()).get(0));

            String retornoValidacao = validarCamposObrigatoriosAtualizacao(lanc);

            if (retornoValidacao.equals("")) {
                lancamentoDao.atualizarLancamento(lanc);
            }
        } catch (Exception e) {
            context.setRollbackOnly();
        }

    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void excluirLancamento(int idLancamento) throws SQLException {
        try {
            if (idLancamento >= 0) {
                lancamentoDao.excluirLancamento(idLancamento);
                System.out.println("Lancamento Excluido com Sucesso!");
            } else {
                System.out.println("E necessario informar o codigo do lancamento!");
            }
        } catch (SQLException e) {
            context.setRollbackOnly();
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
        if (!validarTipoLancamentoInformado(TipoLancamentoEnum.getByCodigo(idtipolancamento))) {
            return lancamentoDao.pesquisarLancamentoPorTipoDeLancamento(TipoLancamentoEnum.getByCodigo(idtipolancamento));
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
        } else if (lanc.getTipoLancamento() != TipoLancamentoEnum.DEPOSITO
                && lanc.getTipoLancamento() != TipoLancamentoEnum.SAQUE
                && lanc.getTipoLancamento() != TipoLancamentoEnum.TRANSFERENCIA) {
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
        } else if (validarTipoLancamentoInformado(lanc.getTipoLancamento())) {
            return "E necessario informar um tipo de lancamento !";
        } else if (lanc.getData() == null) {
            return "E necessario informar a data do lancamento !";
        } else {
            return "";
        }

    }

    private boolean validarTipoLancamentoInformado(TipoLancamentoEnum tipoLancamento) {
        if (tipoLancamento.getId() < 0) {
            return true;
        } else {
            return false;
        }
    }

}
