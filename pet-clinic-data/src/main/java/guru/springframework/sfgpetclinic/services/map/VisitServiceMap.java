package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.springframework.stereotype.Service;

@Service
public class VisitServiceMap extends AbstractMapService<Visit, Long> implements VisitService {

    private final PetService petService;

    public VisitServiceMap(PetService petService) {
        this.petService = petService;
    }

    @Override
    public Visit save(Visit visit) {
        if (visit != null) {
            if (visit.getPet() != null) {
                if (visit.getPet().getId() == null) {
                    Pet savedPet = petService.save(visit.getPet());
                    visit.getPet().setId(savedPet.getId());
                }
            } else {
                throw new RuntimeException("Pet is required");
            }
            return super.save(visit);
        }
        return null;
    }
}
