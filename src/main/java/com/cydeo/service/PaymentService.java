package com.cydeo.service;

import com.cydeo.dto.ChargeRequest;
import com.cydeo.dto.PaymentDTO;
import com.stripe.exception.*;
import com.stripe.model.Charge;

import java.util.List;

public interface PaymentService {

    List<PaymentDTO> paymentDTOList(int year);

    Charge charge(ChargeRequest chargeRequest, Long id) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, CardException, APIException;
}
