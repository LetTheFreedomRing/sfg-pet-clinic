package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.internal.util.collections.Sets;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PetControllerTest {

    private static final Long OWNER_ID = 1L;
    private static final Long PET_ID = 2L;
    private static final Long PET_TYPE_ID = 3L;
    private static final String PET_NAME = "pet name";
    private static final String PET_TYPE = "dog";
    private static final LocalDate PET_BIRTH_DATE = LocalDate.of(2015, 11, 11);

    @Mock
    private PetService petService;

    @Mock
    private OwnerService ownerService;

    @Mock
    private PetTypeService petTypeService;

    @InjectMocks
    private PetController petController;

    private MockMvc mockMvc;

    private Pet pet;
    private Owner owner;
    private PetType petType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();

        owner = new Owner();
        owner.setId(OWNER_ID);

        pet = new Pet();
        pet.setId(PET_ID);
        pet.setName(PET_NAME);
        pet.setBirthDate(PET_BIRTH_DATE);

        petType = new PetType();
        petType.setId(PET_TYPE_ID);
        petType.setName(PET_TYPE);

        pet.setPetType(petType);

        Mockito.when(ownerService.findById(ArgumentMatchers.anyLong())).thenReturn(owner);
        Mockito.when(petTypeService.findAll()).thenReturn(Sets.newSet(petType));
        Mockito.when(petService.findById(ArgumentMatchers.anyLong())).thenReturn(pet);
        Mockito.when(petService.save(ArgumentMatchers.any())).thenReturn(pet);
    }

    @Test
    void populatePetTypes() throws Exception {
        mockMvc.perform(get("/owners/{ownerId}/pets/new", OWNER_ID))
                .andExpect(model().attributeExists("petTypes"))
                .andExpect(model().attribute("petTypes", hasSize(1)));
    }

    @Test
    void findOwner() throws Exception {
        mockMvc.perform(get("/owners/{ownerId}/pets/new", OWNER_ID))
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attribute("owner", hasProperty("id", is(OWNER_ID))));
    }

    @Test
    void initCreateForm() throws Exception {
        mockMvc.perform(get("/owners/{ownerId}/pets/new", OWNER_ID))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdateForm"))
                .andExpect(model().attributeExists("pet"));
        Mockito.verifyZeroInteractions(petService);
    }

    @Test
    void initUpdateForm() throws Exception {
        mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/edit", OWNER_ID, PET_ID))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdateForm"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attribute("pet", hasProperty("name", is(PET_NAME))))
                .andExpect(model().attribute("pet", hasProperty("birthDate", is(PET_BIRTH_DATE))));
    }

    @Test
    void processCreateForm() throws Exception {
        ArgumentCaptor<Pet> captor = ArgumentCaptor.forClass(Pet.class);
        mockMvc.perform(post("/owners/{ownerId}/pets/new", OWNER_ID)
                .param("name", PET_NAME)
                .param("petType", PET_TYPE)
                .param("birthDate", PET_BIRTH_DATE.toString())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + OWNER_ID));
        Mockito.verify(petService, Mockito.times(1)).save(captor.capture());
        Pet savedPet = captor.getValue();
        assertEquals(savedPet.getOwner().getId(), OWNER_ID);
    }

    @Test
    void processCreateFormFail() throws Exception {
        Pet pet1 = new Pet();
        pet1.setName(PET_NAME);
        owner.addPet(pet1);
        mockMvc.perform(post("/owners/{ownerId}/pets/new", OWNER_ID)
                .param("name", PET_NAME)
                .param("birthDate", PET_BIRTH_DATE.toString())
                .param("petType", PET_TYPE)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdateForm"))
                .andExpect(model().attribute("pet", hasProperty("name", is(PET_NAME))))
                .andExpect(model().attribute("pet", hasProperty("birthDate", is(PET_BIRTH_DATE))))
                .andExpect(model().attributeHasFieldErrors("pet", "name"))
                .andExpect(model().attributeHasFieldErrorCode("pet", "name", "duplicate"));
        Mockito.verify(petService, Mockito.times(0)).save(ArgumentMatchers.any());
    }

    @Test
    void processUpdateForm() throws Exception {
        ArgumentCaptor<Pet> captor = ArgumentCaptor.forClass(Pet.class);
        mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", OWNER_ID, PET_ID)
                .param("name", PET_NAME)
                .param("birthDate", String.valueOf(PET_BIRTH_DATE))
                .param("petType", PET_TYPE)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + OWNER_ID));
        Mockito.verify(petService, Mockito.times(1)).save(captor.capture());
        Pet savedPet = captor.getValue();
        assertEquals(savedPet.getOwner().getId(), OWNER_ID);
    }

    @Test
    void processUpdateFormFails() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", OWNER_ID, PET_ID)
                .param("name", PET_NAME)
                .param("birthDate", "invalid date")
        )
                .andExpect(model().attribute("pet", hasProperty("name", is(PET_NAME))))
                .andExpect(model().attributeHasFieldErrors("pet", "birthDate"))
                .andExpect(model().attributeHasFieldErrorCode("pet", "birthDate", "typeMismatch"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdateForm"));
        Mockito.verify(petService, Mockito.times(0)).save(ArgumentMatchers.any());
    }
}