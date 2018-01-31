package logic.treinamento.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexaoBancoDados {

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

        } catch (SQLException ex) {
            Logger.getLogger(ConexaoBancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
