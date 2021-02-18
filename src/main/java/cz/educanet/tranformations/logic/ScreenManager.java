package cz.educanet.tranformations.logic;

import cz.educanet.tranformations.logic.models.Coordinate;

import java.util.*;

public class ScreenManager {

    private Set<Coordinate> selectedPoints = new LinkedHashSet<>();
    private TriangleManager line = new TriangleManager();
    boolean[][] myGrid = new boolean[18][24];

    public ScreenManager(){
        cleanGrid();
    }
    public void select(Coordinate coordinate) {
        selectedPoints.add(coordinate);
        System.out.println(selectedPoints.size());
        if(selectedPoints.size() == 3){
            myGrid = line.rasterizeLine(getCoo(0), getCoo(1));
            myGrid = line.rasterizeLine(getCoo(1), getCoo(2));
            myGrid = line.rasterizeLine(getCoo(0), getCoo(2));
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
        for(boolean[] row: myGrid){
            Arrays.fill(row, false);
        }
    }

    public void setGrid(int y, int x){
        myGrid[y][x] = true;
    }


}
