/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Estado;

/**
 *
 * @author Welinton
 */
public class EstadoDAO implements CRUD<Estado> {

    private final Connection conn;

    public EstadoDAO() throws SQLException {
        this.conn = ConnectionFactory.getConnection();
    }

    @Override
    public boolean inserir(Estado estado) {
        String sql = "insert into ESTADO (NOME, SIGLA) values (?, ?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado.getNome());
            stmt.setString(2, estado.getSigla());
            ConnectionFactory.iniciarTransacao();
            stmt.executeUpdate();
            ConnectionFactory.confirmarTransacao();
            return true;
        }
        catch (SQLException e) {
            try {
                ConnectionFactory.desfazerTransacao();
            } 
            catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean alterar(Estado estado) {
        String sql = "update ESTADO set NOME = ?, SIGLA = ? where (ID = ?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado.getNome());
            stmt.setString(2, estado.getSigla());
            stmt.setInt(3, estado.getId());
            stmt.executeUpdate();
            return true;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean excluir(int id) {
        String sql = "delete from ESTADO where (ID = ?);";
        try (PreparedStatement stmt = conn.prepareCall(sql)) {         
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Estado getById(int id) {
        String sql = "select * from ESTADO where (ID = ?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {              
                Estado estado = new Estado();   
                estado.setId(rs.getInt("ID"));
                estado.setNome(rs.getString("NOME"));
                estado.setSigla(rs.getString("SIGlA"));
                return estado;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Estado> getAll() {
        List<Estado> estados = new ArrayList<>();
        
        String sql = "select * from ESTADO;";
        try (PreparedStatement stmt = conn.prepareCall(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Estado estado = new Estado();
                estado.setId(rs.getInt("ID"));
                estado.setNome(rs.getString("NOME"));
                estado.setSigla(rs.getString("SIGLA"));
                estados.add(estado);
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return estados;
    }

}
