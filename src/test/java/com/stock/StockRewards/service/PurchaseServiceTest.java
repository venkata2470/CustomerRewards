package com.stock.StockRewards.service;

import static org.junit.jupiter.api.Assertions.*;

import com.stock.StockRewards.Response.CustomerPurchaseResponse;
import com.stock.StockRewards.dto.RequestDTO;
import com.stock.StockRewards.model.Purchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {
    private PurchaseService purchaseService;

    @BeforeEach
    void setUp() {
        purchaseService = new PurchaseService();
        purchaseService.init(); // Simulate @PostConstruct initialization
    }

    @Test
    void testGetPurchaseRewards_WithNullRequestPurchases_UsesDefaultPurchases() {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setPurchases(null);

        List<CustomerPurchaseResponse> responses = purchaseService.getPurchaseRewards(requestDTO);

        assertNotNull(responses);
        assertFalse(responses.isEmpty());
        assertTrue(responses.stream().anyMatch(r -> r.getName().equals("John")));
        assertTrue(responses.stream().anyMatch(r -> r.getSumOfRewards() > 0));
    }

    @Test
    void testGetPurchaseRewards_WithCustomPurchases() {
        RequestDTO requestDTO = new RequestDTO();
        List<Purchase> customPurchases = Arrays.asList(
                new Purchase("Alice", 120, "May"),
                new Purchase("Bob", 70, "June")
        );
        requestDTO.setPurchases(customPurchases);

        List<CustomerPurchaseResponse> responses = purchaseService.getPurchaseRewards(requestDTO);

        assertEquals(2, responses.size());
        CustomerPurchaseResponse alice = responses.stream()
                .filter(r -> r.getName().equals("Alice"))
                .findFirst().orElse(null);

        assertNotNull(alice);
        assertTrue(alice.getSumOfRewards() > 0);
        assertEquals(90, alice.getSumOfRewards()); // 2×20 + 1×50 = 90 for $120
    }

    @Test
    void testGetPurchaseRewards_WithEmptyRequestPurchases() {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setPurchases(new ArrayList<>()); // Empty list

        List<CustomerPurchaseResponse> responses = purchaseService.getPurchaseRewards(requestDTO);

        assertNotNull(responses);
        assertFalse(responses.isEmpty());
    }

    @Test
    void testGetPurchaseRewards_WithNullPurchaseInList() {
        RequestDTO requestDTO = new RequestDTO();
        List<Purchase> purchases = Arrays.asList(
                new Purchase("TestUser", 150, "July"),
                null
        );
        requestDTO.setPurchases(purchases);

        List<CustomerPurchaseResponse> responses = purchaseService.getPurchaseRewards(requestDTO);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("TestUser", responses.get(0).getName());
        assertTrue(responses.get(0).getSumOfRewards() > 0);
    }

    @Test
    void testCalculateRewards_AmountLessThan50() throws Exception {
        // Using reflection to test private method
        var method = PurchaseService.class.getDeclaredMethod("calculateRewards", double.class);
        method.setAccessible(true);
        int rewards = (int) method.invoke(purchaseService, 40.0);
        assertEquals(0, rewards);
    }

    @Test
    void testCalculateRewards_AmountBetween50And100() throws Exception {
        var method = PurchaseService.class.getDeclaredMethod("calculateRewards", double.class);
        method.setAccessible(true);
        int rewards = (int) method.invoke(purchaseService, 80.0);
        assertEquals(30, rewards); // 80 - 50 = 30
    }

    @Test
    void testCalculateRewards_AmountGreaterThan100() throws Exception {
        var method = PurchaseService.class.getDeclaredMethod("calculateRewards", double.class);
        method.setAccessible(true);
        int rewards = (int) method.invoke(purchaseService, 120.0);
        assertEquals(90, rewards); // (100-50) + 2×20 = 50 + 40 = 90
    }
}
