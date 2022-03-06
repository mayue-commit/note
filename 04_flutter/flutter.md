# 一、Widget

Flutter中，几乎所有的对象都是一个`Widget`，它不仅可以表示UI元素，也可以表示一些功能性的组件如：用于手势检测的 `GestureDetector widget`、用于应用主题数据传递的`Theme`等等。

实际上，Flutter中真正代表屏幕上显示元素的类是`Element`，也就是说Widget只是描述`Element`的一个配置。一个Widget可以对应多个`Element`，同一个Widget对象可以被添加到UI树的不同部分，而真正渲染时，UI树的每一个节点都会对应一个`Element`对象。





## 1、基础组件：

-  `StatelessWidget`：无状态的，展示信息，面向那些始终不变的UI控件；
- `StatefulWidget`：有状态的，可以通过改变状态使得 UI 发生变化，可以包含用户交互(比如弹出一个 dialog)。



### 1.StatelessWidget

```dart
import 'package:flutter/material.dart';

void main(){
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(child: Text("第一行代码",textDirection: TextDirection.ltr,),);
  }
}
```

> `BuildContext`表示构建widget的上下文，它是操作widget在树中位置的一个句柄，它包含了一些查找、遍历当前Widget树的一些方法。每一个widget都有一个自己的context对象

> Center也是一个Widget容器，只包裹一个子类控件，且居中显示



此时，必须指定Text的方向（即textDirection），否则UI不知道本文从哪里绘制，将会报错。而大部分文本都是这样的绘制方式，每一个都要指定就显得很麻烦。

materi design的设计，指明了这些风格。

```dart
class _MyApppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    //使用materialApp类，替代原来的Widget
    return MaterialApp(
        home: Center(child: Text("第一行代码"),)  //home
    );
  }
}
```

<img src="flutter.assets/image-20220306193233427.png" alt="image-20220306193233427" style="zoom:33%;" />



但是这类效果并不理想，因为没指明背景颜色，默认为黑色，material中默认背景是黑色时字体显示红色。类似于android中自定义了一个style。



想要白色背景的APP颜色，需要指定风格为Scaffold：

```dart
class _MyApppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        home: Scaffold(  //指定风格  
            body:Center(child: Text("第一行代码"),)
        )
    );
  }
}
```

> Scaffold 中还有appBar、FloatingActionButton、drawer、endDrawer、backgroundColor、bottomNavigationBar……等等





### 2.Stateful Widget

StatefulWidget是动态的，添加了一个新的接口`createState()`用于创建和Stateful widget相关的状态`State`，它在Stateful widget的生命周期中可能会被多次调用。

当State被改变时，可以手动调用其`setState()`方法通知Flutter framework状态发生改变，Flutter framework在收到消息后，会重新调用其`build`方法重新构建widget树，从而达到更新UI的目的。

==注意：==不能在构造方法中直接调用setstate（），因为此时Widget还没有被创建，也没有state。但可以使用Future类延迟调用

```dart
//修改MyApp继承StatefulWidget
class MyApp extends StatefulWidget {
    @override
    _MyApppState createState() => _MyApppState();
}

class _MyApppState extends State<MyApp> {
    String data = "我好帅!";

    //延迟3秒更新内容
    _MyApppState() {
        Future.delayed(Duration(seconds: 3)).then((s) {
            this.data = "我是最帅的!";
            setState(() {

            });
        });
    }

    @override
    Widget build(BuildContext context) {
        //在这里打印能接收到两次，因为调用了setState
        return MaterialApp(
            home: Scaffold(
                appBar: AppBar(
                    title: Text("我是appBar"),
                ),
                body: Center(
                    child: Text(data),
                )));
    }
}
```







### 3.生命周期

<img src="flutter.assets/image-20220306205828351.png" alt="image-20220306205828351" style="zoom:50%;" />

- initState：当Widget第一次插入到Widget树时会被调用，对于每一个State对象，Flutter framework只会调用一次该回调
- didChangeDependencies：在initState()之后立刻调用，当依赖的InheritedWidget rebuild,会触发此接口被调用
- build：绘制界面，当setState触发的时候会再次被调用
- didUpdateWidget：状态改变的时候会调用该方法，比如调用了setState
- deactivate：当State对象从树中被移除时，会调用此回调
- dispose：当State对象从树中被永久移除时调用；通常在此回调中释放资源



假如现在有一个控件Parent，控件Child，Parent中包含一个Child

```dart
//打印各生命周期方法，点击运行按钮，显示：
I/flutter (22218): parent initState......
I/flutter (22218): parent didChangeDependencies......
I/flutter (22218): parent build......
I/flutter (22218): child initState......
I/flutter (22218): child didChangeDependencies......
I/flutter (22218): child build......
    
//点击按钮移除或替换Child内容时，显示：
I/flutter (22218): parent build......
I/flutter (22218): child deactivate......
I/flutter (22218): child dispose......
```















































