package com.github.ddth.lock;

import java.util.concurrent.locks.Lock;

/**
 * Manages {@link Lock} instances.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public interface ILockManager {
    /**
     * Obtains a lock object.
     * 
     * @param resourceId
     * @return a {@link Lock} object, or {@code null} or the lock object cannot
     *         be obtained
     */
    public Lock getLock(String resourceId);

    /**
     * Releases a lock.
     * 
     * @param lock
     */
    public void unlock(Lock lock);
}
