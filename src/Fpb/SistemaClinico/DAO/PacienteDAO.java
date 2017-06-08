package Fpb.SistemaClinico.DAO;
import Fpb.SistemaClinico.Model.Database.ConnectionFactory;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import Fpb.SistemaClinico.Model.Paciente;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO{
    private static Connection con;

    public void create(Paciente paciente)
    {
        conectar();
        String sql = "insert into pacientes(nome,rg,cpf,endereco,telefone,sexo,convenios_idconvenios) values (?,?,?,?,?,?,?)";

        try {
        // prepared statement para inserção
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
        // seta os valores
            stmt.setString(1,paciente.getNome());
            stmt.setString(2,paciente.getRg());
            stmt.setString(3,paciente.getCpf());
            stmt.setString(4,paciente.getEnd());
            stmt.setString(5,paciente.getTelefone());
            stmt.setString(6,paciente.getSexo());
            stmt.setInt(7,paciente.getConveniopaciente());
        // executa
            stmt.execute();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public  List<Paciente> read()
    {
        conectar();
        List<Paciente> pacientes = new ArrayList<>();

        try {
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement("select * from pacientes");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Paciente temp = new Paciente();
                // pega todos os atributos da pessoa
                temp.setId(rs.getInt("idpacientes"));
                temp.setNome(rs.getString("nome"));
                temp.setRg(rs.getString("rg"));
                temp.setCpf(rs.getString("cpf"));
                temp.setEnd(rs.getString("endereco"));
                temp.setTelefone(rs.getString("telefone"));
                temp.setSexo(rs.getString("sexo"));
                temp.setNomeconvenio(ConvenioDAO.selectNameById(rs.getInt("convenios_idconvenios")));
                pacientes.add(temp);
            }

            stmt.close();
            con.close();
            return pacientes;

        } catch (SQLException e) {
            System.out.println("Erro ao buscar Pacientes" + e.getMessage());
            return null;
        }
    }
    public  List<Paciente> search(String nome)
    {
        conectar();
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "select * from pacientes WHERE nome LIKE ? OR cpf LIKE ? OR rg LIKE ? OR endereco LIKE ? OR telefone LIKE ?";
        try {

            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
            stmt.setString(1,'%'+nome+'%');
            stmt.setString(2,'%'+nome+'%');
            stmt.setString(3,'%'+nome+'%');
            stmt.setString(4,'%'+nome+'%');
            stmt.setString(5,'%'+nome+'%');
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Paciente temp = new Paciente();
                // pega todos os atributos da pessoa
                temp.setId(rs.getInt("idpacientes"));
                temp.setNome(rs.getString("nome"));
                temp.setRg(rs.getString("rg"));
                temp.setCpf(rs.getString("cpf"));
                temp.setEnd(rs.getString("endereco"));
                temp.setTelefone(rs.getString("telefone"));
                temp.setSexo(rs.getString("sexo"));
                temp.setNomeconvenio(ConvenioDAO.selectNameById(rs.getInt("convenios_idconvenios")));
                pacientes.add(temp);
            }
            stmt.close();
            con.close();
            return pacientes;

        } catch (SQLException e) {
            System.out.println("Erro ao buscar Pacientes" + e.getMessage());
            return null;
        }
    }
    public void update(Paciente paciente)
    {
       conectar();
       String sql = "UPDATE pacientes SET nome=?, rg=?, cpf=?, endereco=?, telefone=?, sexo=?, convenios_idconvenios=?  WHERE idpacientes=?";
       try {
           // prepared statement para Update
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
            // seta os valores
            stmt.setString(1,paciente.getNome());
            stmt.setString(2,paciente.getRg());
            stmt.setString(3,paciente.getCpf());
            stmt.setString(4,paciente.getEnd());
            stmt.setString(5,paciente.getTelefone());
            stmt.setString(6,paciente.getSexo());
            stmt.setInt(7, ConvenioDAO.selectIdByName(paciente.getNomeconvenio()));
            stmt.setInt(8, paciente.getId());
            // executa
            stmt.executeUpdate();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void delete(Paciente paciente)
    {
        conectar();
        String sql = "DELETE FROM pacientes WHERE idpacientes=?";
           try {
            // prepared statement para Update
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
            // seta os valores
            stmt.setInt(1, paciente.getId());
            // executa
            stmt.executeUpdate();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void conectar() {
            con = ConnectionFactory.getConnection();
    }



}
