/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.neotronix.dao;

import in.neotronix.pojo.DemandPojo;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface DemandDAO {
    public boolean addProduct(DemandPojo demandPojo);
    public boolean removeProduct(String userId, String prodId);
    public List<DemandPojo> haveDemanded(String prodId);
}
