package com.andres_k.custom.component.graphicComponents.trailer;

import com.andres_k.gameToolsLib.components.graphicComponents.trailer.TrailerComponent;
import com.andres_k.gameToolsLib.components.camera.CameraController;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.custom.component.gameComponent.animation.EAnimation;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.custom.component.resourceComponents.resources.ResourceManager;
import com.andres_k.custom.component.resourceComponents.sounds.ESound;
import com.andres_k.gameToolsLib.components.resourceComponents.sounds.MusicController;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import com.andres_k.gameToolsLib.utils.tools.Console;
import com.andres_k.gameToolsLib.utils.tools.RandomTools;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 26/04/2017.
 */
public class FinalGameTrailer extends TrailerComponent {

    public FinalGameTrailer() {
        super();
    }

    @Override
    public void reset() {
        super.reset();
    }

    public void initTrailer() throws SlickException {
    }

    public void launchTrailer() {
        this.started = true;
        this.running = true;
        CameraController.get().init();
        MusicController.get().stop(ESound.BACKGROUND_GAME);
        MusicController.get().loop(ESound.BACKGROUND_WIN);
    }

    public void draw(Graphics g) throws SlickException {
    }

    public void update() {
        if (this.running) {
                this.finished = true;
                this.running = false;
        }
    }
}
