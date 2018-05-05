package team.benchem.demo.bill.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
public class OrderBill {
    @Id
    @Column
    String rowId;

    @Column
    String billNumber;

    @Column
    String shippingBillNumber;

    @Column
    Date date;

    @Min(value = 0)
    @Max(value = 3)
    @Column
    String billState;

    @Min(value =  0)
    @Max(value = (long)999999999.99)
    @Column
    Double amount;

    @Column
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
