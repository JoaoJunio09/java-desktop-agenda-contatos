/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author Welinton
 */
public class ModificarProperties {

    private Properties prop;
    private String caminho;

    public ModificarProperties(String caminho) {
        this.caminho = caminho;
        this.prop = new Properties();
    }

    public void carregar() throws IOException {
        try (InputStream input = new FileInputStream(caminho)) {
            prop.load(input);
        }
    }

    public String getValor(String chave) {
        return prop.getProperty(chave);
    }

    public void setValor(String chave, String valor) {
        prop.setProperty(chave, valor);
    }

    public void removerValor(String chave) {
        prop.remove(chave);
    }

    public void gravar() throws IOException {
        try (OutputStream output = new FileOutputStream(caminho)) {
            prop.store(output, "Arquivo atualizado com sucesso.");
        }
    }

    public void exibirDados() {
        prop.forEach((chave, valor) -> System.out.println(chave + ": " + valor));
    }
}
