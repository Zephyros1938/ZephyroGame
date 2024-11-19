package lib;

public class HexDecoder {
    public static int[] Bit_24(int x) { // Convert integer into 24-Bit hex (TrueColor)
        int r = x >> 0x10 & 0xff;
        int g = x >> 0x08 & 0xff;
        int b = x & 0xff;

        int[] combo = { r, g, b };

        return combo;
    }

    public static int[] Bit_16(int x) { // Convert integer into 16-Bit hex
        int r = (x & 0xf00) >> 0x008;
        int g = (x & 0x0f0) >> 0x004;
        int b = x & 0x00f;

        r = r << 4 | r;
        g = g << 4 | g;
        b = b << 4 | b;

        int[] combo = { r, g, b };

        return combo;
    }
}