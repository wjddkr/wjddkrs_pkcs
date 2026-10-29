package io.github.wjddkr.wjddkrs_pkcs;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

public class Wjddkrs_pkcs implements ModInitializer {

    @Override
    public void onInitialize() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> { var ScaleData = ScaleTypes.BASE.getScaleData(handler.player);

        ScaleData.setScaleTickDelay(0);
        ScaleData.setScale(0.0625f);
        });
    }
}

