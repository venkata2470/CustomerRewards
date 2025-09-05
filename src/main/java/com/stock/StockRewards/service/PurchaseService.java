package com.stock.StockRewards.service;

import com.stock.StockRewards.Response.CustomerPurchaseResponse;
import com.stock.StockRewards.dto.RequestDTO;
import com.stock.StockRewards.model.Purchase;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class PurchaseService {

    private List<Purchase> purchases = null;

    @PostConstruct
    void init() {
        purchases = Arrays.asList(new Purchase("John", 101, "January"),
                new Purchase("Welsh", 70, "February"),
                new Purchase("Gill", 120, "March"),
                new Purchase("Welsh", 120, "March"),
                new Purchase("Gill", 120, "April"),
                new Purchase("John", 120, "February"));
    }

    public List<CustomerPurchaseResponse> getPurchaseRewards(RequestDTO requestDTO) {
        List<Purchase> reqPurchases = null;
        if(requestDTO.getPurchases() != null && !requestDTO.getPurchases().isEmpty())
            reqPurchases = requestDTO.getPurchases();
        else reqPurchases = purchases;
        log.info(" list = {}", reqPurchases);
        Map<String, Map<String, Integer>> customerPerMonthRewards = new HashMap<>();
        Map<String, Integer> customerOverallRewards = new HashMap<>();
        for (Purchase purchase : reqPurchases) {
            if (purchase != null) {
                Integer rewardPoints = calculateRewards(purchase.getAmount());
                customerPerMonthRewards
                        .computeIfAbsent(purchase.getName(), k -> new HashMap<>())
                        .merge(purchase.getMonth(), rewardPoints, Integer::sum);
                customerOverallRewards.merge(purchase.getName(), rewardPoints, Integer::sum);

            }

        }
        log.info(" customerPerMonthRewards = {}", customerPerMonthRewards.toString());
        log.info(" customerOverallRewards = {}", customerOverallRewards.toString());

        List<CustomerPurchaseResponse> customerPurchaseResponses = new LinkedList<>();
        for(String key : customerOverallRewards.keySet()){
            CustomerPurchaseResponse customerPurchaseResponse = new CustomerPurchaseResponse();
            customerPurchaseResponse.setName(key);
            customerPurchaseResponse.setCustomerPerMonthRewards(customerPerMonthRewards.get(key));
            customerPurchaseResponse.setSumOfRewards(customerOverallRewards.get(key));
            customerPurchaseResponses.add(customerPurchaseResponse);
        }

        return customerPurchaseResponses;
    }

    private Integer calculateRewards(double price) {

        int rewards = 0;
        if(price > 50){
            rewards += (int)Math.min(price, 100) - 50;
        }
        if (price > 100) {
            rewards += (int) (price - 100) * 2;
        }
        return rewards;
    }
}
