package io.github.wcarmon.rx;

import io.reactivex.rxjava3.disposables.Disposable;
import org.jetbrains.annotations.Nullable;

/** utilities for RxJava */
public final class RxUtils {

    private RxUtils() {}

    /**
     * null safe close
     *
     * @param disposable to close
     */
    public static void close(@Nullable Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
