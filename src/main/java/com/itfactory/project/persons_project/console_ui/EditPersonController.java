package com.itfactory.project.persons_project.console_ui;


import com.itfactory.project.persons_project.dto.Person;

public class EditPersonController {
    public static Person readNewPerson() {
        System.out.println("Enter details to create new person : ");
        String name = MenuUtil.readString("Name: ", false);
        String surname = MenuUtil.readString("Surname: ", false);
        String email = MenuUtil.readString("Email: ", false);
        int age = MenuUtil.readInt("Age: ");
        String gender = MenuUtil.readString("Gender: ",false);
        return new Person(name, surname, email, age,gender);
    }

    public static Person readPersonForUpdate(long id, Person existingPerson) {
        System.out.println("Enter details to edit existing person with id = "+ id+" : ");
        String name = MenuUtil.readString("Name: ", true);
        String surname = MenuUtil.readString("Surname: ", true);
        String email = MenuUtil.readString("Email: ", true);
        int age = MenuUtil.readInt("Age: ");
        String gender = MenuUtil.readString("Gender: ", true);

        return new Person(id,
                name.isEmpty() ? existingPerson.getName():name,
                surname.isEmpty() ? existingPerson.getSurname() : surname,
                email.isEmpty() ? existingPerson.getEmail():email,
                age ==0 ? existingPerson.getAge():age,
                gender.isEmpty()? existingPerson.getGender() : gender);
    }
}
