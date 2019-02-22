package com.greggahill.wallenda.organization;

import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class OrganizationController {

  @Autowired
  private OrganizationService organizationService;

  //get organizations
  @RequestMapping(value = "/organizations", method = RequestMethod.GET)
  public List<String> getOrganizations() {
    List<String> list = organizationService.getAll();
    return list;
  }

  //append a new organization to the organization list
  @RequestMapping(value = "/organizations/{organization}", method = RequestMethod.POST)
  public void insertOrganization(@PathVariable String organization) {
    organizationService.insert(organization);
    return;
  }

  //append a new organization to the organization list
  @RequestMapping(value = "/organizations/{organization}", method = RequestMethod.DELETE)
  public void deleteOrganization(@PathVariable String organization) {
    organizationService.delete(organization);
    return;
  }

  //would replace the organizations list but we aren't going to do that.
  @RequestMapping(value = "/organizations", method = RequestMethod.PUT)
  public String replaceOrganizations() {
    return "redirect:/notsupported";
  }

}
