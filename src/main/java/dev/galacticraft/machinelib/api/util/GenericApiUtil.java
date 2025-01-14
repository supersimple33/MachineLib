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

package dev.galacticraft.machinelib.api.util;

import dev.galacticraft.machinelib.api.storage.slot.ResourceSlot;
import dev.galacticraft.machinelib.api.storage.slot.SlotGroup;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.TransferVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

/**
 * Utility methods for dealing with {@link Storage storages}.
 */
public final class GenericApiUtil {
    @Contract(value = " -> fail", pure = true)
    private GenericApiUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static <Resource, Variant extends TransferVariant<Resource>, S extends Storage<Variant>> long move(Variant variant, @Nullable SlotGroup<Resource, ?, ?> from, @Nullable S to, long maxAmount, @Nullable TransactionContext context) {
        if (from == null || to == null || variant.isBlank() || maxAmount == 0) return 0;
        StoragePreconditions.notNegative(maxAmount);

        long maxExtracted;
        try (Transaction test = Transaction.openNested(context)) {
            maxExtracted = from.extract(variant.getObject(), variant.getNbt(), maxAmount, test);
        }

        try (Transaction moveTransaction = Transaction.openNested(context)) {
            long accepted = to.insert(variant, maxExtracted, moveTransaction);

            if (from.extract(variant.getObject(), variant.getNbt(), accepted, moveTransaction) == accepted) {
                moveTransaction.commit();
                return accepted;
            }
        }

        return 0;
    }

    public static <Resource, Variant extends TransferVariant<Resource>, S extends Storage<Variant>> long move(Variant variant, @Nullable S from, @Nullable SlotGroup<Resource, ?, ?> to, long maxAmount, @Nullable TransactionContext context) {
        if (from == null || to == null || variant.isBlank() || maxAmount == 0) return 0;
        StoragePreconditions.notNegative(maxAmount);

        long maxExtracted;
        try (Transaction test = Transaction.openNested(context)) {
            maxExtracted = from.extract(variant, maxAmount, test);
        }

        try (Transaction moveTransaction = Transaction.openNested(context)) {
            long accepted = to.insert(variant.getObject(), variant.getNbt(), maxExtracted, moveTransaction);

            if (from.extract(variant, accepted, moveTransaction) == accepted) {
                moveTransaction.commit();
                return accepted;
            }
        }

        return 0;
    }


    public static <Resource, Variant extends TransferVariant<Resource>, S extends Storage<Variant>> long move(Variant variant, @Nullable ResourceSlot<Resource, ?> from, @Nullable S to, long maxAmount, @Nullable TransactionContext context) {
        if (from == null || to == null || variant.isBlank() || maxAmount == 0) return 0;
        StoragePreconditions.notNegative(maxAmount);

        long maxExtracted;
        try (Transaction test = Transaction.openNested(context)) {
            maxExtracted = from.extract(variant.getObject(), variant.getNbt(), maxAmount, test);
        }

        try (Transaction moveTransaction = Transaction.openNested(context)) {
            long accepted = to.insert(variant, maxExtracted, moveTransaction);

            if (from.extract(variant.getObject(), variant.getNbt(), accepted, moveTransaction) == accepted) {
                moveTransaction.commit();
                return accepted;
            }
        }

        return 0;
    }

    public static <Resource, Variant extends TransferVariant<Resource>, S extends Storage<Variant>> long move(Variant variant, @Nullable S from, @Nullable ResourceSlot<Resource, ?> to, long maxAmount, @Nullable TransactionContext context) {
        if (from == null || to == null || variant.isBlank() || maxAmount == 0) return 0;
        StoragePreconditions.notNegative(maxAmount);

        long maxExtracted;
        try (Transaction test = Transaction.openNested(context)) {
            maxExtracted = from.extract(variant, maxAmount, test);
        }

        try (Transaction moveTransaction = Transaction.openNested(context)) {
            long accepted = to.insert(variant.getObject(), variant.getNbt(), maxExtracted, moveTransaction);

            if (from.extract(variant, accepted, moveTransaction) == accepted) {
                moveTransaction.commit();
                return accepted;
            }
        }

        return 0;
    }

    public static <T, S extends Storage<T>> long move(T variant, @Nullable S from, @Nullable S to, long maxAmount, @Nullable TransactionContext context) {
        if (from == null || to == null) return 0;
        StoragePreconditions.notNegative(maxAmount);

        long maxExtracted;
        try (Transaction extractionTestTransaction = Transaction.openNested(context)) {
            maxExtracted = from.extract(variant, maxAmount, extractionTestTransaction);
        }

        try (Transaction moveTransaction = Transaction.openNested(context)) {
            long accepted = to.insert(variant, maxExtracted, moveTransaction);

            if (from.extract(variant, accepted, moveTransaction) == accepted) {
                moveTransaction.commit();
                return accepted;
            }
        }

        return 0;
    }

    public static <T, S extends Storage<T>> void moveAll(@Nullable S from, @Nullable S to, long maxPerTransaction, @Nullable TransactionContext context) {
        if (from == null || to == null || !from.supportsExtraction() || !to.supportsInsertion()) return;
        StoragePreconditions.notNegative(maxPerTransaction);
        LongList list = new LongArrayList();
        try (Transaction indexingTransaction = Transaction.openNested(context)) {
            for (StorageView<T> view : from) {
                list.add(to.insert(view.getResource(), view.extract(view.getResource(), maxPerTransaction, indexingTransaction), indexingTransaction));
            }
        }

        int i = 0;
        try (Transaction transaction = Transaction.openNested(context)) {
            for (StorageView<T> view : from) {
                list.add(to.insert(view.getResource(), view.extract(view.getResource(), list.getLong(i++), transaction), transaction));
            }
            transaction.commit();
        }
    }
}
