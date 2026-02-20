package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        TextField titleField = new TextField();
        titleField.setPromptText("Please enter the title");

        Button searchBtn = new Button("Search");
        Button clearBtn = new Button("Clear");

        Label statusLabel = new Label();
        TextArea outputArea = new TextArea();
        outputArea.setPrefSize(800, 400);
        outputArea.setEditable(false);


        Requester requester = new Requester();
        RevisionParser parser = new RevisionParser();


        searchBtn.setOnAction(e -> {
            String title = titleField.getText() == null ? "" : titleField.getText().trim();
            if (title.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error: empty title");
                alert.setHeaderText("Error: empty title");
                alert.setContentText("Please provide an article name.");
                alert.showAndWait();
                statusLabel.setText("Please provide an article name.");
                return;
            }

            outputArea.clear();
            statusLabel.setText("Loading...");

            searchBtn.setDisable(true);
            clearBtn.setDisable(true);
            titleField.setDisable(true);

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    String json = requester.fetchRecentRevisions(title);

                    String redirectTarget = parser.getRedirectTarget(json);
                    List<Revision> revisions = parser.parse(json);


                    Platform.runLater(() -> {
                        if (redirectTarget != null) {
                            outputArea.appendText("Redirected to " + redirectTarget + "\n");
                        }

                        if (revisions == null || revisions.isEmpty()) {
                            outputArea.appendText("No revisions found\n");
                        } else {
                            int i = 1;
                            for (Revision r : revisions) {
                                outputArea.appendText(i + "  " + r.getTimestamp() + "  " + r.getUser() + "\n");
                                i++;
                            }
                        }

                        statusLabel.setText("Done.");

                        searchBtn.setDisable(false);
                        clearBtn.setDisable(false);
                        titleField.setDisable(false);
                    });

                    return null;
                }
            };


            task.setOnFailed(ev -> {
                Throwable ex = task.getException();
                String msg = (ex == null ? "unknown error" : ex.getMessage());
                statusLabel.setText("Error: " + msg);
                outputArea.appendText("[ERROR] " + msg + "\n");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                if(ex instanceof NotFoundException) {
                    alert.setHeaderText("Page Not Found");
                }
                else if(ex instanceof NetworkException) {
                    alert.setHeaderText("Network Error");
                }
                else{
                    alert.setHeaderText("An Error Occurred");
                }
                alert.setContentText(msg);
                alert.showAndWait();

                searchBtn.setDisable(false);
                clearBtn.setDisable(false);
                titleField.setDisable(false);
            });
            new Thread(task).start();
        });

        clearBtn.setOnAction(e -> {
            titleField.clear();
            outputArea.clear();
            statusLabel.setText("");
        });


        HBox top = new HBox(10, new Label("Title:"), titleField, searchBtn, clearBtn);
        VBox root = new VBox(10, top, statusLabel, outputArea);
        root.setPadding(new Insets(12));

        stage.setTitle("Wiki Revisions search developed by Daniel and Fei");
        stage.setScene(new Scene(root, 900, 550));
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}
