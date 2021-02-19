package cz.educanet.tranformations.presentation.canvas;

import cz.educanet.tranformations.Dimensions;
import cz.educanet.tranformations.logic.ScreenManager;
import cz.educanet.tranformations.logic.models.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;

public class Canvas extends JPanel {

    private Dimensions dimensions = new Dimensions();
    private static int SCREEN_WIDTH;
    private static int SCREEN_HEIGHT;

    private final ScreenManager screenManager = new ScreenManager();

    private int cellWidth;
    private int cellHeight;

    public Canvas() {
        SCREEN_WIDTH = dimensions.getWidth();
        SCREEN_HEIGHT = dimensions.getHeight();
        setBackground(Color.BLACK);

        addMouseListener(new CanvasMouseListener((x, y, button) -> {
            Coordinate c = new Coordinate(x / cellWidth, y / cellHeight);

            if (screenManager.getSelectedPoints().size() < 3 && button == MouseEvent.BUTTON1)
                screenManager.select(c);
            else
                screenManager.unselect(c);
        }));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        cellWidth = (getSize().width - 1) / SCREEN_WIDTH;
        cellHeight = getSize().height / SCREEN_HEIGHT;
        Set<Coordinate> vertices = screenManager.getSelectedPoints();

        for (int i = 0; i < SCREEN_WIDTH; i++) {
            for (int j = 0; j < SCREEN_HEIGHT; j++) {
                Coordinate cell = new Coordinate(i, j);

                if (screenManager.isFilledIn(cell)) {
                    g.setColor(screenManager.getColor(cell));
                    g.fillRect(i * cellWidth, j * cellHeight, cellWidth, cellHeight);
                }

                g.setColor(Color.darkGray);
                g.drawRect(i * cellWidth, j * cellHeight, cellWidth, cellHeight);

                if (screenManager.isSelected(cell)) {
                    g.setColor(Color.lightGray);
                    g.fillOval(i * cellWidth + (cellWidth / 2 - 5), j * cellHeight + (cellHeight / 2 - 5), 10, 10);
                }

            }
        }

        vertices.forEach(i -> {
            vertices.forEach(j -> {
                if (!i.equals(j)) { // TODO: Optimize, but meh...
                    g.setColor(Color.lightGray);
                    g.drawLine(i.getX() * cellWidth + (cellWidth / 2), i.getY() * cellHeight + (cellHeight / 2), j.getX() * cellWidth + (cellWidth / 2), j.getY() * cellHeight + (cellHeight / 2));
                }
            });
        });
    }
}
