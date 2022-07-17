#  零、Android Studio前言

本文所有的项目，基于《第一行代码》、《菜鸟教程》、哔哩哔哩的 《程序员拉锯大战》 和《八月潜空 》的教学案例，进行实现，并结合公司所使用的技术略微改动了一些内容，并成功在自己的机型上通过调试。仅提供给数马公司初入Android开发的人员进行学习，无商用价值。学习前，需要具备一定的JAVA基础，网络编程部分，需要对JAVAWeb和服务器有一定的认知，如缺少该部分的知识，请先去补足。

可以直接从第二章内容开始看起，需要用到快捷键、调试工具的时候，返回查询即可。

##  0.相关术语

2. **AVD：** (android virtual machine)：安卓的模拟器
3. **ADT：** (android development tools)：安卓开发工具
4. **SDK：**(software development kit)：软件开发工具包,就是安卓系统；平台架构等的工具集合
5. **DDMS：**(dalvik debug monitor service)：安卓调试工具
6. **adb：**安卓调试桥，在sdk的platform-tools目录下，功能很多，命令行必备
8. **AAPT：**(android asset packing tool)，安卓资源打包工具
9. **R.java文件：**由AAPT工具根据App中的资源文件自动生成，可以理解为资源字典
10. **AndroidManifest.xml：**app包名 + 组件声明 + 程序兼容的最低版本 + 所需权限等程序的配置文件



## 1. Ctrl 组合键

| 方法名   | 具体功能               |
| -------- | ---------------------- |
| Ctrl+D   | 在当前行下方复制一行   |
| Ctrl+N   | 查找类名、文件名       |
| Ctrl+O   | 显示父类中可覆写的方法 |
| Ctrl+F   | 查找                   |
| Ctrl+R   | 查找替换               |
| Ctrl+J   | 自动代码               |
| Ctrl+H   | 显示类继承结构图       |
| Ctrl+F12 | 快速查看类中所有的方法 |



## 2. Ctrl +Alt 组合键

| 方法名         | 具体功能                                        |
| -------------- | ----------------------------------------------- |
| Ctrl +Alt+T    | 选中代码块，按下此键快速添加if、try catch等语句 |
| Ctrl +Alt+L    | 格式化代码                                      |
| Ctrl +Alt+空格 | 弹出提示                                        |
| Ctrl +Alt+O    | 优化导入的包                                    |
| Ctrl +Alt+H    | 弹出此方法的调用关系                            |



## 3. Ctrl + Shift 组合键

| 方法名                 | 具体功能           |
| ---------------------- | ------------------ |
| Ctrl + Shift+空格      | 自动补全代码       |
| Ctrl + Shift+BackSpace | 回到上次编辑的地方 |
| Ctrl + Shift+上        | 代码块整体上移     |
| Ctrl + Shift+N         | 查找文件           |



## 4. 其他组合快捷键

| 快捷键                      | 具体功能                          |
| --------------------------- | --------------------------------- |
| Alt+Insert                  | 生成构造方法、getter、settter方法 |
| Alt+鼠标左键                | 多行编辑                          |
| Ctrl+鼠标左键               | 快速进入该方法                    |
| 双击shift                   | 全局搜索类或文件                  |
| shift+F6                    | 选中变量后快速替换全部变量名称    |
| ctrl+R输入^\s*\n，点击Regex | 快速删除所有空行                  |



# 一、调试工具

## 1. 程序调试快捷键

debug模式下启用：

| 按键         | 作用                                                     |
| ------------ | -------------------------------------------------------- |
| F7           | 单步跳入：一步步从断点处执行代码，进入方法内部。         |
| alt+shift+F7 | 强制进入该方法                                           |
| F8           | 单步跳过：直接跳过改方法，至下一个断点，不进入方法内部。 |
| shift+F8     | 单步跳出：直接跳出当前方法                               |
| alt+F9       | 直接跳到下一个断点                                       |

  

右键变量，可以设置值

![image-20210113160442022](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210113160442022.png)



## 2. DDMS工具的使用

### 2.1 查看内存

堆内存信息列表中，如果【data object】这一项的值在操作应用时逐渐增大，就可能存在内存泄漏的情况

![image-20210114131608923](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210114131608923.png)



### 2.2 文件导入/导出

导入文件按钮左侧为导出文件，可以把模拟器的文件传入电脑中

![image-20210114132106782](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210114132106782.png)



### 2.3 给模拟器发短信/打电话

==短信目前不能识别中文==

![image-20210114132604630](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210114132604630.png)







## 3. 日志工具Log的使用

该工具类在android.util.Log包下，类中提供了五个方法供我们打印日志

| 方法名    | 具体功能                                   | 对应级别 |
| --------- | ------------------------------------------ | -------- |
| Log.v（） | 打印最繁琐、意义最小的日志信息。           | verbose  |
| Log.d（） | 打印一些调试信息。                         | debug    |
| Log.i（） | 打印一些较为重要的数据，帮你分析用户行为。 | info     |
| Log.w（） | 打印警告信息，提示程序可能存在的潜在风险。 | warn     |
| Log.e（） | 打印程序中的错误信息。                     | error    |

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210114133151097.png" alt="image-20210114133151097" style="zoom: 33%;" />



# 二、初步认识Android

![image-20210307195003560](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210307195003560.png)



Android版本很多，开发时需要面对Android的"碎片化"问题，这个问题可以分为两个：

1. 系统碎片化：我们开发App时可能需要做到低版本兼容，如：最低兼容至5.0版本

2. 屏幕碎片化：市面上各种各样屏幕尺寸的手机，4.3寸，4.5寸，4.7寸，5.0寸，5.3寸...等等，除了手机外，还有Android平板，所以开发时我们可能要处理这个屏幕适配的问题








## 一、安卓项目目录结构

### 1.1 安卓项目整体目录解析：

![image-20201130182342516](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20201130182342516.png)

### 1.2 app目录解析：

![image-20201130215818654](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20201130215818654.png)

### 1.3 AndroidManifest文件：

![image-20201201195925492](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20201201195925492.png)

点开.MainActivity可以看到：

![image-20210517140916390](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210517140916390.png)

其中setContentView方法给当前Activity引入了一个布局文件，==所有的布局文件都放在res/layout目录==下



### 1.4 res目录文件解析：

![image-20201201195352935](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20201201195352935.png)

这么多mipmap开头的目录，主要是**为了让程序能更好地兼容各种设备**。

> 如果只有一份图片，放在-xxhdpi目录下即可，因为这是最主流的设备分辨率目录。



### 1.5 values目录下的资源引用

#### 1.5.1 strings资源引用

![image-20201201200706692](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20201201200706692.png)

通过这样的设置的资源，成为**动态设置**，便于后期的维护和调整

> 注意：本文中为了简单易懂，便于观看，没有这么做



#### 1.5.2 colors资源文件

使用该文件资源时：

JAVA中使用  getResources().getColor(R.color.XXXX)  获取对应的资源项

XML文件中用法和string资源相同

#### 1.5.3 数组资源

在 /values 目录下新建一个资源文件 array.xml ，为其中设置列表名称方便引用，还有每个子项的内容

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string-array name="listItem">
        <item>画画</item>
        <item>读书</item>
        <item>玩游戏</item>
    </string-array>
</resources>
```

在布局文件的xml中引用：

```xml
<ListView
    android:layout_marginTop="40dp"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:entries="@array/listItem"></ListView>
<!-- android:entries为引用属性，@array表示目录，对应上面的name名称-->
<!-- 同理，今后还有@id，表示引用一个控件，而@+id/表示新建一个id属性-->
```

JAVA代码中引用：

```java
String[] arr=getResources().getStringArray(R.array.listItem);
```



#### 1.5.4 style资源



##### 设置主题：

该资源文件下，定义了APP的主题样式，在布局文件页的Design页面中可以设置该布局文件的主题，也可以自定义主题样式

![image-20210116153451699](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210116153451699.png)

自定义主题样式：在style文件下：

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210116153651480.png" alt="image-20210116153651480" style="zoom:67%;" />

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210116153813474.png" alt="image-20210116153813474" style="zoom:67%;" />

JAVA代码中设置主题样式：

```java
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.XXX);//设置主题样式的代码，须写在布局文件之前
        setContentView(R.layout.activity_main);

    }
```



##### 设置样式：

为每个item子项设置特殊的字体样式，可以用parent指定父类，继承其样式属性。（如果子样式和父样式都有，则采用子样式）

```xml
<style name="title">
    <item name="android:textSize">30sp</item>
    <item name="android:textColor">#06F</item>
</style>
```

在布局文件中引用：

```java
style="@style/title"
```



### 1.6 drawable资源

图片资源不要带大写字母，不能以数字开头，放在/drawable目录下，根据分辨率选择xx-dpi



#### 自定义常见形状的图形：

如圆形：

```xml
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="oval">

    <size
        android:width="8dp"
        android:height="8dp" />
    <solid android:color="#44000000" />
</shape>
```











#### 制作 Nine-Patch 图片：

有些图片导入布局时，会被拉伸变形，Nine-Patch 图片就是可以指定哪些区域被拉伸，哪些不可以的特殊png图片，右键单击想制作成 Nine-Patch 的图片，Create 9-Patch file。然后选择保存图片的位置，即可。

> 按住左键在边缘拉即可拉出黑线，shift左键可以取消黑线

![image-20201219165905459](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20201219165905459.png)





#### 状态列表资源（StateListDrawable）：

它可以根据不同的状态，显示不同的效果。比如当点击时，图片变大。



在/drawable文件下创建一个edittext_focused.xml ，里面每个item子项定义一种状态改变的选项，这里表示控件获得焦点时显示不同的颜色：

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_focused="true" android:color="#f60"></item>
    <item android:state_focused="false" android:color="#0a0"></item>
</selector>
```

然后在布局文件中引用：

```xml
android:textColor="@drawable/edittext_focused"
```





### 1.7 mipmap资源

该图片主要是存放android app启动图标，可以提高系统启动的速度，和图片的质量





### 1.8 详解build.gradle

AS是采用Gradle来构建项目的，Gradle是一个非常先进的项目构建工具，它使用了一种基于Groovy领域的特定语言（DSL）来进行项目设置，摒弃了传统基于XML（如Ant和Maven）的各种繁琐配置。

外层目录下的build.gradle：

![image-20201202110602305](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20201202110602305.png)

app目录下的build.gradle：

![image-20210703193223977](images/image-20210703193223977.png)



### 1.9 程序国际化

在系统语言设置为中文简体时显示中文简体，英文时显示英文。



步骤：

1. 首先在  /values/strings.xml  中定义好我们的资源文件
2. 创建对应的资源文件夹，/res 下 新建 values-zh-rCN （对应中文简体），values-zh-rTW（对应中文繁体），values-zh-rUS（对应英文）等等
3. 将strings.xml复制到对应文件夹下，修改选项显示的内容即可



## 二、横屏竖屏的使用

模拟器按 ctrl + F11 /F12 切换横竖屏

横竖屏会在切换的时候，==销毁当前Activity重新创建一个==，因此临时的数据不会被保存，保存临时数据的内容见【四大组件之Activity章节】



### 一、固定屏幕的显示方向

可以在Manifest中加入 **android:screenOrientation** 属性，有以下可选值：

```
unspecified:默认值 由系统来判断显示方向.判定的策略是和设备相关的，所以不同的设备会有不同的显示方向。
landscape:横屏显示（宽比高要长）
portrait:竖屏显示(高比宽要长)
user:用户当前首选的方向
behind:和该Activity下面的那个Activity的方向一致(在Activity堆栈中的)
sensor:有物理的感应器来决定。如果用户旋转设备这屏幕会横竖屏切换。
nosensor:忽略物理感应器，这样就不会随着用户旋转设备而更改了（"unspecified"设置除外）。
```



###  二、 在不同显示效果下呈现不同的布局：

1）准备两套不同的布局，放在layout-land 和 layout-port 文件夹中，文件名相同，系统会自动判断并加载相应布局

2）JAVA代码控制：

```java
if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){  
     setContentView(R.layout.横屏);
}  

else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {  
    setContentView(R.layout.竖屏);
}
```







# 三、UI界面



几个基本单位：

dp：设备独立像素，不同设备有不同显示效果，最常用的单位

sp：主要用于字体显示，根据用户手机设置字体大小的首选项进行缩放

px：像素，不同设备显示效果相同

pt：标准长度单位，1/72英寸，用于印刷业



## 一、常用控件

通用属性：

| 常用属性               | 说明                                     | 常用值                                                       |
| ---------------------- | ---------------------------------------- | ------------------------------------------------------------ |
| android:id             | 唯一标识，常用来获取控件                 | 自定义                                                       |
| android:layout_width   | 控件的宽度                               | wrap_content（包裹自身）、match_parent（和父容器相同）       |
| android:layout_herght  | 控件的高度                               | wrap_content（包裹自身）、match_parent（和父容器相同）       |
| android:text           | 指定TextView中的文本显示内容             | 自定义，一般我们是把字符串写到string.xml文件中，然后通过@String/xxx取得对应的字符串内容的 |
| android:gravity        | 指定控件内（文字等）对齐方式             | top、bottom、left、right、center……                           |
| android:layout_gravity | 控件在布局下的对其方式，部分布局中会失效 | top、bottom、left、right、center……                           |
| android:textSize       | 指定文字大小                             | 自定义，单位用sp                                             |
| android:textColor      | 指定文字颜色                             | 自定义                                                       |
| android:textStyle      | 设置字体风格                             | normal、bold（加粗）、italic（斜体）                         |
| android:background     | 背景                                     | 自定义，可以是图片，也可以是颜色，布局文件等等               |

通过id，在Activity的类中定义相同的控件，在onCreate()方法中通过findViewById()找到对应id的控件，即可对控件进行一定的操作

==下面的布局文件中将删除不重要的信息==，比如字体大小、颜色等，**控件的宽高不指明的情况下默认包裹自身**

Activity中findViewById等无意义操作，只展示关键代码



### 1.1 TextView

| 特殊属性                                  | 描述                                            |
| ----------------------------------------- | ----------------------------------------------- |
| android:drawableTop="@mipmap/ic_launcher" | 带图片的TextView，Top换为Button等可改变图片位置 |

JAVA代码中可以使用  TextUtls.isEmpy(String str)  对字符串判空，【不等于null 且 不等于空 】时，返回true

 

##### 带图片的TV：

不能直接设置图片大小，需要在JAVA代码中修改：

```java
public class MainActivity extends Activity {  
    private TextView mTextView;  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);
        
        mTextView=findViewById(R.id.text);
        Drawable[] drawable = mTextView.getCompoundDrawables(); //获得四个不同方向上的图片资源,数组元素依次是:左上右下的图片
        //setBounds设置左上右下坐标点,可以理解为建了一张这么大的画布，在画布上绘图，可以拉伸图片
        //比如这里设置了代表的是: 长是:左边开始100dp-200dp 宽是:从文字上方0dp-200dp
        drawable[1].setBounds(100, 0, 200, 200);
        mTextView.setCompoundDrawables(drawable[0], drawable[1], drawable[2],drawable[3]);
        //为TextView重新设置drawable数组!没有图片可以用null代替
    }  
}
```





##### 设置背景边框

res/drawable目录下新建 text_rectborder.xml 资源文件，设置样式属性

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android" >

    <!-- 设置透明背景色 -->
    <solid android:color="#87CEEB" />
    <!-- 设置一个黑色边框 -->
    <stroke android:width="2px" android:color="#000000"/>
    <!-- 渐变 -->
    <gradient
        android:angle="270"
        android:endColor="#C0C0C0"
        android:startColor="#FCD209" />
      <!-- 设置四个圆角的半径 -->
    <corners
        android:bottomLeftRadius="10px"
        android:bottomRightRadius="10px"
        android:topLeftRadius="10px"
        android:topRightRadius="10px" />
</shape>
```

将其应用到对应控件上

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android">
    <!--不相关的东西省略-->

    <TextView
        android:background="@drawable/text_rectborder"/>
</LinearLayout>
```



##### ==链接==效果：

android:autoLink="phone"

> 可选值有all、none、web、email等，如果是错误的类型，也没有效果，比如写电话号对应"email"。all就是全部都包含,自动识别协议头 
>
> ```java
> //也可以在JAVA代码中控制自动识别：
> mTextView.setAutoLinkMask(Linkify.ALL);
> mTextView.setMovementMethod(LinkMovementMethod.getInstance()); 
> ```



##### 跑马灯效果：

```xml
 <TextView
        android:id="@+id/mTextView"
        android:ellipsize="marquee"		
        android:singleLine="true"		
        android:text="快讯：红色预警，超强台风“莫兰蒂”即将登陆，请居民关紧门窗、备足粮草，做好防汛救灾准备！" />
```

需要在JAVA代码中：

```java
mTtextView.setSelected(true);
```

| XML属性    | 说明                                                         |
| ---------- | ------------------------------------------------------------ |
| singleLine | 单行显示                                                     |
| ellipsize  | 超出范围后的省略方式，取值：start，middle，end（省略号在结尾），marquee（跑马灯） |





·····

### 1.2 Button

| 特殊属性                    | 说明                              |
| --------------------------- | --------------------------------- |
| android:textAllCaps="false" | 默认全是大写，值为false时正常显示 |



##### 带特殊样式的按钮

/drawable下xml文件的特殊样式，采用Nine-Patch图片作为背景较好

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_pressed="true" ><!--被按压时-->
        <shape>
            <solid android:color="@color/colorPrimaryDark" /><!--颜色-->
            <stroke android:width="1dp" android:color="@color/colorAccent" /><!--边框-->
            <corners android:radius="20dp"/><!--圆角-->
        </shape>
    </item>
    <item android:drawable="@color/colorPrimary"></item> <!--正常时的状态-->
</selector>
```

（更多item节点：）

| 属性（为了方便不写android:） | 描述                                          |
| ---------------------------- | --------------------------------------------- |
| drawable                     | 控件的正常状态                                |
| state_pressed="true"         | 控件被按压时                                  |
| state_enabled="false"        | 控件不可用时                                  |
| state_focused="true"         | 控件获得焦点时                                |
| state_checkable=“true"       | 控件能被勾选，Chekbox的属性                   |
| state_checked=”true“         | 控件被勾选                                    |
| state_single                 | 控件包含多个子控件时,确定是否只显示一个子控件 |

在XML直接引用即可：

```xml
<Button
    android:background="@drawable/btn_bg1"/>
```



##### 开关按钮ToggleButton

```xml
<ToggleButton
    android:id="@+id/tbtn_open"
    android:checked="true"
    android:textOff="关闭声音"
    android:textOn="打开声音" />
```

```java
public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    private ToggleButton tbtn_open;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbtn_open = findViewById(R.id.tbtn_open);
        tbtn_open.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.tbtn_open:
                if(compoundButton.isChecked()) Toast.makeText(this,"打开声音",Toast.LENGTH_SHORT).show();
                else Toast.makeText(this,"打开声音",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
```





##### 开关Switch

==SDK 14以上==

```xml
<Switch
        android:switchTextAppearance="@style/SwitchTextAppearance"
        android:showText="true"
        android:textOff="关"
        android:textOn="开"
></Switch>
```

显示的文字的大小，颜色在  values/style.xml 文件定义

```xml
 <style name="SwitchTextAppearance" parent="@android:style/TextAppearance.Holo.Small">
        <item name="android:textColor">#ffffff</item>
        <item name="android:textSize">12sp</item>
    </style>
```

点击事件的监听和 ToggleButton 相同



### 1.3 EditText

![image-20210108201507230](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210108201507230.png)

##### 带删除按钮的EditText：

(需要自己准备一个图片放在/drawable目录下作为删除的按钮）

```java
import android.annotation.SuppressLint;

@SuppressLint("AppCompatCustomView")
public class EditTextWithDel extends EditText {
    private final static String TAG = "EditTextWithDel";
    private Drawable imgInable;
    private Drawable imgAble;
    private Context mContext;

    public EditTextWithDel(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        imgInable = mContext.getResources().getDrawable(R.drawable.delete_gray); //这里的值换成你的图片名称
        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }
        });
        setDrawable();
    }

    // 设置删除图片
    private void setDrawable() {
        if (length() < 1)
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        else
            setCompoundDrawablesWithIntrinsicBounds(null, null, imgInable, null);
    }

    // 处理删除事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (imgInable != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Log.e(TAG, "eventX = " + eventX + "; eventY = " + eventY);
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 100;
            if (rect.contains(eventX, eventY))
                setText("");
        }
        return super.onTouchEvent(event);
    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
```

引用：

在xml中输入 EditTextWithDel （类名），找到对应的包，即可，其余属性同EditText





### 1.4 ImageView

![image-20210109163930620](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210109163930620.png)

展示图片的控件，通常图片放在以 “ drawable ” 开头的目录下，不过因为这个目录没有指定分辨率，我们常放在 res 目录下的 drawable-xhdpi 中

注意：==图片名不能以数字开头，不能"-"进行连接名称==

```xml
<ImageView
    android:src="@drawable/image_1"	/>
```





##### PhotoView根据手势缩放图片：

导入第三方依赖库：

```java
implementation 'com.commit451:PhotoView:1.2.4'
```

直接将ImageView替换成PhotoView使用即可，其余属性相同



### 1.5 ProgressBar(进度条)

进度条，表示程序正在加载一些数据，默认为圆形的滚动进度条

```xml
<!--style风格如下图所示，max属性设置进度条最大值-->
<ProgressBar
    style="?android:attr/progressBarStyleHorizontal"		
    android:max="100"/>
```

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210112122556064.png" alt="image-20210112122556064" style="zoom:50%;" />





范例：==模拟进度条的加载效果==：【布局主界面就一个进度条（id：progress_bar），此处省略】

```java
public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (progress == 100) {
                    Toast.makeText(MainActivity.this, "加载完毕", Toast.LENGTH_SHORT).show();
                }else {
                    progress = progressBar.getProgress();
                    progress = progress + 10;
                    progressBar.setProgress(progress);
                }
            }
        });
    }
}
```





### 1.6 SeekBar（拖动条）

```xml
<SeekBar
    android:id="@+id/seek_bar"
    android:max="10"	//最大值
    android:progress="5"	//当前进度
    android:thumb="" //可以设置拖动按钮的图片样式  ></SeekBar>
```

```java
seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txt.setText("当前进度值:" + progress + "  / 100 ");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(this, "触碰SeekBar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(this, "放开SeekBar", Toast.LENGTH_SHORT).show();
            }
        });
```



### 1.7星际评分条：RatingBar

```xml
<RatingBar
    android:id="@+id/rb_normal"
          
    android:isIndicator="false"  //是否不让用户更改，默认false
    android:rating="4"		//默认值
    android:stepSize="1.5"	//每次增加的值 />
```

```java
rb_normal = findViewById(R.id.rb_normal);
rb_normal.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
     @Override
     public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
          Toast.makeText(MainActivity.this, "rating:" + String.valueOf(rating),
          Toast.LENGTH_LONG).show();
     }
});
```



### 1.8 AlertDialog(对话框)

对话框直接在需要弹出的地方使用JAVA代码创建即可，不需要xml：

对话框由这几个部分组成，但不一定每个部分都存在：

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210117133131504.png" alt="image-20210117133131504" style="zoom:67%;" />

当前界面弹出一个对话框，能屏蔽其它控件的交互能力。比如防止用户误删重要内容，弹出一个提示框确认。

```java
//省略设置控件，获取控件，添加监听器等操作
case R.id.button2:
    AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
    dialog.setTitle("我是对话框");       //提示标题
    dialog.setMessage("重要信息提示");    //提示内容
    dialog.setCancelable(false);        //不能用back键返回
    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {      //设置确定按钮的内容和点击事件
        @Override
        public void onClick(DialogInterface dialog, int which) {
        }
    });
    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {  //设置取消按钮的内容和点击事件
        @Override
        public void onClick(DialogInterface dialog, int which) {
        }
    });
    dialog.show();  //让对话框显示出来
    break;
```



### 1.9 特殊形式对话框

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210117133432183.png" alt="image-20210117133432183" style="zoom:80%;" />



#### 列表对话框：

```java
        final String[] items = new String[]{"吃饭", "睡觉", "打游戏", "玩手机"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("娱乐事情");
        builder.setItems(items, new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                  Toast.makeText(MainActivity.this, "您选了" + items[which], Toast.LENGTH_SHORT).show();
             }
        });
     builder.show();
```



#### 单选对话框：

```java
     final String[] items1 = new String[]{"吃饭", "睡觉", "打游戏", "玩手机"};
     AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
     builder1.setTitle("娱乐事情");
     builder1.setSingleChoiceItems(items1, 0, new DialogInterface.OnClickListener() {    //参数二为默认选择第几项
           @Override
           public void onClick(DialogInterface dialog, int which) {
                  Toast.makeText(MainActivity.this, "您选择了" + items1[which], Toast.LENGTH_SHORT).show();
           }
     });
     builder1.setPositiveButton("确定", null);//null为单击确定时的事件监听器
     builder1.show();
```



#### 多选对话框：

```java
     final boolean[] checkedItems = new boolean[]{false, true, true, false};//选项的默认状态
     final String[] items2 = new String[]{"吃饭", "睡觉", "打游戏", "玩手机"};//每个选项
     AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
     builder2.setTitle("娱乐事情");
     builder2.setMultiChoiceItems(items2, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                  checkedItems[which] = isChecked;//被单机时改变选项的状态
            }
      });
     builder2.setPositiveButton("确定", new DialogInterface.OnClickListener() { //添加确定按钮和点击事件
           @Override
           public void onClick(DialogInterface dialog, int which) {
                   String result = "";//记录结果
                   for (int i = 0; i < checkedItems.length; i++) {
                         if (checkedItems[i]) {
                             result += items2[i] + "、";//把选中的值添加到结果中
                         }
                   }
                   if (!"".equals(result)) {
                        Toast.makeText(MainActivity.this, "您选择了" + result, Toast.LENGTH_SHORT).show();
                   }
            }
      });
      builder2.show();
```





#### 进度条对话框：

会在界面中的对话框显示一个进度条，一般用于表示当前操作比较耗时，让用户耐心等待。它的用法和 Alertdialog 也比较相似

```java
    //省略设置控件，获取控件，添加监听器等操作
    ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
    progressDialog.setTitle("This is ProgressDialog");	//提示标题
    progressDialog.setMessage("Loading……");				//提示内容
    progressDialog.setCancelable(true);			//能用back键返回
    progressDialog.show();				//让对话框显示出来
```

> 注意：如果 setCancelable(false);  不能用back返回，则当数据加载完成后必须调用 progressDialog.dismiss()  关闭对话框，否则一直存在



#### 日期选择对话框：

```java
 	  result = "";
    Calendar cale1 = Calendar.getInstance();
    new DatePickerDialog(MainActivity.this,new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
            //这里获取到的月份需要加上1哦~
            result += "你选择的是"+year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日";
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }
    }
            ,cale1.get(Calendar.YEAR)
            ,cale1.get(Calendar.MONTH)
            ,cale1.get(Calendar.DAY_OF_MONTH)).show();
```



#### 时间选择对话框：

```java
	result = "";
    Calendar cale2 = Calendar.getInstance();
    new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            result = "";
            result += "您选择的时间是:"+hourOfDay+"时"+minute+"分";
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }
    }, cale2.get(Calendar.HOUR_OF_DAY), cale2.get(Calendar.MINUTE), true).show();
```



### 1.10 ScrollView滚动条

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <Button
        android:id="@+id/btn_up"
        android:text="最上方" />

    <Button
        android:id="@+id/btn_down"
        android:text="最下方" />

    <ScrollView
        android:id="@+id/scrollView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:scrollbarThumbVertical=“""/> <!--设置滑块样式-->

        <TextView
            android:id="@+id/txt_show"/>
    </ScrollView>

</LinearLayout>
```

```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_down;
    private Button btn_up;
    private ScrollView scrollView;
    private TextView txt_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //省略findView
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 5; i++) {
            sb.append("呵呵 * " + i + "\n");
        }
        txt_show.setText(sb.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_down:
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);   //滑动到最下方
                break;
            case R.id.btn_up:
                scrollView.fullScroll(ScrollView.FOCUS_UP);     //滑动到最上方
                break;
        }
    }
}
```







### 1.11 单选框：RadioGroup 

利用 RadioGroup 配合 RadioButton 实现

==要为每个RadioButton添加一个id，不然单选功能会失效！==

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" >

    <TextView
        android:text="请选择性别"/>

    <RadioGroup
        android:id="@+id/radioGroup"
        android:orientation="horizontal">

        <RadioButton
            android:id="@+id/btnMan"
            android:text="男"
            android:checked="true"/><!--默认被选择-->

        <RadioButton
            android:id="@+id/btnWoman"
            android:text="女"/>
    </RadioGroup>

    <Button
        android:id="@+id/btnpost"
        android:text="提交"/>
