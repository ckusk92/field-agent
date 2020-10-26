package learn.field_agent.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.field_agent.data.AliasRepository;
import learn.field_agent.models.Alias;
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
public class AliasControllerTest {

    @MockBean
    AliasRepository repository;

    @Autowired
    MockMvc mvc;

    List<Alias> all = List.of(
            new Alias(1, "Captain Crunch", "Searches the seas for delicious cereal", 1),
            new Alias(2, "Ray Mysterio", "Infamouse masked wrester", 2),
            new Alias(3, "James Bond", "No explanation needed, spy extroardinaire", 3)
    );

    List<Alias> allForOne = List.of(new Alias(1, "Captain Crunch", "Searches the seas for delicious cereal", 1));

    @Test
    void shouldGetAll() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(all);

        when(repository.findAll()).thenReturn(all);

        mvc.perform(get("/api/alias"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldGetAllForAgentOne() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(allForOne);

        when(repository.findAllForAgent(1)).thenReturn(allForOne);

        mvc.perform(get("/api/alias/agent/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldGetById() throws Exception {
        Alias expected = new Alias(1, "Captain Crunch", "Searches the seas for delicious cereal", 1);

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(expected);

        when(repository.findById(1)).thenReturn(expected);

        mvc.perform(get("/api/alias/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldNotFindMissingAliasId() throws Exception {

        var request = get("/api/alias/1000")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldNotFindMissingAgentId() throws Exception {

        when(repository.findAllForAgent(1000)).thenReturn(null);

        var request = get("/api/alias/agent/1000")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldAdd() throws Exception {
        Alias aliasIn = new Alias(0, "Charlie Murphy", "Phenominal Story Teller", 1);
        Alias expected = new Alias(1, "Charlie Murphy", "Phenominal Story Teller", 1);

        when(repository.add(any())).thenReturn(expected);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(aliasIn);
        String expectedJson = jsonMapper.writeValueAsString(expected);

        var request = post("/api/alias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldNotAddEmptyName() throws Exception {

        Alias aliasIn = new Alias(0, "", "", 1);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(aliasIn);

        var request = post("/api/alias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void addShouldReturn400WhenEmpty() throws Exception {

        var request = post("/api/alias")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }
}
