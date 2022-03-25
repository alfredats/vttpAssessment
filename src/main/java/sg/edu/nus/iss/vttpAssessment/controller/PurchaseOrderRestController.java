package sg.edu.nus.iss.vttpAssessment.controller;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class PurchaseOrderRestController {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderRestController.class);

    @PostMapping(path = "/po")
    public ResponseEntity<String> indexResource(@RequestBody String reqBody) {
        logger.info(">>> raw request: " + reqBody);

        JsonObject respJson = Json.createReader(new StringReader(reqBody)).readObject();

        JsonArray lineitems = respJson.getJsonArray("lineItems");
        List<String> items = new ArrayList<>();
        lineitems.stream()
            .filter(v -> v != null)
            .forEach( v -> {
                JsonObject x = v.asJsonObject();
                items.add(x.getString("item"));
            });

    logger.info(">>> quotation items: " + items.toString());


        return null;
    }
    
}
