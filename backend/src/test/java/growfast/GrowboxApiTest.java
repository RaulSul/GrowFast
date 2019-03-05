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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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


    private void fillRepositoryWith4Growboxes()
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
    public void getAllGrowboxesFromEmptyRepoShouldReturnOk() throws Exception
    {
        this.mockMvc
                .perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAllGrowboxesFromEmptyRepoShouldReturnJson() throws Exception
    {
        this.mockMvc
                .perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
    }

    @Test
    public void getAllGrowboxesFromEmptyRepoShouldReturnNoItems() throws Exception
    {
        this.mockMvc
                .perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.growboxList").doesNotExist());
    }

    @Test
    public void getAllGrowboxesShouldReturnOk() throws Exception
    {
        fillRepositoryWith4Growboxes();

        this.mockMvc
                .perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAllGrowboxesShouldReturnJson() throws Exception
    {
        fillRepositoryWith4Growboxes();

        this.mockMvc
                .perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
    }

    @Test
    public void getAllGrowboxesShouldReturnInstances() throws Exception
    {
        fillRepositoryWith4Growboxes();

        this.mockMvc
                .perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.growboxList", hasSize(4)));
    }
}
