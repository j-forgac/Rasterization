package cz.educanet.tranformations.logic;

import cz.educanet.tranformations.logic.models.Coordinate;

public class TriangleManager {
	boolean[][] myGrid = new boolean[18][24];

	int x;
	int x1;
	int x2;

	int y;
	int y1;
	int y2;

	double slope;
	double displacement;
	double funkcniHodnota;
	double argumentFunkce;

	public TriangleManager(){
	}

	public boolean[][] rasterizeLine(Coordinate coo1, Coordinate coo2) {
		x1 = coo1.getX();
		x2 = coo2.getX();

		y1 = coo1.getY();
		y2 = coo2.getY();

		if (x1 == x2) {
			if (y1 > y2) {
				int temp = y1;
				y1 = y2;
				y2 = temp;
			}

			for(int y = y1; y <= y2; y++){
				myGrid[y][x1] = true;
			}
		}else {
			slope = ((double) y1 - y2) / (x1 - x2);
		}
		displacement = (double) y1 - slope * x1;

		if (Math.abs(slope) <= 1) {
			if (x1 > x2) {
				int temp = x1;
				x1 = x2;
				x2 = temp;

				temp = y1;
				y1 = y2;
				y2 = temp;

			}
			int y = y1;
			if (slope > 0) {
				for (x = x1; x <= x2; x++) {
					funkcniHodnota = slope * (x) + displacement;
					if (funkcniHodnota - y > 0.5) {
						y++;
					}
					myGrid[y][x] = true;
				}
			} else {
				for (x = x1; x <= x2; x++) {
					funkcniHodnota = slope * (x) + displacement;
					if (funkcniHodnota - y <= 0.5) {
						y--;
					}
					myGrid[y + 1][x] = true;
				}
			}
		} else {
			if (y1 > y2) {
				int temp = x1;
				x1 = x2;
				x2 = temp;

				temp = y1;
				y1 = y2;
				y2 = temp;
			}
			int x = x1;
			if (slope > 0) {
				for (y = y1; y <= y2; y++) {
					argumentFunkce = (y - displacement) / slope;
					if (argumentFunkce - x > 0.5) {
						x++;
					}
					myGrid[y][x] = true;
				}
			} else {
				for (y = y1; y <= y2; y++) {
					argumentFunkce = (y - displacement) / slope;
					if (argumentFunkce - x <= 0.5) {
						x--;
					}
					myGrid[y][x + 1] = true;
				}
			}
		}
		return myGrid;
	}

	public boolean[][] fillTriangle( boolean[][] gridInput){
		int firstTile;
		int lastTile;
		boolean autoFill = false;
		for(int y = 0; y < gridInput.length; y++){
			firstTile = -1;
			lastTile = -1;
			autoFill = false;
			for(int x = 0; x < gridInput[y].length; x++){
				if(autoFill){
					if(x == lastTile){
						break;
					}
					myGrid[y][x] = true;
				} else if (gridInput[y][x]){
					myGrid[y][x] = true;
					if(firstTile > lastTile && !gridInput[y][x-1]){
						lastTile = x;
						x = firstTile;
						autoFill = true;
					} else {
						firstTile = x;
					}
				}
			}
		}
		return myGrid;
	}
}