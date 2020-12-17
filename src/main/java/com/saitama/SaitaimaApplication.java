package com.saitama;

import com.saitama.view.MainView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

/**
 * @author : zhangyongjie
 * @version : 1.0
 * @createTime : 2020/12/17 13:55
 * @description :
 */

@SpringBootApplication
@Slf4j
public class SaitaimaApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launch(SaitaimaApplication.class, MainView.class,args);
    }

    @Override
    public void start(Stage stage) throws Exception {
//        JMetro jMetro = new JMetro(Style.DARK);
//        jMetro.setScene(stage.getScene());
        stage.setTitle("地下城与勇士");
//        ClassPathResource resource = new ClassPathResource("icon/dnf.png");
        stage.getIcons().addAll(new Image(getClass().getResourceAsStream("/icon/dnf.png")));
//        stage.setWidth(1920);
//        stage.setHeight(1000);
        stage.setFullScreen(true);
        super.start(stage);
    }
}
