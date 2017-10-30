package com.andres_k.custom.component.resourceComponents.resources.data;

import com.andres_k.custom.component.resourceComponents.resources.ESprites;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.resourceComponents.resources.data.DataManager;
import com.andres_k.gameToolsLib.components.resourceComponents.resources.factory.AnimatorFactory;
import com.andres_k.custom.component.resourceComponents.resources.factory.AnimatorGameFactory;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

import java.util.HashMap;

/**
 * Created by andres_k on 13/03/2015.
 */
public class AnimatorGameData extends DataManager {
    private AnimatorFactory animatorFactory;
    private HashMap<EGameObject, AnimatorController> playerAnimator;
    private HashMap<EGameObject, AnimatorController> itemAnimator;

    public AnimatorGameData() {
        this.animatorFactory = new AnimatorGameFactory();
        this.playerAnimator = new HashMap<>();
        this.itemAnimator = new HashMap<>();
    }

    @Override
    public void prerequisite() throws NoSuchMethodException, SlickException, JSONException {
        this.initialiseMethods();
    }

    @Override
    protected void initialiseMethods() throws NoSuchMethodException {
        this.methods.clear();
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initPlayers")));
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initItems")));
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initItems2")));
    }

    @Override
    public String success() {
        return "> Game complete";
    }

    public void initPlayers() throws NoSuchMethodException, SlickException, JSONException {
        this.addPlayerAnimator(this.animatorFactory.getAnimator(ESprites.PLAYER), EGameObject.PLAYER);
    }


    public void initItems() throws NoSuchMethodException, SlickException, JSONException {
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.GROUND), EGameObject.GROUND);
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.WALL), EGameObject.WALL);
    }

    public void initItems2() throws NoSuchMethodException, SlickException, JSONException {
    }

    private void addPlayerAnimator(AnimatorController animatorController, EGameObject type) {
        this.playerAnimator.put(type, animatorController);
    }

    private void addItemAnimator(AnimatorController animatorController, EGameObject type) {
        this.itemAnimator.put(type, animatorController);
    }


    // GETTERS
    public AnimatorController getAnimator(EGameObject index) throws SlickException {
        if (this.playerAnimator.containsKey(index)) {
            return new AnimatorController(this.playerAnimator.get(index));
        } else if (this.itemAnimator.containsKey(index)) {
            return new AnimatorController(this.itemAnimator.get(index));
        }
        throw new SlickException("[ERROR]: The content of " + index.getValue() + " is missing.");
    }
}

