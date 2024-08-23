package com.minenash.customhud.HudElements.icon;

import com.minenash.customhud.data.Flags;
import com.minenash.customhud.render.RenderPiece;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.PlayerSkinDrawer;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

import static com.minenash.customhud.CustomHud.CLIENT;

public class ListPlayerHeadIconElement extends IconElement {

    public ListPlayerHeadIconElement(UUID providerID, Flags flags) {
        super(flags, 10);
        this.providerID = providerID;
    }

    @Override
    public void render(DrawContext context, RenderPiece piece) {
        int y = piece.y;
        PlayerListEntry playerEntry = (PlayerListEntry) piece.value;

        if (!referenceCorner)
            y -= (10*scale-10)/2;

        PlayerEntity playerEntity = CLIENT.world.getPlayerByUuid(playerEntry.getProfile().getId());
        boolean flip = playerEntity != null && LivingEntityRenderer.shouldFlipUpsideDown(playerEntity);
        boolean hat = playerEntity != null && playerEntity.isPartVisible(PlayerModelPart.HAT);
        context.getMatrices().translate(piece.x+((int)scale) + shiftX, y + shiftY, 0);
        int size = (int)(8*scale);
        rotate(context.getMatrices(), size, size);
        PlayerSkinDrawer.draw(context, playerEntry.getSkinTextures().texture(), 0, 0, size, hat, flip);
    }

}
