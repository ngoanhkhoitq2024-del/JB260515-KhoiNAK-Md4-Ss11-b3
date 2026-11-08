package re.edu.medicinestore.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import re.edu.medicinestore.dto.AlertMessage;
import re.edu.medicinestore.service.AlertPublisher;

@RestController
@RequestMapping("/api/v1/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertPublisher alertPublisher;

    @PostMapping
    public ResponseEntity<String> sendAlert(
            @RequestBody AlertMessage alert
    ) {

        alertPublisher.publishAlert(alert);

        return ResponseEntity.ok(
                "Đã gửi thông báo đến channel: pharmacy-alerts"
        );
    }
}