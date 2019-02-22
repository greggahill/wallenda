package com.greggahill.wallenda.organization;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Component;

@Component
public class OrganizationService {
  public static final String key = "organizations";

  @Autowired
  RedisConnection connection;


  public List<String> getAll() {
    List<String> list = new ArrayList();
    List<byte[]> lbytes = connection.lRange(key.getBytes(), 0, -1);
    for (byte[] b : lbytes ) {
      list.add(new String(b));
    }
    return list;
  }

  public void insert(String organization) {
    connection.lPush(key.getBytes(),organization.getBytes());
    return;
  }

  public void delete(String organization) {
    connection.del((organization+".members").getBytes());
    connection.lRem(key.getBytes(), 0, organization.getBytes());
    return;
  }

}
