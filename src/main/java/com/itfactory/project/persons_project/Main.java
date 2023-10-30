package com.itfactory.project.persons_project;


import com.itfactory.project.persons_project.console_ui.MenuController;
import com.itfactory.project.persons_project.service.DatabaseInitialService;
import com.itfactory.project.persons_project.service.PersonDao;

public class Main {
    public static void main(String[] args) {

        DatabaseInitialService.createMissingTable();
        PersonDao dao = new PersonDao();
        MenuController.showMenu(dao);
    }
}