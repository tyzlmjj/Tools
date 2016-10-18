
public class ColorUtils
{
    private ColorUtils() {}

    /**
     * 获取对比度 [1~21]
     */
    public static float contrastRatio(int firstColor,int secondColor)
    {
        float contrastRatio;

        float firstLuminance = luminance(firstColor);
        float secondLuminance = luminance(secondColor);

        if(firstLuminance > secondLuminance)
        {
            contrastRatio = (firstLuminance + 0.05f)/(secondLuminance + 0.05f);
        }
        else
        {
            contrastRatio = (secondLuminance + 0.05f)/(firstLuminance + 0.05f);
        }

        return contrastRatio;
    }

    /**
     * 获取相对亮度
     */
    public static float luminance(int color)
    {
        double red = red(color) / 255.0;
        red = red < 0.03928 ? red / 12.92 : Math.pow((red + 0.055) / 1.055, 2.4);
        double green = green(color) / 255.0;
        green = green < 0.03928 ? green / 12.92 : Math.pow((green + 0.055) / 1.055, 2.4);
        double blue = blue(color) / 255.0;
        blue = blue < 0.03928 ? blue / 12.92 : Math.pow((blue + 0.055) / 1.055, 2.4);
        return (float) ((0.2126 * red) + (0.7152 * green) + (0.0722 * blue));
    }

    public static int alpha(int color) {
        return color >>> 24;
    }

    public static int red(int color) {
        return (color >> 16) & 0xFF;
    }

    public static int green(int color) {
        return (color >> 8) & 0xFF;
    }


    public static int blue(int color) {
        return color & 0xFF;
    }
}
