package tictactoe.presenter.Admin;

import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class AdminViewBase extends StackPane {

    protected final ImageView backgroundIV;
    protected final BorderPane borderPane;
    protected final Label titleLbl;
    protected final PieChart playersPieChart;
    protected final AnchorPane anchorPane;
    protected final AnchorPane anchorPane0;
    protected final Button startBtn;
    protected final AnchorPane anchorPane1;

    public AdminViewBase() {

        backgroundIV = new ImageView();
        borderPane = new BorderPane();
        titleLbl = new Label();
        playersPieChart = new PieChart();
        anchorPane = new AnchorPane();
        anchorPane0 = new AnchorPane();
        startBtn = new Button();
        anchorPane1 = new AnchorPane();

        setPrefHeight(150.0);
        setPrefWidth(200.0);

        backgroundIV.setFitHeight(400.0);
        backgroundIV.setFitWidth(600.0);
        backgroundIV.setPickOnBounds(true);
        //backgroundIV.setImage(new Image(getClass().getResourceAsStream("/tictactoeserver/resource/images/bg.jpg")));

        borderPane.setPrefHeight(200.0);
        borderPane.setPrefWidth(200.0);
        borderPane.setStyle("-fx-background-color: white;");

        BorderPane.setAlignment(titleLbl, javafx.geometry.Pos.CENTER);
        titleLbl.setAlignment(javafx.geometry.Pos.CENTER);
        titleLbl.setPrefHeight(50.0);
        titleLbl.setPrefWidth(280.0);
        titleLbl.setText("X Candy O Admin");
        titleLbl.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        titleLbl.setTextFill(javafx.scene.paint.Color.valueOf("#00b8d0"));
        titleLbl.setFont(new Font("Cooper Black", 30.0));
        borderPane.setTop(titleLbl);
        StackPane.setMargin(borderPane, new Insets(10.0));

        BorderPane.setAlignment(playersPieChart, javafx.geometry.Pos.CENTER);
        playersPieChart.setPrefHeight(248.0);
        playersPieChart.setPrefWidth(502.0);
        playersPieChart.setTitle("Players Statistics");
        borderPane.setCenter(playersPieChart);

        BorderPane.setAlignment(anchorPane, javafx.geometry.Pos.CENTER);
        anchorPane.setPrefHeight(248.0);
        anchorPane.setPrefWidth(10.0);
        borderPane.setRight(anchorPane);

        BorderPane.setAlignment(anchorPane0, javafx.geometry.Pos.CENTER);
        anchorPane0.setPrefHeight(62.0);
        anchorPane0.setPrefWidth(560.0);

        startBtn.setLayoutX(107.0);
        startBtn.setMnemonicParsing(false);
        startBtn.setPrefHeight(3.0);
        startBtn.setPrefWidth(347.0);
        startBtn.setStyle("-fx-background-color: #00B8D0;");
        startBtn.setText("Start");
        startBtn.setTextFill(javafx.scene.paint.Color.WHITE);
        startBtn.setFont(new Font("Cooper Black", 20.0));
        borderPane.setBottom(anchorPane0);
        borderPane.setPadding(new Insets(10.0));

        BorderPane.setAlignment(anchorPane1, javafx.geometry.Pos.CENTER);
        anchorPane1.setPrefHeight(248.0);
        anchorPane1.setPrefWidth(10.0);
        borderPane.setLeft(anchorPane1);

        getChildren().add(backgroundIV);
        anchorPane0.getChildren().add(startBtn);
        getChildren().add(borderPane);
        
    }
}
