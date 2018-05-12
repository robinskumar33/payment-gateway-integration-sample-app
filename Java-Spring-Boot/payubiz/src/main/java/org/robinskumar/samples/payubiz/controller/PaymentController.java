package org.robinskumar.samples.payubiz.controller;

import org.robinskumar.samples.payubiz.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/startPayment")
    public ResponseEntity redirectWithUsingRedirectPrefix() throws Exception{

        return paymentService.startPayment();
    }
}
