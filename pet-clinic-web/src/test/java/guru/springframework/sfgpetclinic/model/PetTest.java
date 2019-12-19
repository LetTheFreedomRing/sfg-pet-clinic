package guru.springframework.sfgpetclinic.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PetTest {

    @Test
    void addVisit() {
        Pet pet = new Pet();

        Visit visit = new Visit();
        visit.setDate(LocalDate.now());
        visit.setDescription("dummy");

        pet.addVisit(visit);

        assertEquals(visit.getPet(), pet);
    }

    @Test
    void testEquals() {
        Owner owner1 = new Owner();
        owner1.setFirstName("1");
        Owner owner2 = new Owner();
        owner1.setFirstName("2");

        Pet pet1 = new Pet();
        pet1.setName("dummy");
        pet1.setId(1L);
        pet1.setPetType(new PetType());
        pet1.setBirthDate(LocalDate.now());

        Pet pet2 = new Pet();
        pet2.setName("dummy");
        pet2.setId(2L);
        pet2.setPetType(new PetType());
        pet2.setBirthDate(pet1.getBirthDate());

        assertEquals(pet1, pet2);
    }

    @Test
    void testNotEquals() {
        Pet pet1 = new Pet();
        pet1.setName("dummy");
        pet1.setPetType(new PetType());
        pet1.setBirthDate(LocalDate.now());

        Pet pet2 = new Pet();
        pet2.setName("not equals");
        pet2.setPetType(new PetType());
        pet2.setBirthDate(pet1.getBirthDate());

        assertNotEquals(pet1, pet2);
    }
}