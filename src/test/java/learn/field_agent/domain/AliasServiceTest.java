package learn.field_agent.domain;

import learn.field_agent.data.AliasRepository;
import learn.field_agent.models.Alias;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AliasServiceTest {

    @Autowired
    AliasService service;

    @MockBean
    AliasRepository repository;

    List<Alias> all = List.of(
            new Alias(1, "Captain Crunch", "Searches the seas for delicious cereal", 1),
            new Alias(2, "Ray Mysterio", "Infamouse masked wrester", 2),
            new Alias(3, "James Bond", "No explanation needed, spy extroardinaire", 3)
    );

    List<Alias> allForOne = List.of(new Alias(1, "Captain Crunch", "Searches the seas for delicious cereal", 1));

    @Test
    void shouldGetAll() {
        when(repository.findAll()).thenReturn(all);
        assertEquals(3, service.findAll().size());
    }

    @Test
    void shouldFindOneForAgentOne() {
        when(repository.findAllForAgent(1)).thenReturn(allForOne);
        assertEquals(1, service.findAllForAgent(1).size());
    }

    @Test
    void shouldFindById() {
        Alias alias = new Alias(1, "Captain Crunch", "Searches the seas for delicious cereal", 1);
        when(repository.findById(1)).thenReturn(alias);
        Alias actual = service.findById(1);
        assertEquals(alias, actual);
    }

    @Test
    void shouldNotFindNotExisting() {
        when(repository.findById(10000)).thenReturn(null);
        assertNull(service.findById(10000));
    }

    @Test
    void shouldAdd() {
        Alias newAlias = new Alias(0, "Charlie Murphy", "Phenominal Story Teller", 1);
        Alias mockOut = new Alias(4, "Charlie Murphy", "Phenominal Story Teller", 1);
        when(repository.add(newAlias)).thenReturn(mockOut);

        Result<Alias> actual = service.add(newAlias);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());
    }

    @Test
    void shouldNotAddBlankName() {
        Alias badAlias = new Alias(0, "", "", 1);
        Result<Alias> actual = service.add(badAlias);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotAddDuplicateNameWithNoPersona() {
        when(repository.findAll()).thenReturn(all);

        Alias alias = new Alias(1, "Captain Crunch", "", 1);

        Result<Alias> actual = service.add(alias);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldUpdate() {
        Alias alias = new Alias(2, "Charlie Murphy", "Phenominal Story Teller", 1);
        when(repository.update(alias)).thenReturn(true);

        Result<Alias> actual = service.update(alias);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdateToExistingName() {
        Alias badAlias = new Alias(2, "James Bond", "Phenominal Story Teller", 1);

        when(repository.findAll()).thenReturn(all);
        when(repository.update(badAlias)).thenReturn(true);

        Result<Alias> actual = service.update(badAlias);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotUpdateEmptyName() {
        Alias bad = new Alias(1,"", "", 1);
        Result<Alias> actual = service.update(bad);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotUpdateMissing() {
        Alias badAlias = new Alias(1000, "Generic Name", "Phenominal Story Teller", 1);
        when(repository.findAll()).thenReturn(all);
        Result<Alias> actual = service.update(badAlias);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }

    @Test
    void shouldDelete() {
        when(repository.deleteById(2)).thenReturn(true);
        assertTrue(repository.deleteById(2));
    }

    @Test
    void shouldNotDeleteMissing() {
        assertFalse(repository.deleteById(1000));
    }
}
