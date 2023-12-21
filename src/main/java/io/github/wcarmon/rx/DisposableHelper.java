package io.github.wcarmon.rx;

import static java.util.Objects.requireNonNull;

import io.reactivex.rxjava3.disposables.Disposable;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Helps manage the lifecycle of {@link Disposable}s.
 *
 * <p>Aligns with java try-with-resources
 */
public final class DisposableHelper implements Disposable, Closeable {

    private final Collection<Disposable> disposables;
    private final Lock stateLock;

    public DisposableHelper() {
        this.disposables = new ArrayList<>(8);
        this.stateLock = new ReentrantLock();
    }

    public void add(Runnable cleanupFn) {
        requireNonNull(cleanupFn, "cleanupFn is required and null.");

        this.stateLock.lock();
        try {
            disposables.add(Disposable.fromRunnable(cleanupFn));

        } finally {
            this.stateLock.unlock();
        }
    }

    public void add(Disposable disposable) {
        requireNonNull(disposable, "disposable is required and null.");

        this.stateLock.lock();
        try {
            disposables.add(disposable);

        } finally {
            this.stateLock.unlock();
        }
    }

    @Override
    public void close() {
        this.dispose();
    }

    @Override
    public void dispose() {
        this.stateLock.lock();
        try {
            disposables.forEach(Disposable::dispose);
            disposables.clear();

        } finally {
            this.stateLock.unlock();
        }
    }

    @Override
    public boolean isDisposed() {
        this.stateLock.lock();
        try {
            return disposables.isEmpty();

        } finally {
            this.stateLock.unlock();
        }
    }
}
