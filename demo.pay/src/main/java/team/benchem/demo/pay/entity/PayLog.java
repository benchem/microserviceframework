package team.benchem.demo.pay.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class PayLog {
    String RowId;
    Date payDate;
    String billNumber;
    Double amount;

    public PayLog() {
        RowId = UUID.randomUUID().toString();
        Calendar calendar = Calendar.getInstance();
        payDate = calendar.getTime();
    }

    public String getRowId() {
        return RowId;
    }

    public void setRowId(String rowId) {
        RowId = rowId;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
