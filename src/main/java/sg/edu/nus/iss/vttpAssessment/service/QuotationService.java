package sg.edu.nus.iss.vttpAssessment.service;

import java.io.StringReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonStructure;

public interface QuotationService {
    default Logger getLogger() { return LoggerFactory.getLogger(this.getClass()); }

    default JsonStructure invoke(String url) throws RestClientException, JsonException, IllegalArgumentException {
        RestTemplate rt = new RestTemplate();
        RequestEntity<Void> req = RequestEntity.get(url).accept(MediaType.APPLICATION_JSON).build();
        ResponseEntity<String> resp = rt.exchange(req, String.class);
        if (resp.getStatusCodeValue() != 200) {
            throw new IllegalArgumentException("HTTP error: code " + resp.getStatusCodeValue());
        }
        return Json.createReader(new StringReader(resp.getBody())).read();
    }
   
}
