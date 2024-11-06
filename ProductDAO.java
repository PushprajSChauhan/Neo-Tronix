/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.neotronix.dao;

import in.neotronix.pojo.ProductPojo;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface ProductDAO {
    public String addProduct(ProductPojo product);
    public String updateProduct(ProductPojo prevProduct, ProductPojo updatedProduct);
    public String updateProductPrice(String prodId, double updatedPrice);
    public List<ProductPojo> getAllProducts();
    public List<ProductPojo> getAllProductsByType(String type);
    public List<ProductPojo> searchAllProducts(String search);
    public ProductPojo getProductDetails(String prodId);
    public int getProductQuantity(String prodId);
    public String updateProductWithoutImage(String prevProductId, ProductPojo updatedProduct);
    public double getProductPrice(String prodId);
    public boolean sellNProducts(String prodId, int n);
    public List<String> getAllProductsType();
    public byte[] getImage(String prodId);
    public String removeProduct(String prodId);
}
