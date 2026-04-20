## Prerequisites
- Java Development Kit (JDK) 17 or later
- JavaFX SDK 21.0.10

## Setup

1. Download and extract the JavaFX SDK from https://gluonhq.com/products/javafx/
2. Update the `settings.json` file to include the path to the JavaFX SDK:
   ```json
   {
       "java.project.sourcePaths": ["src"],
       "java.project.outputPath": "bin",
       "java.project.referencedLibraries": [
           "lib/**/*.jar",
           "C:/path/to/javafx-sdk-21.0.10/lib/*.jar"
       ]
   }


   launch : 
    {
        "version": "0.2.0",
        "configurations": [
            {
                "type": "java",
                "name": "Launch PhysicsSim",
                "request": "launch",
                "mainClass": "PhysicsSim",
                "vmArgs": "--module-path \"C:/javafx-sdk-21.0.10/lib\" --add-modules javafx.controls,javafx.graphics"
            }
        ]
    }
## RUN
./run PhysicsSim