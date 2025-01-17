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

package dev.galacticraft.machinelib.api.storage.slot;

import com.mojang.datafixers.util.Pair;
import dev.galacticraft.machinelib.api.storage.ResourceFilter;
import dev.galacticraft.machinelib.api.storage.ResourceFilters;
import dev.galacticraft.machinelib.api.storage.slot.display.ItemSlotDisplay;
import dev.galacticraft.machinelib.impl.storage.slot.ItemResourceSlotImpl;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ItemResourceSlot extends ResourceSlot<Item, ItemStack>, ContainerItemContext {
    @Contract(" -> new")
    static @NotNull Builder builder() {
        return new Builder();
    }

    @Contract("_, _ -> new")
    static @NotNull ItemResourceSlot create(@NotNull ItemSlotDisplay display, @NotNull ResourceFilter<Item> filter) {
        return create(display, filter, 64);
    }

    @Contract("_, _, _ -> new")
    static @NotNull ItemResourceSlot create(@NotNull ItemSlotDisplay display, @NotNull ResourceFilter<Item> filter, int capacity) {
        return create(display, filter, filter, capacity);
    }

    @Contract("_, _, _ -> new")
    static @NotNull ItemResourceSlot create(@NotNull ItemSlotDisplay display, @NotNull ResourceFilter<Item> filter, @NotNull ResourceFilter<Item> strictFilter) {
        return create(display, filter, strictFilter, 64);
    }

    @Contract("_, _, _, _ -> new")
    static @NotNull ItemResourceSlot create(@NotNull ItemSlotDisplay display, @NotNull ResourceFilter<Item> filter, @NotNull ResourceFilter<Item> strictFilter, int capacity) {
        if (capacity < 0 || capacity > 64) throw new IllegalArgumentException();
        return new ItemResourceSlotImpl(display, filter, strictFilter, capacity);
    }

    @NotNull ItemSlotDisplay getDisplay();

    @Override
    long getAmount();

    final class Builder {
        private int x = 0;
        private int y = 0;
        private @Nullable Pair<ResourceLocation, ResourceLocation> icon = null;

        private ResourceFilter<Item> filter = ResourceFilters.any();
        private ResourceFilter<Item> strictFilter = null;
        private int capacity = 64;

        @Contract(pure = true)
        private Builder() {
        }

        @Contract("_, _ -> this")
        public @NotNull Builder pos(int x, int y) {
            this.x(x);
            this.y(y);
            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public @NotNull Builder x(int x) {
            this.x = x;
            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public @NotNull Builder y(int y) {
            this.y = y;
            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public @NotNull Builder icon(@Nullable Pair<ResourceLocation, ResourceLocation> icon) {
            this.icon = icon;
            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public @NotNull Builder filter(@NotNull ResourceFilter<Item> filter) {
            this.filter = filter;
            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public @NotNull Builder strictFilter(@Nullable ResourceFilter<Item> strictFilter) {
            this.strictFilter = strictFilter;
            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public @NotNull Builder capacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        @Contract(pure = true)
        public @NotNull ItemResourceSlot build() {
            if (this.capacity <= 0) throw new IllegalArgumentException("capacity <= 0!");

            return ItemResourceSlot.create(ItemSlotDisplay.create(this.x, this.y, this.icon), this.filter, this.strictFilter == null ? this.filter : this.strictFilter, this.capacity);
        }
    }
}
