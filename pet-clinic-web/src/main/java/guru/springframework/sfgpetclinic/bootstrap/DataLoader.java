package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import guru.springframework.sfgpetclinic.services.SpecialityService;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
    }

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }

    private void loadData() {
        Speciality speciality = new Speciality();
        speciality.setDescription("Dummy speciality description");
        Speciality savedSpeciality = specialityService.save(speciality);

        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        dog.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat);

        Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");
        owner1.setAddress("Dummy address for " + owner1.getFirstName() + " " + owner1.getLastName());
        owner1.setCity("Dummy city");
        owner1.setTelephone("12212112112");

        Pet owner1Pet = new Pet();
        owner1Pet.setName("Mimiko");
        owner1Pet.setPetType(savedCatPetType);
        owner1Pet.setBirthDate(LocalDate.now());

        owner1.getPets().add(owner1Pet);

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Fione");
        owner2.setLastName("Gleanne");
        owner2.setAddress("Dummy address for " + owner2.getFirstName() + " " + owner2.getLastName());
        owner2.setCity("Dummy city");
        owner2.setTelephone("54233234334");

        Pet owner2Pet = new Pet();
        owner2Pet.setName("Pavuvu");
        owner2Pet.setBirthDate(LocalDate.now());
        owner2Pet.setPetType(savedDogPetType);

        owner2.getPets().add(owner2Pet);

        ownerService.save(owner2);

        System.out.println("Loaded owners...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialities().add(savedSpeciality);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("George");
        vet2.setLastName("Wales");
        vet2.getSpecialities().add(savedSpeciality);

        vetService.save(vet2);

        System.out.println("Loaded vets...");
    }
}
