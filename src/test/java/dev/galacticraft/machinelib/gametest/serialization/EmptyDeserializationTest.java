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

package dev.galacticraft.machinelib.gametest.serialization;

import dev.galacticraft.machinelib.api.gametest.GameUnitTest;
import dev.galacticraft.machinelib.api.gametest.annotation.UnitTest;
import dev.galacticraft.machinelib.impl.machine.MachineConfigurationImpl;
import dev.galacticraft.machinelib.impl.machine.MachineIOConfigImpl;
import dev.galacticraft.machinelib.impl.machine.SecuritySettingsImpl;
import net.minecraft.nbt.CompoundTag;

public class EmptyDeserializationTest extends GameUnitTest<Void> {
    public EmptyDeserializationTest() {
        super("empty_deserialization", null);
    }

    @UnitTest
    public void securitySettings() {
        SecuritySettingsImpl securitySettings = new SecuritySettingsImpl();
        securitySettings.readTag(new CompoundTag());
    }

    @UnitTest
    public void ioConfiguration() {
        MachineIOConfigImpl ioConfig = new MachineIOConfigImpl();
        ioConfig.readTag(new CompoundTag());
    }

    @UnitTest
    public void machineConfiguration() {
        MachineConfigurationImpl machineConfiguration = new MachineConfigurationImpl();
        machineConfiguration.readTag(new CompoundTag());
    }
}
