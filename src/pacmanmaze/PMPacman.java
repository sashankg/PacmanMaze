/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacmanmaze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Sashank
 */
public class PMPacman {
    
    private double x;
    private double y;
    private int angle;
    private int upDown;
    private boolean isMoving;
    private ArrayList<Point> solution;
    private int dir;
    public PMPacman()
    {
        x = 8;
        y = 8;
        angle = 0;
        dir = 0;
    }
    
    public void paint(Graphics g)
    {
        g.setColor(Color.YELLOW);
        g.fillArc((int)(x*50)+150+5,(int)(y*50)+150+5, 40, 40, 45-angle+dir, 270+angle*2);
    }
    
    public void update() {

        double speed = 0.001;
        if (angle <= 0) {
            upDown = 1;
        } else if (angle > 45) {
            upDown = -1;
        }
        angle += upDown;
        if (Math.abs(solution.get(0).x - x) < 0.001 && Math.abs(solution.get(0).y - y) < 0.001) {
            x = solution.get(0).x;
            y = solution.get(0).y;
            solution.remove(0);
            System.out.println(solution);
            System.out.println(y);
            System.out.println();
        }
        if (solution.size() > 0) {
            Point p = solution.get(0);
            if (p.y > y) {
                y += speed;
                dir = 270;
            } else if (p.y < y) {
                y -= speed;
                dir = 90;
            }
            if (p.x > x) {
                x += speed;
                dir = 0;
            } else if (p.x < x) {
                x -= speed;
                dir = 180;
            }
        } else {
            isMoving = false;
        }
    }
    
    public void setSolution(ArrayList<Point> a)
    {
        System.out.println(a);
        solution = a;
    }
    
    public void startMoving(){
        isMoving = true;
    }
    
    public boolean isMoving()
    {
        return isMoving;
    }
}
