package rl.util;

import rl.util.asciiPanel.AsciiPanel;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by bison on 02-01-2016.
 */
public class XPFile {
    int version;
    int noLayers;
    ArrayList<XPLayer> layers;

    public XPFile(int version, int noLayers, ArrayList<XPLayer> layers) {
        this.version = version;
        this.noLayers = noLayers;
        this.layers = layers;
    }

    public void draw(AsciiPanel terminal)
    {
        if(layers.size() < 1)
            return;
        layers.get(0).draw(terminal);
    }

    public XPLayer layer(int i)
    {
        return layers.get(i);
    }

    public int noLayers()
    {
        return noLayers;
    }
}
