package guru.springframework.sfgpetclinic.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OwnerTest {

    @Test
    void addPet() {
        Owner owner = new Owner();
        owner.setFirstName("Dummy");
        Pet pet = new Pet();

        owner.addPet(pet);

        assertEquals(pet.getOwner(), owner);
    }

    @Test
    void getPet() {
        Owner owner = new Owner();
        Pet pet = new Pet();
        pet.setName("dummy");
        owner.addPet(pet);

        assertEquals(pet, owner.getPet("Dummy"));
    }

    @Test
    void getPetNull() {
        Owner owner = new Owner();
        Pet pet = new Pet();
        pet.setName("dummy");
        owner.addPet(pet);

        assertNull(owner.getPet("not equals"));
    }

    @Test
    void testEquals() {
        Owner owner1 = new Owner();
        owner1.setAddress("dummy");
        owner1.setCity("dummy");
        owner1.setTelephone("12121212");

        Owner owner2 = new Owner();
        owner2.setAddress("dummy");
        owner2.setCity("dummy");
        owner2.setTelephone("12121212");

        assertEquals(owner1, owner2);
    }

    @Test
    void testNotEquals() {
        Owner owner1 = new Owner();
        owner1.setAddress("dummy");
        owner1.setCity("dummy");
        owner1.setTelephone("12121212");

        Owner owner2 = new Owner();
        owner2.setAddress("dummy");
        owner2.setCity("dummygd");
        owner2.setTelephone("12121212");

        assertNotEquals(owner1, owner2);
    }
}