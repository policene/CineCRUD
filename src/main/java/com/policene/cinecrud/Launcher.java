package com.policene.cinecrud;

import com.policene.cinecrud.config.DatabaseConnection;

public class Launcher {
    public static void main(String[] args) {
        new DatabaseConnection();
        App.main(args);
    }
}
