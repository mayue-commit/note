# 一、Handler

它是消息管理机制，所有代码都在Handler中运行，**维持着APP运行的框架，不仅仅用于线程的通信**。

实质：共享msg的内存。

在子线程中将msg发送，主线程中通过Handler处理



![image-20211025234257761](framework%E7%9F%A5%E8%AF%86%E7%82%B9.assets/image-20211025234257761.png)



## 一、主线程中的启动过程：

launcher（app）点击后    –>  zygote产生一个进程   –>   为APP创建一个JVM    –>   ActivityThrad.main()      –>   搭建环境，Looper.prepare()；Looper.loop()；死循环



> 为什么给每个应用都单独创建JVM？
>
> **单独管理资源，隔离事件，挂掉后应用不受影响**





## 二、发送Msg对象后的处理

1. 所有的Handler.sendMessage(msg)，不管有无延迟，最终都会调用sendMessageAtTime()  
2. sendMessageAtTime() 中调用enqueueMessage()把消息放进消息队列	
3. Looper.loop()通过MerssageQueue.next()取消息
4. dispatchMessage(msg)
5. handleMessage(msg)





## 三、Handler中涉及的类



### 1.MeeageQueue

由单链表实现的优先级队列

* 单链表：message.next()
* 排序算法：按照发送的时间顺序进行插入排序
* 队列：先进先出原则，永远先取头部的消息



### 2.Looper（唯一）

私有构造，并new MessageQueue()

prepare()：完成构造的初始化工作，并使用ThreadLocal保持一个线程只有一个Looper，同时因为构造中初始化了MessageQueue，所以MessageQueue唯一

loop()：开启Handler的循环机制



looper对象就是通过`ThreadLocal.set()`存放进去的，以此来保证全局唯一

```
mLooper=Looper.myLooper()	获取当前线程的looper对象

mQueue=mLooper.mQueue  获取当前的线程MsgQueue对象
```



### 3.ThreadLocal

线程隔离工具，使用TheadLoaclMap<this,value>存储线程对象，保证线程的唯一性



默认创建的`Map`对象，直接`get()`拿到的为`null`

```java
    public void test1() {
        ThreadLocal<String> threadLocal = new ThreadLocal<String>();
        System.out.println("主线程ThreadLocal:" + threadLocal.get());	//null
    }
```



重写其中的`initialValue()`，或者`set()`一个数据，拿到的就是返回值的数据

多次`set()`只会保留最后一次的数据

```java
    public void test1() {
        ThreadLocal<String> threadLocal = new ThreadLocal<String>(){
            @Nullable
            @Override
            protected String initialValue() {
                return "hello";
            }
        };
        //        threadLocal.set("hello");
        System.out.println("主线程ThreadLocal:" + threadLocal.get()); //hello
    }
```





**区别：**

子线程中对同一个`threadLocal.get()`时，如果调用了`initialValue()`，则得到该方法的返回值，否则得到的null。

```java
    public void test1() {
        final ThreadLocal<String> threadLocal = new ThreadLocal<String>(){
            @Nullable
            @Override
            protected String initialValue() {
                return "hello";
            }
        };
        System.out.println("主线程ThreadLocal:" + threadLocal.get()); //hello

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程ThreadLocal:" + threadLocal.get());	//hello
            }
        }).start();
    }
```



```java
   public void test1() {
        final ThreadLocal<String> threadLocal = new ThreadLocal<String>();
        threadLocal.set("hello"); 
        System.out.println("主线程ThreadLocal:" + threadLocal.get()); //hello

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程ThreadLocal:" + threadLocal.get()); //hello
            }
        }).start();
    }
```



原因：`get()`方法中，如果没有获取到对应线程的 `ThreadLocalMap `对象，则会调用 `setInitialValue()` 方法，该方法中获取了 `initialValue()`的值。

但需要注意，子线程中，仍然是创建了一个新的 `ThreadLocalMap `对象，不是原本的对象

```java
   //源码分析
	public T get() {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null) {
            ThreadLocalMap.Entry e = map.getEntry(this);
            if (e != null) {
                @SuppressWarnings("unchecked")
                T result = (T)e.value;
                return result;
            }
        }
        return setInitialValue();		//调用下面的方法
    }
    
   private T setInitialValue() {
        T value = initialValue();		//获取值，并设置到新线程的threadLocalMap中
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
        return value;
    }
```

```java
	//案例讲解
	public void test1() {
        final ThreadLocal<String> threadLocal = new ThreadLocal<String>() {
            @Nullable
            @Override
            protected String initialValue() {
                return "hello";
            }
        };
        System.out.println("主线程ThreadLocal:" + threadLocal.get());	//hello


        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set("world");
                System.out.println("子线程ThreadLocal:" + threadLocal.get());	//world
            }
        });
        thread1.start();
      
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("主线程ThreadLocal:" + threadLocal.get());	//hello
    }
}
```









