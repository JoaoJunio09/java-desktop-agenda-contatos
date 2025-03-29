/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;

/**
 *
 * @author Welinton
 * @param <T>
 */
public interface CRUD<T> {
    boolean inserir(T t) throws Exception;

    boolean alterar(T t) throws Exception;

    boolean excluir(int id) throws Exception;

    T getById(int id) throws Exception;

    List<T> getAll() throws Exception;
}
