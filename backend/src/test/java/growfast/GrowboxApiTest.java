package growfast;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GrowboxApiTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GrowboxRepository repository;


    private final String ENDPOINT = "/growboxes/";


    private void fillRepositoryWith4Items()
    {
        repository.save(new Growbox("Box A"));
        repository.save(new Growbox("Box B"));
        repository.save(new Growbox("Box C"));
        repository.save(new Growbox("Box D"));
    }

    @After
    public void tearDown()
    {
        repository.deleteAll();
    }

    @Test
    public void getAllFromEmptyRepo() throws Exception
    {
        this.mockMvc
                .perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$._embedded.growboxList").doesNotExist());
    }

    @Test
    public void getAll() throws Exception
    {
        fillRepositoryWith4Items();

        this.mockMvc
                .perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$._embedded.growboxList", hasSize(4)));
    }

    @Test
    public void getOneNonExistingItem() throws Exception
    {
        this.mockMvc
                .perform(get(ENDPOINT + "/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getOne() throws Exception
    {
        String id = repository.save(new Growbox("Box Z")).getId();

        this.mockMvc
                .perform(get(ENDPOINT + "/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Box Z"))
                .andExpect(jsonPath("$.sensors").isEmpty());
    }

    @Test
    public void postOne() throws Exception
    {
        String box = "{\"name\": \"Box Z\", \"sensors\": []}";

        this.mockMvc
                .perform(post(ENDPOINT)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(box))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Box Z"))
                .andExpect(jsonPath("$.sensors").isArray())
                .andExpect(jsonPath("$.sensors").isEmpty());
    }

    @Test
    public void postOneWithNoSensors() throws Exception
    {
        String box = "{\"name\": \"Box Z\"}";

        this.mockMvc
                .perform(post(ENDPOINT)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(box))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Box Z"))
                .andExpect(jsonPath("$.sensors").isArray())
                .andExpect(jsonPath("$.sensors").isEmpty());
    }

    @Test
    public void deleteOne() throws Exception
    {
        String id = repository.save(new Growbox("Box Z")).getId();

        fillRepositoryWith4Items();

        this.mockMvc
                .perform(delete(ENDPOINT + "/" + id))
                .andDo(print())
                .andExpect(status().isNoContent());

        this.mockMvc
                .perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$._embedded.growboxList", hasSize(4)));
    }
}
