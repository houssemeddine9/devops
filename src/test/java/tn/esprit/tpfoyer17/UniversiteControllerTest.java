package tn.esprit.tpfoyer17;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer17.controllers.UniversiteController;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.services.interfaces.IUniversiteService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UniversiteController.class)
class UniversiteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUniversiteService universiteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRetrieveAllUniversities() throws Exception {
        List<Universite> universities = Arrays.asList(
                new Universite(1L, "Uni 1", "Address 1", null),
                new Universite(2L, "Uni 2", "Address 2", null)
        );

        when(universiteService.retrieveAllUniversities()).thenReturn(universities);

        mockMvc.perform(get("/api/univeristes/retrieveAllUniversities"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nomUniversite").value("Uni 1"))
                .andExpect(jsonPath("$[1].nomUniversite").value("Uni 2"));

        verify(universiteService, times(1)).retrieveAllUniversities();
    }

    @Test
    void testAddUniversity() throws Exception {
        Universite university = new Universite(1L, "Uni 1", "Address 1", null);
        when(universiteService.addUniversity(any(Universite.class))).thenReturn(university);

        mockMvc.perform(post("/api/univeristes/addUniversity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(university)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomUniversite").value("Uni 1"));

        verify(universiteService, times(1)).addUniversity(any(Universite.class));
    }

    @Test
    void testUpdateUniversity() throws Exception {
        Universite university = new Universite(1L, "Uni 1", "Address 1", null);
        when(universiteService.updateUniversity(any(Universite.class))).thenReturn(university);

        mockMvc.perform(put("/api/univeristes/updateUniversity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(university)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomUniversite").value("Uni 1"));

        verify(universiteService, times(1)).updateUniversity(any(Universite.class));
    }

    @Test
    void testRetrieveUniversity() throws Exception {
        Universite university = new Universite(1L, "Uni 1", "Address 1", null);
        when(universiteService.retrieveUniversity(1L)).thenReturn(university);

        mockMvc.perform(get("/api/univeristes/retrieveUniversity/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomUniversite").value("Uni 1"));

        verify(universiteService, times(1)).retrieveUniversity(1L);
    }

}
