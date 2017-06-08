package Fpb.SistemaClinico.DAO;

import Fpb.SistemaClinico.Model.Database.ConnectionFactory;
import Fpb.SistemaClinico.Model.Especialidade;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadeDAO {
    private static Connection con;

    public void create(Especialidade especialidade)
    {
        conectar();
        String sql = "insert into especialidades(nome) values (?)";
        try {
            // prepared statement para inserção
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
            // seta os valores
            stmt.setString(1,especialidade.getNome());
            // executa
            stmt.execute();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public List<Especialidade> read()
    {
        conectar();
        List<Especialidade> especialidades = new ArrayList<>();

        try {
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement("select * from especialidades");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Especialidade temp = new Especialidade();
                // pega todos os atributos da pessoa
                temp.setId(rs.getInt("idespecialidades"));
                temp.setNome(rs.getString("nome"));
                especialidades.add(temp);
            }

            stmt.close();
            con.close();
            return especialidades;

        } catch (SQLException e) {
            System.out.println("Erro ao buscar Especialidades" + e.getMessage());
            return null;
        }
    }
    public List<Especialidade> search(String nome)
    {
        conectar();
        List<Especialidade> especialidades = new ArrayList<>();
        String sql = "select * from especialidades WHERE nome LIKE ?";
        try {

            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
            stmt.setString(1,'%'+nome+'%');
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Especialidade temp = new Especialidade();
                // pega todos os atributos da pessoa
                temp.setId(rs.getInt("idespecialidades"));
                temp.setNome(rs.getString("nome"));
                especialidades.add(temp);
            }
            stmt.close();
            con.close();
            return especialidades;

        } catch (SQLException e) {
            System.out.println("Erro ao buscar Pacientes" + e.getMessage());
            return null;
        }
    }
    public void update(Especialidade especialidade)
    {
        conectar();
        String sql = "UPDATE especialidades SET nome=?  WHERE idespecialidades=?";
        try {
            // prepared statement para Update
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
            // seta os valores
            stmt.setString(1,especialidade.getNome());
            stmt.setInt(2, especialidade.getId());
            // executa
            stmt.executeUpdate();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void delete(Especialidade especialidade)
    {
        conectar();
        String sql = "DELETE FROM especialidades WHERE idespecialidades=?";
        try {
            // prepared statement para Update
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
            // seta os valores
            stmt.setInt(1, especialidade.getId());
            // executa
            stmt.executeUpdate();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static List<String> selectNames() throws ParseException {
        conectar();
        List<String> especialidades = new ArrayList<>();
        try {

            PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT nome FROM especialidades");
            ResultSet resultadoBusca = stmt.executeQuery();
            while (resultadoBusca.next()) {
                especialidades.add(resultadoBusca.getString("nome"));
            }
            stmt.close();
            con.close();
            resultadoBusca.close();
            return especialidades;
        } catch (SQLException e) {
            e.getMessage();
            return null;
        }
    }

    public static int selectIdByName(String nome) {
        conectar();

        try {
            int resultado = 0;
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT idespecialidades FROM especialidades WHERE nome = ?");
            stmt.setString(1, nome);
            ResultSet resultadoBusca = stmt.executeQuery();
            if (resultadoBusca.next())
            {
                resultado = resultadoBusca.getInt("idespecialidades");
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
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT nome FROM especialidades WHERE idespecialidades = ?");
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
