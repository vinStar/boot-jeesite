package com.thinkgem.jeesite;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.activation.DataHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by vin on 2018/7/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {OssApp.class})
@WebAppConfiguration
public class QiNiuTest {


    private MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac
        ).alwaysExpect(status().isOk()).addFilters(
                new CharacterEncodingFilter()).build();
    }


    @Test
    public void testUpload() throws Exception {
        String uri = "/qiniu/upload";
        InputStream inputStream;

        //M1: local file
        File f = new File("/Users/liutong/Documents/rxMialLogo.png");
        inputStream = new FileInputStream(f);

        //M2: net resource
        DataHandler dataHandler = new DataHandler(new URL(
                "https://avatars1.githubusercontent.com/u/2784452?s=460&v=4"));

        inputStream = dataHandler.getInputStream();

        MockMultipartFile upload = new MockMultipartFile(
                "upload", f.getName(), "multipart/form-data", inputStream);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.fileUpload(
                uri).file(upload).accept(MediaType.APPLICATION_JSON)).andReturn();


        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();

        Assert.assertEquals(status, 200);
        Assert.assertNotNull(content);

        System.out.println(status);
        System.out.println(content);
    }


}
