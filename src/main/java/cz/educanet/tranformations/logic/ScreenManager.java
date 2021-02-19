package cz.educanet.tranformations.logic;

import cz.educanet.tranformations.Dimensions;
import cz.educanet.tranformations.logic.models.Coordinate;

import java.awt.*;
import java.util.*;

public class ScreenManager {

    private Set<Coordinate> selectedPoints = new LinkedHashSet<>();
    private TriangleManager line = new TriangleManager();
    public Color[][] myGrid;
    private Dimensions dimensions = new Dimensions();

    public ScreenManager(){
        myGrid = new Color[dimensions.getHeight()][dimensions.getWidth()];
        cleanGrid();
    }
    public void select(Coordinate coordinate) {
        selectedPoints.add(coordinate);
        System.out.println(selectedPoints.size());
        int[] col0 = {255,0,0};
        int[] col1 = {0,102,255};
        int[] col2 = {0,255,0};
        if(selectedPoints.size() == 3){
            myGrid = line.rasterizeLine(getCoo(0), getCoo(1),col0, col1);
            myGrid = line.rasterizeLine(getCoo(1), getCoo(2),col1, col2);
            myGrid = line.rasterizeLine(getCoo(0), getCoo(2),col0, col2);
            myGrid = line.fillTriangle(myGrid);
        }
    }

    public void unselect(Coordinate coordinate) {
        cleanGrid();
        selectedPoints.remove(coordinate);
    }

    public boolean isSelected(Coordinate coordinate) {
        return selectedPoints.contains(coordinate);
    }

    public Set<Coordinate> getSelectedPoints() {
        return selectedPoints;
    }

    public boolean isFilledIn(Coordinate coordinate) { // TODO: Implement this
        if(myGrid[coordinate.getY()][coordinate.getX()] != null){
            return true;
        } else {
            return false;
        }
    }

    public Color getColor(Coordinate coordinate){
        return myGrid[coordinate.getY()][coordinate.getX()];
    }

    public void print(){
        int y = 0;
        for (Coordinate i : selectedPoints) {
            System.out.println("kolikata je: " + y + " coos: " + i.getX() + ":" + i.getY());
            y++;
        }
    }

    public Coordinate getCoo(int pos){
        int i = 0;
        for(Coordinate coo: selectedPoints){
            if(i == pos){
                return coo;
            }
            i++;
        }
        return null;
    }

    private void cleanGrid(){
        for(Color[] row: myGrid){
            Arrays.fill(row, null);
        }
    }

    public void setGrid(int y, int x, int red, int green, int blue){
        myGrid[y][x] = new Color(red,green,blue);
    }


}
