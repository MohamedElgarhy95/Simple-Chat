/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chat_App;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 *
 * @author Mohamed Elgarhy
 */
public class Client_side extends Application {
TextArea txt = new TextArea();
Label lb = new Label("Enter Your Message");
Button SendButton = new Button("Send");
TextField tx = new TextField();

FlowPane f = new FlowPane();
BorderPane root = new BorderPane();
//////////////////////////////////////////////////
Socket s;
DataInputStream dis;
PrintStream ps;
@Override
public void init(){
    try {
        s = new Socket("127.0.0.1", 5005);
        dis = new DataInputStream(s.getInputStream());
        ps = new PrintStream(s.getOutputStream());

    } catch (IOException e) {
System.out.print("Error");
    }
   
new Thread(() -> {
    while(true){
        String replyMsg;
        try {
            replyMsg = dis.readLine();
            txt.appendText("\n"+replyMsg);
        } catch (IOException e) {
            
        }
        
        
    }   }).start();
}
    @Override
    public void start(Stage primaryStage) {
        
        SendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ps.println(tx.getText());
                tx.clear();
            }
        });
        txt.setEditable(false);
        f.getChildren().addAll(lb,tx,SendButton);
        root.setCenter(txt);
        root.setBottom(f);
        
        Scene scene = new Scene(root, 300, 300);
        
        primaryStage.setTitle("Welcome to My World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
