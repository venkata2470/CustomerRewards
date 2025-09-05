package com.stock.StockRewards.dto;

import com.stock.StockRewards.model.Purchase;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RequestDTO {

    List<Purchase> purchases;
}
