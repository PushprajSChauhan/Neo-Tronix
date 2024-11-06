/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.neotronix.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ASUS
 */
public class IDUtil {
    public static String generateProdId(){
        Date today=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
        String prodId=sdf.format(today);
        prodId="P"+prodId;
        return prodId;
    }
    
    public static String generateTranId(){
        Date today=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
        String tranId=sdf.format(today);
        tranId="T"+tranId;
        return tranId;
    }
}
