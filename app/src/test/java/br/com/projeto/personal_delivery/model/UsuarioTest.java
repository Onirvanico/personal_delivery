package br.com.projeto.personal_delivery.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class UsuarioTest {

    @Test
    public void getEmail() {

    }

    @Test
    public void deve_PermitirSenha_QuandoSenhaMaiorOuIgualASeis() {
        Usuario usuario = new Usuario("cristianosantosesilva@gmail.com", "331425");

        assertEquals(6, usuario.getSenha().length(), 20);
    }
}