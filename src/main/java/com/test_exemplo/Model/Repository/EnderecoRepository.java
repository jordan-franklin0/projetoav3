package com.test_exemplo.Model.Repository;

import com.test_exemplo.Model.Entity.Endereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class EnderecoRepository {

    @Autowired
    private DataSource dataSource;

    public EnderecoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertEndereco(Endereco endereco) {
        String sql = """
                INSERT INTO endereco (usuario_id, cep, logradouro, numero, bairro, municipio, estado, complemento) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, endereco.getUsuarioId());
            stmt.setString(2, endereco.getCep());
            stmt.setString(3, endereco.getLogradouro());
            stmt.setString(4, endereco.getNumero());
            stmt.setString(5, endereco.getBairro());
            stmt.setString(6, endereco.getMunicipio());
            stmt.setString(7, endereco.getEstado());
            stmt.setString(8, endereco.getComplemento());

            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir endereço no banco de dados", e);
        }
    }

    public Endereco findEnderecoByUsuarioId(Long usuarioId) {
        String sql = "SELECT id, usuario_id, cep, logradouro, numero, bairro, municipio, estado, complemento FROM endereco WHERE usuario_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, usuarioId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEndereco(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar endereço por ID de usuário no banco de dados", e);
        }
    }

    public void updateEndereco(Endereco endereco) {
        String sql = """
            UPDATE endereco SET cep = ?, logradouro = ?, numero = ?, bairro = ?, municipio = ?, estado = ?, complemento = ?
            WHERE usuario_id = ?
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, endereco.getCep());
            stmt.setString(2, endereco.getLogradouro());
            stmt.setString(3, endereco.getNumero());
            stmt.setString(4, endereco.getBairro());
            stmt.setString(5, endereco.getMunicipio());
            stmt.setString(6, endereco.getEstado());
            stmt.setString(7, endereco.getComplemento());
            stmt.setLong(8, endereco.getUsuarioId());

            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar endereço no banco de dados", e);
        }
    }

    public void deleteEnderecoByUsuarioId(Long usuarioId) {
        String sql = "DELETE FROM endereco WHERE usuario_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, usuarioId);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar endereço no banco de dados", e);
        }
    }

    private Endereco mapResultSetToEndereco(ResultSet rs) throws SQLException {
        Endereco endereco = new Endereco();
        endereco.setId(rs.getLong("id"));
        endereco.setUsuarioId(rs.getLong("usuario_id"));
        endereco.setCep(rs.getString("cep"));
        endereco.setLogradouro(rs.getString("logradouro"));
        endereco.setNumero(rs.getString("numero"));
        endereco.setBairro(rs.getString("bairro"));
        endereco.setMunicipio(rs.getString("municipio"));
        endereco.setEstado(rs.getString("estado"));
        endereco.setComplemento(rs.getString("complemento"));
        return endereco;
    }
}