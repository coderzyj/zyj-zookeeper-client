package com.saitama.view;

import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import de.felixroske.jfxsupport.GUIState;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;

import javax.annotation.PostConstruct;

/**
 * @author: zyj
 * @time: 2020/12/17  22:14
 * @description:
 */
@FXMLView("/views/addConnection.fxml")
public class AddConnectionView  extends AbstractFxmlView {

    private Stage stage;

    @PostConstruct
    private void initUI(){
        AnchorPane pane  = (AnchorPane) getView();

        Platform.runLater(()->{
            stage = new Stage();
            Scene scene  = new Scene(pane);
            JMetro jMetro = new JMetro();
            jMetro.setScene(scene);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initOwner(GUIState.getStage());
        });
    }



    public void show() {
        if (stage.isShowing()) {
            stage.requestFocus();
        } else {
            stage.show();
        }
    }

    public void hide() {
        if (stage.isShowing()) {
            stage.close();
        }
    }
}
