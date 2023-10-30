import com.itfactory.project.persons_project.dto.Person;
import com.itfactory.project.persons_project.service.DatabaseInitialService;
import com.itfactory.project.persons_project.service.DbManager;
import com.itfactory.project.persons_project.service.PersonDao;
import org.junit.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;


import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ProjectTest {
    private static final String TEST_DB_FILE = "project_test.db";
    private static final List<Person> samplePerson = Arrays.asList(new Person(-1,"Marian","Dumitras","marian_dumitras@yahoo.com",20,"M"),
            new Person(-1,"Ciprian","Andrei","andrei.ciprian@yahoo.com",27,"M"),
            new Person(-1,"Maria","Popescu","maria.posecu@yahoo.com",19,"M"));

    private final PersonDao personDao = new PersonDao();

    @BeforeClass
    public static void initDbBeforeAnyTest(){
        DbManager.setDbFile(TEST_DB_FILE);
        DatabaseInitialService.createMissingTable();
    }
    @AfterClass
    public static void deleteDbAfterAllTest(){
        new File(TEST_DB_FILE).delete();
    }
    @Before
    public void insertRowsBeforeTest(){
        assertTrue(personDao.getAllPersons().isEmpty());
        for(Person item : samplePerson){
            personDao.insert(item);
        }
    }
    @After
    public void deleteRowsAfterTest(){
        personDao.getAllPersons().forEach((dto-> personDao.delete(dto.getId())));
        assertTrue(personDao.getAllPersons().isEmpty());
    }
    @Test
    public void getAll(){
        checkOnlyTheSampleItemArePresentInDb(3);
    }

    @Test
    public void get(){
        Person person1 = personDao.getAllPersons().get(0);
        assertEqualsItemExpectId(samplePerson.get(0),person1);
    }

    @Test
    public void getForInvalidId(){
        assertFalse(personDao.getById(-99).isPresent());
    }

    @Test
    public void insert(){
        assertEquals(3,personDao.getAllPersons().size());
        Person newPerson = new Person(-1,"Petru","Mane","mane_petru@yahoo.com",33,"M");
        personDao.insert(newPerson);
        checkOnlyTheSampleItemArePresentInDb(4);
    }

    @Test
    public void updateForInvalidId(){
        personDao.update(new Person(-98,"test","update","test_update@yahoo.com",33,"F"));
        checkOnlyTheSampleItemArePresentInDb(3);
    }

    @Test
    public void delete(){
        List<Person> itemsFromDb = personDao.getAllPersons();
        personDao.delete(itemsFromDb.get(0).getId());
        List<Person> itemsFromDbAfterDelete = personDao.getAllPersons();
        assertEquals(2,itemsFromDbAfterDelete.size());
        assertEqualsItemExpectId(itemsFromDb.get(1),itemsFromDbAfterDelete.get(0));
        assertEqualsItemExpectId(itemsFromDb.get(2),itemsFromDbAfterDelete.get(1));
    }

    @Test
    public void deleteFromInvalidId(){
        checkOnlyTheSampleItemArePresentInDb(3);
        personDao.delete(-55);
        checkOnlyTheSampleItemArePresentInDb(3);
    }

    private void assertEqualsItemExpectId(Person person1,Person person2){
        assertTrue("Items should be equal (exceptid):"+person1+","+person2,
                person1.getName().equals(person2.getName())&&
                person1.getSurname().equals(person2.getSurname())&&
                person1.getEmail().equals(person2.getEmail())&&
                person1.getAge() == person2.getAge()&&
        person1.getGender().equals(person2.getGender()));
    }
    private void checkOnlyTheSampleItemArePresentInDb(int expected){
        List<Person> itemsFromDb = personDao.getAllPersons();
        assertEquals(expected, itemsFromDb.size());
        assertEqualsItemExpectId(samplePerson.get(0),itemsFromDb.get(0));
        assertEqualsItemExpectId(samplePerson.get(1),itemsFromDb.get(1));
        assertEqualsItemExpectId(samplePerson.get(2),itemsFromDb.get(2));
    }
}
