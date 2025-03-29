/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javax.swing.JOptionPane;

/**
 *
 * @author Welinton
 */
public class Config {

    private String backgroudMenu;
    private String ufPadrao;
    private String caminhoDB;
    private String porta;

    public Config() {
    }

    /**
     * @return the backgroudMenu
     */
    public String getBackgroudMenu() {
        return backgroudMenu;
    }

    /**
     * @param backgroudMenu the backgroudMenu to set
     */
    public void setBackgroudMenu(String backgroudMenu) {
        this.backgroudMenu = backgroudMenu;
    }

    /**
     * @return the ufPadrao
     */
    public String getUfPadrao() {
        return ufPadrao;
    }

    /**
     * @param ufPadrao the ufPadrao to set
     */
    public void setUfPadrao(String ufPadrao) {
        this.ufPadrao = ufPadrao;
    }

    /**
     * @return the caminhoDB
     */
    public String getCaminhoDB() {
        return caminhoDB;
    }

    /**
     * @param caminhoDB the caminhoDB to set
     */
    public void setCaminhoDB(String caminhoDB) {
        if (caminhoDB.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Caminho do banco de dados é inválido!", "Atenção", JOptionPane.WARNING_MESSAGE);           
        }
        this.caminhoDB = caminhoDB;
    }

    /**
     * @return the porta
     */
    public String getPorta() {
        return porta;
    }

    /**
     * @param porta the porta to set
     */
    public void setPorta(String porta) {
        this.porta = porta;
    }

}
