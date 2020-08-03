package br.com.projeto.personal_delivery.model;

public class Usuario {

    private String id;
    private String email;
    private String senha;

    public Usuario(String email, String senha) {
        this. email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}
