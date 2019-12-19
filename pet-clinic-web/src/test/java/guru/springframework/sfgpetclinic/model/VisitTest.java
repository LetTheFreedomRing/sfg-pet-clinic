package guru.springframework.sfgpetclinic.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class VisitTest {

    @Test
    void testEqualsNoPets() {
        Visit visit1 = new Visit();
        visit1.setId(1L);
        visit1.setDescription("dummy");
        visit1.setDate(LocalDate.now());

        Visit visit2 = new Visit();
        visit2.setId(2L);
        visit2.setDescription(visit1.getDescription());
        visit2.setDate(visit1.getDate());

        assertEquals(visit1, visit2);
    }

    @Test
    void testNotEqualsNoPets() {
        Visit visit1 = new Visit();
        visit1.setDescription("dummy");
        visit1.setDate(LocalDate.now());

        Visit visit2 = new Visit();
        visit2.setDescription("not equals");
        visit2.setDate(visit1.getDate());

        assertNotEquals(visit1, visit2);
    }

    @Test
    void testEqualsWithPets() {

        Pet pet = new Pet();
        pet.setBirthDate(LocalDate.now());
        pet.setName("dummy");
        pet.setPetType(new PetType());

        Visit visit1 = new Visit();
        visit1.setDescription("dummy");
        visit1.setDate(LocalDate.now());
        visit1.setPet(pet);

        Visit visit2 = new Visit();
        visit2.setDescription(visit1.getDescription());
        visit2.setDate(visit1.getDate());
        visit2.setPet(pet);

        assertEquals(visit1, visit2);
    }

    @Test
    void testNotEqualsWithPets() {

        Pet pet1 = new Pet();
        pet1.setBirthDate(LocalDate.now());
        pet1.setName("dummy");
        pet1.setPetType(new PetType());

        Pet pet2 = new Pet();
        pet1.setBirthDate(LocalDate.now());
        pet1.setName("not equals");
        pet1.setPetType(new PetType());

        Visit visit1 = new Visit();
        visit1.setDescription("dummy");
        visit1.setDate(LocalDate.now());
        visit1.setPet(pet1);

        Visit visit2 = new Visit();
        visit2.setDescription(visit1.getDescription());
        visit2.setDate(visit1.getDate());
        visit2.setPet(pet2);

        assertNotEquals(visit1, visit2);
    }
}