package com.pm.paymentgateway;

import com.pm.paymentgateway.model.CardType;
import com.pm.paymentgateway.model.MasterCard;

import java.util.function.BiFunction;

public class Util {
    public static final BiFunction<String,String, MasterCard> TEST_DATA= Util::apply;


    private static MasterCard apply(String cardNumber, String name) {
        MasterCard masterCard = new MasterCard();
        masterCard.setCardNumber(cardNumber);
        masterCard.setName(name);
        masterCard.setPin("043");
        masterCard.setExpDate("2021-01");
        masterCard.setCardType(CardType.MASTERCARD);
        masterCard.setPin("302");

        return masterCard;
    }
}
