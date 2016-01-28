package rl.util;

import rl.util.asciiPanel.AsciiPanel;

import java.awt.*;

/**
 * Created by bison on 02-01-2016.
 */
public class XPLayer
{
    public int width;
    public int height;
    public XPChar[][] data;

    public void draw(AsciiPanel terminal)
    {
        for(int y=0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(data[x][y].code != 32)
                    terminal.write(data[x][y].code, x, y, data[x][y].fgColor, data[x][y].bgColor);
            }
        }
    }
}
