package com.itfactory.project.persons_project.service;



import com.itfactory.project.persons_project.dto.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.itfactory.project.persons_project.service.DbManager.getConnection;


public class PersonDao {
    public List<Person> getAllPersons() {
        StringBuilder query = new StringBuilder();
        query.append(" SELECT * FROM ");
        query.append(PersonTable.TABLE_NAME);

        List<Person> people = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query.toString());
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                people.add(extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all persons" + e.getMessage());
        }
        return people;
    }

    public Optional<Person> getById(long id) {
        StringBuilder query = new StringBuilder();
        query.append(" SELECT * FROM ");
        query.append(PersonTable.TABLE_NAME);
        query.append(" WHERE ");
        query.append(PersonTable.FIELD_ID);
        query.append(" = ? ");

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query.toString())) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Person person = extractFromResultSet(rs);
                    return Optional.of(person);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting person with id = " + id +" "+ e.getMessage());
        }
        return Optional.empty();
    }

    public void insert(Person person) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ").append(PersonTable.TABLE_NAME);
        query.append(" ( ").append(PersonTable.FIELD_NAME).append(", ").append(PersonTable.FIELD_SURNAME)
                .append(", ").append(PersonTable.FIELD_EMAIL).append(", ").append(PersonTable.FIELD_AGE)
                .append(", ").append(PersonTable.GENDER).append(") ");
        query.append(" VALUES (?,?,?,?,?) ");

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query.toString())) {
             ps.setString(1, person.getName());
             ps.setString(2, person.getSurname());
             ps.setString(3, person.getEmail());
             ps.setInt(4, person.getAge());
             ps.setString(5, person.getGender());

             ps.execute();
        } catch (SQLException e) {
            System.err.println("Error inserting in db person  =" + person.toString() + e.getMessage());
        }
    }



    public void update(Person person) {
        StringBuilder query = new StringBuilder();
        query.append(" UPDATE ").append(PersonTable.TABLE_NAME);
        query.append(" SET ").append(PersonTable.FIELD_NAME).append(" = ?, ")
                .append(PersonTable.FIELD_SURNAME).append(" = ?, ")
                .append(PersonTable.FIELD_EMAIL).append(" = ?, ")
                .append(PersonTable.FIELD_AGE).append(" = ?, ")
                .append(PersonTable.GENDER).append(" = ? ");
        query.append(" WHERE ").append(PersonTable.FIELD_ID).append(" = ? ");
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query.toString())) {
             ps.setString(1, person.getName());
             ps.setString(2, person.getSurname());
             ps.setString(3, person.getEmail());
             ps.setInt(4, person.getAge());
            ps.setString(5, person.getGender());
             ps.setLong(6, person.getId());

             ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating in db person  =" + person + e.getMessage());
        }
    }

    public void delete(long id) {
        StringBuilder query = new StringBuilder();
        query.append(" DELETE FROM ").append(PersonTable.TABLE_NAME);
        query.append(" WHERE ").append(PersonTable.FIELD_ID).append(" = ? ");
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query.toString())) {

            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException e) {
            System.err.println("Error deleting person with id = " + id + " " + e.getMessage());
        }
    }

    private Person extractFromResultSet(ResultSet rs) throws SQLException {
        long id = rs.getLong(PersonTable.FIELD_ID);
        String name = rs.getString(PersonTable.FIELD_NAME);
        String surname = rs.getString(PersonTable.FIELD_SURNAME);
        String email = rs.getString(PersonTable.FIELD_EMAIL);
        int age = rs.getInt(PersonTable.FIELD_AGE);
        String gender = rs.getString(PersonTable.GENDER);
        return new Person(id, name, surname, email, age,gender);
    }
}
