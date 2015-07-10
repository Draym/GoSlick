package com.andres_k.components.graphicComponents.userInterface.overlay.windowOverlay;

import com.andres_k.components.gameComponents.animations.AnimatorOverlayData;
import com.andres_k.components.graphicComponents.input.EnumInput;
import com.andres_k.components.graphicComponents.input.InputData;
import com.andres_k.components.graphicComponents.sounds.MusicController;
import com.andres_k.components.graphicComponents.sounds.SoundController;
import com.andres_k.components.graphicComponents.userInterface.elements.InterfaceElement;
import com.andres_k.components.graphicComponents.userInterface.elements.generic.GenericElement;
import com.andres_k.components.graphicComponents.userInterface.elements.table.TableMenuElement;
import com.andres_k.components.graphicComponents.userInterface.overlay.EnumOverlayElement;
import com.andres_k.components.graphicComponents.userInterface.overlay.Overlay;
import com.andres_k.components.graphicComponents.userInterface.tools.elements.*;
import com.andres_k.components.graphicComponents.userInterface.tools.items.BodyRect;
import com.andres_k.components.graphicComponents.userInterface.tools.items.StringTimer;
import com.andres_k.components.networkComponents.MessageModel;
import com.andres_k.components.taskComponent.EnumTargetTask;
import com.andres_k.components.taskComponent.TaskFactory;
import com.andres_k.utils.configs.WindowConfig;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.stockage.Tuple;
import com.andres_k.utils.tools.ColorTools;
import com.andres_k.utils.tools.Debug;
import com.andres_k.utils.tools.StringTools;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * Created by andres_k on 20/06/2015.
 */
public class InterfaceOverlay extends Overlay {

    public InterfaceOverlay() throws JSONException {
        super();

        WindowConfig.initWindow1();
        this.initElements();
        this.initPreference();
    }

