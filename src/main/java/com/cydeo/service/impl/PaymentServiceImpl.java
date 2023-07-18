package com.cydeo.service.impl;

import com.cydeo.dto.ChargeRequest;
import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.PaymentDTO;
import com.cydeo.entity.Company;
import com.cydeo.entity.Payment;
import com.cydeo.enums.Months;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.PaymentRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final MapperUtil mapperUtil;
    private final CompanyService companyService;
    @Value("${stripe_secret_key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public PaymentServiceImpl(PaymentRepository paymentRepository, MapperUtil mapperUtil, CompanyService companyService) {
        this.paymentRepository = paymentRepository;
        this.mapperUtil = mapperUtil;
        this.companyService = companyService;
    }

    @Override
    public List<PaymentDTO> paymentDTOList(int year) {
        List<Payment> paymentList = paymentRepository.findAllByYear(year);
        if (paymentList.size() == 0) {
            Months current = Months.DECEMBER;
            int month = 1;
            CompanyDTO companyDTO = companyService.getCompanyDTOByLoggedInUser();
            Company company = mapperUtil.convert(companyDTO, new Company());
            for (int i = 0; i < 12; i++) {
                paymentList.add(new Payment(year, BigDecimal.valueOf(250.00).setScale(2, RoundingMode.HALF_UP),
                        LocalDate.of(year, month++, 5), false, current.getNextMonth(),
                        company));
                current = current.getNextMonth();
            }
            paymentRepository.saveAll(paymentList);
        }
        return paymentList.stream()
                .map(payment -> mapperUtil.convert(payment, new PaymentDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDTO findPaymentDTOById(Long id) {
        return mapperUtil.convert(paymentRepository.findById(id).orElseThrow(),new PaymentDTO());
    }

    @Transactional
    public Charge charge(ChargeRequest chargeRequest, Long id)
            throws AuthenticationException, InvalidRequestException,
                         APIConnectionException, CardException, APIException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.getAmount());
        chargeParams.put("currency", chargeRequest.getCurrency());
        chargeParams.put("description", chargeRequest.getDescription());
        chargeParams.put("source", chargeRequest.getStripeToken());

        Payment payment = paymentRepository.findById(id).orElseThrow();
        try {
            Charge charge = Charge.create(chargeParams);
            if (charge.getStatus().equals("succeeded")) {
                payment.setPaid(true);
                paymentRepository.save(payment);
            }
            return charge;
        } catch (StripeException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Integer findYear(Integer year) {
        int currentYear = Year.now().getValue();
        return (year != null) ? year : currentYear;
    }

}
