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

package dev.galacticraft.machinelib.gametest;

import dev.galacticraft.machinelib.api.fluid.FluidStack;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class Assertions {
    public static void assertTrue(boolean b) {
        if (!b) {
            raise(format(true, false, 1));
        }
    }

    public static void assertFalse(boolean b) {
        if (b) {
            raise(format(false, true, 1));
        }
    }

    public static void assertEquals(boolean a, boolean b) {
        if (a != b) {
            raise(format(a, b, 1));
        }
    }

    public static void assertEquals(byte a, byte b) {
        if (a != b) {
            raise(format(a, b, 1));
        }
    }

    public static void assertEquals(short a, short b) {
        if (a != b) {
            raise(format(a, b, 1));
        }
    }

    public static void assertEquals(int a, int b) {
        if (a != b) {
            raise(format(a, b, 1));
        }
    }

    public static void assertEquals(long a, long b) {
        if (a != b) {
            raise(format(a, b, 1));
        }
    }

    public static void assertEquals(float a, float b) {
        if (a != b) {
            raise(format(a, b, 1));
        }
    }

    public static void assertEquals(double a, double b) {
        if (a != b) {
            raise(format(a, b, 1));
        }
    }

    public static void assertEquals(Object a, Object b) {
        if (!Objects.equals(a, b)) {
            raise(format(a, b, 1));
        }
    }

    //apparently itemstack does not implement Object#equals()
    public static void assertEquals(ItemStack a, ItemStack b) {
        if (a == null || b == null || !ItemStack.isSameItemSameTags(a, b) || a.getCount() != b.getCount()) {
            raise(format(a, b, 1));
        }
    }

    public static void assertEquals(FluidStack a, FluidStack b) {
        if (a.equals(b)) {
            raise(format(a, b, 1));
        }
    }

    public static void assertIdentityEquals(Object a, Object b) {
        if (a != b) {
            String aStr = "null";
            String bStr = "null";
            if (a != null) {
                aStr = a.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(a)) + "[" + a + "]";
            }
            if (b != null) {
                bStr = b.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(b)) + "[" + b + "]";
            }
            raise(format(aStr, bStr, 1));
        }
    }

    public static void assertIdentityNotEquals(Object a, Object b) {
        if (a == b) {
            String aStr = "null";
            String bStr = "null";
            if (a != null) {
                aStr = a.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(a)) + "[" + a + "]";
            }
            if (b != null) {
                bStr = b.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(b)) + "[" + b + "]";
            }
            raise(format(aStr, bStr, 1));
        }
    }

    public static void assertThrows(Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable throwable) {
            return;
        }
        raise(format("<any exception>", "<none>", 1));
    }

    public static <T extends Throwable> void assertThrows(Class<T> clazz, Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable throwable) {
            if (!clazz.isInstance(throwable)) {
                GameTestAssertException GameTestAssertException = new GameTestAssertException(format(clazz.getName(), throwable.getClass().getName(), 1));
                GameTestAssertException.addSuppressed(throwable);
                throw GameTestAssertException;
            } else {
                return;
            }
        }
        raise(format(clazz.getName(), "<none>", 1));
    }

    public static <T extends Throwable> void assertThrowsExactly(Class<T> clazz, Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable throwable) {
            if (!clazz.equals(throwable.getClass())) {
                GameTestAssertException exception = new GameTestAssertException(format(clazz.getName(), throwable.getClass().getName(), 1));
                exception.addSuppressed(throwable);
                throw exception;
            }
        }
        raise(format(clazz.getName(), "<none>", 1));
    }

    private static @NotNull String format(@Nullable Object expected, @Nullable Object found, int depth) {
        return "[Expected: " + expected + ", Found: " + found + "] (Line: " + StackWalker.getInstance().walk(s -> s.skip(depth + 1).findFirst().map(StackWalker.StackFrame::getLineNumber).orElse(-1)) + ")";
    }

    @Contract(value = "_ -> fail", pure = true)
    private static void raise(String s) {
        throw new GameTestAssertException(s); // make breakpoints easier
    }
}
