package com.test_exemplo.service;

import com.test_exemplo.Model.Entity.Endereco;
import com.test_exemplo.Model.Repository.EnderecoRepository;
import com.test_exemplo.dto.EnderecoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public void registerEndereco(EnderecoDTO enderecoDTO) throws Exception {
        this.validateEndereco(enderecoDTO);

        Endereco endereco = this.convertEndereco(enderecoDTO);
        enderecoRepository.insertEndereco(endereco);
    }

    public Endereco findEnderecoByUsuarioId(Long usuarioId) {
        if (usuarioId == null || usuarioId <= 0) {
            return null;
        }
        return enderecoRepository.findEnderecoByUsuarioId(usuarioId);
    }

    public void updateEndereco(EnderecoDTO enderecoDTO) throws Exception {
        this.validateEndereco(enderecoDTO);
        Endereco endereco = this.convertEndereco(enderecoDTO);
        enderecoRepository.updateEndereco(endereco);
    }

    public void deleteEnderecoByUsuarioId(Long usuarioId) throws Exception {
        if (usuarioId == null || usuarioId <= 0) {
            throw new Exception("ID do usuário inválido para exclusão do endereço.");
        }
        enderecoRepository.deleteEnderecoByUsuarioId(usuarioId);
    }


    private void validateEndereco(EnderecoDTO enderecoDTO) throws Exception {
        if (enderecoDTO.getCep() == null || enderecoDTO.getCep().isEmpty()) {
            throw new Exception("O CEP é obrigatório.");
        }
        if (enderecoDTO.getLogradouro() == null || enderecoDTO.getLogradouro().isEmpty()) {
            throw new Exception("O Logradouro é obrigatório.");
        }
        if (enderecoDTO.getNumero() == null || enderecoDTO.getNumero().isEmpty()) {
            throw new Exception("O Número é obrigatório.");
        }
        if (enderecoDTO.getUsuarioId() == null || enderecoDTO.getUsuarioId() <= 0) {
            throw new Exception("ID do usuário não fornecido ou inválido.");
        }
    }

    private Endereco convertEndereco(EnderecoDTO enderecoDTO) {
        Endereco endereco = new Endereco();

        endereco.setUsuarioId(enderecoDTO.getUsuarioId());
        endereco.setCep(enderecoDTO.getCep());
        endereco.setLogradouro(enderecoDTO.getLogradouro());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setMunicipio(enderecoDTO.getMunicipio());
        endereco.setEstado(enderecoDTO.getEstado());
        endereco.setComplemento(enderecoDTO.getComplemento());

        return endereco;
    }
}