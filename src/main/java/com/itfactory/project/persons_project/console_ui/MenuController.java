package com.itfactory.project.persons_project.console_ui;



import com.itfactory.project.persons_project.dto.Person;
import com.itfactory.project.persons_project.service.PersonDao;

import java.util.List;
import java.util.Optional;

public class MenuController {
    public static void showMenu (PersonDao personDao){
        while(true){
           char c = MenuUtil.readChoice("A - Add new person\n" +
                                     "V - View person by ID\n" +
                                     "S - Show all persons\n" +
                                     "D - Delete a person\n" +
                                     "U - Update an existing person\n" +
                                     "Q - Quit", 'A', 'V', 'S', 'D','U', 'Q');

           switch (c){
               case 'A':
                   Person newPerson = EditPersonController.readNewPerson();
                   personDao.insert(newPerson);
                   break;
               case 'V':
                   int id = MenuUtil.readInt("Insert id of person to be shown: ");
                   Optional<Person> foundUser = personDao.getById(id);
                   if(foundUser.isPresent()){
                       System.out.println(foundUser);
                   }else{
                       System.out.println("There is no person with id: "+id);
                   }
                   break;
               case 'S':
                   List<Person> people = personDao.getAllPersons();
                   if(!people.isEmpty()){
                       people.forEach(System.out::println);
                   }else{
                       System.out.println("There is no person to be shown!");
                   }
                   break;
               case 'D':
                   int idDelete = MenuUtil.readInt("Insert person to be deleted: ");
                   Optional<Person> userToBeDeleted = personDao.getById(idDelete);
                   if(userToBeDeleted.isPresent()){
                       personDao.delete(idDelete);
                   }else{
                       System.out.println("There is no person with id: "+idDelete);
                   }
                   break;
               case 'U':
                   int idUpdate = MenuUtil.readInt("Insert id of person for update: ");
                   Optional<Person> userExist = personDao.getById(idUpdate);
                   if(userExist.isPresent()){
                       Person updatedPerson = EditPersonController.readPersonForUpdate(idUpdate,userExist.get());
                       personDao.getById(idUpdate).ifPresent(person -> personDao.update(updatedPerson));
                   }else{
                       System.out.println("There is no person with id: "+idUpdate);
                   }
                   break;
               default:
                   System.out.println("Bye");
                   return;
           }
        }
    }
}
