package com.andres_k.custom.component.gameComponent.gameObject;

import com.andres_k.custom.component.graphicComponents.background.EBackground;
import com.andres_k.custom.component.resourceComponents.resources.ResourceManager;
import com.andres_k.gameToolsLib.components.controllers.EMode;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameDesign;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObject;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObjectController;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.Character;
import com.andres_k.gameToolsLib.components.gameComponent.movement.EDirection;
import com.andres_k.gameToolsLib.utils.configs.GameConfig;
import com.andres_k.gameToolsLib.utils.tools.Console;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 10/07/2015.
 */
public final class MyGameDesign extends GameDesign {

    public MyGameDesign() {
    }


    // INIT
    @Override
    public void initWorld() throws SlickException {
    }

    // FUNCTIONS

    @Override
    public void enter() throws SlickException {
        this.initWorld();
    }

    @Override
    public void addWinner(String id) {
        this.point += 1;
    }

    @Override
    public void thisPlayerIsDead(Character character) {
        Console.write("\n A Slime is dead");/*
        if (character.getId().equals(CameraController.get().getIdOwner())) {
        }*/
    }

    @Override
    public boolean isTheEndOfTheGame() {
        return (GameConfig.mode != EMode.SOLO && GameObjectController.get().getNumberPlayers() == 0);
    }
    
    // GETTERS
    @Override
    public int getWinnersNumber() {
        return this.point;
    }

    @Override
    public int getTotalScore() {
        int winners = this.point;

        int totalScore = (winners * 1000) + this.bonusPoint * 10;

        if (this.bonusPoint > 0) {
            totalScore *= this.bonusPoint;
        }
        return totalScore;
    }

    @Override
    public boolean didIWin() {
        return this.point > 0;
    }
}
