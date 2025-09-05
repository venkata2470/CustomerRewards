package com.stock.StockRewards.controller;

import com.stock.StockRewards.Response.CustomerPurchaseResponse;
import com.stock.StockRewards.dto.RequestDTO;
import com.stock.StockRewards.service.PurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
@Slf4j
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping(value = "/calculateRewards", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerPurchaseResponse>> getPurchaseResponse(@RequestBody RequestDTO requestDTO) {
        try {
            List<CustomerPurchaseResponse> customerPurchaseResponses = purchaseService.getPurchaseRewards(requestDTO);
            return new ResponseEntity<>(customerPurchaseResponses, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
