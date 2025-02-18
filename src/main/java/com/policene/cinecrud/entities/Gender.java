package com.policene.cinecrud.entities;

public enum Gender {
    FICCAO_CIENTIFICA ("Ficção Científica"),
    ACAO ("Ação"),
    AVENTURA ("Aventura"),
    ROMANCE ("Romance"),
    TERROR ("Terror"),
    DRAMA ("Drama"),
    SUSPENSE ("Suspense"),
    COMEDIA ("Comédia");

    private String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Gender verifyGender (String inputGender) {
        for (Gender gender : Gender.values()) {
            if (inputGender.equalsIgnoreCase(gender.getDescription())){
                return gender;
            }
        }
        return null;
    }


}
