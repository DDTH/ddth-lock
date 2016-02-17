ddth-lock
=========

DDTH's API for shared lock(s).

Project home:
[https://github.com/DDTH/ddth-lock](https://github.com/DDTH/ddth-lock)

OSGi environment: `ddth-lock` is packaged as an OSGi bundle.


## License ##

See LICENSE.txt for details. Copyright (c) 2016 Thanh Ba Nguyen.

Third party libraries are distributed under their own licenses.


## Installation ##

Latest release version: `0.1.0`. See [RELEASE-NOTES.md](RELEASE-NOTES.md).

Maven dependency: if only a sub-set of `ddth-lock` functionality is used, choose the corresponding
dependency artifact(s) to reduce the number of unused jar files.

*ddth-lock-core*: in-memory locks, all other dependencies *optional*.

```xml
<dependency>
	<groupId>com.github.ddth</groupId>
	<artifactId>ddth-lock-core</artifactId>
	<version>0.1.0</version>
</dependency>
```

*ddth-lock-zk*: distributed lock implementation based on Apache ZooKeeper, include all *ddth-lock-core* and ZooKeeper dependencies.

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-lock-zk</artifactId>
    <version>0.1.0</version>
    <type>pom</type>
</dependency>
```


## Usage ##

1. Obtain the lock manager

```java
// in-memory lock manager: at one time, only one thread within the JVM can acquire the lock.
ILockManager lm = new InmemLockManager().init();

// ZooKeeper-based lock manager: at one time, only one thread (across JVMs) can acquire the lock.
ZooKeeperClient zkClient = new ZooKeeperClient();
{
    zkClient.setConnectString("localhost:2181").setSessionTimeout(5000);
    zkClient.init();
}
ZkLockManager lm = new ZkLockManager();
{
    lm.setZkClient(zkClient);
    lm.init();
}
```


2. Lock/Unlock

```java
java.util.concurrent.locks.Lock lock = lm.getLock("resource-id");

lock.lock();
try {
   ...
} finally {
    lock.unlock();
    //or lm.unlock(lock);
}
...
```

3. Destroy the lock manager when done

```java
((AbstractLockManager)manager).destroy();
```