</LinearLayout>
```

```java
        //当按钮选项发生变化时被调用，通过checkedID关联到对应的RadioButton
        radgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radbtn = (RadioButton) findViewById(checkedId);
                Toast.makeText(getApplicationContext(), "按钮组值发生改变,你选了" + radbtn.getText(), Toast.LENGTH_LONG).show();
            }
        });

        //点击提交按钮时，获取单选框中的值
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < radgroup.getChildCount(); i++) {//遍历RadioGroup的子项
                    RadioButton rd = (RadioButton) radgroup.getChildAt(i);//获取到每一个RadioButton
                    if (rd.isChecked()) {//判断是否被选中
                        Toast.makeText(getApplicationContext(), "点击提交按钮,获取你选择的是:" + rd.getText(), Toast.LENGTH_LONG).show();
                        break;
                    }
                }
            }
        });
```





### 1.12 复选框：CheckBox

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <TextView
        android:text="请选择爱好"/>

    <CheckBox
        android:id="@+id/cb_one"
        android:text="打游戏" />

    <CheckBox
        android:id="@+id/cb_two"
        android:text="看肥皂剧" />

    <CheckBox
        android:id="@+id/cb_three"
        android:text="吃" />

    <Button
        android:id="@+id/btn_send"
        android:text="提交" />
</LinearLayout>
```

```java
    //当选项发生变化时调用
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(compoundButton.isChecked()) Toast.makeText(this,compoundButton.getText().toString(),Toast.LENGTH_SHORT).show();
    }

    //提交按钮点击时，判断每个选项是否被选中，提示选中内容
    @Override
    public void onClick(View view) {
        String choose = "";
        if(cb_one.isChecked())choose += cb_one.getText().toString() + "";
        if(cb_two.isChecked())choose += cb_two.getText().toString() + "";
        if(cb_three.isChecked())choose += cb_three.getText().toString() + "";
        Toast.makeText(this,choose,Toast.LENGTH_SHORT).show();
    }
```



#### 自定义的点击样式：

/drawable下新建rad_btn_selector.xml，编辑选择样式，引用drawable下的图片资源为样式按钮：

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:state_enabled="true"
        android:state_checked="true"
        android:drawable="@mipmap/ic_checkbox_checked"/>
    <item
        android:state_enabled="true"
        android:state_checked="false"
        android:drawable="@mipmap/ic_checkbox_normal" />
</selector>
```

1.然后在CheckBox或RadioButton上引用：

```java
 android:button="@drawable/rad_btn_selector"
```



2.或是在styles.xml中定义

```xml
 <item name="MyCheckBox">@drawable/rad_btn_selctor</item>
```

然后引用

```java
style="@style/MyCheckBox"
```



#### 改变按钮的位置：

```xml
<CheckBox
    android:id="@+id/cb_one"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="打游戏"
          
    android:button="@null"
    android:drawableTop="@android:drawable/btn_radio" /> <!--这里使用对应的方向属性改变->
```



### 1.13 计时器：Chronometer

![image-20210111210250603](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210111210250603.png)

```java
ch.setBase(SystemClock.elapsedRealtime());  //获取当前系统时间，并设置为计时器的起始时间
ch.start();
ch.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
    @Override
    public void onChronometerTick(Chronometer chronometer) {
        if (SystemClock.elapsedRealtime()-ch.getBase()>=60000){     //计时60秒停止
            ch.stop();
        }
    }
});
```

#### 案例： 倒计时60秒

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <Chronometer
        android:id="@+id/chronometer"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:textColor="#ff0000"
        android:textSize="60dip" />

    <LinearLayout
        android:orientation="horizontal">
        <Button
            android:id="@+id/btnStart"
            android:text="开始记时" />

        <Button
            android:id="@+id/btnStop"
            android:text="停止记时" />

        <Button
            android:id="@+id/btnReset"
            android:text="重置" />

        <Button
            android:id="@+id/btn_format"
            android:text="格式化" />
    </LinearLayout>
</LinearLayout>
```

```java
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStart:
                chronometer.start();// 开始计时
                break;
            case R.id.btnStop:
                chronometer.stop();// 停止计时
                break;
            case R.id.btnReset:
                chronometer.setBase(SystemClock.elapsedRealtime());// 复位
                break;
            case R.id.btn_format:
                chronometer.setFormat("Time：%s");// 更改时间显示格式
                break;
        }
    }

    @Override
    public void onChronometerTick(Chronometer chronometer) {
        String time = chronometer.getText().toString();
        if(time.equals("00:00")){
            Toast.makeText(MainActivity.this,"时间到了~",Toast.LENGTH_SHORT).show();
        }
    }
}
```





### 1.14 下拉列表：Spinner

```xaml
<Spinner
    android:id="@+id/spinner"
    android:entries="@array/hobby"	//使用该属性引用文件，填写每个下拉选项
    ></Spinner>
```

在values目录下新建arrays.xml文件，并填写该内容：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<resources>
    <string-array name="hobby">
        <item>全部</item>
        <item>电影</item>
        <item>图书</item>
        <item>游戏</item>
        <item>学习</item>
    </string-array>
</resources>
```





### 1.15 自动完成文本框：

AutoCompleteTextView （单个自动完成） 和  MultiAutoCompleteTextView （能输入多个自动完成的文本）

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <AutoCompleteTextView
        android:id="@+id/atv_content"
        android:layout_width="match_parent"
        android:layout_height="48dp"
        android:hint="请输入搜索内容"
        android:completionThreshold="1"		//这项属性决定输入几个字后匹配提示信息
        android:dropDownHorizontalOffset="5dp" />

    <MultiAutoCompleteTextView
        android:id="@+id/matv_content"
        android:layout_width="match_parent"
        android:layout_height="48dp"
        android:completionThreshold="1"
        android:dropDownHorizontalOffset="5dp"
        android:text="" />
</LinearLayout>
```

```java
public class MainActivity extends AppCompatActivity {
    private AutoCompleteTextView atv_content;
    private MultiAutoCompleteTextView matv_content;

    private static final String[] data = new String[]{"小猪猪", "小狗狗", "小鸡鸡", "小猫猫", "小咪咪"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, data);
        atv_content.setAdapter(adapter);

        matv_content.setAdapter(adapter);
        matv_content.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());//为其设置分隔符，这里是一个逗号
    }
}
```



### 1.16 Toast详解：

构造定制的Toast：

```java
  private void midToast(String str)
    {
        Toast toast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM , 0, 0);  //设置显示位置
        LinearLayout layout = (LinearLayout) toast.getView();
        layout.setBackgroundColor(Color.BLUE);
        ImageView image = new ImageView(this);
        image.setImageResource(R.mipmap.ic_icon_qitao); //加载想展示的图片资源文件
        layout.addView(image, 0);
        TextView v = toast.getView().findViewById(android.R.id.message);//这个ID是SDK包下的
        v.setTextColor(Color.YELLOW);     //设置字体颜色
        toast.show();
    }
```

但这个是图片位于文字上方，如果想要放在左边：

创建布局文件：view_toast_custom.xml

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/lly_toast"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@drawable/bg_toast"	//这里引用的是一张圆角纯色图片
    android:orientation="horizontal">
	
    <!-- 要显示的图片-->
    <ImageView
        android:id="@+id/img_logo"
        android:layout_width="24dp"
        android:layout_height="24dp"
        android:layout_marginLeft="10dp"
        android:src="@mipmap/iv_lol_icon1" />	

    <!-- 还可以设置字体大小-->
    <TextView
        android:id="@+id/tv_msg"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginLeft="10dp"
        android:textSize="20sp" />
</LinearLayout>
```

/drawable/bg_toast.xml

```xml
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- 设置透明背景色 -->
    <solid android:color="#BADB66" />
    <!-- 设置一个黑色边框 -->
	<stroke
        android:width="3px"
        android:color="#000000" />
    <!-- 设置四个圆角的半径 -->
    <corners
        android:bottomLeftRadius="50px"
        android:bottomRightRadius="50px"
        android:topLeftRadius="50px"
        android:topRightRadius="50px" />
    <!-- 设置一下边距,让空间大一点 -->
    <padding
        android:bottom="5dp"
        android:left="5dp"
        android:right="5dp"
        android:top="5dp" />
</shape>  
```

```java
private void midToast(String str)
{
    View view = getLayoutInflater().inflate(R.layout.view_toast_custom, (ViewGroup) findViewById(R.id.lly_toast));
    ImageView img_logo =   view.findViewById(R.id.img_logo);
    TextView tv_msg =   view.findViewById(R.id.tv_msg);
    tv_msg.setText(str);
    Toast toast = new Toast(getApplicationContext());
    toast.setGravity(Gravity.CENTER, 0, 0);
    toast.setDuration(Toast.LENGTH_LONG);
    toast.setView(view);
    toast.show();
}
```





### 1.17 悬浮框：PopupWindow

一个弹出窗口控件，可以用来显示任意View，而且会浮动在当前activity的顶部【QQ列表里长按某一个子项时弹出的那个东西】



先创建悬浮框的布局文件 item_popup.xml：

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal">
    <Button
        android:id="@+id/btn_xixi"
        android:text="嘻嘻" />

    <Button
        android:id="@+id/btn_hehe"
        android:text="呵呵"/>
</LinearLayout>
```

主布局文件放置一个按钮（id：btn_show）用于显示，这里省略xml文件，MA：

```java
public class MainActivity extends AppCompatActivity{
    private Button btn_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopWindow(v);
            }
        });
    }

    private void initPopWindow(View v) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_popup, null, false);
        Button btn_xixi = view.findViewById(R.id.btn_xixi);
        Button btn_hehe = view.findViewById(R.id.btn_hehe);
        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        //以下代码为了点击非PopupWindow区域，PopupWindow会消失的
        // 如果没有下面的代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键，PopupWindow并不会关闭
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效


        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(v, 50, 0);

        //设置popupWindow里的按钮的事件
        btn_xixi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你点击了嘻嘻~", Toast.LENGTH_SHORT).show();
            }
        });
        btn_hehe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你点击了呵呵~", Toast.LENGTH_SHORT).show();
                popWindow.dismiss();
            }
        });
    }
}
```









### 1.18 Mean菜单

/res/menu下新建一个menu.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/add_item"		//ID进行唯一标识，titile表示显示的信息
        android:title="Add"></item>		
    <item
        android:id="@+id/remove_item"
        android:title="Remove"></item>
</menu>
```

MA：

```java
public class MainActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);  //得到MenuInflater对象，再调用inflate方法给当前活动创建菜单
        //第一个参数用于指定通过哪一个资源文件创建菜单，第二个参数是上面的形参，指定我们的菜单将添加到哪一个Menu对象中
        return true;  //true表示显示创建的菜单
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {        //添加菜单响应事件
        switch (item.getItemId()) {        //通过item的ID判断选择的是哪一个菜单
            case R.id.add_item:
                Toast.makeText(this, "你点了ADD按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this, "你点了Remove按钮", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
```



### 1.19 上下文菜单：

效果等同于PopupMenu

/menu/menu下：

```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/add_item"
        android:title="Add"></item>
    <item
        android:id="@+id/remove_item"
        android:title="Remove"></item>
</menu>
```

xml中一个TextView（id：TextView），MA：

```java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //为控件注册上下文菜单
        TextView textview = findViewById(R.id.TextView);
        registerForContextMenu ;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
         new MenuInflater(this).inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Toast.makeText(this, "你点了添加", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this, "你点了移除", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
```







### 1.20 最上面的导航条：ActionBar

可以在 Manifest 中设置主题为 noActionBar 的主题样式来隐藏主题

```java
//或者以下方式可以
ActionBar actionBar = getSupportActionBar();
actionBar.hide();
```

AB上的每一个子项称为Action Item。

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210117102539986.png" alt="image-20210117102539986" style="zoom:67%;" />

ActionBar分为了四个区域：根据手机屏幕尺寸，位置会相对不同，横屏时能多显示一些菜单项（always除外，它会占用其他区域的空间）

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210117104946093.png" alt="image-20210117104946093" style="zoom:67%;" />

![image-20210117103627370](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210117103627370.png)



#### 自定义ActionItem按钮：

新建一个/menu/menu.xml，编写代码如下：

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <item
        android:id="@+id/search"
        android:icon="@drawable/message_left"	//设置显示图标样式
        android:title="search"
        app:showAsAction="always"></item>
    <item
        android:id="@+id/search1"
        android:icon="@drawable/message_left"
        android:title="search"
        app:showAsAction="ifRoom"></item>
    <item
        android:id="@+id/search2"
        android:icon="@drawable/message_left"
        android:title="search"
        app:showAsAction="ifRoom"></item>
</menu>
```

MA中创建选项菜单：

```java
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater=getMenuInflater(); //实例化对象
    inflater.inflate(R.menu.menu,menu);
    return super.onCreateOptionsMenu(menu);
}
```



#### 搜索栏：ActionView

![image-20210117105732664](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210117105732664.png)





```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <item
        android:id="@+id/search"
        android:title="搜索"
        app:actionViewClass="android.support.v7.widget.SearchView"
        app:showAsAction="always"></item>
</menu>
```

```java
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater=getMenuInflater(); //实例化对象
    inflater.inflate(R.menu.menu,menu);
    return super.onCreateOptionsMenu(menu);
}
```





## 二、事件处理



### 一、监听事件

监听事件已经接触过很多了，当事件源发生事件时，系统将会执行该事件源上监听器的对应处理方法。

①Event Source 事件源：即事件发生的场所，例如，按钮，窗口，菜单。
 	   ②Event 事件：通常指一次用户的操作，例如用户单击，双击等。
 	   ③Event Listener 事件监听器：负责监听事件源所发生的事件，并对各种事件做出相应的响应。



==优先级排序==：触摸、长按、点击

#### 长按事件：

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    TextView tv = findViewById(R.id.textview);
    tv.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            registerForContextMenu(v);  //将长按事件注册到菜单中
            openContextMenu(v);     //打开菜单
            return false;	//代表了当前动作是否完全消耗掉了该事件，如果同时有触摸和单击事件，且触摸事件的返回值为true，则不会执行单击事件
            }
        });
    }

    //创建菜单，为菜单添加参数
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("复制");
        menu.add("收藏");
        menu.add("举报");
    }
}
```



#### 触摸事件：

```java
Button button = findViewById(R.id.Button1);
button.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){	
            Log.i("MA:", "按下");
        }else if (event.getAction()==MotionEvent.ACTION_UP){
            Log.i("MA:", "抬起");
        }
        return false;	//代表了当前动作是否完全消耗掉了该事件，如果同时有触摸和单击事件，且触摸事件的返回值为true，则不会执行长按和单击事件
    }
});
```







### 二、回调事件

对于回调机制事件处理模型来说，事件源和监听器是统一的，或者说事件监听器完全消失了，当组件激发某个事件时，组件自己特定的方法会负责去处理

```java
 	@Override
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(this, "触摸", Toast.LENGTH_SHORT).show();
        return super.onTouchEvent(event);
    }
```



| 名称              | 对应的按键                         |
| ----------------- | ---------------------------------- |
| KEYCODE_VOLUME_UP | 音量加号键，同理 DOWN 为音量减号键 |
| KEYCODE_POWER     | 电源键                             |
| KEYCODE_BACK      | 返回键                             |
| KEYCODE_HOME      | 主屏建                             |
| KEYCODE_MENU      | 菜单键                             |

Android对每个按键都提供了三种回调机制：

onKeyDown（） 按键按下时触发

onKeyUp（）按键抬起时触发

onKeyLongPress（）按键长按时触发



#### 案例：按下返回键弹出提示：再按一次返回

```java
public class MainActivity extends AppCompatActivity {
    private long exitTime=0;	//定义上一次按下时间
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            exit();		//按下时执行该方法
        }
        return true;
    }

    private void exit() {
        //如果是本次按下时间和上次按下时间相差2秒以上，提示再按一次，否则直接退出
        if ((System.currentTimeMillis()-exitTime)>2000){
            Toast.makeText(this, "再按一次返回", Toast.LENGTH_SHORT).show();
            exitTime=System.currentTimeMillis();
        }else {
            finish();
            System.exit(0);
        }
    }
}
```



### 三、手势

监听各种手势的代码：

```java
public class MainActivity extends AppCompatActivity{
   
    private GestureDetector mDetector;  //识别手势的类
    private MyGestureListener mgListener;   //自定义的内部类，用于监听手势操作
    private final static String TAG = "MyGesture";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //实例化对象
        mgListener = new MyGestureListener();
        mDetector = new GestureDetector(this, mgListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }

    //自定义的内部类,继承View体系下的GestureDetector.OnGestureListener 
    //GestureDetector类识别各种手势，OnGestureListener提供了多个抽象方法，并根据GestureDetector的手势识别结果调用相应方法
    private class MyGestureListener implements GestureDetector.OnGestureListener {

        //MotionEvent类封装了手势、触摸笔、轨迹球等动作时间，内部封装了X和Y属性，记录横纵坐标
        @Override
        public boolean onDown(MotionEvent motionEvent) {
            Log.d(TAG, "onDown:按下");
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {
            Log.d(TAG, "onShowPress:手指按下一段时间,不过还没到长按");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            Log.d(TAG, "onSingleTapUp:手指离开屏幕的一瞬间");
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            Log.d(TAG, "onScroll:在触摸屏上滑动");
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            Log.d(TAG, "onLongPress:长按并且没有松开");
        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            Log.d(TAG, "onFling:迅速滑动，并松开");
            return false;
        }
    }
}
```

问题：实现OnGestureListener需要实现所有的手势，如果仅针对一种，继承SimpleOnGestureListener即可

```java
public class MainActivity extends AppCompatActivity{

