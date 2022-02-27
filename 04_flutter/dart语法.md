# 一、基础语法

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











