package com.greggahill.wallenda.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Configuration
@ConfigurationProperties("spring.datasource")
@SuppressWarnings("unused")
public class DBConfiguration {
  private String driver;
  private String url;
  private String username;
  private String password;
  private int port;

  public String getDriver() {return driver;}
  public void setDriver(String driver) {this.driver=driver;}

  public String getUrl() {return url;}
  public void setUrl(String url) {this.url=url;}

  public String getUsername() {return username;}
  public void setUsername(String username) {this.username=username;}

  public String getPassword() {return password;}
  public void setPassword(String password) {this.password=password;}

  public int getPort() {return port;}
  public void setPort(int port) {this.port=port;}

  @Profile("dev")
  @Bean
  public DBConfiguration devDatabaseConnection() {return this;}

  @Profile("prod")
  @Bean
  public DBConfiguration prodDatabaseConnection() {return this;}
}
