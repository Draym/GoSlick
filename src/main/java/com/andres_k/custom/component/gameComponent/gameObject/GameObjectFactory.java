package com.andres_k.custom.component.gameComponent.gameObject;

import com.andres_k.custom.component.gameComponent.gameObject.objects.players.PlayerTest;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObject;
import com.andres_k.gameToolsLib.components.gameComponent.movement.EDirection;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.map.MapObstacle;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.obstacles.Border;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.obstacles.Platform;
import com.andres_k.custom.component.graphicComponents.graphic.EnumWindow;
import com.andres_k.gameToolsLib.utils.configs.WindowConfig;

/**
 * Created by andres_k on 13/10/2015.
 */
public class GameObjectFactory {

    public static GameObject create(EGameObject type, AnimatorController animatorController, String id, float x, float y) {
        GameObject object = null;

        if (type.isIn(EGameObject.ANIMATED) && x > WindowConfig.get().centerPosX(EnumWindow.GAME, 0)) {
            animatorController.setEyesDirection(EDirection.LEFT);
        }

        if (type == EGameObject.PLAYER) {
            object = new PlayerTest(animatorController, id, x, y);
        }
        return object;
    }
}
