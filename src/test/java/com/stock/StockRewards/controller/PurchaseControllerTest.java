package com.stock.StockRewards.controller;

import com.stock.StockRewards.controller.PurchaseController;
import com.stock.StockRewards.dto.RequestDTO;
import com.stock.StockRewards.model.Purchase;
import com.stock.StockRewards.Response.CustomerPurchaseResponse;
import com.stock.StockRewards.service.PurchaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

@ExtendWith(MockitoExtension.class)
class PurchaseControllerTest {

    @Mock
    private PurchaseService purchaseService;

    @InjectMocks
    private PurchaseController purchaseController;

    private RequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        Purchase purchase1 = new Purchase("cust1", 120.0, "2025-09-05");
        Purchase purchase2 = new Purchase("cust2", 75.0, "2025-09-05");

        requestDTO = new RequestDTO();
        requestDTO.setPurchases(Arrays.asList(purchase1, purchase2));
    }

    @Test
    void testGetPurchaseResponse_Success() {
        List<CustomerPurchaseResponse> mockResponse = getCustomerPurchaseResponses();

        when(purchaseService.getPurchaseRewards(any(RequestDTO.class))).thenReturn(mockResponse);

        ResponseEntity<List<CustomerPurchaseResponse>> response = purchaseController.getPurchaseResponse(requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(purchaseService, times(1)).getPurchaseRewards(any(RequestDTO.class));
    }

    @Test
    void testGetPurchaseResponse_EmptyList() {
        when(purchaseService.getPurchaseRewards(any(RequestDTO.class))).thenReturn(Collections.emptyList());

        ResponseEntity<List<CustomerPurchaseResponse>> response = purchaseController.getPurchaseResponse(requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(purchaseService, times(1)).getPurchaseRewards(any(RequestDTO.class));
    }


    private static List<CustomerPurchaseResponse> getCustomerPurchaseResponses() {
        Map<String, Integer> rewardsCust1 = new HashMap<>();
        rewardsCust1.put("January", 120);
        rewardsCust1.put("February", 90);

        Map<String, Integer> rewardsCust2 = new HashMap<>();
        rewardsCust2.put("January", 50);
        rewardsCust2.put("February", 70);
        List<CustomerPurchaseResponse> mockResponse = Arrays.asList(
                new CustomerPurchaseResponse("cust1", rewardsCust1, 120),
                new CustomerPurchaseResponse("cust2", rewardsCust2, 130)
        );
        return mockResponse;
    }
}