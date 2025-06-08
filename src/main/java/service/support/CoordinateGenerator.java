package service.support;

import domain.model.Coordinate;

import java.util.Random;

/**
 * Генератор случайных координат в заданном диапазоне.
 */
public class CoordinateGenerator {

    private final double minX;
    private final double maxX;
    private final double minY;
    private final double maxY;
    private final Random random = new Random();

    public CoordinateGenerator(double minX, double maxX, double minY, double maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public Coordinate generate() {
        double x = minX + (maxX - minX) * random.nextDouble();
        double y = minY + (maxY - minY) * random.nextDouble();
        return new Coordinate(x, y);
    }
}
