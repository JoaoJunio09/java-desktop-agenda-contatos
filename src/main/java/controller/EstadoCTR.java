/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.EstadoDAO;
import java.sql.SQLException;
import java.util.List;
import model.Estado;

/**
 *
 * @author joaoj
 */
public class EstadoCTR {
    
    // final >> (constante) = não muda seu valor; 
    private final EstadoDAO estadoDAO;
    
    public EstadoCTR() throws SQLException {
        this.estadoDAO = new EstadoDAO();
    }
    
    public boolean inserir(Estado estado) {
        return estadoDAO.inserir(estado);
    }
    
    public boolean alterar(Estado estado) {
        return estadoDAO.alterar(estado);
    }
    
    public boolean excluir(int id) {
        return estadoDAO.excluir(id);
    }
    
    public Estado getById(int id) {
        return estadoDAO.getById(id);
    }
    
    public List<Estado> getAll() {
        return estadoDAO.getAll();
    }
    
}
