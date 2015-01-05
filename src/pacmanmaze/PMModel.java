/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacmanmaze;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Sashank
 */
public class PMModel {
    private boolean[][] hWalls;
    private boolean[][] vWalls;
    private boolean isEditing;
    private PMPacman pacman;
    private ArrayList<Point> recurFringe;
    public PMModel()
    {
        isEditing = true;
        hWalls = new boolean[9][10];
        vWalls = new boolean[10][9];
        for(int i = 0; i < 9; i++)
        {
            hWalls[i][0] = true;
            hWalls[i][9] = true;
            vWalls[0][i] = true;
            vWalls[9][i] = true;
        }
        pacman = new PMPacman();        
    }
    
    public boolean[][] getHorizontalWalls()
    {
        return hWalls;
    }
    
    public boolean[][] getVerticalWalls()
    {
        return vWalls;
    }
    
    public void newWalls(int x1, int y1, int x2, int y2)
    {
        if(y1 == y2)
        {
            for(int i = 0; i < Math.abs(x1-x2); i ++)
            {
                if(x1 < x2)
                {
                    hWalls[x1+i][y1] = !hWalls[x1+i][y1];
                }
                else
                {
                    hWalls[x2+i][y1] = !hWalls[x2+i][y1];
                }
            }
        }
        if(x1 == x2)
        {
            for(int i = 0; i < Math.abs(y1-y2); i++)
            {
                if(y1 < y2)
                {
                    vWalls[x1][y1+i] = !vWalls[x1][y1+i];
                }
                else
                {
                    vWalls[x1][y2+i] = !vWalls[x1][y2+i];
                }
            }
        }
        for(int i = 0; i < 9; i++)
        {
            hWalls[i][0] = true;
            hWalls[i][9] = true;
            vWalls[0][i] = true;
            vWalls[9][i] = true;
        }
    }
    public boolean isEditing()
    {
        return isEditing;
    }
    public void setEditing(boolean b)
    {
        if(!b)
        {
            pacman.setSolution(getSolution());
            pacman.startMoving();
        }
        isEditing = b;
    }
    
    public PMPacman getPacman()
    {
        return pacman;
    }
    
    public ArrayList<Point> getPossibleMoves(int x, int y)
    {
        ArrayList<Point> o = new ArrayList<>();
        if(x > 0 && !vWalls[x][y])
        {
            o.add(new Point(x-1,y));
        }
        if(x < 8 && !vWalls[x+1][y])
        {
            o.add(new Point(x+1,y));
        }
        if(y > 0 && !hWalls[x][y])
        {
            o.add(new Point(x, y-1));
        }
        if( y < 8 && !hWalls[x][y+1])
        {
            o.add(new Point(x, y+1));
        }
        return o;
    }
    
