package es.didiez.idea253694;

import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DepartmentsController {

    @GetMapping("/departments")
    String departments(Model model) {
        model.addAttribute("departments", List.of(new Department(1L, "Sales", 2), new Department(2L, "IT", 5)));
        return "list-departments";
    }

    @Value
    public static class Department {
        Long id;
        String name;
        Integer floor;
    }

}
