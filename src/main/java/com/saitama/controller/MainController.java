package com.saitama.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.saitama.core.Node;
import com.saitama.core.ZkConnectionManager;
import com.saitama.view.AddConnectionView;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import sun.plugin.util.UIUtil;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private Button refla;
    @FXML
    private TreeView treeView;

//    @FXML
//    private GridPane gridPane;

    @Autowired
    private AddConnectionView addConnectionView;

    @Autowired
    private ZkConnectionManager zkConnectionManager;

    @FXML
    private Label aversion;

    @FXML
    private Label ctime;

    @FXML
    private Label cversion;

    @FXML
    private Label czxid;

    @FXML
    private Label dataLength;

    @FXML
    private Label ephemeralOwner;

    @FXML
    private Label mtime;

    @FXML
    private Label mzxid;

    @FXML
    private Label numChildren;

    @FXML
    private Label pzxid;

    @FXML
    private Label version;

    @FXML
    private Label value;

    @Autowired
    private AddConnectionController addConnectionController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuBar.prefWidthProperty().bind(pane.widthProperty());
//        gridPane.prefHeightProperty().bind(pane.heightProperty());
        Image image = new Image(getClass().getResourceAsStream("/icon/connect.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(32);
        imageView.setFitWidth(32);
        button.setGraphic(imageView);
        Image reflashImage = new Image(getClass().getResourceAsStream("/icon/刷新.png"));
        ImageView imageView1 = new ImageView(reflashImage);
        imageView1.setFitHeight(32);
        imageView1.setFitWidth(32);
        refla.setGraphic(imageView1);
        TreeItem<String> item = new TreeItem<>("/",new ImageView(new Image(getClass().getResourceAsStream("/icon/dictionary.png"))));
        treeView.setRoot(item);

//        TreeItem<String> item1 = new TreeItem<>("dubbo",new ImageView(new Image(getClass().getResourceAsStream("/icon/dictionary.png"))));
//        TreeItem<String> item2 = new TreeItem<>("provider",new ImageView(new Image(getClass().getResourceAsStream("/icon/dictionary.png"))));
//        item.getChildren().addAll(item1,item2);
        treeView.prefHeightProperty().bind(pane.heightProperty());
        treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            javafx.scene.Node node = event.getPickResult().getIntersectedNode();

            if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
                String name = (String) ((TreeItem)treeView.getSelectionModel().getSelectedItem()).getValue();
                System.out.println("Node click: " + name);
                try {
                    Map<String, Object> nodeMetaData = zkConnectionManager.getNodeMetaData(name);
                    Stat stat = (Stat) nodeMetaData.get("stat");
                    aversion.setText(String.valueOf(stat.getAversion()));
                    ctime.setText(String.valueOf(stat.getCtime()));
                    cversion.setText(String.valueOf(stat.getCversion()));
                    czxid.setText(String.valueOf(stat.getCzxid()));
                    dataLength.setText(String.valueOf(stat.getDataLength()));
                    ephemeralOwner.setText(String.valueOf(stat.getEphemeralOwner()));
                    mtime.setText(String.valueOf(stat.getMtime()));
                    mzxid.setText(String.valueOf(stat.getMzxid()));
                    numChildren.setText(String.valueOf(stat.getNumChildren()));
                    pzxid.setText(String.valueOf(stat.getPzxid()));
                    version.setText(String.valueOf(stat.getVersion()));

                    if(StringUtils.isNotBlank((String) nodeMetaData.get("value"))){
                        value.setText((String) nodeMetaData.get("value"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
//        gridPane.prefHeightProperty().bind(pane.heightProperty());
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

    public void addAllNode(TreeItem<String> treeItem, Node node){
        if(treeItem == null){
            treeItem = treeView.getTreeItem(0);
        }
        if(CollectionUtil.isEmpty(node.getChildren())){
            return;
        }

        Map<Node,TreeItem<String>> map = new HashMap<>();
        List<TreeItem<String>> collect = node.getChildren().stream().map(ch -> {
            TreeItem<String> item = new TreeItem<>(ch.getPath(), new ImageView(new Image(getClass().getResourceAsStream("/icon/dictionary.png"))));
            map.put(ch,item);
            return item;
        }).collect(Collectors.toList());
        treeItem.getChildren().addAll(collect);

        node.getChildren().forEach(x -> addAllNode(map.get(x),x));
    }


    public void reflash(){
        addConnectionController.doCreate();
    }
}
