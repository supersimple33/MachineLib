/*
 * Copyright (c) 2021-2023 Team Galacticraft
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

package dev.galacticraft.machinelib.api.machine.configuration;

import com.google.common.base.Preconditions;
import dev.galacticraft.machinelib.api.block.entity.MachineBlockEntity;
import dev.galacticraft.machinelib.api.compat.transfer.ExposedStorage;
import dev.galacticraft.machinelib.api.menu.sync.MenuSynchronizable;
import dev.galacticraft.machinelib.api.misc.Deserializable;
import dev.galacticraft.machinelib.api.storage.MachineEnergyStorage;
import dev.galacticraft.machinelib.api.storage.ResourceStorage;
import dev.galacticraft.machinelib.api.storage.slot.ResourceSlot;
import dev.galacticraft.machinelib.api.transfer.ResourceFlow;
import dev.galacticraft.machinelib.api.transfer.ResourceType;
import dev.galacticraft.machinelib.impl.block.face.MachineIOFaceImpl;
import dev.galacticraft.machinelib.impl.block.face.NullMachineIOFaceImpl;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;

/**
 * Represents a face of a {@link MachineBlockEntity} that has been configured to
 * accept certain types of resources.
 */
public interface MachineIOFace extends Deserializable<CompoundTag>, MenuSynchronizable {
    /**
     * Creates a new, blank {@link MachineIOFace}.
     *
     * @return A new, blank {@link MachineIOFace}.
     * @see #of(ResourceType, ResourceFlow)
     */
    @Contract(value = " -> new", pure = true)
    static @NotNull MachineIOFace blank() {
        return of(ResourceType.NONE, ResourceFlow.BOTH);
    }

    /**
     * Creates a new {@link MachineIOFace}.
     *
     * @param type The type of resource to accept.
     * @param flow The flow direction of the resource.
     * @return A new {@link MachineIOFace}.
     * @see MachineIOFaceImpl the default implementation
     */
    @Contract(value = "_, _ -> new", pure = true)
    static @NotNull MachineIOFace of(@NotNull ResourceType type, @NotNull ResourceFlow flow) {
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(flow);

        return new MachineIOFaceImpl(type, flow);
    }

    @Contract(value = " -> new", pure = true)
    static @NotNull MachineIOFace nullFace() {
        return new NullMachineIOFaceImpl();
    }

    /**
     * Configures this face to accept the given resource type and flow.
     *
     * @param type The type of resource to accept.
     * @param flow The flow direction of the resource.
     */
    @Contract(mutates = "this")
    void setOption(@NotNull ResourceType type, @NotNull ResourceFlow flow);

    /**
     * Returns the type of resource that this face is configured to accept.
     *
     * @return The type of resource that this face is configured to accept.
     */
    @Contract(pure = true)
    @NotNull ResourceType getType();

    /**
     * Returns the flow direction of this face.
     *
     * @return The flow direction of this face.
     */
    @Contract(pure = true)
    @NotNull ResourceFlow getFlow();

    @Nullable ExposedStorage<Item, ItemVariant> getExposedItemStorage(@NotNull ResourceStorage<Item, ? extends ResourceSlot<Item>> storage);

    @Nullable ExposedStorage<Fluid, FluidVariant> getExposedFluidStorage(@NotNull ResourceStorage<Fluid, ? extends ResourceSlot<Fluid>> storage);

    /**
     * Returns the exposed energy storage of this face.
     * If the type of storage is not an energy storage, this method will return {@code null}.
     *
     * @param storage The storage to use.
     * @return The exposed energy storage of this face.
     */
    @Nullable EnergyStorage getExposedEnergyStorage(@NotNull MachineEnergyStorage storage);
}