
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author joaoj
 */
public interface CRUD<T> {
    
    boolean insert(T obj);
    boolean update(T obj);
    boolean delete(int id);
    T findById(int id);
    List<T> findAll();
}