    private GestureDetector mDetector;
    private MyGestureListener mgListener;
	private final static int MIN_MOVE = 200;   //最小距离

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //实例化对象
        mgListener = new MyGestureListener();
        mDetector = new GestureDetector(this, mgListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
            if(e1.getY() - e2.getY() > MIN_MOVE){
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                Toast.makeText(MainActivity.this, "通过手势启动Activity", Toast.LENGTH_SHORT).show();
            }else if(e1.getY() - e2.getY()  < MIN_MOVE){
                finish();
                Toast.makeText(MainActivity.this,"通过手势关闭Activity",Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    }
```





## 三、常见布局

通用属性：

| 属性          | 描述                                                         |
| ------------- | ------------------------------------------------------------ |
| layout_margin | 对外边距，默认方向左上。取值：x dp ，后可跟 Left 指定对某个方向的边距 |
| padding       | 对内边距，默认方向四周。取值：x dp ，后可跟 Left 指定对某个方向的边距 |

margin 是可以设置为负数的，比如一些图片的×一部分嵌入在图片内部

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210307212020506.png" alt="image-20210307212020506" style="zoom:67%;" />

### 2.1 LinearLayout（线性布局）

LinearLayout的属性：

| 属性           | 描述                                                         |
| -------------- | ------------------------------------------------------------ |
| orientation    | 取值：horizontal （水平）和 vertical (垂直)                  |
| gravity        | 内部组件在该布局下的对齐方式，多个属性值用 \| 连接，取值：center 等等…… |
| divider        | 为LinearLayout设置分割线的图片，也可以是颜色                 |
| showDividers   | 设置分割线所处位置：none、middle、begining、end              |
| dividerPadding | 设置分割线的边距                                             |

处于该布局下的控件属性：

| 属性           | 描述                                                         |
| -------------- | ------------------------------------------------------------ |
| layout_gravity | 表示该控件在父容器的位置，取值：center 等等……；如果是垂直线性布局，子控件引用center属性会处在水平方向居中的位置，如果是水平布局，则处在垂直方向居中的位置。另外，可以配合LinearLayout指定的gravity使用。比如gravity为center，layout_gravity为start，则控件会处在整个布局的中间最左边的起始位。 |
| weight         | 组件的权重，按比例划分。如果布局是垂直，则该属性会让垂直方向的属性值失效，如果垂直方向属性值为match_parent，则会出现特殊的情况 |

> 使用权重注意：
>
> 如果有三个控件，占比1:2:3，且为水平排列
>
> 它们的宽度如果为wrap_content则正常划分为1/6,2/6,3/6
>
> 如果为match_parent（fill_parent）时，此时屏幕只有一个，因此1-3=-2，1-2*(1/6)=2/3，则A站屏幕的2/3，以此类推。



==缺点==：

无法在同一个水平线上设置一左一右两个控件

另外，就是当界面比较复杂的时候，需要嵌套多层的 LinearLayout,这样就会降低UI Render的效率(渲染速度),而且如果是listview或者GridView上的 item,效率会更低,另外太多层LinearLayout嵌套会占用更多的系统资源,还有可能引发stackoverflow





#### 设置分割线做法二：

直接在布局中添加一个View

```xml
<View  
    android:layout_width="match_parent"  
    android:layout_height="1px"  
    android:background="#000000" />
```





### 2.2 RelativeLayout(相对布局)

组件没有定义其他影响布局的属性时，会默认堆叠在一起



布局属性：

| 属性    | 描述                                                         |
| ------- | ------------------------------------------------------------ |
| gravity | 容器内的对齐方式，多个属性值用 \| 连接，注意不要结合其他控件对齐属性使用，会有意想不到的效果 |

该布局下的控件属性：

| 属性                    | 描述                                                         |
| ----------------------- | ------------------------------------------------------------ |
| layout_alignParentRight | 和父容器右对齐，取值：true、false，其余对齐方式Right更换为Left等即可。 |
| layout_centerVertical   | 垂直居中，取值：true、false，水平居中同理。                  |
| layout_toLeftOf         | 在参考组件的左边，取值为参考组件的ID。同理 Right             |
| layout_above            | 在参考组件的上面，取值为参考组件的ID。同理 below             |
| layout_alignTop         | 对齐参考组件的上边界，同理Bootom、Left、Right                |





### 2.3 FrameLayout (帧布局)

没有方便的定位方式，所有控件都默认摆在左上角，控件可使用 padding 属性定位，或使用如下属性指定摆放位置

| 属性值         | 描述                                     |
| -------------- | ---------------------------------------- |
| layout_gravity | 指定组件的摆放位置，多个属性值用 \| 连接 |



### 2.4 百分比布局

只有**线性布局**能够实现**按比例指定控件大小**的功能，其他两种布局都不支持。因此提供了 PercentFrameLayout 和 PercentRelativeLayout 两种全新布局。

==使用百分比布局之前需要在项目 buid.gradle 中添加百分比布局库的依赖==

```java
implementation 'com.android.support:appcompat-v7:24.2.1'   //这个是gradle本来就有的，不用添加
implementation 'com.android.support:percent:24.2.1'		//注意，这个版本要和上面的一致，不然会导致R文件丢失
```

```xml
<!--需要定义一个app的命名-->
<android.support.percent.PercentFrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto" 
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.layouttest.MainActivity">
    
    <!--使用app前缀的属性就是因为前面定义了app空间，android属性也是一样的道理 -->
    <Button
        android:id="@+id/button1"
        android:text="Button 1"
        android:layout_gravity="left|top"
        app:layout_widthPercent="50%"		
        app:layout_heightPercent="50%"/>
</android.support.percent.PercentFrameLayout>    <!--因为百分比布局不是SDK中的，所以需要把完整的包路径写出来-->
```



### 2.6 GridLayout（网格布局）

这个布局下，控件不需要指定宽高，直接分配父容器的比例

布局属性

| 属性        | 描述       |
| ----------- | ---------- |
| columnCount | 列数       |
| rowCount    | 行数       |
| orientation | 排序的方式 |

控件属性

| 属性                  | 描述                                                         |
| --------------------- | ------------------------------------------------------------ |
| layout_columnSpan     | 横跨列数，同理RowSpan为横跨行数                              |
| layout_gravity="fill" | 如果设置了横跨属性，加这个属性则显示跨n行，不加为占n行但宽度仅包裹自身 |



## 四、 自定义控件

View是安卓最基本的一种UI组件，它可以在屏幕上绘制一块矩形区域，并响应这块区域的各种事件，因此我们使用的各种控件就是在View的基础上又添加了各自特有的功能。

ViewGroup是一个用于放置控件和布局的容器。



#### 引入布局:

首先创建一个title.xml文件，定义好组件，可以使用background属性设置空间背景

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android" 
        android:id="@+id/title"      >
    
     <!--返回键-->
    <Button		
        android:id="@+id/title_back"
        android:text="back"/>

    <!--中间标题-->
    <TextView	
        android:id="@+id/title_text"
        android:gravity="center"
        android:text="Title" />
    
    <!-- 一个编辑按钮 -->
    <Button		
        android:id="@+id/title_edit"
        android:text="Edit" />
</LinearLayout>
```

在所需要的xml布局中，引入上面的自定义布局文件：

```xml
<include layout="@layout/title"></include>
```

然后在mainActivity中隐藏原有ActionBar

```java
protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
    }
```



#### 添加事件处理：

引入布局能解决重复编写布局代码的问题，但是如果布局中有一些控件要求能响应事件，且响应事件相同，这种情况最好使用自定义控件解决。

新建一个TitleLayout，继承LinearLayout，让它称为自定义的标题栏控件，代码如下：

```java
public class TitleLayout extends LinearLayout {
    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        //from方法构建一个LayoutInflater对象，然后调用inflate完成动态加载布局文件
        //第一个参数是布局文件ID，第二个参数是给加载好的布局添加一个父布局
        LayoutInflater.from(context).inflate(R.layout.title,this);
        
        //注册监听器
        Button titleBack= findViewById(R.id.title_back);
        Button titleEdit= findViewById(R.id.title_edit);
        titleBack.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            ((Activity) getContext()).finish();
            }
        });

   		titleEdit.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(),"你点了编辑按钮",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
```

然后在需要引入的布局xml文件中，添加如下代码：（作用和引入布局是一样的）

```xml
<com.example.layouttest.TitleLayout   //注意这里需要指明控件的完整类名
    android:layout_width="match_parent"
    android:layout_height="wrap_content"/>
```







## 五、海量数据的展示（适配器和列表控件）

Adapter是用来帮助填充数据的中间桥梁，将各种数据以合适的形式显示到view上,提供给用户看

![image-20210309144121238](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210309144121238.png)



### 3.1 ListView

#### 简单用法：

##### 1.通过数组或集合使用：

在布局xml文件中添加ListView控件：

```xml
<ListView
    android:id="@+id/list_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"></ListView>
```

在JAVA代码中将要显示的东西传入，建立连接：

![image-20201217145056736](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20201217145056736.png)



**Adapter的第二个参数解析：**

| 参数                             | 描述                       |
| -------------------------------- | -------------------------- |
| simple_list_item_1               | 单独一行的文本框           |
| simple_list_item_2               | 两个文本框组成             |
| simple_list_item_checked         | 每项有一个可以选中的列表项 |
| simple_list_item_multiple_choice | 每项带有一个复选框         |
| simple_list_item_single_choice   | 都带有一个单选钮           |





##### 2.通过xml文件引用：

/res/values创建 myarray.xml 文件，直接在ListView属性中引用：

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string-array name="myarray">
        <item>语文</item>
        <item>数学</item>
        <item>英语</item>
    </string-array>
</resources>
```

```xml
<ListView
    android:id="@+id/list_test"
    android:layout_height="match_parent"
    android:layout_width="match_parent"
    android:entries="@array/myarray"/>
```



 



#### 定制ListView的显示界面：

新建实体类：

```java
public class Fruit {
    private String name;
    private int imageID;

    public Fruit(String name, int imageID) {
        this.name = name;
        this.imageID = imageID;
    }

    public String getName() {
        return name;
    }

    public int getImageID() {
        return imageID;
    }
}
```

为每一个ListView的子项指定一个我们自定义的布局，fruit_item.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:orientation="vertical">

    <!--每个子项显示一张图片和一行文字-->
    <ImageView
        android:id="@+id/fruit_image"
        android:layout_width="80dp"
        android:layout_height="80dp" />

    <TextView
        android:id="@+id/fruit_name"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
</LinearLayout>
```

创建一个自定义适配器（类），继承 ArrayAdapter ，并指定泛型

```java
public class FruitAdapter extends ArrayAdapter<Fruit> {

    private  int resourceID;

    //形参是【上下文对象，每个子项的ID，对象实例的集合】
    public FruitAdapter(Context context, int textViewResourceID, List<Fruit> objects) {	
        super(context, textViewResourceID, objects);
        resourceID=textViewResourceID;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fruit fruit=getItem(position);  //获取当前项的fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resourceID,parent,false); //为这个子项加载传入的布局

        ImageView fruitImage =  view.findViewById(R.id.fruit_image); //获取控件
        TextView fruitName= view.findViewById(R.id.fruit_name);

        fruitImage.setImageResource(fruit.getImageID()); //设置图片
        fruitName.setText(fruit.getName());     //设置文字

        return view;
    }
```

```java
public class MainActivity extends AppCompatActivity {

    private List<Fruit> fruitList=new ArrayList<>();	//新建一个list列表存储Fruit对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFruits();   //初始化数据
        FruitAdapter adapter=new FruitAdapter(MainActivity.this,R.layout.fruit_item,fruitList);		//添加适配器
        ListView listView= findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

     private void initFruits() {
        for (int i = 0; i < 20; i++) {		//创建20遍图片，防止资源太少无法滑动
            Fruit image= new Fruit("image"+i,R.mipmap.ic_launcher);
            fruitList.add(image);
        }
    }
}
```





#### 提升效率：

上述 ListView 的运行时效率很低，因为 FruitAdapter 的 getView（） 方法中，每次都将布局重新加载了一遍，当ListView快速滚动的时候，就会成为性能的瓶颈。getView（）方法中的 convertView 参数用于将之前加载好的布局进行缓存，以便之后重用。

修改 FruitAdapter 的代码：

```java
@NonNull
@Override
public View getView(int position, View convertView, ViewGroup parent) {
    Fruit fruit=getItem(position);  //获取当前项的fruit实例
    //修改过后的：
    View view;
    if (convertView==null){		//如果convertView为空，则加载布局
        view=LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
    }else {		//如果convertView不为空，则进行重用
        view=convertView;
    }

    ImageView fruitImage = view.findViewById(R.id.fruit_image); //获取控件
    TextView fruitName= view.findViewById(R.id.fruit_name);

    fruitImage.setImageResource(fruit.getImageID()); //设置图片
    fruitName.setText(fruit.getName());     //设置文字

    return view;
}
```



再次优化每次调用  findViewById（）获取实例的问题：

```java
 @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fruit fruit=getItem(position);  //获取当前项的fruit实例
        //修改的代码
        View view;
        ViewHolder viewHolder;  //新增一个内部类
        if (convertView==null){     
 //如果convertView为空，创建一个ViewHolder对象，并将控件得到实例都存放在ViewHolder里，然后调用setTag（）方法，将ViewHolder存入View中
            view=LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
            viewHolder =new ViewHolder();
            viewHolder.fruitIamge= view.findViewById(R.id.fruit_image);
            viewHolder.fruitName= view.findViewById(R.id.fruit_name);
            view.setTag(viewHolder);
        }else {     
 			//否则调用View的getTag重新取出ViewHolder
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }

        viewHolder.fruitIamge.setImageResource(fruit.getImageID()); 
        viewHolder.fruitName.setText(fruit.getName());     

        return view;
    }

    class ViewHolder{   //使用内部类对控件实例缓存
        ImageView fruitIamge;
        TextView fruitName;
    }
}
```





#### 点击事件：

需要注意的是，ListView的点击事件注册是子项的点击事件

```java
listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fruit fruit=fruitList.get(position);		//通过position判断用户点击的是哪一个子项，然后获取到对应的实例对象
        Toast.makeText(MainActivity.this,fruit.getName(),Toast.LENGTH_SHORT).show();
    }
});
```

如果子项中存在多个按钮，想给这些按钮注册点击事件就较为麻烦，因此引入了RecyclerView



### 3.2 RecyclerView

ListView的性能较差，扩展性不好（即不能实现数据横向滚动）。因此提供了更强大的 RecyclerView，不仅可以轻松实现和 ListView 相同的效果，还优化了各种不足之处。

#### 常规用法：

想要使用该控件，==也需要在build.gradle中添加相应的依赖库。==

> 4.x 版本的AS已将该控件添加至lib包，不用添加依赖

```java
implementation 'com.android.support:recyclerview-v7:24.2.1'  
```

在布局文件中添加RecyclerView：

```xml
<android.support.v7.widget.RecyclerView
    android:id="@+id/recycler_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

 同样加入 Fruit 类和 fruit_item.xml 布局文件（作为RecyclerView的每个子项）,注意布局方式的高度应该为恰好包含

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/fruit_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">
   <!--注意高度改为了wrap_content，不然每个子项会占满整个屏幕-->
   <!--方向改为了水平，根据自己喜好来就行 -->
    
    <!--每个子项显示一张图片和一行文字-->
    <ImageView
        android:id="@+id/fruit_image"
        android:layout_width="80dp"
        android:layout_height="80dp" />

    <TextView
        android:id="@+id/fruit_name" />
</LinearLayout>
```

创建适配器，继承 RecyclerView.Adapter ，指定泛型 FruitAdapter.ViewHolder

```java
public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {   	
    private List<Fruit> mFruitList; //存放Fruit类的列表

    //外层类的构造函数
    public FruitAdapter(List<Fruit> fruitList) {
        mFruitList = fruitList;   //把要展示数据传进来并赋值给全局变量
    }

    private OnItemClickListner mOnItemClickListner;

    //内部接口，暴漏给外部设置点击事件
    public interface OnItemClickListner {
        void onItemClick(View view, int position);
        void onItemImgClick(View view, int position);
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        mOnItemClickListner = onItemClickListner;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      	//加载布局，并创建VH
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //得到当前项实例并设置数据
        Fruit fruit = mFruitList.get(position);       
        holder.fruitImage.setImageResource(fruit.getImageID());        
        holder.fruitName.setText(fruit.getName());
				//设置子类需要的监听
        if (mOnItemClickListner != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListner.onItemClick(holder.itemView, position);
                }
            });

            holder.fruitImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListner.onItemImgClick(holder.itemView, position);
                }
            });
        }
    }

    //统计数量
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    //内部类
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(View view) {      //构造函数，这个view就是RecyclerView子项的最外层布局
            super(view);
            fruitImage = view.findViewById(R.id.fruit_image);//获取布局中的控件
            fruitName =  view.findViewById(R.id.fruit_name);
        }
    }
}
```

MA代码中：

```java
public class MainActivity extends AppCompatActivity {
    private List<Fruit> fruitList=new ArrayList<>();	//新建一个list列表存储Fruit对象
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFruits();//初始化List列表
        recyclerView= findViewById(R.id.recycler_view);
        //RecyclerView需要指定一个布局方式，这里使用的线性布局
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FruitAdapter fruitAdapter = new FruitAdapter(mFruits);
        recyclerView.setAdapter(fruitAdapter);       //设置适配器
        //设置监听器
      	fruitAdapter.setOnItemClickListner(new FruitAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(Main3Activity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemImgClick(View view, int position) {
                Fruit fruit = mFruits.get(position);
                Toast.makeText(Main3Activity.this, "点击了"+fruit.getImageID(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initFruits() {
        for (int i = 0; i < 20; i++) {		//创建20遍图片，防止资源太少无法滑动
            Fruit image= new Fruit("image"+i,R.mipmap.ic_launcher);
            fruitList.add(image);
        }
    }
}
```



#### 其他布局方式：

如果想**改成横向的滚动**，只需要略微修改fruit_item中的布局，将LinearLayout改为垂直排列，并将宽度指定为合适大小（例如50dp），然后把MainActivity中指定排列方式即可：

```java
LinearLayoutManager layoutManager =new LinearLayoutManager(this);  
layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);	//两句话中间指定横向排列即可
recyclerView.setLayoutManager(layoutManager);   
```



除了LinearLayoutManager之外，RecyclerView 还提供了 GridLayoutManager（网格布局） 和 StaggeredGridLayoutManager（瀑布流布局） 这两种布局排列方式。

想换成瀑布流布局，重新指定布局方式即可：

```java
//第一个参数用于指定列数，第二个参数指定布局排列方向
StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
recyclerView.setLayoutManager(layoutManager);
```



#### 刷新：

在Adapter中把对应的集合中的元素删除，或增加，再调用 `notifyDataSetChange()`即可

注意：刷新不出结果可能是`Activity`或`Fragment`没刷新，需要在生命周期函数中重新设置下适配工作。生命周期的内容参见下章



特殊的下拉刷新样式：

引入布局

```java
    //RV刷新
    api  'com.scwang.smart:refresh-layout-kernel:2.0.1'
    api  'com.scwang.smart:refresh-header-classics:2.0.1'
```

```java
<com.scwang.smart.refresh.layout.SmartRefreshLayout
        android:id="@+id/srl"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_margin="12dp"
        android:layout_weight="1">

        <com.scwang.smart.refresh.header.ClassicsHeader
            android:layout_width="match_parent"
            android:layout_height="150dp" />

        <android.support.v7.widget.RecyclerView
            android:id="@+id/rvFactoryList"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
    </com.scwang.smart.refresh.layout.SmartRefreshLayout>
```

```java
   //下拉刷新
   refreshLayout.setOnRefreshListener(listener-> {
        getData();
   });

	//取消刷新
	 refreshLayout.finishRefresh(false);
```



#### 滚动到某一项：

```java
recyclerView.scrollToPosition(mFruits.size() - 1);  //滑到第几项
```













### 3.3 案例：编写聊天界面

先准备两张nine-pitch图片（message_left 和 message_right），作为消息发送的框架背景

用 RecycleView，在 build.gradle 中导入依赖库。

#### 1.编写主页面

首先需要用到的一个RecycleView，然后RecycleView下面有一个文本框，一个按钮用于发送所需消息。

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" >

    <android.support.v7.widget.RecyclerView
        android:id="@+id/msg_recyler"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        ></android.support.v7.widget.RecyclerView>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <EditText
            android:id="@+id/input_text"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:hint="此处写文本"
            android:maxLines="2"
            />

        <Button
            android:id="@+id/send"
            android:text="发送"/>
    </LinearLayout>
</LinearLayout>
```



#### 2.然后确定每个item子项的布局：

每一个子项为左右两个对话框，根据收发消息的不同状态，一边显示，另一边隐藏，msg_item：

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="10dp" >

    <LinearLayout
        android:id="@+id/left_layout"       
        android:layout_gravity="left"
        android:background="@drawable/message_left"  >
        
        <TextView
            android:id="@+id/left_msg"
            android:layout_gravity="left|top"
            android:layout_margin="10dp"
            android:textColor="#fff"  />
    </LinearLayout>

    <LinearLayout
        android:id="@+id/right_layout"
        android:layout_gravity="right"
        android:background="@drawable/message_right">
        
        <TextView
            android:id="@+id/right_msg"
            android:layout_gravity="left|top"
            android:layout_margin="10dp"  />
    </LinearLayout>
</LinearLayout>
```





#### 3.定义一个消息类Msg

```java
public class Msg {
    public static final int TYPE_RECEIVED=0;
    public static final int TYPE_SNET=1;
    private String content; //消息内容
    private int type;       //消息类型：收到或发出消息，可选值为上面定义的两个终极变量

    public Msg(String content,int type){
        this.content=content;
        this.type=type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
```



#### 4.建立消息类的适配器：

让每一条消息成为一个Item子项：

```java
public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<Msg> mMsgList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout leftLayout,rightLayout;
        TextView leftMsg,rightMsg;

        public ViewHolder(View view) {
            super(view);
            leftLayout=  view.findViewById(R.id.left_layout);
            rightLayout= view.findViewById(R.id.right_layout);
            leftMsg=  view.findViewById(R.id.left_msg);
            rightMsg=  view.findViewById(R.id.right_msg);
        }
    }

    public MsgAdapter(List<Msg> mMsgList) {
        this.mMsgList = mMsgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Msg msg=mMsgList.get(position);
        if (msg.getType()==Msg.TYPE_RECEIVED){
            //如果收到消息，显示左边的消息布局，右边隐藏
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
        }else if (msg.getType()==Msg.TYPE_SNET){
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
}
```



#### 5.最后在MA中，确定子项的布局方式，找到对应控件，确定监听事件即可

```java
public class MainActivity extends AppCompatActivity {

    private List<Msg> msgList=new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMsgs(); //初始化数据消息
        inputText=  findViewById(R.id.input_text);    //找到控件
        send=  findViewById(R.id.send);
        msgRecyclerView= findViewById(R.id.msg_recyler);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);    //设置为线性布局管理器（默认垂直）
        msgRecyclerView.setLayoutManager(layoutManager);

        adapter=new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=inputText.getText().toString();
                if (!"".equals(content)){
                    Msg msg=new Msg(content,Msg.TYPE_SNET);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size()-1);   //有消息时刷新RecyclerView中的显示
                    msgRecyclerView.scrollToPosition(msgList.size()-1); //将显示的数据定位到最后一行，方便用户查看上一条消息
                    inputText.setText(""); //清空输入框
                }
            }
        });
    }

    private void initMsgs() {
        Msg msg1=new Msg("Hello,李四",Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2=new Msg("Helo,张三",Msg.TYPE_SNET);
        msgList.add(msg2);
    }
}
```



### 3.4 ViewPager

可以实现翻页效果的工具

首先准备好三张布局文件：layout1.xml，其余两张类似，更改下颜色即可

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#ff00ffff"
    android:orientation="vertical">

    <TextView
        android:text="layout1"/>

</LinearLayout>
```



主布局文件用一个ViewPager填满：

```xml
<!-- 根据不同版本的AS，有android v4等不同包下的VP，但用法相同-->
<androidx.viewpager.widget.ViewPager
    android:id="@+id/vp"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```



然后设置一个适配器：MyAdapter

```java
public class MyAdapter extends PagerAdapter {

    private List<View> mListView;

    public MyAdapter(List<View> mListView) {
        this.mListView = mListView;
    }

     //给指定位置的View添加到ViewGroup中，创建并显示；返回一个代表新增页面的对象（view本身）
         /**
         * 相当于getView方法
         * @param container ViewPager自身
         * @param position 当前实例化页面的位置
         * @return
         */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(mListView.get(position), 0);
        return mListView.get(position);
    }

  	//统计页面数量，如果想左右无限划，设置一个很大的值即可，然后在有position的地方对数组大小取模。
    //如果想要一开始就能左滑，还需要viewpager.setCurrentItem(posi) [posi为很大值的中间值，且为数组的整数倍，即x/2-x/2%数组大小]
    @Override
    public int getCount() {
        return mListView.size();
    }

    //判断instantiateItem()函数返回的key与一个页面视图是否是【同一个视图】
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    //VP默认加载三页，当前页和前后页，多余的页用该方法销毁。
          /**
         * 释放资源
         * @param container viewpager
         * @param position 要释放的位置
         * @param object 要释放的页面
         */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mListView.get(position));
    }
}
```



MA：

```java
public class MainActivity extends AppCompatActivity {
    List<View> viewList = new ArrayList<>();   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ViewPager viewPager = findViewById(R.id.vp);
        //加载三个布局
        LayoutInflater lf = getLayoutInflater().from(this);
        View view1 = lf.inflate(R.layout.layout1, null);    
        View view2 = lf.inflate(R.layout.layout2, null);
        View view3 = lf.inflate(R.layout.layout3, null);

        //将view添加到集合中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        MyAdapter myAdapter = new MyAdapter(viewList);//view的集合传递给adpter
        viewPager.setAdapter(myAdapter);
    }
}
```



##### 其他VP的知识点：

VP滑动时的监听：

```java
    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        /**
         * 当页面滚动了的时候回调这个方法
         *
         * @param position             当前页面的位置
         * @param positionOffset       滑动页面的百分比
         * @param positionOffsetPixels 在屏幕上滑动的像数
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        /**
         * 当某个页面被选中了的时候回调
         *
         * @param position 被选中页面的位置
         */
        @Override
        public void onPageSelected(int position) {
        }

          /**
         当页面滚动状态变化的时候回调这个方法
         静止->滑动
         滑动-->静止
         静止-->拖拽
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
```



如果想VP自动轮播，可以使用Handler：

```java
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int item = viewpager.getCurrentItem()+1;
            viewpager.setCurrentItem(item);

            //延迟发消息
            handler.sendEmptyMessageDelayed(0,4000);
        }
    };

//MA中发送一次消息即可
handler.sendEmptyMessageDelayed(0, 4000);
```



如果想按住VP中的控件，停止滑动：

```java
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN://手指按下
                        handler.removeCallbacksAndMessages(null);//移除消息队列的所有消息
                        break;
                    case MotionEvent.ACTION_UP://手指离开
                        handler.removeCallbacksAndMessages(null);
                        handler.sendEmptyMessageDelayed(0, 4000);
                        break;
                }
                return false;
            }
        });
```

> MotionEvent.ACTION_CANCEL 是在，用户按住VP拖动时，VP走向下一页了，此时不触发UP，而触发CANCEL

只写这样还有个BUG，拖动时不触发UP事件，因此自动轮播被卡住，而如果在CANCEL中写入handler的代码，则会拖动时自动发送4秒延迟的消息，继续往下执行轮播。



因此==更加可选的方案==是在VP的onPageScrollStateChanged () 中，重写state的事件：

```java
  	 /**
     * 是否被拖拽
     */
    private boolean isDragging = false;

		@Override
        public void onPageScrollStateChanged(int state) {
            if(state == ViewPager.SCROLL_STATE_DRAGGING){
                //拖拽时触发
                isDragging = true;
                handler.removeCallbacksAndMessages(null);
            }else if(state == ViewPager.SCROLL_STATE_SETTLING){
                //滚动时触发
            }else if(state == ViewPager.SCROLL_STATE_IDLE&&isDragging){
                //SCROLL_STATE_IDLE停止时触发
                isDragging = false;
                handler.removeCallbacksAndMessages(null);
                handler.sendEmptyMessageDelayed(0,4000);
            }
        }
