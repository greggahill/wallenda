package com.greggahill.wallenda.redislabs;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/** 
 *  PoolManager uses Jedis to manage a pool of redis connections.
 *  It the variable VCAP_SERVICES exists it assumes it is running
 *  in a Pivotal Cloud Foundry production environment.  If 
 *  VCAP_SERVICES does not exist it PoolManager looks for a local
 *  redis for testing.
 *  <p>
 *  <b>Note:</b> I expect this class to change a lot as I move 
 *  production to kubernetes running on GCP.
 *
 *  @author  gregg
 */


public class PoolManager {
  private JedisPool pool = null;
  private Jedis jedis = null;

  public PoolManager() {
      String vcap_services = System.getenv("VCAP_SERVICES");

      // if no VCAP_SERVICES look for local instance of redis 
      if (vcap_services == null || vcap_services.length() <= 0) {
/*
        pool = new JedisPool(new JedisPoolConfig(),
                rc.getCredentials().getHostName(),
                Integer.parseInt(rc.getCredentials().getPort()),
                Protocol.DEFAULT_TIMEOUT,
                rc.getCredentials().getPassword());
*/
        return;
      }
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      VcapServices vcap = gson.fromJson(vcap_services, VcapServices.class);

      RedisCloud[] rediscloud = vcap.getRedisCloud();

      for (RedisCloud rc:rediscloud) {
        pool = new JedisPool(new JedisPoolConfig(),
                rc.getCredentials().getHostName(),
                Integer.parseInt(rc.getCredentials().getPort()),
                Protocol.DEFAULT_TIMEOUT,
                rc.getCredentials().getPassword());
        break; // I just want one.  Will change when needed
      }
      if (pool == null)  return;
      jedis = pool.getResource();
  }

  public Jedis getJedis() {
    return jedis;
  }

  public void close() {
    pool.returnResource(jedis);
    pool.close();
  }

}
