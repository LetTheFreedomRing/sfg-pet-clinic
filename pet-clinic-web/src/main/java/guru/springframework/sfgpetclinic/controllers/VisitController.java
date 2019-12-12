package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/owners/*/pets/{petId}/visits")
@Controller
public class VisitController {

    private static final String VIEWS_VISITS_CREATE_OR_UPDATE_FORM = "visits/createOrUpdateForm";

    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @ModelAttribute("pet")
    public Pet findPet(@PathVariable("petId") Long petId) {
        return petService.findById(petId);
    }

    @GetMapping("/new")
    public String initCreateForm(Pet pet, Model model) {
        Visit visit = new Visit();
        pet.addVisit(visit);
        model.addAttribute("visit", visit);
        return VIEWS_VISITS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreateForm(Pet pet, Visit visit, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("visit", visit);
            return VIEWS_VISITS_CREATE_OR_UPDATE_FORM;
        } else {
            pet.addVisit(visit);
            visitService.save(visit);
            return "redirect:/owners/" + pet.getOwner().getId();
        }
    }
}
