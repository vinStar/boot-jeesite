package com.thinkgem.jeesite.other.web;

import com.thinkgem.jeesite.modules.other.helloController.HelloController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by vin on 04/05/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class testControllerTest {

    private MockMvc mvc;

    @Autowired
    private HelloController helloController;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(helloController).build();
    }

    @Test
    public void testHello() throws Exception {
        mvc.perform(get("/v0.1/hello"))
                .andExpect(status().isOk());
                //.andExpect(content().json("sdf"));
    }
    @Test
    public void testTest() throws Exception {

        mvc.perform(get("/v0.1/test"))
                .andExpect(status().isOk());

                //.andExpect(content().json("sdf"));
    }

}
