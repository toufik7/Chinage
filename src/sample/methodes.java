package sample;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import static sample.Main.*;

public class methodes {

    static public void success(ArrayList<Integer> steps, ArrayList<String> faits){
        String title1 = "les regles appliquées sont : ";
        String msg1 = "";
        String title2 = "la table de fait obtenue : ";
        String msg2 = "";

        for(int s=0;s<steps.size();s++){
            msg1 = msg1 + "R(" + steps.get(s)+"), ";
        }

        for(int f=0;f<faits.size();f++){
            msg2 = msg2 + faits.get(f)+ ",";
        }
        ResultBox.display("Success",title1,msg1,title2,msg2);
    }
    static public void failure(ArrayList<RULE> rules){
        String title = "Impossible de démontrer les faits en entrées !!\n" +
                "les regles suivantes ne peuvent etre demantré!";
        String msg ="";

        for (RULE rule:rules) {
            msg = msg + "\n R("+rule.getNumber() +") "+ rule.getPermissions()+" --> "+rule.getActions();
        }
        ResultBox.display("failure", title, msg);
    }
    static public void closeProgram(Stage window) {
        boolean result = ConfirmBox.display("Exsit Window", "Etes vous sur de vouloir quitter le Programme ?");
        if (result) window.close();
    }
    static public GridPane remplir_info(ComboBox<String> file_path_box)  throws IOException{
        GridPane gridPane = new GridPane();


        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);


        //FTinti_Label
        Label ftInit = new Label("table de faits initiale");
        gridPane.add(ftInit, 0, 1);

        //faits_init
        faits_init_Box.setDisable(true);
        faits_init_Box.setDisable(true);


        gridPane.add(faits_init_Box, 1, 1);

        //action_Label
        Label action = new Label("Action a prouvée");
        gridPane.add(action, 0, 2);

        //action_val
        action_Box.setDisable(true);
        action_Box.setEditable(true);
        //action_Box.getSelectionModel().clearSelection();
        rules.clear();
        rules = list_faits(action_Box,faits_init_Box,file_path_box);

        gridPane.add(action_Box, 1, 2);

