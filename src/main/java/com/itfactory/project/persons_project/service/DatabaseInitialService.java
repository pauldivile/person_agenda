package com.itfactory.project.persons_project.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitialService {
    public static void createMissingTable(){
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS ").append(PersonTable.TABLE_NAME).append(" ( ");
        query.append(PersonTable.FIELD_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        query.append(PersonTable.FIELD_NAME).append(" TEXT NOT NULL, ");
        query.append(PersonTable.FIELD_SURNAME).append(" TEXT NOT NULL, ");
        query.append(PersonTable.FIELD_AGE).append(" INTEGER NOT NULL,  ");
        query.append(PersonTable.FIELD_EMAIL).append(" TEXT NOT NULL,  ");
        query.append(PersonTable.GENDER).append(" TEXT NOT NULL,  ");
        query.append(" CONSTRAINT MAIL_UNQ UNIQUE ( ");
        query.append(PersonTable.FIELD_EMAIL).append(" )); ");

        try(Connection connInt  = DbManager.getConnection();
            Statement st = connInt.createStatement()){
            st.execute(query.toString());
         } catch (SQLException e ){
            System.err.println("Error creating missing table " + e.getMessage());
        }
    }
}
