import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Binro on 2017/5/31.
 */
public class Main extends Application {

    private Stage stage1;
    private Stage stage2;
    private Stage stage3;
    private Stage stage4;
    private List<File> list = new ArrayList<>();
    private static List<Student> data;
    private static List<Student> result;
    private static List<Student> history;
    private ListView<Student> listView;
    private ListView<Student> listView1;
    private ListView<Student> listView2;
    private ListView<Student> listView3;
    private static JdbcCRUDByPreparedStatement ts;
    private int sum = 1;

    public static void main(String[] args) {
        ts = new JdbcCRUDByPreparedStatement();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        stage1 = new Stage();
        stage2 = new Stage();
        stage3 = new Stage();
        stage4 = new Stage();
        Stage stage5 = new Stage();
        data = new ArrayList<Student>();
        result = new ArrayList<>();
        history = new ArrayList<>();
        data.add(new Student("学号", "姓名", "文件", null));
        result.add(new Student("学号", "姓名", "文件                   ", "结果"));
        primaryStage.setTitle("三年E班查重系统");
        GridPane primarygrid = new GridPane();
        primarygrid.setAlignment(Pos.CENTER);
        primarygrid.setHgap(10);
        primarygrid.setVgap(10);
        primarygrid.setPadding(new Insets(25, 25, 25, 25));


        Button btn01 = new Button("开始使用");
        btn01.setStyle("-fx-font: 19 arial;");
        primarygrid.add(btn01, 0, 0);

        Button btn02 = new Button("关       闭");
        btn02.setStyle("-fx-font: 19 arial;");
        primarygrid.add(btn02, 0, 1);

        btn01.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                stage1.show();
                primaryStage.close();
            }
        });

        btn02.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                primaryStage.close();

            }
        });

        Scene scene = new Scene(primarygrid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();

        stage1.setTitle("作业提交");
        BorderPane borderPane = new BorderPane();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        String[] name = {"学号", "姓名", "作业"};
        Label l1 = new Label("学号");
        grid.add(l1, 0, 0);
        TextField t1 = new TextField();
        grid.add(t1, 1, 0);

        Label l2 = new Label("姓名");
        grid.add(l2, 0, 1);
        TextField t2 = new TextField();
        grid.add(t2, 1, 1);

        Label l3 = new Label("作业");
        grid.add(l3, 0, 2);
        Button button = new Button("选择文件");
        final FileChooser fileChooser = new FileChooser();
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                list = fileChooser.showOpenMultipleDialog(new Stage());
                if (list != null) {
                    button.setText(list.get(0).getName());
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("请正确选择文件！");
                    alert.show();
                }
            }
        });
        grid.add(button, 1, 2);

        FlowPane flowPane = new FlowPane();
        listView = new ListView<Student>();
        listView.setCellFactory((ListView<Student> item) -> new Cell());
        flowPane.getChildren().add(listView);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setHgap(100);
        listView.setItems(FXCollections.observableArrayList(data));

        Button button1 = new Button("提交");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!t1.getText().isEmpty() && !t2.getText().isEmpty() && list != null) {
                    data.add(new Student(t1.getText().toString(), t2.getText().toString(), list.get(0).getAbsolutePath(), "合格"));

                    ts.sInsert(new String[]{t1.getText().toString(), t2.getText().toString(), list.get(0).getAbsolutePath()});
                    t1.setText("");
                    t2.setText("");
                    button.setText("");
                    //ts.sInsert(null);
                    listView.setItems(FXCollections.observableArrayList(data));
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("信息或文件错误！");
                    alert.show();
                }
            }
        });
        grid.add(button1, 2, 2);

        FlowPane flowPane1 = new FlowPane();
        Button button2 = new Button("下一步");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage2.show();
                listView1.setItems(FXCollections.observableArrayList(data));
                stage1.close();
            }
        });
        flowPane1.getChildren().add(button2);
        flowPane1.setAlignment(Pos.CENTER);

        Button button10 = new Button("历史");
        button10.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage4.show();
                history.addAll(ts.nsearch());

                listView3.setItems(FXCollections.observableArrayList(history));
                stage1.close();
            }
        });
        flowPane1.getChildren().add(button10);

        borderPane.setTop(grid);
        borderPane.setCenter(flowPane);
        borderPane.setBottom(flowPane1);

        stage1.setScene(new Scene(borderPane, 600, 600));

        BorderPane borderPane1 = new BorderPane();
        listView1 = new ListView<>();
        listView1.setCellFactory((ListView<Student> item) -> new Cell3());
        listView1.setItems(FXCollections.observableArrayList(data));
        listView1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        stage2.setTitle("作业查重");
        GridPane gridPane2 = new GridPane();
        Button button3 = new Button("上一步");
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage1.show();
                listView.setItems(FXCollections.observableArrayList(data));
                stage2.close();
            }
        });
        gridPane2.add(button3, 0, 0);


        stage5.setTitle("正在查重");
        FlowPane flowPane4 = new FlowPane();
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setMinSize(500, 20);

        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i < 11; i++) {
                    progressBar.setProgress(0.1 * i);
                    Thread.sleep(50);
                    //(i+1, 100);
                }
                return null;
            }
        };

        Button btn_task = new Button("确认");
        btn_task.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                stage3.show();
                stage5.close();
            }
        });
        flowPane4.getChildren().add(progressBar);
        flowPane4.getChildren().add(btn_task);
        stage5.setScene(new Scene(flowPane4));

        TextField tf = new TextField();
        tf.setPromptText("设定阈值:(0...1)");

        Button button4 = new Button("查重");
        button4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (!tf.getText().isEmpty() && 0 < Double.parseDouble(tf.getText().toString()) && Double.parseDouble(tf.getText().toString()) < 1) {
                    stage2.close();
                    String strA = "", strB = "", temp = "";
                    BufferedReader bufferedReader;
                    List<Integer> num = new ArrayList<Integer>();
                    List<Student> ll = listView1.getItems();

                    if (ll.get(0).getSelect()) {
                        for (int i = 1; i < ll.size(); i++) {
                            num.add(i);
                            result.add(data.get(i));
                        }
                    } else {
                        for (int i = 0; i < ll.size(); i++) {
                            if (ll.get(i).getSelect()) {
                                num.add(i);
                                result.add(data.get(i));
                            }
                        }
                    }


                    if (num.size() >= 2) {

                            //result.addAll(data.subList(1, data.size()));
                            for (int i = 0; i < num.size(); i++) {
                                for (int j = 0; j < num.size(); j++) {
                                    if (i != j && j < i) {
                                        bufferedReader = new BufferedReader(new FileReader(data.get(num.get(i)).getPath()));
                                        while ((temp = bufferedReader.readLine()) != null) {
                                            strA += temp;
                                        }
                                        bufferedReader.close();
                                        bufferedReader = new BufferedReader(new FileReader(data.get(num.get(j)).getPath()));
                                        while ((temp = bufferedReader.readLine()) != null) {
                                            strB += temp;
                                        }
                                        bufferedReader.close();
                                        Double prob = Computeclass.SimilarDegree(strA, strB);
                                        if (prob > Double.parseDouble(tf.getText().toString())) {
                                            for(int k = sum;k<result.size();k++){
                                                Student s = result.get(k);
                                                if (data.get(num.get(i)).getPath().equals(s.getPath())) {
                                                    Student s1 = new Student(s.getName(),s.getId(),s.getPath(),"抄袭");
                                                    result.remove(k);
                                                    result.add(k,s1);
                                                }
                                                if (data.get(num.get(j)).getPath().equals(s.getPath())) {
                                                    Student s2 = new Student(s.getName(),s.getId(),s.getPath(),"抄袭");
                                                    result.remove(k);
                                                    result.add(k,s2);
                                                }
                                            }
                                        }
                                        listView2.setItems(FXCollections.observableArrayList(result));
                                        stage5.show();
                                        new Thread(task).start();
                                    }
                                }
                            }


                            for (int i = sum+1; i < result.size(); i++) {
                                ts.nsInsert(new String[]{result.get(i).getId(), result.get(i).getName(), result.get(i).getPath(), result.get(i).getMark()});
                            }
                            sum += num.size();

                    } else {
                        stage2.show();
                        listView1.setItems(FXCollections.observableArrayList(data));
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("文件选择错误！");
                        alert.show();
                    }
                    } else {
                        stage2.show();
                        listView1.setItems(FXCollections.observableArrayList(data));
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("阈值输入错误！");
                        alert.show();

                    }

                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        gridPane2.add(tf, 1, 0);
        gridPane2.add(button4, 2, 0);
        gridPane2.setAlignment(Pos.CENTER);

        stage3.setTitle("查重结果");
        borderPane1.setCenter(listView1);
        borderPane1.setBottom(gridPane2);
        stage2.setScene(new Scene(borderPane1, 600, 600));

        BorderPane borderPane2 = new BorderPane();
        listView2 = new ListView<>();
        listView2.setCellFactory((ListView<Student> item) -> new Cell2());
        listView2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        GridPane gridPane3 = new GridPane();
        Button button5 = new Button("上一步");
        button5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage2.show();
                listView1.setItems(FXCollections.observableArrayList(data));
                stage3.close();
            }
        });
        gridPane3.add(button5, 0, 0);

        Button button7 = new Button("历史");
        button7.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage4.show();
                history.addAll(ts.nsearch());

                listView3.setItems(FXCollections.observableArrayList(history));
                stage3.close();
            }
        });
        gridPane3.add(button7, 1, 0);

        Button button6 = new Button("完成");
        button6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage3.close();
            }
        });
        gridPane3.add(button6, 2, 0);
        gridPane3.setAlignment(Pos.CENTER);

        borderPane2.setCenter(listView2);
        borderPane2.setBottom(gridPane3);
        stage3.setScene(new Scene(borderPane2, 600, 600));


        stage4.setTitle("查重历史");
        BorderPane borderPane3 = new BorderPane();
        listView3 = new ListView<>();
        listView3.setCellFactory((ListView<Student> item) -> new Cell2());

        GridPane gridPane4 = new GridPane();
        Button button8 = new Button("上一步");
        button8.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage3.show();
                listView2.setItems(FXCollections.observableArrayList(result));
                stage4.close();
            }
        });
        gridPane4.add(button8, 0, 0);

        Button button9 = new Button("完成");
        button9.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage4.close();
            }
        });
        gridPane4.add(button9, 1, 0);
        gridPane4.setAlignment(Pos.CENTER);

        borderPane3.setCenter(listView3);
        borderPane3.setBottom(gridPane4);
        stage4.setScene(new Scene(borderPane3, 600, 600));


    }


    public class Cell extends ListCell<Student> {
        @Override
        protected void updateItem(Student item, boolean empty) {
            super.updateItem(item, empty);
            HBox hBox = new HBox();
            hBox.setSpacing(10);
            if (item != null) {
                Label id = new Label(item.getId());
                Label name = new Label(item.getName());
                Label path = new Label(item.getPath());
                hBox.getChildren().addAll(name, id, path);
                setGraphic(hBox);
            } else {
                setGraphic(null);
            }
        }
    }

    public class Cell2 extends ListCell<Student> {
        @Override
        protected void updateItem(Student item, boolean empty) {
            super.updateItem(item, empty);
            HBox hBox = new HBox();
            hBox.setSpacing(10);
            if (item != null) {
                Label id = new Label(item.getId());
                Label name = new Label(item.getName());
                Label path = new Label(item.getPath());
                Label mark = new Label(item.getMark());
                hBox.getChildren().addAll(name, id, path, mark);

                setGraphic(hBox);
            } else {
                setGraphic(null);
            }
        }
    }

    public class Cell3 extends ListCell<Student> {
        @Override
        protected void updateItem(Student item, boolean empty) {
            super.updateItem(item, empty);
            HBox hBox = new HBox();
            hBox.setSpacing(10);
            if (item != null) {
                CheckBox checkBox = new CheckBox();
                checkBox.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        item.setSelect(checkBox.isSelected());
                    }
                });
                Label id = new Label(item.getId());
                Label name = new Label(item.getName());
                Label path = new Label(item.getPath());
                Label mark = new Label(item.getMark());
                hBox.getChildren().addAll(checkBox, name, id, path);

                setGraphic(hBox);
            } else {
                setGraphic(null);
            }
        }
    }

}
