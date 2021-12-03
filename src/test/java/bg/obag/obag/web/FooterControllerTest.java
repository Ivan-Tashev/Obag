package bg.obag.obag.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class FooterControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void phylosophy() throws Exception {
        mockMvc.perform(get("/phylosophy"))
                .andExpect(status().isOk())
                .andExpect(view().name("phylosophy"));
    }

    @Test
    public void delivery() throws Exception {
        mockMvc.perform(get("/delivery"))
                .andExpect(status().isOk())
                .andExpect(view().name("delivery"));
    }

    @Test
    public void contact() throws Exception {
        mockMvc.perform(get("/contact"))
                .andExpect(status().isOk())
                .andExpect(view().name("contact"));
    }

    @Test
    public void faq() throws Exception {
        mockMvc.perform(get("/faq"))
                .andExpect(status().isOk())
                .andExpect(view().name("faq"));
    }

    @Test
    public void loyalty() throws Exception {
        mockMvc.perform(get("/loyalty"))
                .andExpect(status().isOk())
                .andExpect(view().name("loyalty"));
    }

    @Test
    public void viber() throws Exception {
        mockMvc.perform(get("/viber"))
                .andExpect(status().isOk())
                .andExpect(view().name("viber"));
    }

    @Test
    public void conditions() throws Exception {
        mockMvc.perform(get("/conditions"))
                .andExpect(status().isOk())
                .andExpect(view().name("conditions"));
    }

    @Test
    public void claims() throws Exception {
        mockMvc.perform(get("/claims"))
                .andExpect(status().isOk())
                .andExpect(view().name("claims"));
    }

}