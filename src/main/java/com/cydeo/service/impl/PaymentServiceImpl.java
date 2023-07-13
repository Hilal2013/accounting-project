package com.cydeo.service.impl;

import com.cydeo.dto.ChargeRequest;
import com.cydeo.dto.PaymentDTO;
import com.cydeo.entity.Payment;
import com.cydeo.enums.Months;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.PaymentRepository;
import com.cydeo.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final MapperUtil mapperUtil;
    @Value("${stripe_secret_key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public PaymentServiceImpl(PaymentRepository paymentRepository, MapperUtil mapperUtil) {
        this.paymentRepository = paymentRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<PaymentDTO> paymentDTOList(int year) {
        List<Payment> paymentList = paymentRepository.findAllByYearOrderByMonthAsc(year);
        if (paymentList.size() == 0) {
            Months current = Months.DECEMBER;
            int month = 1;
            for (int i = 0; i < 12; i++) {
                paymentList.add(new Payment(year, BigDecimal.valueOf(250.00).setScale(2, RoundingMode.HALF_UP), LocalDate.of(year, month++, 5)
                        , false, current.getNextMonth()));
                current = current.getNextMonth();
            }
            paymentList = paymentList.stream()
                    .sorted(Comparator.comparing(payment -> payment.getMonth().getValue()))
                    .collect(Collectors.toList());
            paymentRepository.saveAll(paymentList);
        }
        return paymentList.stream()
                .map(payment -> mapperUtil.convert(payment, new PaymentDTO()))
                .collect(Collectors.toList());
    }

    public Charge charge(ChargeRequest chargeRequest, Long id)
            throws AuthenticationException, InvalidRequestException,
                         APIConnectionException, CardException, APIException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.getAmount());
        chargeParams.put("currency", chargeRequest.getCurrency());
        chargeParams.put("description", chargeRequest.getDescription());
        chargeParams.put("source", chargeRequest.getStripeToken());
        Payment payment = paymentRepository.findById(id).orElseThrow();
        payment.setPaid(true);
        paymentRepository.save(payment);
        return Charge.create(chargeParams);
    }
}
