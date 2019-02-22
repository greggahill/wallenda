package com.greggahill.wallenda.organization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import static junit.framework.TestCase.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrganizationServiceTest {
    public static final String key = "organizations";
    public static List<String> expected = Arrays.asList("Citizens", "Walmart", "Kraft");
    private RedisConnection connection = null;

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Autowired
    private OrganizationService organizationService;

    @Before
    public void setup() {
        connection = jedisConnectionFactory.getConnection();
        connection.del(key.getBytes());
        Collections.sort(expected);
    }

    @After
    public void teardown() {
        connection.del(key.getBytes());
    }

    @Test
    public void getAll() throws Exception {
        for (String s : expected) {
            connection.lPush(key.getBytes(), s.getBytes());
        }

        List<String> list = organizationService.getAll();

        Collections.sort(list);
        assertTrue(list.equals(expected));
    }

    @Test
    public void insert() throws Exception {
        for (String s : expected) {
            organizationService.insert(s);
        }

        List<String> list = new ArrayList();
        List<byte[]> lbytes = connection.lRange(key.getBytes(), 0, -1);
        for (byte[] b : lbytes ) {
            list.add(new String(b));
        }

        Collections.sort(list);
        assertTrue(list.equals(expected));
    }

    @Test
    public void delete() throws Exception {
        for (String s : expected) {
            connection.lPush(key.getBytes(), s.getBytes());
        }

        for (String s : expected) {
            organizationService.delete(s);
        }

        List<byte[]> list = connection.lRange(key.getBytes(), 0, -1);
        assertTrue(list.isEmpty());
    }

/*

mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
.andExpect(status().isOk());
.andExpect(status().isOk())
.andExpect(content().string(equalTo("Greetings from Gregg's first Spring Boot app running in PCF!\nbar")));
*/

}
