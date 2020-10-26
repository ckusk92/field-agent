package learn.field_agent.data;

import learn.field_agent.models.Alias;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AliasJdbcTemplateRepositoryTest {

    @Autowired
    AliasJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() { knownGoodState.set(); }

    @Test
    void shouldFindAll() {
        List<Alias> all = repository.findAll();
        assertNotNull(all);
        assertEquals(3, all.size());
    }

    @Test
    void shouldFindOneForAgentOne() {
        List<Alias> agentOnesAliases = repository.findAllForAgent(1);
        assertNotNull(agentOnesAliases);
        assertEquals(1, agentOnesAliases.size());
    }

    @Test
    void shouldFindById() {
        Alias expected = new Alias(2, "Ray Mysterio", "Infamous masked wrestler", 2);
        Alias actual = repository.findById(2);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindMissing() {
        assertNull(repository.findById(2000));
    }

    @Test
    void shouldAdd() {

        Alias alias = new Alias();
        alias.setName("Michael Scar");
        alias.setPersona("Deadliest paper company manager in history");
        alias.setAgentId(4);

        Alias actual = repository.add(alias);
        assertNotNull(actual);
        assertEquals(alias, actual);
        assertEquals(4, actual.getAliasId());
    }

    @Test
    void shouldUpdateExisting() {
        Alias alias = new Alias(1, "Michael Scar", "Deadlist paper company manager in history", 3);
        assertTrue(repository.update(alias));
        assertEquals("Michael Scar", repository.findById(1).getName());
    }

    @Test
    void shouldNotUpdateMissing() {
        assertFalse(repository.update(new Alias(1000, "Michael Scar", "Deadlist paper company manager in history", 3)));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(3));
    }

    @Test
    void shouldNotDelete() {
        assertFalse(repository.deleteById(200000));
    }
}
