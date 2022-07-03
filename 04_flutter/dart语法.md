# 一、基础语法



## 0、类型转换

dynamic 转换为 String

```dart
dynamic a = "hello";
String str = a;
```



List<dynamic> 转换为 List<String>

```dart
 List<String> newlist= list.cast<String>().toList();
```



Map<String,dynamic> 转换 Map<String,String>

```dart
Map<String,dynamic> map={};
map["a"]="a";

Map<String,String> newMap =map.cast<String,String>();
```







## 1、方法

没有重载，方法也是对象，对应的类是Exception，方法也可以作为参数

```dart
//定义一个类型： F 类型， 这个F类型其实就是一个方法，接受两个int参数，返回void
typedef String F(int i,int j);

//调用该方法
String fun2(F f){
  return f(1,1);
}
```



不推荐的写法

```dart
void fun(Function f){ //没有办法表示要传递的这个方法参数 这个方法需要什么参数，返回什么
  f(1);
}
```

```dart
//这种写法 可以表达 我这个方法接受的方法参数f1 需要什么类型的变量返回什么
//但是写起来很麻烦
void fun3(void f1(int i,int j) ){

}
```





### 1.方法的参数：

可以有默认值。



可选位置参数：[] 包裹

```dart
void fun([int i = 1,int j = 2]){
  print(i);
  print(j);
}
```

> 如果想给j传值，就必须保证要给i传值



可选命名参数：{} 包裹

```dart
//可选命名参数
void fun1({int i = 1,int j = 2}){
}
```

>  fun1(j:10,i:11);







## 2、异常捕获：

介绍catch的参数

```dart
  try {
    test();
  }catch(e,s){
    //e: 就是抛出的异常对象
    print(e.runtimeType); //打印e的类型
    print(e);
    //s: StackTrace 调用栈信息
    print(s.runtimeType);
    print(s);
  }
```







除了异常，dart还可以抛出所有的类型：

```dart
void test() {
  throw "你太帅了，不给你调用!";
  // throw 111;
  // throw new Exception("hello");  
}
```



根据异常的类型进行不同处理：

```dart
 //根据不同的异常类型 进行不同的处理
  // on TYPE catch....
  try {
    test();
  } on Exception  catch(e,s){
    print("Exception");
  } on int catch(e){
    print("int");
  } on String catch(e){
    print("String");
  }

```







## 3、类

类的属性默认是pulic ，以_开头的，默认私有，类名也是如此。



### 1.构造：

```dart
class Point{
  int x;
  int y;

  Point(this.x, this.y);
}
```



#### ①命名构造方法

因为不允许重载，所以需要多个构造方法时，可以如下定义：

```dart
class Point{
  late int x;
  late int y;

  Point(this.x, this.y);

  Point.Y(this.y);
  Point.X(this.x);
}
```

```dart
void main(){
  var point =Point(1, 2);
  var point1 =Point.X(3);
  var point2 =Point.Y(4);
  print(point2.y);
}
```



参数初始化列表，可以在构造方法后面跟冒号，定义参数的初始值：

```dart
//参数初始化列表 初始化你类当中的属性 可以不写方法体
Point.fromMap(Map map):x=map['x'],y=map['y'];
```





#### ②常量构造函数

```dart
class ImmutabelPoint{
  final int x;
  final int y;
  // 常量构造方法
  const ImmutabelPoint(this.x,this.y);
}
```

使用const 来创建多个对象，传递参数一样 表示，则这几个对象是同一个 【编译期常量】 对象 （必须定义常量构造函数）

```dart
void main(){
  var p1 = const ImmutabelPoint(1,1);
  var p2 = const ImmutabelPoint(1,1);
  print(p1.hashCode == p2.hashCode); //true
  print(p1 == p2); //true
}
```







#### ③工厂构造方法

必须返回一个 实例对象，类似 Java中的static构造单例模式

```dart
class Manager{
  static Manager _instance;
  // 工厂构造方法 必须返回一个 实例对象
  factory Manager.getInstance(){
    if(_instance == null){
      _instance = new Manager._newInstance();
    }
    return _instance;
  }

  //私有构造方法，同一个文件仍然可以使用
  Manager._newInstance();
}
```







### 2.Get/Set

每一个实例属性 变量都会有一个隐式的 get/set，因为不允许重载，所以**设置属性为私有，才能设置get/set** 

```dart
class Point{
  int _x;
  int _y;

  //x 是方法名
  int get x => _x;
  set x(int x) => _x = x;
}
```

```dart
void main(){
  var point =Point();
  point.x=10; //set
  print( point.x); //get
}
```





### 3.重载运算符

