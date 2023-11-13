package com.minenash.customhud.HudElements.functional;

import com.minenash.customhud.HudElements.HudElement;
import com.minenash.customhud.HudElements.list.Attributers;
import com.minenash.customhud.HudElements.list.ListProvider;
import com.minenash.customhud.data.HudTheme;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class FunctionalElement implements HudElement {

    @Override public String getString() { return null; }
    @Override public Number getNumber() { return null; }
    @Override public boolean getBoolean() { return false; }

    public static class ChangeTheme extends FunctionalElement {
        public final HudTheme theme;
        public ChangeTheme(HudTheme theme) { this.theme = theme; }
    }

    public static class ChangeColor extends FunctionalElement {
        public final int color;
        public ChangeColor(int color) { this.color = color; }
        public Integer getColor() {return color;}
    }
    public static class ChangeColorFromElement extends ChangeColor {
        public final HudElement element;
        public ChangeColorFromElement(HudElement element) {super(0); this.element = element; }
        public Integer getColor() {return Float.isNaN(element.getNumber().floatValue()) ? null : element.getNumber().intValue();}
    }

    public static class NewLine extends FunctionalElement {}
    public static class IgnoreNewLineIfSurroundedByNewLine extends FunctionalElement {}

    public static class AdvanceList extends FunctionalElement {}
    public static class PopList extends FunctionalElement {}
    public static class PushList extends FunctionalElement {
        public final List<?> values;
        public PushList(List<?> values) { this.values = values; }
    }
    public static class CreateListElement extends FunctionalElement {
        public final ListProvider provider;
        public final Attributers.Attributer attributer;
        public CreateListElement(Supplier<?> supplier, Function<?,List<?>> function, Attributers.Attributer attributer) {
            this.provider = new ListProvider.ListFunctioner(supplier,function);
            this.attributer = attributer;
            Attributers.ATTRIBUTER_MAP.put(provider, attributer);
        }
    }

}
