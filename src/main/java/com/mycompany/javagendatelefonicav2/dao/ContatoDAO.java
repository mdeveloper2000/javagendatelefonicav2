package com.mycompany.javagendatelefonicav2.dao;

import com.mycompany.javagendatelefonicav2.pojos.Contato;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ContatoDAO {
    
    private Connection conn;
    private int quantidade = 5;
    
    public ContatoDAO() {
        this.conn = ConnectionFactory.getConnection();
    }
    
    public int save(Contato contato) {
        int resultado = 0;
        PreparedStatement ps = null;
        try {
            String SQL = "INSERT INTO contatos(nome, telefone, email, anotacoes) " +
                "VALUES(?, ?, ?, ?)";
            ps = this.conn.prepareStatement(SQL);
            ps.setString(1, contato.getNome());
            ps.setString(2, contato.getTelefone());
            ps.setString(3, contato.getEmail());
            ps.setString(4, contato.getAnotacoes());
            resultado = ps.executeUpdate();            
        }
        catch(SQLException e) {
            e.printStackTrace();            
        }
        return resultado;
    }
    
    public int edit(Contato contato, InputStream is) {
        int resultado = 0;
        PreparedStatement ps = null;
        if(is == null) {
             try {
                String SQL = "UPDATE contatos SET nome = ?, telefone = ?, " +
                "email = ?, anotacoes = ? WHERE id = ?";
                ps = this.conn.prepareStatement(SQL);
                ps.setString(1, contato.getNome());
                ps.setString(2, contato.getTelefone());
                ps.setString(3, contato.getEmail());
                ps.setString(4, contato.getAnotacoes());
                ps.setInt(5, contato.getId());
                resultado = ps.executeUpdate();
            }
            catch(SQLException e) {
                e.printStackTrace();            
            }      
        }
        else {
            try {
                String SQL = "UPDATE contatos SET nome = ?, telefone = ?, "
                        + "email = ?, foto = ?, anotacoes = ? WHERE id = ?";
                ps = this.conn.prepareStatement(SQL);
                ps.setString(1, contato.getNome());
                ps.setString(2, contato.getTelefone());
                ps.setString(3, contato.getEmail());
                ps.setBlob(4, is);
                ps.setString(5, contato.getAnotacoes());
                ps.setInt(6, contato.getId());
                resultado = ps.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }        
        return resultado;
    }
    
    
    public List list(int indice) {
        
        List<Contato> contatos = new ArrayList<>();
        PreparedStatement ps = null;        
        
        try {
            String SQL = "SELECT * FROM contatos LIMIT ?, ?";
            ps = this.conn.prepareStatement(SQL);
            ps.setInt(1, ((indice - 1) * this.quantidade));
            ps.setInt(2, this.quantidade);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Contato contato = new Contato();
                contato.setId(rs.getInt("id"));
                contato.setNome(rs.getString("nome"));
                contato.setTelefone(rs.getString("telefone"));
                contato.setEmail(rs.getString("email"));                
                contato.setAnotacoes(rs.getString("anotacoes"));
                Blob blob = rs.getBlob("foto");
                if(blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] imageBytes = outputStream.toByteArray();
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    inputStream.close();
                    outputStream.close();
                    contato.setFoto(base64Image);
                }
                else {
                    contato.setFoto(null);
                }
                contatos.add(contato);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        catch(IOException i) {
            i.printStackTrace();
        }
        return contatos;
        
    }
    
    public Contato read(int id) {
        PreparedStatement ps = null;
        try {
            String SQL = "SELECT * FROM contatos WHERE id = ?";
            ps = this.conn.prepareStatement(SQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {                
                Contato contato = new Contato();
                contato.setId(rs.getInt("id"));
                contato.setNome(rs.getString("nome"));
                contato.setTelefone(rs.getString("telefone"));
                contato.setEmail(rs.getString("email"));
                contato.setAnotacoes(rs.getString("anotacoes"));
                Blob blob = rs.getBlob("foto");
                if(blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] imageBytes = outputStream.toByteArray();
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    inputStream.close();
                    outputStream.close();
                    contato.setFoto(base64Image);
                }
                else {
                    contato.setFoto(null);
                }
                return contato;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        catch(IOException i) {
            i.printStackTrace();
        }
        return null;
        
    }
    
    public void delete(int id) {        
        PreparedStatement ps = null;
        try {
            String SQL = "DELETE FROM contatos WHERE id = ?";
            ps = this.conn.prepareStatement(SQL);
            ps.setInt(1, id);
            ps.executeUpdate();            
        }
        catch(SQLException e) {
            e.printStackTrace();
        }        
    }
    
    public int getNumberOfPages() {
        
        Statement stmt = null;
        ResultSet rs = null;
        int total = 0;
        
        try {
            String SQL = "SELECT COUNT(id) AS total FROM contatos";
            stmt = this.conn.createStatement();
            rs = stmt.executeQuery(SQL);
            while(rs.next()) {
                total = rs.getInt("total");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return (int)Math.ceil((double)total/quantidade);
        
    }
    
}