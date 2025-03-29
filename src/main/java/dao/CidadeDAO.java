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
import model.Cidade;

/**
 *
 * @author joaoj
 */
public class CidadeDAO implements CRUD<Cidade> {
    
    private Connection conn;
    
    public CidadeDAO() throws SQLException {
        this.conn = ConnectionFactory.getConnection();
    }

    @Override
    public boolean inserir(Cidade cidade) {
        String sql = "insert into CIDADE (ID, ID_ESTADO, NOME) values (?, ?, ?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cidade.getId());
            stmt.setInt(2, cidade.getIdEstado());
            stmt.setString(3, cidade.getNome());
            stmt.executeUpdate(sql);
            return true;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean alterar(Cidade cidade) {
        String sql = "update CIDADE set ID_ESTADO = ?, NOME = ? where (ID = ?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cidade.getIdEstado());
            stmt.setString(2, cidade.getNome());
            stmt.setInt(3, cidade.getId());
            stmt.executeUpdate(sql);
            return true;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean excluir(int id) {
        String sql = "delete from CIDADE where (ID = ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate(sql);
            return true;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Cidade getById(int id) {
        String sql = "select * from CIDADE where (ID = ?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Cidade cidade = new Cidade();
                cidade.setId(rs.getInt("ID"));
                cidade.setIdEstado(rs.getInt("ID_ESTADO"));
                cidade.setNome(rs.getString("NOME"));
                return cidade;
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Cidade> getAll() {
        List<Cidade> cidades = new ArrayList<>();
        
        String sql = "select * from CIDADE";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cidade cidade = new Cidade();
                cidade.setId(rs.getInt("ID"));
                cidade.setIdEstado(rs.getInt("ID_ESTADO"));
                cidade.setNome(rs.getString("NOME"));
                cidades.add(cidade);
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        return cidades;
    }
    
}
