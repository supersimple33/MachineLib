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

package dev.galacticraft.machinelib.impl.storage.slot;

import dev.galacticraft.machinelib.api.transfer.cache.ModCount;
import dev.galacticraft.machinelib.impl.fluid.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

@ApiStatus.Internal
public final class FluidSlot extends ResourceSlot<Fluid, FluidVariant, FluidStack> {
    public FluidSlot(long capacity, @NotNull Predicate<FluidVariant> filter, @NotNull ModCount modCount) {
        super(capacity, filter, modCount);
    }

    @Override
    protected @NotNull FluidVariant getBlankVariant() {
        return FluidVariant.blank();
    }

    @Override
    protected long getVariantCapacity(@NotNull FluidVariant variant) {
        return Long.MAX_VALUE;
    }

    @Contract(pure = true)
    @Override
    protected @NotNull FluidStack getEmptyStack() {
        return FluidStack.EMPTY;
    }

    @Contract(pure = true)
    @Override
    protected @NotNull FluidStack createStack(@NotNull FluidVariant variant, long amount) {
        return new FluidStack(variant.getFluid(), variant.copyNbt(), amount);
    }
}