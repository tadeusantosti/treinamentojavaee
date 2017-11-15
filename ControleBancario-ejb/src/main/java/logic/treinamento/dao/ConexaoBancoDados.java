package logic.treinamento.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoBancoDados {

    public Connection conectaBanco() throws SQLException {
        String url = "jdbc:hsqldb:file:/" + System.getProperty("user.dir") + "/dbControleBancario";
        System.out.println(url);
        String user = "SA";
        String password = "";
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException ex) {
            throw new SQLException(ex.getException());
        }
        return DriverManager.getConnection(url, user, password);
    }

    public void fecharConexao(Connection con, PreparedStatement pstm, Statement stm, ResultSet rs) {
        try {
            if (con != null) {
                con.createStatement().execute("shutdown");
                con.close();
            }
            if (pstm != null) {
                pstm.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }

        } catch (SQLException e) {
        }
    }
}
