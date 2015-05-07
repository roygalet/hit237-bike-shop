/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontEnd;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

/**
 *
 * @author seitstudent
 */
public class Fireworks extends Component {

    int left, top, width, height, duration;
    double x, y, verticalAcceleration = 0.01;
    Splint[] splint = new Splint[200];
    Color color;

    public Fireworks(int x, int y, int width, int height) {
        this.x = x;
        this.left = x;
        this.y = y;
        this.top = y;
        this.width = width;
        this.height = height;
        duration = 0;
        for (int index = 0; index < splint.length; index++) {
            splint[index] = new Splint(x, y);
        }
        color = getRandomColor();
    }
    
    private Color getRandomColor(){
        int colorChooser = (int) (Math.random() * 5);
        if (colorChooser == 1) {
            return Color.CYAN;
        } else if (colorChooser == 2) {
            return Color.PINK;
        } else if (colorChooser == 3) {
            return Color.ORANGE;
        } else if (colorChooser == 4) {
            return Color.GREEN.brighter();
        } else {
            return Color.yellow;
        }
    }

    void draw(Graphics2D page) {
        Graphics2D pencil = (Graphics2D) page;

        pencil.setPaint(new GradientPaint(43, top, Color.darkGray.darker(), 43, top + height, Color.gray.darker().darker(), false));
        pencil.fillRect(left, top, width, height);
        
        pencil.setColor(color);
        for (int index = 0;
                index < splint.length;
                index++) {
            if (splint[index].x > this.left && splint[index].x < this.left + this.width && splint[index].y > this.top && splint[index].y < this.top + this.height) {
                pencil.drawLine((int) splint[index].prevX, (int) splint[index].prevY, (int) splint[index].x, (int) splint[index].y);
            }
        }

        pencil.setStroke(
                new BasicStroke(3));
        pencil.setColor(Color.gray);

        pencil.drawRect(left, top, width, height);
    }

    public void explode() {
        if (duration > 0 && duration < 70) {
            for (int index = 0; index < splint.length; index++) {
                splint[index].move();
            }

        } else if (duration >= 20) {
            this.x = Math.random() * width + left;
            this.y = Math.random() * height / 2 + top;
            for (int index = 0; index < splint.length; index++) {
                splint[index] = null;
                splint[index] = new Splint(this.x, this.y);
            }
            duration = -20;
            color = getRandomColor();
        }
        duration++;

    }

    private class Splint {

        double prevX, prevY;
        double x, y, angle, force, xSpeed, ySpeed;

        public Splint(double x, double y) {
            this.x = x;
            this.y = y;
            this.prevX = x;
            this.prevY = y;
            this.angle = Math.random() * 360;
            this.force = Math.random() * 1.5;
            this.xSpeed = force * Math.cos(angle * 3.1416 / 180);
            this.ySpeed = force * Math.sin(angle * 3.1416 / 180);
        }

        public void move() {
            this.prevX = this.x;
            this.prevY = this.y;
            this.x = this.x + this.xSpeed;
            this.y = this.y + this.ySpeed;
            this.ySpeed = this.ySpeed + verticalAcceleration;
        }
    }
}
