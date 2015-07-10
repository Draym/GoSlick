package com.andres_k.components.gameComponents.animations;

import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 13/03/2015.
 */
public class AnimatorGameFactory extends AnimatorFactory {
    public Animator getAnimator(EnumSprites index) throws SlickException {
        return null;
    }

    public Animator getItemAnimator(EnumSprites index) throws SlickException {
        Animator animator = new Animator();

        return animator;
    }

}
