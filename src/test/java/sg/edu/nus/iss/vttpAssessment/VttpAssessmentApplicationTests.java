package sg.edu.nus.iss.vttpAssessment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

	// optQuot should be empty because "plum" is not a valid item
	Assertions.assertFalse(optQuot.isPresent());
	}

}
