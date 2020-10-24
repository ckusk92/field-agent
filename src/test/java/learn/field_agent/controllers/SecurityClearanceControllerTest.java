package learn.field_agent.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import learn.field_agent.data.SecurityClearanceRepository;
import learn.field_agent.models.SecurityClearance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityClearanceControllerTest {

    @MockBean
    SecurityClearanceRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldGetAll() throws Exception {

        List<SecurityClearance> all = List.of(
                new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret"),
                new SecurityClearance(3, "Super Secret")
        );

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(all);

        when(repository.findAll()).thenReturn(all);

        mvc.perform(get("/api/securityclearance"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));

    }

    @Test
    void shouldGetById() throws Exception {

        SecurityClearance expected = new SecurityClearance(1, "Secret");

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(expected);

        when(repository.findById(1)).thenReturn(expected);

        mvc.perform(get("/api/securityclearance/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));

    }

    @Test
    void shouldNotFindMissingId() throws Exception {

        var request = get("/api/securityclearance/1000")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isNotFound());

    }

    @Test
    void shouldAdd() throws Exception {

        SecurityClearance in = new SecurityClearance(0, "Secret");
        SecurityClearance expected = new SecurityClearance(1, "Secret");

        when(repository.add(any())).thenReturn(expected);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(in);
        String expectedJson = jsonMapper.writeValueAsString(expected);

        var request = post("/api/securityclearance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldNotAddEmptyName() throws Exception {

        SecurityClearance in = new SecurityClearance(0, " ");

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(in);

        var request = post("/api/securityclearance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void addShouldReturn400WhenEmpty() throws Exception {

        var request = post("/api/securityclearance")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdate() throws Exception {

        SecurityClearance in = new SecurityClearance(1, "Secret");
        SecurityClearance expected = new SecurityClearance(1, "Really Really Secret");

        when(repository.update(any())).thenReturn(true);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(in);
        String expectedJson = jsonMapper.writeValueAsString(expected);

        var request = put("/api/securityclearance/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                //.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }
}
