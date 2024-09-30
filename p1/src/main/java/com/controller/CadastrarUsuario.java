package com.controller;
import java.sql.*;

import com.model.Usuario;
import com.repository.DatabaseConection;

import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;



public class CadastrarUsuario {

    public void adicionarUsuario(Usuario usuario) throws SQLException
    {
       
        String sql = "INSERT INTO usuarios (nome, telefone, email, cpf, senha) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getTelefone());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getCpf());
            stmt.setString(5, BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt()));
            stmt.executeUpdate();
        }
    }


    public void editarUsuario(String cpf, Usuario usuario) throws SQLException
    {
        String sql = "UPDATE usuarios SET nome = ?, telefone = ?, email = ?, senha = ? WHERE cpf = ?  ";
        
        try (Connection conn = DatabaseConection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome() );
            stmt.setString(2, usuario.getTelefone() );
            stmt.setString(3, usuario.getEmail() );
            stmt.setString(4, usuario.getSenha() );
            stmt.setString(5, usuario.getCpf()) ;
            stmt.executeUpdate();

        }


    }

    public void excluirUsuario(String cpf) throws SQLException
    {
        String sql = "DELETE FROM usuarios WHERE cpf = ?";

        try(Connection conn = DatabaseConection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql) )
        {
            stmt.setString(1, cpf);
            stmt.executeUpdate();

        }
    }

    public List<Usuario> listarUsuarios(String filtro) throws SQLException
    {
        String sql = "SELECT * FROM usuarios WHERE nome ILIKE ? OR email ILIKE ? OR cpf LIKE ? ";

        List<Usuario> usuarios = new ArrayList<>();

        try(Connection conn = DatabaseConection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql))
        {

            stmt.setString(1, "%" + filtro + "%");
            stmt.setString(2, "%" + filtro + "%");
            stmt.setString(3, "%" + filtro + "%");

            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setTelefone(rs.getString("telefone"));
                usuario.setEmail(rs.getString("email"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setSenha(rs.getString("senha"));

                usuarios.add(usuario);

            }

        }

        return usuarios;

    }


}
