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
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(2, usuario.getEmail());

            stmt.execute();
        } catch (SQLException e) {
        }
    }
}