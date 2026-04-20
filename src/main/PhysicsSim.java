package main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
// import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import view.utils.Text;

import model.Ball;
import model.FrictionSimulation;
import model.ISimulation;



public class PhysicsSim extends Application {
    private ISimulation currentSim = new FrictionSimulation(); // Start with Gravity


    @Override
    public void start(Stage stage) {


        
       

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                currentSim.update();
                
            }
        };


        Button pauseButton = new Button("pause");

        // action event
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                text.setContent("button selected    ");
                running = true;
            }
        };

      
        // Button toggles the timer
        pauseButton.setOnAction(e -> {
            if (running) {
                timer.stop();
                text.setContent("Simulation paused");
            } else {
                timer.start();
                text.setContent("Simulation running");
            }
            running = !running; // flip the state
        });
        root.getChildren().add(pauseButton);


       

        root.getChildren().add(text.getView());


        stage.setTitle("Physics Engine v1.0");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}