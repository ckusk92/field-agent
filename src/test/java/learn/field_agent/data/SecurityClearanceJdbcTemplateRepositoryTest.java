package learn.field_agent.data;

import learn.field_agent.models.SecurityClearance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

    import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SecurityClearanceJdbcTemplateRepositoryTest {

    @Autowired
    SecurityClearanceJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<SecurityClearance> all = repository.findAll();
        assertNotNull(all);
        assertEquals(2, all.size());
    }

    @Test
    void shouldFindById() {
        SecurityClearance secret = new SecurityClearance(1, "Secret");
        SecurityClearance topSecret = new SecurityClearance(2, "Top Secret");

        SecurityClearance actual = repository.findById(1);
        assertEquals(secret, actual);

        actual = repository.findById(2);
        assertEquals(topSecret, actual);

        actual = repository.findById(5);
        assertEquals(null, actual);
    }

    @Test
    void shouldNotFindMissingId() {
        SecurityClearance aintReal = repository.findById(10000);
        assertNull(aintReal);
    }

    @Test
    void shouldAdd() {
        SecurityClearance securityClearance = new SecurityClearance();
        securityClearance.setName("Super duper top secret");
        SecurityClearance actual = repository.add(securityClearance);
        assertNotNull(actual);
        assertEquals(securityClearance, actual);
        assertEquals(3, actual.getSecurityClearanceId());
    }

    @Test
    void shouldUpdateExisting() {
        SecurityClearance securityClearance = new SecurityClearance();
        securityClearance.setSecurityClearanceId(2);
        securityClearance.setName("Meh Clearance");
        assertTrue(repository.update(securityClearance));
        assertEquals("Meh Clearance", repository.findById(2).getName());
    }

    @Test
    void shouldNotUpdateMissing() {
        SecurityClearance securityClearance = new SecurityClearance();
        securityClearance.setSecurityClearanceId(2000);
        securityClearance.setName("Fake");
        assertFalse(repository.update(securityClearance));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(1));
    }

    @Test
    void shouldNotDeleteMissing() {
        assertFalse(repository.deleteById(10000));
    }
}