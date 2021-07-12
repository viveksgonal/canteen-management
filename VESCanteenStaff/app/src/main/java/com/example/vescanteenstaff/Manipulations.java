package com.example.vescanteenstaff;
import java.util.ArrayList;
public class Manipulations {
    private static ArrayList<Item> listOfItems=new ArrayList<>();
    protected ArrayList<String> temp = new ArrayList<>();;
    public void addItem(Item item){
        listOfItems.add(item);
    }
    public String getItemTotal(int i){
        if(i<listOfItems.size()) {
            return listOfItems.get(i).getTotal();
        }
        return "invalid";
    }
    public String getPhone(int i){
        return listOfItems.get(i).getMobile_num();
    }
    public String getItemName(int i){
        if(i<listOfItems.size()) {
            return listOfItems.get(i).getItem_name();
        }
        return "invalid";
    }
    public String getItemQuantity(int i){
        if(i<listOfItems.size()) {
            return listOfItems.get(i).item_quantity;
        }
        return "invalid";
    }

    public ArrayList<String> printOrderName(){
        temp.clear();
        for(Item item:listOfItems){
            temp.add(item.getOrder_num());
        }
        return temp;
    }
    public void deleteItem(int index){
        listOfItems.remove(index);
        temp.remove(index);
    }
    public void deleteAllItems(){
        listOfItems.clear();
    }

    public String getOrderName(int i){
        if(i<listOfItems.size()) {
            return listOfItems.get(i).getOrder_num();
        }
        return " invalid! ";
    }
    public Item getByName(String name){
        for(Item item:listOfItems){
            if(item.getItem_name().equals(name)){
                return item;
            }
        }
        return null;
    }
}
