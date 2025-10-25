package com.test_exemplo.controller;

import com.test_exemplo.dto.UsuarioDTO;
import com.test_exemplo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/cadastro")
    public ModelAndView getCadastroPage() {
        ModelAndView modelAndView = new ModelAndView("cadastro");
        modelAndView.addObject("usuarioDTO", new UsuarioDTO());
        return modelAndView;
    }

    @GetMapping({"/", "/login"})
    public ModelAndView getLoginPage() {
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }

    @PostMapping("/cadastro")
    public ModelAndView registerUser(@ModelAttribute UsuarioDTO usuarioDTO) {
        try{
            usuarioService.registerUser(usuarioDTO);
            ModelAndView mv = new ModelAndView("endereço");
            return mv;
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("cadastro");
            mv.addObject("usuarioDTO", usuarioDTO);
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }

    @GetMapping ("/userForms")
    public ModelAndView getUserForms() {
        ModelAndView modelAndView = new ModelAndView("userForms");
        return modelAndView;
    }

    @GetMapping ("/erroUserForms")
    public ModelAndView geterroUserForms() {
        ModelAndView modelAndView = new ModelAndView("erroUserForms");
        return modelAndView;
    }
}
