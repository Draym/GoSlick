package com.andres_k.custom.component.resourceComponents.resources.factory;

import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.custom.component.gameComponent.animation.EAnimation;
import com.andres_k.custom.component.resourceComponents.resources.ESprites;
import com.andres_k.gameToolsLib.components.resourceComponents.resources.factory.AnimationFactory;
import com.andres_k.gameToolsLib.components.resourceComponents.resources.factory.AnimatorFactory;
import com.andres_k.gameToolsLib.utils.configs.ConfigPath;
import com.andres_k.gameToolsLib.utils.tools.FilesTools;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 20/01/2016.
 */
public class AnimatorBackgroundFactory extends AnimatorFactory {
    @Override
    public AnimatorController getAnimator(ESprites index) throws SlickException, JSONException, NoSuchMethodException {
        if (index.getIndex() == ESprites.SCREEN_BACKGROUND.getIndex()) {
            return this.getMenuBackgroundAnimator(index);
        } else if (index.getIndex() == ESprites.MAP_BACKGROUND.getIndex()) {
            return this.getMapBackgroundAnimator(index);
        }
        return null;
    }

    public AnimatorController getMenuBackgroundAnimator(ESprites index) throws SlickException {
        AnimatorController animatorController = new AnimatorController();

        if (index == ESprites.LOAD_SCREEN) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createStaticUniqueFrame(ConfigPath.image_background + "/backgroundLoad.png"));
        } else if (index == ESprites.LOGO) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createStaticUniqueFrame(ConfigPath.image_background + "/logo.png"));
        } else if (index == ESprites.HOME_SCREEN) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createStaticUniqueFrame(ConfigPath.image_background + "/backgroundHome.png"));
        } else if (index == ESprites.FINAL_SCREEN) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createStaticUniqueFrame(ConfigPath.image_background + "/final/final.jpg"));
        }
        return animatorController;
    }

    public AnimatorController getMapBackgroundAnimator(ESprites index) throws SlickException, JSONException {
        AnimatorController animatorController = new AnimatorController();

        if (index == ESprites.MAP_1) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createStaticUniqueFrame(ConfigPath.image_map + "/map.jpg"));
        }
        return animatorController;
    }
}
