package Fpb.SistemaClinico.Model.Database;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {
    private static Connection connection;
    private static String URL = "jdbc:mysql://localhost/hospital?useSSL=false", NOME = "root", SENHA = "";
    public static String status="";

    public static Connection getConnection(){
        Connection conn;

        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = (Connection) DriverManager.getConnection(URL,NOME,SENHA);

            status = "Connection opened";
            return conn;
        }
        catch (SQLException e) {
            status = e.getMessage(); // a variavel status vai receber a string da exceção
        }catch (ClassNotFoundException e) {
            status = e.getMessage();

        }catch(Exception e){
            status = e.getMessage();}
        return null;
    }

    public static Connection conectar() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(URL,NOME,SENHA);
            System.out.println("Conectado!");
            return connection;
        }catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public void desconectar(Connection conn) {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }





}
