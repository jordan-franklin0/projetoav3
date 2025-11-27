package com.test_exemplo.service;

import com.test_exemplo.Model.Entity.Usuario;
import com.test_exemplo.Model.Repository.UserRepository;
import com.test_exemplo.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UserRepository userRepository;

    public Long registerUser(UsuarioDTO usuarioDTO) throws Exception {
        this.validateUser(usuarioDTO);
        Usuario usuario = this.convertUser(usuarioDTO);
        return this.saveUser(usuario);
    }
    private Long saveUser(Usuario usuario) {
        return userRepository.insertUser(usuario);
    }

    public Usuario findUserByCpf(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            return null;
        }
        return userRepository.findUserByCpf(cpf);
    }

    public Usuario findUserById(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        return userRepository.findUserById(id);
    }

    public List<Usuario> findAllUsers() {
        return userRepository.findAllUsers();
    }

    public void updateUser(UsuarioDTO usuarioDTO) throws Exception {
        this.validateUser(usuarioDTO);
        Usuario usuario = this.convertUser(usuarioDTO);
        userRepository.updateUser(usuario);
    }

    public void deleteUser(String cpf) throws Exception {
        if (cpf == null || cpf.length() != 11) {
            throw new Exception("CPF inválido para exclusão.");
        }
        userRepository.deleteUser(cpf);
    }

    private void validateUser(UsuarioDTO usuarioDTO) throws Exception {
        if (usuarioDTO.getEmail() == null || usuarioDTO.getEmail().isEmpty()) {
            throw new Exception("O email é obrigatório.");
        }
        if (usuarioDTO.getCpf() == null || usuarioDTO.getCpf().length() != 11) {
            throw new Exception("O CPF deve conter 11 dígitos.");
        }
        if (usuarioDTO.getSenha() == null || usuarioDTO.getSenha().length() < 6) {
            throw new Exception("A senha deve conter no mínimo 6 caracteres.");
        }
        if (!usuarioDTO.getSenha().equals(usuarioDTO.getConfirmacaoSenha())) {
            throw new Exception("A senha e a confirmação de senha não são iguais.");
        }
    }

    private Usuario convertUser(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setRg(usuarioDTO.getRg());
        usuario.setNome(usuarioDTO.getNome());
        usuario.setDataNascimento(usuarioDTO.getDataNascimento());
        usuario.setGenero(usuarioDTO.getGenero());
        usuario.setCelular(usuarioDTO.getCelular());
        usuario.setSenha(usuarioDTO.getSenha());
        return usuario;
    }
}