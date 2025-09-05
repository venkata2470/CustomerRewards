package com.stock.StockRewards.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPurchaseResponse {
    private String name;
    private Map<String, Integer> customerPerMonthRewards;
    private Integer sumOfRewards;

}
