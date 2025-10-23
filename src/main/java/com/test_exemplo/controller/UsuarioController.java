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

    @PostMapping("/userForms")
    public ModelAndView registerUser(@ModelAttribute UsuarioDTO usuarioDTO) {
        try{
            usuarioService.registerUser(usuarioDTO);
            return this.getUserForms();
        } catch (Exception e) {
            return this.geterroUserForms();
        }
    }
}