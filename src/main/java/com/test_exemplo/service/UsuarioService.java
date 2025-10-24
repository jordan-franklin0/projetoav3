package com.test_exemplo.service;
import com.test_exemplo.Model.Entity.Usuario;
import com.test_exemplo.Model.Repository.UserRepository;
import com.test_exemplo.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    @Autowired
    private UserRepository userRepository;

    public void registerUser(UsuarioDTO usuarioDTO) throws Exception {
        this.validateUser(usuarioDTO);

        Usuario usuario = this.convertUser(usuarioDTO);

        this.saveUser(usuario);
    }

    private void validateUser(UsuarioDTO usuarioDTO) throws Exception {
        if (usuarioDTO.getEmail() == null || usuarioDTO.getEmail().isEmpty()) {
            throw new Exception("O e-mail é obrigatório.");
        }

        if (usuarioDTO.getCpf() == null || usuarioDTO.getCpf().length() != 11) {
            throw new Exception("CPF inválido.");
        }

        if (usuarioDTO.getSenha() == null || usuarioDTO.getSenha().length() < 6) {
            throw new Exception("A senha deve ter pelo menos 6 caracteres.");
        }

        if (!usuarioDTO.getSenha().equals(usuarioDTO.getConfirmacaoSenha())) {
            throw new Exception("As senhas não coincidem.");
        }

    }

    private Usuario convertUser(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();

        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setRg(usuarioDTO.getRg());

        return usuario;
    }

    private void saveUser(Usuario usuario) {
        userRepository.insertUser(usuario);
    }
}