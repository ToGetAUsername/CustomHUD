package com.minenash.customhud.HudElements.list;

import com.minenash.customhud.HudElements.HudElement;
import com.minenash.customhud.HudElements.MultiElement;
import com.minenash.customhud.HudElements.functional.FunctionalElement;
import com.minenash.customhud.HudElements.icon.IconElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class ListElement implements HudElement, MultiElement {

    private static final HudElement POP_LIST_ELEMENT = new FunctionalElement.PopList();
    private static final HudElement ADVANCE_LIST_ELEMENT = new FunctionalElement.AdvanceList();

    private final Supplier<List<?>> supplier;
    private final List<HudElement> elements;

    public ListElement(Supplier<List<?>> supplier, List<HudElement> format) {
        this.supplier = supplier;
        this.elements = format;
    }

    public List<HudElement> expand() {
        if (elements == null)
            return List.of(this);
        List<?> values = supplier.get();
        if (values.isEmpty())
            return Collections.emptyList();

        List<HudElement> expanded = new ArrayList<>();
        expanded.add(new FunctionalElement.PushList(values));
        for (int i = 0; i < values.size(); i++) {
            for (HudElement element : elements) {
                if (element instanceof IconElement ie)
                    ie.setListValue(i, values.get(i));
                expanded.add(element);
            }
            expanded.add(ADVANCE_LIST_ELEMENT);
        }
        expanded.set(expanded.size()-1, POP_LIST_ELEMENT);
        return expanded;
    }

    @Override
    public String getString() {
        return getNumber().toString();
    }

    @Override
    public Number getNumber() {
        return supplier.get().size();
    }

    @Override
    public boolean getBoolean() {
        return supplier.get().isEmpty();
    }

    public static class MultiLineBuilder {
        private static final Supplier<List<?>> EMPTY = () -> Collections.EMPTY_LIST;

        public final Supplier<List<?>> supplier;
        private final List<HudElement> elements = new ArrayList<>();

        public MultiLineBuilder(Supplier<List<?>> supplier) {
            this.supplier = supplier == null ? EMPTY : supplier;
        }

        public void add(HudElement element) {
            this.elements.add(element);
        }

        public void addAll(List<HudElement> elements) {
            this.elements.addAll(elements);
        }

        public ListElement build() {
            return new ListElement(supplier, elements);
        }

    }

}
