package com.policene.cinecrud;

import com.policene.cinecrud.config.DatabaseConnection;

public class Launcher {
    public static void main(String[] args) {
        DatabaseConnection db = new DatabaseConnection();
        System.out.println("Banco de dados pronto para uso!");
        App.main(args);
    }
}
