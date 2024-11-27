package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application 
{

    static Scene scene;

    public static void main(String[] args) 
    {
        // Database connection check at the start of the application
        try (Connection connection = DatabaseManager.getConnection()) 
        {
            System.out.println("Database connected!");            
        } 
        catch (SQLException e) 
        {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }

        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException 
    {
        scene = new Scene(loadFXML("Welcome"), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException 
    {
        try
        {
            scene.setRoot(loadFXML(fxml));
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    private static Parent loadFXML(String fxml) throws IOException 
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
