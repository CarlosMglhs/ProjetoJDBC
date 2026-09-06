package db;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection conn = null; //Interface para trazer funções para conexão;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Properties props = loadProperties(); //carrego as propiedades
                String url = props.getProperty("dburl"); // trago em forma de URL meu BD
                conn = DriverManager.getConnection(url, props);//ligo a conexão com a url e meu user e password
                System.out.println("\n=-=-=-=-=- Banco de dados conectado com sucesso!! =-=-=-=-=-\n");
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }

        }
        return conn;
    }

    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            // ⬆️ Ele acessa o disco rígido, encontra o arquivo físico db.properties
            Properties props = new Properties(); //a estrutura de dados que interpreta e armazena as informações.
            props.load(fs); //Ele consome o fluxo de dados vindo do fs
            return props;
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close(); //desligo a conexão.
                System.out.println("\nDesconectando...");
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
                System.out.println("\nEncerrando o Statement");
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
                System.out.println("\nEncerrando Result Set");
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeStatementPrep(PreparedStatement stPrep) {
        if (stPrep != null) {
            try {
                stPrep.close();
                System.out.println("\nEncerrando o Prepare Statement");
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}
