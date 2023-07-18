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
import java.time.Year;

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
    public String listPayment(Model model, @RequestParam(value = "year", required = false) Integer year) {
        int selectedYear = paymentService.findYear(year);
        model.addAttribute("payments", paymentService.paymentDTOList(selectedYear));
        model.addAttribute("year", selectedYear);
        return "payment/payment-list";
    }

    @GetMapping("/newpayment/{monthId}")
    public String newPayment(@PathVariable("monthId") Long id, Model model) {
        model.addAttribute("payment", paymentService.findPaymentDTOById(id)); // in cents
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
        model.addAttribute("description", charge.getDescription());
        model.addAttribute("chargeId", charge.getId());
        return "payment/payment-result";
    }

}
