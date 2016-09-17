package areaOfCircle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Application {
    DataOutputStream toServer = null;
    DataInputStream fromServer = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Client");
        Label inputLable = new Label("Radius");
        TextField inputTxt = new TextField();
        inputTxt.setMaxSize(200, 200);

        Label areaLable = new Label("Area");
        TextArea textArea = new TextArea();
        VBox vBox = new VBox(inputLable, inputTxt, areaLable, textArea);
        Scene scene = new Scene(vBox, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        inputTxt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    double radius = Double.parseDouble(inputTxt.getText().trim());

                    toServer.writeDouble(radius);
                    toServer.flush();

                    double area = fromServer.readDouble();

                    textArea.appendText("Radius is " + radius + "\n");
                    textArea.appendText("Area received from the server is "
                            + area + '\n');

                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
        });

        try {
            Socket socket = new Socket("localhost", 6996);

            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());

        } catch (IOException ex) {
            textArea.appendText(ex.toString() + '\n');
        }
    }

    public static void main(String args[]) {
        Application.launch(args);
    }
}