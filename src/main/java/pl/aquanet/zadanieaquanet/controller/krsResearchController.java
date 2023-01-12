package pl.aquanet.zadanieaquanet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.aquanet.zadanieaquanet.dto.FirmDataDto;
import pl.aquanet.zadanieaquanet.dto.FirmDataDtoBuilder;


@RestController
class krsResearchController {

    public static final String rejestr = "P";

    @GetMapping(value = "/firmdata/{krs}")
    public ResponseEntity<FirmDataDto> getFirmData(@PathVariable String krs) throws JsonProcessingException {

        String uri = getUri(krs, rejestr);
        RestTemplate restTemplate = new RestTemplate();
        String jsonResult = restTemplate.getForObject(uri, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonResult);

        String formaPrawna = jsonNode.get("odpis").get("dane").get("dzial1").get("danePodmiotu").get("formaPrawna").asText();
        String regon = jsonNode.get("odpis").get("dane").get("dzial1").get("danePodmiotu").get("identyfikatory").get("regon").asText();
        String nip = jsonNode.get("odpis").get("dane").get("dzial1").get("danePodmiotu").get("identyfikatory").get("nip").asText();

        FirmDataDto firmDataDto = new FirmDataDtoBuilder()
                .withFormaPrawna(formaPrawna)
                .withRegon(regon)
                .withNip(nip)
                .build();

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(firmDataDto);
    }

    private String getUri(String krs, String rejestr) {
        StringBuilder uri = new StringBuilder("https://api-krs.ms.gov.pl/api/krs/OdpisAktualny/");
        uri.append(krs);
        uri.append("?rejestr=");
        uri.append(rejestr);
        uri.append("&format=json");
        return uri.toString();
    }

}
