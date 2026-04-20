package trash;
// --- THE INTERFACE: Defines what every simulation must do ---

import main.View;

interface Simulation {
    void update();
    void render(GraphicsContext gc);
}

// --- CONCRETE MODELS: Different physics behaviors ---
class GravitySim implements Simulation {
    public void update() { /* Gravity math */ }
    public void render(GraphicsContext gc) { gc.fillOval(10, 10, 20, 20); }
}

class ElasticSim implements Simulation {
    public void update() { /* Collision math */ }
    public void render(GraphicsContext gc) { gc.fillRect(50, 50, 30, 30); }
}

// --- THE CONTROLLER: Manages Context Switching ---
public class PhysicsController extends Application {
    private Simulation currentSim = new GravitySim(); // Start with Gravity

    @Override
    public void start(Stage stage) {
        View view = new View();
        
        // Navigation: Switching context based on UI input
        view.gravityBtn.setOnAction(e -> currentSim = new GravitySim());
        view.elasticBtn.setOnAction(e -> currentSim = new ElasticSim());

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // The Controller doesn't care which sim is active
                currentSim.update(); 
                view.clear();
                currentSim.render(view.getGC()); 
            }
        }.start();
    }
}