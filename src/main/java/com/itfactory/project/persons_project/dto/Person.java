package com.itfactory.project.persons_project.dto;

import java.util.Objects;

public class Person {
    private final long id;
    private final String name;
    private final String surname;
    private final String email;
    private final int age;
    private final String gender;

    public String getGender() {
        return gender;
    }

    public Person(long id, String name, String surname, String email, int age, String gender) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.age = age;
        this.gender = gender;

    }

    public Person(String name, String surname, String email, int age, String gender) {
        this(-1, name, surname, email, age,gender);
    }

    public long getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && age == person.age && Objects.equals(name, person.name) && Objects.equals(surname, person.surname) && Objects.equals(email, person.email) && Objects.equals(gender, person.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, age, gender);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}
