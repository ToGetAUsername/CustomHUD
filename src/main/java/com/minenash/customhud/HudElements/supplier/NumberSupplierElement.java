package com.minenash.customhud.HudElements.supplier;

import com.minenash.customhud.HudElements.interfaces.HudElement;
import com.minenash.customhud.HudElements.interfaces.NumElement;
import com.minenash.customhud.data.Flags;
import net.minecraft.stat.StatFormatter;

import java.util.function.Supplier;

public class NumberSupplierElement implements HudElement, NumElement {

    public record Entry(Supplier<Number> supplier, int precision, StatFormatter formatter) {}
    public static Entry of(Supplier<Number> supplier, int precision) {
        return new Entry(supplier, precision, null);
    }
    public static Entry of(Supplier<Number> supplier, int precision, StatFormatter formatter) {
        return new Entry(supplier, precision, formatter);
    }

    public static final StatFormatter HEX = (value) -> Integer.toHexString(value).toUpperCase();

    private final Supplier<Number> supplier;
    private final int precision;
    private final double scale;
    private StatFormatter formatter;

    public NumberSupplierElement(Entry entry, Flags flags) {
        this(entry.supplier, flags);
        if (flags.formatted && !flags.hex) formatter = entry.formatter;
    }

    public NumberSupplierElement(Supplier<Number> supplier, Flags flags) {
        this.supplier = supplier;
        this.precision = flags.precision == -1 ? 0 : flags.precision;
        this.scale = flags.scale;
        this.formatter = flags.hex ? HEX : null;
    }

    @Override
    public int getPrecision() {
        return precision;
    }

    @Override
    public String getString() {
        try {
            double num = supplier.get().doubleValue() * scale;
            return NumElement.formatString(num, formatter, precision);

        }
        catch (Exception _e) {
            return "-";
        }
    }

    @Override
    public Number getNumber() {
        return sanitize(supplier, Double.NaN);
    }

    @Override
    public boolean getBoolean() {
        return sanitize(supplier, Double.NaN).doubleValue() > 0;
    }

}
