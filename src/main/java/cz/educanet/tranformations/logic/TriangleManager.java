package cz.educanet.tranformations.logic;

import cz.educanet.tranformations.Dimensions;
import cz.educanet.tranformations.logic.models.Coordinate;

import java.awt.*;

public class TriangleManager {
	Dimensions dimensions = new Dimensions();
	Color[][] myGrid;

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

	int interpolation;
	int interp1;
	int interp2;
	int[] colCode = new int[3];
	public TriangleManager(){
		myGrid = new Color[dimensions.getHeight()][dimensions.getWidth()];
	}

	public Color[][] rasterizeLine(Coordinate coo1, Coordinate coo2, Color color1, Color color2) {
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

			interpolation = y2 - y1;
			interp1 = interpolation;
			interp2 = 0;
			for(int y = y1; y <= y2; y++){
				myGrid[y][x1] = Color.ORANGE;
				interp1--;
				interp2++;
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

				Color tempColor = color1;
				color1 = color2;
				color2 = tempColor;

			}
			int y = y1;
			interpolation = x2 - x1;
			interp1 = interpolation;
			interp2 = 0;

			if (slope > 0) {
				for (x = x1; x <= x2; x++) {
					funkcniHodnota = slope * (x) + displacement;
					if (funkcniHodnota - y > 0.5) {
						y++;
					}
					myGrid[y][x] = interpolate(color1,color2,interp1,interp2);
					interp1--;
					interp2++;
				}
			} else {
				for (x = x1; x <= x2; x++) {
					funkcniHodnota = slope * (x) + displacement;
					if (funkcniHodnota - y <= 0.5) {
						y--;
					}
					myGrid[y+1][x] = interpolate(color1,color2,interp1,interp2);
					interp1--;
					interp2++;
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

				Color tempColor = color1;
				color1 = color2;
				color2 = tempColor;
			}
			int x = x1;
			interpolation = y2 - y1;
			interp1 = interpolation;
			interp2 = 0;

			if (slope > 0) {
				for (y = y1; y <= y2; y++) {
					argumentFunkce = (y - displacement) / slope;
					if (argumentFunkce - x >= 0.5) {
						x++;
					}
					myGrid[y][x] = interpolate(color1,color2,interp1,interp2);
					interp1--;
					interp2++;
				}
			} else {
				for (y = y1; y <= y2; y++) {
					argumentFunkce = (y - displacement) / slope;
					if (argumentFunkce - x < 0.5) {
						x--;
					}
					myGrid[y][x+1] = interpolate(color1,color2,interp1,interp2);
					interp1--;
					interp2++;
				}
			}
		}
		return myGrid;
	}

	public Color[][] fillTriangle( Color[][] gridInput){
		int firstTile;
		int lastTile;
		boolean autoFill = false;
		for(int y = 0; y < gridInput.length; y++){
			firstTile = -1;
			lastTile = -1;
			autoFill = false;
			for(int x = 0; x < gridInput[y].length; x++){
				if(autoFill){
					interp1--;
					interp2++;
					if(x == lastTile){
						break;
					}
					myGrid[y][x] = interpolate(myGrid[y][firstTile],myGrid[y][lastTile],interp1,interp2);
				} else if (gridInput[y][x] != null){
					if(firstTile > lastTile && gridInput[y][x-1] == null){
						lastTile = x;
						x = firstTile;

						interpolation = lastTile - firstTile;
						interp1 = interpolation;
						interp2 = 0;

						autoFill = true;
					} else {
						firstTile = x;
					}
				}
			}
		}
		return myGrid;
	}

	public Color interpolate(Color c1, Color c2, int x, int y){
		int[] col1 = {c1.getRed(), c1.getGreen(), c1.getBlue()};
		int[] col2 = {c2.getRed(), c2.getGreen(), c2.getBlue()};
		double[] result = new double[3];
		double amount1 = (double) x/interpolation;
		double amount2 = (double) y/interpolation;
		for(int i = 0; i < 3; i++){
			result[i] = amount1*col1[i] + amount2*col2[i];
		}

		return new Color((int) (result[0]), (int) (result[1]), (int) (result[2]));
	}
}