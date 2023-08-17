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

package dev.galacticraft.machinelib.api.storage;

import dev.galacticraft.machinelib.api.filter.ResourceFilter;
import dev.galacticraft.machinelib.api.menu.sync.MenuSynchronizable;
import dev.galacticraft.machinelib.api.misc.Deserializable;
import dev.galacticraft.machinelib.api.misc.MutableModifiable;
import dev.galacticraft.machinelib.api.storage.slot.ResourceSlot;
import net.minecraft.nbt.ListTag;
import org.jetbrains.annotations.NotNull;

public interface ResourceStorage<Resource, Slot extends ResourceSlot<Resource>> extends Iterable<Slot>, MutableModifiable, SlottedStorageAccess<Resource, Slot>, Deserializable<ListTag>, MenuSynchronizable {
    void setListener(Runnable listener);

    Slot[] getSlots();

    @NotNull Slot getSlot(int slot);

    @NotNull ResourceFilter<Resource> getStrictFilter(int slot);
}
