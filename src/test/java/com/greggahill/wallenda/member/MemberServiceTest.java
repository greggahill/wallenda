package com.greggahill.wallenda.member;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static junit.framework.TestCase.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {
    public static final String MEMBERS_KEY = "members";
    public static List<String> NEW_MEMBERS = Arrays.asList("willie.mcgee", "ozzie.smith", "keith.hernandez", "george.hendrick",
            "lonnie.smith","ken.oberkfel", "tommie.herr", "jauquin.andujar");
    public static String NEW_ORGANIZATION = "Cardinals.1982";

    public static Map<String, List<String>> MEMBER_MAP = new HashMap<String, List<String>>() {{
        put("Mensa", Arrays.asList("gregg.hill", "john.smith", "isaac.newton", "albert.einstein"));
        put("Citizens", Arrays.asList("ronald.reagen", "olivia.de_haveland","jane.wyman"));
        put("Walmart", Arrays.asList("humphrey.bogart", "john.wayne", "clark.gable", "katherine.hepburn"));
        put("Kraft", Arrays.asList("ronald.reagen", "george.hw.bush", "bill.clinton", "george.w.bush","barak.obama","donald.trump"));

    }};

    private RedisConnection connection = null;

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Autowired
    private MemberService memberService;

    @Before
    public void setup() {
        connection = jedisConnectionFactory.getConnection();

        // remove organizations and their members in case some were added to the database by another source
        MEMBER_MAP.forEach((k,v) -> connection.del(k.getBytes()));
        connection.del((NEW_ORGANIZATION+"."+MEMBERS_KEY).getBytes());

        // add members to organizations
        MEMBER_MAP.forEach((k,v) -> {
            for (String s : v) {connection.lPush((k+"."+MEMBERS_KEY).getBytes(), s.getBytes());}
        });
    }

    @After
    public void teardown() {
        MEMBER_MAP.forEach((k,v) -> {connection.del((k+"."+MEMBERS_KEY).getBytes());});
        connection.del((NEW_ORGANIZATION+"."+MEMBERS_KEY).getBytes());
    }

    @Test
    public void getAll() throws Exception {
        List<String> expected = MEMBER_MAP.get("Mensa");
        Collections.sort(expected);

        List<String> list = memberService.getAll("Mensa");
        Collections.sort(list);
        assertTrue(list.equals(expected));
    }

    @Test
    public void insert() throws Exception {
        for (String s : NEW_MEMBERS) {
            memberService.insert(NEW_ORGANIZATION, s);
        }

        List<String> list = new ArrayList();
        List<byte[]> lbytes = connection.lRange((NEW_ORGANIZATION+"."+MEMBERS_KEY).getBytes(), 0, -1);
        for (byte[] b : lbytes ) {
            list.add(new String(b));
        }
        Collections.sort(NEW_MEMBERS);
        Collections.sort(list);

        assertTrue(list.equals(NEW_MEMBERS));
    }

    @Test
    public void delete() throws Exception {
        for (String member : NEW_MEMBERS) {
            connection.lPush((NEW_ORGANIZATION+"."+MEMBERS_KEY).getBytes(), member.getBytes());
        }

        for (String member : NEW_MEMBERS) {
            memberService.delete(NEW_ORGANIZATION,member);
        }

        List<byte[]> list = connection.lRange((NEW_ORGANIZATION+"."+MEMBERS_KEY).getBytes(), 0, -1);
        assertTrue(list.isEmpty());
    }

}
