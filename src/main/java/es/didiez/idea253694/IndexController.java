package es.didiez.idea253694;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("foo", "bar");
        return "index";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

}

