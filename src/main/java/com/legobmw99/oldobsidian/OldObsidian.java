package com.legobmw99.oldobsidian;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod("oldobsidian")
public class OldObsidian {

    public OldObsidian() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onNotify(BlockEvent.NeighborNotifyEvent e) {
        var world = e.getWorld();
        if (world.getBlockState(e.getPos()).getMaterial() == Material.LAVA) { //Make sure block updating is lava
            for (Direction side1 : Direction.values()) { //Iterate through relevant sides
                var state = world.getBlockState(e.getPos().relative(side1));
                if (state.getBlock() == Blocks.REDSTONE_WIRE && state.getValue(RedStoneWireBlock.POWER) == 0) { //See if they are unpowered redstone
                    //Iterate through the horizontal sides of the redstone
                    for (Direction side2 : Direction.Plane.HORIZONTAL) {
                        //Check if water is present
                        if (world.getBlockState(e.getPos().relative(side1).relative(side2)).getMaterial() == Material.WATER) {
                            //Set the block to obsidian and play an effect
                            world.setBlock(e.getPos().relative(side1), Blocks.OBSIDIAN.defaultBlockState(), 2);
                            world.playSound(null, e.getPos(), SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 0.5F,
                                            2.6F + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.8F);
                            //No need to keep checking this block
                            break;
                        }
                    }
                }
            }
        }
    }
}

