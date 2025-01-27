package com.policene.cinecrud.model;

public enum Gender {
    AVENTURA("Aventura"),
    ACAO ("Ação"),
    FICCAO_CIENTIFICA("Ficção Científica"),
    DRAMA("Drama"),
    COMEDIA("Comédia"),
    ROMANCE("Romance"),
    TERROR("Terror"),
    SUSPENSE("Suspense");

    private final String nome;

    Gender(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
