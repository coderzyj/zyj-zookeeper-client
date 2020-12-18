package com.saitama.view;

import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import de.felixroske.jfxsupport.GUIState;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import jfxtras.styles.jmetro.JMetro;

import javax.annotation.PostConstruct;

/**
 * @author : zhangyongjie
 * @version : 1.0
 * @createTime : 2020/12/17 14:24
 * @description :
 */
@FXMLView("/views/main.fxml")
public class MainView extends AbstractFxmlView {


    @PostConstruct
    public void initMain(){
        AnchorPane view = (AnchorPane) getView();
        Scene scene = new Scene(view);
        JMetro jMetro = new JMetro();
        jMetro.setScene(scene);
        GUIState.setScene(scene);
    }
}
