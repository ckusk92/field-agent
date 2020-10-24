package learn.field_agent.domain;

import learn.field_agent.data.SecurityClearanceRepository;
import learn.field_agent.models.SecurityClearance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SecurityClearanceServiceTest {

    @Autowired
    SecurityClearanceService service;

    @MockBean
    SecurityClearanceRepository repository;



    @Test
    void shouldGetAll() {

        List<SecurityClearance> all = List.of(
                new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret")
        );

        when(repository.findAll()).thenReturn(all);

        assertEquals(2, service.findAll().size());
    }

    @Test
    void shouldFindById() {
        SecurityClearance secret = new SecurityClearance(1, "Secret");
        when(repository.findById(1)).thenReturn(secret);
        SecurityClearance actual = service.findById(1);
        assertEquals(secret, actual);
    }

    @Test
    void shouldNotFindNotExisting() {
        when(repository.findById(1000)).thenReturn(null);
        assertNull(service.findById(1000));
    }

    @Test
    void shouldAdd() {
        SecurityClearance newSecurityClearance = new SecurityClearance(0, "Ultimate top secret");
        SecurityClearance mockOut = new SecurityClearance(3, "Ultimate top secret");
        when(repository.add(newSecurityClearance)).thenReturn(mockOut);

        Result<SecurityClearance> actual = service.add(newSecurityClearance);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());
    }

    @Test
    void shouldNotAddBlankName() {
        SecurityClearance badSecurityClearance = new SecurityClearance(0, "");
        //when(repository.add(newSecurityClearance)).thenReturn(mockOut);

        Result<SecurityClearance> actual = service.add(badSecurityClearance);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotAddDuplicateName() {
        SecurityClearance badSecurityClearance = new SecurityClearance(0, "Secret");

        List<SecurityClearance> all = List.of(
                new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret")
        );

        when(repository.findAll()).thenReturn(all);

        Result<SecurityClearance> actual = service.add(badSecurityClearance);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldUpdate() {
        SecurityClearance updatedClearance = new SecurityClearance(1, "Ultimate top secret");

        when(repository.update(updatedClearance)).thenReturn(true);

        Result<SecurityClearance> actual = service.update(updatedClearance);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdateToExistingName () {
        SecurityClearance badSecurityClearance = new SecurityClearance(1, "Top Secret");

        List<SecurityClearance> all = List.of(
                new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret")
        );

        when(repository.findAll()).thenReturn(all);
        when(repository.update(badSecurityClearance)).thenReturn(true);

        Result<SecurityClearance> actual = service.update(badSecurityClearance);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotUpdateEmptyName () {
        SecurityClearance badSecurityClearance = new SecurityClearance(1, "");
        Result<SecurityClearance> actual = service.update(badSecurityClearance);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotUpdateMissing () {
        SecurityClearance badSecurityClearance = new SecurityClearance(1000, "Even More Top Secret");

        List<SecurityClearance> all = List.of(
                new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret")
        );

        when(repository.findAll()).thenReturn(all);
        when(repository.update(badSecurityClearance)).thenReturn(false);

        Result<SecurityClearance> actual = service.update(badSecurityClearance);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }

    @Test
    void shouldDelete() {
        when(repository.deleteById(2)).thenReturn(true);
        Result<SecurityClearance> result = service.deleteById(2);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotDeleteUsedSecurityClearance() {
        when(repository.deleteById(1)).thenReturn(true);
        Result<SecurityClearance> result = service.deleteById(1);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNoDeletetNonExisting() {
        when(repository.deleteById(1000)).thenReturn(false);
        Result<SecurityClearance> result = service.deleteById(1000);
        assertEquals(ResultType.NOT_FOUND, result.getType());
    }
}
