package com.temx.security.email;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("api/v1/mail")
@RestController
//@Deprecated
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;


    @PostMapping()
    public ResponseEntity<Map<String,String>> sendEmail(@RequestBody EmailDetails emailDetails) {
        try {
            emailService.sendEmail(emailDetails.getTo(), emailDetails.getSubject(), emailDetails.getBody());
            Map<String,String> map = new HashMap<>();
            map.put("email",emailDetails.getTo());
            map.put("subject",emailDetails.getSubject());
            map.put("body",emailDetails.getBody());
            map.put("message","Email sent successfully!");
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            return null;
        }
    }


}
