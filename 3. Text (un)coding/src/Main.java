
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {

            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 800, 800);
            root.setId("root");


            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

            Label instruction = new Label("Wpisz ciąg znaków np.: aaaaaabbbbbbb i zaznacz \"koduj\"");
            Label source = new Label("Źródło:");
            Label operationResult = new Label("Wynik operacji:");
            Label operation = new Label("Operacja:");

            source.setId("source");
            instruction.setId("instruction");
            operation.setId("operation");

            TextArea textSource = new TextArea();
            TextArea textOperationResult = new TextArea();
            textSource.setPrefHeight(60);
            textSource.setWrapText(true);
            textOperationResult.setPrefHeight(60);
            textOperationResult.setWrapText(true);

            Button copy = new Button("Kopiuj");
            Button execute = new Button("Wykonaj");
            execute.setId("execute");

            RadioButton coding = new RadioButton("kodowanie");
            RadioButton decoding = new RadioButton("dekodowanie");

            coding.setId("coding");
            decoding.setId("decoding");

            HBox copyButtonHBox = new HBox();
            copyButtonHBox.getChildren().add(copy);

            VBox rightSide = new VBox(30);
            rightSide.setPrefWidth(200);
            rightSide.getChildren().addAll(operation, coding, decoding);
            root.setRight(rightSide);

            VBox center1 = new VBox(10);
            center1.getChildren().addAll(instruction, source, textSource, copyButtonHBox, operationResult, textOperationResult, execute);
            root.setCenter(center1);

            operationResult.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255),
                    new CornerRadii(0), new Insets(-3,-5,-3,-5))));


            copyButtonHBox.setAlignment(Pos.BASELINE_RIGHT);

            execute.setOnAction(
                    event -> {

                        if (coding.isSelected()) {
                            int count = 1;
                            String result = "";
                            String newText = textSource.getText() + " ";
                            for (int i = 0; i < newText.length() - 1; i++) {
                                if (newText.charAt(i) == newText.charAt(i + 1)) {
                                    count++;
                                } else {
                                    String counting = newText.charAt(i) + "" + count + ", ";
                                    result = result + counting;
                                    count = 1;
                                }
                            }
                            textOperationResult.setText(result);

                        } else if (decoding.isSelected()) {

                            String regex = "\\b(([0-9]|[a-z]|[A-Z])[0-9]+)\\b";

                            Pattern newText = Pattern.compile(regex);
                            Matcher m = newText.matcher(textSource.getText());


                            String decodedString = "";

                            while (m.find()){

                                String subStringLetter = textSource.getText().substring(m.start(), m.start() + 1);
                                String subStringNumber = textSource.getText().substring(m.start() + 1, m.end());
                                int intNumber = Integer.parseInt(subStringNumber);

                                for(int j = 0; j < intNumber; j++){
                                    decodedString = decodedString + subStringLetter;
                                }
                                textOperationResult.setText(decodedString);
                            }
                        }
                    });

            copy.setOnAction(
                event -> {
                    textSource.setText(textOperationResult.getText());
                });


            ToggleGroup rButtons = new ToggleGroup();
            coding.setToggleGroup((rButtons));
            decoding.setToggleGroup((rButtons));

            center1.setId("center1");
            rightSide.setId("rightSide");

            primaryStage.setScene(scene);
            primaryStage.show();


            } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        launch(args);
    }
}

