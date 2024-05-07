package com.minenash.customhud.mixin;

import com.minenash.customhud.ProfileManager;
import com.minenash.customhud.complex.ComplexData;
import com.minenash.customhud.CustomHud;
import com.minenash.customhud.data.Profile;
import com.minenash.customhud.errors.Errors;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

import static com.minenash.customhud.CustomHud.PROFILE_FOLDER;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Shadow @Final public GameOptions options;
    @Shadow private double gpuUtilizationPercentage;

    @Shadow @Final private Window window;

    @Redirect(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;wasPressed()Z"))
    public boolean readClick(KeyBinding instance) {
        boolean p = instance.wasPressed();

        if (p && instance == options.attackKey)
            ComplexData.clicksSoFar[0]++;
        if (p && instance == options.useKey)
            ComplexData.clicksSoFar[1]++;

        return p;
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    public void init(RunArgs args, CallbackInfo ci) {
        CustomHud.delayedInitialize();
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Ljava/lang/String;format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;"))
    public void getGpuUsage(boolean tick, CallbackInfo ci) {
        ComplexData.gpuUsage = gpuUtilizationPercentage > 100 ? 100 : gpuUtilizationPercentage;
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/DebugHud;shouldShowDebugHud()Z"))
    public boolean getGpuUsageAndOtherPerformanceMetrics(DebugHud hud) {
        return hud.shouldShowDebugHud() || (ProfileManager.getActive() != null && ProfileManager.getActive().enabled.gpuMetrics);
    }

    //@Redirect(method = "onResolutionChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;setScaleFactor(D)V"))
    //public void modifyGuiScale(Window instance, double scaleFactor) {
    //    Profile p = ProfileManager.getActive();
    //    if (p != null && p.baseTheme.guiScale != null)
    //        window.setScaleFactor(p.baseTheme.getTargetGuiScale());
    //    else
    //        window.setScaleFactor(scaleFactor);
    //}


    @Unique private static boolean isFirst = true;
    @Inject(method = "onFinishedLoading", at = @At("RETURN"))
    public void reloadProfiles(MinecraftClient.LoadingContext loadingContext, CallbackInfo ci) {
        if (isFirst) {
            isFirst = false;
            return;
        }

        boolean anyHasErrors = false;
        try(Stream<Path> pathsStream = Files.list(PROFILE_FOLDER).sorted(Comparator.comparing(p -> p.getFileName().toString()))) {
            for (Path path : pathsStream.toList()) {
                if (!Files.isDirectory(path)) {
                    String name = path.getFileName().toString();
                    if (name.endsWith(".txt")) {
                        name = name.substring(0, name.length() - 4);
                        ProfileManager.replace(Profile.parseProfile(path, name));
                        if (Errors.hasErrors(name)) {
                            anyHasErrors = true;
                            CustomHud.showToast(name);
                        }
                    }
                }
            }
            if (!anyHasErrors) {
                CustomHud.showAllUpdatedToast();
            }
        } catch (IOException e) {
            CustomHud.logStackTrace(e);
        }
    }

}
