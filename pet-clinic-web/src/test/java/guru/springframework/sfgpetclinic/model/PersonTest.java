package guru.springframework.sfgpetclinic.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PersonTest {

    @Test
    void testEquals() {
        Person person1 = new Person();
        person1.setFirstName("dummy");
        person1.setLastName("dummy");
        person1.setId(1L);

        Person person2 = new Person();
        person2.setFirstName("dummy");
        person2.setLastName("dummy");
        person2.setId(2L);

        assertEquals(person1, person2);
    }

    @Test
    void testNotEquals() {
        Person person1 = new Person();
        person1.setFirstName("dummy");
        person1.setLastName("dummy");

        Person person2 = new Person();
        person2.setFirstName("dummy");
        person2.setLastName("not equals");

        assertNotEquals(person1, person2);
    }
}