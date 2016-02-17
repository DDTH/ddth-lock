package com.github.ddth.lock.impl;

import com.github.ddth.lock.AbstractLockManager;
import com.github.ddth.lock.ILockManager;

/**
 * This implementation of {@link ILockManager} creates {@link InmemLock}
 * objects.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class InmemLockManager extends AbstractLockManager {

    /**
     * {@inheritDoc}
     */
    @Override
    protected InmemLock createLockObject(String resourceId) {
        return new InmemLock(resourceId);
    }

}
