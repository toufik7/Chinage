package sample;

import org.controlsfx.control.CheckComboBox;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.util.ArrayList;

import static sample.methodes.*;

public class Main extends Application {
    static Stage window;
    static ComboBox<String> file_path_Box;
    static CheckComboBox<String> faits_init_Box;
    //static ComboBox<String> faits_init_Box = new ComboBox<>();
    static ComboBox<String> action_Box;
    static public ArrayList<RULE> rules;
    static Button chAvant;
    static Button chArriere;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        chArriere = new Button("Chainage Arriere");
        chAvant = new Button("Chainage Avant");
        rules = new ArrayList<>();
        action_Box = new ComboBox<>();
        faits_init_Box = new CheckComboBox<String>();
        file_path_Box = new ComboBox<>();

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(10, 10, 10, 10));
        file_path_Box.setMinSize(300, 35);
        file_path_Box.setPromptText("file path");
        file_path_Box.setValue("file_path");
        file_path_Box.setEditable(true);



        layout.getChildren().addAll(file_path_Box, remplir_info(file_path_Box), show_algo());
        chAvant.setOnAction(e ->{
            ArrayList<String> table_faits = new ArrayList<>();
            String action = action_Box.getValue();
            table_faits.addAll(faits_init_Box.getCheckModel().getCheckedItems());
            avant(table_faits,rules,action);
        });
        chArriere.setOnAction(e ->{
            ArrayList<String> table_faits = new ArrayList<>();
            String action = action_Box.getValue();
            table_faits.addAll(faits_init_Box.getCheckModel().getCheckedItems());
            arriere(table_faits,rules,action);
        });

        Scene scene = new Scene(layout, 900, 600);

        window.setScene(scene);
        window.setTitle("Chainage Algorithme");
        window.show();


        window.setOnCloseRequest(event -> {
            event.consume();
            closeProgram(window);
        });

        window.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {

            switch (event.getCode().getCode()) {
                case 27: { // 27 = ESC key
                    closeProgram(window);
                    break;
                }
                default: {
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }






}