```









# 四、存储机制

Android系统中主要提供了三种方式用于简单地实现数据持久化功能：【文件存储】，【SharedPreferences】和【数据库存储】

==注意：root权限问题，需要Android7.0以下版本，否则无法打开/data目录==，可以使用 Android6.0 模拟器查看



## 一、文件存储

不对存储内容进行任何格式化处理，所有数据原封不动保存在文件中，比较适合于存储一些【简单文本数据】或【二进制数据】。



### 内部存储：

Android系统中，==每个应用只能操作自己包下文件==，默认存储到/data/data/<packagename>/files/目录下，且不能操作其他应用下的文件。因此如果用输出流直接写入一个文件名时，会报错，需要加目录。

1.openFileOutput()方法，默认在当前应用的路径下创建文件

2.getFilesDir() ，获取当前的文件目录。（同理，可以this.getCacheDir() 获取缓存文件目录：/data/data/<packagename>/cache/  ，当系统内存不够用时会被自动清理）



内部存储特点：

1. 默认只能被创建它的应用访问到
2. 当这个应用卸载后，内部存储中的文件也被删除
3. 一旦内部存储空间耗尽，手机也无法使用

| 方法名                               | 具体功能             | 参数含义                                   |
| ------------------------------------ | -------------------- | ------------------------------------------ |
| context提供的 openFileOutput()  方法 | 保存数据到指定的文件 | 1：文件名，不能包含路径。2：文件的操作模式 |
| openFileInput()  方法                | 从文件中读取数据     | 1：文件名                                  |

![image-20210317155739372](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210317155739372.png)

> 多个操作模式用“+”连接



#### 写入数据：

xml就一个EditText（id：edit），MA：

```java
public class MainActivity extends AppCompatActivity {

    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText= findViewById(R.id.edit);//获取控件
    }

    @Override
    protected void onDestroy() {    //保证活动销毁前一定会存储
        super.onDestroy();
        String inputText=editText.getText().toString();
        try {
            save(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void save(String inputText) throws IOException {
        FileOutputStream out=openFileOutput("data", Context.MODE_PRIVATE);
        //利用JAVA缓存流提高效率
        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(out));
        //写入数据
        writer.write(inputText);
    }
}
```



#### 读取数据：

xml就一个EditText（id：edit），MA：

```java
public class MainActivity extends AppCompatActivity {

    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText= findViewById(R.id.edit);//获取控件
        String inputText=load();
        editText.setText(inputText);
        editText.setSelection(inputText.length());	//默认全文被选中
        Toast.makeText(this,"加载成功",Toast.LENGTH_SHORT).show();
    }

    private String load()  {
        FileInputStream in=null;
        BufferedReader reader=null;
        StringBuilder content=new StringBuilder();
        try {
            in=openFileInput("data");
            //同理，用BufferedReader提高读取效率
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            try {
                while ((line=reader.readLine())!=null){content.append(line);}
            } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭流
            if (reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  content.toString();
    }
}
```





### 外部存储：

连接电脑，能够被识别的，就是外部存储。SD卡也是外部存储，但外部存储不仅限如此，一般用于存储音乐、视频等需要大量存储空间的。



通常不同的手机设备有不同的SD卡目录，==代码中可以直接用  Environment.getExternalStorageDirectory() 获取SD卡虚拟目录==，只有用 FileInputStream 和 FileOutputStream 执行读写操作。

==CMD中可通过命令行直接  cd sdcard/  进入SD卡存储目录==



权限：

```xml
<!-- 在SDCard中创建与删除文件权限 -->
<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"
        tools:ignore="ProtectedPermissions" />
<!-- 往SDCard写入数据权限 -->
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

注意：如果授权后仍然存在Permission Denied 的情况，把应用的 targetSdkVersion设置为29以下





#### 检查SD卡是否存在：

```java
 public void check_SD_Card(View view) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "SD卡可用", Toast.LENGTH_SHORT).show();
        } else if (state.equals(Environment.MEDIA_REMOVED)) {
            Toast.makeText(this, "SD卡被移除", Toast.LENGTH_SHORT).show();
        } else if (state.equals(Environment.MEDIA_UNMOUNTED)) {
            Toast.makeText(this, "SDk卡未挂载", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
        }
    }
```



#### 获取可用空间的大小：

```java
 public void check_SD_Space(View view){
        File exFile = Environment.getExternalStorageDirectory();
        long freeSpace = exFile.getFreeSpace();
        //转换成常用大小
        String size = Formatter.formatFileSize(this, freeSpace);  //import android.text.format.Formatter;
        Toast.makeText(this, size, Toast.LENGTH_SHORT).show();
    }
```

还可以获取总空间大小等，调用对应函数即可



#### 实例：

```java
public class MainActivity extends Activity {
    byte[] buffer = null;   //定义保存数据的数组
    private File file;      //定义存储路径
    EditText etext;
    Button btn_save, btn_open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //动态授权，这里参考ContentProvider部分
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        //省略findViewById
        file = new File(Environment.getExternalStorageDirectory(), "Text.text");  //设置存储sd卡的目录和文件

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileOutputStream fos = null;
                String text = etext.getText().toString();        //获取文本信息
                try {
                    fos = new FileOutputStream(file);     //获得文件输出流,并指定文件保存的位置
                    fos.write(text.getBytes());           //保存文本信息
                    fos.flush();                          //把缓存区的数据写入
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //关闭输出流
                    if (fos != null) {
                        try {
                            fos.close();
                            Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileInputStream fis = null;                         //定义文件输入流
                try {
                    fis = new FileInputStream(file);               //获得文件输入流
                    buffer = new byte[fis.available()];            //保存数据的数组
                    fis.read(buffer);                             //从输入流中读取数据
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (fis != null) {
                        try {
                            fis.close();                            //关闭输入流
                            String data = new String(buffer);       // 获得数组中保存的数据
                            etext.setText(data);                    //填写到编辑框中
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
```







## 二、SharedPreferences存储

SharedPreferences使用==键值对==的方式存储数据，xml文件格式存储。支持多种不同的数据类型存储，存储的类型是什么，读取出的类型就是什么。

一般用于保存一些偏好设置，如是否第一次使用APP，记住密码等。

SharedPreferences文件都是放在/data/data/<package name>/shared_prefs/目录下



获取SharedPreferences对象的方法：

| 方法名                                               | 参数含义                                                     |
| ---------------------------------------------------- | ------------------------------------------------------------ |
| Context的getSharedPreferences()                      | 1：指定SharedPreferences文件名称，如果指定的不存在则创建一个，2：指定操作模式，只有MODE_PRIVATE模式，和直接传入0的效果相同，其余模式均废弃 |
| Activity类中的getPreferences()                       | 只接收操作模式的参数，自动以当前活动的类名作为文件名         |
| PreferenceManager类中的getDefaultSharedPreferences() | 静态方法，接受一个context参数，并以当前程序的包名作为前缀来命名SharedPreferences文件 |



```xml
<Button
    android:id="@+id/sava_data"
    android:text="SP保存数据" />

<Button
    android:id="@+id/scan_data"
    android:text="SP显示数据"/>
```

```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //保存数据
        Button savedata=findViewById(R.id.sava_data);
        savedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
                editor.putString("name","张三");
                editor.putInt("age",21);
                editor.putBoolean("male",true);
                editor.apply();
            }
        });

        //读出数据
        Button scandata=findViewById(R.id.scan_data);
        scandata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
                String name=pref.getString("name","");
                int age=pref.getInt("age",0);
                boolean male=pref.getBoolean("male",false);
                Log.d("MainActivity","name is "+name);
                Log.d("MainActivity","age is "+age);
                Log.d("MainActivity","male is "+male);
            }
        });
    }
}
```





### 案例：记住密码功能

```xml
<LinearLayout
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">
    <!--省略用户名、密码输入框，登录按钮，id分别如下：
 		account  password  login-->
    <CheckBox
        android:id="@+id/rember_pass"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="18dp"
        android:text="记住密码"/>
</LinearLayout>
```

修改 LoginActivity 的代码：

```java
public class LoginActivity extends BaseActivity {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox remberpass;
    private EditText accountEdit, passwordEdit;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
 		
        //省略findView
        pref = PreferenceManager.getDefaultSharedPreferences(this); //获取到SharedPreferences对象      
        boolean isRemember = pref.getBoolean("remember_password", false);//从SharedPreferences文件中读取状态，默认为否

        if (isRemember) {   //如果状态是【true】的话，从SharedPreferences中读取账号密码，并设置在文本框中
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            remberpass.setChecked(true);
        }

      
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                  //如果用户名为my，密码123则登陆成功，判断复选框状态，如果是选中状态，则将信息存入SP中，并保存选中状态
                if (account.equals("my") && password.equals("123")) {
                    editor = pref.edit();//获取到SharedPreferences的Edit对象
                    if (remberpass.isChecked()) {   
                        editor.putBoolean("remember_password", true);
                        editor.putString("account", account);
                        editor.putString("password", password);
                    } else {    //未选中则清空SP的信息
                        editor.clear();
                    }
                    editor.apply();		//提交SP的内容
                    //界面的跳转
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);	
                    startActivity(intent);
                    finish();	
                } else {		//账号密码不正确则提示
                    Toast.makeText(LoginActivity.this, "账号或密码不正确", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
```







## 三、SQLite存储

SQLite是安卓内置数据库，是一款轻量级的关系型数据库，运算速度非常快，占资源少，适合移动设备上使用。

SQLite支持标准的SQL语法，还遵循了ACID事务，安卓提供了SQLiteOpenHelper抽象类，让我们非常简单地对数据库创建和升级。



数据库文件放在/data/data/<packagename>/databases/目录下

继承SQLiteOpenHelper必须复写的方法：

| 方法名                      | 具体功能                                                     |
| --------------------------- | ------------------------------------------------------------ |
| onCreate()  和  onUpgrade() | 必须重写的两个方法，在这两个方法中实现创建、升级数据库的逻辑 |
| 构造方法                    | 四个参数：1：Context，对数据库操作的对象。2：数据库名，创建数据库时指定的名称。3：允许查询数据的时候返回一个自定义的Cursor，一般传入null。4：当前数据库版本号，作用于判断数据库升级 |

其他相关方法

| 方法名                                           | 描述                                                         |
| ------------------------------------------------ | ------------------------------------------------------------ |
| getReadableDatabase()  和  getWritableDatabase() | 创建或打开现有的数据库，并返回一个可以对数据库进行读写操作的对象。不同的是，当数据库不可写入的时候（如磁盘已满），前者返回的对象将以只读的方式打开数据库，后者出现异常。 |
| execSQL（）                                      | 执行SQL语句                                                  |



### 3.1 SQLite的数据类型：

| 英文          | 对应类型   |
| ------------- | ---------- |
| integer       | 整形变量   |
| text          | 文本       |
| blob          | 二进制类型 |
| real          | 浮点型     |
|               |            |
| primary key   | 主键       |
| autoincrement | 自动增长   |

SQLite最大特点: **你可以各种数据类型的数据保存到任何字段中**，比如你可以在Integer类型的字段中存放字符串





### 3.2 实例演示：

先创建一个数据库的管理类，继承SQLiteOpenHelper，实现相应的重写方法：

```java
public class MyDataBaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_BOOK ="create table book("
            +"id integer primary key autoincrement,"
            +"author text,"
            +"price real,"
            +"pages integer,"
            +"name text)";

    private Context mContext;

    //构造函数
    public MyDataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {		
        super(context, name, factory, version);
        mContext=context;
    }

    //第一次被创建时回调
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        Toast.makeText(mContext,"Create succeeded",Toast.LENGTH_LONG).show();
        //创建表的字段
    }

    //升级时回调
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
```

然后MA布局中添加一个按钮用于创建数据库，在MA中获取控件并创建数据库：

```java
public class MainActivity extends AppCompatActivity {
    private  MyDataBaseHelper dbHelper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper=new MyDataBaseHelper(this,"BookStore.db",null,1);//构造数据库类的对象，指明数据库名，版本号
        
        Button createDatabase=findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();     //创建数据库，并调用onCreate方法，如果表存在则异常
            }
        });
    }
}
```









### 3.3 升级数据库的作用

onUpgrade() 方法是对数据库升级的。如果我们在之前的项目中想再添加一个表，就需要用到该功能。

> 原因：此时 BookStore.db 数据库已经存在，不管怎么点击 Create database 按钮，MyDatabaseHelper 类中的 onCreate 方法都不会再执行



在onUpgrade（）方法中写入如下语句即可：

```java
 public static final String CREATE_PERSON ="create table person("
            +"id integer primary key autoincrement,"
            +"name text,"
            +"age integer";

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CREATE_PERSON);
	}
```

某个按钮触发操作，执行  onUpgrade()：

```java
dbHelper=new MyDataBaseHelper(this,"BookStore.db",null,2);//创建对象时第四个参数（数据库版本号）传入一个比上一次大的，就可以了
```





### 3.4 对表操作CRUD

安卓提供了一系列辅助性方法，借助 创建数据库返回的 db 对象，可以对数据库进行CRUD操作

| 方法     | 参数                                                         |
| -------- | ------------------------------------------------------------ |
| insert() | 3个参数：1：表名。2：用于未指定添加数据的情况下给某些可以为空的列添加null，一般用不到，直接写入null即可。3：ContentValues对象，它提供了一系列  put() 方法重载给ContentValues添加数据，值需要传入每个列名以及响应数据即可。 |
| updata() | 4个参数：1：表名。2是ContentValues对象。3、4：用于约束更新某一行或某几行，不指定该默认所有行 |
| delete() | 3个参数：1：表名。2、3：用于约束，不指定默认所有行           |
| query()  | 7个参数：1：表名。2：指定查询哪几列，不指定为所有。3、4：用于约束查询某一行或某几行，不指定为所有行。5：用于指定需要group by的列，不指定表示不进行该操作。6：用于对group by后的数据进一步过滤，不指定为不过滤。7：用于指定查询结果的排列方式，不指定为默认排序方式。这个方法会返回一个Cursor对象 |

> ContentValues 是一个缓存对象，保存了临时想要改变的属性等
>

| Cursor方法       | 具体功能                       |
| ---------------- | ------------------------------ |
| getColumnIndex() | 获取某一列在表中对应的位置索引 |
| moveToFirst()    | 将指针定位到第一行             |



#### 实例演示：

```java
//数据库建表部分
public class MyDataBaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_BOOK ="create table Book("
            +"id integer primary key autoincrement,"
            +"author text,"
            +"price real,"
            +"pages integer,"
            +"name text)";

    public static final String CREATE_GATEGORY="create table Category("
            +"id integer primary key autoincrement,"
            +"author text,"
            +"price real,"
            +"pages integer,"
            +"name text)";

    private Context mContext;

    public MyDataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_GATEGORY);
        Toast.makeText(mContext,"Create succeeded",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
        onCreate(db);
    }
}
```

```xml
<Button
    android:id="@+id/create_database"
    android:text="数据库建表"/>
<Button
    android:id="@+id/add_data"
    android:text="添加数据"/>
<Button
    android:id="@+id/updata_data"
    android:text="修改数据"/>
<Button
    android:id="@+id/delete_data"
    android:text="删除数据"/>
<Button
    android:id="@+id/query_data"
    android:text="查询数据"/>
```

数据库增删改查主体部分：

```java
public class MainActivity extends AppCompatActivity {
    private  MyDataBaseHelper dbHelper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper=new MyDataBaseHelper(this,"BookStore.db",null,2);//构造数据库类的对象，指明数据库名，版本号
        
        //省略findView
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();     //创建数据库
            }
        });

        //添加数据
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //使用官方API插入数据
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                //添加第一条数据
                values.put("author","张三");
                values.put("price",36.2);
                values.put("pages",256);
                values.put("name","张三的第一本书");
                db.insert("Book",null,values);
                //插入数据完毕，清楚缓存区的内容
                values.clear();
                //插入第二条数据
                values.put("author","李四");
                values.put("price",32.10);
                values.put("pages",278);
                values.put("name","李四的第一本书");
                db.insert("Book",null,values);

                //使用原生sql语句插入数据
                db.execSQL("insert into Book (name,author,pages,price) 
                           values(?,?,?,?)",new String[]{"王五的第一本书","王五","397","28.0"});
            }
        });

        //修改数据
        Button updataData=findViewById(R.id.updata_data);
        updataData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //使用官方API修改数据
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("price",38.2);
                db.update("Book",values,"name=?",new  String[]{"张三的第一本书"});//第四个参数与第三个参数形成约束条件

                //sql
                db.execSQL("update Book set price=? where name=?",new String[]{"34.0","王五的第一本书"});
            }
        });

        //删除数据
        Button deleteData=findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Book","pages > ?",new String[]{"270"});

                //sql
                db.execSQL("delete from Book where name=?",new String[]{"张三的第一本书"});
            }
        });

        //查询数据
        Button queryData=findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                if (cursor.moveToFirst()){
                    do {
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        double price=cursor.getDouble(cursor.getColumnIndex("price"));
                        int pages=cursor.getInt(cursor.getColumnIndex("pages"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        Log.d("MA", "书名："+name+"作者："+author+ "价格："+price+ "页数："+pages);
                    }while (cursor.moveToNext());
                }
                cursor.close();

                //查询的SQL语句：
                //Cursor cursor = db.rawQuery("select * from Book", null);
            }
        });
    }
}
```

### 3.5 事务

![image-20210320163328128](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210320163328128.png)







## 附加：查看创建的表

将sdk文件夹下的platform-tools目录，设置为path环境变量。然后用**CMD输入adb shell**，如果是普通管理员显示$，超级管理员显示#。

超级管理员模式下，**cd命令进入到/data/data/包名/databases目录下**。此时 ls命令 列出所有文件，.db文件是数据库文件，.db-journal文件是为了让数据库支持事务产生的临时日志文件，通常为0kb，**键入sqlite3 数据库名**即可**打开数据库**，对表进行管理。android_metadata表是每个数据库中都会自动生成的。



| cmd命令        | 具体功能             |
| -------------- | -------------------- |
| .table         | 查看数据库中有哪些表 |
| .schema        | 查看建表语句         |
| .exit 和 .quit | 退出数据库编辑       |



其他常用adb命令查看 【Android adb 讲解.md】文件



## 四、LitePal_开源库

LitePal是一款开源的数据库框架，采用**对象关系映射模式**（面向对象的思维操作数据库），将我们平时开发最常用到的一些数据库功能进行了封装，使得不用编写一行SQL语句就可以完成增删改查。Litepal项目主页：https://github.com/LitePalFramwork/LitePal



### 1. 准备工作



#### 1.声明开源库的引用

在build.gradle中的dependencis添加如下代码：

```
implementation 'org.litepal.android:core:1.4.1'
```

#### 2.修改Manifest的name属性

在Manifest中，将litepal 的生命周期 指定为整个APP。

```xml
<application
    android:name="org.litepal.LitePalApplication"
    ………………
</application>
```



#### 3.创建实体类

新建图书类Book，写入get、set、toString方法，==继承DataSupport类==

```java
public class Book extends DataSupport{
    private int id;
    private String anthor;
    private double price;
    private int pages;
    private String name;
    …………
   }
```



#### 4.关系映射

/res/main下新建assets文件夹，新建litepal.xml文件

```xml
<?xml version="1.0" encoding="utf-8" ?>
<litepal>
    <dbname value="BookStore"></dbname>				//指定数据库名
    <version value="1"></version>			//指定版本号
    <list>		//指定映射模型
    </list>
</litepal>
```



### 2. 创建数据库：



```java
   Button createDatabase =findViewById(R.id.create_database);
   createDatabase.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
            LitePal.getDatabase();
       }
   });
```

用adb shell查看，可以发现数据库中有三张表，android_metadata 不用管，table_schema表是litePal内部使用的。**book表根据Book类以及类中的字段已经自动生成**。



==优势：LitaPal升级数据库完全不用思考任何逻辑，只要改你想改的内容，然后版本号加1即可。==

> 比如新加一个属性，然后生成get、set方法，版本号+1。需要新建一个类，直接写完类的属性，然后添加到映射模型的列表中，values的值改为2，就完成了。





### 3. 增删改查：

==注意：先要让实体类继承DataSupport类，DataSupport类提供的 save()  方法==

| 方法 | 方法名                  | 具体实现                                                     |
| ---- | ----------------------- | ------------------------------------------------------------ |
| 增   | 对象名.save()           | 直接创建出模型类的实例对象，再将所有数据设置好，最后调用save()方法即可。 |
| 删   | DataSupport.deleteAll() | 调用方法，设置约束条件。【不指定约束条件为删除所有数据】     |
| 改   | 对象名.updataAll()      | 新建一个实例对象，用set设置需要修改的属性，再用 updataAll() 方法设置更新条件【不设置为更新所有的数据】。注意：想设置为默认值时，不能用set方法，使用setToDefault() 方法即可。 |
| 查   | DataSupprot.findAll()   | 接收参数是一个类，返回的是一个List集合，遍历集合即可取出我们想要的值 |



查询方法其他API：

除了第一行返回值是个对象外，其余的返回值均是List集合：【还可以对这五个方法进行任意连缀，完成复杂的查询操作】

| 方法                                                  | 介绍                                      |
| ----------------------------------------------------- | ----------------------------------------- |
| DataSupprot.findFirst()  /     DataSupprot.findLast() | 获取第一条数据和最后一条数据              |
| DataSupprot.select("name","author).findAll()          | 只查询特定的几列数据                      |
| DataSupprot.where("pages>?","400).findAll()           | 条件查询：页数大于400的                   |
| DataSupprot.order("price desc").findAll()             | 结果按降序排序查询，asc或不写表升序排序   |
| DataSupprot.limt(3).findAll()                         | 只查表中前三条数据                        |
| DataSupprot.limt(3).offset(1).findAll()               | offset指定偏移量，这样表示查2、3、4条数据 |

除此之外，==DataSupprot.findBySQL()  方法还支持用SQL语句原生查询==。第一个参数表示SQL语句，后面的参数表示 ?【即占位符】的值。此方法返回的是Cursor对象，需要用cursor对象一个个取出数据。



#### 代码演示：

布局界面四个按钮，MA：

```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //省略findView
        //建表
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.getDatabase();
            }
        });

        //添加
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book=new Book("张三",35.2,400,"张三的第一本书");
                book.save();
            }
        });

        //修改
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book=new Book();
                book.setPrice(28);
                book.updateAll("id=?","1");
            }
        });

        //删除
        Button deleteButton=findViewById(R.id.delete_data);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(Book.class,"id=?","3");
            }
        });

        //查询
        Button queryButton=findViewById(R.id.query_data);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Book> books = DataSupport.findAll(Book.class);
                for (Book book : books) {
                    Log.d("MA", "书名："+book.getName()+"作者："+book.getAnthor()+"页数："+book.getPages()+"价格："+book.getPrice());
                }
            }
        });
    }
}
```







# 五、四大组件



## 一、Activity

Activity是一种可以包含用户界面的组件，主要用于和用户进行交互。一个应用程序可以包含0个或多个活动。

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210112181829901.png" alt="image-20210112181829901" style="zoom:67%;" />





### 1.1 创建Activity：

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20201202113418069.png" alt="image-20201202113418069" style="zoom:67%;" />

![image-20201202113952294](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20201202113952294.png)



### 1.2 Manifest中注册Activity

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="activitytest.example.com.activitytest">
    
    <application		//活动的注册声明要放在此标签内
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">

        <activity android:name=".FirstActivity" android:label="This is FirstActivity">
        //android:name来指定具体注册哪一个活动，因为mainifest标签中已经申明了包名，这里直接用“.”缩写即可
        //android:label制定活动中标题栏的内容，给主Activity指定label不仅会称为标题栏中的内容，还会成为启动器（Launcher）中应用程序显示的名称
            <intent-filter>
                <action android:name="android.intent.action.MAIN"></action>
                <category android:name="android.intent.category.LAUNCHER"></category>
            </intent-filter>	//注册成为主Activity
        </activity>
        
      //activity标签对活动进行注册，AS帮助我们自动完成

    </application>
</manifest>    
```

> 注意：如果没有声明任何一个活动作为主Activity，这个程序仍然是可以正常安装的，只是你无法在启动器中看到或打开这个程序。这种程序一般都是作为第三方服务供其他应用在内部进行调用的，如支付宝快捷支付服务

> 注意：如果有多个程序入口，则从上到下分别加载多个启动图标，作用于不同的入口标志







### 1.3 销毁活动

方法1：按一下Back键

方法2：代码中使用  finish()  方法

```java
Button button2= findViewById(R.id.button_2);
button2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        finish();		//销毁活动
    }
});
```



### 1.4刷新活动

```java
 onCreate(null);		//类似Windows的F5
```







### 1.5 使用Intent在活动之间跳转

Intent是安卓程序中四大组件之间进行交互的一种重要方式，指明当前组件想要执行的动作，并在不同组件之间传递数据。

Intent一般可以被用于启动活动、启动服务、发送广播等场景

Intent大致分为两种：显式和隐式

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210114124823486.png" alt="image-20210114124823486" style="zoom: 80%;" />

#### 使用Intent返回Home：

```java
Intent it = new Intent();
it.setAction(Intent.ACTION_MAIN);
it.addCategory(Intent.CATEGORY_HOME);
startActivity(it);
```



<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210114125132157.png" alt="image-20210114125132157" style="zoom:50%;" />

#### 显式Intent：

```java
button3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(FirstActivity.this,SecondActivity.class);//第一个参数是启动活动的上下文，第二个参数指定想要启动的目标活动
        startActivity(intent);
    }
});
```



#### 隐式Intent：

不明确指定想要启动哪一个活动，制定了一系列更为抽象的action和category等信息，交由系统分析并找出合适的活动去启动。

==一般配和过滤器使用，也就是Manifest中的<intent-filter>==

```java
button4.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent("com.example.activitytest.ACTION_START");	//传入一个字符串
        intent.addCategory("com.example.activitytest.MY_CATEGORY");	//添加的新的category,可以有多个
        startActivity(intent);
    }
});
```

Manifest修改目标活动的属性如下：

```xml
<activity android:name=".SecondActivity">
    <intent-filter>
        <action android:name="com.example.activitytest.ACTION_START"></action>	//对应上方的字符串
        <category android:name="android.intent.category.DEFAULT"></category>	//android.intent.category.DEFAULT 是一种默认的 category ，调用 startActivity 方法的时候会自动将这个category添加到intent中，也可以不写
        <category android:name="com.example.activitytest.MY_CATEGORY"></category>	//添加对应的category
    </intent-filter>
</activity>
```





#### 隐式Intent的好处：

不仅可以启动自己程序内的活动，还可以启动其他程序活动，使得android多个应用程序之间功能共享成为了可能。

> 比如:在应用程序中展示一个网页：

```java
button5.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(Intent.ACTION_VIEW);	//Intent.ACTION_VIEW是android系统内置动作
        intent.setData(Uri.parse("http://www.baidu.com"));	//setData（）方法会接收一个 URI 对象，主要用于指定当前Intent正在操作的数据，这些数据通常是以字符串形式传入Uri.parse（）方法进行解析
        startActivity(intent);
    }
});
```



除了http协议，还可以指定很多其他协议，例如：**geo表示显示地理位置，tel表示拨打电话**。

下面的代码就可以在程序中调用拨号系统界面：

```java
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent= new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:10086"));		//触发监听器后直接进入拨号界面，号码为10086
        startActivity(intent);
    }
});
```



#### 《Intent-filter》标签中的《data》标签

```xml
<intent-filter>
    <data
        android:scheme //用于指定数据的协议部分，如http
        android:host	//用于指定数据的主机名部分，如www.baidu.com
        android:port	//用于指定数据的端口部分，一般紧随主机名之后
        android:path	//用于指定主机名和端口之后的部分，如一段网址中跟在域名之后的内容
        android:mineType	//用于指定可以处理的数据类型，允许使用通配符的方式指定
    />
    ……
</intent-filter>
```

![image-20201203220127258](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20201203220127258.png)

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20201203220620477.png" alt="image-20201203220620477" style="zoom:67%;" />



###  1.6 Intent传递数据

**用putExtra（）将信息存入Intent中，在从下一个Acitivity的Intent中取出即可**



在第一个Activity中设置按钮触发事件，并传递字符串

```java
Button button6= findViewById(R.id.button_6);
button6.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String data="Hello SecondActivity";	
        Intent intent=new Intent(FirstActivity.this,SecondActivity.class);
        intent.putExtra("extra_data",data);		//使用该方式传递数据，以键值对方式存储
        startActivity(intent);
    }
});
```

在第二个Activity中接收字符串，提示出内容

```java
Button button1 = findViewById(R.id.button_2_1);
button1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=getIntent(); //获取Intent
        String data=intent.getStringExtra("extra_data");	//读取字符串，根据值的类型，getXxxExtra做对应的变化
        Toast.makeText(SecondActivity.this,data,Toast.LENGTH_SHORT).show();
    }
});
```



##### 传递数组：

```java
intent.putStringArray("StringArray", new String[]{"呵呵","哈哈"});	//String可以换成其他类型的数组,int float……

String[] str = intent.getStringArray("StringArray");
```



##### 传递对象：

person 类需要实现 Serializable 接口

```java
intent.putExtra("person",person);

person = (Person) intent.getSerializableExtra("person");
```



##### 传递集合：

1）List<基本数据类型 或 String>

```java
//传
intent.putStringArrayListExtra("name", value)
intent.putIntegerArrayListExtra("name", value)
//取
intent.getStringArrayListExtra("name")
intent.getIntegerArrayListExtra("name")
```

2) List < Object >	

注意：这里的对象所属的类也必须实现Serializable接口

```java
//传
bundle.putSerializable("persons",(Serializable)persons);
intent.putExtras(bundle);
//取
Intent intent=getIntent();
ArrayList<Person> persons = (ArrayList<Person>) intent.getSerializableExtra("persons");
```

3）Map 集合

```java
//传        
final Map<String,Integer> map=new HashMap();
map.put("张三",18);
map.put("李四",19);
button.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
          Intent intent=new Intent(MainActivity.this,Main2Activity.class);
          Bundle bundle=new Bundle();
          bundle.putSerializable("map", (Serializable) map);
          intent.putExtras(bundle);
          startActivity(intent);
         }
     });   

//取
Bundle bundle=getIntent().getExtras();
Map map= (Map) bundle.get("map");
//获取到Map集合的键值对的set数组
Set<Map.Entry<String ,Integer>> entryset=map.entrySet();
//遍历输出内容
for (Map.Entry<String, Integer> entry : entryset) { 
    String key = entry.getKey();
    Integer value = entry.getValue();
    System.out.println(key+ value);
}     
```





##### 传递Bitmap：

```java
Bitmap bitmap = null;
Intent intent = new Intent();
Bundle bundle = new Bundle();
bundle.putParcelable("bitmap", bitmap);
intent.putExtra("bundle", bundle);
```



### 1.7 Intent返回数据

返回上一个活动有两种办法：Back键 或者 设置一个按钮用于返回

首先，在第一个活动中，将活动的启动方式设置为：startActivityForResult（）

```java
Button button7=   findViewById(R.id.button_7);
button7.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(FirstActivity.this,SecondActivity.class);
        startActivityForResult(intent,1);	//设置启动方式，第二个参数是请求码，用于之后在回调中判断数据来源
    }
});
```

活动二中，设置返回按钮的监听器，传递返回数据：setResult（）

```java
Button button2=   findViewById(R.id.button_2_2);
button2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("return_data","Hello first");	//将返回的数据放入intent中
        setResult(RESULT_OK,intent);  //传递返回数据，第一个参数用于向上一个活动返回处理结果（RESULT_CANCLE 或 RESULT_OK，CANCEL会取消存放的放回数据）
        finish();  //结束当前方法
    }
});

	//如果是Back返回，则设置如下代码
	@Override
    public void onBackPressed() {
        Intent intent=new Intent();
        intent.putExtra("return_data","你点了Back返回");
        setResult(RESULT_OK,intent);
        finish();
    }
```



最后，在**活动一**中 **重写onActivityResult** 方法

```java
//三个参数：请求码;返回数据时的处理结果;携带返回数据的intent
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {	
    switch (requestCode){		//根据请求码决定后续操作，判断数据从哪个活动返回的
        case 1:
            if (resultCode == RESULT_OK){
                String returnedDate=data.getStringExtra("return_data");
                Toast.makeText(FirstActivity.this,returnedDate,Toast.LENGTH_SHORT).show();
                Log.d("FirstActivity", returnedDate);
            }
            break;
        default:
            break;
    }
}
```







### 1.8 活动的生命周期

活动可以层叠，每当启动一个新的活动， 就会覆盖在原活动上，点击back键会销毁最上面的活动，下面的一个活动就会重新显示。

安卓是使用==任务（Task）==来管理活动的，一个任务就是一组放在栈里的活动的集合，这个栈也被称为**返回栈**（Back Stack）



活动的状态：

| 状态     | 具体描述                                                     |
| -------- | ------------------------------------------------------------ |
| 运行状态 | 活动位于返回栈栈顶时，系统不愿意回收该活动                   |
| 暂停状态 | 不再处于栈顶位置，但仍然可见。处于该状态的活动依然是存活的，系统也不愿意回收该活动，只有内存极低的情况下，系统才会考虑回收 |
| 停止状态 | 不处于栈顶位置，也完全不可见。系统会为这种活动保存相应的状态和成员变量，但并不可靠，其他地方需要内存时，处于停止状态的活动可能被系统回收 |
| 销毁状态 | 从返回栈中移除后。系统最倾向于回收这种状态的活动             |



**活动的回调方法**：（覆盖了活动生命周期的每一个环节）

| 方法名    | 具体功能                                                     |
| --------- | ------------------------------------------------------------ |
| onCreate  | 在活动第一次被创建时调用。在这个方法中完成活动的初始化操作，比如：加载布局，绑定事件 |
| onStart   | 活动由不可见变为可见时调用                                   |
| onResume  | 活动准备好和用户进行交互时调用，此时活动一定处于栈顶位置，并且处于运行状态 |
| onPause   | 系统准备启动或恢复另一个活动时调用。通常在这个方法中将消耗CPU的资源释放，保存一些关键数据，这个方法的执行速度一定要快，否则影响栈顶活动的使用 |
| onStop    | 在活动完全不可见的时候调用。如果启动的新活动是对话框式的活动，该方法会执行，onPause不执行 |
| onDestroy | 在活动被销毁之前调用，之后活动变为销毁状态                   |
| onRestart | 在活动由停止状态变为运行状态之前调用，也就是活动被重新启动了 |

> 除了onRestart方法，其余方法两两对应



**三种生存期：**

| 名称       | 回调方法                                                     |
| ---------- | ------------------------------------------------------------ |
| 完整生存期 | onCreate 和 onDestroy 方法之间经历的。一般情况，会在onCreate完成初始化操作，onDestroy完成释放内存操作 |
| 可见生存期 | onStart 和 onStop 方法之间经历的。在该期间，活动对用户总是可见的，onStart中对资源加载，onStop中释放资源 |
| 前台生存期 | onResume 和 onPause 方法之间经历的。该期间，活动总处于运行状态的，此时的活动可以和用户进行交互，平时看到和接触的最多的就是该状态下的活动。 |

![image-20201214204105606](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20201214204105606.png)

#### ==活动被回收了怎么办？==

A活动地基础上启动了B活动，由于内存不足A活动被回收，此时用户按下Back键返回A，A仍然能够正常显示，但是不会执行  onRestart（）方法，而是执行活动A的onCreate（）方法，**A被重新创建了一次**。

为了**防止A中的临时数据随着被回收而消失**，Activity中提供了一个  onSavaInstanceState（）回调方法，这个方法可以保证在活动被回收之前一定被调用。它会携带一个 Bundle 类型的参数，Bundle 提供了一系列的方法用于保存数据，比如：putString  保存字符串

例如，在需要保存数据的活动中添加如下代码：

```java
@Override
protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    String temp="Something";
    outState.putString("data_key",temp);
}
```

而onCreate（）方法中也有Bundle参数，一般情况下都为null，如果有上述保存的临时数据，我们只需要通过相应的取值方法取出数据即可

onCreate（）方法中加入如下代码：

```java
if (savedInstanceState!=null){
    String temp=savedInstanceState.getString("data_key");
    Log.d(TAG,temp);
}
```





#### ==多窗口模式：==

多窗口模式并不会改变活动原有的生命周期，只是会将最近交互的那个活动置为运行状态，另一个可见的活动置为暂停状态。

关闭多窗口模式：

targetSdkVersion高于24的时候，只需要在manifest中的application标签下，加入如下代码即可：

```
android:resizeableActivity="false"
```

24以下，可以在activity标签中仅限于竖屏模式，达到相同效果

```
android:screenOrientation="portrait"
```





###  1.9活动的启动模式

活动有4种启动模式：【standard】、【singleTop】、【singleTask】、【singleInstance】

在Manifest中给<activity>标签指定android:launchMode属性选择启动模式



#### standard模式：

默认的启动模式。每次启动都会在返回栈中入栈，并处于栈顶位置，每次启动都会创建该活动的一个新的实例。

例如我们在 FirstActivity 中加入按钮，监听器设置为跳转到本活动，则每次点一次按钮，就有一个新的 FirstAcitivity 覆盖，按back键返回上一个FirstAcitivity

![image-20201214220440897](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20201214220440897.png)



#### SingleTop模式：

当活动的启动模式为该模式时，如果返回栈的栈顶已经是该活动，则认为可以直接使用它，不用再创建新的活动实例。

但如果该活动未处于栈顶，仍然会创建新的实例。

![image-20201214221525343](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20201214221525343.png)



#### singleTask模式：

让活动在整个应用程序中只存在一个实例。

![image-20201215170322110](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20201215170322110.png)



#### singleInstance模式：

启用新的返回栈来管理这个活动，可以实现其他程序和本程序共享一个活动实例。

```java
Log.d("ThirdActivity","Task ID is"+getTaskId());	//通过 getTaskId( ) 方法，获取当前返回栈ID
```



![image-20201215191259714](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20201215191259714.png)



### 1.10 如何知道当前活动是哪个？

新建一个JAVA类，类名 BaseActvity ，继承自 AppCompatActivity ，加入如下代码：

```java
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity",getClass().getSimpleName());	//获取当前活动类名
    }
}
```

然后让每个 Acitivity 继承 BaseActvity , 这样就可以在日志台观察到是哪个 Actvity 了。



### 1.11 随时随地退出程序

编写一个类：ActivityCollector，管理所有的活动

```java
public class ActivityCollector {
    public static List<Activity> activities=new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActvity(Activity activity){
        activities.remove(activity);

    }

    public static void finishAll(){
        for (Activity activity : activities) {
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
        activities.clear();
    }
}
```

然后再BaseActivity中添加如下代码：

```java
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity",getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActvity(this);
    }
}
```

不管想在什么地方 **退出程序**，直接调用：

```
ActivityCollector.finishAll( )
```



### 1.12 启动活动的最佳写法

项目对接时，比如你写的 FirstActivity 想要向 SecondActivity 传递数据，你并不清楚 SecondActivity 中的代码。

此时只需要在 SecondActivity 中加入如下的方法即可：

```java
public static void actionStart(Context context,String data1,String data2){
    Intent intent = new Intent(context,SecondActivity.class);
    intent.putExtra("param1",data1);
    intent.putExtra("param2",data2);
    context.startActivity(intent);
}
```

然后再 FirstActivity 这样写：

```java
button9.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        SecondActivity.actionStart(FirstActivity.this,"data1","data2");
    }
});
```

需要数据的时候直接使用即可：

```java
button4.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=getIntent();
        String param1 = intent.getStringExtra("param1");	
        String param2 = intent.getStringExtra("param2");
        Toast.makeText(SecondActivity.this,param1,Toast.LENGTH_SHORT).show();		//显示data1
        Toast.makeText(SecondActivity.this,param2,Toast.LENGTH_SHORT).show();		//显示data2
    }
});
```





### 1.13 系统提供的常见活动

```java
//1.拨打电话
// 给移动客服10086拨打电话
Uri uri = Uri.parse("tel:10086");
Intent intent = new Intent(Intent.ACTION_DIAL, uri);
startActivity(intent);

//2.发送短信
// 给10086发送内容为“Hello”的短信
Uri uri = Uri.parse("smsto:10086");
Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
intent.putExtra("sms_body", "Hello");
startActivity(intent);

//3.发送彩信（相当于发送带附件的短信）
Intent intent = new Intent(Intent.ACTION_SEND);
intent.putExtra("sms_body", "Hello");
Uri uri = Uri.parse("content://media/external/images/media/23");
intent.putExtra(Intent.EXTRA_STREAM, uri);
intent.setType("image/png");
startActivity(intent);

//4.打开浏览器:
// 打开Google主页
Uri uri = Uri.parse("http://www.baidu.com");
Intent intent  = new Intent(Intent.ACTION_VIEW, uri);
startActivity(intent);

//5.发送电子邮件:(阉割了Google服务的没戏!!!!)
// 给someone@domain.com发邮件
Uri uri = Uri.parse("mailto:someone@domain.com");
Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
startActivity(intent);
// 给someone@domain.com发邮件发送内容为“Hello”的邮件
Intent intent = new Intent(Intent.ACTION_SEND);
intent.putExtra(Intent.EXTRA_EMAIL, "someone@domain.com");
intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
intent.putExtra(Intent.EXTRA_TEXT, "Hello");
intent.setType("text/plain");
startActivity(intent);

// 给多人发邮件
Intent intent=new Intent(Intent.ACTION_SEND);
String[] tos = {"1@abc.com", "2@abc.com"}; // 收件人
String[] ccs = {"3@abc.com", "4@abc.com"}; // 抄送
String[] bccs = {"5@abc.com", "6@abc.com"}; // 密送
intent.putExtra(Intent.EXTRA_EMAIL, tos);
intent.putExtra(Intent.EXTRA_CC, ccs);
intent.putExtra(Intent.EXTRA_BCC, bccs);
intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
intent.putExtra(Intent.EXTRA_TEXT, "Hello");
intent.setType("message/rfc822");
startActivity(intent);

