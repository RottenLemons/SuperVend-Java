package sample.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {
    @FXML
    private ImageView creatorImage;
    @FXML
    private Label label;
    @FXML
    private MediaView mediaView;
    private int curIndex;
    private Media[] media = new Media[5];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String[] ads = {"Model/Resource/Advertisement Video/Addidas.mp4", "Model/Resource/Advertisement Video/Dove.mp4", "Model/Resource/Advertisement Video/Nescafe1.mp4", "Model/Resource/Advertisement Video/Nescafe2.mp4", "Model/Resource/Advertisement Video/Nike.mp4"};
        for (int i = 0; i < ads.length; i++) {
            media[i] = new Media(Main.class.getResource(ads[i]).toExternalForm());
        }
        playNextVideo();
    }

    private void playNextVideo() {
        disposePlayer();
        if (curIndex == media.length) {
            curIndex = 0;
        }

        MediaPlayer player = new MediaPlayer(media[curIndex++]);
        player.setAutoPlay(true);
        player.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                playNextVideo();
            }
        });
        mediaView.setMediaPlayer(player);
    }

    private void disposePlayer() {
        MediaPlayer player = mediaView.getMediaPlayer();
        if (player != null) {
        }
    }
}

