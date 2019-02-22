package com.greggahill.wallenda.member;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.greggahill.wallenda.organization.OrganizationService;
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

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MemberService memberService;

    public List<String> mockMembers = Arrays.asList("gregg.hill", "john.smith", "isaac.newton", "albert.einstein");

    @Test
    public void getMembers() throws Exception {
        String expected = "[\"gregg.hill\",\"john.smith\",\"isaac.newton\",\"albert.einstein\"]";
        Mockito.when(memberService.getAll(Mockito.anyString())).thenReturn(mockMembers);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(Mockito.anyString()+"/mensa/members").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();

        assertThat(result.getResponse().getContentAsString(), equalTo(expected));

    }

    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
/*
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Greetings from Gregg's first Spring Boot app running in PCF!\nbar")));
*/
    }
}
