/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import libs.Tipos.TipoContato;

/**
 *
 * @author Welinton
 */
public class PessoaContato {
    private int id;
    private int idPessoa;
    private TipoContato tipo;
    private String contato;
    
    public PessoaContato(){
    }
    
    public PessoaContato(TipoContato tipo, String contato) {
        this.tipo = tipo;
        this.contato = contato;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the idPessoa
     */
    public int getIdPessoa() {
        return idPessoa;
    }

    /**
     * @param idPessoa the idPessoa to set
     */
    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    /**
     * @return the tipo
     */
    public TipoContato getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(TipoContato tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the contato
     */
    public String getContato() {
        return this.contato;
    }

    /**
     * @param contato the contato to set
     */
    public void setContato(String contato) {
        this.contato = contato;
    }
}
