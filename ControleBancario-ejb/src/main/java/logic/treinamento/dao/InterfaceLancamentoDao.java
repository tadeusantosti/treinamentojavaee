package logic.treinamento.dao;

import logic.treinamento.model.Lancamento;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface InterfaceLancamentoDao {

    public void salvarContasDoMes(Lancamento lanc) throws SQLException;

    public void atualizarLancamento(Lancamento lanc) throws SQLException;

    public void excluirLancamento(long idLancamento) throws SQLException;

    public List<Lancamento> pesquisarLancamentoPorPeriodo(Date dataInicial, Date dataFinal) throws SQLException;

    public List<Lancamento> pesquisarLancamentoPorNome(String nome) throws SQLException;

    public List<Lancamento> pesquisarLancamentoPorTipoDeLancamento(TipoLancamentoEnum tipoLancamento) throws SQLException;
}
