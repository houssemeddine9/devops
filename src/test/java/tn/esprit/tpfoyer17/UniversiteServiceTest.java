package tn.esprit.tpfoyer17;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.repositories.FoyerRepository;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import tn.esprit.tpfoyer17.services.impementations.UniversiteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class UniversiteServiceTest {

    @InjectMocks
    private UniversiteService universiteService;

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private FoyerRepository foyerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllUniversities() {
        List<Universite> mockUniversities = new ArrayList<>();
        mockUniversities.add(new Universite(1L, "Uni 1", "Address 1", null));
        mockUniversities.add(new Universite(2L, "Uni 2", "Address 2", null));

        when(universiteRepository.findAll()).thenReturn(mockUniversities);

        List<Universite> result = universiteService.retrieveAllUniversities();
        assertEquals(2, result.size());
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    void testAddUniversity() {
        Universite university = new Universite(1L, "Uni 1", "Address 1", null);
        when(universiteRepository.save(university)).thenReturn(university);

        Universite result = universiteService.addUniversity(university);
        assertEquals("Uni 1", result.getNomUniversite());
        verify(universiteRepository, times(1)).save(university);
    }

    @Test
    void testUpdateUniversity() {
        Universite university = new Universite(1L, "Uni 1", "Address 1", null);
        when(universiteRepository.save(university)).thenReturn(university);

        Universite result = universiteService.updateUniversity(university);
        assertEquals("Uni 1", result.getNomUniversite());
        verify(universiteRepository, times(1)).save(university);
    }

    @Test
    void testRetrieveUniversity() {
        Universite university = new Universite(1L, "Uni 1", "Address 1", null);
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(university));

        Universite result = universiteService.retrieveUniversity(1L);
        assertEquals("Uni 1", result.getNomUniversite());
        verify(universiteRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveUniversityNotFound() {
        when(universiteRepository.findById(1L)).thenReturn(Optional.empty());

        Universite result = universiteService.retrieveUniversity(1L);
        assertNull(result);
        verify(universiteRepository, times(1)).findById(1L);
    }

    @Test
    void testDesaffecterFoyerAUniversite() {
        Universite university = new Universite(1L, "Uni 1", "Address 1", new Foyer());
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(university));
        when(universiteRepository.save(any(Universite.class))).thenReturn(university);

        Universite result = universiteService.desaffecterFoyerAUniversite(1L);
        assertNull(result.getFoyer());
        verify(universiteRepository, times(1)).save(university);
    }

    @Test
    void testAffecterFoyerAUniversite() {
        Foyer foyer = new Foyer();
        Universite university = new Universite(1L, "Uni 1", "Address 1", null);

        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));
        when(universiteRepository.findByNomUniversiteLike("Uni 1")).thenReturn(university);
        when(universiteRepository.save(any(Universite.class))).thenReturn(university);

        Universite result = universiteService.affecterFoyerAUniversite(1L, "Uni 1");
        assertEquals(foyer, result.getFoyer());
        verify(universiteRepository, times(1)).save(university);
    }
}
