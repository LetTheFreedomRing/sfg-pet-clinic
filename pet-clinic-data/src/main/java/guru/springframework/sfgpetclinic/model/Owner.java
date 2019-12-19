package guru.springframework.sfgpetclinic.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "owners")
public class Owner extends Person {

    public Owner(String firstName, String lastName, String address, String city, String telephone, Set<Pet> pets) {
        super(firstName, lastName);
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.pets = pets;
    }

    @NotBlank
    private String address;
    @NotBlank
    private String city;
    @NotBlank
    private String telephone;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Pet> pets = new HashSet<>();

    public void addPet(Pet pet) {
        if (pet.isNew()) {
            pets.add(pet);
        }
        pet.setOwner(this);
    }

    public Pet getPet(String name) {
        String nameLower = name.toLowerCase();
        for (Pet pet : pets) {
            if (pet.getName().toLowerCase().equals(nameLower)) {
                return pet;
            }
        }
        return null;
    }
}
