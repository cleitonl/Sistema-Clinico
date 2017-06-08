package Fpb.SistemaClinico.DAO;

import Fpb.SistemaClinico.Model.Database.ConnectionFactory;
import Fpb.SistemaClinico.Model.Medico;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {
    private static Connection con;

    public void create(Medico medico)
    {
        conectar();
        String sql = "insert into medicos(nome,crm) values (?,?)";
        String sql2 = "insert into medicos_especialidades(medicos_idmedicos,especialidades_idespecialidades) values (?,?)";
        try {
            // prepared statement para inserção
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
            // seta os valores
            stmt.setString(1,medico.getNome());
            stmt.setString(2,medico.getCrm());
            // executa
            stmt.execute();
            stmt.close();

            PreparedStatement stmt2 = (PreparedStatement) con.prepareStatement(sql2);
            // seta os valores
            stmt2.setInt(1,selectIdByName(medico.getNome()));
            stmt2.setInt(2,medico.getEspecialidade());
            // executa
            stmt2.execute();
            stmt2.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public List<Medico> read()
    {
        conectar();
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT m.idmedicos, m.nome, m.crm, esp.idespecialidades FROM medicos AS m " +
                     "LEFT JOIN medicos_especialidades ON idmedicos = medicos_idmedicos\n" +
                     "LEFT JOIN especialidades AS esp ON idespecialidades = especialidades_idespecialidades;";
        try {
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Medico temp = new Medico();
                // pega todos os atributos da pessoa
                temp.setId(rs.getInt("idmedicos"));
                temp.setNome(rs.getString("nome"));
                temp.setCrm(rs.getString("crm"));
                temp.setEspecialidade(rs.getInt("esp.idespecialidades"));
                temp.setNomeEspecialidade(EspecialidadeDAO.selectNameById(rs.getInt("esp.idespecialidades")));
                medicos.add(temp);
            }
            stmt.close();
            con.close();
            return medicos;
        } catch (SQLException e) {
            System.out.println("Erro ao buscar Médicos" + e.getMessage());
            return null;
        }
    }
    public List<Medico> search(String nome)
    {
        conectar();
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT m.idmedicos, m.nome, m.crm, esp.idespecialidades FROM medicos AS m \n" +
                "LEFT JOIN medicos_especialidades ON idmedicos = medicos_idmedicos\n" +
                "LEFT JOIN especialidades AS esp ON idespecialidades = especialidades_idespecialidades " +
                "WHERE m.nome LIKE ? OR  m.crm LIKE ?";
        try {

            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
            stmt.setString(1,'%'+nome+'%');
            stmt.setString(2,'%'+nome+'%');
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Medico temp = new Medico();
                // pega todos os atributos da pessoa
                temp.setId(rs.getInt("idmedicos"));
                temp.setNome(rs.getString("nome"));
                temp.setCrm(rs.getString("crm"));
                temp.setEspecialidade(rs.getInt("esp.idespecialidades"));
                temp.setNomeEspecialidade(EspecialidadeDAO.selectNameById(rs.getInt("esp.idespecialidades")));
                medicos.add(temp);
            }
            stmt.close();
            con.close();
            return medicos;

        } catch (SQLException e) {
            System.out.println("Erro ao buscar Pacientes" + e.getMessage());
            return null;
        }
    }
    public void update(Medico medico)
    {
        conectar();
        String sql = "UPDATE medicos SET nome=?, crm=?  WHERE idmedicos=?";
        String sql2 = "UPDATE medicos_especialidades SET especialidades_idespecialidades=? WHERE medicos_idmedicos=?";
        try {
            // prepared statement para Update
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
            // seta os valores
            stmt.setString(1,medico.getNome());
            stmt.setString(2,medico.getCrm());
            stmt.setInt(3, medico.getId());
            // executa
            stmt.executeUpdate();
            stmt.close();

            PreparedStatement stmt2 = (PreparedStatement) con.prepareStatement(sql2);
            // seta os valores
            stmt2.setInt(1, medico.getEspecialidade());
            stmt2.setInt(2, medico.getId());
            // executa
            stmt2.execute();
            stmt2.close();


            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void delete(Medico medico)
    {
        conectar();
        String sql = "DELETE FROM medicos WHERE idmedicos=?";
        try {
            // prepared statement para Update
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
            // seta os valores
            stmt.setInt(1, medico.getId());
            // executa
            stmt.executeUpdate();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public int selectIdByName(String nome) {
        conectar();

        try {
            int resultado = 0;
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT idmedicos FROM medicos WHERE nome = ?");
            stmt.setString(1, nome);
            ResultSet resultadoBusca = stmt.executeQuery();
            if (resultadoBusca.next())
            {
                resultado = resultadoBusca.getInt("idmedicos");
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

    public void conectar() {
        con = ConnectionFactory.getConnection();
    }



}
