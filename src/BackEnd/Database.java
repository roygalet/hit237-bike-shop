package BackEnd;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Roy Galet
 */
public interface Database {

    public void loadBikesFromHTMLFile();

    public String getBikeList();
    
    public String checkBrand(String brand);
}
