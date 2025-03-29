/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import libs.Tipos.TipoContato;
import model.Pessoa;
import model.PessoaContato;

/**
 *
 * @author joaoj
 */
public class PessoaDao implements CRUD<Pessoa> {
    
    private final Connection conn;
    
    public PessoaDao() throws SQLException {
        conn = ConnectionFactory.getConnection();
    }

    @Override
    public boolean inserir(Pessoa pessoa) throws Exception {
        Connection conn = null;
        PreparedStatement stmtPessoa = null;
        PreparedStatement stmtContato = null;
        ResultSet rs = null;        
        
        try {
            conn = ConnectionFactory.getConnection();
            ConnectionFactory.iniciarTransacao();
            String sqlPessoa = "insert into PESSOA (ID, ID_CIDADE, NOME, APELIDO, ENDERECO, NUMERO, BAIRRO, CEP) values (?, ?, ?, ?, ?, ?, ?, ?)";
            stmtPessoa = conn.prepareStatement(sqlPessoa);
            
            int idPessoa = ConnectionFactory.gerarCodigoSequencial(conn, "PESSOA", "ID", null);
            if (idPessoa > 0) pessoa.setId(idPessoa);
            else throw new Exception("Erro ao obter o ID da Pessoa");
            
            stmtPessoa.setInt(1, pessoa.getId());
            stmtPessoa.setInt(2, pessoa.getIdCidade());
            stmtPessoa.setString(3, pessoa.getNome());
            stmtPessoa.setString(4, pessoa.getApelido());
            stmtPessoa.setString(5, pessoa.getEndereco());
            stmtPessoa.setString(6, pessoa.getNumero());
            stmtPessoa.setString(7, pessoa.getBairro());
            stmtPessoa.setString(8, pessoa.getCep());
            stmtPessoa.executeUpdate();           

            if (!pessoa.getContatos().isEmpty()) {
                String sqlPessoaContato = "insert into PESSOA_CONTATO (ID, ID_PESSOA, TIPO, CONTATO) values (?, ?, ?, ?)";
                stmtContato = conn.prepareStatement(sqlPessoaContato);
                
                for (PessoaContato pessoaContato : pessoa.getContatos()) {
                    
                    int idContato = ConnectionFactory.gerarCodigoSequencial(conn, 
                            "PESSOA_CONTATO", 
                            "ID", 
                            "WHERE (ID_PESSOA = " + pessoa.getId() + ");");
                    
                    pessoaContato.setId(idContato);
                    pessoaContato.setIdPessoa(pessoa.getId());
                    
                    stmtContato.setInt(1, pessoaContato.getId());
                    stmtContato.setInt(2, pessoa.getId());
                    stmtContato.setInt(3, pessoaContato.getTipo().ordinal());
                    stmtContato.setString(4, pessoaContato.getContato());
                    stmtContato.executeUpdate();
                }
            }
            
            ConnectionFactory.confirmarTransacao();
            return true;
        }
        catch (SQLException e) {
            ConnectionFactory.desfazerTransacao();
            throw e;
        }
        finally {
            if (rs != null) {
                rs.close();
            }
            if (stmtPessoa != null) {
                stmtPessoa.close();
            }
            if (stmtContato != null) {
                stmtContato.close();
            }
            ConnectionFactory.closeConnection();
        }
    }

    @Override
    public boolean alterar(Pessoa pessoa) throws Exception {
        Connection conn = null;
        PreparedStatement stmtPessoa = null;
        PreparedStatement stmtContatoDelete = null;
        PreparedStatement stmtContatoInsert = null;    
        
        try {
            conn = ConnectionFactory.getConnection();
            ConnectionFactory.iniciarTransacao();
            String sqlPessoa = "UPDATE PESSOA SET ID_CIDADE=?, NOME=?, APELIDO=?, ENDERECO=?, NUMERO=?, BAIRRO=?, CEP=? WHERE ID=?";
            stmtPessoa = conn.prepareStatement(sqlPessoa);            
            stmtPessoa.setInt(1, pessoa.getId());
            stmtPessoa.setInt(2, pessoa.getIdCidade());
            stmtPessoa.setString(3, pessoa.getNome());
            stmtPessoa.setString(4, pessoa.getApelido());
            stmtPessoa.setString(5, pessoa.getEndereco());
            stmtPessoa.setString(6, pessoa.getNumero());
            stmtPessoa.setString(7, pessoa.getBairro());
            stmtPessoa.setString(8, pessoa.getCep());
            
            stmtPessoa.executeUpdate();
            
            String sqlDeleteContatos = "DELETE FROM PESSOA_CONTATO WHERE ID_PESSOA=?";
            stmtContatoDelete = conn.prepareStatement(sqlDeleteContatos);
            stmtContatoDelete.setInt(1, pessoa.getId());
            stmtContatoDelete.executeUpdate();
            
            String sqlContatoInsert = "INSERT INTO PESSOA_CONTATO (ID, ID_PESSOA, TIPO, CONTATO) VALUES (?, ?, ?, ?)";
            stmtContatoInsert = conn.prepareStatement(sqlContatoInsert);

            if (!pessoa.getContatos().isEmpty()) {                
                for (PessoaContato pessoaContato : pessoa.getContatos()) {                    
                    int idContato = ConnectionFactory.gerarCodigoSequencial(conn, 
                            "PESSOA_CONTATO", 
                            "ID", 
                            null);
                    pessoaContato.setId(idContato);
                    pessoaContato.setIdPessoa(pessoa.getId());
                    
                    stmtContatoInsert.setInt(1, pessoaContato.getId());
                    stmtContatoInsert.setInt(2, pessoa.getId());
                    stmtContatoInsert.setInt(3, pessoaContato.getTipo().ordinal());
                    stmtContatoInsert.setString(4, pessoaContato.getContato());
                    
                    stmtContatoInsert.executeUpdate();
                }
            }
            
            ConnectionFactory.confirmarTransacao();
            return true;
        }
        catch (SQLException e) {
            ConnectionFactory.desfazerTransacao();
            throw e;
        }
        finally {
            if (stmtPessoa != null) {
                stmtPessoa.close();
            }
            if (stmtContatoDelete != null) {
                stmtContatoDelete.close();
            }
            if (stmtContatoInsert != null) {
                stmtContatoInsert.close();
            }
            ConnectionFactory.closeConnection();
        }
    }

