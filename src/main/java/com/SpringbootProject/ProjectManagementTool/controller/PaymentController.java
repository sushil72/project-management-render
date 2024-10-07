package com.SpringbootProject.ProjectManagementTool.controller;

import com.SpringbootProject.ProjectManagementTool.Services.UserService;
import com.SpringbootProject.ProjectManagementTool.model.Enum.PlanType;
import com.SpringbootProject.ProjectManagementTool.model.User;
import com.SpringbootProject.ProjectManagementTool.response.PaymentLinkResponse;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")

public class PaymentController {
    @Value("${razorpay.api.key}")
    private String apiKey;
    @Value("${razorpay.api.secret}")
    private String apiSecret;

    @Autowired
    private UserService userService;


//    @PostMapping("/{planType}")
//    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
//            @PathVariable PlanType planType,
//            @RequestHeader("Authorization") String jwt) throws Exception {
//        System.out.println("plantype : " + planType);
//        System.out.println("jwt : " + jwt);
//        User user = userService.findUserProfileByJwt(jwt);
//        int amount = 799 * 100;
//        if (planType.equals(PlanType.ANNUALLY)) {
//            amount = amount * 12;
//            amount = (int) (amount * 0.7);
//        }
//
//
//        RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);
//        JSONObject paymentRequest = new JSONObject();
//        paymentRequest.put("amount", amount);
//        paymentRequest.put("currency", "INR");
//
//        JSONObject customer = new JSONObject();
//        customer.put("name",user.getFullname());
//        customer.put("email",user.getEmail());
//        paymentRequest.put("customer", customer);
//
//        JSONObject notify=new JSONObject();
//        notify.put("email",true);
//        paymentRequest.put("notify",notify);
//
//        paymentRequest.put("callback_url","http://localhost:5173/upgrade_plan/success?planType"+planType);
//        System.out.println("payment Request"+ paymentRequest);
//        PaymentLink payment = razorpay.paymentLink.create(paymentRequest) ;
//
//        String paymentLinkId = payment.get("id");
//        String paymentLinkUrl = payment.get("short_url");
//        PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();
//        paymentLinkResponse.setPayment_link_url(paymentLinkUrl);
//        paymentLinkResponse.setPayment_link_id(paymentLinkId);
//        return new ResponseEntity<>(paymentLinkResponse , HttpStatus.CREATED);
//    }
@PostMapping("/{planType}")
public ResponseEntity<?> createPaymentLink(
        @PathVariable PlanType planType,
        @RequestHeader("Authorization") String jwt) {
    try {
        System.out.println("plantype : " + planType);
        System.out.println("jwt : " + jwt);
        User user = userService.findUserProfileByJwt(jwt);
        int amount = 799 * 100;
        if (planType.equals(PlanType.ANNUALLY)) {
            amount = amount * 12;
            amount = (int) (amount * 0.7);
        }

        RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);
        JSONObject paymentRequest = new JSONObject();
        paymentRequest.put("amount", amount);
        paymentRequest.put("currency", "INR");
        paymentRequest.put("callback_url", "http://localhost:5173/upgrade_plan/success?planType=" + planType);

        // Razorpay request creation
        PaymentLink payment = razorpay.paymentLink.create(paymentRequest);

        // Return response
        String paymentLinkId = payment.get("id");
        String paymentLinkUrl = payment.get("short_url");
        PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();
        paymentLinkResponse.setPayment_link_url(paymentLinkUrl);
        paymentLinkResponse.setPayment_link_id(paymentLinkId);
        return new ResponseEntity<>(paymentLinkResponse, HttpStatus.CREATED);
    } catch (Exception e) {
        e.printStackTrace();  // Log the exception to understand the root cause
        return new ResponseEntity<>("Error creating payment link", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

}
