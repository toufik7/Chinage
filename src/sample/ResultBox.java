package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ResultBox {
    public static void display(String title,String title1, String msg1,String title2, String msg2){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMaxHeight(300);

        Label t1 = new Label();
        t1.setText(title1);
        Label m1 = new Label();
        m1.setText(msg1);
        HBox hBox1 = new HBox(t1,m1);
        hBox1.setSpacing(5);

        Label t2 = new Label();
        t2.setText(title2);
        Label m2 = new Label();
        m2.setText(msg2);
        HBox hBox2 = new HBox(t2,m2);
        hBox2.setSpacing(5);

        Button ok = new Button("ok");
        ok.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(hBox1, hBox2, ok);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void display(String title,String title1, String msg1){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMaxHeight(300);

        Label t1 = new Label();
        t1.setText(title1);
        Label m1 = new Label();
        m1.setText(msg1);

        Button ok = new Button("ok");
        ok.setOnAction(e -> window.close());

        VBox layout = new VBox(10);

        layout.getChildren().addAll(t1, m1, ok);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
