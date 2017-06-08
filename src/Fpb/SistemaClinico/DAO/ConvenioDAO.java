package Fpb.SistemaClinico.DAO;

import Fpb.SistemaClinico.Model.Database.ConnectionFactory;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ConvenioDAO {
    private static Connection con;

    public static List<String> selectNames() throws ParseException {
        conectar();

        List<String> convenios = new ArrayList<>();
        try {

            PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT nome FROM convenios");
            ResultSet resultadoBusca = stmt.executeQuery();
            while (resultadoBusca.next()) {
                convenios.add(resultadoBusca.getString("nome"));
            }
            stmt.close();
            con.close();
            resultadoBusca.close();
            return convenios;
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    public static int selectIdByName(String nome) {
        conectar();

        try {
            int resultado = 0;
                PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT idconvenios FROM convenios WHERE nome = ?");
                stmt.setString(1, nome);
                ResultSet resultadoBusca = stmt.executeQuery();
                    if (resultadoBusca.next())
                    {
                        resultado = resultadoBusca.getInt("idconvenios");
                    }
            stmt.close();
            con.close();
            resultadoBusca.close();
            return resultado;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public static String selectNameById(Integer idconvenio) {
        conectar();

        try {
            String resultado = null;
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT nome FROM convenios WHERE idconvenios = ?");
            stmt.setInt(1, idconvenio);
            ResultSet resultadoBusca = stmt.executeQuery();
            if (resultadoBusca.next())
            {
                resultado = resultadoBusca.getString("nome");
            }
            stmt.close();
            con.close();
            resultadoBusca.close();
            return resultado;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static void conectar() {
        con = ConnectionFactory.getConnection();
    }
}
