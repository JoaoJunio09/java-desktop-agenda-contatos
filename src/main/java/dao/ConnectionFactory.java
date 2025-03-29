/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.ModificarProperties;

/**
 *
 * @author Welinton
 */
public class ConnectionFactory {

    private static String url;
    private static String user = "SYSDBA";
    private static String password = "masterkey";
    private static String caminhoProp = "src/main/java/resources/config.properties";
    private static Connection connection;

    static {
        carregarConfiguracoes();
    }

    /**
     * Carrega as configurações do banco de dados utilizando a classe
     * ModificarProperties.
     */
    private static void carregarConfiguracoes() {
        try {
            ModificarProperties prop = new ModificarProperties(caminhoProp);
            prop.carregar();

            String port = prop.getValor("db.porta");
            String caminhoDB = prop.getValor("db.caminho");
            //user = prop.getValor("db.usuario", "SYSDBA");
            //password = prop.getValor("db.senha", "masterkey");

            //url = "jdbc:firebirdsql:127.0.0.1/" + port + ":" + caminhoDB + "?encoding=WIN1252";
            url = "jdbc:firebirdsql://127.0.0.1:" + port + "/" + caminhoDB + "?encoding=WIN1252";
            Class.forName("org.firebirdsql.jdbc.FBDriver");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar configurações do banco.", e);
        }
    }

    /**
     * Obtém uma conexão com o banco de dados.
     *
     * @return Objeto Connection
     * @throws SQLException Se ocorrer um erro ao conectar
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, password);
                connection.setAutoCommit(false);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException("Erro ao iniciar conexão.", e);
        }
        return connection;
    }

    /**
     * Fecha a conexão com o banco de dados.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao fechar conexão.", e);
        }
    }

    /**
     * Inicia uma transação no banco de dados.
     */
    public static void iniciarTransacao() throws SQLException {
        getConnection().setAutoCommit(false);
    }

    /**
     * Confirma a transação no banco de dados.
     */
    public static void confirmarTransacao() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.commit();
        }
    }

    /**
     * Desfaz a transação no banco de dados.
     */
    public static void desfazerTransacao() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.rollback();
        }
    }

    /**
     * Gera um código sequencial para uma tabela específica.
     *
     * @param tabela Nome da tabela
     * @param chavePrimaria Nome da chave primária
     * @param condicaoWhere Condição WHERE opcional (ex: "WHERE id_cliente =
     * 10")
     * @return Próximo ID disponível
     * @throws SQLException Se ocorrer um erro no SQL
     */
    /* public static int gerarCodigoSequencial(String tabela, String chavePrimaria, String condicaoWhere) throws SQLException {
        String sql = "SELECT COALESCE(MAX(" + chavePrimaria + "), 0) + 1 AS SEQ FROM " + tabela;
        if (condicaoWhere != null && !condicaoWhere.trim().isEmpty()) {
            sql += " " + condicaoWhere;
        }

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("SEQ");
            }
        }
        return 1;
    } */
    public static int gerarCodigoSequencial(Connection conn, String tabela, String chavePrimaria, String condicaoWhere) throws SQLException {
        String sql = "SELECT COALESCE(MAX(" + chavePrimaria + "), 0) + 1 AS SEQ FROM " + tabela;
        if (condicaoWhere != null && !condicaoWhere.trim().isEmpty()) {
            sql += " " + condicaoWhere;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("SEQ");
            }
        }
        return 1;
    }
}