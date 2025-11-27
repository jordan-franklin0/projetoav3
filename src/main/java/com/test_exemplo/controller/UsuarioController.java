package com.test_exemplo.controller;

import com.test_exemplo.dto.UsuarioDTO;
import com.test_exemplo.dto.EnderecoDTO;
import com.test_exemplo.Model.Entity.Usuario;
import com.test_exemplo.Model.Entity.Endereco;
import com.test_exemplo.service.UsuarioService;
import com.test_exemplo.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    EnderecoService enderecoService;

    @GetMapping("/recuperarAcesso")
    public ModelAndView getRecuperarAcessoPage() {
        return new ModelAndView("recuperarAcesso");
    }

    @GetMapping("/emailEnviado.html")
    public ModelAndView getEmailEnviadoPage() {
        return new ModelAndView("emailEnviado");
    }

    @GetMapping("/cadastro")
    public ModelAndView getCadastroPage() {
        ModelAndView modelAndView = new ModelAndView("cadastro");
        modelAndView.addObject("usuarioDTO", new UsuarioDTO());
        return modelAndView;
    }

    @GetMapping({"/", "/login"})
    public ModelAndView getLoginPage() {
        return new ModelAndView("login");
    }

    @GetMapping ("/userForms")
    public ModelAndView getUserForms() {
        return new ModelAndView("userForms");
    }

    @GetMapping ("/erroUserForms")
    public ModelAndView geterroUserForms() {
        return new ModelAndView("erroUserForms");
    }



    @PostMapping("/cadastro")
    public ModelAndView registerUser(@ModelAttribute UsuarioDTO usuarioDTO) {
        try{
            Long newUserId = usuarioService.registerUser(usuarioDTO);

            ModelAndView mv = new ModelAndView("endereço");

            EnderecoDTO enderecoDTO = new EnderecoDTO();
            enderecoDTO.setUsuarioId(newUserId);

            mv.addObject("enderecoDTO", enderecoDTO);
            return mv;
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("cadastro");
            mv.addObject("usuarioDTO", usuarioDTO);
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }

    @PostMapping("/endereco")
    public ModelAndView registerEndereco(@ModelAttribute EnderecoDTO enderecoDTO) {
        try {
            enderecoService.registerEndereco(enderecoDTO);
            return new ModelAndView("redirect:/login");
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("endereço");
            mv.addObject("enderecoDTO", enderecoDTO);
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }


    @GetMapping("/usuarios")
    public ModelAndView listUsers() {
        List<Usuario> usuarios = usuarioService.findAllUsers();
        ModelAndView mv = new ModelAndView("listaUsuarios");
        mv.addObject("usuarios", usuarios);
        return mv;
    }

    @GetMapping("/usuario/{cpf}")
    public ModelAndView viewUserDetails(@PathVariable String cpf) {
        Usuario usuario = usuarioService.findUserByCpf(cpf);
        ModelAndView mv = new ModelAndView("detalhesUsuario");

        if (usuario != null) {
            Endereco endereco = enderecoService.findEnderecoByUsuarioId(usuario.getId());
            mv.addObject("usuario", usuario);
            mv.addObject("endereco", endereco);
        } else {
            mv.addObject("erro", "Usuário não encontrado.");
        }
        return mv;
    }


    @GetMapping("/usuario/editar/{cpf}")
    public ModelAndView getEditUserForm(@PathVariable String cpf) {
        Usuario usuario = usuarioService.findUserByCpf(cpf);
        ModelAndView mv = new ModelAndView("editarUsuario");

        if (usuario != null) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setCpf(usuario.getCpf());
            usuarioDTO.setNome(usuario.getNome());
            usuarioDTO.setEmail(usuario.getEmail());
            usuarioDTO.setRg(usuario.getRg());
            usuarioDTO.setCelular(usuario.getCelular());
            usuarioDTO.setGenero(usuario.getGenero());
            usuarioDTO.setDataNascimento(usuario.getDataNascimento());

            Endereco endereco = enderecoService.findEnderecoByUsuarioId(usuario.getId());

            EnderecoDTO enderecoDTO = new EnderecoDTO();
            enderecoDTO.setUsuarioId(usuario.getId());
            if (endereco != null) {
                enderecoDTO.setCep(endereco.getCep());
                enderecoDTO.setLogradouro(endereco.getLogradouro());
                enderecoDTO.setNumero(endereco.getNumero());
                enderecoDTO.setBairro(endereco.getBairro());
                enderecoDTO.setMunicipio(endereco.getMunicipio());
                enderecoDTO.setEstado(endereco.getEstado());
                enderecoDTO.setComplemento(endereco.getComplemento());
            }

            mv.addObject("usuarioDTO", usuarioDTO);
            mv.addObject("enderecoDTO", enderecoDTO);
            return mv;

        } else {
            mv.setViewName("redirect:/usuarios");
            return mv;
        }
    }

    @PostMapping("/usuario/editar")
    public ModelAndView processEditUser(@ModelAttribute UsuarioDTO usuarioDTO) {

        try {

            usuarioService.updateUser(usuarioDTO);

            EnderecoDTO enderecoDTO = (EnderecoDTO) new ModelAndView().getModel().get("enderecoDTO");


            Usuario usuario = usuarioService.findUserByCpf(usuarioDTO.getCpf());

            if (usuario != null) {
                enderecoDTO.setUsuarioId(usuario.getId());
                enderecoService.updateEndereco(enderecoDTO);
            } else {
                throw new Exception("Usuário não encontrado durante o processo de edição.");
            }

            return new ModelAndView("redirect:/usuario/" + usuarioDTO.getCpf());

        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("editarUsuario");
            mv.addObject("usuarioDTO", usuarioDTO);
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }



    @PostMapping("/usuario/deletar/{cpf}")
    public ModelAndView deleteUser(@PathVariable String cpf) {
        try {
            Usuario usuario = usuarioService.findUserByCpf(cpf);
            if (usuario != null && usuario.getId() != null) {
                enderecoService.deleteEnderecoByUsuarioId(usuario.getId());
            }
            usuarioService.deleteUser(cpf);

            return new ModelAndView("redirect:/usuarios");

        } catch (Exception e) {
            return new ModelAndView("redirect:/usuario/" + cpf);
        }
    }
}