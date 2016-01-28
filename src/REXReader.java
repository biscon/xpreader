package rl.util;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

/**
 * Created by bison on 02-01-2016.
 */
public class REXReader {
    static public XPFile loadXP(String filename) throws IOException, DataFormatException {
        byte[] compressed = Files.readAllBytes(new File(filename).toPath());
        System.out.println("Size of compressed data: " + compressed.length);
        byte[] decompressed = CompressionUtils.gzipDecodeByteArray(compressed);
        System.out.println("Size of decompressed data: " + decompressed.length);
        ByteBuffer bb = ByteBuffer.wrap(decompressed);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        int version = bb.getInt();
        int no_layers = bb.getInt();
        System.out.println("version is " + version + ", layers = " + no_layers);
        ArrayList<XPLayer> layers = new ArrayList<>();

        // x-value with "index/height", and y-value with "index%height"

        for(int i=0; i < no_layers; i++) {
            XPLayer layer = new XPLayer();
            layer.width = bb.getInt();
            layer.height = bb.getInt();
            layer.data = new XPChar[layer.width][layer.height];
            for(int index = 0; index < (layer.width*layer.height); index++)
            {
                int x = index / layer.height;
                int y = index % layer.height;
                char ch = (char) bb.getInt();

                Byte fR = bb.get();
                Byte fG = bb.get();
                Byte fB = bb.get();
                Byte bR = bb.get();
                Byte bG = bb.get();
                Byte bB = bb.get();
                Color fgcol = new Color(pack(fR, fG, fB, 0));
                Color bgcol = new Color(pack(bR, bG, bB, 0));
                //System.out.println("char=" + int_ch + ", fgcol =" + fgcol + ", bgcol =" + bgcol);
                XPChar xpch = new XPChar();
                xpch.fgColor = fgcol;
                xpch.bgColor = bgcol;
                xpch.code = ch;
                layer.data[x][y] = xpch;
            }
            layers.add(layer);
        }
        XPFile xp = new XPFile(version, no_layers, layers);
        return xp;
    }

    public static int pack(int r, int g, int b, int a)
    {
        //return (c1 << 24) | (c2 << 16) | (c3 << 8) | (c4);
        int value = ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8)  |
                ((b & 0xFF) << 0);
        return value;
    }
}
