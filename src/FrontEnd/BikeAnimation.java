/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontEnd;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Roy Galet
 */
public class BikeAnimation extends JPanel implements ActionListener {

    Timer animationTimer;
    private final BikeTyre[] bouncingTyre;
    private final BikeRider swingingRider;
    private final int tyresize, initial_X_Position, initial_Y_Position, cableLength;
    private int X_Coordinate, Y_Coordinate;
    private double cableAngle, angleSpeed, angleAcceleration;
    int shopNumber;
    String shopName;
    private final Fireworks firework;

    public BikeAnimation(String shopName, int shopNumber) {
        super();
        this.shopName = shopName;
        this.shopNumber = shopNumber;
        animationTimer = new Timer(10, this);
        bouncingTyre = new BikeTyre[5];
        firework = new Fireworks(120,160, 420, 195);
        tyresize = 45;
        initial_X_Position = 50;
        initial_Y_Position = 0;
        cableLength = 50;
        X_Coordinate = 0;
        Y_Coordinate = 0;
        cableAngle = Math.PI / 4;
        angleSpeed = 0;
        angleAcceleration = 0.0005;
        for (int index = 1; index <= bouncingTyre.length; index++) {
            bouncingTyre[index - 1] = new BikeTyre(this.getWidth() / 2 + tyresize * index + 25, 160 + this.getHeight() / 2 + tyresize * index, tyresize + (int) (Math.random() * 10), 11 + (int) (Math.random() * 15), index);
        }
        swingingRider = new BikeRider(0, 0, shopName, shopNumber);
        swingingRider.riderNumber = this.shopNumber;
        swingingRider.riderName = this.shopName;
        animationTimer.start();
        this.setLayout(null);
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D pencil = (Graphics2D) g;
        drawBackground(pencil);
        firework.draw(pencil);
//        Bouncing tyres at the back
        for (int index = 1; index <= bouncingTyre.length / 2; index++) {
            bouncingTyre[index - 1].draw((Graphics2D) g);
        }
//        Table/Counter
        pencil.setStroke(new BasicStroke(1));
        pencil.setColor(Color.gray.brighter());
        pencil.fillRect(0, this.getHeight() - 200, this.getWidth(), this.getHeight() - 1);
        pencil.setColor(Color.gray);
        pencil.fillRect(0, this.getHeight() - 200, this.getWidth(), 10);
        pencil.setColor(Color.gray.darker());
        pencil.drawRect(0, this.getHeight() - 200, this.getWidth(), 10);
//        Bouncing tyres in front
        for (int index = bouncingTyre.length / 2 + 1; index <= bouncingTyre.length; index++) {
            bouncingTyre[index - 1].draw((Graphics2D) g);
        }
        drawDoor(pencil);
//        Cable holdng the shop sign
        pencil.setStroke(new BasicStroke(3));
        pencil.setColor(Color.gray);
        pencil.drawLine(initial_X_Position + 50, initial_Y_Position, X_Coordinate + 50, Y_Coordinate);
        pencil.drawLine(initial_X_Position + 500, initial_Y_Position, X_Coordinate + 500, Y_Coordinate);
        swingingRider.draw(pencil);
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int index = 1; index <= bouncingTyre.length; index++) {
            animateTyre(bouncingTyre[index - 1]);
        }
        animateRider(swingingRider);
        animateFireworks(firework);
        repaint();
    }
    
    private void animateFireworks(Fireworks firework){
        firework.explode();
    }

    private void animateTyre(BikeTyre tyre) {
        if ((tyre.x - tyre.radius) < 0 || (tyre.x + tyre.radius + tyre.horizontalSpeed) > this.getWidth()) {
            tyre.horizontalSpeed = -tyre.horizontalSpeed;
        }
        if ((tyre.y + tyre.radius + tyre.verticalSpeed) > this.getHeight()) {
            tyre.verticalSpeed = -tyre.verticalSpeed + 1;
        }
        tyre.moveTyre();
    }

    private void animateRider(BikeRider rider) {
        if (cableAngle > Math.PI / 2) {
            angleAcceleration = -1 * Math.abs(angleAcceleration);
        } else {
            angleAcceleration = Math.abs(angleAcceleration);
        }
        angleSpeed += angleAcceleration;
        cableAngle += angleSpeed;
        X_Coordinate = initial_X_Position + (int) (cableLength * Math.cos(cableAngle));
        Y_Coordinate = initial_Y_Position + (int) (cableLength * Math.sin(cableAngle));
        rider.X = X_Coordinate - 45;
        rider.Y = Y_Coordinate - 36;
    }

    private void drawDoor(Graphics2D pencil) {
        pencil.setStroke(new BasicStroke(1));

        pencil.setColor(Color.GRAY);
        pencil.fillRect(0, 150, this.getWidth() - 1, 15);
        pencil.fillRect(200, 165, 15, this.getHeight() - 166);
        pencil.fillRect(450, 165, 15, this.getHeight() - 166);
        pencil.fillRect(215, 165, 20, this.getHeight() - 161);
        pencil.fillRect(430, 165, 20, this.getHeight() - 161);
        pencil.fillRect(215, 165, 235, 20);
        pencil.fillRect(215, this.getHeight() - 20, 235, 20);
//        Transparent white
        pencil.setColor(new Color(223, 223, 223, 127));
        pencil.fillRect(0, 320, 200, 75);
        pencil.fillRect(465, 320, this.getWidth() - 465, 75);
//        Door frame
        pencil.setColor(Color.BLACK);
        pencil.drawRect(0, 150, this.getWidth() - 1, 15);
        pencil.drawRect(200, 165, 15, this.getHeight() - 166);
        pencil.drawRect(450, 165, 15, this.getHeight() - 166);
        pencil.drawRect(215, 165, 235, this.getHeight() - 166);
        pencil.drawRect(235, 185, 195, this.getHeight() - 206);
        pencil.drawLine(215, 165, 235, 185);
        pencil.drawLine(450, 165, 430, 185);
        pencil.drawLine(430, this.getHeight() - 21, 450, this.getHeight() - 1);
        pencil.drawLine(215, this.getHeight() - 1, 235, this.getHeight() - 21);
//        String
        pencil.drawLine(330, 305, 300, 325);
        pencil.drawLine(330, 305, 360, 325);
        pencil.setColor(Color.GRAY);
        pencil.fillRect(225, 166 + (this.getHeight() - 166) / 2, 215, 10);
//        Door Sign
        pencil.setColor(Color.yellow);
        pencil.fillOval(325, 300, 10, 10);
        pencil.fillRect(300, 325, 60, 25);
        pencil.setColor(Color.BLACK);
        pencil.drawRect(225, 166 + (this.getHeight() - 166) / 2, 215, 10);
        pencil.drawOval(325, 300, 10, 10);
        pencil.drawRect(300, 325, 60, 25);
        pencil.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        pencil.drawString("OPEN", 302, 345);
        //Checkered Flag
        int rows = 3, columns = 10;

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                if ((i + j) % 2 == 1) {
                    pencil.setColor(Color.white);

                } else {
                    pencil.setColor(Color.black);
                }
                pencil.fillRect(i * (this.getWidth() / columns), j * 50, this.getWidth() / columns, 50);
            }
        }

    }

    private void drawBackground(Graphics2D pencil) {
        pencil.setPaint(new GradientPaint(0, 0, new Color(1, 60, 176), 0, this.getHeight() / 2, new Color(73, 186, 255)));
        pencil.fillRect(0, 0, this.getWidth(), this.getHeight() - 200);
        pencil.setColor(Color.gray.darker());
        for (int i = 0; i < 10; i++) {
            pencil.drawLine(i * this.getWidth() / 10, 0, i * this.getWidth() / 10, this.getHeight() - 200);
        }
        pencil.setPaint(new GradientPaint(0, 0, new Color(1, 60, 176), 0, this.getHeight() / 2, new Color(73, 186, 255)));
        pencil.fillRect(0, 250, this.getWidth(), 10);
        pencil.setColor(Color.gray.darker());
        pencil.drawRect(0, 250, this.getWidth(), 10);
        pencil.setPaint(new GradientPaint(0, 0, new Color(1, 60, 176), 0, this.getHeight() / 2, new Color(73, 186, 255)));
        pencil.fillRect(0, 350, this.getWidth(), 10);
        pencil.setColor(Color.gray.darker());
        pencil.drawRect(0, 350, this.getWidth(), 10);
    }
}
