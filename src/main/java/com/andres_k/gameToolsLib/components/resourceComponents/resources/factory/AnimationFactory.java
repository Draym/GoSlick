package com.andres_k.gameToolsLib.components.resourceComponents.resources.factory;

import com.andres_k.gameToolsLib.utils.tools.*;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.BufferedImageUtil;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by andres_k on 09/12/2015.
 */
public class AnimationFactory {

    public static Animation loadAnimation(String id, String extension, int start, int end, boolean looping, int interval) throws SlickException {
        return loadAnimation(id, extension, start, end, looping, interval, 0);
    }

    public static Animation loadAnimation(String id, String extension, int start, int end, boolean looping, int interval, int addZero) throws SlickException {
        Animation animation = new Animation();
        String zero;

        for (int i = start; i <= end; ++i) {
            zero = StringTools.duplicateString("0", MathTools.numberLevel(i, end) + addZero);
            animation.addFrame(new Image(id + zero + i + extension), interval);
        }
        animation.setLooping(looping);
        return animation;
    }


    public static Animation createAnimationFromGIF(String file, boolean looping, boolean pingpong) {
        Animation animation = new Animation();

        GIFDecoder gifDecoder = new GIFDecoder();
        gifDecoder.read(AnimationFactory.class.getClassLoader().getResourceAsStream(file));
        for (int i =0; i < gifDecoder.getFrameCount(); ++i) {
            try {
                animation.addFrame(new Image(BufferedImageUtil.getTexture("gif", gifDecoder.getFrame(i))), gifDecoder.getDelay(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        animation.setLooping(looping);
        animation.setPingPong(pingpong);
        return animation;
    }

    public static Animation createAnimation(MovieDecoder.Movie movie, boolean looping, boolean pingpong) {
        Animation animation = new Animation();

        for (BufferedImage image : movie.frames) {
            try {
                animation.addFrame(new Image(BufferedImageUtil.getTexture("movie", image)), movie.frameRate);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        animation.setLooping(looping);
        animation.setPingPong(pingpong);
        return animation;
    }

    public static Animation createAnimation(SpriteSheet spriteSheet, boolean looping, int startX, int endX, int startY, int endY, int speed[]) {
        return createAnimation(spriteSheet, looping, false, startX, endX, startY, endY, speed);
    }

    public static Animation createAnimation(SpriteSheet spriteSheet, boolean looping, boolean pingpong, int startX, int endX, int startY, int endY, int speed[]) {
        Animation animation = new Animation();
        int i = 0;

        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                animation.addFrame(spriteSheet.getSprite(x, y), speed[i]);
                ++i;
            }

        }
        animation.setLooping(looping);
        animation.setPingPong(pingpong);
        return animation;
    }

    public static Animation createStaticUniqueFrame(String file) throws SlickException {
        Animation animation = new Animation();
        Image img = new Image(file);
        animation.addFrame(img, 100);
        animation.setLooping(false);
        return animation;
    }

    public static Animation createUniqueFrame(String file, int time, boolean looping) throws SlickException {
        Animation animation = new Animation();
        Image img = new Image(file);
        animation.addFrame(img, time);
        animation.setLooping(looping);
        return animation;
    }
}
