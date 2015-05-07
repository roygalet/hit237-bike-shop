package FrontEnd;

import BackEnd.Database;
import BackEnd.BikeShop;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Roy Galet
 */
public class UserInterface {

    private String shopName;
    private int shopNumber;
    private JFrame frameMain;
    private JTabbedPane tabMain, tabSearch;
    private JPanel panelWelcome, panelWelcomeHeader, panelWelcomeBody, panelSearch, panelOptions;
    private BikeAnimation panelAnimation;
    private JButton buttonLoadDatabase, buttonDisplayBikesList, buttonSearchBrand, buttonClearSearch, buttonAnimate;
    private JLabel labelStatus;
    private JTextField textfieldSearchBrand;
    private String concatenatedList;
    private String[] records;
    private final int frameWidth = 960, frameHeight = 640;
    Database db;

    public UserInterface() {
        db = new BikeShop();
        initializeUserInterface();
    }

    public UserInterface(String shopName, int shopNumber) {
        db = new BikeShop();
        this.shopName = shopName;
        this.shopNumber = shopNumber;
        initializeUserInterface();
    }

    private void initializeUserInterface() {
        frameMain = new JFrame("Welcome to the Bike Shop");
        frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMain.setSize(frameWidth, frameHeight);
        frameMain.setResizable(false);
        frameMain.setLocationRelativeTo(null);
        frameMain.setVisible(true);
        tabMain = new JTabbedPane(JTabbedPane.TOP);
        frameMain.setLayout(new BorderLayout());
        frameMain.getContentPane().add(tabMain, BorderLayout.EAST);
        loadAnimationScreen();
        loadWelcomeScreen();
        frameMain.repaint();
    }

    private void loadWelcomeScreen() {
        if (panelWelcome == null) {
            buttonLoadDatabase = new JButton("Load Database");
            buttonDisplayBikesList = new JButton("Display Bikes List");
            textfieldSearchBrand = new JTextField(10);
            buttonSearchBrand = new JButton("Search");
            buttonAnimate = new JButton("Pause Animation");
            panelWelcome = new JPanel(new BorderLayout(), true);
            panelWelcomeHeader = new JPanel(new FlowLayout(), true);
            panelSearch = new JPanel(new FlowLayout(), true);
            panelOptions = new JPanel(new FlowLayout(), true);
            panelWelcome.add(panelWelcomeHeader, BorderLayout.NORTH);
            panelSearch.add(new JLabel("Search: "));
            panelSearch.add(textfieldSearchBrand);
            panelSearch.add(buttonSearchBrand);
            panelWelcomeHeader.add(buttonLoadDatabase);
            panelWelcomeHeader.add(buttonDisplayBikesList);
            panelOptions.add(buttonAnimate);
            tabMain.addTab("Welcome", panelWelcome);
            tabMain.addTab("Animation Options", panelOptions);
            labelStatus = new JLabel(" ");
            labelStatus.setForeground(Color.red);
            panelWelcome.add(labelStatus, BorderLayout.SOUTH);
            panelWelcomeBody = new JPanel();
            panelWelcomeBody.setLayout(new BorderLayout());
            panelWelcome.add(panelWelcomeBody, BorderLayout.CENTER);
            panelWelcomeBody.add(panelSearch, BorderLayout.SOUTH);
            panelWelcomeBody.setVisible(true);
            frameMain.setVisible(true);
            buttonLoadDatabase.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    loadBikesFromBackEnd();
                }
            });
            buttonDisplayBikesList.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displayBikesList();
                }
            });
            buttonSearchBrand.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (textfieldSearchBrand.getText().length() > 0) {
                        searchBrand(textfieldSearchBrand.getText());
                    } else {
                        setStatusMessage("Please type the Brand Name to search.", Color.RED);
                    }
                }
            });
            buttonAnimate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (panelAnimation.animationTimer.isRunning() == false) {
                        panelAnimation.animationTimer.start();
                        buttonAnimate.setText("Pause Animation");
                    } else {
                        panelAnimation.animationTimer.stop();
                        buttonAnimate.setText("Resume Animation");
                    }
                }
            });
            tabMain.addMouseListener(new MouseListener(){

                @Override
                public void mouseClicked(MouseEvent e) {
//Sorry, this is usually unnecessary but this is my workaround for the overlapping swing/awt components bug.
//This prevents the Animate Button from mysteriously appearing whenever 
//the mouse hovers over the space between the Load and Display buttons
                    buttonAnimate.setVisible(true);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
                
            });
        }
        panelSearch.setVisible(true);
        panelWelcomeHeader.setVisible(true);
        panelWelcome.setVisible(true);
        panelOptions.setVisible(true);
        buttonAnimate.setVisible(false);
    }

    public void loadAnimationScreen() {
        if (panelAnimation == null) {
            panelAnimation = new BikeAnimation(shopName, shopNumber);
            panelAnimation.shopName = this.shopName;
            panelAnimation.shopNumber = this.shopNumber;
        }
        frameMain.getContentPane().add(panelAnimation, BorderLayout.CENTER);
        panelAnimation.setVisible(true);
        frameMain.repaint();
    }

    public void loadBikesFromBackEnd() {
        concatenatedList = db.getBikeList();
        records = concatenatedList.split("\n");
        setStatusMessage(records.length + " record(s) have been loaded successfully.", Color.GREEN.darker());
    }

    public void displayBikesList() {
        try {
            String[] columnNames = {"Bike Name", "Bike Size"};
            Object[][] data = new Object[records.length][2];
            for (int index = 0; index < records.length; index++) {
                String[] word = records[index].split(" ");
                data[index][0] = word[0];
                data[index][1] = Integer.parseInt(word[1]);
            }
            JTable tableBikes = new JTable(data, columnNames);
            tableBikes.setFillsViewportHeight(true);
            panelWelcomeBody.add(tableBikes.getTableHeader(), BorderLayout.PAGE_START);
            panelWelcomeBody.add(tableBikes, BorderLayout.CENTER);
            tabMain.repaint();
        } catch (NullPointerException e) {
            setStatusMessage("Please click the LOAD DATABASE button.", Color.RED);
        }
    }

    public void searchBrand(String brand) {
        String result = db.checkBrand(brand);
        if (result.length() == 0) {
            setStatusMessage("Sorry, \'" + brand + "\' is not in the list.", Color.RED);
        } else {
            setStatusMessage("Result(s) found: " + result, Color.BLUE);
        }
    }
    
    private void setStatusMessage(String message, Color color){
        labelStatus.setForeground(color);
        labelStatus.setText(message);
        labelStatus.setVisible(true);
    }
}