```dart
class Point{
    //运算符重载， 定义+ 操作的行为
    Point operator +(Point othre){
        var point = new Point();
        point._x = _x + othre.x;
        return point;
    }

    String operator -(int x){
        return "hahahaha";
    }
}
```







### 4.call 方法

写了call() 方法的，可以通过【对象() 】调用

```dart
void main(){
  A a=A();
  a(); //调用的是call()
}

class A{
  void call(){}
}
```



## 4、抽象类和接口

和 JAVA 基本一致，抽象方法不用写 abstract 关键字



所有的类都可以看作是接口

```dart
class A{
  void Add(){}
}


class B implements A{
  @override
  void Add() {
    // TODO: implement Add
  }
}
```







## 5、混合

with 关键字，作用类似于多继承，但是被混入的类不能有构造方法，并且不能是抽象类。

A，B如果有相同的方法，C有时调用C的，没有时默认为后者

```dart
void main(){
   C c=C();
   c.a();
   c.b();
   c.c();
}

class A{
  void a(){}
}

class B {
  void b(){}
}

class C with A,B{
  void c(){}
}
```







# 二、异步

## 1、通信基础：Isolate

dart是单线程，内存隔离

一个Isolate相当于一个进程，有自己独立的内存和数据，和当前是隔离的，不用担心多线程的资源抢夺问题

```dart
int a = 0;

void multiThreat(){
  //创建一个Isolate,设置初始值，在fun中执行(类似于多线程执行)
  //参数1接受一个方法，参数二是方法一的参数
  Isolate.spawn(test1, 10);
  sleep(Duration(seconds: 1));
  print("isolate回来之后a的值==$a"); //0
}

void test1(int count){
  a = count;
  print("isolate执行完毕1===$count"); //1
  print("修改之后的a的值==$a"); //1
}
```





消息接收器：可以单向地传递消息

```dart
void main(){
    //消息接收器
    var receiverPort = ReceivePort();
    //监听接收到的消息
    receiverPort.listen((message) {
        print('msg=${message}');
        print('111');
    });
    //异步，并把消息接收器给Isolate对象
    Isolate.spawn(entryPoint,  receiverPort.sendPort);
}

void entryPoint(SendPort sendPort) {
    //利用sendPort 就可以给main发送消息了
    sendPort.send("22");
    print('22');
}
```







双向发送：

<img src="dart%E8%AF%AD%E6%B3%95.assets/image-20220303234055956.png" alt="image-20220303234055956" style="zoom: 67%;" />

```dart
void main() {
    ReceivePort receiverPort = new ReceivePort();
    //开启异步
    Isolate.spawn(entryPoint, receiverPort.sendPort);

    //监听B给Main的消息
    receiverPort.listen((t) {
        if (t is SendPort) {
            print("main给B发送消息 ，内容为：收到收到");
            t.send("收到收到!");
        } else
            print("main接收到 B  发来的消息~~~~~" + t);
    });
}

void entryPoint(SendPort sendPort) {
    sendPort.send("我是B，我给main 发消息啦");
    //B也拥有自己的receiverPort，并发送给Main
    ReceivePort receiverPort = new ReceivePort();
    sendPort.send(receiverPort.sendPort);
    //监听Main的消息
    receiverPort.listen((t) {
        print("B接收到 main 的消息~~~~~" + t);
    });
}
```

> 案例在main() 后面sleep 2s，过了2s才会打印出 监听到的消息。因为单线程





## 2、事件队列

isolate发送的消息通过loop处理，与Android不同的是，dart中有两个队列，一个叫做**event queue(事件队列)**，另一个叫做**microtask queue(微任务队列)**。

<img src="dart%E8%AF%AD%E6%B3%95.assets/image-20220303234621471.png" alt="image-20220303234621471" style="zoom:50%;" />



Dart在执行完main函数后，就会由Loop开始执行两个任务队列中的Event。

首先Loop检查微服务队列，依次执行Event，当微服务队列执行完后，就检查Event queue队列依次执行，在执行Event queue的过程中，每执行完一个Event就再检查一次微服务队列。所以微服务队列优先级高，可以利用微服务进行插队。



```dart
void main(){
  var receivePort = new ReceivePort();

  receivePort.listen((t){
    print(t);
  });

  receivePort.sendPort.send("发送消息给消息接收器1!");
  receivePort.sendPort.send("发送消息给消息接收器2!");
  receivePort.sendPort.send("发送消息给消息接收器3!");

  // 在微任务队列中提交一个任务
 Future.microtask((){
   print("微任务执行1");
 });
}

//输出：
微任务执行1
发送消息给消息接收器1!
发送消息给消息接收器2!
发送消息给消息接收器3!
```





并且微任务队列可以进行插队

