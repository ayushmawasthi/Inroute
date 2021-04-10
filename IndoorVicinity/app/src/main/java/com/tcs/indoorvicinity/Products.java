package com.tcs.indoorvicinity;

public class Products {
    private String product_name;
    private String product_brand;
    private String product_discount;

    public Products(String product_name, String product_brand, String product_discount) {
        this.product_name = product_name;
        this.product_brand = product_brand;
        this.product_discount = product_discount;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_brand() {
        return product_brand;
    }

    public void setProduct_brand(String product_brand) {
        this.product_brand = product_brand;
    }

    public String getProduct_discount() {
        return product_discount;
    }

    public void setProduct_discount(String product_discount) {
        this.product_discount = product_discount;
    }
}
