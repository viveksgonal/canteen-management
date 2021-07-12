package com.example.vescanteenstaff;

public class Item {
    String item_name,item_price,item_quantity,order_num,mobile_num,total;
    Item(){}
    Item(String item_name, String item_price, String item_quantity, String total, String order_num, String mobile_num){
        this.item_name=item_name;
        this.item_price=item_price;
        this.item_quantity = item_quantity;
        this.total=total;
        this.order_num = order_num;
        this.mobile_num = mobile_num;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getItem_quantity() {
        return item_quantity;
    }

    public void setItem_quantity(String item_quantity) {
        this.item_quantity = item_quantity;
    }
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getMobile_num() {
        return mobile_num;
    }

    public void setMobile_num(String mobile_num) {
        this.mobile_num = mobile_num;
    }
}