        return gridPane;
    }
    static public GridPane show_algo() {
        GridPane gridPane = new GridPane();

        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(15);
        gridPane.setHgap(10);

        //button avant

        chAvant.setMinSize(150, 100);
        chAvant.setDisable(true);
        gridPane.add(chAvant, 0, 1);
        // description algo avant
        String av_desc = "• Detecter les regles dont les premisses sont verifees (filtrage).\n" +
                "• Selectionner la regle a appliquer.\n" +
                "• Appliquer la regle et desactive cette regle.\n" +
                "• Recommencer jusqu'a ce qu'il n'y ait plus de regle applicable.";
        Label desc_av = new Label(av_desc);
        gridPane.add(desc_av, 1, 1);

        //button arriere
        chArriere.setMinSize(150, 100);
        chArriere.setDisable(true);
        gridPane.add(chArriere, 0, 2);
        // description algo arriere
        String ar_desc = "• But initial place au sommet d'une pile.\n" +
                "• Detection des regles qui concluent a ce but.\n" +
                "• Resolution de conflits (s'ils existent).\n" +
                "• Application de la regles, i.e, les elements des premisses deviennent de nouveau sous-buts a atteindre.\n" +
                "• Arret : pile vide ou aucune regle applicable.";
        Label desc_ar = new Label(ar_desc);
        gridPane.add(desc_ar, 1, 2);

        action_Box.getSelectionModel().selectedItemProperty().addListener(
                (v,oldValue, newValue) -> {
                    enDisable_algo(action_Box,faits_init_Box);
                });

        faits_init_Box.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            public void onChanged(ListChangeListener.Change<? extends String> c) {
                    enDisable_algo(action_Box,faits_init_Box);
                }
        });

        return gridPane;
    }
    static public void enDisable_algo(ComboBox<String> actionbox,CheckComboBox<String> fait_init){
        if (!(actionbox.getSelectionModel().getSelectedIndex() == -1) && (!fait_init.getCheckModel().isEmpty())) {

                chAvant.setDisable(false);
                chArriere.setDisable(false);

        } else {
                chAvant.setDisable(true);
                chArriere.setDisable(true);
            }
        }
    static public HashSet<String> per_act_list(String line) {
        HashSet<String> list = new HashSet<>();
        int n = 0;
        while (n <= line.length()) {

            Character ch = line.charAt(n);
            list.add(ch.toString());
            //System.out.println(ch);
            n = n + 2;
        }
        return list;
    }
    protected static ArrayList<RULE> list_faits(ComboBox<String> action_Box,CheckComboBox<String> faits_init_Box,ComboBox<String> file_path) throws IOException {
        action_Box.getItems().clear(); // initializer a null
        HashSet<String> tous_faits = new HashSet<>();
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<RULE> rules = new ArrayList<>();



        file_path.getSelectionModel().selectedItemProperty().addListener(
                (v,oldValue, newValue) -> {

                    action_Box.getItems().clear();
                    action_Box.getSelectionModel().clearSelection();

                    faits_init_Box.getCheckModel().clearChecks();
                    faits_init_Box.getItems().clear();
                    lines.clear();
                    tous_faits.clear();
                    rules.clear();
                    if (newValue.isEmpty()){
                        AlertBox.display("Error","Entrer le lien vers le fichier texte");

                    }else {
                        try {
                            BufferedReader in = new BufferedReader(new FileReader(
                                    newValue));
                            String line, per_line = "", act_line = "";
                            int i = 0;
                            while ((line = in.readLine()) != null) {

                                // Afficher le contenu du fichier
                                lines.add(line); // ajouter a tables rules
                                per_line = line.substring(line.indexOf("SI (") + 4, line.indexOf("),"));
                                act_line = line.substring(line.indexOf("ALORS (") + 7, line.length() - 1);

                                //extract rule from line
                                RULE rule = new RULE(i+1,per_act_list(per_line),per_act_list(act_line));
                                tous_faits.addAll(per_act_list(per_line));
                                tous_faits.addAll(per_act_list(act_line));
                                rules.add(rule);



                                //System.out.println (per_line + "-->" + act_line);
                                i++;
                            }
                            in.close();
                            for (String ele:tous_faits) {
                                if (!action_Box.getItems().contains(ele)) {
                                    action_Box.getItems().add(ele);
                                }
                                if (!faits_init_Box.getItems().contains(ele)) {
                                    faits_init_Box.getItems().add(ele);

                                }

                            }
                            faits_init_Box.setDisable(false);
                            action_Box.setDisable(false);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            AlertBox.display("Error","fichier non trouvé");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }


                });
        return rules;

        }

    static public void avant(ArrayList<String> faits, ArrayList<RULE> rules,String act_pr){
        ArrayList<Integer> steps = new ArrayList<>();
        ArrayList<RULE> rules_clone = new ArrayList<>();
        rules_clone.addAll(rules);
        boolean trouve = false; int i=0,j=0,n=0;

        while(!trouve && i<rules_clone.size()){
            for (String fait:faits) {
                if (rules_clone.get(i).getPermissions().contains(fait)){
                    n++;
                }
                j++;
            }

            if (n==rules_clone.get(i).getPermissions().size()) {
                trouve = true;
                // mettre a jour table de faits
                for (String act : rules_clone.get(i).getActions()) {
                    if (!faits.contains(act)) {
                        faits.add(act);
                        steps.add(rules_clone.get(i).getNumber());
                        if (act.equals(act_pr)){
                            success(steps, faits);
                            return;
                        }
                    }
                }
                //desactiver la regle
                rules_clone.remove(i);
                i = -1;
                trouve = false;
            }
            i++; j=0;n =0;
        }
        if (rules.size()==0){
            success(steps, faits);
        }else {
            failure(rules);
        }
    }

    static public int recherche(ArrayList<RULE> rules, String act_pr) {
        int i = 0; boolean trouve = false;
        while (!trouve && i < rules.size()) {
            if (rules.get(i).getActions().contains(act_pr)) {
                trouve = true;
            } else i++;
        }
        if (!trouve) {
            System.out.println(act_pr + " impossible a  prouvé");
        }
        return i;
    }

    static public void chainage_recursive(ArrayList<Integer> steps,ArrayList<ele> pile ,
                                          ArrayList<String> faits,String action,
                                          ArrayList<RULE> rules,String act_pr,int previouse_rule,boolean exit) {

        RULE rule;
        int ruleNb = recherche(rules,act_pr);
        String new_action ="";
        if (ruleNb > rules.size()-1){ int i;
            System.out.println("fait non trouvé");
            for (String fait:rules.get(previouse_rule).getPermissions()) {
                i=0;
                while (i<pile.size()){
                    if (pile.get(i).getFait().equals(fait)){
                        if (pile.get(i).getAction().equals(action)){
                            System.out.println("suppression de faits :"+ pile.get(i).getFait() +" de l'action :"+
                                    pile.get(i).getAction());
                            pile.remove(i);
                            i--;
                        }
                    }else if (pile.get(i).getFait().equals(action)){
                        System.out.println("suppression de l'action :"+ pile.get(i).getFait());
                        act_pr=action;
                        new_action = pile.get(i).getAction();
                        pile.remove(i);
                        i--;
                    }
                    i++;
                }
            }

                action = new_action;
                System.out.println("suppression de la regle :" + rules.get(previouse_rule).getNumber());
                rules.remove(previouse_rule);

                //i=rules.size()+1;
                ruleNb = recherche(rules, act_pr);

        }


        if ( ruleNb < rules.size() ) {
            rule = rules.get(ruleNb);
            pile.add(new ele(act_pr,action,rule.getNumber()));

            ArrayList<String> fait_pile = new ArrayList<>();
            for (ele ele:pile) {
                fait_pile.add(ele.getFait());
            }
            for (String fait : rule.getPermissions()) {
                if (/*!faits.contains(fait)*/!fait_pile.contains(fait)) {
                    // recursivité !!
                    //1. chercher la regle contanant l'action == fait
                    //2.ajouter le num regle
                    chainage_recursive(steps,pile, faits,act_pr ,rules, fait,ruleNb, exit);
                }
            }
            //mise a jour table de faits
            int n=0;
            for (String per:rule.getPermissions()) {
                if (!faits.contains(per)){
                    n++;
                }
            }
            if (n==0){ //we should add after we are sure that the rule works !!
                for (String act : rule.getActions()) {
                    if (!faits.contains(act)){
                        faits.add(act);
                        steps.add(rule.getNumber());
                        //pile.get(pile.size()-1).setNum(null);
                    }
                }
            }
        }

        }


    static public void arriere(ArrayList<String> faits, ArrayList<RULE> rules,String act_pr){

        ArrayList<Integer> steps = new ArrayList<>();
        ArrayList<RULE> rules_clone = new ArrayList<>();
        rules_clone.addAll(rules);
        ArrayList<ele> pile = new ArrayList<>();
        //initialiser la pile
        for (String fait:faits) {
            pile.add(new ele(fait));
        }

        //trouver la regle contenant l'action a prouvée
        chainage_recursive(steps,pile, faits,"", rules_clone, act_pr,0,false);

        //apres avoir recuperer tou les faits
        if (faits.contains(act_pr)){
            success(steps,faits);
        }else failure(rules);
    }


}
