package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {

    OwnerServiceMap service;

    final Long ownerId = 1L;

    @BeforeEach
    public void setUp() {
        service = new OwnerServiceMap(new PetTypeServiceMap(), new PetServiceMap());
        Owner owner = new Owner();
        owner.setId(ownerId);
        service.save(owner);
    }

    @Test
    void findAll() {
        Set<Owner> owners = service.findAll();
        assertEquals(1, owners.size());
    }

    @Test
    void findById() {
        Owner owner = service.findById(ownerId);

        assertEquals(ownerId, owner.getId());
    }

    @Test
    void saveWithId() {
        Long owner2Id = 2L;
        Owner owner2 = new Owner();
        owner2.setId(owner2Id);

        Owner savedOwner = service.save(owner2);
        assertEquals(owner2Id, savedOwner.getId());
    }

    @Test
    void saveNoId() {
        Owner savedOwner = service.save(new Owner());
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void delete() {
        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setFirstName("Dummy");
        service.save(owner2);

        service.delete(owner2);
        assertEquals(1, service.findAll().size());
    }

    @Test
    void deleteById() {
        service.deleteById(ownerId);
        assertEquals(0, service.findAll().size());
    }

    @Test
    void findByLastName() {
        String lastName = "Dummy";
        Owner owner = new Owner();
        owner.setLastName(lastName);
        service.save(owner);

        Owner foundOwner = service.findByLastName(lastName);
        assertNotNull(foundOwner);
        assertEquals(lastName, foundOwner.getLastName());
    }

    @Test
    void findByLastNameNotFound() {
        String lastName = "Dummy";
        Owner owner = new Owner();
        owner.setLastName(lastName);
        service.save(owner);

        Owner foundOwner = service.findByLastName("lastName");
        assertNull(foundOwner);
    }
}