package com.test_exemplo.Model.Repository;

import com.test_exemplo.Model.Entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

@Repository
public class UserRepository {

    @Autowired
    private DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertUser(Usuario usuario) {
        String sql = """
                INSERT INTO usuario(nome_completo, email, cpf, rg, data_nascimento, senha, genero) VALUES
                (?,?,?,?,?,?,?)
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, usuario.getFullName());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getCpf());
            stmt.setString(4, usuario.getRg());
            stmt.setDate(5, Date.valueOf(usuario.getDateOfBirth()));
            stmt.setString(6, usuario.getPassword());
            stmt.setString(7, usuario.getGender());

            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuário no banco de dados: " + e.getMessage(), e);
        }
    }
}