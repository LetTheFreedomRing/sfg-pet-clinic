package guru.springframework.sfgpetclinic.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PetTypeTest {

    @Test
    void testEquals() {
        PetType petType1 = new PetType();
        petType1.setId(1L);
        petType1.setName("dummy");

        PetType petType2 = new PetType();
        petType2.setId(2L);
        petType2.setName("dummy");

        assertEquals(petType1, petType2);
    }

    @Test
    void testNotEquals() {
        PetType petType1 = new PetType();
        petType1.setName("dummy");

        PetType petType2 = new PetType();
        petType2.setName("not equals");

        assertNotEquals(petType1, petType2);
    }
}