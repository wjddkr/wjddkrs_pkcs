package io.github.wjddkr.wjddkrs_pkcs;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;


public class Wjddkrs_pkcs implements ModInitializer {

    @Override
    public void onInitialize() {
        //플레이어 크기 설정
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            var scaleData = ScaleTypes.BASE.getScaleData(handler.player);

            scaleData.setScaleTickDelay(0);
            scaleData.setScale(0.0625f);
        });

        //엔티티 크기 설정
        ServerEntityEvents.ENTITY_LOAD.register((entity, server) -> {

            if (server.getRandom().nextFloat() < 0.99f &&
                    entity.spawnReason() != EntitySpawnReason.LOAD &&
                    entity.spawnReason() != EntitySpawnReason.BREEDING &&
                    entity.spawnReason() != EntitySpawnReason.DIMENSION_TRAVEL &&
                    entity.getType() != EntityType.END_CRYSTAL) {

                var scaleData = ScaleTypes.BASE.getScaleData(entity);

                scaleData.setScaleTickDelay(0);
                scaleData.setScale(0.0625f);

                System.out.println(entity.getType() + " / " + entity.spawnReason());
            }
        });

        // 커맨드 /chisel
        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) -> {
                    dispatcher.register(
                            Commands.literal("chisel")
                                    .executes(context -> {
                                        var source = context.getSource();

                                        try {
                                            var item = BuiltInRegistries.ITEM.getValue(
                                                    net.minecraft.resources.Identifier.parse(
                                                            "chiselsandbits:chisel_iron"
                                                    )
                                            );

                                            if (item == net.minecraft.world.item.Items.AIR) {
                                                source.sendFailure(
                                                        Component.literal(
                                                                "Download Chisel & Bits"
                                                        )
                                                );
                                                return 0;
                                            }

                                            var stack = new ItemStack(item);
                                            stack.set(
                                                    DataComponents.UNBREAKABLE,
                                                    net.minecraft.util.Unit.INSTANCE
                                            );

                                            var player = source.getPlayerOrException();

                                            if (!player.getInventory().add(stack)) {
                                                player.drop(stack, false);
                                            }

                                            return 1;

                                        } catch (Exception e) {
                                            source.sendFailure(
                                                    Component.literal(
                                                            "Download Chisel & Bits"
                                                    )
                                            );
                                            return 0;
                                        }
                                    })
                    );
                }
        );
    }
    }


