/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.CidadeDAO;
import java.sql.SQLException;
import java.util.List;
import model.Cidade;

/**
 *
 * @author joaoj
 */
public class CidadeCTR {
    
    private final CidadeDAO cidadeDAO;
    
    public CidadeCTR() throws SQLException {
        this.cidadeDAO = new CidadeDAO();
    }
    
    public boolean inserir(Cidade cidade) {
        return cidadeDAO.inserir(cidade);
    }
    
    public boolean alterar(Cidade cidade) {
        return cidadeDAO.alterar(cidade);
    }
    
    public boolean excluir(int id) {
        return cidadeDAO.excluir(id);
    }
    
    public Cidade getById(int id) {
        return cidadeDAO.getById(id);
    }
    
    public List<Cidade> getAll() {
        return cidadeDAO.getAll();
    }
    
}
