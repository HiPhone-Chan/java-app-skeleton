package tech.hiphone.admin_page.web.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminClientForwardController {

    /**
     * Forwards any unmapped paths (except those containing a period) to the client {@code index.html}.
     * @return forward to client {@code index.html}.
     */
    @GetMapping(value = "/page/admin/**/{path:[^\\.]*}")
    public String otherForward() {
        return "forward:/page/admin/index.html";
    }
    
    @GetMapping(value = "/page/admin")
    public String defaultForward() {
        return "forward:/page/admin/index.html";
    }
}