    public ArrayList<Point> getSolution() {
        ArrayList<ArrayList<Point>> fringe = new ArrayList<>();
        ArrayList<Point> t = new ArrayList<>();
        t.add(new Point(8, 8));
        fringe.add(t);
        ArrayList<Point> tested = new ArrayList<>();
        try {
            while (!didWin(fringe.get(0).get(fringe.get(0).size() - 1))) {

                ArrayList<Point> pop = fringe.remove(0);
                for (Point p : getPossibleMoves(pop.get(pop.size() - 1).x, pop.get(pop.size() - 1).y)) {
                    ArrayList<Point> temp = new ArrayList<>(pop);
                    temp.add(p);
                    if (!tested.contains(p)) {
                        fringe.add(temp);
                        tested.add(p);
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }

        return fringe.get(0);
    }
    
    public boolean didWin(Point p)
    {
        return p.equals(new Point(0,0));
    }
    
    public void makeBraidMaze()
    {
        allTrue();
        while(getDeadEnds().size() > 0)
        {
            ArrayList<Point> temp = getDeadEnds();
            Point p = temp.get((int)(temp.size()*Math.random()));
            breakWalls(p, (int)(Math.random()*4));
        }
        for(int i = 0; i < 9; i++)
        {
            hWalls[i][0] = true;
            hWalls[i][9] = true;
            vWalls[0][i] = true;
            vWalls[9][i] = true;
        }
        if(getSolution()==null)
        {
            makeBraidMaze();
        }
    }
    
    public void breakWalls(Point p, int d)
    {
        if(d == 0 && hWalls[p.x][p.y]) hWalls[p.x][p.y] = false;
        if(d == 1 && hWalls[p.x][p.y+1]) hWalls[p.x][p.y+1] = false;
        if(d == 2 && vWalls[p.x][p.y]) vWalls[p.x][p.y] = false;
        if(d == 3 && vWalls[p.x+1][p.y]) vWalls[p.x+1][p.y] = false;
    }
    
    public void recursiveBacktrack()
    {
        allTrue();
        Point curCell = new Point(0, 0);
        ArrayList<Point> visitedCells = new ArrayList<>();
        visitedCells.add(curCell);
        ArrayList<Point> fringe = new ArrayList<>();
        while(visitedCells.size() < 81)
        {
            ArrayList<Point> neighbors = getWalledNeighbors(curCell.x, curCell.y, visitedCells);
            if(!neighbors.isEmpty())
            {
                Point p = neighbors.get((int)(Math.random()*neighbors.size()));
                fringe.add(0, curCell);
                if(p.x == curCell.x)
                {
                    if(p.y > curCell.y && hWalls[p.x][p.y]) hWalls[p.x][p.y] = false;
                    if(p.y < curCell.y && hWalls[p.x][curCell.y]) hWalls[p.x][p.y+1] = false;
                }
                else if(p.y == curCell.y)
                {
                    if(p.x > curCell.x && vWalls[p.x][p.y]) vWalls[p.x][p.y] = false;
                    if(p.x < curCell.x && vWalls[p.x+1][p.y]) vWalls[p.x+1][p.y] = false;
                }
                curCell = p;
                visitedCells.add(p);
            }
            else if(!fringe.isEmpty())
            {
                curCell = fringe.remove(0);  
            }
            else if(visitedCells.size() < 81)
            {
                Point p = new Point((int)(Math.random()*10), (int)(Math.random()*10));
                while(visitedCells.contains(p))
                {
                    p = new Point((int)(Math.random()*10), (int)(Math.random()*10));
                }
                curCell = p;
                visitedCells.add(curCell);
            }
        }
    }
    
    private void allTrue()
    {
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                if(i < 9) hWalls[i][j] = true;
                if(j < 9) vWalls[i][j] = true;
            }
        }
    }
    
    public ArrayList<Point> getDeadEnds()
    {
        ArrayList<Point> o = new ArrayList<>();
        for(int x = 0; x < 9; x ++)
        {
            for(int y = 0; y < 9; y++)
            {
                int walls = 0;
                if(hWalls[x][y]) walls++;
                if(hWalls[x][y+1]) walls++;
                if(vWalls[x][y]) walls++;
                if(vWalls[x+1][y]) walls++;
                if(walls > 2) o.add(new Point(x,y));
            } 
        }
        return o;
    }
    
    
    public ArrayList<Point> getWalledNeighbors(int x, int y, ArrayList<Point> c)
    {
        ArrayList<Point> o = new ArrayList<>();
        if(x > 0 && vWalls[x][y] && !c.contains(new Point(x-1, y)))
        {
            o.add(new Point(x-1,y));
        }
        if(x < 8 && vWalls[x+1][y] && !c.contains(new Point(x+1, y)))
        {
            o.add(new Point(x+1,y));
        }
        if(y > 0 && hWalls[x][y] && !c.contains(new Point(x, y-1)))
        {
            o.add(new Point(x, y-1));
        }
        if( y < 8 && hWalls[x][y+1] && !c.contains(new Point(x, y+1)))
        {
            o.add(new Point(x, y+1));
        }
        return o;
    }
    
    public void clear()
    {
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                if(i < 9) hWalls[i][j] = false;
                if(j < 9) vWalls[i][j] = false;
            }
        }
        for(int i = 0; i < 9; i++)
        {
            hWalls[i][0] = true;
            hWalls[i][9] = true;
            vWalls[0][i] = true;
            vWalls[9][i] = true;
        }
    }
}
