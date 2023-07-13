package com.cydeo.controller;

import com.cydeo.dto.ChargeRequest;
import com.cydeo.enums.Currency;
import com.cydeo.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Value("${stripe_public_key}")
    private String stripePublicKey;
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/list")
    public String listPayment(Model model, @RequestParam(value = "year", required = false, defaultValue = "2023") Integer year) {
        model.addAttribute("payments", paymentService.paymentDTOList(year));
        model.addAttribute("localDateTime", LocalDateTime.MIN);
        model.addAttribute("year", year);
        return "payment/payment-list";
    }

    @GetMapping("/newpayment/{modelId}")
    public String newPayment(@PathVariable("modelId") Long id, Model model) {
        model.addAttribute("amount", 250 * 100); // in cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", Currency.USD);
        return "payment/payment-method";
    }

    @PostMapping("/charge/{modelId}")
    public String charge(ChargeRequest chargeRequest, Model model, @PathVariable("modelId") Long id)
            throws StripeException {
        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(Currency.USD);
        Charge charge = paymentService.charge(chargeRequest, id);
        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());
        return "payment/payment-result";
    }

    @GetMapping("/toInvoice/{id}")
    public String toInvoice(@PathVariable Long id) {

    }

}
