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
        }

            throw new Exception("erro no email");
        }
    }

    private Usuario convertUser(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();

        usuario.setEmail(usuarioDTO.getEmail());

        return usuario;
    }

    private void saveUser(Usuario usuario) {
        userRepository.insertUser(usuario);
    }

}