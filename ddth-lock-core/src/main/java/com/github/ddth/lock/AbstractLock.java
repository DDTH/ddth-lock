package com.github.ddth.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * A wrapper around a underlying lock.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public abstract class AbstractLock implements Lock {

    private String resourceId;

    public AbstractLock() {
    }

    public AbstractLock(String resourceId) {
        setResourceId(resourceId);
    }

    public String getResourceId() {
        return resourceId;
    }

    public AbstractLock setResourceId(String resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException("Method [lockInterruptibly] is not implemented!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException("Method [newCondition] is not implemented!");
    }
}
