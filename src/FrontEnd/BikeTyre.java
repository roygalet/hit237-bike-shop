/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontEnd;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Roy Galet
 */
public class BikeTyre {

    int x, y, radius, size;
    double verticalSpeed, horizontalSpeed, verticalAcceleration;

    public BikeTyre(int x, int y, int radius, int size, double horizontalSpeed) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.radius = radius - size / 2;
        this.horizontalSpeed = horizontalSpeed;
        verticalSpeed = 0;
        verticalAcceleration = 0.1;
    }

    void draw(Graphics2D page) {
        Graphics2D pencil = (Graphics2D) page;
        pencil.setColor(new Color(32, 32, 32));
        pencil.setStroke(new BasicStroke(size));
        pencil.drawOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    public void moveTyre() {
        x += horizontalSpeed;
        y += verticalSpeed;
        verticalSpeed += verticalAcceleration;
    }
}
