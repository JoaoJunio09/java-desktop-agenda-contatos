/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package br.com.clipatec.agendacontatos;

import controller.PessoaCTR;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Pessoa;

/**
 *
 * @author Welinton
 */
public class AgendaContatos {

    public static void main(String[] args) throws IOException, SQLException, Exception {
        
        PessoaCTR pessoaCTR = new PessoaCTR();
        Pessoa pessoa = new Pessoa();
        
        pessoa = pessoaCTR.getById(1);
        System.out.println(pessoa);
        
        
    }
}
