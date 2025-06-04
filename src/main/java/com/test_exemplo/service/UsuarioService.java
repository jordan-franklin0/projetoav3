package com.test_exemplo.service;
import com.test_exemplo.Model.Entity.Usuario;
import com.test_exemplo.Model.Repository.UserRepository;
import com.test_exemplo.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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
        if (usuarioDTO.getFullName() == null || !usuarioDTO.getFullName().matches("^[A-Za-zÀ-ÿ\\s]{2,100}$")) {
            throw new Exception("erro no nome completo");
        }

        if (usuarioDTO.getEmail() == null || !usuarioDTO.getEmail().matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$")) {
            throw new Exception("erro no email");
        }

        if (usuarioDTO.getCpf() == null || !usuarioDTO.getCpf().matches("^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$")) {
            throw new Exception("erro no CPF");
        }

        if (usuarioDTO.getRg() == null || !usuarioDTO.getRg().matches("^[0-9]{1,2}.?[0-9]{3}.?[0-9]{3}-?[0-9Xx]{1}$")) {
            throw new Exception("erro no RG");
        }

        if (usuarioDTO.getDateOfBirth() == null || usuarioDTO.getDateOfBirth().isEmpty()) {
            throw new Exception("Data de nascimento é obrigatória");
        }
        try {
            LocalDate.parse(usuarioDTO.getDateOfBirth());
        } catch (DateTimeParseException e) {
            throw new Exception("Formato de data de nascimento inválido (esperado YYYY-MM-DD)");
        }

        if (usuarioDTO.getPassword() == null || !usuarioDTO.getPassword().matches("^(?=.[A-Za-z])(?=.\\d)[A-Za-z\\d]{6,}$")) {
            throw new Exception("A senha deve ter no mínimo 6 caracteres, incluindo letras e números.");
        }

        if (usuarioDTO.getGender() == null || !(usuarioDTO.getGender().equals("Masculino") ||
                usuarioDTO.getGender().equals("Feminino") ||
                usuarioDTO.getGender().equals("Outro"))) {
            throw new Exception("Gênero inválido");
        }
    }

    private Usuario convertUser(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();

        usuario.setFullName(usuarioDTO.getFullName());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setRg(usuarioDTO.getRg());

        usuario.setDateOfBirth(LocalDate.parse(usuarioDTO.getDateOfBirth()));

        usuario.setPassword(usuarioDTO.getPassword());

        usuario.setGender(usuarioDTO.getGender());

        return usuario;
    }

    private void saveUser(Usuario usuario) {
        userRepository.insertUser(usuario);
    }

}