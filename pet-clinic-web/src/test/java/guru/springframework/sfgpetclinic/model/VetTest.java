package guru.springframework.sfgpetclinic.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class VetTest {

    @Test
    void testEquals() {
        Set<Speciality> specialities = new HashSet<>();
        specialities.add(new Speciality());

        Vet vet1 = new Vet();
        vet1.setSpecialities(specialities);

        Vet vet2 = new Vet();
        vet2.setSpecialities(specialities);

        assertEquals(vet1, vet2);
    }

    @Test
    void testNotEquals() {
        Set<Speciality> specialities1 = new HashSet<>();
        specialities1.add(new Speciality());

        Set<Speciality> specialities2 = new HashSet<>();
        Speciality speciality = new Speciality();
        speciality.setDescription("dflkgksdlg");
        specialities2.add(speciality);

        Vet vet1 = new Vet();
        vet1.setSpecialities(specialities1);

        Vet vet2 = new Vet();
        vet2.setSpecialities(specialities2);

        assertNotEquals(vet1, vet2);
    }
}