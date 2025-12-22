package com.cybersourcecem.services;

import com.cybersourcecem.configuration.CyberSourceProperties;
import com.cybersource.authsdk.core.MerchantConfig;

import Invokers.ApiClient;
import Invokers.ApiException;
import Invokers.ApiResponse;
import Invokers.Configuration;

import Api.PaymentsApi;

import Model.CreatePaymentRequest;
import Model.PtsV2PaymentsPost201Response;
import Model.Ptsv2paymentsOrderInformation;
import Model.Ptsv2paymentsOrderInformationAmountDetails;
import Model.Ptsv2paymentsPaymentInformation;
import Model.Ptsv2paymentsPaymentInformationCard;

import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class PaymentService {

    private final CyberSourceProperties csProps;

    public PaymentService(CyberSourceProperties csProps) {
        this.csProps = csProps;
    }

    public String createPayment(
            String cardNumber,
            String expMonth,
            String expYear,
            String cvv,
            Long amountCents
    ) {

        try {
            // 1️⃣ Propriétés Cybersource
            Properties props = new Properties();
            props.setProperty("authenticationType", "http_signature");
            props.setProperty("merchantID", csProps.getMerchantID());
            props.setProperty("merchantKeyId", csProps.getApiKeyId());
            props.setProperty("merchantsecretKey", csProps.getSecretKey());
            props.setProperty("runEnvironment", "apitest.cybersource.com");

            // 2️⃣ MerchantConfig
            MerchantConfig merchantConfig = new MerchantConfig(props);

            // 3️⃣ ApiClient AVEC MerchantConfig (POINT CLÉ 🔥🔥🔥)
            ApiClient apiClient = new ApiClient(merchantConfig);

            // 4️⃣ Payments API
            PaymentsApi paymentsApi = new PaymentsApi(apiClient);


            // 4️⃣ Requête paiement
            CreatePaymentRequest request = new CreatePaymentRequest();

            Ptsv2paymentsOrderInformationAmountDetails amount =
                    new Ptsv2paymentsOrderInformationAmountDetails();
            amount.setTotalAmount(String.valueOf(amountCents)); // 🔥 XOF = entier
            amount.setCurrency("XOF");

            Ptsv2paymentsOrderInformation orderInfo =
                    new Ptsv2paymentsOrderInformation();
            orderInfo.setAmountDetails(amount);
            request.setOrderInformation(orderInfo);

            Ptsv2paymentsPaymentInformationCard card =
                    new Ptsv2paymentsPaymentInformationCard();
            card.number(cardNumber)
                    .expirationMonth(expMonth)
                    .expirationYear(expYear)
                    .securityCode(cvv);

            Ptsv2paymentsPaymentInformation paymentInfo =
                    new Ptsv2paymentsPaymentInformation();
            paymentInfo.setCard(card);
            request.setPaymentInformation(paymentInfo);

            // 5️⃣ Appel Cybersource
            ApiResponse<PtsV2PaymentsPost201Response> response =
                    paymentsApi.createPaymentWithHttpInfo(request);

            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                return response.getData().toString();
            }

            return "Erreur Cybersource HTTP : " + response.getStatusCode();

        } catch (ApiException e) {
            throw new RuntimeException(
                    "Erreur Cybersource API : " + e.getResponseBody(), e
            );
        } catch (Exception e) {
            throw new RuntimeException(
                    "Erreur paiement : " + e.getMessage(), e
            );
        }
    }
}
