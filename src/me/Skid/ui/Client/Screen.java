package me.Skid.ui.Client;

public interface Screen extends Utils {

    void initGui();

    void keyTyped(char c0, int i);

    void drawScreen(int i, int j);

    void mouseClicked(int i, int j, int k);

    void mouseReleased(int i, int j, int k);
}
