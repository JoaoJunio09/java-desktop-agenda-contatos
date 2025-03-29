/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.PessoaDao;
import java.util.List;
import model.Pessoa;

/**
 *
 * @author joaoj
 */
public class PessoaCTR {
    
    private final PessoaDao dao;
    
    public PessoaCTR() {
        this.dao = new PessoaDao();
    }
    
    public boolean inserir(Pessoa pessoa) throws Exception {
        return dao.inserir(pessoa);
    }
    
    public boolean alterar(Pessoa pessoa) throws Exception {
        return dao.alterar(pessoa);
    }
    
    public List<Pessoa> getAll() throws Exception {
        return dao.getAll();
    }
    
    public Pessoa getById(int id) throws Exception {
        return dao.getById(id);
    }
    
    public List<Pessoa> getByNome(String nome) throws Exception {
        return dao.getByNome(nome);
    }
}