    @Override
    public void initElements() {
        float menuX = (WindowConfig.getSizeX() / 2) - 150;
        float menuY = (WindowConfig.getSizeY() / 2) - 150;

        this.elements.put(EnumOverlayElement.TABLE_MENU_CONTROLS, new TableMenuElement(EnumOverlayElement.TABLE_MENU_CONTROLS, this.genericSendTask,
                new BodyRect(new Rectangle(menuX, menuY, 400, 300), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREYBLUE))));
        this.elements.put(EnumOverlayElement.TABLE_MENU_SETTINGS, new GenericElement(EnumOverlayElement.TABLE_MENU_SETTINGS,
                new BodyRect(new Rectangle(menuX, menuY, 300, 310), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREYBLUE)), new Pair<>(false, true), false, new boolean[]{true, true}));

        this.elements.put(EnumOverlayElement.TABLE_MENU_NEWGAME, new GenericElement(EnumOverlayElement.TABLE_MENU_NEWGAME, this.genericSendTask,
                new BodyRect(new Rectangle(menuX, menuY, 300, 180), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREYBLUE)), new Pair<>(false, true), false, new boolean[]{true, true}));
        this.elements.put(EnumOverlayElement.TABLE_MENU, new GenericElement(EnumOverlayElement.TABLE_MENU, this.genericSendTask,
                new BodyRect(new Rectangle(menuX, menuY, 300, 240), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREYBLUE)), new Pair<>(true, true), false, new boolean[]{true, true}));



        this.elements.put(EnumOverlayElement.TABLE_MENU_HOME, new GenericElement(EnumOverlayElement.TABLE_MENU_HOME, this.genericSendTask,
                new BodyRect(new Rectangle(50, 220, 350, 170), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREYBLUE)), new Pair<>(false, false), true, new boolean[]{true, true}));
    }

    @Override
    public void initElementsComponent(AnimatorOverlayData animatorOverlayData) {
        this.animatorOverlayData = animatorOverlayData;

        this.initTableMenu();
        this.initTableMenuAcceuil();
        this.initTableMenuNew();
        this.initTableMenuControls();
        this.initTableMenuSettings();
    }

    @Override
    public void enter() {
        this.initTableMenuControls();
    }

    private void initTableMenuAcceuil(){
        InterfaceElement table = this.elements.get(EnumOverlayElement.TABLE_MENU_HOME);

        table.doTask(new ButtonElement(new ImageElement(new BodyRect(new Rectangle(table.getBody().getMinX() + 20, table.getBody().getMinY() + 20, table.getBody().getSizeX() - 40, 60), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREYBLUE)),
                this.animatorOverlayData.getAnimator(EnumOverlayElement.NEW_GAME), Element.PositionInBody.MIDDLE_MID), EnumOverlayElement.TABLE_MENU_NEWGAME));
        table.doTask(new ButtonElement(new ImageElement(new BodyRect(new Rectangle(table.getBody().getMinX() + 20, table.getBody().getMinY() + 90, table.getBody().getSizeX() - 40, 60), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREYBLUE)),
                this.animatorOverlayData.getAnimator(EnumOverlayElement.SCORE), Element.PositionInBody.MIDDLE_MID), EnumOverlayElement.SCORE));
    }

    public void initTableMenuNew() {
        InterfaceElement tableMenuNew = this.elements.get(EnumOverlayElement.TABLE_MENU_NEWGAME);

        float posX = tableMenuNew.getBody().getMinX();
        float posY = tableMenuNew.getBody().getMinY();


        posY += (StringTools.charSizeY());
        tableMenuNew.doTask(new StringElement(new BodyRect(new Rectangle(posX + 20, posY, tableMenuNew.getBody().getSizeX() + 20, StringTools.charSizeY())), new StringTimer("Nb Players"), Color.black, Element.PositionInBody.LEFT_MID));
        tableMenuNew.doTask(new SelectionField(new BodyRect(new Rectangle(posX + 150, posY, 70, StringTools.charSizeY()), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREYBLUE)),
                new StringElement(new StringTimer(""), Color.black, Element.PositionInBody.LEFT_MID), EnumOverlayElement.SELECT_FIELD.getValue() + EnumOverlayElement.NEW.getValue() + "nbPlayer", true));
        posY += (StringTools.charSizeY() * 2);
        tableMenuNew.doTask(new StringElement(new BodyRect(new Rectangle(posX + 20, posY, tableMenuNew.getBody().getSizeX() + 20, StringTools.charSizeY())), new StringTimer("Speed Game"), Color.black, Element.PositionInBody.LEFT_MID));
        tableMenuNew.doTask(new SelectionField(new BodyRect(new Rectangle(posX + 150, posY, 70, StringTools.charSizeY()), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREYBLUE)),
                new StringElement(new StringTimer(""), Color.black, Element.PositionInBody.LEFT_MID), EnumOverlayElement.SELECT_FIELD.getValue() + EnumOverlayElement.NEW.getValue() + "speedGame", true));
        posY += (StringTools.charSizeY());

        tableMenuNew.doTask(new ButtonElement(new ImageElement(new BodyRect(new Rectangle(posX + (tableMenuNew.getBody().getSizeX() / 2) - 105, posY + 40, 220, 50), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREYBLUE)),
                this.animatorOverlayData.getAnimator(EnumOverlayElement.GO), Element.PositionInBody.MIDDLE_MID), EnumOverlayElement.GO));
    }

    private void initTableMenu() {
        InterfaceElement tableMenu = this.elements.get(EnumOverlayElement.TABLE_MENU);

        tableMenu.doTask(new ButtonElement(new ImageElement(new BodyRect(new Rectangle(tableMenu.getBody().getMinX() + 20, tableMenu.getBody().getMinY() + 20, tableMenu.getBody().getSizeX() - 40, 60), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREYBLUE)),
                this.animatorOverlayData.getAnimator(EnumOverlayElement.CONTROLS), Element.PositionInBody.MIDDLE_MID), EnumOverlayElement.TABLE_MENU_CONTROLS));
        tableMenu.doTask(new ButtonElement(new ImageElement(new BodyRect(new Rectangle(tableMenu.getBody().getMinX() + 20, tableMenu.getBody().getMinY() + 90, tableMenu.getBody().getSizeX() - 40, 60), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREYBLUE)),
                this.animatorOverlayData.getAnimator(EnumOverlayElement.SETTINGS), Element.PositionInBody.MIDDLE_MID), EnumOverlayElement.TABLE_MENU_SETTINGS));
        tableMenu.doTask(new ButtonElement(new ImageElement(new BodyRect(new Rectangle(tableMenu.getBody().getMinX() + 20, tableMenu.getBody().getMinY() + 160, tableMenu.getBody().getSizeX() - 40, 60), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREYBLUE)),
                this.animatorOverlayData.getAnimator(EnumOverlayElement.EXIT), Element.PositionInBody.MIDDLE_MID), EnumOverlayElement.EXIT));
    }

    private void initTableMenuControls() {
        InterfaceElement tableMenuControls = this.elements.get(EnumOverlayElement.TABLE_MENU_CONTROLS);

        tableMenuControls.doTask(new ButtonElement(new StringElement(new StringTimer("Controls"), Color.black,
                EnumOverlayElement.CONTROLS.getValue() + ":" + EnumOverlayElement.CONTROLS.getValue(), Element.PositionInBody.MIDDLE_MID), EnumOverlayElement.BUTTON));

        tableMenuControls.doTask(new Pair<>("clear", EnumOverlayElement.CONTROLS.getValue() + ":" + EnumOverlayElement.CONTROLS.getValue()));
        for (Map.Entry<EnumInput, String> entry : InputData.getAvailableInput().entrySet()) {
            tableMenuControls.doTask(new ButtonElement(new StringElement(new StringTimer(entry.getKey().getValue() + ":" +
                    StringTools.duplicateString(" ", 14 - entry.getKey().getValue().length()) + entry.getValue() +
                    StringTools.duplicateString(" ", 18 - entry.getValue().length())), Color.black,
                    EnumOverlayElement.CONTROLS.getValue() + ":" + entry.getKey().getValue(), Element.PositionInBody.MIDDLE_MID), EnumOverlayElement.CONTROLS));
        }
    }

    private void initTableMenuSettings() {
        InterfaceElement tableMenuSettings = this.elements.get(EnumOverlayElement.TABLE_MENU_SETTINGS);
        float posX = tableMenuSettings.getBody().getMinX();
        float posY = tableMenuSettings.getBody().getMinY();
        float sizeX = tableMenuSettings.getBody().getSizeX();

        tableMenuSettings.doTask(new StringElement(new BodyRect(new Rectangle(posX, posY, sizeX, StringTools.charSizeY())), new StringTimer("Settings"), Color.black, Element.PositionInBody.MIDDLE_MID));

        posY += (StringTools.charSizeY() * 2);
        tableMenuSettings.doTask(new StringElement(new BodyRect(new Rectangle(posX, posY, tableMenuSettings.getBody().getSizeX() / 2, StringTools.charSizeY())), new StringTimer("Sounds"), Color.black, Element.PositionInBody.MIDDLE_MID));
        posY += (StringTools.charSizeY() * 2);
        tableMenuSettings.doTask(new StringElement(new BodyRect(new Rectangle(posX, posY, (int) (tableMenuSettings.getBody().getSizeX() / 1.1), StringTools.charSizeY())), new StringTimer(String.valueOf((int) (SoundController.getVolume() * 100))), Color.black, EnumOverlayElement.SOUNDS_VALUE.getValue(), Element.PositionInBody.RIGHT_MID));
        tableMenuSettings.doTask(new ImageElement(new BodyRect(new Rectangle(posX + 10, posY + 4, 202, 12), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), EnumOverlayElement.SOUNDS_GRAPH.getValue() + EnumOverlayElement.BORDER.getValue(), Element.PositionInBody.LEFT_MID));
        tableMenuSettings.doTask(new ImageElement(new BodyRect(new Rectangle(posX + 11, posY + 5, 200, 10), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLUE)), EnumOverlayElement.SOUNDS_GRAPH.getValue(), Element.PositionInBody.LEFT_MID));

        posY += 50;
        tableMenuSettings.doTask(new StringElement(new BodyRect(new Rectangle(posX, posY, tableMenuSettings.getBody().getSizeX() / 2, StringTools.charSizeY())), new StringTimer("Musics"), Color.black, Element.PositionInBody.MIDDLE_MID));
        posY += (StringTools.charSizeY() * 2);
        tableMenuSettings.doTask(new StringElement(new BodyRect(new Rectangle(posX, posY, (int) (tableMenuSettings.getBody().getSizeX() / 1.1), StringTools.charSizeY())), new StringTimer(String.valueOf((int) (MusicController.getVolume() * 100))), Color.black, EnumOverlayElement.MUSICS_VALUE.getValue(), Element.PositionInBody.RIGHT_MID));
        tableMenuSettings.doTask(new ImageElement(new BodyRect(new Rectangle(posX + 10, posY + 4, 202, 12), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), EnumOverlayElement.MUSICS_GRAPH.getValue() + EnumOverlayElement.BORDER.getValue(), Element.PositionInBody.LEFT_MID));
        tableMenuSettings.doTask(new ImageElement(new BodyRect(new Rectangle(posX + 11, posY + 5, 200, 10), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLUE)), EnumOverlayElement.MUSICS_GRAPH.getValue(), Element.PositionInBody.LEFT_MID));

        tableMenuSettings.doTask(new Pair<>(EnumOverlayElement.SOUNDS_GRAPH.getValue(), new Pair<>("cutBody", SoundController.getVolume() / SoundController.getMaxVolume())));
        tableMenuSettings.doTask(new Pair<>(EnumOverlayElement.MUSICS_GRAPH.getValue(), new Pair<>("cutBody", MusicController.getVolume() / MusicController.getMaxVolume())));
    }

    // TASK
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Tuple) {
            Tuple<EnumTargetTask, EnumTargetTask, Object> received = (Tuple<EnumTargetTask, EnumTargetTask, Object>) arg;

            if (received.getV1().equals(EnumTargetTask.WINDOWS) && received.getV2().isIn(EnumTargetTask.INTERFACE_OVERLAY)) {

                Debug.debug("\nOVERLAY RECEIVED tuple: " + arg);
                if (received.getV3() instanceof Pair && ((Pair) received.getV3()).getV1() instanceof EnumOverlayElement) {
                    Pair<EnumOverlayElement, Object> task = (Pair<EnumOverlayElement, Object>) received.getV3();

                    List<EnumOverlayElement> targets = new ArrayList<>();
                    targets.addAll(EnumOverlayElement.getChildren(task.getV1()));
                    for (EnumOverlayElement target : targets) {
                        Debug.debug("CHIDL: " + targets.size() + " -> send to " + target);
                        if (this.elements.containsKey(target)) {
                            Debug.debug("send " + task.getV2() + " to " + target);
                            this.elements.get(target).doTask(task.getV2());
                        }
                    }
                } else {
                    Debug.debug("\n*************\nWARNING!\nyou shouldn't call this method like this : " + received.getV3());
                    for (Map.Entry<EnumOverlayElement, InterfaceElement> entry : this.elements.entrySet()) {
                        entry.getValue().doTask(received.getV3());
                    }
                }
            }
        } else if (arg instanceof Pair) {
            Debug.debug("OVERLAY RECEIVED pair: " + arg);
            if (((Pair) arg).getV2() instanceof MessageModel) {
                this.setChanged();
                this.notifyObservers(TaskFactory.createTask(EnumTargetTask.INTERFACE_OVERLAY, EnumTargetTask.INTERFACE, ((Pair) arg).getV2()));
            } else if (((Pair) arg).getV1() instanceof EnumOverlayElement && ((Pair) arg).getV2() instanceof EnumOverlayElement) {
                Pair<EnumOverlayElement, EnumOverlayElement> received = (Pair<EnumOverlayElement, EnumOverlayElement>) arg;

                if ((received.getV2() == EnumOverlayElement.EXIT || received.getV2() == EnumOverlayElement.GO) && this.elements.containsKey(received.getV1())) {
                    this.elements.get(received.getV1()).stop();
                    this.setChanged();
                    this.notifyObservers(TaskFactory.createTask(EnumTargetTask.INTERFACE_OVERLAY, EnumTargetTask.INTERFACE, received.getV2()));
                } else if (this.elements.containsKey(received.getV1()) && this.elements.containsKey(received.getV2())) {
                    this.elements.get(received.getV1()).stop();
                    this.elements.get(received.getV2()).start();
                }
            } else if (((Pair) arg).getV2() instanceof Pair) {
                Pair<EnumOverlayElement, Pair> received = (Pair<EnumOverlayElement, Pair>) arg;
                if (this.elements.containsKey(received.getV1())) {
                    this.elements.get(received.getV1()).doTask(received.getV2());
                    this.overlayConfigs.setAvailableInput(received.getV1(), this.elements.get(received.getV1()).getReachable());
                }
            }
        }
    }


    // FUNCTIONS

    public void doTask(Object task) {
        if (task instanceof Integer) {
            if ((Integer) task == EnumInput.OVERLAY_1.getIndex() || (Integer) task == EnumInput.OVERLAY_2.getIndex()) {
                String value = EnumInput.getEnumByIndex((Integer) task).getValue();
                this.current = Integer.valueOf(value.substring(value.indexOf("_") + 1)) - 1;
                this.current = (this.current < 0 ? 0 : this.current);
                this.current = (this.current > 1 ? 1 : this.current);
                this.elements.get(EnumOverlayElement.TABLE_MENU_SCREEN).doTask(this.current);
            }
        }
    }

    public boolean event(int key, char c, EnumInput type) {
//        Debug.debug("\n NEW EVENT: " + Input.getKeyName(key) + " (" + type + ")");
        for (Map.Entry<EnumOverlayElement, InterfaceElement> entry : this.elements.entrySet()) {
            boolean[] reachable = entry.getValue().getReachable();
            if (reachable[this.current]) {
                Object result = null;
                if (type == EnumInput.PRESSED) {
                    result = entry.getValue().eventPressed(key, c);
                } else if (type == EnumInput.RELEASED) {
                    result = entry.getValue().eventReleased(key, c);
                }
                // TODO: A ENVOYER AUX AUTRE JOUEURS
                if (result instanceof MessageModel) {
                    this.setChanged();
                    this.notifyObservers(TaskFactory.createTask(EnumTargetTask.INTERFACE_OVERLAY, EnumTargetTask.INTERFACE, result));
                    return true;
                } else if (result instanceof Boolean) {
                    return (Boolean) result;
                } else if (result instanceof Pair) {
                    Pair<Object, Object> task = (Pair<Object, Object>) result;

                    if (task.getV1() instanceof EnumInput && task.getV2() instanceof String) {
                        if (InputData.setAvailableInput((EnumInput) task.getV1(), (String) task.getV2())) {
                            this.elements.get(EnumOverlayElement.TABLE_MENU_CONTROLS).doTask(task.getV2());
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
