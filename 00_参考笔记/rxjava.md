# RXJAVA

```
compile 'io.reactivex.rxjava2:rxjava:2.1.0'
compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
```



## 1.概念和原理

基于事件流的链式调用，逻辑简洁，使用简单。主要用于实现异步操作，类似`Android`中的`AsyncTask`和`Handler`。



原理是基于一种扩展的观察者模式

| 角色                   | 作用                     | 类比                       |
| ---------------------- | ------------------------ | -------------------------- |
| 被观察者（Observable） | 产生事件                 | 甲方，提需求               |
| 观察者（Observer）     | 接收事件，并做出影响     | 乙方，接收需求，并实现功能 |
| 订阅（Subscribe）      | 连接上面二者             | 平台                       |
| 事件（Event）          | 沟通的载体（传递的消息） | 需求                       |



实例演示：

```java
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.msg)
    TextView msg;

    Observable<String> oble;    //甲方
    Observer<String> oser;       //乙方

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //创建甲方对象，提出需求并发布
        creaetObervable();

        //创建乙方对象，根据甲方的需求，执行对应方法
        createObserver();
    }

    /**
     * 建立连接，相当于平台，给甲方发布需求
     */
    @OnClick(R.id.goto2)
    public void onViewClicked() {
        //甲方找到乙方
        oble.subscribe(oser);
    }


    /**
     * 创建一个被观察的订阅接口对象，实现方法中是订阅的一系列事件
     */
    private void creaetObervable() {
        //甲方发布需求
        oble = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                //通过ObservableEmitter对象，产生事件并发送
                emitter.onNext("需求一");
                emitter.onNext("需求二");
                emitter.onNext("需求三");
                emitter.onComplete();       //该事件发送后，后续事件不被接受【甲方结账了，后续需求乙方不接受了】
                emitter.onNext("需求四");
            }
        });
    }


    /**
     * 创建观察者对象，复写对应事件的方法，响应对应的时间
     */
    private void createObserver() {
        oser = new Observer<String>() {
            private Disposable mDisposable;

            @Override   //接收事件前默认调用
            public void onSubscribe(@NonNull Disposable d) {
                Toast.makeText(MainActivity.this, "订阅开始，默认被调用了", Toast.LENGTH_SHORT).show();
                mDisposable = d;
            }

            @Override
            public void onNext(@NonNull String s) {
                if (s.equals("需求二")) {
                    mDisposable.dispose();	//乙方切断联系，罢工不干了，不接收后续消息，也不执行onComplete和onError
                }
                //但需求二的处理还会做完，是个负责任的乙方
                Toast.makeText(MainActivity.this, "接收到了" + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(MainActivity.this, "发生错误", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this, "订阅完成", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
```



事件类型：

| 事件类型 | 作用                                         | 规则                                                         |
| -------- | -------------------------------------------- | ------------------------------------------------------------ |
| Next     | 普通事件，发送需要被响应的信号               | 无限制                                                       |
| Complete | 队列的完结事件，标志被观察者不再发送普通事件 | 该事件后的普通事件继续发送，但观察者不再接受，且与Error事件互斥 |
| Error    | 队列异常事件，标志处理事件异常               | 队列自动终止，不允许再有事件发出，其他与Complete相同         |



现在再体验`RXJava`的链式编程：

```java
        // RxJava的链式操作
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "对Next事件" + value + "作出响应");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
```



## 2.简便模式

`RxJava.2.x`提供了多个函数式的接口，`Consumer`、`Function`、`Predicte`等，配合一些方法的使用，能达到简便效果

| 函数名             | 说明                                                         |
| ------------------ | ------------------------------------------------------------ |
| just(obj..obj)     | 快速创建一个被观察者对象，并发送多个对象，最多十个           |
| fromArray(arr[])   | 创建一个被观察者对象，并发送数组对象中的内容，用于发送十个以上对象 |
| fromIterable(list) | 发送集合对象中的数据，用于发送十个以上对象                   |



```java
        Observable.just("需求一","需求二","需求三").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogUtils.e("接收到" + s);
            }
        });
```





## 3.延迟模式

