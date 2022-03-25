package sg.edu.nus.iss.vttpAssessment;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.vttpAssessment.model.Quotation;
import sg.edu.nus.iss.vttpAssessment.service.QuotationService;

@SpringBootTest
@AutoConfigureMockMvc
class VttpAssessmentApplicationTests {

	@Autowired
	private QuotationService qSvc;

	@Autowired
	private MockMvc mvc;

	@Test
	void contextLoads() {
	List<String> items = new ArrayList<>();
	items.add("durian");
	items.add("plum");
	items.add("pear");

	Optional<Quotation> optQuot = qSvc.getQuotations(items);

	// optQuot should be empty because "plum" is not a valid item
	Assertions.assertFalse(optQuot.isPresent());
	}


	@Test
	void appleAndDurian() throws Exception {
		JsonArray lineItems = Json.createArrayBuilder()
								.add(Json.createObjectBuilder().add("item","apple").add("quantity",1))
								.add(Json.createObjectBuilder().add("item","durian").add("quantity",1))
								.build();

		JsonObject payLoad = Json.createObjectBuilder()
								.add("address", "teletubby ave 1")
								.add("email", "twinkywinky@teletubby.com")
								.add("name", "twinky winky")
								.add("navigationId", 4)
								.add("lineItems", lineItems)
								.build();

		RequestBuilder req = MockMvcRequestBuilders.post("/api/po")
										.accept(MediaType.APPLICATION_JSON)
										.contentType(MediaType.APPLICATION_JSON)
										.content(payLoad.toString());

		MvcResult result = mvc.perform(req).andReturn();
		int status = result.getResponse().getStatus();
		JsonObject resultJson = Json.createReader(new StringReader(result.getResponse().getContentAsString())).readObject();

		Assertions.assertEquals(200, status);
		// see https://stackoverflow.com/a/153753
		double rounding = (double)Math.round(resultJson.getJsonNumber("total").doubleValue() * 100d) / 100d;
		Assertions.assertEquals(5.30d, rounding);

	}


}
