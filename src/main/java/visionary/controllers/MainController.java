package visionary.controllers;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiVersion;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Home controller for api restful
 */
@Api(name = "Home page", description = "Home page of Rest WebService of Visionary", visibility = ApiVisibility.PUBLIC)
@ApiVersion(since = "1.0", until = "1.2.10")
@Controller
public class MainController {
  @RequestMapping("/")
  @ResponseBody
  public String index() {
    return "WebService de Visionary";
  }

}