### 4.HandlerThread

Thraed子类，在自己的线程里面创建了Looper，用于在子线程中发送消息



**优点：**

* 方便初始化和获取线程looper
* 保证了使用线程的handler一定是安全的
* 降低MainLooper的工作量



> 在synchronized(this)中，一个有wait() ，一个notifyAll()
>
> 只有当notifyAll()的代码块全部执行完成后，wait()后的代码才执行







## 四、常见问题

### 1.一个线程几个Hadnler？

0-无数个，可以无限new



### 2.一个线程有几个Looper？

1个，用ThreadLocal工具类中的Map集合保证





### 3.Handler内存泄漏原因

默认持有外部类的对象引用（即Handler持有Activity对象），enqueueMessage()的msg会持有Handler引用。

当活动onDestory()后，而Handler延迟执行的时候，JVM无法回收，就会出现该问题。





### 4.子线程newHandler需要什么

```java
	Looper.prepare();
	newHandler();
	Looper.loop();
```



### 5.子线程维护Looper，消息队列无消息的时候怎么处理？

* ==主线程中：==

​	队列满的时候，阻塞，直到next()取出消息，通知MessageQueue可以消息入队

​	Loop.loop()启动轮询器，对queue轮询，消息到达执行时间就取出，为空时队列阻塞，enqueueMessage()通知队列，取消阻塞

> ​	注意：这里是msg个数有限制，Handler个数无限制，内存满了会卡死，设计原因：防止系统的handler被阻塞，发不出消息

​	

​	阻塞：通过nativePoolOnce()实现睡眠，第二个参数为-1时无限等待，0时为立刻返回，当msg延迟执行时，到时自动唤醒

​	空消息队列时，有消息入队，通过nativeWake()唤醒



> ​	阻塞函数的真正实现是在JNI层，Looper::pollInner()中通过epoll_wait()实现，唤醒同理。



* ==子线程中：==

​	无消息调用quit()->唤醒线程，messageQueue赋值null，退出Looper.loop()

​	不调用quit()，会出现内存泄漏。





### 6.多个Handler往MessageQueue添加数据，发消息时各个Handler可能在不同线程，怎么保证线程安全？

Handler发送的msg对象，锁机制为Message类的this对象，取和插都有锁机制，插入msg根据时间进行排序，取时永远从头取，以此保证了线程的安全





### 7.Msg的创建方式

```
Message.obtain()
```

从Message线程池中拿msg对象，不断new时内存满了会卡死。

> 线程池的获取方式保证了内存的复用，防止内存抖动，该模式也称享元设计模式





### 8.死循环为什么不会导致应用卡死

这两者没有直接关系。

为了保证主线程一致处于运行状态，所以会死循环，没有消息时，Handler会将自己阻塞（block）有消息时再唤醒。

ANR的本质也是Msg，也是Handler发送的，事件5S没处理完，发送一个ANR的msg。











# 二、Binder

![image-20211025232556485](framework%E7%9F%A5%E8%AF%86%E7%82%B9.assets/image-20211025232556485.png)



**IPC角度看：**

它是进程间通信的机制，也是一个驱动，Binder.java实现了IBinder接口，继承了该类的具有==跨进程通信能力==。

> 该方式在linux中没有，是Android独有的。



**Android Drivier层角度看：**

可以理解为一种虚拟的物理设备，设备驱动是/dev/binder

> 驱动位于Linux内核中，提供了最底层的数据传递，对象标识，线程管理，调用过程控制等功能。驱动层是整个Binder机制的核心。



**Android  Nativie层看：**

Binder是创建Service Manager以及BpBinder/BBinder模型，搭建与binder驱动的桥梁



**Android Framework层看：**

Binder是各种Manager（ActivityManager、WindowManager等）和相应xxxManagerService的桥梁



**Android APP层看：**

Binder是客户端和服务端进行通信的媒介，当bindService的时候，服务端会返回一个包含了服务端业务调用的 Binder对象，通过这个Binder对象，客户端就可以获取服务端提供的服务或者数据，这里的服务包括普通服务和基于AIDL的服务





多进程优点：
				可以获取更多内存	通过命令adb shell 后getprop dalvik.vm.heapsize 就可以获得当前虚拟机的内存大小风险隔离，其他进程挂掉后不受影响



Linux常见的跨进程通信方式（IPC）：管道、信号量、socket、共享内存等。

