/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package br.com.clipatec.agendacontatos;

import controller.PessoaCTR;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import libs.Tipos;
import model.Pessoa;
import model.PessoaContato;

/**
 *
 * @author Welinton
 */
public class AgendaContatos {

    public static void main(String[] args) throws IOException, SQLException, Exception {
        
        PessoaCTR pessoaCTR = new PessoaCTR();
        Pessoa pessoa = new Pessoa();
        
        pessoa = pessoaCTR.getById(1);
        
        pessoa.setNome("João Junio");
        pessoa.setApelido("Jota jota");
//        pessoa.setEndereco("Rua Marechal Castelo Branco");
//        pessoa.setNumero("353");
//        pessoa.setCep("15180000");
//        pessoa.setBairro("Centro");
//        pessoa.setIdCidade(1);

        pessoa.getContatos().get(0).setContato("joaojunio818@gmail.com");
        
//        List<PessoaContato> contatos = new ArrayList<>();
//        for (int i = 0; i <=2; i++) {
//            contatos.add(new PessoaContato(Tipos.TipoContato.Email, "emailteste" + i + "@gmail.com"));
//        }
//        pessoa.setContatos(contatos);
        
        try {
            if (pessoaCTR.alterar(pessoa)) {
                JOptionPane.showMessageDialog(null, "Pessoa alterada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ão foi possível alterar Pessoa." + " " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
    }
}
