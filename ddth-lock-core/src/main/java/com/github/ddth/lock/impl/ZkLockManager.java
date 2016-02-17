package com.github.ddth.lock.impl;

import com.github.ddth.lock.AbstractLockManager;
import com.github.ddth.lock.ILockManager;
import com.github.ddth.zookeeper.ZooKeeperClient;

/**
 * This implementation of {@link ILockManager} creates {@link ZkLock} objects.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class ZkLockManager extends AbstractLockManager {

    private ZooKeeperClient zkClient;
    private String parentNodePath = "/ddth-lock";

    public ZkLockManager setZkClient(ZooKeeperClient zkClient) {
        this.zkClient = zkClient;
        return this;
    }

    public ZooKeeperClient getZkClient() {
        return zkClient;
    }

    public String getParentNodePath() {
        return parentNodePath;
    }

    public ZkLockManager setParentNodePath(String parentNodePath) {
        this.parentNodePath = "/" + parentNodePath.replaceAll("^\\/+", "").replaceAll("\\/+$", "");
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ZkLock createLockObject(String resourceId) {
        ZkLock lock = new ZkLock(zkClient, parentNodePath, resourceId);
        return lock;
    }

}
