/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.neotronix.pojo;

import java.sql.Date;

/**
 *
 * @author ASUS
 */
public class TransactionPojo {
    private String transactionId;
    private String useremail;
    private Date tranTime;
    private double amount;

    public TransactionPojo() {
    }

    public TransactionPojo(String transactionId, String useremail, Date tranTime, double amount) {
        this.transactionId = transactionId;
        this.useremail = useremail;
        this.tranTime = tranTime;
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransactionPojo{" + "transactionId=" + transactionId + ", useremail=" + useremail + ", tranTime=" + tranTime + ", amount=" + amount + '}';
    }
    
    
}
