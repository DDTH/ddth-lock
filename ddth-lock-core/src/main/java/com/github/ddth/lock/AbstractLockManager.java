package com.github.ddth.lock;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;

/**
 * Base implementation of {@link ILockManager}.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public abstract class AbstractLockManager implements ILockManager {

    private ConcurrentMap<String, AbstractLock> locks = new ConcurrentHashMap<String, AbstractLock>();

    public AbstractLockManager init() {
        return this;
    }

    public void destroy() {
    }

    /**
     * Creates a new lock object.
     * 
     * @param resourceId
     * @return
     */
    protected abstract AbstractLock createLockObject(String resourceId);

    /**
     * {@inheritDoc}
     */
    @Override
    public Lock getLock(String resourceId) {
        AbstractLock lock = locks.get(resourceId);
        if (lock == null) {
            lock = createLockObject(resourceId);
            AbstractLock existingLock = locks.putIfAbsent(resourceId, lock);
            if (existingLock != null) {
                lock = existingLock;
            }
        }
        return lock;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unlock(Lock lock) {
        lock.unlock();
    }
}