//6.显示地图:
// 打开Google地图中国北京位置（北纬39.9，东经116.3）
Uri uri = Uri.parse("geo:39.9,116.3");
Intent intent = new Intent(Intent.ACTION_VIEW, uri);
startActivity(intent);

//7.多媒体播放:
Intent intent = new Intent(Intent.ACTION_VIEW);
Uri uri = Uri.parse("file:///sdcard/foo.mp3");
intent.setDataAndType(uri, "audio/mp3");
startActivity(intent);

//8.获取SD卡下所有音频文件,然后播放第一首=-= 
Uri uri = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "1");
Intent intent = new Intent(Intent.ACTION_VIEW, uri);
startActivity(intent);

//9.打开摄像头拍照:
// 打开拍照程序
Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
startActivityForResult(intent, 0);
// 取出照片数据
Bundle extras = intent.getExtras(); 
Bitmap bitmap = (Bitmap) extras.get("data");

//另一种:
//调用系统相机应用程序，并存储拍下来的照片
Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
time = Calendar.getInstance().getTimeInMillis();
intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment
.getExternalStorageDirectory().getAbsolutePath()+"/tucue", time + ".jpg")));
startActivityForResult(intent, ACTIVITY_GET_CAMERA_IMAGE);

//10.获取并剪切图片
// 获取并剪切图片
Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
intent.setType("image/*");
intent.putExtra("crop", "true"); // 开启剪切
intent.putExtra("aspectX", 1); // 剪切的宽高比为1：2
intent.putExtra("aspectY", 2);
intent.putExtra("outputX", 20); // 保存图片的宽和高
intent.putExtra("outputY", 40); 
intent.putExtra("output", Uri.fromFile(new File("/mnt/sdcard/temp"))); // 保存路径
intent.putExtra("outputFormat", "JPEG");// 返回格式
startActivityForResult(intent, 0);

// 剪切特定图片
Intent intent = new Intent("com.android.camera.action.CROP"); 
intent.setClassName("com.android.camera", "com.android.camera.CropImage"); 
intent.setData(Uri.fromFile(new File("/mnt/sdcard/temp"))); 
intent.putExtra("outputX", 1); // 剪切的宽高比为1：2
intent.putExtra("outputY", 2);
intent.putExtra("aspectX", 20); // 保存图片的宽和高
intent.putExtra("aspectY", 40);
intent.putExtra("scale", true);
intent.putExtra("noFaceDetection", true); 
intent.putExtra("output", Uri.parse("file:///mnt/sdcard/temp")); 
startActivityForResult(intent, 0);

//11.打开Google Market 
// 打开Google Market直接进入该程序的详细页面
Uri uri = Uri.parse("market://details?id=" + "com.demo.app");
Intent intent = new Intent(Intent.ACTION_VIEW, uri);
startActivity(intent);

//12.进入手机设置界面:
// 进入无线网络设置界面（其它可以举一反三）  
Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);  
startActivityForResult(intent, 0);

//13.安装apk:
Uri installUri = Uri.fromParts("package", "xxx", null);   
returnIt = new Intent(Intent.ACTION_PACKAGE_ADDED, installUri);

//14.卸载apk:
Uri uri = Uri.fromParts("package", strPackageName, null);      
Intent it = new Intent(Intent.ACTION_DELETE, uri);      
startActivity(it); 

//15.发送附件:
Intent it = new Intent(Intent.ACTION_SEND);      
it.putExtra(Intent.EXTRA_SUBJECT, "The email subject text");      
it.putExtra(Intent.EXTRA_STREAM, "file:///sdcard/eoe.mp3");      
sendIntent.setType("audio/mp3");      
startActivity(Intent.createChooser(it, "Choose Email Client"));

//16.进入联系人页面:
Intent intent = new Intent();
intent.setAction(Intent.ACTION_VIEW);
intent.setData(People.CONTENT_URI);
startActivity(intent);

//17.查看指定联系人:
Uri personUri = ContentUris.withAppendedId(People.CONTENT_URI, info.id);//info.id联系人ID
Intent intent = new Intent();
intent.setAction(Intent.ACTION_VIEW);
intent.setData(personUri);
startActivity(intent);
```



### 1.14 保存临时数据

通过这三个重写的方法的参数Bundle保存：

```java
onCreate(Bundle savedInstanceState);
onSaveInstanceState(Bundle outState);
onRestoreInstanceState(Bundle savedInstanceState);
```

你只重写上面的任一方法，往这个Bundle中写入数据，比如：

```java
savedInstanceState.putInt("num",1);
```

 通过形参就可以拿出里面存储的数据，不过拿之前要判断下是否为null哦！

```java
if(savedInstanceState != null){
	savedInstanceState.getInt("num");
}
```





## 二、Fragment

==minSDK>11==

Fragment 和 Activity 就像水塘的水和鱼的关系，Fragment受到所在的 Activity 的生命周期影响，但独立于Activity 存在。

Fragment不是四大组件之一，但重要性等同于四大组件，Fragment 现今的使用场景越来越广，大部分Activity能做的工作都可以用 Fragment 替代。

V4包下的Fragment能让碎片在所有Android 系统版本中保持功能的一致性，4.2系统之后能使用Fragment中嵌套Fragment

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210113133149398.png" alt="image-20210113133149398" style="zoom:50%;" />



![image-20210317153642379](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210317153642379.png)

![image-20210317153652281](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210317153652281.png)



### 2.1 最简单的应用

比如平板中，如果像手机一样一个按钮占一行就会造成资源的浪费，因此可以开创左右两块不同的Fragment，共享一个活动界面。

左边界面有一个按钮，点击按钮，切换右侧不同的Fragment。

【熟练以后可以直接用AS创建，并配置相应布局】



新建  left_fragment.xml  布局文件

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical">

    <Button
        android:id="@+id/button"
        android:text="Button"></Button>
</LinearLayout>
```

创建一个类LeftFragment继承Fragment

```java
public class LeftFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.left_fragment,container,false);//将刚创建的布局文件加载进来
        return view;
    }
}
```

同理创建left_fragment.xml和 RightFragment

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:background="#C1E4C1">

    <TextView
        android:text="Fragment" ></TextView>
</LinearLayout>
```

```java
public class RightFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.right_fragment,container,false);
        return view;
    }
}
```

同理创建another_right_fragment.xml和AnotherRightFragment类

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:background="#CCCC84"
    android:orientation="vertical">

    <TextView
        android:text="另一个Fragment"></TextView>
</LinearLayout>
```

```java
public class AnotherRightFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.another_right_fragment,container,false);
        return view;
    }
}
```



最后在main.xml中添加左侧的fragment，右侧用一个帧布局作为fragment的容器

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal">

    <fragment
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="1"
        android:name="com.example.a03_fragmenttest.LeftFragment"//这里要写完整的包名
        android:id="@+id/left_fragment"></fragment>

     <FrameLayout
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="1"
        android:id="@+id/right_layout"></FrameLayout>
</LinearLayout>
```

MA中添加点击按钮切换Fragment的功能：

```java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(new RightFragment());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AnotherRightFragment());
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager=getSupportFragmentManager();//获取FM实例
        FragmentTransaction transaction=fragmentManager.beginTransaction();//开启一个事务
        transaction.replace(R.id.right_layout,fragment);//向容器内(帧布局)添加或替换碎片
        transaction.addToBackStack(null);//把事务添加到返回栈，接收一个名字描述返回栈的状态，一般传入null即可
        transaction.commit();   //提交事务
        //可以直接替换为 getFragmentManager().beginTransaction().replace(R.id.right_layout,fragment).commit();
    }
}
```



### 2.2 碎片和活动间的通信

![image-20210317154436678](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210317154436678.png)

##### 实例：Bundle发送数据：

```java
public class MainActivity extends AppCompatActivity {
    private Button button;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      	//省略findView
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("msg","我爱你");
                BlankFragment blankFragment=new BlankFragment();  //创建Fragment对象
                blankFragment.setArguments(bundle);//把Bundle对象传入Argument，就跟Intent一样
                replaceFragment(blankFragment);
            }
        });
    }
    
    private void replaceFragment(Fragment fragment) {
       getFragmentManager().beginTransaction().replace(R.id.right_layout,fragment).commit();
    }
}
```

Fragment：

```java
public class BlankFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();	//如果获取Intent
        String msg=bundle.getString("msg");
        Log.e(TAG,msg);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }
}
```





##### 实例：接口通信

首先创建一个接口，写入需要的方法：

```java
public interface Communication {
    void send_Msg_To_Activity(String msg);
}
```

创建一个BlankFragment，并将接口定义为成员变量，写入发送消息的方法逻辑

```xml
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context=".BlankFragment">
    
    <Button
        android:id="@+id/btn_sendMsg"
        android:layout_gravity="center"
        android:text="发送消息" />
</FrameLayout>
```

```java
public class BlankFragment extends Fragment {
    private Button send_msg;
    private Communication mCommunication;

    public BlankFragment() {
    }

    public void setFragmentCallBack(Communication communication){
        mCommunication=communication;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        send_msg = view.findViewById(R.id.btn_sendMsg);
        //将信息传入接口的方法
        send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCommunication.send_Msg_To_Activity("你好，我是Fragment");
            }
        });
        return view;
    }
}
```



MA中，对fragment实例对象调用 【匿名内部类实现接口】的方法：

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:gravity="center"
    android:orientation="vertical">

    <Button
        android:id="@+id/show_Fragment"
        android:onClick="show_Fragment"
        android:text="展示Fragment" />

    <FrameLayout
        android:id="@+id/frame_layout" />
</LinearLayout>
```

```java
public class MainActivity extends AppCompatActivity {
    private BlankFragment mBlankFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //加载出Fragment
    public void show_Fragment(View view) {
        mBlankFragment=new BlankFragment();//新建fragment对象实例
        //动态加载fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,mBlankFragment).commit();
        //设计匿名内部类接口
        mBlankFragment.setFragmentCallBack(new Communication() {
            @Override
            public void send_Msg_To_Activity(String msg) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
```







### 2.3 碎片的状态

| 状态     | 描述                                                         |
| -------- | ------------------------------------------------------------ |
| 运行状态 | 当一个碎片可见，并且它所关联的活动也处于运行状态时，该碎片也处于运行状态。 |
| 暂停状态 | 当一个活动进入暂停状态时，与它关联的可见碎片就会进入到暂停状态 |
| 停止状态 | 当一个活动进入停止状态时，与它关联的碎片就会进入到停止状态，或者通过FragmentTransaction的remove（）、replace（）方法将碎片从活动中移除，但如果事务提交之前调用addToBackStack（），这时碎片也会进入到停止状态。停止状态的碎片对用户完全不可见，有可能被系统回收。 |
| 销毁状态 | 当活动被销毁时，与它关联的碎片就会进入到销毁状态，或者移除。如果事务提交之前没有调用 addToBackStack（）方法，这时碎片也会进入到停止状态。进入停止状态的碎片对用户来说是完全不可见的。 |







### 2.4 碎片的生命周期

![image-20210113133337533](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210113133337533-1625052729134.png)

| 回调方法           | 描述                                   |
| ------------------ | -------------------------------------- |
| onAttach()         | 活动和碎片建立关联时调用               |
| onCreateView()     | 为碎片创建视图（加载布局）时调用       |
| onActivityCreate() | 确保与碎片关联的活动已经创建完毕时调用 |
| onDestoryView()    | 与碎片关联的视图被移除的时候调用       |
| onDetach()         | 当碎片和活动解除关联时调用             |





### 2.5 动态加载布局的技巧

程序根据分辨率或屏幕决定加载哪个布局



#### 2.5.1 使用限定符

平板采用双页模式，手机采用单页模式，借助限定符（Qualifiers）判断，我们就可以实现平板和手机上不同的布局界面：



例题：拿2.1的案例来说

将主布局文件中的帧布局删除，只留下fragment，并让它match_parent。

/res下创建layout-large文件夹，并创建activity_main，采用双页模式，实现两个碎片：



**创建layout-large步骤如下：**

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210123133229556.png" alt="image-20210123133229556" style="zoom:80%;" />

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210123133400752.png" alt="image-20210123133400752" style="zoom: 80%;" />

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210123133434686.png" alt="image-20210123133434686" style="zoom:80%;" />

同理，其他的大小还可以适应小屏幕设备、中屏幕设备、超大屏幕设备等

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal">

    <fragment
        android:id="@+id/left_fragment"
        android:name="com.example.my.http.leftFragment"
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="1"></fragment>

    <fragment
        android:id="@+id/right_fragment"
        android:name="com.example.my.http.rightFragment"
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="3"></fragment>
</LinearLayout>
```

此时，就实现了大屏幕下双页模式，小屏幕下的单页模式。



#### 2.5.2 使用最小宽度限定符

它允许我们指定一个屏幕宽度的最小值（单位：dp），宽度大于它的采用另一个布局

同理，跟上述创建layout-large一样，指定不同选项即可：

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210123134042544.png" alt="image-20210123134042544" style="zoom:80%;" />





### 2.6 编写最佳实例——简易版新闻应用

![image-20210123185626829](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210123185626829.png)

##### 1.gradle中添加RecyclerView的依赖库：

```java
dependencies {
    //……
    implementation 'com.android.support:recyclerview-v7:23.4.0'	//RecyclerView库
}
```

##### 2.新建News类，添加属性：标题和内容，重写Get和Set方法

```java
public class News {
    private String title;   //新闻标题
    private String content; //新闻内容

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
```

##### 3.新建双页模式的新闻页的布局视图：

头部TV显示标题，尾部TV显示内容，中间用View作为分割线，隔开

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android">
    <LinearLayout
        android:id="@+id/visibility_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:visibility="invisible">

        <TextView
            android:id="@+id/news_title"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"/>

        <!--分割线-->
        <View
            android:layout_width="match_parent"
            android:layout_height="1dp"
            android:background="#000"></View>

        <TextView
            android:id="@+id/news_content"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1"
            android:padding="15dp"
            android:textSize="18sp" />
    </LinearLayout>

    <!--分割线-->
    <View
        android:layout_width="1dp"
        android:layout_height="match_parent"
        android:layout_alignParentLeft="true"
        android:background="#000"></View>
</RelativeLayout>
```



##### 4.新建NewsContentFragment类，关联此布局

```java
public class NewsContentFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_content_flag,container,false);    //返回新闻布局界面的视图
    }
	//刷新标题和内容
    public void refresh(String newsTitle,String newsContent){
        View visibilityLayout=view.findViewById(R.id.visibility_layout);
        visibilityLayout.setVisibility(View.VISIBLE);
        TextView newsTitleText= view.findViewById(R.id.news_title);
        TextView newsContentText= view.findViewById(R.id.news_content);
        //将传入的形参设置为新闻的标题和内容
        newsTitleText.setText(newsTitle);
        newsContentText.setText(newsContent);
    }
}
```



##### 5.新建单页模式下的活动 NewsContentActivity

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/news_content"
    android:orientation="vertical">

    <fragment
        android:id="@+id/news_content_fragment"
        android:name="com.example.a45_fragmentbestpractice.NewsContentFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"></fragment>
</LinearLayout>
```

```java
public class NewsContentActivity extends AppCompatActivity {
    //启动活动的最佳写法
    public static void actionStart(Context context,String newsTitle,String newsContent){
        Intent intent=new Intent(context,NewsContentActivity.class);
        intent.putExtra("news_title",newsTitle);
        intent.putExtra("news_content",newsContent);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);

        String newsTitle=getIntent().getStringExtra("news_title");  //获取传入的新闻标题
        String newsContent=getIntent().getStringExtra("news_content");//获取传入的新闻内容
        NewsContentFragment newsContentFragment= (NewsContentFragment) getSupportFragmentManager().findFragmentById(R.id.news_content_fragment);//通过fragment的ID找对应的Fragment实例
        newsContentFragment.refresh(newsTitle,newsContent);//刷新新闻界面
    }
}
```



##### 6.新建显示新闻列表的布局news_title_frag.xml 和 显示每个子项的布局 news_item.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <android.support.v7.widget.RecyclerView
        android:id="@+id/news_title_recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"></android.support.v7.widget.RecyclerView>
</LinearLayout>
```

```xml
<?xml version="1.0" encoding="utf-8"?>
<TextView xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/news_title"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:maxLines="1"
    android:ellipsize="end"		//表示文本内容超出控件宽度时，文本的缩略方式，end为尾部缩略
    android:textSize="18sp"
    android:padding="10dp"
    >
</TextView>
```



##### 7.使用限定符，分别写入双页模式和单页模式的布局

/layout下的activity_main.xml中使用单页模式：

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/news_title_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <fragment
        android:id="@+id/news_title_frament"
        android:name="com.example.a45_fragmentbestpractice.NewsTitleFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"></fragment>
</FrameLayout>
```

新建/layout-sw600dp 下的activity_main.xml使用双页模式

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal">

    <fragment
        android:id="@+id/news_title_fragment"
        android:name="com.example.a45_fragmentbestpractice.NewsTitleFragment"
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="1"></fragment>

    <FrameLayout
        android:id="@+id/news_content_layout"
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="3">

        <fragment
            android:id="@+id/news_content_fragment"
            android:name="com.example.a45_fragmentbestpractice.NewsContentFragment"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
        </fragment>
    </FrameLayout>
</LinearLayout>
```



##### 8.新建NewsTitleFragment 作为展示新闻列表的碎片

```java
public class NewsTitleFragment extends Fragment {
    private  boolean isTwoPane;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.news_title_frag,container,false);   //关联布局
        //找到布局中的RecyclerView控件
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.news_title_recycler_view);
        //设置线性布局管理器管理RecyclerView
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //为RecyclerView设置数据
        NewsAdapter adapter=new NewsAdapter(getNews());
        recyclerView.setAdapter(adapter);
        return view;
    }
    //设置新闻中的内容
    public List<News> getNews() {
        List<News> newslist=new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            News news=new News();
            news.setTitle("新闻标题"+i);
            news.setContent("新闻内容"+i);
            newslist.add(news);
        }
        return newslist;
    }
    //当活动创建时通过判断活动中有无双页模式下的特有ID，设置isTwoPane属性
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.news_content_layout)!=null){
            isTwoPane=true;
        }else {
            isTwoPane=false;
        }
    }

    //通过匿名内部类NewsAdapter作为RecycleView的适配器
    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{
        private List<News> mNewsList;  //新闻列表

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView newsTitleText;
            public  ViewHolder(View view) { //内部类的构造函数，形参view是RecyclerView子项的最外层布局
                super(view);
                newsTitleText=   view.findViewById(R.id.news_title); //获取新闻标题的控件
            }
        }
        //外层类的构造函数
        public NewsAdapter(List<News> newslsit){
            mNewsList=newslsit;     //把要展示的数据赋值给全局变量
        }
        //创建实例
        @Override
        public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false); //每个子项加载成一个单独的view
            final ViewHolder holder=new ViewHolder(view);   //利用view创建ViewHolder实例，把子项传入构造函数
            //设置每个子项的单击监听器
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    News news=mNewsList.get(holder.getAdapterPosition()); //给新闻列表的每个值赋予一个实例化的News对象
                    if (isTwoPane){
                        //是双页模式，则更新碎片中的内容
                        NewsContentFragment newsContentFragment= (NewsContentFragment) getFragmentManager().findFragmentById(R.id.news_content_fragment);
                        newsContentFragment.refresh(news.getTitle(),news.getContent());
                    }else {
                        //单页模式则启动活动展示
                        NewsContentActivity.actionStart(getActivity(),news.getTitle(),news.getContent());
                    }
                }
            });
            return holder;
        }
        //为view子项数据加载并赋值，在每个子项滚动到屏幕内时执行
        @Override
        public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
            News news=mNewsList.get(position);
            holder.newsTitleText.setText(news.getTitle()); //设置新闻标题
        }
        //告诉RecylerView共有多少子项
        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }
}
```













## 三、BroadcastReceiver

广播，没有自己的表现形式。广播接收器更多地是扮演一个打开其他程序或组件的角色，用于传递或提示某些信息，比如启动一个服务，创建一条状态栏通知等等。



广播接收器可以自由地对自己感兴趣的广播进行注册，当有相应的广播发出时，广播接收器就能收到该广播，并在内部处理响应逻辑。注册广播的方式一般有两种，代码中注册【动态注册】和 Manifest中注册【静态注册】。

注意：无论哪种注册方式，都需要重写 onReceive() 方法，不要在 onReceive() 方法中添加过多的逻辑或进行任何的耗时操作，因为==在广播接收器中是不允许开启线程的==，当它运行较长时间还没有结束时，程序就会报错。

![image-20210315155122209](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210315155122209.png)

### 一、广播的注册方式

#### 1.1 动态注册

动态注册的广播接收器可以自由地控制注册与注销，灵活性方面有着很大的优势，但也存在着一个缺点，必须要在程序启动之后才能接收到广播，因为注册逻辑是写在oncreate（）方法中的

使用一个类，继承自BroadcastReceiver，重写onReceive（）方法即可。

==动态注册的广播接收器一定要取消注册==



##### 案例：动态监听网络变化案例

 manifest 中申明网络监听权限

```xml
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```



###### 方法1：内部类注册

```java
public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);  //取消注册
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        //想监听什么广播，这里添加什么值。网络状态发生变化时，系统发出的正是一条值为该字符串的广播
        networkChangeReceiver=new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);   //进行注册
    }

    class NetworkChangeReceiver extends BroadcastReceiver{
        //当有广播到来时，该方法自动会执行
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            //ConnectivityManager这个类是系统服务类，专用于管理网络连接的
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo(); //获取networkInfo实例
            if (networkInfo != null && networkInfo.isAvailable()) { //判断当前网络是否可用
                Toast.makeText(context, "network is available", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
```



###### 方法2：外部类注册

新建外部类并继承MyBRReceiver：

```java
public class MyBRReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"网络状态发生改变~",Toast.LENGTH_SHORT).show();
    }
}
```

MA中进行广播的注册：

```java
public class MainActivity extends AppCompatActivity{
    MyBRReceiver myReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //核心部分代码：
        myReceiver = new MyBRReceiver();//创建实例对象
        IntentFilter itFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");	//添加IF过滤
        registerReceiver(myReceiver, itFilter); //注册广播
    }

    //广播取消注册
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }
}
```





#### 1.2 静态注册

使用android studio创建广播接收器：

![image-20201220162939015](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20201220162939015.png)

![image-20201220163049580](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20201220163049580.png)

##### 案例：监听开机启动：

Android4.3以上版本，允许将应用安装在SD卡，开机一段时间后才加载SD卡，这样就导致监听不到该广播。所以需要既监听开机广播，又监听SD卡挂载广播。有的手机可能没有SD卡，所以两个广播监听器要写到两个 Intent-filter 里。

如上方法注册BootCompleteReceiver：

```java
public class BootCompleteReciver extends BroadcastReceiver {
    //该方法中不要添加过多的逻辑和耗时操作，且不允许开启线程，否则程序报错
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_BOOT.equals(intent.getAction()))
            Toast.makeText(context, "开机完毕~", Toast.LENGTH_LONG).show();
    }
}
```

Manifest中添加权限和过滤：

```xml
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>		//所需权限
<receiver
    android:name=".BootCompleteReceiver"
    android:enabled="true"		//这就是刚才打对勾的两个权限
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.BOOT_COMPLETED"/>		//intent-filter过滤监听内容
    </intent-filter>
     <intent-filter>
        <action android:name="ANDROID.INTENT.ACTION.MEDIA_MOUNTED"/>
        <action android:name="ANDROID.INTENT.ACTION.MEDIA_UNMOUNTED"/>
        <data android:scheme="file"/>
    </intent-filter>
</receiver>
```



### 二、广播的发送方式

广播可以分为两种类型：【标准广播】和【有序广播】

标准广播：是一种完全的异步执行的广播，广播发出后，所有的广播接收器几乎都在同一时刻接收这条广播消息，没有先后顺序。效率高，但无法被截断。

有序广播：同步执行的广播，同一时刻只有一个广播接收器能接收这条消息，当这个广播接收器的逻辑执行完毕后，广播才继续传递。优先级高的广播先接到广播消息，前面的广播接收器还可以截断正在传递的广播。

#### 2.1 发送标准广播

新建一个静态注册的广播发送类，写入要进行的程序逻辑

```java
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
         //当MyBroadcastReceiver收到自定义的广播时，就会弹出消息提示
        Toast.makeText(context,"received in MyBroadcastReceiver",Toast.LENGTH_SHORT).show();
    }
}
```

在Manifest中，对这个广播接收器进行修改：

```xml
<receiver
    android:name=".MyBroadcastReceiver"
    android:enabled="true"
    android:exported="true">

    <intent-filter>
        <action android:name="com.example.broadcasttest.MY_BROADCAST"/>		//设置好需要接收的广播类型
    </intent-filter>
</receiver>
```

activity_main.xml 中添加发送广播的按钮，MA中写入发送的逻辑代码：

```java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button= findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("com.example.broadcasttest.MY_BROADCAST");//先把要发送广播的值传入Intent对象
                sendBroadcast(intent);//用该方法将广播发送出去，所有监听到上述字符串的广播接收器就会收到消息
            }
        });
    }
}
```



#### 2.2 发送有序广播

只需要在MA中改动发送方式的那条代码即可：

```java
public void onClick(View v) {
    Intent intent=new Intent("com.example.broadcasttest.MY_BROADCAST");//先把要发送广播的值传入Intent对象
    sendOrderedBroadcast(intent,null);//改为有序广播发送方式，第二个参数是与权限相关的字符串
}
```

在Manifest中，可以设置广播接收器的优先级，有序广播的优先级可以在【-1000~1000】之间

```xml
<receiver
    android:name=".MyBroadcastReceiver"
    android:enabled="true"
    android:exported="true">

    <intent-filter android:priority="100">		<!--设置优先级-->
        <action android:name="com.example.broadcasttest.MY_BROADCAST"/>
    </intent-filter>
</receiver>
```

再回到之前的广播发送类中，通过abortBroadcast();方法，即可截断广播的发送

```java
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"received in MyBroadcastReceiver",Toast.LENGTH_SHORT).show();
        //当MyBroadcastReceiver收到自定义广播时，就会弹出消息提示
        abortBroadcast();//截断广播的发送
    }
}
```





### 三、本地广播

全局广播如果带有关键性数据，可能被其他程序截获，造成安全性问题。

本地广播，使用该机制发出的广播**只能在应用程序的内部进行传递**，并且==只接收本应用程序发出的广播。本地广播只能动态注册。==

```java
public class MainActivity extends AppCompatActivity {
    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;

     //利用一个LocalBroadcastManager类，对广播进行管理
    class LocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"received local broadcast",Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localBroadcastManager=LocalBroadcastManager.getInstance(this);	//获取实例
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("com.example.broadcasttest.LOCAL_BROADCAST");	//注册本地发送广播
                localBroadcastManager.sendBroadcast(intent);
            }
        });
        intentFilter=new IntentFilter();
        intentFilter.addAction("com.example.broadcasttest.LOCAL_BROADCAST");    //添加IF监听本地广播
        localReceiver=new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);		//动态注册本地广播接收器
    }
    
     @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }
}
```







### 四、练习：强制下线功能

==**【程序逻辑】：**==

![image-20201222160205805](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20201222160205805.png)





==**【具体实现代码】：**==

```java
public class ActivityCollector {
    public static List<Activity> activities=new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActvity(Activity activity){
        activities.remove(activity);

    }

    public static void finishAll(){
        for (Activity activity : activities) {
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
        activities.clear();
    }
}
```



```java
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity",getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActvity(this);
    }

    //创建广播接收器接收强制下线广播
    private ForceOfflineReceiver receiver;

    @Override
    protected void onPostResume() {
        //在onPostResume方法中注册广播接收器，能保证只有处于栈顶的活动才能接收到这条广播，非栈顶活动不用接收
        super.onPostResume();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.example,broadcastbestpractive.FORCE_OFFLINE");  //监听自定义的下线广播
        receiver =new ForceOfflineReceiver();
        registerReceiver(receiver,intentFilter);//注册广播
    }

    @Override
    protected void onPause() {
        //在onPause()方法中注册广播接收器，能保证只有处于栈顶的活动才能接收到这条广播，非栈顶活动不用接收
        super.onPause();
        if (receiver!=null){
            unregisterReceiver(receiver);
            receiver=null;
        }
    }

    class ForceOfflineReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(final Context context, Intent intent) {
            //添加了一个对话框，提示你被强制下线信息，用户点确定时，销毁所有活动并重置界面
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle("Warning");
            builder.setMessage("你被强制下线了");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCollector.finishAll();  //销毁所有活动
                    Intent intent=new Intent(context,LoginActivity.class);
                    context.startActivity(intent);  //重启登录界面
                }
            });
            builder.show();
        }
    }
}
```



