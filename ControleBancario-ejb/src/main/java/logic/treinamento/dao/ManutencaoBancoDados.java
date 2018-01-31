package logic.treinamento.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.sql.DataSource;

@Singleton
@Startup
public class ManutencaoBancoDados {

    @Resource(mappedName = "java:/dbControleBancario")
    private DataSource dataSource;

    @PostConstruct
    public void init() {
        try {
            removerDados();
            criarTabelas();
            inserirDadosTipoLancamento();
        } catch (SQLException ex) {
            Logger.getLogger(ManutencaoBancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void criarTabelas() throws SQLException {
        Connection con = null;
        PreparedStatement pstm = null;
        StringBuilder sql = null;

        try {
            con = dataSource.getConnection();

            Logger.getLogger(ManutencaoBancoDados.class.getName()).log(Level.SEVERE, null, "Criando tabela lancamento");
            sql = new StringBuilder();
            sql.append("\n CREATE TABLE IF NOT EXISTS lancamento");
            sql.append("\n (id INTEGER IDENTITY PRIMARY KEY, nome VARCHAR(50), data TIMESTAMP, valor DOUBLE, idtipolancamento VARCHAR(50));");
            pstm = con.prepareStatement(sql.toString());
            pstm.execute();

            Logger.getLogger(ManutencaoBancoDados.class.getName()).log(Level.SEVERE, null, "Criando tabela tipolancamento");
            sql.delete(0, sql.length());
            sql.append("\n CREATE TABLE IF NOT EXISTS tipolancamento");
            sql.append("\n (id INTEGER IDENTITY PRIMARY KEY, tipolancamento VARCHAR(50));");
            pstm = con.prepareStatement(sql.toString());
            pstm.execute();

            Logger.getLogger(ManutencaoBancoDados.class.getName()).log(Level.SEVERE, null, "Limpando tabela tipolancamento");
            sql.delete(0, sql.length());
            sql.append("\n DELETE FROM tipolancamento;");
            pstm = con.prepareStatement(sql.toString());
            pstm.execute();

        } finally {
            con.close();
            pstm.close();
        }
    }

    private void inserirDadosTipoLancamento() throws SQLException {
        Connection con = null;
        Statement stm = null;
        StringBuilder sql = null;

        try {
            con = dataSource.getConnection();

            Logger.getLogger(ManutencaoBancoDados.class.getName()).log(Level.SEVERE, null, "Inserindo os tipos de lancamento");
            sql = new StringBuilder();
            sql.append("\n INSERT INTO tipolancamento (id, tipolancamento) VALUES (1, 'TRANSFERENCIA');");
            sql.append("\n INSERT INTO tipolancamento (id, tipolancamento) VALUES (2, 'SAQUE');");
            sql.append("\n INSERT INTO tipolancamento (id, tipolancamento) VALUES (3, 'DEPOSITO');");
            stm = con.createStatement();
            stm.execute(sql.toString());

        } finally {
            con.close();
            stm.close();
        }
    }

    private void removerDados() throws SQLException {
        Connection con = null;
        Statement stm = null;
        StringBuilder sql = null;        

        try {
            con = dataSource.getConnection();

            sql = new StringBuilder();
            sql.append("\n DROP TABLE IF EXISTS lancamento; ");
            sql.append("\n DROP TABLE IF EXISTS tipolancamento; ");
            stm = con.createStatement();
            stm.execute(sql.toString());

        } finally {
            con.close();
            stm.close();            
        }
    }
}
