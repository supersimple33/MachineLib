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

package dev.galacticraft.machinelib.impl.storage.slot;

import dev.galacticraft.machinelib.api.storage.io.ResourceFlow;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public enum InputType {
    INPUT(true, false, true), // external: insertion only, players: insertion and extraction allowed
    OUTPUT(false, true, false), // external: extraction only, players: extraction only
    STORAGE(true, true, true), // external: insertion and extraction allowed, players: insertion and extraction allowed
    TRANSFER(false, false, true); // external: immutable, players: insertion and extraction allowed - e.g. battery slots

    private final boolean externalInsert;
    private final boolean externalExtract;
    private final boolean playerInsert;

    InputType(boolean externalInsert, boolean externalExtract, boolean playerInsert) {
        this.externalInsert = externalInsert;
        this.externalExtract = externalExtract;
        this.playerInsert = playerInsert;
    }

    @Contract(pure = true)
    public boolean externalExtraction() {
        return externalExtract;
    }

    @Contract(pure = true)
    public boolean externalInsertion() {
        return externalInsert;
    }

    @Contract(pure = true)
    public @Nullable ResourceFlow getExternalFlow() {
        return this.externalExtract ? this.externalInsert ? ResourceFlow.BOTH : ResourceFlow.OUTPUT : this.externalInsert ? ResourceFlow.INPUT : null;
    }

    @Contract(pure = true)
    public boolean playerInsertion() {
        return playerInsert;
    }

    @Contract(pure = true)
    public boolean playerExtraction() {
        return true;
    }
}
