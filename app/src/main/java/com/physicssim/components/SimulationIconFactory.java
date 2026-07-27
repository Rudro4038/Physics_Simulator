package com.physicssim.components;

import com.physicssim.model.SimulationType;
import com.physicssim.theme.AppTheme;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public final class SimulationIconFactory {

    private SimulationIconFactory() {
    }

    public static StackPane create(SimulationType type) {
        return switch (type) {
            case PENDULUM -> createPendulumIcon();
            case WORK_POWER_ENERGY -> createWorkPowerEnergyIcon();
            case KINEMATICS -> createKinematicsIcon();
            case GRAVITY -> createOrbitIcon();
            case VECTOR -> createVectorIcon();
            case ELECTRICITY -> createElectricityIcon();
            case ATOMIC_NUCLEAR -> createAtomicNuclearIcon();
        };  
    }

    private static StackPane createPendulumIcon() {
        Line topBar = new Line(25, 18, 95, 18);
        topBar.setStroke(AppTheme.ICON_PRIMARY);
        topBar.setStrokeWidth(5);

        Line centerString = new Line(60, 18, 60, 72);
        centerString.setStroke(AppTheme.ICON_PRIMARY);
        centerString.setStrokeWidth(3.5);

        Line leftString = new Line(60, 18, 36, 64);
        leftString.setStroke(AppTheme.ICON_PRIMARY);
        leftString.setStrokeWidth(3.5);

        Line rightString = new Line(60, 18, 84, 64);
        rightString.setStroke(AppTheme.ICON_LIGHT);
        rightString.setStrokeWidth(3.5);

        Circle leftBall = new Circle(36, 64, 8, AppTheme.ICON_MID);
        Circle centerBall = new Circle(60, 72, 8, AppTheme.ICON_MID);
        Circle rightBall = new Circle(84, 64, 8, AppTheme.ICON_LIGHT);

        return new StackPane(new Group(topBar, centerString, leftString, rightString, leftBall, centerBall, rightBall));
    }

    private static StackPane createMechanicsIcon() {
        Circle ball = new Circle(68, 48, 22, AppTheme.ICON_MID);
        Line ground = new Line(28, 74, 96, 74);
        ground.setStroke(AppTheme.ICON_PRIMARY);
        ground.setStrokeWidth(4);

        Arc motion1 = new Arc(50, 48, 20, 20, 112, 46);
        motion1.setFill(Color.TRANSPARENT);
        motion1.setStroke(AppTheme.ICON_MID);
        motion1.setStrokeWidth(4);

        Arc motion2 = new Arc(42, 48, 30, 30, 115, 34);
        motion2.setFill(Color.TRANSPARENT);
        motion2.setStroke(AppTheme.ICON_MID);
        motion2.setStrokeWidth(3);

        Line trail1 = new Line(20, 42, 30, 42);
        trail1.setStroke(AppTheme.ICON_MID);
        trail1.setStrokeWidth(4);

        Line trail2 = new Line(18, 54, 26, 54);
        trail2.setStroke(AppTheme.ICON_MID);
        trail2.setStrokeWidth(4);

        return new StackPane(new Group(ball, ground, motion1, motion2, trail1, trail2));
    }

    private static StackPane createOrbitIcon() {
        Circle planet = new Circle(60, 52, 22, AppTheme.ICON_MID);
        Arc ring = new Arc(60, 52, 40, 22, 0, 360);
        ring.setFill(Color.TRANSPARENT);
        ring.setStroke(AppTheme.ICON_PRIMARY);
        ring.setStrokeWidth(4);
        ring.setRotate(-24);

        Circle moon = new Circle(92, 42, 5.5, AppTheme.ICON_ACCENT);
        moon.setTranslateY(-2);

        return new StackPane(new Group(ring, planet, moon));
    }

    private static StackPane createVectorIcon() {
        Line yAxis = new Line(28, 18, 28, 82);
        yAxis.setStroke(AppTheme.ICON_PRIMARY);
        yAxis.setStrokeWidth(4);

        Line xAxis = new Line(28, 82, 94, 82);
        xAxis.setStroke(AppTheme.ICON_PRIMARY);
        xAxis.setStrokeWidth(4);

        Line vector = new Line(28, 82, 74, 34);
        vector.setStroke(AppTheme.ICON_ACCENT);
        vector.setStrokeWidth(4);

        Line arrowLeft = new Line(74, 34, 64, 38);
        arrowLeft.setStroke(AppTheme.ICON_ACCENT);
        arrowLeft.setStrokeWidth(4);

        Line arrowRight = new Line(74, 34, 70, 44);
        arrowRight.setStroke(AppTheme.ICON_ACCENT);
        arrowRight.setStrokeWidth(4);

        Circle origin = new Circle(28, 82, 5, AppTheme.ICON_PRIMARY);

        return new StackPane(
                new Group(
                        yAxis,
                        xAxis,
                        vector,
                        arrowLeft,
                        arrowRight,
                        origin
                )
        );
    }

    private static StackPane createElectricityIcon() {
        Line wire1 = new Line(18, 48, 36, 48);
        wire1.setStroke(AppTheme.ICON_PRIMARY);
        wire1.setStrokeWidth(3);

        Line plate1 = new Line(40, 36, 40, 60);
        plate1.setStroke(AppTheme.ICON_MID);
        plate1.setStrokeWidth(4);

        Line plate2 = new Line(50, 42, 50, 54);
        plate2.setStroke(AppTheme.ICON_MID);
        plate2.setStrokeWidth(2);

        Line r1 = new Line(56, 48, 64, 40);
        r1.setStroke(AppTheme.ICON_MID);
        r1.setStrokeWidth(3);
        Line r2 = new Line(64, 40, 72, 56);
        r2.setStroke(AppTheme.ICON_MID);
        r2.setStrokeWidth(3);
        Line r3 = new Line(72, 56, 80, 40);
        r3.setStroke(AppTheme.ICON_MID);
        r3.setStrokeWidth(3);

        Line wire2 = new Line(80, 48, 96, 48);
        wire2.setStroke(AppTheme.ICON_PRIMARY);
        wire2.setStrokeWidth(3);

        Circle plus = new Circle(44, 28, 3, AppTheme.ICON_ACCENT);
        Circle minus = new Circle(44, 76, 3, AppTheme.ICON_LIGHT);

        return new StackPane(new Group(wire1, plate1, plate2, r1, r2, r3, wire2, plus, minus));
    }

    private static StackPane createKinematicsIcon() {
        Circle ball = new Circle(50, 48, 12, AppTheme.ICON_MID);

        Line velocityLine = new Line(62, 48, 88, 48);
        velocityLine.setStroke(AppTheme.ICON_PRIMARY);
        velocityLine.setStrokeWidth(3);
        Line velocityArrow1 = new Line(88, 48, 80, 40);
        velocityArrow1.setStroke(AppTheme.ICON_PRIMARY);
        velocityArrow1.setStrokeWidth(3);
        Line velocityArrow2 = new Line(88, 48, 80, 56);
        velocityArrow2.setStroke(AppTheme.ICON_PRIMARY);
        velocityArrow2.setStrokeWidth(3);

        Line accelLine = new Line(50, 48, 70, 28);
        accelLine.setStroke(AppTheme.ICON_ACCENT);
        accelLine.setStrokeWidth(3);
        Line accelArrow1 = new Line(70, 28, 62, 26);
        accelArrow1.setStroke(AppTheme.ICON_ACCENT);
        accelArrow1.setStrokeWidth(3);
        Line accelArrow2 = new Line(70, 28, 68, 34);
        accelArrow2.setStroke(AppTheme.ICON_ACCENT);
        accelArrow2.setStrokeWidth(3);

        Line ground = new Line(18, 72, 102, 72);
        ground.setStroke(AppTheme.ICON_PRIMARY);
        ground.setStrokeWidth(3);

        return new StackPane(new Group(ground, ball, velocityLine, velocityArrow1, velocityArrow2, accelLine, accelArrow1, accelArrow2));
    }

    private static StackPane createAtomicNuclearIcon() {
        Circle nucleus = new Circle(60, 48, 12, AppTheme.ICON_MID);

        Arc orbit1 = new Arc(60, 48, 30, 30, 0, 360);
        orbit1.setFill(Color.TRANSPARENT);
        orbit1.setStroke(AppTheme.ICON_PRIMARY);
        orbit1.setStrokeWidth(3);

        Arc orbit2 = new Arc(60, 48, 30, 30, 0, 360);
        orbit2.setFill(Color.TRANSPARENT);
        orbit2.setStroke(AppTheme.ICON_PRIMARY);
        orbit2.setStrokeWidth(3);
        orbit2.setRotate(60);

        Arc orbit3 = new Arc(60, 48, 30, 30, 0, 360);
        orbit3.setFill(Color.TRANSPARENT);
        orbit3.setStroke(AppTheme.ICON_PRIMARY);
        orbit3.setStrokeWidth(3);
        orbit3.setRotate(120);

        Circle electron1 = new Circle(90, 48, 5, AppTheme.ICON_ACCENT);
        Circle electron2 = new Circle(60, 18, 5, AppTheme.ICON_ACCENT);
        Circle electron3 = new Circle(30, 48, 5, AppTheme.ICON_ACCENT);

        return new StackPane(new Group(orbit1, orbit2, orbit3, nucleus, electron1, electron2, electron3));
    }

    private static StackPane createWorkPowerEnergyIcon() {
        javafx.scene.shape.Rectangle box = new javafx.scene.shape.Rectangle(40, 38, 40, 24);
        box.setFill(AppTheme.ICON_MID);
        box.setStroke(AppTheme.ICON_ACCENT);
        box.setStrokeWidth(2);

        Line dispLine = new Line(85, 50, 105, 50);
        dispLine.setStroke(AppTheme.ICON_ACCENT);
        dispLine.setStrokeWidth(3);
        Line dispArrow1 = new Line(105, 50, 97, 44);
        dispArrow1.setStroke(AppTheme.ICON_ACCENT);
        dispArrow1.setStrokeWidth(3);
        Line dispArrow2 = new Line(105, 50, 97, 56);
        dispArrow2.setStroke(AppTheme.ICON_ACCENT);
        dispArrow2.setStrokeWidth(3);

        Line forceLine = new Line(60, 50, 78, 32);
        forceLine.setStroke(AppTheme.ICON_PRIMARY);
        forceLine.setStrokeWidth(3);
        Line forceArrow1 = new Line(78, 32, 70, 30);
        forceArrow1.setStroke(AppTheme.ICON_PRIMARY);
        forceArrow1.setStrokeWidth(3);
        Line forceArrow2 = new Line(78, 32, 76, 38);
        forceArrow2.setStroke(AppTheme.ICON_PRIMARY);
        forceArrow2.setStrokeWidth(3);

        Line ground = new Line(18, 70, 102, 70);
        ground.setStroke(AppTheme.ICON_PRIMARY);
        ground.setStrokeWidth(3);

        return new StackPane(new Group(ground, box, dispLine, dispArrow1, dispArrow2, forceLine, forceArrow1, forceArrow2));
    }
}
