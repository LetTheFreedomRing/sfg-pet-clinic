package guru.springframework.sfgpetclinic.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SpecialityTest {

    @Test
    void testEquals() {
        Speciality speciality1 = new Speciality();
        speciality1.setId(1L);
        speciality1.setDescription("dummy");

        Speciality speciality2 = new Speciality();
        speciality2.setId(2L);
        speciality2.setDescription("dummy");

        assertEquals(speciality1, speciality2);
    }

    @Test
    void testNotEquals() {
        Speciality speciality1 = new Speciality();
        speciality1.setId(1L);
        speciality1.setDescription("not equals");

        Speciality speciality2 = new Speciality();
        speciality2.setId(1L);
        speciality2.setDescription("dummy");

        assertNotEquals(speciality1, speciality2);
    }
}