![Binder优势](framework%E7%9F%A5%E8%AF%86%E7%82%B9.assets/Binder%E4%BC%98%E5%8A%BF.png)



> Binder的性能仅次于共享内存，优于其他方式





**关于内存**：

```
内存被划分为两块：用户空间和内核空间，用户空间是用户程序代码运行的地方，内核空间是内核代码运行的地方。
为了安全，它们是隔离的，即用户程序崩溃，内核不受影响。
32位系统中，总共访问的空间为2^32（4G），用户空间3G，内核1G。
64位系统，低位0-47位是有效的可变地址（256T），高位48-63位全补0或1，0为用户空间，1为内核空间
```

<img src="framework%E7%9F%A5%E8%AF%86%E7%82%B9.assets/image-20211025224905208.png" alt="image-20211025224905208" style="zoom: 67%;" />







## 一、Binder传输原理：



![Binder传送数据](framework%E7%9F%A5%E8%AF%86%E7%82%B9.assets/Binder%E4%BC%A0%E9%80%81%E6%95%B0%E6%8D%AE.png)



Binder通过mmap()把数据接收方和内核地址空间对应
		发送数据时，通过数据发送方的虚拟内存，将其中的虚拟内存中的内容保存到内核地址空间中的物理内存中，接收方直接取内核中的物理内存的数据即可，以此用一次拷贝完成了跨进程的数据通信

> mmap：linux通过一个虚拟内存区域与一个磁盘上的对象关联起来，初始化虚拟内存区域的内容，这个过程称为内存映射。对文件进行mmap，会在进程的虚拟内存分配地址空间，建立映射关系。之后就可以用指针的方式读写这段内存，系统就会自动写回对应的文件磁盘。











## 二、ServiceManager

因为Server端提供了很多服务（AMS,PMS），客户端调用时就需要知道每个服务的 handle ，不方便。所以有了ServiceManager，它本身也是一个服务，但其handler==0，可以通过该服务拿到其他服务的`IBinder`对象。





### 1.SM的注册过程

<img src="framework%E7%9F%A5%E8%AF%86%E7%82%B9.assets/image-20211026221549668.png" alt="image-20211026221549668" style="zoom:67%;" />



1.打开驱动（设置大小128K），内存映射

2.设置SM为守护进程，管理系统服务

​		1.创建binder_node结构体对象

​		2.proc -> binder_node 

​		3.创建work和tode -> 类似MessageQueue

3.BC_ENTER_LOOPER命令

​		1.写入状态Loop

​		2.读数据：binder_thread_read() 进入等待




**binder_init():**
	1.分配内存
	2.初始化设备
	3.放入链表 binder_devices
	
**binder_open():**
	1.创建binder_proc对象
	2.当前进程信息proc
	3.filr->private_data=proc
	4.添加到binder_procs链表中
	
**binder_mmap():**

首先在内核虚拟地址空间，申请一块与用户虚拟内存相同大小的内存；然后再申请1个page大小的物理内存，再将同一块物理内存分别映射到内核虚拟地址空间和用户虚拟内存空间，从而实现了用户空间的Buffer和内核空间的Buffer同步操作的功能。
	
	vma --进程的虚拟内存4m
	驱动定的  1M-8K
	所以binder通信的最大内存是1M-8K（intent传输大小限制也是这个）


	1.通过用户空间的虚拟内存大小  分配一块内核的虚拟内存
	2.分配一块物理内存4k
	3.把这块物理内存分别映射到用户空间的虚拟内存和内核的虚拟内存



**binder_ioctl()：**

数据操作





### 2.SM获取

 通过defaultServiceManager()方法来获取，当前进程注册服务或获取服务的过程之前，都要先调用该方法获取gDefaultServiceManager对象，该对象是单例的。创建该对象的过程包括用open()打开binder驱动设备，利用mmap()映射内核的地址空间。



- ProcessState::self() ：用于获取ProcessState对象
- getContextObject()：创建BpBinder对象，对于handler=0的BpBinder对象，存在则直接返回，不存在创建
- interface_cast<IServiceManager>()：用于获取BpServiceManager对象





### 3.注册服务

do_add_service()：

- svc_can_register()：检查sellinux权限是否满足
- find_svc()：服务检索，find_svc() 从svclist服务列表中，根据服务名遍历查找是否已经注册，已存在列表中返回相应服务名，否则返回null
- svcinfo_death()：释放服务，当查询到已存在同名的服务，先清理该服务信息，再将当前的服务加入到服务列表svclist







## 三、framework层分析

Binder的注册函数：

- register_android_os_Binder()
- register_android_os_BinderInteral()
- register_android_os_BinderProxy()























































































