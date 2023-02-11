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

package dev.galacticraft.machinelib.gametest.storage.builder;

import dev.galacticraft.machinelib.api.gametest.GameUnitTest;
import dev.galacticraft.machinelib.api.gametest.annotation.UnitTest;
import dev.galacticraft.machinelib.api.storage.ResourceFilters;
import dev.galacticraft.machinelib.api.storage.slot.FluidResourceSlot;
import dev.galacticraft.machinelib.gametest.Assertions;
import net.minecraft.gametest.framework.GameTestGenerator;
import net.minecraft.gametest.framework.TestFunction;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class FluidResourceSlotBuilderTest extends GameUnitTest<FluidResourceSlot.Builder> {
    public FluidResourceSlotBuilderTest() {
        super("fluid_resource_slot_builder", FluidResourceSlot::builder);
    }

    @Override
    @GameTestGenerator
    public @NotNull List<TestFunction> generateTests() {
        return super.generateTests();
    }

    @UnitTest
    public void no_capacity(FluidResourceSlot.Builder builder) {
        //noinspection ResultOfMethodCallIgnored
        Assertions.assertThrows(builder::build);
    }

    @UnitTest
    public void negative_capacity(FluidResourceSlot.Builder builder) {
        //noinspection ResultOfMethodCallIgnored
        Assertions.assertThrows(() -> builder.capacity(-1).build());
    }

    @UnitTest
    public void highCapacity(FluidResourceSlot.Builder builder) {
        FluidResourceSlot slot = builder.capacity(81000 * 64).build();

        Assertions.assertEquals(81000 * 64, slot.getCapacity());
    }

    @UnitTest
    public void displayPosition(FluidResourceSlot.Builder builder) {
        FluidResourceSlot slot = builder.capacity(1).pos(11, 43).build();

        Assertions.assertEquals(11, slot.getDisplay().x());
        Assertions.assertEquals(43, slot.getDisplay().y());
    }

    @UnitTest
    public void displayPositionXY(FluidResourceSlot.Builder builder) {
        FluidResourceSlot slot = builder.capacity(1).x(5).y(7).build();

        Assertions.assertEquals(5, slot.getDisplay().x());
        Assertions.assertEquals(7, slot.getDisplay().y());
    }

    @UnitTest
    public void filter(FluidResourceSlot.Builder builder) {
        FluidResourceSlot slot = builder.capacity(1).filter(ResourceFilters.none()).build();

        Assertions.assertEquals(0, slot.insert(Fluids.WATER, 1));
    }

    @UnitTest
    public void reuse(FluidResourceSlot.Builder builder) {
        builder.capacity(1);
        Assertions.assertIdentityNotEquals(builder.build(), builder.build());
    }
}
