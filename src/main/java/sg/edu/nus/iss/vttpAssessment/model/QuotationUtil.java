package sg.edu.nus.iss.vttpAssessment.model;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;


public class QuotationUtil {
    // can't modify Quotation.java so i created a util package
    public static final Quotation create(String resp) throws JsonException {
        Quotation quot = new Quotation();
        Map<String, Float> itemQuots = new HashMap<>();

        JsonObject respJson = Json.createReader(new StringReader(resp)).readObject();
        JsonArray rawQuots = respJson.getJsonArray("quotations");

        rawQuots.stream()
            .filter(v -> v != null)
            .map( v -> v.asJsonObject())
            .forEach( v -> {
                itemQuots.put(v.getString("item"), v.getJsonNumber("unitPrice").bigDecimalValue().floatValue());
            });

        quot.setQuoteId(respJson.getString("quoteId"));
        quot.setQuotations(itemQuots);

        return quot;
    }
}
