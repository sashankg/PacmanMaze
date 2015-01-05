/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacmanmaze;

import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Sashank
 */
public class PMView extends JPanel{
    private PMModel model;
    private PMControl control;
    private JFrame ref;
    
    public PMView(PMModel m, PMControl c, JFrame j)
    {
        model = m;
        setBackground(Color.BLACK);
        control = c;
        addMouseListener(c);
        ref = j;
    }
    
    public void run()
    {
        while(true)
        {
            repaint();

            try {
                Thread.sleep(48);
            } catch (InterruptedException ex) {
                Logger.getLogger(PMView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                int x = (i*50)+150;
                int y = (j*50)+150;
                
                if(model.isEditing())
                {
                    g.setColor(Color.red);
                    g.drawLine(x-2, y, x+2, y);
                    g.drawLine(x, y-2, x, y+2);
                    g.drawString("Perfect Maze", 50, 700);
                    g.drawString("Multi Solution Maze", 250, 700);
                    g.drawString("Clear", 450, 700);
                    g.drawString("Solve Maze", 650, 700);
                    
                }
                else if(model.getPacman().isMoving())
                {
                    model.getPacman().update();
                }
                g.setColor(Color.blue);
                //horizontal walls
                if(i < 9)
                {
                    if(model.getHorizontalWalls()[i][j])
                    {
                        g.drawLine(x, y+3, x+50, y+3);
                        g.drawLine(x, y-3, x+50, y-3);
                    }
                }

                //vertical walls
                if(j < 9)
                {
                    if(model.getVerticalWalls()[i][j])
                    {
                        g.drawLine(x+3, y, x+3, y+50);
                        g.drawLine(x-3, y, x-3, y+50);
                    }
                }
            }
        }

        if(control.isDrawing())
        {
            g.setColor(Color.YELLOW);
            g.fillOval((control.getActive().x*50)+150-2, (control.getActive().y*50)+150-2, 5, 5);
            g.drawLine((control.getActive().x*50)+150, (control.getActive().y*50)+150, control.getEnd().x-ref.getLocationOnScreen().x, control.getEnd().y-ref.getLocationOnScreen().y);
        }
        g.setColor(Color.white);
        g.fillOval(150+20, 150+20, 10, 10);
        model.getPacman().paint(g);

    }
    
}
