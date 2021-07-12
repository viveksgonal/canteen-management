package com.example.vescanteenstaff;

public class Passing_Values {

    String Name;
    String Value;

    public Passing_Values(){

    }
    public Passing_Values(String Name,String Value){
        this.Name=Name;
        this.Value=Value;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
