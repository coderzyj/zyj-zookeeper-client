package com.saitama.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.saitama.view.AddConnectionView;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author : zhangyongjie
 * @version : 1.0
 * @createTime : 2020/12/17 14:22
 * @description :
 */
@FXMLController
public class MainController implements Initializable {
    @FXML
    private AnchorPane pane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Button button;
    @FXML
    private TreeView treeView;

    @Autowired
    private AddConnectionView addConnectionView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuBar.prefWidthProperty().bind(pane.widthProperty());

        Image image = new Image(getClass().getResourceAsStream("/icon/connect.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(32);
        imageView.setFitWidth(32);
        button.setGraphic(imageView);

        TreeItem<String> item = new TreeItem<>("/",new ImageView(new Image(getClass().getResourceAsStream("/icon/dictionary.png"))));
        treeView.setRoot(item);

//        TreeItem<String> item1 = new TreeItem<>("dubbo",new ImageView(new Image(getClass().getResourceAsStream("/icon/dictionary.png"))));
//        TreeItem<String> item2 = new TreeItem<>("provider",new ImageView(new Image(getClass().getResourceAsStream("/icon/dictionary.png"))));
//        item.getChildren().addAll(item1,item2);
        treeView.prefHeightProperty().bind(pane.heightProperty());
    }

    public void createConnection(){
        addConnectionView.show();
    }


    public void addNode(List<String>nodes){
        if(CollectionUtil.isEmpty(nodes)){
            return;
        }

        List<TreeItem<String>> items = nodes.stream().map(node -> {
            TreeItem<String> item = new TreeItem<>(node, new ImageView(new Image(getClass().getResourceAsStream("/icon/dictionary.png"))));
            return item;
        }).collect(Collectors.toList());

        TreeItem treeItem = treeView.getTreeItem(0);
        treeItem.getChildren().addAll(items);
    }
}
