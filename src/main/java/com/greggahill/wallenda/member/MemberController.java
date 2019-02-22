package com.greggahill.wallenda.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class MemberController {

  @Autowired
  private MemberService memberService;

  //get member homee
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String getHome() {
      return "Greetings from Gregg's first Spring Boot app running in PCF!\n";
  }

  //get all members of the organization
  @RequestMapping(value = "/{organization}/members", method = RequestMethod.GET)
  public List<String> getMembers(@PathVariable String organization) {
    List<String> list = memberService.getAll(organization);
    return list;
  }

  //add a member to an organization
  @RequestMapping(value = "/{organization}/members/{member}", method = RequestMethod.POST)
  public void getMembers(@PathVariable String organization, @PathVariable String member) {
    memberService.insert(organization, member);
    return;
  }

  //delete a member from an organization
  @RequestMapping(value = "/{organization}/members/{member}", method = RequestMethod.DELETE)
  public void deletePartner(@PathVariable String organization, @PathVariable String member) {
    memberService.delete(organization, member);
    return;
  }

  //get all members of the organization
  @RequestMapping(value = "/{organization}/members", method = RequestMethod.PUT)
  public String replaceMembers(@PathVariable String organization) {
    return "redirect:/notsupported";
  }

}
