package br.com.projeto.personal_delivery.model;

public class Professor {

    public String experiencia;
    String nome;

    public Professor(String nome, String experiencia) {
        this.nome = nome;
        this.experiencia = experiencia;
    }

    public String getNome() {
        return nome;
    }

    public String getExperiencia() {
        return experiencia;
    }
}
