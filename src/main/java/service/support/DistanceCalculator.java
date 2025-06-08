package service.support;

import domain.model.Coordinate;

/**
 * Утилита для расчёта расстояния между координатами
 */
public class DistanceCalculator {

    public static double calculate(Coordinate a, Coordinate b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
