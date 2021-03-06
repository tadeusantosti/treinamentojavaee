package logic.treinamento.dao;

import java.math.RoundingMode;
import logic.treinamento.model.Lancamento;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import utilitarios.Formatadores;

@Stateless
public class LancamentoDao implements InterfaceLancamentoDao {

    @Resource(mappedName = "java:/dbControleBancario")
    private DataSource dataSource;

    @Override
    public void salvarContasDoMes(Lancamento lanc) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        StringBuilder sql = null;

        try {
            con = dataSource.getConnection();
            sql = new StringBuilder();
            sql.append("\n INSERT INTO lancamento (nome, data, valor, idtipolancamento)");
            sql.append("\n VALUES (?, ?, ?, ?)");
            stm = con.prepareStatement(sql.toString());
            stm.setString(1, lanc.getNome());
            stm.setDate(2, lanc.getData());
            stm.setBigDecimal(3, lanc.getValor());
            stm.setInt(4, lanc.getIdTipoLancamento());
            stm.execute();
        } finally {            
            con.close();
            stm.close();
        }
    }

    @Override
    public void atualizarLancamento(Lancamento lanc) throws SQLException {
        Connection con = null;
        PreparedStatement pstm = null;
        StringBuilder sql = null;

        try {
            con = dataSource.getConnection();
            sql = new StringBuilder();
            sql.append("\n UPDATE lancamento SET ");
            sql.append("\n nome = '").append(lanc.getNome()).append("'");
            sql.append("\n , data = '").append(lanc.getData()).append("'");
            sql.append("\n , valor = ").append(lanc.getValor());
            sql.append("\n , idtipolancamento = ").append(lanc.getIdTipoLancamento());
            sql.append("\n WHERE id = ").append(lanc.getId()).append(";");
            pstm = con.prepareStatement(sql.toString());
            pstm.execute();

        } finally {
            con.close();
            pstm.close();
        }
    }

    @Override
    public void excluirLancamento(int idLancamento) throws SQLException {
        Connection con = null;
        PreparedStatement pstm = null;
        StringBuilder sql = null;

        try {
            con = dataSource.getConnection();
            sql = new StringBuilder();
            sql.append("\n DELETE FROM lancamento WHERE id = ").append(idLancamento).append(";");
            pstm = con.prepareStatement(sql.toString());
            pstm.execute();

        } catch (Exception ex) {
            throw new SQLException("erro ao excluir o lancamento " + ex.getMessage());
        } finally {
            con.close();
            pstm.close();
        }
    }

    @Override
    public List<Lancamento> pesquisarLancamentoPorPeriodo(Date dataInicial, Date dataFinal) throws SQLException {
        Connection con = null;
        PreparedStatement pstm = null;
        StringBuilder sql = null;
        ResultSet rs = null;
        List<Lancamento> listaLancamentos = new ArrayList<>();

        try {
            con = dataSource.getConnection();
            sql = new StringBuilder();
            sql.append("\n SELECT lancamento.* FROM lancamento ");
            sql.append("\n JOIN tipolancamento ON tipolancamento.id = lancamento.idtipolancamento");
            sql.append("\n WHERE lancamento.data BETWEEN '").append(Formatadores.formatoDataBanco.format(dataInicial)).append("' AND '").append(Formatadores.formatoDataBanco.format(dataFinal)).append("' ORDER BY lancamento.id;");
            pstm = con.prepareStatement(sql.toString());
            rs = pstm.executeQuery();

            while (rs.next()) {
                Lancamento lanc = new Lancamento();
                lanc.setId(rs.getInt("id"));
                lanc.setDataGUI(Formatadores.formatarDataGui(rs.getDate("data")));
                lanc.setNome(rs.getString("nome"));
                lanc.setValor(rs.getBigDecimal("valor").setScale(2, RoundingMode.HALF_UP));
                lanc.setIdTipoLancamento(rs.getInt("idtipolancamento"));
                listaLancamentos.add(lanc);
            }

            return listaLancamentos;

        } finally {
            con.close();
            pstm.close();
            rs.close();
        }
    }

    @Override
    public List<Lancamento> pesquisarLancamentoPorNome(String nome) throws SQLException {
        Connection con = null;
        PreparedStatement pstm = null;
        StringBuilder sql = null;
        ResultSet rs = null;
        List<Lancamento> listaLancamentos = new ArrayList<Lancamento>();

        try {
            con = dataSource.getConnection();
            sql = new StringBuilder();
            sql.append("\n SELECT lancamento.* FROM lancamento ");
            sql.append("\n JOIN tipolancamento ON tipolancamento.id = lancamento.idtipolancamento");
            sql.append("\n WHERE lancamento.nome LIKE '%").append(nome).append("%' ORDER BY lancamento.id;");
            pstm = con.prepareStatement(sql.toString());
            rs = pstm.executeQuery();

            while (rs.next()) {
                Lancamento lanc = new Lancamento();
                lanc.setId(rs.getInt("id"));
                lanc.setDataGUI(Formatadores.formatarDataGui(rs.getDate("data")));
                lanc.setNome(rs.getString("nome"));
                lanc.setValor(rs.getBigDecimal("valor").setScale(2, RoundingMode.HALF_UP));
                lanc.setIdTipoLancamento(rs.getInt("idtipolancamento"));
                listaLancamentos.add(lanc);
            }

            return listaLancamentos;

        } finally {
            con.close();
            pstm.close();
            rs.close();
        }
    }

    @Override
    public List<Lancamento> pesquisarLancamentoPorTipoDeLancamento(int idtipolancamento) throws SQLException {
        Connection con = null;
        PreparedStatement pstm = null;
        StringBuilder sql = null;
        ResultSet rs = null;
        List<Lancamento> listaLancamentos = new ArrayList<>();

        try {
            con = dataSource.getConnection();
            sql = new StringBuilder();
            sql.append("\n SELECT lancamento.* FROM lancamento");
            sql.append("\n JOIN tipolancamento ON tipolancamento.id = lancamento.idtipolancamento");
            sql.append("\n WHERE lancamento.idtipolancamento = ").append(idtipolancamento).append(" ORDER BY lancamento.id;");

            pstm = con.prepareStatement(sql.toString());
            rs = pstm.executeQuery();

            while (rs.next()) {
                Lancamento lanc = new Lancamento();
                lanc.setId(rs.getInt("id"));
                lanc.setDataGUI(Formatadores.formatarDataGui(rs.getDate("data")));
                lanc.setNome(rs.getString("nome"));
                lanc.setValor(rs.getBigDecimal("valor").setScale(2, RoundingMode.HALF_UP));
                lanc.setIdTipoLancamento(rs.getInt("idtipolancamento"));
                listaLancamentos.add(lanc);
            }

            return listaLancamentos;
        } finally {
            con.close();
            pstm.close();
            rs.close();
        }
    }
}
