package com.minenash.customhud.HudElements.interfaces;

import java.util.function.Supplier;

public interface HudElement {

    String getString();
    Number getNumber();
    boolean getBoolean();

    default <T> T sanitize(Supplier<T> supplier, T onFail) {
        try {
            T value = supplier.get();
            return value == null? onFail : value;
        }
        catch(Exception _e) {
            return onFail;
        }
    }



}
