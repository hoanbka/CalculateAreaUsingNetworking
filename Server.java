package areaOfCircle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server extends Application {

    private static Socket socket;


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Server");
        TextArea textArea = new TextArea();

        Scene scene = new Scene(textArea);
        primaryStage.setScene(scene);
        primaryStage.show();

        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(6996);
                textArea.appendText("Server started at " + new Date() + '\n');

                Socket socket = serverSocket.accept();

                DataInputStream inputFromClient = new DataInputStream(
                        socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(
                        socket.getOutputStream());

                while (true) {
                    double radius = inputFromClient.readDouble();

                    double area = radius * radius * Math.PI;

                    outputToClient.writeDouble(area);
                    textArea.appendText("Radius received from client: "
                            + radius + '\n');
                    textArea.appendText("Area is: " + area + '\n');
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}