登录界面布局文件：

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="10dp">

    <LinearLayout
        android:layout_marginTop="130dp"
        android:orientation="horizontal"
        android:layout_width="match_parent"
        android:layout_height="60dp">

        <TextView
            android:layout_width="90dp"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:textSize="18sp"
            android:text="账号："/>

        <EditText
            android:id="@+id/account"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:layout_gravity="center_vertical" />
    </LinearLayout>

    <LinearLayout
        android:orientation="horizontal"
        android:layout_width="match_parent"
        android:layout_height="60dp">

        <TextView
            android:layout_width="90dp"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:textSize="18sp"
            android:text="密码："/>

        <EditText
            android:id="@+id/password"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:layout_gravity="center_vertical"
            android:inputType="textPassword"/>
    </LinearLayout>

    <Button
        android:id="@+id/login"
        android:layout_width="match_parent"
        android:layout_height="60dp"
        android:text="登录"/>
</LinearLayout>
```

```java
public class LoginActivity extends BaseActivity {

    private EditText accountEdit,passwordEdit;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accountEdit=  findViewById(R.id.account);
        passwordEdit=  findViewById(R.id.password);
        login=   findViewById(R.id.login);
        //如果用户名为my，密码123则登陆成功，否则提示账号或密码不正确
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account=accountEdit.getText().toString();
                String password=passwordEdit.getText().toString();
                if (account.equals("my")&&password.equals("123")){
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,"账号或密码不正确",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
```



主活动布局文件：

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="10dp"
    >

    <Button
        android:id="@+id/force_offline"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="强制下线"/>
</LinearLayout>
```

```java
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button forceOffline=   findViewById(R.id.force_offline);
        forceOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("com.example,broadcastbestpractive.FORCE_OFFLINE");    //发送一条广播，值为该字符串
                sendBroadcast(intent);
            }
        });
    }
}
```



Manifest文件中：修改启动Activity

```xml
<activity android:name=".MainActivity"></activity>

<activity android:name=".LoginActivity">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```









## 四、ContentProvider

该组件用来访问一些共享数据，用其余方式也能达到共享数据的效果，如：文件存储，SharedPreference等，但读写数据的方式和API都不相同。

使用ContentProvider的好处是：**统一了数据访问方式**。



### 一、安卓权限机制

android6.0以下，安装应用程序时会显示所有需要的权限内容，但有些软件存在“店大欺客”的现象，不管是否能用到，先申请了再说。

6.0系统中加入了运行时权限的功能，在软件使用过程中对某一项申请进行授权。安卓将所有权限归成了两类，一类是【普通权限】，一类是【危险权限】。普通权限是指不会直接威胁到用户的安全和隐私的权限，对于这部分，系统会自动帮我们进行授权，不用手动操作。危险权限表示可能触及用户隐私或对设备安全性造成影响的权限，如获取设备联系人信息，定位设备地理位置。这部分权限必须要用户手动点击授权。

查看完整权限列表：https://developer.android.google.cn/reference/android/Manifest.permission.html



所有危险权限：9组24个

如果以后想关闭权限，可在手机设置——应用——权限管理中关闭

![image-20201225194406862](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20201225194406862.png)





### 二、动态申请权限

```java
//判断用户是不是已经给过我们授权
//该方法第一个参数是Context，第二个参数是具体的权限名，用它的返回值和【PERMISSION_GRANTED】常量值作比较，相同即为授权，不同即为没授权
 		 //没授权的话，申请授权，该方法会弹出一个对话框
     //该方法三个参数分别是：Activity实例，二是String数组，把要申请的权限放入数组中即可，三是请求码，是唯一值即可
if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED) {
     ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
} else {
     //这里写入已授权的逻辑
}

    //对话框弹出选择后的最终结果执行这个方法，授权的所有结果（申请N个权限产生N个结果）封装在最后的参数grantResults中
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //如果用户同意，xxx处理
                } else {
                    //拒绝的话就弹出提示信息
                    Toast.makeText(this, "你没授予XXX权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
```





#### 多个权限一同申请：

```java
		//创建一个list集合，如果需要的权限被授予，则添加到这个集合中，
        List<String> permiossionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permiossionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permiossionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permiossionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        //如果集合不为空，则转换成数组，然后一同授权
        if (!permiossionList.isEmpty()) {
            String[] permissions = permiossionList.toArray(new String[permiossionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        } else {
            //已经授权了的处理逻辑
        }
    


   //申请权限的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        //如果有任何一个权限被拒绝，则直接关闭当前程序
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "需要同意所有权限才可使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    //所有权限都同意的处理
                  
                } 
            default:
                break;
        }
    }
```







### 三、访问其他程序中的数据

内部存储的数据只能在本应用程序中访问，一些可以让其他程序进行二次开发的基础性数据，可以选择共享给其他程序。例如系统的电话簿程序，短信，媒体库等。

内容提供器（ContentProvider）主要用于在不同应用程序之间实现数据共享的功能，它提供了一套完整的机制，允许一个程序访问另一个程序中的数据，可以选择只对哪一部分数据进行共享，从而保证被访数据的安全性。

![image-20210315175802276](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210315175802276.png)



ContentProvider的用法有两种：

1. 使用现有的ContentProvider来读取和操作相应程序中的数据
2. 创建自己的ContentProvider给我们程序的数据提供外部访问接口（电话簿、短信等都提供了类似的访问接口）



#### 3.1 基本用法：

想要访问内容提供器中共享的数据，就一定要借助ContentResolver类

| 方法名                       | 描述                                                         |
| ---------------------------- | ------------------------------------------------------------ |
| Context.getContentResolver() | 获取实例对象，提供了一系列的增删改查方法，和sql的API相同。不同的是，参数1为URI而非表名，这个URI也被称为**内容URI** |

内容 URI 由 authority 和 path 组成，authority 一般采用包名对不同的应用程序做区分。path对同一应用程序的不同表做区分。

比如com.example.app中有 table1 和 table2 两张表，这时内容 URI 为 String类型的：content://com.example.app.provider/table1 。可以用 URI.Parse()  方法即可转换成URI对象。

不仅可以访问数据库的数据，还可以访问XML文件，如果URI表示为	~/word/detail	时，表示访问word节点下的detail结点

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210120212033677.png" alt="image-20210120212033677" style="zoom: 33%;" />



参数简单演示：

```java
Cursor cursor=getContentResolver（）.query(uri,projection,selection,selectionArgs,sortOrder);
if(cursor!=null){
    while(cursor.moveToNext()){
        String column1=cursor.getString(cursor.getColumnIndex("列名"))
        …………
    }
    cursor.close();
}

//增删改
ContentValues values=new ContentValues();
values.put("键","值");
getContentResolver().insert(uri,values);
getContentResolver().updata(uri,values,"列1=? and 列2=?",new String[]{"值1","值2"});
getContentResolver().delete(uri,"列1=?",new String[]{"值"});
```

![image-20201226171317394](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20201226171317394.png)



#### 3.2 范例

##### 读取系统联系人：



Manifest加入权限申请:

```xml
<uses-permission android:name="android.permission.READ_CONTACTS"/>
```

xml 和 MA：

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical">

    <ListView
        android:id="@+id/contacts_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"></ListView>
</LinearLayout>
```

```java
public class MainActivity extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    List<String> contactslist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView contactsView = findViewById(R.id.contacts_view);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactslist);
        contactsView.setAdapter(adapter);//设置ListView的适配器

        //是否授权
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED) {
            //没授权则申请授权
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            readContacts();//如果授权则读取联系人
        }
    }

    private void readContacts() {
        Cursor cursor=null;
        //查询联系人数据
        try {
            cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
            if (cursor!=null){
                while (cursor.moveToNext()){
                    //获取联系人姓名
                    String displayName=
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    //获取联系人手机号
                    String number=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactslist.add(displayName+"\n"+number);
                }
                adapter.notifyDataSetChanged(); //刷新
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (cursor!=null){
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                readContacts();
            }else {
                Toast.makeText(this,"你没有权限",Toast.LENGTH_SHORT).show();
            }
            break;
            default:
        }
    }
}
```



##### 查询指定电话的联系人信息：

```java
private void queryContact(String number){
    Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/" + number);
    ContentResolver resolver = getContentResolver();
    Cursor cursor = resolver.query(uri, new String[]{"display_name"}, null, null, null);
    if (cursor.moveToFirst()) {
        String name = cursor.getString(0);
        System.out.println(number + "对应的联系人名称：" + name);
    }
    cursor.close();
}
```





##### 读取短信内容：

```xml
<uses-permission android:name="android.permission.READ_SMS"/>
```

```java
private void getMsgs(){
        Uri uri = Uri.parse("content://sms/");
        ContentResolver resolver = getContentResolver();
        //获取的是哪些列的信息
        Cursor cursor = resolver.query(uri, new String[]{"address","date","type","body"}, null, null, null);
        while(cursor.moveToNext())
        {
            String address = cursor.getString(0);
            String date = cursor.getString(1);
            String type = cursor.getString(2);
            String body = cursor.getString(3);
            System.out.println("地址:" + address);
            System.out.println("时间:" + date);
            System.out.println("类型:" + type);
            System.out.println("内容:" + body);
            System.out.println("======================");
        }
        cursor.close();
    }
```





### 三、创建自己的ContentProvider（外部接口）

（用的很少，了解就好）

编写一个类继承 ContentProvider 类，实现其中的六个方法：

| 需要实现的方法     | 具体功能                                                     |
| ------------------ | ------------------------------------------------------------ |
| boolean onCreate() | 初始化内容提供器的时候调用，通常在这完成对数据库的创建和升级操作 |
| Cursor query（）   | 从内容提供器中查询数据，具体参数见3.1                        |
| Uri insert（）     | 向内容提供器中添加一条数据，使用uri参数确定要添加到的表，待添加的数据保存在values中。完成后返回一个URI表示这条新记录 |
| int update（）     | 更新内容提供器中已有数据，uri确定哪一张表中的数据，数据保存在values中，selection和selectionArgs用于约束更新条件，返回值为受影响的行数 |
| int delete（）     | 从内容提供器中删除数据，uri指定删除那张表，selection和selectionArgs用于约束删除条件，返回值为受影响的行数 |
| String getType（） | 根据传入的URI返回相应的MIME类型                              |



==URI 内容的格式规则：==

content://com.example.app.provider/table1  	表示访问的是 com.example.app 应用的 table1 表

content://com.example.app.provider/table1/1 	表示访问table1表中id为1的数据

content://com.example.app.provider/* 	表示匹配任意表的内容

content://com.example.app.provider/table1/# 	表示匹配 table1 中任意一行的数据内容



借助URIMatcher中提供的一个addURI（）方法，就能实现匹配内容URI的功能。接收的三个参数分别是anthority，path和一个自定义的代码。只要不向该雷中添加隐私数据，外部程序就不能访问到这部分数据。



URI返回的MIME类型：由三部分组成：

* 必须以vnd开头
* 如果内容以URI路径结尾，后接android.cursor.dir/，如果以id结尾，则后接android.cursor.item/
* 最后接上 vnd.<authority>.<path>

> 对于上述前两个URI，分别对应
>
> vnd.android.cursor.dir/vnd.com.example.app.provider.table1
>
> vnd.android.cursor.item/vnd.com.example.app.provider.table1





```java
public class MyProvider extends ContentProvider {

    public static final  int TABLE1_DIR=0;
    public static final  int TABLE1_ITEM=1;
    public static final  int TABLE2_DIR=2;
    public static final  int TABLE2_ITEM=3;

    public  static UriMatcher uriMatcher;

    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.examole.app.provider","table1",TABLE1_DIR);
        uriMatcher.addURI("com.examole.app.provider","table1/#",TABLE1_ITEM);
        uriMatcher.addURI("com.examole.app.provider","table2",TABLE2_DIR);
        uriMatcher.addURI("com.examole.app.provider","table2/#",TABLE2_ITEM);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (uriMatcher.match(uri)){
            case TABLE1_DIR:
                //查询table1表中的所有数据
                break;
            case TABLE1_ITEM:
                //查询table1表中的单条数据
                break;
            case TABLE2_DIR:
                //查询table2表中的所有数据
                break;
            case TABLE2_ITEM:
                //查询table2表中的单条数据
                break;
            default:
                    break;
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case TABLE1_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.app.provider.table1";
            case TABLE1_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.app.provider.table1";
            case TABLE2_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.app.provider.table2";
            case TABLE2_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.app.provider.table2";
            default:
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
```



## 五、service

后台长期运行，并且没有用户界面。适合执行不需要和用户交互，还要求长期运行的任务，如下载、音乐播放等。

服务依赖于创建服务时所在的应用程序进程，当某个应用程序进程被杀掉时，所有依赖于该进程的服务也会停止运行。

> 任何一个服务在整个应用程序范围内都是通用的，可以和多个活动绑定，绑定后都可以获取到相同的匿名内部类实例



### 一、多线程编程

进程：一个android应用就是一个进程，每个应用在各自的进程中运行

线程：比进程更小的独立运行的基本单位，一个进程可以包含多个线程

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210121155206035.png" alt="image-20210121155206035" style="zoom:50%;" />

当需要执行耗时操作，比如发起一条网络请求时，服务器未必会立刻相应我们的请求，如果不将这类操作放到子线程去做，主线程将会被堵塞，从而影响用户对软件的正常使用。==Android4.0后禁止在主线程中执行网络操作，否则报**android.os.NetworkOnMainThreadException**==



#### 1.1 Hadnle更新UI

Android的UI也是线程不安全的，如果想更新应用程序里的UI元素必须在主线程中进行。

==子线程不允许操作主线程中的组件==，但是可以通过发送Message，用Handler对象处理Message，达到更新UI的效果。



Handler是android中提供的一个消息处理的机制，可以在任意线程中发送消息，在主线程中获取并处理消息。

例题：点击按钮改变文字：

```xml
<Button
    android:id="@+id/change_text"
    android:text="改变文字" />

<TextView
    android:id="@+id/text"
    android:text="Hello World" />
```

MA代码中使用子线程发送Message，Handle处理：

```java
public class MainActivity extends AppCompatActivity{
    public  static  final int UPDATA_TEXT=1;    //表示更新TV这个动作
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //省略findView
        changeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //也可以用Message.obtain()方法或Handler.obtainMessage()获取，避免创建新的Message对象
                        Message message=new Message();
                        //设置发送的消息内容
                        message.what=UPDATA_TEXT;
                        //发送Message
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }

    private Handler handler=new Handler(){      //定义Handler对象处理Message
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
               	//如果收到的是UPDATA_TEXT，则改变TV显示的内容
                case UPDATA_TEXT:
                    textView.setText("Nice to meet you");
                    break;
                default:
                    break;
            }
        }
    };
}
```



#### 1.2 异步处理消息机制

异步消息处理主要由四部分组成：Message、Handler、MessageQueue、Looper

![image-20210125154004517](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210125154004517.png)

一条Message经过如上一个流程的辗转后，从子线程进入到了主线程，就可以更新UI了

##### 1.Message

线程之间传递的消息，它可以在内部携带少量的信息，用于在不同线程之间交换数据

Message对象的属性：

| 属性名称   | 描述             |
| ---------- | ---------------- |
| arg1、arg2 | 整形             |
| obj        | Object类型       |
| replyTo    | 发送到何处       |
| what       | 自定义的消息代码 |



##### 2.Handler

负责消息的发送和处理，发送消息一般使用  handler.sendMessage()  方法，经过一系列辗转处理后，最终会传递到重写的  handleMessage() 方法中



##### 3.MessageQueue

存放所有通过Handler发送的消息，这部分消息一直存于队列中，等待被处理，**每个线程只有一个MessageQueue对象**



##### 4.Looper

是每个线程中的MessageQueue管家，调用 Looper.loop() 方法后，就会进入一个无限循环中，每当发现 MessageQueue中有一条消息，就会将它取出，传递到HandlerMessage（）方法中，**每个线程也只有一个Looper对象**

> 子线程中使用Toast，程序运行时就会出现错误，在Toast前后加入 Looper.prepered() 和 Looper.loop() 方法后，可以正常运行
>



#### 1.3 AsyncTask

它是一个方便在子线程对UI操作的类，实现原理基于异步消息处理机制，Android为我们做了很好的封装。实际异步用的最多的地方就是网络操作，图片加载，数据传输等。

优点：方便实现异步通信（不需要继承Thread类），节省资源（采用线程池的缓存线程+复用线程，避免了频繁创建和销毁线程带来的资源开销）



该类是个抽象类，需要被继承，继承时可以为它指定3个泛型参数：

* params：启动任务执行的输入参数，比如HTTP请求的URL
* progress：任务执行的进度（百分比）
* result：任务执行完毕返回的结果，这里指定的泛型作为返回值类型。

```java
class DownloadTask extends AsyncTask<Void,Integer,Boolean>	//表示不需要传参给后台任务，整形数据作为进度单位，布尔型反馈结果
    //类中定义成员变量，需要更新的UI控件
```

```java
//使用
DownloadTask myTask = new DownloadTask(); //参数为显示进度和内容的UI控件 
myTask.execute();  //该对象开始运行
```



核心方法:

![1](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/1.png)

==注意事项：==

1. AsyncTask实例必须在主线程中创建
2. execute方法必须在主线中调用
3. 不能直接调用上图中除了onCancelled() 方法以外的其他方法
4. 一个AsyncTask对象只能执行一次，只调用一次 execute()  方法，否则报运行时异常

![2](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/2-1625106875867.png)



##### 范例：

主布局文件：

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"  
    android:orientation="vertical"  
    tools:context=".MyActivity">  
    <TextView  
        android:id="@+id/txttitle" />  
    <!--设置一个进度条,并且设置为水平方向-->  
    <ProgressBar  
        android:layout_width="match_parent"
        android:id="@+id/pgbar"  
        style="?android:attr/progressBarStyleHorizontal"/>  
    <Button  
        android:id="@+id/btnupdate"  
        android:text="更新progressBar"/>  
</LinearLayout>
```

自定义MyAsncTask类，去获取进度，并更新UI：

```java
public class MyAsyncTask extends AsyncTask<Integer,Integer,String> {
    //用于更新UI的控件
    private TextView txt;
    private ProgressBar pgbar;

    //构造方法
    public MyAsyncTask(TextView txt,ProgressBar pgbar)
    {
        super();
        this.txt = txt;
        this.pgbar = pgbar;
    }

    //执行耗时操作，完成主要任务
    @Override
    protected String doInBackground(Integer... integers) {
        int i = 0;  //进度
        for (i = 10;i <= 100;i+=10)
        {
            try {
                Thread.sleep(500);    //延迟0.5秒，模拟下载
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(i); //AT的内部方法，发布当前进度
        }
        return  i + integers[0].intValue() + "";
    }

    //该方法运行在UI线程中,可对UI控件进行设置
    @Override
    protected void onPreExecute() {
        txt.setText("开始执行异步线程");
    }

    //在doBackground方法中,每次调用publishProgress方法都会触发该方法 运行在UI线程中,可对UI控件进行操作
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        pgbar.setProgress(values[0]);  //更新进度位置
    }

    @Override
    protected void onCancelled() {
        txt.setText("取消了下载");
        pgbar.setProgress(0);
    }
}
```

MA：

```java
public class MyActivity extends ActionBarActivity {  
  
    private TextView txttitle;  
    private ProgressBar pgbar;  
    private Button btnupdate;  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);  
        //findView
        btnupdate.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  //直接新建对象，将需要更新的UI传入进去，然后开始任务
                MyAsyncTask myTask = new MyAsyncTask(txttitle,pgbar);  
                myTask.execute();  
            }  
        });  
    }  
}
```









### 二、 启动分类和生命周期

启动服务有两种方式：通过StartService( ) 和 BindService ( )，前者无法和组件进行交互数据、通信

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210121113654666.png" alt="image-20210121113654666" style="zoom: 50%;" />

每个服务的实例都只会存在一个，在项目的任何位置调用了startService() 方法，就会启动相应的服务，并回调onStartCommand() 方法。如果这个服务还没有被创建过，onCreate() 方法就会先执行。服务启动后一直保持运行状态，直到 stopService() 或 stopSelf() 方法被调用。

注意：每调用一次startService（）方法，就会执行一次onStartCommand（），但只需要调用一次stopService（）或stopSelf（）方法，就会停止。

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210121143727827.png" alt="image-20210121143727827" style="zoom: 50%;" />



bindService( ) 方法可以获取一个服务的持久连接，这时会回调onBind() 方法，同理，如果这个服务之前没被创建，onCreate( ) 方法会优先执行。unbindService( ) 方法被调用后，onDestory( ) 方法也会执行。

==注意：==

如果一个服务既调用了onCreate( ) ，又调用了onBind( ) 方法，那么需要调用StopService( ) 和unbindService( )方法，onDestroy( ) 方法才会执行。



### 三、创建和使用

四大组件相同的创建方式，这里就不演示了。

![image-20210121112624311](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210121112624311.png)

创建完成，manifest中自动注册和配置Service:

```xml
<service
    android:name=".MyService"
    android:enabled="true"
    android:exported="true"></service>
```









#### 3.1 服务和活动通信

可以在控制台观察，通过两种方式启动服务的生命周期变化

##### 1.创建服务

设置一个内部类，写入执行的逻辑功能方法，并实例化该对象，在onBind() 方法中返回这个实例化对象

```java
public class MyService extends Service {
    private DownloadBinder mBinder = new DownloadBinder();

    //内部类，模拟开始下载和查看进度的方法
    class DownloadBinder extends Binder {
        public void startDownload() {
            Log.d("MyService", "startDownload:executed");
        }

        public int getProgress() {
            Log.d("MyService", "getProgress:executed");
            return 0;
        }
    }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("Service", "已绑定");
        return mBinder; //和Binder绑定
    }

    //Service创建时调用
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Service", "已创建");
    }

    //Service被销毁时调用
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Service", "已销毁");
    }

    //Service启动时调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Service", "已启动");
        return super.onStartCommand(intent, flags, startId);
    }
}
```

##### 2.xml 和 MA

设定四个按钮，用于开启服务，关闭服务，绑定服务和解绑服务

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/activity_main"
    android:orientation="vertical">

    <Button
        android:id="@+id/start"
        android:text="开启服务" />

    <Button
        android:id="@+id/stop"
        android:text="关闭服务" />

    <Button
        android:id="@+id/bind_Service"
        android:text="绑定服务" />

    <Button
        android:id="@+id/unbind_Service"
        android:text="解绑服务" />
</LinearLayout>
```

调用服务中的匿名内部类对象，在ServiceConnection中建立连接，并调用一些方法

```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_start, btn_stop, bind_Service, unbind_Service;
    Intent intent;
    //申明Service中的内部类
    private MyService.DownloadBinder downloadBinder;    
    //ServiceConnection是一个接口，定义了和Service建立连接和取消连接的方法
    private ServiceConnection connection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (MyService.DownloadBinder) iBinder;//实例化服务的内部类对象
            //指挥服务去干什么事情
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //bindView 和 设置监听 等
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                intent = new Intent(MainActivity.this, MyService.class); 
                startService(intent); //开启服务
                break;
            case R.id.stop:
                intent = new Intent(MainActivity.this, MyService.class);  
                stopService(intent);    //结束服务
                break;
            case R.id.bind_Service:
                intent = new Intent(MainActivity.this, MyService.class);  
              //参数二是ServiceConnection实例，三是标志位，该值表示另一种启动活动的方式，即onCreate()执行，onStartCommand()不执行
                bindService(intent, connection, BIND_AUTO_CREATE);//绑定服务
                break;
            case R.id.unbind_Service:
                unbindService(connection);//解绑服务
                break;
            default:
                break;
        }
    }
}
```



#### 3.2 停止服务

服务让自己停下：在服务（类）的任意位置调用 stopSelf() 即可





### 四、前台服务

服务的优先级较低，系统内存不足时，就可能回收正在后台运行的服务，如果希望服务可以一直保持运行状态，就可以考虑使用前台服务。

前台服务会一直有一个正在运行的图标在系统的状态栏显示，下拉后能看到详细信息，类似于通知。



只需要在MyService类的onCreate（）方法中加入通知的创建即可：

```java
public void onCreate() {
    super.onCreate();
    Log.i("Service", "已创建");
    Intent intent=new Intent(this,MainActivity.class);
    PendingIntent pi=PendingIntent.getActivity(this,0,intent,0);
    Notification notification=new NotificationCompat.Builder(this)
            .setContentTitle("前台服务标题")
            .setContentText("前台服务内容")
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
            .setContentIntent(pi)
            .build();
    startForeground(1,notification);  //参数1是通知的ID
}
```





### 五、IntentService

服务中的代码都是默认运行在主线程中，如果直接在服务里处理一些耗时的逻辑，很容易出现ANR（Application Not Responding），如果总是用匿名内部类的多线程方式去处理，可能会忘记启动，或者关闭服务

IntentService能够自动开启线程，并且运行完毕后自动结束服务。



#### 简单实例：

创建 IntentService 和 创建 Service 的过程一样，最后选择IntentService即可。

创建好后删除自动添加的内容，重写以下两个方法和构造方法：

```java
public class MyIntentService extends IntentService {
    public MyIntentService() {
        super("MyintentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //这个方法中处理具体的逻辑，不用当心ANR问题
        Log.d("MyIS:", "当前线程的ID是"+Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyIS:", "已销毁");
    }
}
```

xml中一个按钮，MA中 findView 设置事件监听器：

```java
case R.id.intent_Service:
    Log.d("MA:", "主线程ID是"+Thread.currentThread().getId());
    Intent intentService=new Intent(this,MyIntentService.class);
    startService(intentService);
    break;
```



多次点击按钮后可以看到：IS每次都新建了一个线程，打印完当前线程ID后，自动销毁，主线程一直保持常量。







# 六、运用手机多媒体



## 一、使用通知

==MIN SDK>=16==，通知可以在活动、广播接收器、服务里创建

关于通知的详解：https://blog.csdn.net/shanshui911587154/article/details/105683683/

![4](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/4.png)

```
1.小图标：此为必要图标，通过 setSmallIcon() 设置。
2.应用名称：此由系统提供。
3.时间戳：此由系统提供，不过您可以通过 setWhen() 进行替换，或使用 setShowWhen(false) 将其隐藏。
4.大图标：此为可选图标（通常仅用于联系人照片；请勿将其用于应用图标），通过 setLargeIcon() 设置。
5.标题：此为可选内容，通过 setContentTitle() 设置。
6.文本：此为可选内容，通过 setContentText() 设置
```



```java
  	    //延迟Intent，用于点击通知后的跳转
        Intent intent = new Intent(this, Main2Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //获取 PI 的方法：根据需求选择 getAcitivity()、getBroadcast()、getService()。
        // 接收的参数均相同，1为context对象，2一般用不到，通常传入0，3是Intent对象，4确定PendiongIntent行为，通常传入0。
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        //构建通知渠道，因在不同版本的通知有相应的变化，可能在高版本出现加载不出的情况，所以需要该渠道
        String channelId = createNotificationChannel("my_channel_ID", "my_channel_NAME", NotificationManager.IMPORTANCE_HIGH);
        //创建通知的属性
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("通知")      //通知标题
                .setContentText("收到一条消息")        //通知标内容
                .setContentIntent(pendingIntent)         //点击通知跳转的目标
                .setSmallIcon(R.mipmap.ic_launcher)         //必要设置的小图标，大图标可选
                .setPriority(NotificationCompat.PRIORITY_HIGH)      //优先级
                .setAutoCancel(true)    //用户点击后自动移除，用户清除下拉框的所有消息也会把该通知清理掉，可以设置下一条属性避免
                .setOngoing(true);      //除非APP死掉||用户点击||代码中取消，否则都不会被清除
        //用notificationManager发送通知
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(100, notification.build());//参数1为 通知的ID


 	private String createNotificationChannel(String channelID, String channelNAME, int level) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {		//SDK>26
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(channelID, channelNAME, level);
            manager.createNotificationChannel(channel);
            return channelID;
        } else {
            return null;
        }
    }
```







## 二、画笔和画布

画笔能够设置不透明度、颜色、粗细等

画布能够设置画出的形状、画布的颜色、大小等



### 2.1 简单示范：

```java
public class MyView extends View {
    public MyView(Context context) {
        super(context);		//构造方法如果不含上下文会报错
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();//定义一个默认画笔
        paint.setColor(0xFFFF6600);  //设置画笔的颜色和不透明度，默认为完全透明
        paint.setStyle(Paint.Style.FILL); //填充方式
        canvas.drawRect(10,10,280,150,paint); //画一个矩形，参数为左上右下的坐标和画笔
    }
}
```

xml 中只有一个帧布局，MA：

```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout frameLayout= findViewById(R.id.framlayout);
        frameLayout.addView(new MyView(this));//帧布局中添加视图，myView的匿名对象
    }
```



### 2.2 绘制几何图形

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210118133044850.png" alt="image-20210118133044850" style="zoom:50%;" />



例如：绘制一个这样的机器人![image-20210118142222960](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210118142222960.png)

修改ondraw方法代码如下即可：

```java
@Override
protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    /** 绘制一个机器人**/
    Paint paint=new Paint();//定义一个默认画笔
    paint.setAntiAlias(true); //采用抗锯齿
    paint.setColor(0xFFFF6600);  //设置画笔的颜色和不透明度，默认为完全透明

