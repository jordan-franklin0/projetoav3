package com.test_exemplo.Model.Repository;

import com.test_exemplo.Model.Entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Long insertUser(Usuario usuario) {
        String sql = """
                INSERT INTO usuario (email, cpf, rg, nome, data_nascimento, genero, celular, senha) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getRg());
            stmt.setString(4, usuario.getNome());
            stmt.setString(5, usuario.getDataNascimento());
            stmt.setString(6, usuario.getGenero());
            stmt.setString(7, usuario.getCelular());
            stmt.setString(8, usuario.getSenha());

            stmt.execute();

            try (java.sql.ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuário no banco de dados", e);
        }
    }

    public Usuario findUserByCpf(String cpf) {
        String sql = "SELECT id, email, cpf, rg, nome, data_nascimento, genero, celular, senha FROM usuario WHERE cpf = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUsuario(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por CPF no banco de dados", e);
        }
    }

    public Usuario findUserById(Long id) {
        String sql = "SELECT id, email, cpf, rg, nome, data_nascimento, genero, celular, senha FROM usuario WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUsuario(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por ID no banco de dados", e);
        }
    }

    public List<Usuario> findAllUsers() {
        String sql = "SELECT id, email, cpf, rg, nome, data_nascimento, genero, celular, senha FROM usuario ORDER BY nome";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                usuarios.add(mapResultSetToUsuario(rs));
            }
            return usuarios;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os usuários no banco de dados", e);
        }
    }

    public void updateUser(Usuario usuario) {
        String sql = """
            UPDATE usuario SET email = ?, rg = ?, nome = ?, data_nascimento = ?, genero = ?, celular = ?, senha = ?
            WHERE cpf = ?
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getRg());
            stmt.setString(3, usuario.getNome());
            stmt.setString(4, usuario.getDataNascimento());
            stmt.setString(5, usuario.getGenero());
            stmt.setString(6, usuario.getCelular());
            stmt.setString(7, usuario.getSenha());
            stmt.setString(8, usuario.getCpf());

            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário no banco de dados", e);
        }
    }

    public void deleteUser(String cpf) {
        String sql = "DELETE FROM usuario WHERE cpf = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário no banco de dados", e);
        }
    }

    private Usuario mapResultSetToUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setEmail(rs.getString("email"));
        usuario.setCpf(rs.getString("cpf"));
        usuario.setRg(rs.getString("rg"));
        usuario.setNome(rs.getString("nome"));
        usuario.setDataNascimento(rs.getString("data_nascimento"));
        usuario.setGenero(rs.getString("genero"));
        usuario.setCelular(rs.getString("celular"));
        usuario.setSenha(rs.getString("senha"));
        return usuario;
    }
}