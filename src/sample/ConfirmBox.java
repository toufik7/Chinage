package sample;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;


public class ConfirmBox{
    static boolean answer;

    public static boolean display(String title, String msg){
        Stage window = new Stage();



        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMaxHeight(250);

        Label label = new Label();
        label.setText(msg);

        Button ok = new Button("ok");
        ok.setOnAction(e -> {
            answer = true;
            window.close();

        });

        Button annuler = new Button("annuler");
        annuler.setOnAction(e -> {
            answer = false;
            window.close();

        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, ok,annuler);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
