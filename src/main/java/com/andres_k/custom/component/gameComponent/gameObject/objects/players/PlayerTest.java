package com.andres_k.custom.component.gameComponent.gameObject.objects.players;

import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.characters.Player;

public class PlayerTest extends Player {
    public PlayerTest(AnimatorController animatorController, String id, float x, float y) {
        super(animatorController, EGameObject.PLAYER, id, 1, x, y, 1, 0, 100, 220, 15);
    }
}
