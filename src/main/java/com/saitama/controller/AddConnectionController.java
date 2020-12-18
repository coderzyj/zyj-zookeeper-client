package com.saitama.controller;

import com.saitama.core.ZkConnectionManager;
import com.saitama.util.DialogUtil;
import com.saitama.view.AddConnectionView;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: zyj
 * @time: 2020/12/17  21:48
 * @description:
 */
@FXMLController
@Slf4j
public class AddConnectionController {
    @Autowired
    private AddConnectionView addConnectionView;
    @FXML
    private TextField host;

    @FXML
    private TextField port;

    @FXML
    private TextField timeout;

    @Autowired
    private ZkConnectionManager zkConnectionManager;

    @Autowired
    private MainController mainController;

    public void cancel(){
        addConnectionView.hide();
    }


    public void doCreate(){
        String host = this.host.getText();
        String port = this.port.getText();

        if(StringUtils.isBlank(host)){
            DialogUtil.showAlertDialog("请填写主机地址！","提示", Alert.AlertType.WARNING);
            return;
        }
        if(StringUtils.isBlank(port)){
            DialogUtil.showAlertDialog("请填写端口号！","提示", Alert.AlertType.WARNING);
            return;
        }

        try {
            zkConnectionManager.connect(host,port,this.timeout.getText());
            DialogUtil.showAlertDialog("connect success！","提示", Alert.AlertType.INFORMATION);
            addConnectionView.hide();
        } catch (InterruptedException e) {
            DialogUtil.showAlertDialog("connect failed! please check your info","错误", Alert.AlertType.ERROR);
        }

        try {
            List<String> allNode = zkConnectionManager.getAllNode();
            System.out.println(allNode);
            mainController.addNode(allNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
