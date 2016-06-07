import jason.environment.grid.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Hashtable;
    
/** class that implements the View of Domestic robber application */
public class HouseView extends GridWorldView {

    HouseModel hmodel;
    
    public HouseView(HouseModel model) {
        super(model, "Domestic robber", 700);
        hmodel = model;
        defaultFont = new Font("Arial", Font.BOLD, 16); // change default font
        setVisible(true);
        repaint();
    }

    /** draw application objects */
    @Override
    public void draw(Graphics g, int x, int y, int object) {
        
        switch (object) {
        case HouseModel.FRIDGE:  drawGold(g, x, y);  break;   
        case HouseModel.protect:   drawDepot(g, x, y);  break;
      
        }
        
    }

    @Override
    public void drawAgent(Graphics g, int x, int y, Color c, int id) {
    	Color idColor = Color.black;
        Location lrobber = hmodel.getAgPos(0);
        if (!lrobber.equals(hmodel.lprotect) && !lrobber.equals(hmodel.lFridge)) {
        	idColor= Color.yellow;
        	super.drawAgent(g, x, y, idColor, -1);
        	}
            if (hmodel.carryingBeer)
            	{
            	idColor = Color.black;
            	super.drawAgent(g, x, y, Color.orange, -1);
            	}
            
            
            //g.setColor(idColor);
            super.drawString(g, x, y, defaultFont, "robber");
        }


    
    public void drawDepot(Graphics g, int x, int y) {
        g.setColor(Color.gray);
        g.fillRect(x * cellSizeW, y * cellSizeH, cellSizeW, cellSizeH);
        g.setColor(Color.pink);
        g.drawRect(x * cellSizeW + 2, y * cellSizeH + 2, cellSizeW - 4, cellSizeH - 4);
        g.drawLine(x * cellSizeW + 2, y * cellSizeH + 2, (x + 1) * cellSizeW - 2, (y + 1) * cellSizeH - 2);
        g.drawLine(x * cellSizeW + 2, (y + 1) * cellSizeH - 2, (x + 1) * cellSizeW - 2, y * cellSizeH + 2);
    }

    public void drawGold(Graphics g, int x, int y) {
        g.setColor(Color.yellow);
        g.drawRect(x * cellSizeW + 2, y * cellSizeH + 2, cellSizeW - 4, cellSizeH - 4);
        int[] vx = new int[4];
        int[] vy = new int[4];
        vx[0] = x * cellSizeW + (cellSizeW / 2);
        vy[0] = y * cellSizeH;
        vx[1] = (x + 1) * cellSizeW;
        vy[1] = y * cellSizeH + (cellSizeH / 2);
        vx[2] = x * cellSizeW + (cellSizeW / 2);
        vy[2] = (y + 1) * cellSizeH;
        vx[3] = x * cellSizeW;
        vy[3] = y * cellSizeH + (cellSizeH / 2);
        g.fillPolygon(vx, vy, 4);
    }
   
  
}
