package com.andres_k.custom.component.resourceComponents.resources.factory;

import com.andres_k.custom.component.gameComponent.commands.actionComponent.PlayerActions;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.custom.component.gameComponent.animation.EAnimation;
import com.andres_k.gameToolsLib.components.gameComponent.animations.details.AnimationConfigItem;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObject;
import com.andres_k.custom.component.resourceComponents.resources.ESprites;
import com.andres_k.gameToolsLib.components.resourceComponents.resources.factory.AnimationFactory;
import com.andres_k.gameToolsLib.components.resourceComponents.resources.factory.AnimatorFactory;
import com.andres_k.gameToolsLib.utils.configs.ConfigPath;
import com.andres_k.gameToolsLib.utils.configs.GameConfig;
import com.andres_k.gameToolsLib.utils.tools.FilesTools;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by andres_k on 13/03/2015.
 */
public class AnimatorGameFactory extends AnimatorFactory {

    @Override
    public AnimatorController getAnimator(ESprites index) throws SlickException, JSONException, NoSuchMethodException {
        if (index.getIndex() == ESprites.PLAYER.getIndex()) {
            if (index == ESprites.PLAYER) {
                return this.getPlayerAnimator();
            }
        } else if (index.getIndex() == ESprites.ITEM.getIndex()) {
            return this.getItemAnimator(index);
        }
        return null;
    }

    private AnimatorController getPlayerAnimator() throws NoSuchMethodException, SlickException, JSONException {
        AnimatorController animatorController = new AnimatorController();
        String id = "/player/slime";

        animatorController.addAnimation(EAnimation.IDLE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/slimeIdle.png", 64, 64), EAnimation.IDLE.isLoop(), 0, 4, 0, 1, new int[]{200, 200, 200, 200}));
        animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + id + "/slimeIdle.json")));
        animatorController.addConfig(EAnimation.IDLE, 0, new AnimationConfigItem(PlayerActions.class.getMethod("idle", GameObject.class), true));

        return animatorController;
    }

    private AnimatorController getItemAnimator(ESprites index) throws SlickException, JSONException {
        AnimatorController animatorController = new AnimatorController();
        if (index == ESprites.GROUND) {
            animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/map/ground.json")));
        } else if (index == ESprites.WALL) {
            animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/map/wall.json")));
        }
        return animatorController;
    }
}
