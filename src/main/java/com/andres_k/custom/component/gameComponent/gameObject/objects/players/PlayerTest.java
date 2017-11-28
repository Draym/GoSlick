package com.andres_k.custom.component.gameComponent.gameObject.objects.players;

import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.characters.Player2D;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.characters.Player2DPlatform;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.characters.Player2DWorld;

public class PlayerTest extends Player2DPlatform {
    public PlayerTest(AnimatorController animatorController, String id, float x, float y) {
        super(animatorController, EGameObject.PLAYER, id, 1, x, y, 1, 0, 100, 220, 15);
    }
}
