package acktsap.demowebmvc.handlermethod.returns;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
public class FileDownloadControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testFileDownload() throws Exception {
        mockMvc.perform(get("/file/test.jpg"))
                .andExpect(status().isOk());
    }

}
