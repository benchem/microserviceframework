package team.benchem.demo.bill.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class OrderBill {
    String rowId;
    String billNumber;
    String shippingBillNumber;
    Date date;
    String billState;
    Double amount;
    String productName;

    public OrderBill() {
        rowId = UUID.randomUUID().toString();
        Calendar calendar = Calendar.getInstance();
        date = calendar.getTime();
        amount = 0.00;
        billState = "未付款";
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBillState() {
        return billState;
    }

    public void setBillState(String billState) {
        this.billState = billState;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShippingBillNumber() {
        return shippingBillNumber;
    }

    public void setShippingBillNumber(String shippingBillNumber) {
        this.shippingBillNumber = shippingBillNumber;
    }
}
