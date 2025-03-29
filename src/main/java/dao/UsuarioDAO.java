/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;

/**
 *
 * @author joaoj
 */
public class UsuarioDAO implements CRUD<Usuario> {
    
    private final Connection conn;
    
    public UsuarioDAO() throws SQLException {
        this.conn = ConnectionFactory.getConnection();
    }
    
    private int genId() {
        String sql = "select * coalesce (max(ID), 0) + 1 as ID from USUARIO;";
        try (PreparedStatement stmt = conn.prepareCall(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean inserir(Usuario usuario) {
        String sql = "INSERT INTO usuario (ID, NOME, SENNHA) VALUES (?, ?, ?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, genId());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getSenha());
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean alterar(Usuario usuario) {
        String sql = "UPDATE usuario SET NOME = ?, SENHA = ? WHERE (ID = ?);";
        try (PreparedStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getSenha());
            stmt.setInt(3, usuario.getId());
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean excluir(int id) {
        String sql = "DELETE FROM USUARIO WHERE (ID = ?);";
        try (PreparedStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Usuario getById(int id) {
        String sql = "SELECT * FROM USUARIO WHERE (ID = ?);";
        try (PreparedStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("ID"));
                usuario.setNome(rs.getString("NOME"));
                usuario.setSenha(rs.getString("SENHA"));
                return usuario;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Usuario> getAll() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM USUARIO";
        try (PreparedStatement stmt = conn.prepareCall(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("ID"));
                usuario.setNome(rs.getString("NOME"));
                usuario.setSenha(rs.getString("SENHA"));
                usuarios.add(usuario);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}
