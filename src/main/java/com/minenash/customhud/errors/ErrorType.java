package com.minenash.customhud.errors;

import net.minecraft.text.*;
import net.minecraft.util.Formatting;

public enum ErrorType {
    NONE ("", null, "Good Job!"),
    HEADER ("Help", null, "Details"),
    TEST ("Uhhh", "", "The bee movie script The bee movie script The bee movie script The bee movie script The bee movie script The bee movie script The bee movie script The bee movie script The bee movie script"),
    IO ("Help", "../help", "Could not load file: §c"),
    EMPTY_VARIABLE ("Variables", "variables", "No Variable Given"),
    UNKNOWN_VARIABLE ("Variables", "variables", "Unknown Variable: §e"),
    UNKNOWN_LIST ("CHANGE ME", "CHANGE ME", "Unknown List Variable: §e"),
    UNKNOWN_VARIABLE_FLAG ("Variable Flags", "references/variable_flags", "Unknown Variable Flag: §e"),
    UNKNOWN_THEME_FLAG ("Theming", "references/theming", "Unknown Theme Option or Value"),
    UNKNOWN_COLOR ("Theming", "references/theming", "Unknown Color: §e"),
    UNKNOWN_CROSSHAIR ("Theming", "references/theming", "Unknown Crosshair: §e"),
    UNKNOWN_HUD_ELEMENT ("CHANGE ME", "CHANGE ME", "Unknown Vanilla Hud Element: §e"),
    ILLEGAL_GLOBAL_THEME_FLAG("Theming", "references/theming", "This theme option is global-only"),

    INVALID_TIME_FORMAT ("Time Formatting", "references/real_time", "Invalid Time Format: "),
    UNKNOWN_STATISTIC ("Statistics", "references/stats", "Unknown Statistic: §e"),
    UNKNOWN_ITEM_ID (null, null, "Unknown Item ID: §e"),

    UNKNOWN_SLOT ("Slots", "references/item_slots", "Unknown Slot: §e"),
    UNAVAILABLE_SLOT ("Slots", "references/item_slots", "The §e" + "§r slot is not available for players"),
    UNKNOWN_MOD ("CHANGE ME", "references/CHANGE ME", "Mod not installed: §e"),
    UNKNOWN_RESOURCE_PACK ("CHANGE ME", "references/CHANGE ME", "Resource Pack not installed: §e"),
    UNKNOWN_DATA_PACK ("CHANGE ME", "references/CHANGE ME", "Datapack not installed: §e"),
    UNKNOWN_ITEM_PROPERTY ("Item Properties", "variables#items", "Unknown Item Property: §e"),
    UNKNOWN_ATTRIBUTE ("CHANGE ME", "references/CHANGE ME", "Unknown Attribute: §e"),
    UNKNOWN_ATTRIBUTE_PROPERTY ("CHANGE ME", "references/CHANGE ME", "Unknown Attribute Property: §e"),
    UNKNOWN_TEAM_PROPERTY ("CHANGE ME", "references/CHANGE ME", "Unknown Team Property: §e"),
    UNKNOWN_OBJECTIVE_PROPERTY ("CHANGE ME", "references/CHANGE ME", "Unknown Objective Property: §e"),
    UNKNOWN_BOSSBAR_PROPERTY ("CHANGE ME", "references/CHANGE ME", "Unknown Bossbar Property: §e"),
    UNKNOWN_SCORE_PROPERTY ("CHANGE ME", "references/CHANGE ME", "Unknown Score Property: §e"),
    UNKNOWN_MOD_PROPERTY ("CHANGE ME", "references/CHANGE ME", "Unknown Mod Property: §e"),
    UNKNOWN_PACK_PROPERTY ("CHANGE ME", "references/CHANGE ME", "Unknown Pack Property: §e"),
    //TODO FIX ME
    UNKNOWN_ICON ("Icons", "references/icons", "Unknown item/texture: §e"),

    UNKNOWN_SETTING ("Settings", "references/settings", "Unknown Setting: §e"),
    UNKNOWN_KEYBIND("Settings", "references/settings", "Unknown Keybind: §e"),
    UNKNOWN_SOUND_CATEGORY ("Settings", "references/settings", "Unknown Sound Category: §e"),

    LIST_NOT_STARTED ("CHANGE ME", "CHANGE ME", "No =for: §olist§r= to end"),
    LIST_NOT_ENDED ("CHANGE ME", "CHANGE ME", "Missing =endfor="),
    CONDITIONAL_NOT_STARTED ("Conditionals", "conditionals", "No =if: §ocond§r= to "),
    CONDITIONAL_NOT_ENDED ("Conditionals", "conditionals", "Missing =endif="),
    CONDITIONAL_UNEXPECTED_VALUE ("Conditionals", "conditionals", "Unexpected Value: §e"),
    CONDITIONAL_WRONG_NUMBER_OF_TOKENS ("Conditionals", "conditionals", "Expected 4 tokens, found §e"),
    MALFORMED_CONDITIONAL ("Conditionals", "conditionals", "Malformed conditional: "),
    EMPTY_SECTION("Conditionals", "conditionals", "Empty section"),
    MALFORMED_LIST ("CHANGE ME", "CHANGE ME", "Malformed list variable: §e"),
    MALFORMED_BAR ("CHANGE ME", "CHANGE ME", "Malformed bar variable: §e"),
    MALFORMED_LOOP ("CHANGE ME", "CHANGE ME", "Malformed loop: §e"),
    MALFORMED_TIMER ("CHANGE ME", "CHANGE ME", "Malformed timer: §e"),
    EMPTY_TOGGLE ("CHANGE ME", "CHANGE ME", "No toggle name"),
    UNKNOWN_KEY("CHANGE ME", "CHANGE ME", "Invalid key name: §e"),

    NOT_A_WHOLE_NUMBER (null, null, "Not a whole number: §e"),

    REQUIRES_MODMENU ("Get Mod Menu", "https://modrinth.com/mod/modmenu", "Requires the mod §aMod Menu");

    public final String message;
    public final MutableText linkText;
    public final String link;

    ErrorType(String linkText, String link, String msg) {
        this.message = msg;
        this.linkText = linkText == null ? null : Text.literal(linkText).formatted(Formatting.AQUA, Formatting.UNDERLINE);
        this.link = "https://customhud.dev/v3/" + link;
    }
}
