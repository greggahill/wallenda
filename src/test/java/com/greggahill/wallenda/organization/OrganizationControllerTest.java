package com.greggahill.wallenda.organization;

import java.util.List;
import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrganizationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrganizationService organizationService;

    public List<String> mockOrganizations = Arrays.asList("bank", "merchant", "manufacturer");

    @Test
    public void getOrganizations() throws Exception {
      String expected = "[\"bank\",\"merchant\",\"manufacturer\"]";

      Mockito.when(organizationService.getAll()).thenReturn(mockOrganizations);
      RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/organizations").accept(MediaType.APPLICATION_JSON);
      MvcResult result = mvc.perform(requestBuilder).andReturn();

      assertThat(result.getResponse().getContentAsString(), equalTo(expected));
    }

/*

mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
.andExpect(status().isOk());
.andExpect(status().isOk())
.andExpect(content().string(equalTo("Greetings from Gregg's first Spring Boot app running in PCF!\nbar")));
*/

}
