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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class BikeShop implements Database {
    private String name;
    private int streetNumber;
    List<Bike> bikeList;
    private Document htmlDatabase;
    Bike root=null;

    public BikeShop(){
        
    }
    
    public BikeShop(String name, int streetNumber) {
        this.name = name;
        this.streetNumber = streetNumber;
    }
    
    public void add(String brand, int size){
        if(root==null){
            root=new Bike(brand, size);
        }else{
            root.insert(root, brand, size);
        }
    }

    @Override
    public void loadBikesFromHTMLFile() {
        this.clearList();
        File myFile = new File("bikes.html");
        try {
            htmlDatabase = Jsoup.parse(myFile, "UTF-8");
            Elements records = htmlDatabase.select("tr");
            String[] words;
            for (int index = 1; index < records.size(); index++) {
                words = records.get(index).text().split(" ");
                this.add(words[0], Integer.parseInt(words[words.length - 1]));
            }
        } catch (IOException ex) {
            System.out.println("An error occured while parsing the HTML file.");
        }
    }

    @Override
    public String getBikeList() {
        if (root==null) {
            loadBikesFromHTMLFile();
        }
        return root.print(root);
    }
    
    @Override
    public String checkBrand(String brand) {
        if (root==null) {
            loadBikesFromHTMLFile();
        }
        return root.search(root, brand);
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getName() {
        return this.name;
    }

    public int getStreetNumber() {
        return this.streetNumber;
    }

    public void addBike(String name, int size) {
        Bike myBike = new Bike(name, size);
        bikeList.add(myBike);
    }

    public void addBike(int index, String name, int size) {
        Bike myBike = new Bike(name, size);
        bikeList.add(index, myBike);
    }

    public void printBikeElement(int index) {
        if (index < 0 || index >= bikeList.size()) {
            System.out.println("Invalid item.  Please specify index between 0 and " + (bikeList.size() - 1));
        } else {
            System.out.printf("\n%5d.) %-20s : %4d", index, bikeList.get(index).getName(), bikeList.get(index).getSize());
        }
    }

    public void printAllBikeElements() {
        System.out.printf("\n\n%5s   %-20s : %4s", "INDEX", "NAME", "SIZE");
        for (int index = 0; index < bikeList.size(); index++) {
            System.out.printf("\n%5d.) %-20s : %4d", index, bikeList.get(index).getName(), bikeList.get(index).getSize());
        }
        System.out.println();
    }

    public void removeBike(int index) {
        if (index < 0 || index >= bikeList.size()) {
            System.out.println("Invalid item.  Please specify index between 0 and " + (bikeList.size() - 1));
        } else {
            printBikeElement(index);
            System.out.println(" has been successfully REMOVED!");
            bikeList.remove(index);
        }
    }

    public void clearList() {
        root=null;
    }
    
//////UNUSED METHODS/////////////////////////////////////////
    public void saveToFile() {
        ObjectOutputStream outFile = null;
        try {
            outFile = new ObjectOutputStream(new FileOutputStream("Bike Database.ser"));
            outFile.writeObject(bikeList);

        } catch (IOException ex) {
            System.out.println("IO Exception encountered!");
        } finally {
            try {
                if (outFile != null) {
                    outFile.close();
                }
            } catch (IOException ex) {
                System.out.println("An error occured in saving the file.");
            }
        }
    }

    public void readFromSerializedFile() {
        ObjectInputStream inputFile;
        try {
            inputFile = new ObjectInputStream(new FileInputStream("Bike Database.ser"));
            try {
                bikeList = (ArrayList) inputFile.readObject();
            } catch (ClassNotFoundException ex) {
                System.out.println("Class mismatch exception occured!");
            }
        } catch (IOException ex) {
            System.out.println("IO Exception encountered!");
        }
    }

    public void loadBikesListFromFileUsingBufferedReader(String inputFileName) {
        this.clearList();
        FileReader myFileReader = null;
        BufferedReader myBufferedReader = null;
        try {
            File myFile = new File(inputFileName);

            myFileReader = new FileReader(myFile);
            try {
                myBufferedReader = new BufferedReader(myFileReader);
                String line;
                while ((line = myBufferedReader.readLine()) != null) {
                    String[] words;
                    words = line.split(" ");
                    this.addBike(words[0], Integer.parseInt(words[words.length - 1]));
                }

            } catch (IOException ex) {
                System.out.println("An error has occured in reading from file!");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found!");
        } finally {

            try {
                if (myBufferedReader != null) {
                    myBufferedReader.close();
                }
                if (myFileReader != null) {
                    myFileReader.close();
                }
            } catch (IOException ex) {
                System.out.println("An error occured in reading the file.");
            }

        }
    }

    public void loadBikesListFromFileUsingBufferedReader() {
        this.loadBikesListFromFileUsingBufferedReader("bikes.txt");
    }

    public void saveBikesListToFileUsingBufferedWriter(String outputFileName) {
        FileWriter myFileWriter = null;
        BufferedWriter myBufferedWriter = null;
        try {
            myFileWriter = new FileWriter(outputFileName);
            myBufferedWriter = new BufferedWriter(myFileWriter);
            for (int counter = 0; counter < bikeList.size(); counter++) {
                myBufferedWriter.write(bikeList.get(counter).getName() + " " + bikeList.get(counter).getSize() + "\n");
            }
            myBufferedWriter.close();
            myFileWriter.close();
        } catch (IOException ex) {
            System.out.println("An error has occured in writing to file!");
        } finally {
            try {
                if (myBufferedWriter != null) {
                    myBufferedWriter.close();
                }
                if (myFileWriter != null) {
                    myFileWriter.close();
                }
            } catch (IOException e) {
                System.out.println("An error occured in updating the file.");
            }
        }
    }

    public void saveBikesListToFileUsingBufferedWriter() {
        this.saveBikesListToFileUsingBufferedWriter("output.txt");
    }
    
    public void add() {
        String make;
        int size = 0;
        String line;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File("bikes.txt")));
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            
        } catch (IOException ex) {
            System.out.println("An error has occured while reading from file!");
        }finally{
            try{
                if(reader!=null){
                    reader.close();
                }
            }catch(IOException ex){
                
            }
        }
    }
}
