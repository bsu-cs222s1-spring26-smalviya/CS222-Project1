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
        // ---- UI controls（控件）----
        TextField titleField = new TextField();
        titleField.setPromptText("Please enter the title");

        Button searchBtn = new Button("Search");
        Button clearBtn = new Button("Clear");

        Label statusLabel = new Label();
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);

        // ---- Your existing logic classes（你原来的逻辑类）----
        Requester requester = new Requester();
        RevisionParser parser = new RevisionParser();

        // ---- Event handler（事件处理器）----
        searchBtn.setOnAction(e -> {
            String title = titleField.getText() == null ? "" : titleField.getText().trim();
            if (title.isEmpty()) {
                statusLabel.setText("Error: empty title");
                return;
            }

            outputArea.clear();
            statusLabel.setText("Loading...");

            // Background task（后台任务）
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    String json = requester.fetchRecentRevisions(title);

                    String redirectTarget = parser.getRedirectTarget(json);
                    List<Revision> revisions = parser.parse(json);

                    // Update UI on UI thread（在UI线程更新界面）
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
                    });

                    return null;
                }
            };

            // Error handling（错误处理）
            task.setOnFailed(ev -> {
                Throwable ex = task.getException();
                String msg = (ex == null ? "unknown error" : ex.getMessage());
                statusLabel.setText("Error: " + msg);
                outputArea.appendText("[ERROR] " + msg + "\n");
            });

            new Thread(task).start();
        });

        clearBtn.setOnAction(e -> {
            titleField.clear();
            outputArea.clear();
            statusLabel.setText("");
        });

        // ---- Layout（布局）----
        HBox top = new HBox(10, new Label("Title:"), titleField, searchBtn, clearBtn);
        VBox root = new VBox(10, top, statusLabel, outputArea);
        root.setPadding(new Insets(12));

        stage.setTitle("Wiki Revisions search developed by Daniel and Fei");
        stage.setScene(new Scene(root, 900, 550));
        stage.show();
    }
    public static void main(String[] args) {
        launch(args); // 启动 JavaFX runtime（运行时）
    }

}