    //绘制头部
    RectF rectF=new RectF(10,10,100,100); //定义外轮廓
    rectF.offset(90,20);//设置偏移量调整位置，具体为left+=90,top+=20,rihgt+=90,buttom+=20
    //参数：1指定弧形外轮廓的矩形区域 2为起始角度 3为圆弧扫过的角度 4为指定是否包括圆形在内（是，则绘制扇形）5为画笔
    canvas.drawArc(rectF,-10,-160,false,paint);//绘制圆弧
  

    //绘制眼睛
    paint.setColor(0xFFFFFFFF);//完全不透明的白色
    canvas.drawCircle(165,53,4,paint);//绘制圆
    //参数：1和2为圆点位置，3为半径
    canvas.drawCircle(125,53,4,paint);

    //绘制天线
    paint.setColor(0xFFFF6600);//改回橙色画笔
    paint.setStrokeWidth(2);//设置笔触的宽度
    canvas.drawLine(110,15,125,35,paint);//绘制直线
    //参数：1和2为起始坐标 3和4为终止坐标 5为画笔
    canvas.drawLine(180,15,165,35,paint);

    //绘制机器人身体
    canvas.drawRect(100,75,190,150,paint); //绘制矩形
    //绘制圆角矩形
    RectF rectF_body=new RectF(100,140,190,160);//定义矩形范围
    canvas.drawRoundRect(rectF_body,10,10,paint);//1为矩形范围，2/3分别为x/y轴上圆角的半径

    //绘制胳膊
    RectF rectF_arm=new RectF(75,75,95,140);
    canvas.drawRoundRect(rectF_arm,10,10,paint);
    rectF_arm.offset(120,0);
    canvas.drawRoundRect(rectF_arm,10,10,paint);

    //绘制腿
    RectF rectF_leg=new RectF(115,150,135,200);
    canvas.drawRoundRect(rectF_leg,10,10,paint);
    rectF_leg.offset(40,0);
    canvas.drawRoundRect(rectF_leg,10,10,paint);
}
```



### 2.3 绘制文字

```java
//绘制文字
paint.setTextAlign(Paint.Align.LEFT); //设置文字对齐方式
paint.setTextSize(40); //设置文字大小
canvas.drawText("这是个机器人",50,300,paint); //参数：1为文字信息，2/3为起始位置坐标
```





### 2.4 绘制图片

创建图片有两种方式，通过 Bitmap类或者 BitmapFactory类



Bitmap类创建位图的方式：

| 方法                 | 描述                           |
| -------------------- | ------------------------------ |
| createBitmap()       | 根据重载形式创建对应的位图对象 |
| compress()           | 压缩对象并保存文件到输出流     |
| createScaledBitmap() | 将原位图缩放并创建新的位图对象 |



BitmapFactory创建位图的方式：

| 方法             | 描述           |
| ---------------- | -------------- |
| decodeFile()     | 通过路径创建   |
| decodeResource() | 通过资源ID创建 |
| decodeStream()   | 通过输入流创建 |





Manifest中申请读取存储的权限

```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"></uses-permission>
```

可以加入动态申请权限部分，因第一次使用可能未开启权限，可能会出现报错，授权后重启即可



1.将指定图片使用DDMS导入SD卡（/sdcard目录，具体步骤见【第一章节，DDMS工具的使用】）

2.创建View子类，ondraw（）方法中使用Bitmap类创建位图图像

   MA中代码不变，仍为Framelayout加载View

```java
public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
		Paint paint=new Paint();//定义一个默认画笔
        
        String path= Environment.getExternalStorageDirectory()+"/Pictures/WeiXin/mmexport1625068521403.jpg";//设置图片路径
        // BitmapFactory根据路径创建位图对象
        Bitmap bitmap= BitmapFactory.decodeFile(path);
        canvas.drawBitmap(bitmap,0,0,paint);//绘制图片,参数2/3为左上角坐标点

        //使用 Bitmap类 截取一小块图像显示
        Bitmap bitmap1=Bitmap.createBitmap(bitmap,150,150,500,500);//参数分别为截取位置的左上角和右下角横纵坐标
        canvas.drawBitmap(bitmap1,0,bitmap.getHeight(),paint);//绘制图片，紧贴上图绘制，如果图片太长可能加载不完全
    }
}
```











### 2.5 绘制路径

绘制的路径不会显示出来，可以沿着该路径绘制一些图形、文字

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210118170459979.png" alt="image-20210118170459979" style="zoom:50%;" />

例如，绘制一个圆形路径，让文字按路径排列，xml 和 MA保持不变

```java
public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
		 //画笔设置
        Paint paint=new Paint();//定义一个默认画笔
        paint.setAntiAlias(true); //采用抗锯齿
        paint.setColor(0xFFFF6600);  //设置画笔的颜色和不透明度，默认为完全透明
        paint.setStyle(Paint.Style.STROKE);//设置填充样式为描边
        //路径设置
        Path path=new Path();
        //参数1/2为圆点坐标，3为半径，4为方向，CW为顺时针，CCW为逆时针
        path.addCircle(500,500,100, Path.Direction.CW);//创建圆形路径

        paint.setTextSize(50);
        canvas.drawTextOnPath("好好学习，天天向上",path,0,0,paint);//设置路径
        //如果为绘制圆形路径的话，则采用canvas.drawPath(path,paint);即可
    }
}
```



## 三、动画

### 3.6 逐帧动画

1./drawable下新建资源文件fairy.xml，并为每个item项设置一个图片资源文件

```xml
<animation-list xmlns:android="http://schemas.android.com/apk/res/android"
    android:oneshot="true">
    <item android:drawable="@drawable/image_1" android:duration="100"></item>
    <item android:drawable="@drawable/image_2" android:duration="100"></item>
    <item android:drawable="@drawable/image_3" android:duration="100"></item>
    <item android:drawable="@drawable/image_4" android:duration="100"></item>
    <item android:drawable="@drawable/image_5" android:duration="100"></item>
    <item android:drawable="@drawable/image_6" android:duration="100"></item>
    <item android:drawable="@drawable/image_7" android:duration="100"></item>
    <item android:drawable="@drawable/image_8" android:duration="100"></item>
    <item android:drawable="@drawable/image_9" android:duration="100"></item>
</animation-list>
```

2.MA：

```java
public class MainActivity extends AppCompatActivity {
    private boolean flag = true;//记录播放状态
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //用IV加载动画资源
        ImageView imageView= findViewById(R.id.imageView);
        imageView.setBackgroundResource(R.drawable.fairy);
        animationDrawable= (AnimationDrawable) imageView.getBackground();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){
                    animationDrawable.start();
                    flag=false;
                }else {
                    animationDrawable.stop();
                    flag=true;
                }
            }
        });
    }
}
```





### 3.7 补间动画

补间动画就是只需要开头帧和结尾帧，中间帧由系统自动形成



#### 3.7.1 透明度渐变动画

/res下新建目录anim，新建资源文件alpha.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <alpha 
           android:fromAlpha="0" 	//从什么透明度，取值0-1
           android:toAlpha="1" 
           android:duration="2000"  //持续时间></alpha>
</set>
```

主布局文件中用ImageView设置想要渐变的图片

```xml
<ImageView
    android:id="@+id/imageView"
    android:layout_width="200dp"
    android:layout_height="200dp"
    android:src="@drawable/image_1"
    android:layout_centerInParent="true"/>
```

MA中设置如下代码：

```java
ImageView imageView;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    imageView = findViewById(R.id.imageView);
    imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Animation animation= AnimationUtils.loadAnimation(MainActivity.this,R.anim.alpha); //创建动画对象
            imageView.startAnimation(animation);
        }
    });
}
```



#### 2.7.2 旋转动画：

只需要修改alpha.xml中的子项内容即可：

```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <rotate
        android:duration="2000"	//持续时间
        android:fromDegrees="0"	//开始角度
        android:pivotX="50%"	//旋转的X轴
        android:pivotY="50%"	//旋转的Y轴，50%为图片中心
        android:toDegrees="360"	//结束角度></rotate>
</set>
```





#### 2.7.3 缩放动画

```xml
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <scale android:fromXScale="1"	//起始X轴大小
         android:fromYScale="1"	
        android:toXScale="2"	//终止时X大小
        android:toYScale="2"
        android:pivotX="50%"	//缩放的X轴
        android:pivotY="50%"
        android:duration="2000"></scale>
</set>
```



#### 2.7.4 平移动画

```xml
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <translate
        android:duration="2000"
        android:fromXDelta="0"	//初始坐标
        android:fromYDelta="0"
        android:toXDelta="300"	//结束坐标
        android:toYDelta="300"></translate>
</set>
```



### 2.8 属性动画

通过设置一些属性，达到动画的效果

这里是一个从0到1，透明度逐渐上升的变化：

```java
public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        //值从0到1000改变的动画
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 1000);
        valueAnimator.setDuration(4000);
        //设置动画的监听器
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                textView.setText(String.valueOf(value));
            }
        });
        valueAnimator.start();

        //对象属性改变动画，这里是透明度的渐变动画
        //参数分别为对应的控件，控件的某项属性，属性对应的变化值
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(textView, "alpha", 0f, 1f);
        objectAnimator.setDuration(4000);
        
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override//动画被取消时调用
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override//动画结束时调用
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Toast.makeText(MainActivity.this, "计时结束", Toast.LENGTH_SHORT).show();
            }

            @Override//动画重复时调用
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override//动画开始时调用
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                Toast.makeText(MainActivity.this, "开始计时", Toast.LENGTH_SHORT).show();
            }
        });
        objectAnimator.start();
    }
}
```







## 三、音频播放



### 3.1 MediaPlayer 播放音频

MediaPlayer 是 Android 提供的用来控制音频/视频文件的类，播放视频需要配合SurfaceView使用

支持的音频格式：mp3 、 ogg（完全免费）、  3gp（文件体积小，移动性强）、  wav（高音质）

| 常用构造方法                    | 描述                                                         |
| ------------------------------- | ------------------------------------------------------------ |
| mediaPlayer.create(context,uri) | 通过uri创建出该对象，并加载uri对应的音频文件                 |
| mediaPlayer.create(context,int) | 通过AS中的ID创建该对象，并加载对应音频文件                   |
| 下面两个配合使用：              |                                                              |
| mediaPlayer.setDataSource()     | 通过无参构造出MP对象，并设置音频文件源，可以连续播放多个音频文件 |
| mediaPlayer.prepare()           | 音频播放前的准备                                             |

![image-20210124134448593](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210124134448593.png)



1./res目录下新建raw文件，复制多媒体文件进来

注意：复制进来时要剪掉后缀格式名，文件不要带大写字母，不要以数字开头。运行程序时会出现选择文件格式，选择第二项 Open matching files in associated application 



 2.主布局文件下新建三个按钮，用于暂停，播放和停止，MA：

```java
public class MainActivity extends AppCompatActivity {
	MediaPlayer mediaPlayer;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer=MediaPlayer.create(this,R.raw.media01);//实例化多媒体资源播放的类
        //findView

        btn_paly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
            }
        });

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
            }
        });
    }
}
```





### 3.2 SoundPool 播放音效

SoundPool（音效池）：管理多个短促的音效，无法播放时间较长的音乐



例题：

1.新建/raw文件夹，将音频文件保存到该目录下

2.主布局界面中写入一个listview，MA代码如下：

```java
public class MainActivity extends AppCompatActivity {

    HashMap<Integer,Integer> soudmap;//创建hashmap保存音频
    SoundPool soundPool;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] title = new String[]{"布谷鸟叫声", "风铃声", "门铃声", "电话声", "鸟叫声","水流声", "公鸡叫声"};  // 定义并初始化保存列表项文字的数组
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,title);  //实例化一个适配器对象
        ListView listView= findViewById(listview);
        listView.setAdapter(adapter);   //将适配器和listview控件匹配

        //设置音频相关属性
        AudioAttributes attr=new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)  //设置音效的使用场景
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC) //设置音效类型
                .build();
        //创建soudPool对象
        soundPool=new SoundPool.Builder()
                .setAudioAttributes(attr)   //设置音效池的属性
                .setMaxStreams(10)  //设置最多可容纳的音频流
                .build();

        soudmap=new HashMap<>(); 
        soudmap.put(0,soundPool.load(this,R.raw.cuckoo,1));  //参数3是优先权
        soudmap.put(1,soundPool.load(this,R.raw.chimes,1));
        soudmap.put(2,soundPool.load(this,R.raw.notify,1));
        soudmap.put(3,soundPool.load(this,R.raw.ringout,1));
        soudmap.put(4,soundPool.load(this,R.raw.bird,1));
        soudmap.put(5,soundPool.load(this,R.raw.water,1));
        soudmap.put(6,soundPool.load(this,R.raw.cock,1));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                soundPool.play(soudmap.get(position),1,1,0,0,1);    //根据所选的项播放音频文件
                //参数：2/3为左右声道，4为播放的优先级，5为循环次数，6为速率
            }
        });
    }
}
```



## 四、视频播放

视频格式：mp4 （分辨率高，大小适中）、3gp（体积小、移动性强）

### 4.1 VideoView播放视频

一般都使用MC类，他能直接加载出页面的控制条，方便对视频进行操作：

<img src="Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210120131550884.png" alt="image-20210120131550884" style="zoom:33%;" />



![image-20210124135209606](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210124135209606.png)

SD卡中导入多媒体文件，在manifest中申明权限，动态申请或设置中开启权限

```xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"></uses-permission>
```

xml仅有一个VideoView，MA：

```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VideoView videoView= findViewById(R.id.videoview);
        //加载要播放的视频
        File file=new File(Environment.getExternalStorageDirectory()+"/videa.mp4");//获取文件
        if (file.exists()){
            videoView.setVideoPath(file.getAbsolutePath());
        }else {
            Toast.makeText(this, "要播放的文件不存在", Toast.LENGTH_SHORT).show();
        }

        MediaController mediaController=new MediaController(this);
        videoView.setMediaController(mediaController);//让VV关联MC
        videoView.requestFocus();//获取焦点
        videoView.start();
    }
}
```





### 4.2 MediaPlayer+SurfaceView播放视频

1.SD卡中导入多媒体文件

2.manifest设置权限，动态申请或设置中开启存储权限

3.xml 和 MA：

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    tools:context=".MainActivity">
    <!--SurfaceView组件-->
    <SurfaceView
        android:id="@+id/surfaceView"
        android:layout_weight="10" />
    <!--水平线性布局-->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_weight="1"
        android:orientation="horizontal">
        <!--播放按钮-->
        <Button
            android:id="@+id/play"
            android:layout_weight="1"
            android:text="播放"
            />
        <!--暂停按钮-->
        <Button
            android:id="@+id/pause"
            android:layout_weight="1"
            android:text="暂停" />
        <!--停止按钮-->
        <Button
            android:id="@+id/stop"
            android:layout_weight="1"
            android:text="停止" />
    </LinearLayout>
</LinearLayout>
```

```java
public class MainActivity extends Activity {
    private Button play, pause, stop;    
    private MediaPlayer mediaPlayer;      
    private SurfaceHolder surfaceHolder;    
    private boolean noPlay = true;     //定义播放状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //findView
        surfaceHolder = surfaceView.getHolder();   //获取SurfaceHolder
        //视频未播放时设置暂停和停止按钮不可用
        pause.setEnabled(false);      
        stop.setEnabled(false);                                 
	    mediaPlayer = new MediaPlayer();                               
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);    //设置多媒体的类型

		//实现播放与继续播放功能
        play.setOnClickListener(new View.OnClickListener() {          
            @Override
            public void onClick(View v) {
                if (noPlay) {                   
                    play();  //自定义方法，在该方法中实现视频的播放功能                   
                    noPlay = false;            
                } else {
                    mediaPlayer.start();      
                }
            }
        });

        //实现暂停功能
        pause.setOnClickListener(new View.OnClickListener() {  
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();       
                }
            }
        });

        //实现停止功能
        stop.setOnClickListener(new View.OnClickListener() {  
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {  
                    mediaPlayer.stop();          
                    noPlay = true;               
                    pause.setEnabled(false);     
                    stop.setEnabled(false);     
                }
            }
        });
        // 为MediaPlayer对象添加完成事件监听器
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(MainActivity.this, "视频播放完毕！", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void play() {  
        mediaPlayer.reset();                        //重置MediaPlayer
        mediaPlayer.setDisplay(surfaceHolder);    //把视频画面输出到SurfaceView
        try {
            //加载模拟器的SD卡上的视频文件
            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory() + "/videa.mp4");
            mediaPlayer.prepare();    // 预加载
        } catch (Exception e) {       
            e.printStackTrace();
        }
        mediaPlayer.start(); 
        pause.setEnabled(true);   
        stop.setEnabled(true);    
    }

    //当前Activity销毁时，停止正在播放的视频，并释放MediaPlayer所占用的资源
    @Override
    protected void onDestroy() {  
        super.onDestroy();
        if (mediaPlayer != null) {         
            if (mediaPlayer.isPlaying()) {  
                mediaPlayer.stop();        
            }
            // Activity销毁时停止播放，释放资源。不做这个操作，即使退出还是能听到视频播放的声音
            mediaPlayer.release();
        }
    }
}
```







## 五、控制摄像头拍照

权限：

```xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
```



```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical">

    <Button
        android:id="@+id/take_photo"
        android:text="拍照" />

    <Button
        android:id="@+id/choose_from_album"
        android:text="从相册选择照片" />

    <ImageView
        android:id="@+id/picture"
        android:layout_gravity="center_horizontal" />
</LinearLayout>
```

```java
public class MainActivity extends AppCompatActivity {
    //判断标志位
    public static final int CHOOSE_PHOTO = 2;	
    public static final int Take_Photo = 1;		
    private ImageView picture;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //findView
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImage = new File(getExternalCacheDir(), "output_image.jpg"); //设置sd卡的保存路径和图片名称
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //将File对象转换成URI对象
                imageUri = Uri.fromFile(outputImage);
              
          			//android 7.0以上会出现android.os.FileUriExposedException：
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                    builder.detectFileUriExposure();
                }

                //启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");//隐式intent启动该对应的活动打开
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//指定图片的输出地址
                startActivityForResult(intent, Take_Photo);
            }
        });

        //单击打开相册按钮时动态申请权限，如果有权限则打开相册
        chooseFromPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
            }
        });
    }

    private void openAlbum() {
      Intent intentFromGallery = new Intent(Intent.ACTION_PICK, null);
intentFromGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivityForResult(intentFromGallery, CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "你没有授权", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    //拍完照的结果会返回到这个方法中
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Take_Photo:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));//拍照成功会将照片解析成Bitmap对象
                        picture.setImageBitmap(bitmap);//imageView显示位图对象
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode==RESULT_OK){
                    handleImageOnKitKat(data);  //处理选择照片后的结果，4.4以上选择相册的图片不在返回真实URI，而是封装的URI，需要解析
                }
            default:
                break;
        }
    }

    //根据不同类型的URI解析图片
    private void handleImageOnKitKat(Intent data) {
        String imagePath=null;
        Uri uri=data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            //如果是Document类型的Uri，通过document id处理
            String docId=DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docId.split(":")[1];
                String selection=MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.donloads.ducuments".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath=getImagePath(contentUri,null);
            }else if ("content".equalsIgnoreCase(uri.getScheme())){
                imagePath=getImagePath(uri,null);
            }else if ("file".equalsIgnoreCase(uri.getScheme())){
                imagePath=uri.getPath();
            }
            displayImage(imagePath);//根据图片路径显示图片
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path=null;
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath!=null){
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        }else {
            Toast.makeText(this, "打开图片失败", Toast.LENGTH_SHORT).show();
        }
    }
}

```



### 图片裁剪：

```java
  /**
     * 拍照完成裁剪图片
     *
     * @param uri 图片的URI
     */
    private void clipPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 以下这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        File unCompressImg = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), System.currentTimeMillis() + ".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(unCompressImg));
        startActivityForResult(intent, RequstCodeConstants.REQUEST_CODE_CLIP_PHOTO);
    }

	//别忘了返回的处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RequstCodeConstants.REQUEST_CODE_CLIP_PHOTO:   //裁剪图片的处理
                if (resultCode == RESULT_OK) {
                   
                }
                break;
            default:
                break;
        }
    }
```







# 七、网络编程

网络协议和具体的协议部分这里不做过多阐述，JAVAWeb知识不足的建议先补Web的知识点。



Android SDK>27时，直接请求HTTP协议会报错：

![image-20210323161045289](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/image-20210323161045289.png)

解决办法：在Manifest中配置

```xml
android:usesCleartextTraffic="true"
```

本章所有项目都需要用到网络权限，先在manifest中申明，不是危险权限，不需要授权

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```





## 一、常见网络知识

### 1.1 get和post请求对比

```
GET：在请求的URL地址后以?的形式携带参数，多个数据之间以&进行分隔， 但数据容量通常不能超过2K
POST: 请求体中保存了向服务器发送的数据，传输没有数量限制

注意：两者都是发送数据的，只是发送机制不一样，GET安全性非常低，执行效率却比Post方法好，Post安全性较高。
一般查询的时候我们用GET，数据增删改的时候用POST。
```



### 1.2 常见状态码

```
1xx: 成功接受请求，正在处理请求
2xx: OK，客户端请求成功
3xx：重定向，请求资源已移到新的地址
4xx: 服务器无法处理请求，请求路径错误
5xx：服务器错误
```



传统的Client 和 HttpUrlConnection的方式现在都比较少见了，感兴趣的可以了解下，这里直接介绍主流的OkHttp



## 二、WebView

在应用程序里展示一些网页，但又不允许打开浏览器，这时就可以用WebView在程序里嵌入浏览器，展示网页内容

WebView的常用方法：

| 方法                             | 描述                       |
| -------------------------------- | -------------------------- |
| loadUrl(data,MIME Type,encoding) | 加载指定URL对应的网页      |
| loadData()                       | 将指定字符串加载到浏览器中 |
| loadDataWithBaseURL()            | 基于URL加载指定的数据      |
| goBack()                         | 返回上一个网页             |
| goForward()                      | 执行前进操作               |
| stopLoading()                    | 停止加载当前页面           |
| reload()                         | 刷新页面                   |



```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
  >

    <WebView
        android:id="@+id/web_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</LinearLayout>
```

```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView= findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);   //能够使WV支持JavaSript脚本
        webView.getSettings().setDomStorageEnabled(true);   //能够加载Dom树
        webView.setWebViewClient(new WebViewClient());  //当一个网页跳转到另一个网页时，目标网页仍然在当前WV中显示，不打开浏览器
        webView.loadUrl("http://www.baidu.com");    //访问URL路径
    }
}
```





## 三、OkHttp

Square公司开发的处理网络请求的开源项目，在接口封装上做的简单易用，是目前Android使用最广泛的网络框架，主页:https://github.com/square/okhttp



使用该协议前，需要==添加依赖库==；

```java
implementation 'com.squareup.okhttp3:okhttp:3.9.0'
```

添加这个库时会自动下载两个库：一个是OkHttp库，一个是Okio库，后者是前者的通信基础。



#### 3.1 使用步骤：

1. 创建OkHttpClient（不管是异步还是同步），建议全局只有一个

```java
 //方式一：
OkhttpClient client=new OkHttpClient();
//方式二
OkHttpClient client = new OkHttpClient.Builder().build();	//先Builder()构建出 builder对象，然后build()构建client对象
//（链式可选配置）
OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)   //连接超时时间
                .writeTimeout(10, TimeUnit.SECONDS)     //数据发送到服务端超时时间
                .readTimeout(30, TimeUnit.SECONDS)      //从服务端下载数据到本地超时时间
                .build();
```

```java
2. 然后使用Request的内部类Builder来创建一个Request对象，并设置网址
   Request对象包含：URL、method（请求方式）、headers（请求头）、body（请求体）

3. 调用newCall的newCall方法传入request，构建出call对象
 
4. 调用call.execute（同步）或者call.enqueue（异步）来执行这个请求，请求后返回response对象
	Respnse对象包含：code（状态码）、headers（响应头）、body（响应体）
 
5. 调用response的isSuccessful方法来判断是否请求成功
 
6. 调用response.body().string()方法来获取一个字符串结果		//注意，别用toString
   通过response.body()的其他方法拿到bytes数组，输入流等信息。拿到了输入流或者bytes数组我们可以用来手动解码一张图片，或者下载一个文件。
```





#### 3.2 请求演示：

xml中有四个按钮，分别代表get/post方式的同步/异步请求，MA使用http://www.httpbin.org/作为服务器端测试：

后来新加入了一个TV，显示响应体内容，只修改了POST异步请求的代码部分：

```java
public class MainActivity extends AppCompatActivity {
    public final String TAG="MainActivity";
    private OkHttpClient okHttpClient;		//建议全局只有一个

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        okHttpClient=new OkHttpClient();
    }

    //GET同步请求，需要开启新线程
    public void getSync(View view) {
        new Thread(){
            @Override
            public void run() {
                //得到URL构建出的http请求对象
                Request request=new Request.Builder().url("https://www.httpbin.org/get?a=1&b=2").build(); 
                Call call = okHttpClient.newCall(request);  //构建call对象
                try {
                    Response response = call.execute(); //执行，可能会被阻塞（网络问题等），执行完此方法后才会继续执行
                    Log.i(TAG, response.body().string()); //如果服务器给的是二进制的数据，可以使用byteStream读取
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //GET异步请求
    public void getAsync(View view) {
        Request request=new Request.Builder().url("https://www.httpbin.org/get?a=1&b=2").build(); //得到URL构建出的http请求对象
        Call call = okHttpClient.newCall(request);  //构建call对象
        //异步请求不需要开启新的线程，使用enqueue队列，内部会自动创建子线程，不会阻塞后续代码的执行
        call.enqueue(new Callback() {
            //成功响应，即使404、500等错误也是返回到该方法
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    Log.i(TAG, response.body().string());
                }
            }
			//只有连接服务器失败，比如服务器地址不存在，服务器没开启等情况才会返回该方法
            @Override
            public void onFailure(Call call, IOException e) {
            }
        });
    }

    //POST同步请求
    public void postSync(View view) {
        new Thread(){
            @Override
            public void run() {
                //因为post请求参数在body体中，所以需要先构建该对象传参
                FormBody formBody = new FormBody.Builder().add("a", "1").add("b", "2").build();
                Request request = new Request.Builder().url("https://www.httpbin.org/post").post(formBody).build();
                Call call = okHttpClient.newCall(request);
                try {
                    Response response = call.execute(); 
                    Log.i(TAG, response.body().string()); 
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

   //POST异步请求
    public void postASync(View view) {
        FormBody formBody = new FormBody.Builder().add("a", "1").add("b", "2").build();
        Request request = new Request.Builder().url("https://www.httpbin.org/post").post(formBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.d(TAG, "成功");
                    final String content = response.body().string();
                    //更新UI，显示响应体内容。使用UiThread前提：context是Activity
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextView.setText(content);
                        }
                    });
                } else {
                    Log.d(TAG, "响应失败");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "无法连接服务器");
            }
        });
    }
}
```









#### 3.3 文件提交：

提交SD卡中的一张图片，提交的数据放在请求体中。但网络协议没规定数据使用什么媒体格式类型（简称MIME类型），需要在RequestBody的参数中申明对象类型，常用的MIME类型：https://www.runoob.com/http/http-content-type.html



XML为一个按钮，对应uploadFileTest点击事件，MA：

```java
public class MainActivity extends AppCompatActivity {
    private OkHttpClient okHttpClient;        //建议全局只有一个
    public String TAG = "MainActivity";
    //要提交的文件
    File file = new File(Environment.getExternalStorageDirectory()+"/Pictures/Screenshots/Screenshot_20210616-001004__01.jpg");
    //申明MIME类型，要和服务端协定，不然服务器不知道怎么取该类型的数据
    public static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpeg; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        okHttpClient = new OkHttpClient();

        //动态授权，读取SD卡权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    public void uploadFileTest (View view) {
        //根据File创建一个一个请求体
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JPG,file);
        Request request = new Request.Builder()
                .url("https://www.httpbin.org/post")
                .post(requestBody)
                .build();

        //下面的操作相同
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    Log.d(TAG, "上传成功");
                    Log.d(TAG, response.body().string());
                }else {
                    Log.d(TAG, "上传失败");
                }
            }
        });
    }
}
```



#### 3.4 文件下载：

```java
public class DownloadUtil {
    private static DownloadUtil downloadUtil;
    private OkHttpClient okHttpClient;

    public static DownloadUtil get() {
        if (downloadUtil == null) {
            downloadUtil = new DownloadUtil();
        }
        return downloadUtil;
    }

    private DownloadUtil() {
        okHttpClient = new OkHttpClient();
    }

    //内部类的接口，监听下载
    public interface OnDownloadListener {
        //下载成功的回调
        void onDownloadSuccess();
        //下载进度的回调
        void onDownloading(int progress);
        //下载失败的回调
        void onDownloadFailed();
    }
    /*
     * @param url      下载链接
     * @param saveDir  储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    public void download(final String url, final String saveDir, final OnDownloadListener listener) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败，调用接口的失败回调
                listener.onDownloadFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 获取下载文件的绝对路径
                String savePath = isExistDir(saveDir);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();//获取总文件大小
                    File file = new File(savePath, getNameFromUrl(url));//创建文件
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中
                        listener.onDownloading(progress);//持续返回下载进度
                    }
                    fos.flush();
                    // 下载完成
                    listener.onDownloadSuccess();
                } catch (Exception e) {
                    listener.onDownloadFailed();
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    /**
     * @param saveDir
     * @return SD卡下载的绝对路径
     * @throws IOException 判断下载目录是否存在
     */
    private String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    /**
     * @param url
     * @return 从下载链接中解析出文件名
     */
    @NonNull
    public static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
```

