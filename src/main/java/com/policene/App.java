package com.policene;


import com.policene.cinecrud.model.dao.MovieDAO;

public class App {
    public static void main(String[] args) {

        MovieDAO movieDAO = new MovieDAO();


        if(movieDAO.listAll().isEmpty()){
            System.out.println("Nenhum filme encontrado.");
        }
    }
}