package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
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

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
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
        ArrayList<Owner> owners = new ArrayList<>();
        Owner owner1 = new Owner();
        owner1.setId(1L);
        Owner owner2 = new Owner();
        owner2.setId(2L);
        owners.add(owner1);
        owners.add(owner2);
        Mockito.when(ownerService.findAllByLastNameLike(ArgumentMatchers.anyString())).thenReturn(owners);

        mockMvc.perform(MockMvcRequestBuilders.get("/owners").param("lastName", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/ownersList"))
                .andExpect(MockMvcResultMatchers.model().attribute("owners", Matchers.hasSize(2)));
    }

    @Test
    void processFindFormReturnOne() throws Exception {
        Long ownerId = 1L;
        Owner owner = new Owner();
        owner.setId(ownerId);

        Mockito.when(ownerService.findAllByLastNameLike(ArgumentMatchers.anyString())).thenReturn(Collections.singletonList(owner));

        mockMvc.perform(MockMvcRequestBuilders.get("/owners").param("lastName", ""))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/" + ownerId));
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
        Long ownerId = 1L;
        Owner owner = new Owner();
        owner.setId(ownerId);

        Mockito.when(ownerService.findById(ArgumentMatchers.anyLong())).thenReturn(owner);

        mockMvc.perform(MockMvcRequestBuilders.get("/owners/{ownerId}",ownerId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/ownerDetails"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"));
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
        Long ownerId = 1L;
        Owner owner = new Owner();
        owner.setId(ownerId);
        Mockito.when(ownerService.findById(ArgumentMatchers.anyLong())).thenReturn(owner);

        mockMvc.perform(MockMvcRequestBuilders.get("/owners/{ownerId}/edit", ownerId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/createOrUpdateForm"))
                .andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("id", Matchers.is(ownerId))));
    }

    @Test
    void processCreationForm() throws Exception {
        Long ownerId = 1L;
        Owner owner = new Owner();
        owner.setId(ownerId);
        Mockito.when(ownerService.save(ArgumentMatchers.any())).thenReturn(owner);

        mockMvc.perform(MockMvcRequestBuilders.post("/owners/new"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/" + ownerId));

        Mockito.verify(ownerService, Mockito.times(1)).save(ArgumentMatchers.any());
    }

    @Test
    void processUpdateForm() throws Exception {
        Long ownerId = 1L;
        Owner owner = new Owner();
        owner.setId(ownerId);
        Mockito.when(ownerService.save(ArgumentMatchers.any())).thenReturn(owner);

        mockMvc.perform(MockMvcRequestBuilders.post("/owners/{ownerId}/edit", ownerId))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/" + ownerId));

        Mockito.verify(ownerService, Mockito.times(1)).save(ArgumentMatchers.any());
    }
}