| 方法名          | 描述                                                         |
| --------------- | ------------------------------------------------------------ |
| defer()         | 被订阅时才创建Observable对象，但可以用此方法先定义好发送的对象 |
| timer()         | 指定延迟后，发送一个0，一般用于检测                          |
| interval()      | 指定初始延迟、间隔时间和时间单位，从0开始，递增1，循环发送   |
| intervalRange() | 比上一条多两个参数，起始值和发送的数量                       |
| range()         | 无延迟发送指定起始值和数量的int型事件                        |



延迟创建：

```java
    Integer i = 10; //初始值

    @OnClick(R.id.goto2)
    public void onViewClicked() {
        // 2. 通过defer 定义被观察者对象
        // 注：此时被观察者对象还没创建
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(i);
            }
        });

        i = 15; //重新赋值

        // 注：此时，才会调用defer（）创建被观察者对象（Observable）
        observable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer s) throws Exception {
                LogUtils.e("接收到" + s);  //15
            }
        });
    }
```



延迟发送：

```java
   //参数：1 是时间长度，2是时间单位。发送的对象为Long类型的0
	Observable.timer(2, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
       @Override
       public void accept(Long s) throws Exception {
           LogUtils.e("接收到" + s);
       }
   });
```



延迟+循环发送：

```
        //参数：1是初始延迟事件，2是间隔时间，3是时间单位
        Observable.interval(2,1, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long s) throws Exception {
                LogUtils.e("接收到" + s);
            }
        });
```



指定延迟+循环的起始值和数量：

```java
		//参数1：起始值，2：发送的事件数量，其余相同
		Observable.intervalRange(50,10,2,1, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long s) throws Exception {
                LogUtils.e("接收到" + s);
            }
        });
```



无延迟发送指定起始值和数量的int型事件：

```java
 		//参数1：起始值，2数量
		Observable.range(50,10).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer s) throws Exception {
                LogUtils.e("接收到" + s);
            }
        });
```





## 4.线程调度

关于Schedulers的方法

| 方法名                         | 描述                                                         |
| ------------------------------ | ------------------------------------------------------------ |
| newThread()                    | 开启新线程                                                   |
| io()                           | 开启I/O操作的线程，I/O内部是用一个无数量上限的线程池，效率比newThread更高，还应该避免产生大量计算，从而产生不必要的线程 |
| immediate()                    | 指定默认线程（当前线程）                                     |
| computation()                  | 计算使用的调度器，指的是CPU密集型计算，不会被IO等操作限制性能。因此不要放IO操作在该方法中 |
| AndroidSchedulers.mainThread() | RxAndroid扩展的主线程                                        |



其他线程中的方法：

| 方法名称                               | 事件的线程                              |
| -------------------------------------- | --------------------------------------- |
| 事件的产生：create()、just()、from()   | 默认当前线程，可以由subscribeOn指定     |
| 事件的加工：map()、flapMap()、filter() | 和事件产生保持一致，可以由observeOn指定 |
| 事件消费：subscribe()                  | 默认当前线程，可以由observeOn指定       |



```java
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d("kaelpu", "Observable thread is : " + Thread.currentThread().getName());
                Log.d("kaelpu", "emitter 1");
                emitter.onNext(1);
            }
        });

        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d("kaelpu", "Observer thread is :" + Thread.currentThread().getName());
                Log.d("kaelpu", "onNext: " + integer);
            }
        };

        observable.subscribeOn(Schedulers.newThread())      //子线程发送事件
                .observeOn(AndroidSchedulers.mainThread())  //主线程接收事件
                .subscribe(consumer);
```



例子：

```java
   @OnClick(R.id.goto2)
    public void onViewClicked() {
        Observable.just(getDrawableFromUrl())
                .subscribeOn(Schedulers.newThread())    //子线程发送网络请求
                .observeOn(AndroidSchedulers.mainThread())  //主线程处理请求
                .subscribe(new Consumer<Drawable>() {
                    @Override
                    public void accept(Drawable drawable) throws Exception {
                        ivNet.setImageDrawable(drawable);
                    }
                });
    }

    // 模拟网络请求图片
    private Drawable getDrawableFromUrl() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getResources().getDrawable(R.mipmap.ic_launcher);
    }
```





# RXAndroid

针对RxJava的扩展，能简化Android开发的工具。比如线程调度中的`AndroidSchedulers.mainThread()`。



生命周期绑定：

























