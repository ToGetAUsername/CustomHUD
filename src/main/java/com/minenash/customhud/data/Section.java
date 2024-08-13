package com.minenash.customhud.data;

import com.minenash.customhud.HudElements.interfaces.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class Section {
    private static MinecraftClient client = MinecraftClient.getInstance();
    public enum Align {LEFT, CENTER, RIGHT}

    public int xOffset = 0;
    public int yOffset = 0;
    public int width = -1;
    public Align textAlign;
    public Align sAlign;
    public boolean hideOnChat = false;

    public List<HudElement> elements = new ArrayList<>();

    public Section(Align align) { this.sAlign = this.textAlign = align; }

    public int hPaddingOffset(HudTheme theme) {
        return textAlign == Align.LEFT ? theme.padding.left() : textAlign == Align.RIGHT ? -theme.padding.right() : (theme.padding.left() - theme.padding.right())/2;
    }

    public int getStartX(int right, int lineWidth, int maxLineWidth) {
        maxLineWidth = width >= 0 ? width : maxLineWidth;
        float base = switch (textAlign) {
            case LEFT -> 0;
            case CENTER -> maxLineWidth/2F - lineWidth/2F;
            case RIGHT -> maxLineWidth-lineWidth;
        };
        return xOffset + MathHelper.ceil(switch (sAlign) {
            case LEFT -> 5 + base;
            case CENTER -> 1 + right/2F - maxLineWidth/2F + base;
            case RIGHT -> right - maxLineWidth + base;
        });
    }

    public int getSetWidthBgX(int right, int maxLineWidth) { return xOffset + MathHelper.ceil(switch (sAlign) {
        case LEFT -> 5;
        case CENTER -> 1 + right/2F - (width >= 0 ? width : maxLineWidth)/2F;
        case RIGHT -> right - (width >= 0 ? width : maxLineWidth);
    });}


    public abstract int getStartY(HudTheme theme, int lines);

    public static class Top extends Section {
        public Top(Align sAlign) { super(sAlign); }
        public int getStartY(HudTheme theme, int lines) {
            return 3 + yOffset;
        }
    }

    public static class Center extends Section {
        public Center(Align sAlign) { super(sAlign); }
        public int getStartY(HudTheme theme, int lines) {
            return (int) (client.getWindow().getScaledHeight() * (1 / theme.getScale()))/2 - (lines * (9 + theme.lineSpacing))/2 + yOffset +1;
        }
    }

    public static class Bottom extends Section {
        public Bottom(Align sAlign) { super(sAlign); }
        public int getStartY(HudTheme theme, int lines) {
            return (int) (client.getWindow().getScaledHeight() * (1 / theme.getScale())) - 6 - lines * (9 + theme.lineSpacing) + yOffset;
        }
    }

}
