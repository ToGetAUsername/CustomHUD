package com.minenash.customhud.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.minenash.customhud.ProfileManager;
import com.minenash.customhud.data.HudHiddenBehavior;
import com.minenash.customhud.data.Profile;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static com.minenash.customhud.CustomHud.CLIENT;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;render(Lnet/minecraft/client/gui/DrawContext;F)V"))
    public void changeHudGuiScale(InGameHud instance, DrawContext context, float tickDelta, Operation<Void> original) {
        Profile p = ProfileManager.getActive();
        if (p == null || p.baseTheme.hudScale == null) {
            original.call(instance, context, tickDelta);
            return;
        }

        double originalScale = CLIENT.getWindow().getScaleFactor();
        double target = p.baseTheme.getTargetGuiScale();
        float scale = (float) (target/originalScale);
        CLIENT.getWindow().setScaleFactor(target);

        context.getMatrices().push();
        context.getMatrices().scale(scale, scale, 1);
        original.call(instance, context, tickDelta);
        context.getMatrices().pop();

        CLIENT.getWindow().setScaleFactor(originalScale);

    }

    @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;hudHidden:Z"), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void renderHudAnywaysIfHudHiddenBehaviorIsShown(float tickDelta, long startTime, boolean tick, CallbackInfo ci, float f, boolean bl, int i, int j, Window window, Matrix4f matrix4f, Matrix4fStack matrix4fStack, DrawContext drawContext) {
        Profile p = ProfileManager.getActive();
        if (!CLIENT.options.hudHidden || CLIENT.currentScreen != null || p == null || p.hudHiddenBehavior != HudHiddenBehavior.SHOW)
            return;

        if (p.baseTheme.hudScale == null) {
            CLIENT.inGameHud.render(drawContext, tickDelta);
            return;
        }

        double originalScale = CLIENT.getWindow().getScaleFactor();
        double target = p.baseTheme.getTargetGuiScale();
        float scale = (float) (target/originalScale);
        CLIENT.getWindow().setScaleFactor(target);

        drawContext.getMatrices().push();
        drawContext.getMatrices().scale(scale, scale, 1);
        CLIENT.inGameHud.render(drawContext, tickDelta);
        drawContext.getMatrices().pop();

        CLIENT.getWindow().setScaleFactor(originalScale);
    }

}
