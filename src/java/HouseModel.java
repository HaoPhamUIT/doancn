import java.util.HashSet;

import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;

/** class that implements the Model of Domestic robber application */
public class HouseModel extends GridWorldModel {
    
    // constants for the grid objects
    public static final int FRIDGE = 64;
    public static final int protect  = 32;

    // the grid size
    public static final int GSize = 20;
    
    boolean fridgeOpen   = false; // whether the fridge is open
    boolean carryingBeer = false; // whether the robber is carrying beer
    int sipCount        = 0; // how many sip the protect did
    int availableBeers  = 2; // how many beers are available
    Location                  lFridge;
    
     Location lprotect  = new Location(15,15);

  private String            id = "WorldModel";
    
    // singleton pattern
    protected static HouseModel model = null;
    
    synchronized public static HouseModel create(int w, int h, int nbAgs) {
        if (model == null) {
            model = new HouseModel(w, h, nbAgs);
        }
        return model;
    }
    
    public static HouseModel get() {
        return model;
    }
    
    public static void destroy() {
        model = null;
    }

    private HouseModel(int w, int h, int nbAgs) {
        super(w, h, nbAgs);
        
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String toString() {
        return id;
    }
    public void setlFridge(int x, int y) {
    	lFridge = new Location(x, y);
        data[x][y] = FRIDGE;
    }
    public Location getlFridge() {
        return lFridge;
    }
    static HouseModel world() throws Exception {
    	HouseModel model = HouseModel.create(30, 30, 1);      
    	
    	
        model.setAgPos(0, 20, 15);
        model.setlFridge(25, 25);
        model.add(OBSTACLE, 10, 10);
        model.add(OBSTACLE, 12, 10);
        model.add(OBSTACLE, 11, 10);
        
                  
        return model;
    }


    boolean openFridge() {
        if (!fridgeOpen) {
            fridgeOpen = true;
            return true;
        } else {
            return false;
        }
    }

    boolean closeFridge() {
        if (fridgeOpen) {
            fridgeOpen = false; 
            return true;
        } else {
            return false;
        }
    }  

    boolean moveTowards(Location dest) {
        Location r1 = getAgPos(0);
        if (r1.x < dest.x)        r1.x++;
        else if (r1.x > dest.x)   r1.x--;
        if (r1.y < dest.y)        r1.y++;
        else if (r1.y > dest.y)   r1.y--;
        setAgPos(0, r1); // move the robber in the grid
        
        // repaint the fridge and protect locations
        if (view != null) {
            view.update(lFridge.x,lFridge.y);
            view.update(lprotect.x,lprotect.y);          
        }
        return true;
    }
    
    boolean getBeer() {
        if (fridgeOpen && availableBeers > 0 && !carryingBeer) {
            availableBeers--;
            carryingBeer = true;
            if (view != null)
                view.update(lFridge.x,lFridge.y);
            return true;
        } else {
            return false;
        }
    }
    
    boolean addBeer(int n) {
        availableBeers += n;
        if (view != null)
            view.update(lFridge.x,lFridge.y);
        return true;
    }
    
    boolean handInBeer() {
        if (carryingBeer) {
            sipCount = 10;
            carryingBeer = false;
            if (view != null)
                view.update(lprotect.x,lprotect.y);
            return true;
        } else {
            return false;
        }
    }
    
    boolean sipBeer() {
        if (sipCount > 0) {
            sipCount--;
            if (view != null)
                view.update(lprotect.x,lprotect.y);
            return true;
        } else {
            return false;
        }
    }
}
