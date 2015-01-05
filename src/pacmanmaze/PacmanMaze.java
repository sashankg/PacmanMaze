/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacmanmaze;

import javax.swing.JFrame;

/**
 *
 * @author Sashank
 */
public class PacmanMaze extends JFrame{

    public PacmanMaze()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);
        setResizable(false);
        setTitle("Line Jumper");
        setLocationRelativeTo(null);
        setUndecorated(true);
        
        PMModel m = new PMModel();
        PMControl c = new PMControl(m);
        PMView v = new PMView(m, c, this);
        add(v);
        setVisible(true);
        v.run();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new PacmanMaze();
        
    }
    
}
