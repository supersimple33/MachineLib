package dev.galacticraft.machinelib.api.storage;

import dev.galacticraft.machinelib.api.storage.slot.ItemSlot;
import dev.galacticraft.machinelib.impl.storage.ResourceFilter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface ItemStorage {
    int size();

    ItemSlot getSlot(int slot);

    default ItemStack getStack(int slot) {
        return this.getSlot(slot).getStack();
    }
    default ItemStack copyStack(int slot) {
        return this.getSlot(slot).copyStack();
    }

    default int getAmount(int slot) {
        return this.getSlot(slot).getAmount();
    }
    default Item getItem(int slot) {
        return this.getSlot(slot).getItem();
    }

    ResourceFilter<Item> getFilter(int slot);
    boolean canPlayerInsert(int slot);
    boolean canExternalExtract(int slot);

    default void setStack(int slot, ItemStack stack) {
        this.getSlot(slot).setStack(stack);
    }

    default int insert(Item item, int amount, boolean simulate) {
        return simulate ? this.simulateInsert(item, amount) : this.insert(item, amount);
    }
    default int insert(Item item, CompoundTag tag, int amount, boolean simulate) {
        return simulate ? this.simulateInsert(item, tag, amount) : this.insert(item, tag, amount);
    }
    default int insert(ItemStack stack, boolean simulate) {
        return simulate ? this.simulateInsert(stack) : this.insert(stack);
    }
    default int insertMerge(Item item, int amount, boolean simulate) {
        return simulate ? this.simulateInsertMerge(item, amount) : this.insertMerge(item, amount);
    }
    default int insertMerge(Item item, CompoundTag tag, int amount, boolean simulate) {
        return simulate ? this.simulateInsertMerge(item, tag, amount) : this.insertMerge(item, tag, amount);
    }
    default int insertMerge(ItemStack stack, boolean simulate) {
        return simulate ? this.simulateInsertMerge(stack) : this.insertMerge(stack);
    }

    int insert(Item item, int amount);
    int insert(Item item, CompoundTag tag, int amount);
    default int insert(ItemStack stack) {
        return this.insert(stack.getItem(), stack.getTag(), stack.getCount());
    }
    int insertMerge(Item item, int amount);
    int insertMerge(Item item, CompoundTag tag, int amount);
    default int insertMerge(ItemStack stack) {
        return this.insert(stack.getItem(), stack.getTag(), stack.getCount());
    }

    int simulateInsert(Item item, int amount);
    int simulateInsert(Item item, CompoundTag tag, int amount);
    default int simulateInsert(ItemStack stack) {
        return this.simulateInsert(stack.getItem(), stack.getTag(), stack.getCount());
    }
    int simulateInsertMerge(Item item, int amount);
    int simulateInsertMerge(Item item, CompoundTag tag, int amount);
    default int simulateInsertMerge(ItemStack stack) {
        return this.simulateInsertMerge(stack.getItem(), stack.getTag(), stack.getCount());
    }

    // returns amount inserted
    default int insert(int slot, Item item, int amount, boolean simulate) {
        return this.getSlot(slot).insert(item, amount, simulate);
    }
    default int insert(int slot, Item item, CompoundTag tag, int amount, boolean simulate) {
        return this.getSlot(slot).insert(item, tag, amount, simulate);
    }
    default int insert(int slot, ItemStack stack, boolean simulate) {
        return this.getSlot(slot).insert(stack, simulate);
    }

    default int insert(int slot, Item item, int amount) {
        return this.getSlot(slot).insert(item, amount);
    }
    default int insertCopyNbt(int slot, Item item, CompoundTag tag, int amount) {
        return this.getSlot(slot).insert(item, tag, amount);
    }
    default int insert(int slot, Item item, CompoundTag tag, int amount) {
        return this.getSlot(slot).insert(item, tag, amount);
    }
    default int insert(int slot, ItemStack stack) {
        return this.getSlot(slot).insert(stack);
    }

    default int simulateInsert(int slot, Item item, int amount) {
        return this.getSlot(slot).simulateInsert(item, amount);
    }
    default int simulateInsert(int slot, Item item, CompoundTag tag, int amount) {
        return this.getSlot(slot).simulateInsert(item, tag, amount);
    }
    default int simulateInsert(int slot, ItemStack stack) {
        return this.getSlot(slot).simulateInsert(stack);
    }

    default boolean extract(Item item, boolean simulate) {
        return simulate ? this.simulateExtract(item) : this.extract(item);
    } // returns true if one was extracted
    default boolean extractExact(Item item, int amount, boolean simulate) {
        return simulate ? this.simulateExtractExact(item, amount) : this.extractExact(item, amount);
    }
    // returns amount extracted
    default int extract(Item item, int amount, boolean simulate) {
        return simulate ? this.simulateExtract(item, amount) : this.extract(item, amount);
    }
    default int extract(Item item, CompoundTag tag, int amount, boolean simulate) {
        return simulate ? this.simulateExtract(item, tag, amount) : this.extract(item, tag, amount);
    }

    boolean extract(Item item);
    boolean extractExact(Item item, int amount);
    int extract(Item item, int amount);
    int extract(Item item, CompoundTag tag, int amount);

    boolean simulateExtract(Item item);
    boolean simulateExtractExact(Item item, int amount);
    int simulateExtract(Item item, int amount);
    int simulateExtract(Item item, CompoundTag tag, int amount);

    default boolean extract(int slot, Item item, boolean simulate) {
        return this.getSlot(slot).extract(item, simulate);
    } // returns true if one was extracted
    default boolean extractExact(int slot, Item item, int amount, boolean simulate) {
        return this.getSlot(slot).extractExact(item, amount, simulate);
    }
    // returns amount extracted
    default ItemStack extract(int slot, int amount, boolean simulate) {
        return this.getSlot(slot).extract(amount, simulate);
    }
    default int extract(int slot, Item item, int amount, boolean simulate) {
        return this.getSlot(slot).extract(item, amount, simulate);
    }
    default int extract(int slot, Item item, CompoundTag tag, int amount, boolean simulate) {
        return this.getSlot(slot).extract(item, tag, amount, simulate);
    }

    default boolean extract(int slot, Item item) {
        return this.getSlot(slot).extract(item);
    }
    default boolean extractExact(int slot, Item item, int amount) {
        return this.getSlot(slot).extractExact(item, amount);
    }
    default ItemStack extract(int slot, int amount) {
        return this.getSlot(slot).extract(amount);
    }
    default int extract(int slot, Item item, int amount) {
        return this.getSlot(slot).extract(item, amount);
    }
    default int extract(int slot, Item item, CompoundTag tag, int amount) {
        return this.getSlot(slot).extract(item, tag, amount);
    }

    default boolean simulateExtract(int slot, Item item) {
        return this.getSlot(slot).simulateExtract(item);
    }
    default boolean simulateExtractExact(int slot, Item item, int amount) {
        return this.getSlot(slot).simulateExtractExact(item, amount);
    }
    default ItemStack simulateExtract(int slot, int amount) {
        return this.getSlot(slot).simulateExtract(amount);
    }
    default int simulateExtract(int slot, Item item, int amount) {
        return this.getSlot(slot).simulateExtract(item, amount);
    }
    default int simulateExtract(int slot, Item item, CompoundTag tag, int amount) {
        return this.getSlot(slot).simulateExtract(item, tag, amount);
    }

    default ItemStack swap(int slot, ItemStack stack) {
        return this.getSlot(slot).swap(stack);
    }

    boolean isEmpty();
    default boolean isEmpty(int slot) {
        return this.getSlot(slot).isEmpty();
    }

    default long getModCount(int slot) {
        return this.getSlot(slot).getModCount();
    }
    long getModCount();
}
