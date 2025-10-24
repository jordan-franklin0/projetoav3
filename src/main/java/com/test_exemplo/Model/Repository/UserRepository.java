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
        // SQL CORRIGIDO (assumindo que sua tabela 'usuario' tem essas colunas)
        String sql = """
                INSERT INTO usuario (email, cpf, rg) 
                VALUES (?, ?, ?)
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Índices começam em 1
            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getRg());

            stmt.execute();
        } catch (SQLException e) {
            // É uma boa prática relançar a exceção para o Service tratar
            throw new RuntimeException("Erro ao inserir usuário no banco de dados", e);
        }
    }
}