    @Override
    public boolean excluir(int id) throws Exception {
        Connection conn = null;
        PreparedStatement stmtContato = null;
        PreparedStatement stmtPessoa = null;

        try {
            conn = ConnectionFactory.getConnection();
            ConnectionFactory.iniciarTransacao();

            // Excluindo contatos
            String sqlContato = "DELETE FROM PESSOA_CONTATO WHERE IDPESSOA=?";
            stmtContato = conn.prepareStatement(sqlContato);
            stmtContato.setInt(1, id);
            stmtContato.executeUpdate();

            // Excluindo pessoa
            String sqlPessoa = "DELETE FROM PESSOA WHERE ID=?";
            stmtPessoa = conn.prepareStatement(sqlPessoa);
            stmtPessoa.setInt(1, id);
            stmtPessoa.executeUpdate();

            ConnectionFactory.confirmarTransacao();
            return true;
        } catch (Exception e) {
            ConnectionFactory.desfazerTransacao();
            throw e;
        } finally {
            if (stmtContato != null) {
                stmtContato.close();
            }
            if (stmtPessoa != null) {
                stmtPessoa.close();
            }
            ConnectionFactory.closeConnection();
        }
    }

    @Override
    public Pessoa getById(int id) throws Exception {
        Connection conn = null;
        PreparedStatement stmtPessoa = null;
        PreparedStatement stmtContato = null;
        ResultSet rsPessoa = null;
        ResultSet rsContato = null;

        try {
            conn = ConnectionFactory.getConnection();

            // Buscar pessoa
            String sqlPessoa = "SELECT * FROM PESSOA WHERE ID=?";
            stmtPessoa = conn.prepareStatement(sqlPessoa);
            stmtPessoa.setInt(1, id);
            rsPessoa = stmtPessoa.executeQuery();

            if (rsPessoa.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(id);
                pessoa.setIdCidade(rsPessoa.getInt("ID_CIDADE"));
                pessoa.setNome(rsPessoa.getString("NOME"));
                pessoa.setApelido(rsPessoa.getString("APELIDO"));
                pessoa.setEndereco(rsPessoa.getString("ENDERECO"));
                pessoa.setNumero(rsPessoa.getString("NUMERO"));
                pessoa.setBairro(rsPessoa.getString("BAIRRO"));
                pessoa.setCep(rsPessoa.getString("CEP"));

                // Buscar contatos
                List<PessoaContato> contatos = new ArrayList<>();
                String sqlContato = "SELECT * FROM PESSOA_CONTATO WHERE ID_PESSOA=?";
                stmtContato = conn.prepareStatement(sqlContato);
                stmtContato.setInt(1, id);
                rsContato = stmtContato.executeQuery();

                while (rsContato.next()) {
                    PessoaContato contato = new PessoaContato();
                    contato.setId(rsContato.getInt("ID"));
                    contato.setIdPessoa(id);
                    //contato.setTipo(rsContato.getInt("TIPO"));
                    contato.setTipo(TipoContato.values()[rsContato.getInt("TIPO")]);
                    contato.setContato(rsContato.getString("CONTATO"));
                    contatos.add(contato);
                }

                pessoa.setContatos(contatos);
                return pessoa;
            }
            return null;
        } finally {
            if (stmtContato != null) {
                stmtContato.close();
            }
            if (stmtPessoa != null) {
                stmtPessoa.close();
            }
            ConnectionFactory.closeConnection();
        }
    }

    @Override
    public List<Pessoa> getAll() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Pessoa> pessoas = new ArrayList<>();

        try {
            conn = ConnectionFactory.getConnection();
            String sql = "SELECT * FROM PESSOA";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(rs.getInt("ID"));
                pessoa.setIdCidade(rs.getInt("ID_CIDADE"));
                pessoa.setNome(rs.getString("NOME"));
                pessoa.setApelido(rs.getString("APELIDO"));
                pessoa.setEndereco(rs.getString("ENDERECO"));
                pessoa.setNumero(rs.getString("NUMERO"));
                pessoa.setBairro(rs.getString("BAIRRO"));
                pessoa.setCep(rs.getString("CEP"));

                // Buscar os contatos da pessoa
                pessoa.setContatos(getContatosByPessoaId(pessoa.getId()));

                pessoas.add(pessoa);
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            ConnectionFactory.closeConnection();
        }
        return pessoas;
    }

    private List<PessoaContato> getContatosByPessoaId(int pessoaId) throws Exception {
        List<PessoaContato> contatos = new ArrayList<>();
        String sql = "SELECT * FROM PESSOA_CONTATO WHERE ID_PESSOA = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Connection conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, pessoaId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                PessoaContato contato = new PessoaContato();
                contato.setId(rs.getInt("ID"));
                contato.setIdPessoa(rs.getInt("ID_PESSOA"));
                contato.setTipo(TipoContato.valueOf(rs.getString("TIPO")));
                contato.setContato(rs.getString("CONTATO"));

                contatos.add(contato);
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            //ConnectionFactory.closeConnection();
        }
        return contatos;
    }

}
