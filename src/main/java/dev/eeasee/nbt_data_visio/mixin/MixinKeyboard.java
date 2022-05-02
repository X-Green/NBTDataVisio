package dev.eeasee.nbt_data_visio.mixin;

import dev.eeasee.nbt_data_visio.util.Identifiers;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public abstract class MixinKeyboard {

    @Final
    @Shadow
    private final MinecraftClient client = MinecraftClient.getInstance();

    @Inject(method = "copyLookAt", at = @At("RETURN"))
    private void onCopyLookAt(boolean hasQueryPermission, boolean queryServer, CallbackInfo ci) {
        this.client.inGameHud.getChatHud().addMessage(
                displayHintVisioScreen()
        );
    }

    private static Text displayHintVisioScreen() {
        return new LiteralText("F3+i Action Detected")
                .setStyle(
                        Style.EMPTY
                                .withClickEvent(new ClickEvent(
                                        ClickEvent.Action.OPEN_URL, Identifiers.OPEN_VISIO_SCREEN_ACTION
                                ))
                                .withHoverEvent(new HoverEvent(
                                        HoverEvent.Action.SHOW_TEXT, new LiteralText("Click to open nbt visio screen")
                                ))
                                .withUnderline(true)
                );
    }
}