```dart
void main(){
  var receivePort = new ReceivePort();

  receivePort.listen((t){
    print(t);
    //开启一个微任务
    Future.microtask((){
      print("微任务执行");
    });
  });


  receivePort.sendPort.send("发送消息给消息接收器1!");
  receivePort.sendPort.send("发送消息给消息接收器2!");
  receivePort.sendPort.send("发送消息给消息接收器3!");
}

//输出
发送消息给消息接收器1!
微任务执行
发送消息给消息接收器2!
微任务执行
发送消息给消息接收器3!
微任务执行
```







## 3、Future

future关键字，用于异步操作后的内容回调，表示一次异步操作后获得数据。



代码中对可能出现的异常进行捕获：

```dart
new File(r"F:\b.txt").readAsString().then((String s) {
    print(s);
}).catchError((e,leak){
    //异常的处理
});
```





### 1.then方法

作用：获取Future执行后的内容，返回值是一个新的Future对象

缺点：多次 then 的时候，回调很不方便。解决办法：async 和 await 关键字

```dart
var future = Future.delayed(Duration(seconds:3));
future.then((value) => print(value));		//null
```



如 JAVA 中的IO操作，通常会在子线程中进行，dart中为：

```dart
File(r"F:\a.txt").readAsString().then((String s) {
    print(s); //HELLO WORD
});
```

> readAsString() 返回的就是一个Future对象，并且文件不存在时会出现异常
>



因为then返回的是一个Future对象，所以可以链式调用，它的返回值，作为下一个then方法的参数

```dart
File(r"F:\a.txt").readAsString().then((String s) {
    return s;
}).then((value) {
    print(value); //HELLO WORD
});
```





### 2.wait方法

作用：一组任务执行完毕后，进行相同的处理

```dart
Future readDone = new File("F:\a.txt").readAsString();
Future delayedDone = Future.delayed(Duration(seconds: 3));
//等待二者都完成才打印
Future.wait([readDone, delayedDone]).then((values) {
    print(values[0]);//第一个future的结果，hello world
    print(values[1]);//第二个future的结果，null
});
```





### 3.forEach方法

遍历集合

```dart
  Future.forEach([1,2,3,4],(i){
    print(i);
  });
```





### 4.Async 和 await

能较好地解决回调问题，使用async 标注的方法必须是Future修饰的

```dart
//async 表示这是一个异步方法,await必须再async方法中使用
//异步方法只能返回 void和Future
Future<String> readFile() async {
  String content = await new File("F:/a.txt").readAsString();
  String content2 = await new File("F:/b.txt").readAsString();
  return content;
}
```













## 4、Stream

表示**多次**异步获得的数据，比起Future来说内存占用更小，适合处理大型文件

```dart
Stream<List<int>> stream=File(r"F:\a.txt").openRead();
//通过监听得到结果
var listen=stream.listen((s){
    print(s); //[104, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100]
});
```

> dart 没有Byte类型，所有用 int



对listen的其他操作

```dart
//替换listen，有onData将不再回调给listen
listen.onData((s){
    print("strema2");
});

//所有的steam执行完后回调
listen.onDone((){
    print("读完整个文件");
});
//暂停
listen.pause();
//继续
listen.resume();
//取消
listen.cancel();
```





写入文件：

```dart
//拿到需要写入的数据，以及文件流对象
List<int> data=[104, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100];
var file = new File(r"F:/b.txt");
var write =file.openWrite();

//追加写入
write.add(data); 

//覆盖写入：
file.writeAsBytes(data);
```





文件的转存：

```dart
void main(){
  var file = new File(r"F:/a.txt");
  var newFile = new File(r"F:/b.txt");
  //读文件
  Stream<List<int>> data= file.openRead();
  //添加写入流写入
  newFile.openWrite().addStream(data);
}
```







### 1.订阅模式

默认情况下，steam是单订阅模式的

```dart
Stream<List<int>> stream = new File(r"C:\Users\86139\Downloads\010editor.zip").openRead();
//两个订阅者，运行时会报错
var listen = stream.listen((s){

});

stream.listen((s){

});
```



广播模式，可以存在多个订阅者，有订阅者的情况下不能使用广播模式，不然也会运行时报错

```dart
Stream<List<int>> stream = new File(r"C:\Users\86139\Downloads\010editor.zip").openRead();
//广播模式  : 可以多订阅
var broadcastStream = stream.asBroadcastStream();
broadcastStream.listen((_){
    print("listen1");

});
broadcastStream.listen((_){
    print("listen2");
});
```





steam的广播 和 直接创建的广播  区别：

```dart
  //直接创建一个广播
  var streamController = StreamController.broadcast();
  //线监听，后发送，可以收到。 先发送，后监听，收不到
  streamController.stream.listen((i){
    print("广播:"+i);
  });
  streamController.add("1"); 

  streamController.close();
```





















