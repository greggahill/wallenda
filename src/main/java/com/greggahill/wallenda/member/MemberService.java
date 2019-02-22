package com.greggahill.wallenda.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Component;

@Component
public class MemberService {
  public static final String key = "members";

  @Autowired
  RedisConnection connection;

  public List<String> getAll(String organization) {
    List<String> list = new ArrayList<>();
    List<byte[]> lbytes = connection.lRange((organization+"."+key).getBytes(), 0, -1);
    for (byte[] b : lbytes ) {
      list.add(new String(b));
    }
    return list;
  }

  public void insert(String organization, String member) {
    connection.lPush((organization+"."+key).getBytes(), member.getBytes());
    return;
  }

  public void delete(String organization, String member) {
    connection.lRem((organization+"."+key).getBytes(), 0, member.getBytes());
    return;
  }
}
