package com.ecommerce.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Order {
    private Integer orderId;
    private Integer userId;
    private String orderNumber;
    private String status; // PENDING, PAID, SHIPPED, DELIVERED, CANCELLED
    private BigDecimal totalAmount;
    private Integer shippingAddressId;
    private Integer billingAddressId;
    private Timestamp orderDate;
    private Timestamp shippedDate;
    private Timestamp deliveredDate;
    private String notes;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructors
    public Order() {}

    public Order(Integer userId, String orderNumber, BigDecimal totalAmount, Integer shippingAddressId, Integer billingAddressId) {
        this.userId = userId;
        this.orderNumber = orderNumber;
        this.status = "PENDING";
        this.totalAmount = totalAmount;
        this.shippingAddressId = shippingAddressId;
        this.billingAddressId = billingAddressId;
    }

    // Getters and Setters
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(Integer shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public Integer getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(Integer billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public Timestamp getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Timestamp shippedDate) {
        this.shippedDate = shippedDate;
    }

    public Timestamp getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(Timestamp deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", orderNumber='" + orderNumber + '\'' +
                ", status='" + status + '\'' +
                ", totalAmount=" + totalAmount +
                ", orderDate=" + orderDate +
                ", notes='" + notes + '\'' +
                '}';
    }
}
