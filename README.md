# Physics Simulator

Welcome to the **Physics Simulator** (internally structured as `Physics_Simulator` ), a highly interactive and modular desktop application designed to visualize, simulate, and calculate various physics and mathematical phenomena.

From classical mechanics to quantum transitions, this application provides an intuitive graphical playground to help students and enthusiasts explore the laws of nature.

---

## 🚀 Features

The application is structured around six core thematic physics modules:

### 1. Kinematics & Mechanics

* **Distance & Displacement:** Visualize the difference between scalar distance and vector displacement.
* **Speed, Velocity, & Acceleration:** Observe real-time motion graphs driven by custom equations.
* **Projectile Motion:** Simulate trajectories with adjustable launch angles, initial velocities, and gravity.
* **Free Fall:** Explore gravitational acceleration without air resistance.
* **Hooke's Law & Elasticity:** Calculate spring constant ($k$), force ($F$), and extension ($x$) with interactive spring visualizations.
* **Momentum Calculator:** Simulate elastic and inelastic collisions.

### 2. Electricity

* **Ohm's Law:** Analyze the direct relationship between Voltage ($V$), Current ($I$), and Resistance ($R$).
* **Circuit Simulators:** Construct and analyze both **Series** and **Parallel** circuits in real time.
* **Kirchhoff's Current Law (KCL):** Visualize current distribution at junction nodes.
* **Electric Power:** Calculate and visualize power dissipation across different components.

### 3. Gravity & Space Physics

* **N-Body Simulator:** Simulate orbital mechanics and gravitational attraction between multiple cosmic bodies.
* **Interactive Gravity Canvas:** Adjust planetary masses and velocities to observe real-time orbital paths.

### 4. Vector Mathematics

* **Vector Addition:** Visually add multiple vectors and calculate the resultant vector.
* **Dot & Cross Products:** Visualize 2D and 3D vector multiplications and their physical geometric meanings.
* **Boat & River Simulator:** Explore relative velocity and vector drift by navigating a boat across a moving current.

### 5. Atomic & Nuclear Physics

* **Bohr Model:** Visualize electron orbits, shells, and energy levels.
* **Electron Transition:** Simulate photon emission and absorption when electrons jump between energy states.
* **Rutherford Scattering:** Simulate alpha particles firing at a gold foil to observe nuclear scattering.
* **Half-Life Simulator:** Watch unstable atomic nuclei decay statistically over time.
* **Nuclear Fission:** Trigger chain reactions and monitor neutron releases.

### 6. Pendulum Motion

* **Harmonic Motion:** Simulate a classic pendulum with customizable length, mass, and gravity.
* **Real-time Plotting:** View live phase diagrams and energy tracking charts (kinetic vs. potential energy).

---

## 🏗️ Architecture & Project Structure

The project is built on a clean, modular architecture separating layout presentation, application routing, and physical mathematical modeling.

```text
phy_sim/
├── app/
│   ├── src/main/java/com/physicssim/
│   │   ├── app/                 # Main app shell & bootstrap entry point
│   │   ├── components/          # Reusable UI elements (custom buttons, headers, cards)
│   │   ├── features/            # View classes grouped by physics category (UI Layer)
│   │   ├── model/               # Mathematical engine and state models (Logic Layer)
│   │   ├── navigation/          # Controller for handling view swaps
│   │   └── theme/               # Global styling config (AppTheme)
│   └── src/main/resources/css/  # CSS files matching view package structures

```

---

## 🛠️ Requirements & Configuration

* **Java Development Kit (JDK):** Version 17 or higher is recommended.
* 
**Gradle:** Handled automatically via the included Gradle Wrapper.



### Key Build Settings

* **Build Optimization:** Gradle's configuration cache is enabled (`org.gradle.configuration-cache=true`) to dramatically speed up consecutive build times.
* 
**JVM Settings:** The application launches with default memory allocations optimized for desktop environments (`-Xms64m` / `-Xmx64m`).



---

## 💻 How to Run the Project

You do not need to install Gradle globally to run this project. You can use the provided Gradle Wrapper scripts.

### On macOS / Linux

Open your terminal in the root directory and execute:

```bash
# Make the wrapper script executable (if needed)
chmod +x gradlew

# Run the application
./gradlew :app:run

```

### On Windows

Open Command Prompt (`cmd`) or PowerShell in the root directory and execute:

```cmd
gradlew.bat :app:run

```

---

## 🤝 Contributing

1. Create a feature branch: `git checkout -b feature/your-feature-name`
2. Commit your changes: `git commit -m "Add some amazing feature"`
3. Push to the branch: `git push origin feature/your-feature-name`
4. Open a Pull Request.