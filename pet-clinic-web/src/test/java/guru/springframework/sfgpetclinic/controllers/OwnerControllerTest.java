package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController ownerController;

    MockMvc mockMvc;

    Owner owner;
    private final static Long OWNER_ID = 1L;
    private final static String OWNER_ADDRESS = "Dummy address";
    private final static String OWNER_CITY = "Dummy city";
    private final static String OWNER_TELEPHONE = "11111122112";
    private final static String OWNER_FIRST_NAME = "John";
    private final static String OWNER_LAST_NAME = "Smith";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
        owner = new Owner();
        owner.setId(OWNER_ID);
        owner.setLastName(OWNER_LAST_NAME);
        owner.setFirstName(OWNER_FIRST_NAME);
        owner.setTelephone(OWNER_TELEPHONE);
        owner.setCity(OWNER_CITY);
        owner.setAddress(OWNER_ADDRESS);
    }

    @Test
    void findOwners() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/find"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/findOwners"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"));
        Mockito.verifyZeroInteractions(ownerService);
    }

    @Test
    void processFindFormReturnMany() throws Exception {
        Mockito.when(ownerService.findAllByLastNameLike(ArgumentMatchers.anyString())).thenReturn(Lists.newArrayList(owner, new Owner()));

        mockMvc.perform(MockMvcRequestBuilders.get("/owners").param("lastName", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/ownersList"))
                .andExpect(MockMvcResultMatchers.model().attribute("owners", Matchers.hasSize(2)));
    }

    @Test
    void processFindFormReturnOne() throws Exception {
        Mockito.when(ownerService.findAllByLastNameLike(ArgumentMatchers.anyString())).thenReturn(Collections.singletonList(owner));

        mockMvc.perform(MockMvcRequestBuilders.get("/owners").param("lastName", ""))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/" + OWNER_ID));
    }

    @Test
    void processFindFormReturnNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners")
                .param("lastName", "Not found last name"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/findOwners"))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("owner", "lastName"))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrorCode("owner", "lastName", "notFound"));
    }

    @Test
    void processFindFormReturnNone() throws Exception {
        Mockito.when(ownerService.findAllByLastNameLike(ArgumentMatchers.anyString())).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/owners").param("lastName", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/findOwners"));
    }

    @Test
    void showOwner() throws Exception {
        Mockito.when(ownerService.findById(ArgumentMatchers.anyLong())).thenReturn(owner);

        mockMvc.perform(MockMvcRequestBuilders.get("/owners/{ownerId}", OWNER_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/ownerDetails"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
                .andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("firstName", Matchers.is(OWNER_FIRST_NAME))))
                .andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("lastName", Matchers.is(OWNER_LAST_NAME))))
                .andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("city", Matchers.is(OWNER_CITY))))
                .andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("address", Matchers.is(OWNER_ADDRESS))))
                .andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("telephone", Matchers.is(OWNER_TELEPHONE))));
    }

    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/createOrUpdateForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"));
    }

    @Test
    void initUpdateForm() throws Exception {
        Mockito.when(ownerService.findById(ArgumentMatchers.anyLong())).thenReturn(owner);

        mockMvc.perform(MockMvcRequestBuilders.get("/owners/{ownerId}/edit", OWNER_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/createOrUpdateForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
                .andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("firstName", Matchers.is(OWNER_FIRST_NAME))))
                .andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("lastName", Matchers.is(OWNER_LAST_NAME))))
                .andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("city", Matchers.is(OWNER_CITY))))
                .andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("address", Matchers.is(OWNER_ADDRESS))))
                .andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("telephone", Matchers.is(OWNER_TELEPHONE))));
    }

    @Test
    void processCreationForm() throws Exception {
        Mockito.when(ownerService.save(ArgumentMatchers.any())).thenReturn(owner);

        mockMvc.perform(MockMvcRequestBuilders.post("/owners/new")
                .param("firstName", OWNER_FIRST_NAME)
                .param("lastName", OWNER_LAST_NAME)
                .param("address", OWNER_ADDRESS)
                .param("city", OWNER_CITY)
                .param("telephone", OWNER_TELEPHONE)
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/" + OWNER_ID));

        Mockito.verify(ownerService, Mockito.times(1)).save(ArgumentMatchers.any());
    }

    @Test
    void processUpdateForm() throws Exception {
        Mockito.when(ownerService.save(ArgumentMatchers.any())).thenReturn(owner);

        mockMvc.perform(MockMvcRequestBuilders.post("/owners/{ownerId}/edit", OWNER_ID)
                .param("firstName", OWNER_FIRST_NAME)
                .param("lastName", OWNER_LAST_NAME)
                .param("address", OWNER_ADDRESS)
                .param("city", OWNER_CITY)
                .param("telephone", OWNER_TELEPHONE)
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/" + OWNER_ID));

        Mockito.verify(ownerService, Mockito.times(1)).save(ArgumentMatchers.any());
    }
}