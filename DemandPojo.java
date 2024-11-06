/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.neotronix.pojo;

/**
 *
 * @author ASUS
 */
public class DemandPojo {
    private String useremail;
    private String prodId;
    private int demandQuantity; //This denotes the amount of products user wants from the site but currently the product is not available in stock

    public DemandPojo() {
    }

    public DemandPojo(String useremail, String prodId, int demandQuantity) {
        this.useremail = useremail;
        this.prodId = prodId;
        this.demandQuantity = demandQuantity;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public int getDemandQuantity() {
        return demandQuantity;
    }

    public void setDemandQuantity(int demandQuantity) {
        this.demandQuantity = demandQuantity;
    }

    @Override
    public String toString() {
        return "DemandPojo{" + "useremail=" + useremail + ", prodId=" + prodId + ", demandQuantity=" + demandQuantity + '}';
    }
    
    
}
