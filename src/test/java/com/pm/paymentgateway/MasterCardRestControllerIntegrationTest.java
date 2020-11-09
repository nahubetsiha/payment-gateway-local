package com.pm.paymentgateway;

import com.pm.paymentgateway.model.MasterCard;
import com.pm.paymentgateway.repository.MasterCardRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PaymentgatewayApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationTest.properties")
public class MasterCardRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MasterCardRepository masterCardRepository;

    MasterCard masterCard1 =Util.TEST_DATA.apply("5362836483625389", "John");
    MasterCard masterCard2 = Util.TEST_DATA.apply("5382638291634273", "Nahu");

    @After
    public void resetDb() {
        masterCardRepository.deleteAll();
    }

    @Test
    public void givenCard_whenGetCard_thenStatus200()
            throws Exception {

        // populating database with test data
        createTestMasterCard();

        mockMvc.perform(get("/api/mastercard/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].cardNumber", is(masterCard1.getCardNumber())))
                .andExpect(jsonPath("$[1].cardNumber", is(masterCard2.getCardNumber())));
    }

    @Test
    public void whenValidInput_thenCreateCard() throws IOException, Exception {

        mockMvc.perform(post("/api/mastercard/add").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(masterCard1)));
        List<MasterCard> found = masterCardRepository.findAll();
        assertThat(found).extracting(MasterCard::getCardNumber).containsOnly(masterCard1.getCardNumber());
    }

    private void createTestMasterCard() {

        masterCardRepository.saveAndFlush(masterCard1);
        masterCardRepository.saveAndFlush(masterCard2);
    }
}
