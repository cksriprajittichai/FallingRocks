package non_gamepanel;

public interface PanelSwitchListener {

    int MAIN_MENU_PANEL_EXIT = 0;
    int GAME_PANEL_EXIT = 1;
    int OPTIONS_PANEL_EXIT = 2;
    int GAME_OVER_PANEL_EXIT = 3;

    void onPanelSwitch(int panelSwitchStatus);

}
