/*
 * Copyright (c) 2021-2022 Team Galacticraft
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.galacticraft.machinelib.testmod.block.entity;

import dev.galacticraft.api.block.entity.MachineBlockEntity;
import dev.galacticraft.api.machine.MachineStatuses;
import dev.galacticraft.api.machine.storage.MachineItemStorage;
import dev.galacticraft.api.machine.storage.display.ItemSlotDisplay;
import dev.galacticraft.api.screen.SimpleMachineScreenHandler;
import dev.galacticraft.machinelib.testmod.TestMod;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SimpleMachineBlockEntity extends MachineBlockEntity {
    private int ticks = -1;

    public SimpleMachineBlockEntity(@NotNull BlockPos pos, BlockState state) {
        super(TestMod.SIMPLE_MACHINE_BE_TYPE, pos, state);
    }

    @Override
    protected @NotNull MachineItemStorage createItemStorage() {
        return MachineItemStorage.builder()
                .addSlot(TestMod.CHARGE_SLOT, new ItemSlotDisplay(32, 32))
                .build();
    }

    @Override
    public long getEnergyCapacity() {
        return 50_000;
    }

    @Override
    protected void tickConstant(@NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull ProfilerFiller profiler) {
        super.tickConstant(world, pos, state, profiler);
        this.attemptChargeFromStack(0);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return SimpleMachineScreenHandler.create(syncId, player, this, TestMod.SIMPLE_MACHINE_SH_TYPE);
    }

    @Override
    protected void tick(@NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull ProfilerFiller profiler) {
        if (ticks > 0 && world.getBlockState(pos.above()).isAir()) {
            ticks--;
            profiler.push("transaction");
            try (Transaction transaction = Transaction.openOuter()){
                if (this.energyStorage().extract(100, transaction) == 100) {
                    transaction.commit();
                    if (ticks == 0) {
                        world.setBlockAndUpdate(pos.above(), Blocks.DRIED_KELP_BLOCK.defaultBlockState());
                    }
                    this.setStatus(TestMod.WORKING);
                } else {
                    ticks = -1;
                    this.setStatus(MachineStatuses.NOT_ENOUGH_ENERGY);
                }
            }
            profiler.pop();
        } else {
            if (!this.energyStorage().isEmpty() && world.getBlockState(pos.above()).isAir()) {
                ticks = 20*20;
                this.setStatus(TestMod.WORKING);
            } else {
                this.setStatus(MachineStatuses.NOT_ENOUGH_ENERGY);
            }
        }
    }
}