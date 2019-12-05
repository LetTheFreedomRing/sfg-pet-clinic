package guru.springframework.sfgpetclinic.services.jpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OwnerJPAServiceTest {

    @InjectMocks
    OwnerJPAService ownerJPAService;

    @Mock
    OwnerRepository ownerRepository;

    final static Long ownerId = 1L;
    final static String lastName = "dummy";
    static Owner owner;

    @BeforeAll
    static void setUp() {
        owner = new Owner();
        owner.setLastName(lastName);
        owner.setId(ownerId);
    }

    @Test
    void findByLastNameNotFound() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            ownerJPAService.findByLastName(lastName);
        });
    }

    @Test
    void findByLastName() {
        Mockito.when(ownerRepository.findByLastName(ArgumentMatchers.any())).thenReturn(Optional.of(owner));
        Owner foundOwner = ownerJPAService.findByLastName(lastName);

        assertEquals(lastName, foundOwner.getLastName());
    }

    @Test
    void findAll() {
        Set<Owner> owners = new HashSet<>();
        owners.add(owner);
        Mockito.when(ownerRepository.findAll()).thenReturn(owners);
        Set<Owner> foundOwners = ownerJPAService.findAll();
        assertEquals(1, foundOwners.size());
    }

    @Test
    void findById() {
        Mockito.when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(owner));
        Owner owner = ownerJPAService.findById(ownerId);
        assertEquals(ownerId, owner.getId());
    }

    @Test
    void findByIdNotFound() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            ownerJPAService.findById(ownerId);
        });
    }

    @Test
    void save() {
        Mockito.when(ownerRepository.save(ArgumentMatchers.any())).thenReturn(owner);

        Owner savedOwner = ownerJPAService.save(owner);
        assertNotNull(savedOwner);
        Mockito.verify(ownerRepository, Mockito.times(1)).save(ArgumentMatchers.any());
    }

    @Test
    void delete() {
        ownerJPAService.delete(owner);
        Mockito.verify(ownerRepository, Mockito.times(1)).delete(ArgumentMatchers.any());
    }

    @Test
    void deleteById() {
        ownerJPAService.deleteById(ownerId);
        Mockito.verify(ownerRepository, Mockito.times(1)).deleteById(ArgumentMatchers.anyLong());
    }
}