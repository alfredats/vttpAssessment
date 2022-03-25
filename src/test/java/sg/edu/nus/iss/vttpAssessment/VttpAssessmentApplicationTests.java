package sg.edu.nus.iss.vttpAssessment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import sg.edu.nus.iss.vttpAssessment.model.Quotation;
import sg.edu.nus.iss.vttpAssessment.service.QuotationService;

@SpringBootTest
class VttpAssessmentApplicationTests {

	@Autowired
	private QuotationService qSvc;

	@Test
	void contextLoads() {
	List<String> items = new ArrayList<>();
	items.add("durian");
	items.add("plum");
	items.add("pear");

	Optional<Quotation> optQuot = qSvc.getQuotations(items);

	Assertions.assertFalse(optQuot.isPresent());
	}

}
