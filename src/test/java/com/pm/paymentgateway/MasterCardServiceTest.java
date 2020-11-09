package com.pm.paymentgateway;

import com.pm.paymentgateway.model.CardType;
import com.pm.paymentgateway.model.MasterCard;
import com.pm.paymentgateway.repository.MasterCardRepository;
import com.pm.paymentgateway.service.impl.MasterCardServiceImpl;
import org.junit.Assert;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MasterCardServiceTest {

    @Mock
    MasterCardRepository masterCardRepository;

    @InjectMocks
    MasterCardServiceImpl masterCardService;
    MasterCard masterCard;


    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        masterCard = new MasterCard();
        masterCard.setMasterCardId(Long.valueOf(1));
        masterCard.setCardNumber("583927364859473");
        masterCard.setAvailableBalance(10000);
        masterCard.setCardType(CardType.MASTERCARD);
        masterCard.setName("Nahu");
        masterCard.setExpDate("2021-03");
        masterCard.setPin("043");
    }

    @After
    public void tearDown() {
    }


    @Test
    public void findAll() {

        when(masterCardRepository.findAll()).thenReturn(Arrays.asList(masterCard));
        List<MasterCard> masterCards= masterCardService.getAllCards();
        Assert.assertNotNull(masterCards);
        Assert.assertEquals(Arrays.asList(masterCard),masterCards);
    }

    @Test
    public void findByCcNumber() {

        when(masterCardRepository.getByCardNumber(anyString())).thenReturn(masterCard);
        MasterCard master= masterCardService.getByCardNumber(masterCard.getCardNumber());
        Assert.assertNotNull(master);
        Assert.assertEquals(masterCard,master);
        Assert.assertEquals(masterCard.getCardNumber(),master.getCardNumber());
    }
}
