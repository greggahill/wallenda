package com.greggahill.wallenda.redislabs;

import java.util.Arrays;

public class VcapServices {
  private RedisCloud[] rediscloud;

  public RedisCloud[] getRedisCloud() {
    return rediscloud;
  }
  public void setRedisCloud(RedisCloud[] rediscloud) {
    this.rediscloud = rediscloud;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (getRedisCloud()!=null) {
          sb.append("Redis Cloud:  "+Arrays.toString(getRedisCloud()));
        }    return sb.toString();
  }
}
