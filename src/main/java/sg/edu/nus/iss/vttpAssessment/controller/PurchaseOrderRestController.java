package sg.edu.nus.iss.vttpAssessment.controller;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonStructure;
import jakarta.json.JsonValue;
import sg.edu.nus.iss.vttpAssessment.model.Quotation;
import sg.edu.nus.iss.vttpAssessment.service.QuotationService;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class PurchaseOrderRestController {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderRestController.class);

    @Autowired
    private QuotationService qSvc;

    @PostMapping(path = "/po")
    public ResponseEntity<String> poResource(@RequestBody String reqBody) {
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
        
        Quotation quot = qSvc.getQuotations(items).orElse(null);

        try {
            logger.info(">>> Quotation successful: " + quot.getQuotations());
            double total = 0;

            for (JsonValue entry : lineitems) {
                JsonObject x = entry.asJsonObject(); 
                total += x.getInt("quantity") * quot.getQuotation(x.getString("item"));
            }

            JsonObjectBuilder respBuilder = Json.createObjectBuilder();
            respBuilder.add("invoiceId", quot.getQuoteId())
                       .add("name", respJson.getString("name"))
                       .add("total", total);

            return ResponseEntity.ok().body(respBuilder.build().toString());

        } catch (NullPointerException e) {
            logger.error("Quotation returned from Service was empty: " + e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        
        return ResponseEntity.status(400).body("{}");

    }
    
}
