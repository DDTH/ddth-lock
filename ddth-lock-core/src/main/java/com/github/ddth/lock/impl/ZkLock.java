package com.github.ddth.lock.impl;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import com.github.ddth.lock.AbstractLock;
import com.github.ddth.zookeeper.ZooKeeperClient;

/**
 * ZooKeeper-based lock.
 * 
 * <p>
 * This implementation utilizes Apache ZooKeeper (http://zookeeper.apache.org)
 * to hold lock data. Note: Only one thread can acquire the lock across multiple
 * JVMs.
 * </p>
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class ZkLock extends AbstractLock {

    private ZooKeeperClient zkClient;
    private String parentNodePath;

    public ZkLock(ZooKeeperClient zkClient, String parentNodePath, String resourceId) {
        super(resourceId);
        setZkClient(zkClient);
        setParentNodePath(parentNodePath);
    }

    public ZkLock setZkClient(ZooKeeperClient zkClient) {
        this.zkClient = zkClient;
        return this;
    }

    protected ZooKeeperClient getZkClient() {
        return zkClient;
    }

    public String getParentNodePath() {
        return parentNodePath;
    }

    public ZkLock setParentNodePath(String parentNodePath) {
        this.parentNodePath = parentNodePath;
        return this;
    }

    private InterProcessMutex mutex;

    synchronized private InterProcessMutex getMutex() {
        if (mutex == null) {
            CuratorFramework client = zkClient.getCuratorFramework();
            String path = parentNodePath + "/" + getResourceId();
            mutex = new InterProcessMutex(client, path);
        }
        return mutex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void lock() {
        final InterProcessMutex mutex = getMutex();
        try {
            mutex.acquire();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {
        lock();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean tryLock() {
        return tryLock(10, TimeUnit.MILLISECONDS);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean tryLock(long time, TimeUnit unit) {
        final InterProcessMutex mutex = getMutex();
        try {
            return mutex.acquire(time, unit);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unlock() {
        final InterProcessMutex mutex = getMutex();
        try {
            mutex.release();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
