package BackEnd;

import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Roy Galet
 */
public class Bike implements Serializable {
    private String name;
    private int size;
    Bike left = null, right = null;
    String searchResult = "";

    public Bike(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public Bike insert(Bike bike, String brand, int size ) {
        if (bike == null) {
            bike = new Bike(brand, size);
        } else {
            if (brand.compareToIgnoreCase(bike.name)<=0) {
                bike.left = bike.insert(bike.left, brand, size);
            } else {
                bike.right = bike.insert(bike.right, brand, size);
            }
        }
        return bike;
    }
    
    public String print(Bike root){
        if(root!=null){
            return print(root.left) + root.name + " " + root.size + "\n" + print(root.right);
        }else{
            return "";
        }
    }
    
    public String search(Bike root, String keyword){
        if(root!=null){
            search(root.left, keyword);
            if(root.name.compareToIgnoreCase(keyword)==0){
                searchResult += root.name + " " + root.size ;
            }
            search(root.right,keyword);
        }
        return searchResult;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }
}