```java
	//下载方法
    public void download(View view){
        String url="https://public-cn-northwest-1-1251058331.s3.cn-northwest-1.amazonaws.com.cn/download/product/common/app/keytoolplus/android/release/keytoolplus.apk";
        String downloadPath="/download/ducument/";
        DownloadUtil.get().download(url, downloadPath, new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                //成功
                Log.i(TAG,"下载成功");
            }

            @Override
            public void onDownloading(int progress) {
                //进度
                Log.i(TAG,progress+"%");
            }

            @Override
            public void onDownloadFailed() {
                //失败
                Log.i(TAG,"下载失败");
            }
        });
    }
```



#### 3.5 其他设置：

##### 1.拦截器

可以将请求拦截,执行一些操作

比如很多个请求，给每一个分别添加请求头内容过于麻烦，可以直接在拦截器中添加，需要添加请求头方法的直接调用该方法。

采用了【请求演示】中的POST异步请求方式，界面相同：

```java
 public class MainActivity extends AppCompatActivity {
    private OkHttpClient okHttpClient;        //建议全局只有一个
    public String TAG = "MainActivity";
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        okHttpClient = new OkHttpClient();	//屏蔽该行代码，在拦截器中创建OkHttpClient
        mTextView=findViewById(R.id.textview);
    }

    //POST异步请求，只添加了第一行代码
    public void postASync(View view){
        AddInterceptor();  //添加拦截器
        FormBody formBody = new FormBody.Builder().add("a", "1").add("b", "2").build();
        Request request = new Request.Builder().url("https://www.httpbin.org/post").post(formBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.d(TAG, "成功");
                    final String content = response.body().string();
                    //更新UI，显示响应体内容。使用UiThread前提：context是Activity
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextView.setText(content);
                        }
                    });
                } else {
                    Log.d(TAG, "响应失败");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "无法连接服务器");
            }
        });
    }

    public void AddInterceptor(){
        //通过Buidler方法创建对象，添加拦截器，主要拦截器有两种，第一次拦截
        okHttpClient=new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //通过chain.request()获取到这个请求对象,然后执行一些操作,比如添加请求头内容
                Request request = chain.request().newBuilder().addHeader("os", "android").addHeader("version", "1.0").build();
                //用chain将请求头加工，作为“响应”返回，此处的响应是伪响应
                Response response = chain.proceed(request);
                return response;
            }
        }).addNetworkInterceptor(new Interceptor() {		//第二次拦截
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                //获取请求头的version，可以进行二次更改，如获取到值也证明了该拦截在上面的拦截之后进行
                Log.d(TAG, request.header("version"));
                Response response = chain.proceed(request); 
                return response;
            }
        }).build();
    }
}
```



##### 2.缓存

当我们第一次发起请求后，如果后续还需要进行相同的请求，如果符合缓存的规则，则可以减少与服务器的通信，直接从本地文件缓存中读取响应返回给请求者。

```java
OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(new Cache(new File("/path/cache"),1024 * 1024))	//缓存文件的地址和缓存大小
                .build()
```



##### 3.Cookie

Cookie是某些网站为了辨别用户身份，进行会话跟踪(比如确定登陆状态等)，而存储在用户本地终端上的数据，由用户客户端计算机暂时或永久保存的信息

```java
public class CookieUnitTest {
    Map<String, List<Cookie>> cookies = new HashMap<>();

    @Test
    public void cookieTest() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
                        cookies.put(httpUrl.host(), list);
                    }

                    @NotNull
                    @Override
                    public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
                        List<Cookie> cookies = CookieUnitTest.this.cookies.get(httpUrl.host());
                        return cookies == null ? new ArrayList<>() : cookies;
                    }
                }).build();
        FormBody formBody = new FormBody.Builder().add("username", "lanceedu").add("password", "123123").build();
        Request request = new Request.Builder().url("https://www.wanandroid.com/user/login").post(formBody).build();
        // 构建Call对象
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        request = new Request.Builder().url("https://www.wanandroid.com/lg/collect/list/0/json").build();
        // 准备好请求的Call对象
        call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
```







## 四、解析数据格式

每个访问网络的应用程序都会有一个自己的服务器，可以向服务器提交数据，也可以从服务器上获取数据。这些数据一般都是格式化后的数据，便于按相同的规格来解析。常用的格式：XML 和 JSON



### 解析XML

#### 1. SAX

逐行扫描XML文档，遇到标签时触发解析处理器，做出相应处理动作，直至文档结束。每次解析一种XML都需要编写合适的类，且无法对数据增删改，解析是同步的，读到哪处理到哪。能够解析超大的XML文件，内存占用少适合移动端



SAX解析需要编写一个类 ，并继承DefaultHandler 类，并重写父类的五个方法：

| 方法名                                                       | 描述                                       |
| ------------------------------------------------------------ | ------------------------------------------ |
| startDocument ( )                                            | 读到文档开始标志时触发，通常完成初始化操作 |
| startElment (String uri , String local name, String qName,Attributes attributes) | 解析某个节点时触发，参数：1：              |
| characters (char[] ch , int start ,int length )              | 获取节点中的内容时执行                     |
| endElment (uri , local name, qName)                          | 完成解析某个节点时触发                     |
| endDocument ( )                                              | 文档结束时触发，完成一些善后工作           |





1.创建和XML属性相同的实体类：

```java
public class Person {
    private int id;
    private String name;
    private int age;
    //省略get、set、toString，无参和三参构造
}
```

2. /main/assets下新建person.xml文件，模拟从服务器端获取的XML文件：

```xml
<persons>
    <person id = "11">
        <name>张三</name>
        <age>18</age>
    </person>
    <person id = "13">
        <name>李四</name>
        <age>43</age>
    </person>
</persons>
```

3. 编辑SaxHelper类，用于解析XML

```java
public class SaxHelper extends DefaultHandler {
    private Person person;
    private ArrayList<Person> persons;
    //当前解析的元素标签
    private String tagName = null;

    //读取到文档开始标志触发，完成初始化操作
    @Override
    public void startDocument() {
        this.persons = new ArrayList<>();//初始化perosons数组
        Log.i("SAX", "读取到文档头,开始解析xml");
    }

    //读到开始标签时调用,参数2：标签名,4：标签携带的属性数组
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (localName.equals("person")) {   //如果标签名是person
            person = new Person();
            //取得person标签下的id属性并赋值给实例对象
            person.setId(Integer.parseInt(attributes.getValue("id")));
            Log.i("SAX", "开始处理person元素");
        }
        this.tagName = localName;//标记当前处理标签
    }

    //每次读到内容标签时调用,参数1：字符串内容,2/3：起始位置与长度
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //剔除无效标签
        if (this.tagName != null) {
            //读取标签中的内容
            String data = new String(ch, start, length);
            //依次判断标签内容和哪项符合，并赋值给对应的实例对象
            if (this.tagName.equals("name")) {
                this.person.setName(data);
                Log.i("SAX", "处理name元素内容");
            } else if (this.tagName.equals("age")) {
                this.person.setAge(Integer.parseInt(data));
                Log.i("SAX", "处理age元素内容");
            }
        }
    }

    // 读到开始标签对应的结束标签时触发
    @Override
    public void endElement(String uri, String localName, String qName) {
        if (localName.equals("person")) {       //读到/person标签时
            //将person对象添加至数组，并清空person对象（为下个person标签做准备）
            this.persons.add(person);
            person = null;
            Log.i("SAX", "处理person元素结束");
        }
        this.tagName = null;//释放当前所读标签
    }

    //读取到文档结尾时触发
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        Log.i("SAX", "读取到文档尾,xml解析结束");
    }

    //获取persons集合
    public ArrayList<Person> getPersons() {
        return persons;
    }
}
```

MA：

```java
  private ArrayList<Person> readxmlForSAX() throws Exception {
        //获取文件资源，建立输入流对象
        InputStream inputStream = getAssets().open("person1.xml");
        //实例化Sax解析类对象
        SaxHelper saxHelper = new SaxHelper();
        //得到SAX解析工厂实例
        SAXParserFactory factory = SAXParserFactory.newInstance();
        //通过工厂创建SAX解析器
        SAXParser saxParser = factory.newSAXParser();
        //将实例化的解析对象和需要被解析的流文件关联
        saxParser.parse(inputStream, saxHelper);
        inputStream.close();//关闭流
        return saxHelper.getPersons();//解析完成，返回数组对象
    }

//调用
ArrayList<Person> people = readxmlForSAX();
System.out.println(people);
```



#### 2. DOM解析

将所有内容以DOM树的方式加载到内存，通过节点之间的关系解析XML文件。编写简单，很消耗内存，不适合解析大型XML文件。

```java
public class DomHelper {
    private static final String TAG="DomHelper:";
    public static ArrayList<Person> queryXML(Context context) {
        ArrayList<Person> Persons = new ArrayList<Person>();
        try {
            //获得DOM解析器的工厂实例
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            //从Dom工厂获得dom解析器
            DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
            //把要解析的xml文件读入Dom解析器
            Document doc = documentBuilder.parse(context.getAssets().open("person1.xml"));
            //得到文档中名称为person的元素的结点列表
            NodeList nList = doc.getElementsByTagName("person");
            // nList.getLength()为列表的节点数,显示每个节点的元素以及子元素的名字
            for (int i = 0; i < nList.getLength(); i++) {
                //先从Person元素开始解析
                Element personElement = (Element) nList.item(i);//nList.item(i)返回第i个person项
                Person p = new Person();
                p.setId(Integer.valueOf(personElement.getAttribute("id")));

                //获取person节点下的子节点列表
                NodeList childNoList = personElement.getChildNodes();
                //遍历子节点列表，显示每个节点的元素以及子元素的名称
                for (int j = 0; j < childNoList.getLength(); j++) {
                    Node childNode = childNoList.item(j);
                    //判断子node类型是否可用
                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element childElement = (Element) childNode;
                        if ("name".equals(childElement.getNodeName()))  //如果节点名称是name，则设置name属性
                            p.setName(childElement.getFirstChild().getNodeValue());
                        else if ("age".equals(childElement.getNodeName()))//同理设置age属性
                            p.setAge(Integer.valueOf(childElement.getFirstChild().getNodeValue()));
                    }
                }
                Persons.add(p); //遍历完子节点后，将对象添加至集合
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Persons;
    }
}
```

MA：

```java
DomHelper ds = new DomHelper();
ArrayList<Person> persons = ds.queryXML(this);
Log.e(TAG, persons.toString());
```



#### 3. PULL解析

解析方式和SAX相似，代码实现也较为简单，适合移动端设备。Android系统内置了PULL解析器，也是最常用的。SharedPreference就是使用PULL解析。



```java
public class PullHelper {
    public static ArrayList<Person> getPersons(InputStream xml) throws Exception {
        ArrayList<Person> persons = null;
        Person person = null;
        // 创建一个xml解析的工厂实例对象
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        // 通过工厂对象获得xml解析类的引用
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(xml, "UTF-8");//设置解析编码类型
        // 获得首个元素的事件类型
        int eventType = parser.getEventType();
        //只要没解析到末尾元素
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT://解析的是文档开始，初始化操作
                    persons = new ArrayList<Person>();
                    break;
                case XmlPullParser.START_TAG: //开始解析起始标签
                    if ("person".equals(parser.getName())) {
                        //实例化对象，取值赋值
                        person = new Person();
                        int id = Integer.parseInt(parser.getAttributeValue(0));
                        person.setId(id);
                    } else if ("name".equals(parser.getName())) {
                        String name = parser.nextText();// 节点是文字，用parser.nextText()获取文字节点的内容
                        person.setName(name);
                    } else if ("age".equals(parser.getName())) {
                        int age = Integer.parseInt(parser.nextText());
                        person.setAge(age);
                    }
                    break;
                case XmlPullParser.END_TAG: //读到结束标签，添加对象到集合中，并重置对象
                    if ("person".equals(parser.getName())) {
                        persons.add(person);
                        person = null;
                    }
                    break;
            }
            eventType = parser.next(); //循环解析下一个元素
        }
        return persons;
    }
}
```

```java
 public void pull_XML(View view){
        try {
            //获取文件资源建立输入流对象
            InputStream is = getAssets().open("person1.xml");
            //将输出流交给PullHelper解析，得到persons数组
            ArrayList<Person> persons = PullHelper.getPersons(is);
            if (persons.equals(null)) {
                Toast.makeText(getApplicationContext(), "XML文档没有数据", Toast.LENGTH_SHORT).show();
            }
            for (Person p1 : persons) {
                Log.i(TAG, p1.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```



#### 4. 将对象保存为XML

```java
public class SavaXml {
    //利用序列化完成对象到XML文件的存储
    public static void save(List<Person> persons, OutputStream out) throws Exception {
        XmlSerializer serializer = Xml.newSerializer();
        serializer.setOutput(out, "UTF-8");
        serializer.startDocument("UTF-8", true);
        serializer.startTag(null, "persons");
        for (Person p : persons) {
            serializer.startTag(null, "person");
            serializer.attribute(null, "id", p.getId() + "");
            serializer.startTag(null, "name");
            serializer.text(p.getName());
            serializer.endTag(null, "name");
            serializer.startTag(null, "age");
            serializer.text(p.getAge() + "");
            serializer.endTag(null, "age");
            serializer.endTag(null, "person");
        }
        serializer.endTag(null, "persons");
        serializer.endDocument();
        out.flush();
        out.close();
    }
}
```

```java
  public void save(View view){
        Context context = getApplicationContext();
        List<Person> persons = new ArrayList<Person>();
        persons.add(new Person(21,"张三",18));
        persons.add(new Person(31,"李四",51));
        persons.add(new Person(11,"王五",32));
        //设置XML文件保存的位置
        File xmlFile = new File(context.getFilesDir(),"jay.xml");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(xmlFile);
            SavaXml.save(persons, fos);
            Toast.makeText(context, "文件写入完毕", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```







### 解析JSON

Json的体积更小，传输效率更高，解析更容易

Json数据由键值对构成，用引号引起来（单双均可），也可不用引号【为了方便解析，建议统一用双引号】

#### 1.JSON格式

Json 的 key、value 可取值：

```
1. 数字（整数或浮点数）
2. 字符串（在双引号中）
3. 逻辑值（true 或 false）
4. null
```

Json 的 value 常见形式：

```json
1. 普通  {"name":"zhangsan","age":"18"}
2. 数组（在方括号中）	{"persons":[{},{}]}   //也可以没有key，只有一个value数组
3. 对象（在花括号中），对象中又包含普通json {"address":{"province"："陕西"....}}
```

```json
  	//对象
    var person={"name":"张三","age":"15","gender":"true"};
    //没有key的数组
    var persons=[{"name":"张三","age":"15","gender":"true"},
        {"name":"李四","age":"16","gender":"true"},
        {"name":"王五","age":"17","gender":"false"}];

    //有key的数组
    var ps={"persons":[persons]};
    
	//对象中包含普通json
 	var address={"address":{"location":"北京","street":"上海路"}}

	//复杂嵌套，以上几种形式混合搭配，就不演示了，在实例中讲解
```



#### 2.FastJson



引入依赖库：

```java
implementation "com.alibaba:fastjson:1.1.68.android"
```



##### 2.1 解析普通Json

Json 格式：

```json
{
  "age": "20",
  "name": "张三",
  "sex": "男"
}
```

解析方法：（后面的案例中，将不在展示Json数据在代码中的定义，复制粘贴过去，定义变量即可）

```java
 String key_value = "{\"age\":\"20\",\"name\":\"张三\",\"sex\":\"男\"}";	//包含了转义字符，所以格式有差别

    public void handle_key_value_Json(View view) throws IOException {
        JSONObject object = JSON.parseObject(key_value);	//解析Json对象
        String name = object.getString("name");	//取得每个具体属性值
        String age = object.getString("age");
        String sex = object.getString("sex");
        Log.d(TAG, "name="+name+",age="+age+",sex="+sex);
    }
```



##### 2.2 解析数组Json

```json
[
  {
    "age": "20",
    "name": "张三",
    "sex": "男"
  },
  {
    "age": "22",
    "name": "小华",
    "sex": "女"
  }
]
```

```java
    public void handle_array_only_key_value_Json(View view) {
        //获取到数组对象的集合，并遍历
        JSONArray arrayObjects = JSON.parseArray(array_only_key_value);
        for (int i = 0; i < arrayObjects.size(); i++) {
            //对每一个数组中的对象，强转为JSONObject类型，再做同样处理
            JSONObject object = (JSONObject) arrayObjects.get(i);
            String name = object.getString("name");
            String age = object.getString("age");
            String sex = object.getString("sex");
            Log.d(TAG, "name="+name+",age="+age+",sex="+sex);
        }
    }
```



##### 2.3 解析复杂Json

```json
//result对应了一个数组，数组内包含两个对象，其他数据都是普通形式
{
  "result": [
    {
      "sex": "男",
      "age": "20",
      "name": "张三"
    },
    {
      "sex": "女",
      "age": "22",
      "name": "小华"
    }
  ],
  "school": "清华大学",
  "error": false
}
```

```java
  public void handle_muti_array_and_simple(View view){
        //先当做一个对象处理
        JSONObject object = JSON.parseObject(muti_array_and_simple);
        //拿到对象中的result数组，遍历取出数组中的每个对象，并取值
        JSONArray array = object.getJSONArray("result");
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            String name = jsonObject.getString("name");
            String age = jsonObject.getString("age");
            String sex = jsonObject.getString("sex");
            Log.d(TAG, "name="+name+",age="+age+",sex="+sex);
        }
        //再处理单独的对象
        String school = object.getString("school");
        Boolean error = object.getBoolean("error");
        Log.d(TAG, "school="+school+",error="+error);
    }
```



##### 2.4 JavaBean 转换成 Json

创建实体类

```java
public class Student {
    private String name;
    private int age;
    private String sex;
	//省略get、set、三参构造、无参构造
}
```

```java
 public void JavaBean_to_Json(View view) {
        Student student=new Student("张三",18,"男");
        String beanJson = JSON.toJSONString(student);
        Log.d(TAG, beanJson);
    }
```



##### 2.5 集合转Json

使用的方法一模一样，只是用了集合，这里使用map做演示，其他集合相同：

```java
 public void Map_to_Json(View view) {
        Map<String, Object> map = new HashMap<>();
        Student student=new Student("李四",22,"女");
        map.put("name","张三");
        map.put("age","25");
        map.put("sex","男");
        map.put("person",student);
        String jsonMap = JSON.toJSONString(map);
        Log.d(TAG, jsonMap);
    }
```













# 八、Material Design

2014年由谷歌设计师基于传统优秀的设计原则，结合丰富的创意和科学技术所发明的一套全新的界面设计语言，包含了视觉、运动、互动效果等特性。

导入依赖库，即可实现

```java
implementation 'com.android.support:design:24.2.1'		//第一行是Design Support库
implementation 'de.hdodenhof:circleimageview:2.1.0'	    //第二行是一个开源项目CircleIamgeView，实现图片圆形化的功能
```





## 一、DrawerLayout

它带有一个隐藏在侧面的布局，可以左滑展开，也可JAVA代码中设置点击按钮展开

```xml
<?xml version="1.0" encoding="utf-8"?>
<!--直接将根布局替换为DrawerLayout-->
<android.support.v4.widget.DrawerLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/drawer_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!-- FrameLayout包含主屏幕中的布局文件，可以用include引入对应布局-->
    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <TextView
            android:text="这是主布局文件"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"/>
    </FrameLayout>

    <!--必须指定layout_gravity的属性，start表示根据系统语言书写顺序判断，end表示逆序，不推荐使用left和right-->
    <!--宽高必须是match_parent，不然效果很差-->
    <TextView
        android:layout_gravity="start"		
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:text="菜单"
        android:textSize="30sp"
        android:background="#FFF"/>
</android.support.v4.widget.DrawerLayout>
```

这样即可实现一个简单的，左侧滑动展开的菜单





## 二、NavigationView

NavigationView专用于DrawerLayout的布局，它就是隐藏在侧面的布局。它分为上下两个部分，上面可以定义一些头像、信息等，下半部分是一些可以选择的菜单文件。



1.创建/res/menu/nav_menu.xml，为左边滑动后，下半部分展示的选项

```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <group android:checkableBehavior="single">
        <item
            android:id="@+id/nav_call"
            android:icon="@drawable/ic_course_bg_cheng"
            android:title="电话"/>
        <item
            android:id="@+id/nav_friends"
            android:icon="@drawable/ic_course_bg_fen"
            android:title="朋友"/>
        <item
            android:id="@+id/nav_location"
            android:icon="@drawable/ic_course_bg_pressed"
            android:title="位置"/>
    </group>
</menu>
```

3.创建布局文件header.xml，左滑后上半部分头像等设置

```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="180dp"
    android:background="?attr/colorPrimary"
    android:padding="10dp">

    <!--圆形处理工具，展示头像-->
    <de.hdodenhof.circleimageview.CircleImageView
        android:id="@+id/icon_image"
        android:layout_width="70dp"
        android:layout_height="70dp"
        android:layout_centerInParent="true"
        android:src="@mipmap/ic_launcher"/>

    <TextView
        android:id="@+id/mail"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:text="1521115997@qq.com"
        android:textColor="#FFF"
        android:textSize="14sp"/>

    <TextView
        android:id="@+id/username"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_above="@id/mail"
        android:text="张三"
        android:textColor="#FFF"
        android:textSize="14sp"/>
</RelativeLayout>
```

3.主布局文件中，引入NavigationView，并关联上下两部分布局

```xml
<android.support.v4.widget.DrawerLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/drawer_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <TextView
            android:text="这是主布局文件"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"/>
    </FrameLayout>

    <!--通过这个控件，单独掌握左侧布局，app:headerLayout关联上半部分布局，app:menu关联下半部分可选菜单-->
    <android.support.design.widget.NavigationView
        android:id="@+id/nav_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        app:headerLayout="@layout/header"
        app:menu="@menu/nav_menu"/>
</android.support.v4.widget.DrawerLayout>
```

5.MA：

```java
public class MainActivity extends AppCompatActivity {

    private DrawerLayout mdrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mdrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);

        navView.setCheckedItem(R.id.nav_call);  //设置第一项默认选中
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            //当用户点击任意菜单选项时，关闭滑动菜单
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mdrawerLayout.closeDrawers();
                return true;
            }
        });
    }
}
```

最终效果：

![2](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/2-1625298029164.png)





## 四、FloatingActionButton

就一个立体的悬浮按钮效果，和普通按钮一样，但会持久地显示在上方。

可以在/values/style.xml中指定colorAccent属性，改变其背景颜色

```xml
<android.support.design.widget.FloatingActionButton
    android:id="@+id/fab"
    android:layout_width="50dp"
    android:layout_height="50dp"
    android:layout_gravity="bottom|end"
    android:layout_margin="16dp"
    android:src="@drawable/image_1" />
```

事件监听器：

```java
FloatingActionButton fab = findViewById(R.id.fab);
fab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(MainActivity.this, "fab被点击了", Toast.LENGTH_SHORT).show();
    }
});
```





## 五、Snackbar

可交互的提示信息

```java
		FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar的核心代码
                Snackbar.make(view, "删除数据？", Snackbar.LENGTH_SHORT).setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "已删除", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
```

需要注意的是：Snackbar会挡住悬浮按钮，如果想要提升体验，可以把主布局界面中的Framlayout改为CoordinatorLayout，它为加强版的Framlayout，可以监听所有子控件的各种事件，帮助我们做出最合理的响应。











# 九、MVP的开发模式

Model 应包含以下四个部分：

- VO：视图对象，封装页面中所有的数据
- DTO：数据传输对象，网络请求/IO流中
- DO：领域对象，业务实体
- PO：持久化存储的对象

![Snipaste_2021-08-02_13-52-45](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/Snipaste_2021-08-02_13-52-45.png)

MVP中，将Data和View层彻底分离，由Presenter中间层负责双方的交互通信（接口实现），Actvitiy的职责从MVC的 Controller +View 彻底解耦，变成了只需要进行控制UI的View层。Presenter中进行数据业务的分发处理，同时持有View层和Model层的接口引用。Model层完全负责数据的传输和处理工作。

**处理逻辑**：View层需要展示某些数据时，先调用Presenter层的接口引用，Presenter层再调用Model层请求数据，Model层根据情况判断，回调Presenter层的方法，通知Presenter层的数据加载情况，最后Presenter层再调用View的接口，将数据返回并展示。



- 优点：

减少了Activity的职责，耦合度更低，方便进行接口测试，模块职责划分明确。

- 缺点：

每层都需要对应的接口和类，每次改动接口代价较高。





### 实现逻辑：

View向Model层请求数据逻辑：红线线

Model层向View返回数据：蓝色线

![MVP结构图](Android%E9%9B%B6%E5%9F%BA%E7%A1%80%E5%BC%80%E5%8F%91.assets/MVP%E7%BB%93%E6%9E%84%E5%9B%BE.png)

需要注意P层的实现类中，一些接口对象的处理：

因为View层的接口实现类为Activity，且数据请求是从View层调用P层，所以在Activity中实例化时，调用P层构造时直接传入this即可。

Model层的接口实现类，需要new一个实例化对象。在调用login方法时，需要一个回调接口对象，P层实现了该接口，所以直接传入this即可。







### 具体代码：

#### 1.Model层

User类：

```java
@Data
public class User {
    String username;
    String password;
}
```

接口：

```java
public interface LoginModel {
    void login(User user, OnLoginFinishedListener listener);
}
```

接口实现：

```java
public class LoginModelImpl implements LoginModel {
    @Override
    public void login(User user, final OnLoginFinishedListener listener) {
        final String username = user.getUsername();
        final String password = user.getPassword();
        /*
        * 方便起见，这里只简单模拟，为空则登录失败，其余均成功，并模拟延时效果
        */
        new Handler().postDelayed(new Runnable() {
            boolean error = false;

            @Override
            public void run() {
                if (TextUtils.isEmpty(username)) {
                    listener.onUsernameError();
                    error = true;
                }
                if (TextUtils.isEmpty(password)) {
                    listener.onPasswordError();
                    error = true;
                }
                if (!error) {
                    listener.onSuccess();
                }
            }
        }, 1000);
    }
}

```



#### 2.Presenter层

请求接口：

```java
public interface LoginPresenter {
    void validateCredentials(User user);

    void onDestroy();
}
```

回调接口：

```java
public interface OnLoginFinishedListener {
    void onUsernameError();

    void onPasswordError();

    void onSuccess();
}
```

实现类：

```java
public class LoginPresenterImpl implements LoginPresenter, OnLoginFinishedListener {
    private LoginView loginView;
    private LoginModel loginModel;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;	  
        this.loginModel = new LoginModelImpl();		//实例化Model层接口对象
    }

    @Override
    public void validateCredentials(User user) {
        if (loginView != null) {
            loginView.showProgress();
        }
        loginModel.login(user, this);	//因为实现了所需接口，直接传入this即可
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void onUsernameError() {
        if (loginView != null) {
            loginView.setUsernameError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onPasswordError() {
        if (loginView != null) {
            loginView.setPasswordError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        if (loginView != null) {
            loginView.showSuccess();
        }
    }
}
```



#### 3.View层

接口：

```java
public interface LoginView {
    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void showSuccess();
}
```

XML：省略控件宽高等非必须属性

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context=".View.LoginActivity">

    <EditText
        android:id="@+id/ET_username"/>

    <EditText
        android:id="@+id/ET_password"/>

    <ProgressBar
        android:id="@+id/progress" />

    <Button
        android:onClick="login"
        android:text="登录" />
</LinearLayout>
```

Actvity：

```java
public class LoginActivity extends AppCompatActivity implements LoginView {
    ProgressBar mProgressBar;
    EditText ET_username, ET_password;
    LoginPresenter mLoginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProgressBar = findViewById(R.id.progress);
        ET_username = findViewById(R.id.ET_username);
        ET_password = findViewById(R.id.ET_password);
        mLoginPresenter = new LoginPresenterImpl(this);		//P层接口对象的实例化，实现了所需接口，直接传入this即可
        hideProgress();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUsernameError() {
        ET_username.setError("用户名错误");
    }

    @Override
    public void setPasswordError() {
        ET_password.setError("密码错误");
    }

    @Override
    public void showSuccess() {
        hideProgress();
        startActivity(new Intent(this, MainActivity.class));
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }

    public void login(View view) {
        //封装，调用接口请求Model层数据验证
        User user = new User();
        user.setUsername(ET_username.getText().toString());
        user.setPassword(ET_password.getText().toString());
        mLoginPresenter.validateCredentials(user);
    }
}
```



























