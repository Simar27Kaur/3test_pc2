package com.example.test3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testCalculateTotalBill_XLWithNoToppings() {
        Order order = new Order(1, "John Doe", "1234567890", Order.PizzaSize.XL, 0);
        assertEquals(15.00 * 1.15, order.calculateTotalBill(), 0.001);
    }

    @Test
    public void testCalculateTotalBill_LargeWith2Toppings() {
        Order order = new Order(2, "Jane Doe", "0987654321", Order.PizzaSize.LARGE, 2);
        assertEquals((12.00 + 3.00) * 1.15, order.calculateTotalBill(), 0.001);
    }

    @Test
    public void testCalculateTotalBill_MediumWith3Toppings() {
        Order order = new Order(3, "Tom Smith", "1122334455", Order.PizzaSize.MEDIUM, 3);
        assertEquals((10.00 + 4.50) * 1.15, order.calculateTotalBill(), 0.001);
    }

    @Test
    public void testCalculateTotalBill_SmallWith5Toppings() {
        Order order = new Order(4, "Alice Johnson", "5566778899", Order.PizzaSize.SMALL, 5);
        assertEquals((8.00 + 7.50) * 1.15, order.calculateTotalBill(), 0.001);
    }
}
