package com.test_exemplo.Model.Repository;

import com.test_exemplo.Model.Entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class UserRepository {

    @Autowired
    private DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertUser(Usuario usuario) {

        String sql = """
                INSERT INTO usuario (email, cpf, rg, nome, data_nascimento, genero, celular, senha) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getRg());
            stmt.setString(4, usuario.getNome());
            stmt.setString(5, usuario.getDataNascimento());
            stmt.setString(6, usuario.getGenero());
            stmt.setString(7, usuario.getCelular());
            stmt.setString(8, usuario.getSenha());

            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuário no banco de dados", e);
        }
    }
}