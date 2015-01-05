/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacmanmaze;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Sashank
 */
public class PMControl implements MouseListener{
    
    private PMModel model;
    private boolean isDrawing;
    private int x1, y1, x2, y2;
    
    
    public PMControl(PMModel m)
    {
        model = m;
        isDrawing = false;
    }
    
    public void update()
    {
        if(isDrawing)
        {
            Point m = MouseInfo.getPointerInfo().getLocation();
            
        }
    }

    public void mouseClicked(MouseEvent e) {
        if(e.getY() > 690)
        {
            if(e.getX() > 640 && e.getX() < 800)
                model.setEditing(false);
            if(e.getX() > 440 && e.getX() < 640)
                model.clear();
            if(e.getX() > 250 && e.getX() < 440)
                model.makeBraidMaze();
            if(e.getX() > 0 && e.getX() < 250)
                model.recursiveBacktrack();
        }

    }

    public void mousePressed(MouseEvent e) {
        if(model.isEditing())
        {
            x1 = (e.getX()-125)/50;
            y1 = (e.getY()-125)/50;
            if(inField(x1, y1))
            {
                isDrawing = true;
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(model.isEditing())
        {
            x2 = (e.getX()-125)/50;
            y2 = (e.getY()-125)/50;
            if(inField(x2, y2))
            {
                model.newWalls(x1, y1, x2, y2);
            }
            isDrawing = false;
        }
    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}
    
    public boolean isDrawing()
    {
        return isDrawing;
    }
    
    public Point getActive()
    {
        return new Point(x1, y1);
    }
    public Point getEnd()
    {
        return MouseInfo.getPointerInfo().getLocation();
    }
    
    private boolean inField(int x, int y)
    {
        return x >= 0 && x <= 9 && y >=0 && y <= 9;
    }
    
}
