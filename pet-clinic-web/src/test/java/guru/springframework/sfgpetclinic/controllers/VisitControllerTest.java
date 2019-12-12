package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class VisitControllerTest {

    private static final Long OWNER_ID = 1L;
    private static final Long PET_ID = 2L;
    private static final String VISIT_DESCRIPTION = "dummy description";
    private static final LocalDate VISIT_DATE = LocalDate.of(1111, 11, 11);

    @Mock
    private VisitService visitService;

    @Mock
    private PetService petService;

    @InjectMocks
    private VisitController visitController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Owner owner = new Owner();
        owner.setId(OWNER_ID);
        Pet pet = new Pet();
        pet.setId(PET_ID);
        pet.setOwner(owner);

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();

        Mockito.when(petService.findById(ArgumentMatchers.anyLong())).thenReturn(pet);
        Mockito.when(visitService.save(ArgumentMatchers.any())).thenReturn(new Visit());
    }

    @Test
    void findPet() throws Exception {
        mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/visits/new", OWNER_ID, PET_ID))
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attribute("pet", hasProperty("id", is(PET_ID))));
    }

    @Test
    void initCreateForm() throws Exception {
        mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/visits/new", OWNER_ID, PET_ID))
                .andExpect(status().isOk())
                .andExpect(view().name("visits/createOrUpdateForm"))
                .andExpect(model().attributeExists("visit"));
    }

    @Test
    void processCreateForm() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/visits/new", OWNER_ID, PET_ID)
                .param("description", VISIT_DESCRIPTION)
                .param("date", VISIT_DATE.toString())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + OWNER_ID))
                .andExpect(model().attributeExists("visit"));

        ArgumentCaptor<Visit> captor = ArgumentCaptor.forClass(Visit.class);
        Mockito.verify(visitService, Mockito.times(1)).save(captor.capture());
        Visit capturedVisit = captor.getValue();
        assertEquals(PET_ID, capturedVisit.getPet().getId());
    }

    @Test
    void processCreateFormFail() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/visits/new", OWNER_ID, PET_ID)
                .param("description", VISIT_DESCRIPTION)
                .param("date", "invalid date")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("visits/createOrUpdateForm"))
                .andExpect(model().attributeHasErrors("visit"))
                .andExpect(model().attributeHasFieldErrors("visit", "date"))
                .andExpect(model().attributeExists("visit"))
                .andExpect(model().attribute("visit", hasProperty("description", is(VISIT_DESCRIPTION))));
        Mockito.verifyZeroInteractions(visitService);
    }
}