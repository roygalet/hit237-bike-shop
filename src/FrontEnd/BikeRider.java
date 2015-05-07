/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontEnd;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Polygon;

/**
 *
 * @author Roy Galet
 */
public class BikeRider extends Component {
    private Polygon polygon;
    int X, Y, riderNumber;
    String riderName;

    public BikeRider(int x, int y, String riderName, int riderNumber) {
        this.X = x;
        this.Y = y;
        this.riderName = riderName;
        this.riderNumber = riderNumber;
    }

    void draw(Graphics2D page) {
        Graphics2D pencil = (Graphics2D) page;
//        Race Track
        pencil.setColor(new Color(96, 96, 96));
        pencil.setPaint(new GradientPaint(43, 36, Color.gray.darker().darker(), 43, 150, Color.gray, false));
        pencil.fillRect(X + 43, Y + 36, 550, 125);
//        Chicanes
        pencil.setColor(new Color(225, 225, 225));
        pencil.fillRect(X + 43, Y + 36, 550, 12);
        pencil.fillRect(X + 43, Y + 149, 550, 12);
        pencil.setColor(Color.red);
        for(int i=0; i<11; i++){
            pencil.fillRect(X + 43 + i*50, Y + 37, 25, 10);
            pencil.fillRect(X + 43 + i*50, Y + 150, 25, 10);
        }
        pencil.setColor(Color.black);
        pencil.setStroke(new BasicStroke(10));
//        Tyres
        pencil.drawOval(X + 180, Y + 107, 45, 45);
        pencil.drawOval(X + 55, Y + 110, 45, 45);
        pencil.setColor(Color.blue);
        pencil.setStroke(new BasicStroke(1));
//        Chassis
        pencil.fillPolygon(new int[]{X + 192, X + 205, X + 199, X + 182}, new int[]{Y + 101, Y + 129, Y + 132, Y + 107}, 4);
        pencil.setColor(Color.blue.darker());
        pencil.setPaint(new GradientPaint(150, 150, Color.blue.darker().darker(), 200, 200, Color.blue.brighter(), false));
        polygon = new Polygon(new int[]{X + 53, X + 83, X + 87, X + 156, X + 166, X + 181, X + 197, X + 171, X + 175, X + 109, X + 109, X + 67, X + 99, X + 82}, new int[]{Y + 72, Y + 73, Y + 77, Y + 70, Y + 78, Y + 64, Y + 98, Y + 120, Y + 143, Y + 144, Y + 128, Y + 138, Y + 103, Y + 88}, 14);
        pencil.fill(polygon);
//        WindShield
        pencil.setColor(Color.orange);
        pencil.fillPolygon(new int[]{X + 174, X + 201, X + 209, X + 192, X + 157, X + 168, X + 187, X + 188, X + 175}, new int[]{Y + 55, Y + 73, Y + 94, Y + 92, Y + 87, Y + 82, Y + 82, Y + 74, Y + 66}, 9);
//        Helmet
        pencil.setColor(Color.yellow);
        pencil.fillPolygon(new int[]{X + 146, X + 159, X + 159, X + 157, X + 145, X + 134, X + 138}, new int[]{Y + 39, Y + 47, Y + 57, Y + 67, Y + 69, Y + 51, Y + 43}, 7);
        pencil.setColor(Color.black);
        pencil.fillPolygon(new int[]{X + 159, X + 157, X + 147, X + 151}, new int[]{Y + 57, Y + 67, Y + 58, Y + 54}, 4);
//        Body
        pencil.setColor(Color.blue.brighter());
        pencil.fillPolygon(new int[]{X + 132, X + 141, X + 119, X + 88, X + 95, X + 124}, new int[]{Y + 45, Y + 65, Y + 74, Y + 67, Y + 59, Y + 44}, 6);
//        Arms
        pencil.setColor(Color.cyan);
        pencil.fillPolygon(new int[]{X + 129, X + 138, X + 141, X + 162, X + 168, X + 175, X + 170, X + 162, X + 137, X + 125, X + 125}, new int[]{Y + 53, Y + 59, Y + 70, Y + 74, Y + 71, Y + 75, Y + 77, Y + 82, Y + 81, Y + 63, Y + 58}, 11);
//        Legs
        pencil.fillPolygon(new int[]{X + 88, X + 127, X + 134, X + 115, X + 125, X + 124, X + 114, X + 108, X + 102, X + 102, X + 118, X + 94, X + 88, X + 87}, new int[]{Y + 67, Y + 70, Y + 78, Y + 107, Y + 118, Y + 120, Y + 117, Y + 112, Y + 111, Y + 106, Y + 81, Y + 84, Y + 79, Y + 72}, 14);
//        Bike Number
        pencil.setColor(Color.green.brighter());
        pencil.setFont(new Font(Font.SANS_SERIF, Font.BOLD + Font.ITALIC, 36));
        pencil.drawString(String.valueOf( riderNumber), X + 130, Y + 120);
//        Store Name
        pencil.setColor(Color.white);
        pencil.setFont(new Font(Font.SANS_SERIF, Font.BOLD + Font.ITALIC, 48));
        pencil.drawString(riderName + "'s", X + 215, Y + 90);
        pencil.setFont(new Font(Font.SANS_SERIF, Font.BOLD + Font.ITALIC, 36));
        pencil.drawString("Bike Shop", X + 315, Y + 135);
    }
}
