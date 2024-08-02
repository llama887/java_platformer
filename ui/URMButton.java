package ui;

import components.Animation;

public class URMButton extends Button {
    private static final int URM_BUTTON_WIDTH_DEFAULT = 56, URM_BUTTON_HEIGHT_DEFAULT = 56;

    public URMButton(int xPosition, int yPosition, int atlasRow) {
        super(xPosition, yPosition, URM_BUTTON_WIDTH_DEFAULT, URM_BUTTON_HEIGHT_DEFAULT,
                new Animation("assets/urm_buttons.png", 0, URM_BUTTON_WIDTH_DEFAULT, URM_BUTTON_HEIGHT_DEFAULT,
                        new int[][] { { 0, atlasRow }, { 1, atlasRow }, { 2, atlasRow } }));
    }
}
