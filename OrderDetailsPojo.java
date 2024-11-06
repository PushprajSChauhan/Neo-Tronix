/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.neotronix.pojo;

import java.io.InputStream;
import java.sql.Date;

/**
 *
 * @author ASUS
 */
public class OrderDetailsPojo {
//    Iss pojo keliye DB mei koi table nahi hai yaha se bas user ko uske order ki saari details ek saath milengi using JOIN queries
    private String orderId;
    private String prodId;
    private String prodName;
    private String quantity;
    private double amount;
    private int shipped;
    private Date time;
    private InputStream prodImage;

    public OrderDetailsPojo() {
    }

    public OrderDetailsPojo(String orderId, String prodId, String prodName, String quantity, double amount, int shipped, Date time, InputStream prodImage) {
        this.orderId = orderId;
        this.prodId = prodId;
        this.prodName = prodName;
        this.quantity = quantity;
        this.amount = amount;
        this.shipped = shipped;
        this.time = time;
        this.prodImage = prodImage;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getShipped() {
        return shipped;
    }

    public void setShipped(int shipped) {
        this.shipped = shipped;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public InputStream getProdImage() {
        return prodImage;
    }

    public void setProdImage(InputStream prodImage) {
        this.prodImage = prodImage;
    }
}
