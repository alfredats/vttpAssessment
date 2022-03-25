package sg.edu.nus.iss.vttpAssessment.controller;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class PurchaseOrderRestController {

    @GetMapping
    public String indexResource(Model model) {
        return "copyThis";
    }
    
}
