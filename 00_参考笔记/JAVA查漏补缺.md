# 一、JAVA基础





## 1.进制的表示

二进制：`0b`开头

八进制：`0`开头

十六进制：`0x`或`0X`开头，`A-F`不区分大小写



## 2.内存分析

`new`出来的都在堆中

栈中存放着方法的执行过程



字符串常量池也在堆中，所有相同的字符串，在常量池中共用一个内存地址





局部变量位于栈内存，没有初始值

成员变量位于堆内存，有初始值





方法区中有一个静态区，专门用于加载静态的变量和方法





## 3.数组

数组作为参数或返回值时，返回的为地址值，所以可以用来在一个方法中使用数组封装多个需要返回的结果值。



### 3.1 常用方法

| 常用方法                                         | 描述                                                         | 参数                                                         | 返回值 |
| ------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------ |
| array.length                                     | 获取数组长度                                                 |                                                              | int    |
| Arrays.toString(T[ ] array)                      | 打印数组 ，形式[10, 20]                                      | 数组对象                                                     | void   |
| Arrays.sort(T[ ] array)                          | 按照默认进行升序排序（自定义类型需要Comparable或Comparator接口的支持） | 数组对象                                                     | void   |
| System.arraycopy(src,srcPos,dest,destPos,length) | 把原数组的某几个值复制到新的数组                             | 1：原数组对象；2：原数组的起始位置；3：目标数组；4：目标数组的起始位置；5：复制几个 | void   |

```java
    public static void main(String[] args) {
        int a[] = {1, 2, 3};
        int b[] = {4, 5, 6, 7};
        System.arraycopy(a, 0, b, 0, 2);
        System.out.println(Arrays.toString(b)); //[1, 2, 6, 7]
    }
```







## 4.多态转型

### 4.1 向上转型

直接new就可以

```java
 List list=new ArrayList();
```



### 4.2 向下转型

如果确定是该子类，通过向上转型得到的，可以进行强转

```java
 ArrayList arrayList= (ArrayList) list;
```



如果不确定，则使用`instanceof`关键字判断

```java
 ArrayList arrayList=new ArrayList();
 arrayList.add(1);
 System.out.println(arrayList instanceof  List ? arrayList : null);
```







## 5.字符串

字符串常量池也在堆中，所有相同的字符串，在常量池中共用一个内存地址

`.equals`方法中，把常量放在第一个位置，否则容易出现空指针异常



### 5.1 常用方法

| 常用方法            | 描述                         | 参数         | 返回值            |
| ------------------- | ---------------------------- | ------------ | ----------------- |
| length()            | 字符串长度                   |              | int               |
| concat(String str)  | 拼接一个str                  | 拼接的字符串 | 新的字符串        |
| charAt(int index)   | 获取指定位置的单个字符       | 索引         | char              |
| indexOf(String str) | 查找指定字符串首次出现的位置 | 指定字符串   | int，没找到返回-1 |



### 5.2 字符串截取

| 常用方法                     | 描述                               | 参数                               | 返回值   |
| ---------------------------- | ---------------------------------- | ---------------------------------- | -------- |
| substring(int index)         | 从index截取到末尾                  | 索引                               | 新字符串 |
| substring(int begin,int end) | 从begin截取到end                   | 索引                               | 新字符串 |
| spilt(String regex)          | 根据参数的形式，将字符串分为若干份 | 正则表达式，涉及到特殊字符需要转义 | string[] |



### 5.3 转化

| 常用方法                       | 描述                         | 参数         | 返回值         |
| ------------------------------ | ---------------------------- | ------------ | -------------- |
| toCharArray()                  | 当前字符串拆分成数组返回     |              | char[]         |
| getBytes()                     | 获得当前字符串底层的字节数组 |              | byte[]         |
| replace(String old,String new) | 将字符串中所有的old替换为new | char或string | 替换后的字符串 |







## 6.抽象类

* 抽象类不能创建对象
* 抽象类可以有构造方法，供子类super调用
* 抽象类不一定包含抽象方法，有抽象方法的类必定是抽象类
* 抽象类的子类需重写父类所有的抽象方法



## 7.接口

接口编译生成的字节码文件依然是：

```
.java  --->  .class
```



接口之间可以多继承，并且不用重写继承的接口的方法。

如果继承的接口存在冲突的方法，则需要重写。



### 7.1 不同版本

当接口的默认方法或静态方法和父类的方法冲突时，必须覆盖重写

#### JAVA 7 ：

可以包含：

1. 抽象方法
2. 常量，默认为public static final 类型的，赋值完不能修改



#### JAVA 8：

1. 默认方法
2. 静态方法



#### JAVA 9：

1. 私有方法





### 7.2 默认方法

默认方法，接口的实现类可以不要求被重写，也可以重写覆盖，主要用于解决接口升级，子类全部都需要重写新方法的问题。

定义：

```java
public interface Method {
  	//抽象方法
    int add();
  
		//默认方法
    default void doNothing() {
        System.out.println("我什么也没做");
    }
}
```





### 7.3 静态方法

```java
public interface Method {
		static void doStaticThing(){
        System.out.println("我是静态的");
    }
}

    //使用，与类的静态方法相同
    Method.doStaticThing();
```





### 7.4 私有方法

主要解决：默认方法和静态方法代码重复的问题

因为默认方法和静态方法中，出现了方法体，不可避免就会出现代码复用的情况，但又不想让子类调用，于是有了私有方法

```java
public interface Method {
    default void doNothing() {
        printSomething();
        System.out.println("我什么也没做");
    }

    static void doStaticThing(){
        printSomething();
        System.out.println("我是静态的");
    }

  	//不写static时，不能被非static调用
    private static void printSomething(){
        System.out.println("AAA");
        System.out.println("BBB");
    }
}
```







## 8.final

修饰类的时候，该类不能有任何子类

修饰方法的时候，该方法不能被子类继承，因此不能和abstract同时使用

修饰成员变量的时候，必须手动赋值（直接赋值或通过构造赋值）

修饰局部变量的时候，如果修饰的是基本类型，则数据不可改变，如果是引用类型，则地址值不变。



## 9.内部类

编译的时候，会生成如下的文件

```java
外部类名$内部类名.class
```

内部类能访问外部类的所有东西





### 9.1 局部内部类

写在方法中，只能在方法中使用，且不能有权限修饰符

```java
public class Zi {
   public void method(){
       //局部内部类
       class Inner{
           int num=10;
       }
 			 //只能通过方法中创建对象对用
       Inner inner=new Inner();
       System.out.println(inner.num);
   }
}

//使用
 public static void main(String[] args) {
       Zi zi=new Zi();
       zi.method();
    }
```



局部内部类也能访问写在方法中的局部变量，但局部变量都会默认加上`final`关键字

原因：`new`出来的在堆中，而局部变量跟着方法走的，在栈中，可能存在方法执行完消失，但对象仍然存活访问变量的情况，所以需要让变量的生命周期大于方法





### 9.2 匿名内部类

省略的是`Method`接口的实现类

```java
//Method是个接口
Method method=new Method() {
    @Override
    public void add() {
                
   }
};
```



### 9.3 匿名对象

省略的是接口对象

```java
 new Method() {
      @Override
    	public void add() {

    }
};
```





## 10.特殊关键字

native：代表该方法调用的是本地的操作系统的方法，比如获得逻辑地址的哈希值









# 二、JAVA进阶



## 1.Object

所有的类都默认继承该类



### 1.1 equls

使用equls方法时注意：

常量值需要放在前面，否则`前一个对象为空时`会报空指针异常。因为`null`没有`equls`方法

```java
    public static void main(String[] args) {
        Zi zi1 = new Zi("张三");
        Zi zi2 = new Zi("张三");

        String s1="abc";
        String s2="abc";

        boolean b1=Objects.equals(zi1,zi2);      //false
        System.out.println(b1);

        boolean b2 = Objects.equals(s1, s2);    //true
        System.out.println(b2);
    }
```

`Objects`工具类的`equals`方法，比较的是地址值，字符串因为全都在字符串常量池中，所以得出的结果为`true`。自己写的对象（在没有重写`equals`方法前），则为`false`。





### 1.2 requireNonNull

用于判断对象是否是空

```java
    public static void main(String[] args) {
       int []a=null;
       Objects.requireNonNull(a,"是空");	
    }
```





## 2.时间类

一天 = 86400 秒  = 86400 000毫秒

以下的几类都是从`1970年1月1日00:00:00 `开始计算毫秒值

### 1.Date

`java.util.Date`包下，表示一个瞬时的时间，精确到毫秒：



```java
//无参构造表示当前的时间
Date date=new Date();
System.out.println(date); //Sat Sep 04 11:08:52 CST 2021

//有参构造接收一个long类型，并转化为时间格式
Date date=new Date(20211024);
System.out.println(date); //Thu Jan 01 13:36:51 CST 1970

//把时间格式转化成毫秒值（long类型）
long time = date.getTime();
System.out.println(time);   //100

//根据本地格式转化日期对象
Date date=new Date();
System.out.println(date.toLocaleString());	//2021年9月4日 上午11:57:55
```





### 2.DateFormat

接受一个指定的形式参数，通过`format()`方法转为指定的形式

```java
//转化为指定形式
public class Main {
    public static void main(String[] args) {
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String text=simpleDateFormat.format(date);
        System.out.println(text);   //2021-09-04 11:22:20
    }
}
```

`parse()`方法把`字符串`转化回原本的形式，形式不对抛出异常

```java
public class Main {
    public static void main(String[] args) {
        try {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse("2018-01-14 22:22:22");
            System.out.println(date);   //2021-09-04 11:22:20
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
```





### 3.Calanda（常用）

获取当前日期（时分秒等方式均相同）`get()`

```java
    public static void main(String[] args) {
        Calendar c=Calendar.getInstance();
        System.out.println(c.get(Calendar.YEAR));	//2021
        System.out.println(c.get(Calendar.MONTH)); //8（西方的月份从0开始）
        System.out.println(c.get(Calendar.DATE));  //4
    }
```

设置当前日期`set()`

```
    public static void main(String[] args) {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,9999);
        System.out.println(c.get(Calendar.YEAR));   //9999
    }
```

增（减）当前日期`add()`

```java
    public static void main(String[] args) {
        Calendar c=Calendar.getInstance();
        c.add(Calendar.YEAR,2);//负数就是减
        System.out.println(c.get(Calendar.YEAR));   //2023
    }
```







### 2.System

| 方法名                     | 描述                                                         | 入参 | 出参 |
| -------------------------- | ------------------------------------------------------------ | ---- | ---- |
| System.currentTimeMillis() | 从当前系统日期到1970年1月1日00:00:00的时间差（中国在东八区，增加八小时） |      | long |











## 3.集合

### 3.1 Collection接口

对象的公共常用方法：

| 常用方法          | 描述                 | 参数         | 返回值       |
| ----------------- | -------------------- | ------------ | ------------ |
| add(E e)          | 向集合添加元素       | 添加的对象   | boolean      |
| remove(int index) | 根据索引移除一个元素 | 索引         | 被移除的对象 |
| size()            | 获取集合大小         |              | int          |
| clear()           | 清空集合所有元素     |              |              |
| contains(E e)     | 集合是否包含该元素   | 想查找的对象 | boolean      |
| toArray()         | 转化成数组           |              | object[]     |
| isEmpty()         | 判断非空             |              | boolean      |







#### 1.List接口

继承自`Collection`接口，包含`ArrayList`、`LinkedList`、`Vector`三种子类



遍历方式：普通for、foreach、迭代器

共性特点：

* 有序，存和取的顺序相同
* 允许重复元素
* 有索引，可以用普通的for循环遍历



公共方法：

| 常用方法           | 描述                         | 参数               | 返回值   |
| ------------------ | ---------------------------- | ------------------ | -------- |
| set(int index,E e) | 用指定元素替换指定位置的元素 | 1：索引；2：新元素 | 旧的元素 |





==下面的三种实现了`List`接口==

##### ①ArrayList

继承`AbstractList`，底层是数组，查询快，增删慢，是多线程，相比`Vector`速度快，但不同步



##### ②LinkedList

继承`AbstractSequentialList`，底层是双向链表，查询慢，增删快。还包括了大量操作首位元素的方法

<img src="JAVA%E6%9F%A5%E6%BC%8F%E8%A1%A5%E7%BC%BA.assets/image-20210904174912332.png" alt="image-20210904174912332" style="zoom:80%;" />

特有方法：

| 方法名        | 描述                       | 参数       | 返回值   |
| ------------- | -------------------------- | ---------- | -------- |
| addFirst(E e) | 插入列表开头               | 插入的元素 |          |
| addLast(E e)  | 插入列表结尾               | 插入的元素 |          |
| getFirst()    | 得到列表第一个元素         |            | 头元素   |
| getLast()     | 得到列表尾元素             |            | 尾元素   |
| removeFirst() | 移除首元素并返回           |            | 头元素   |
| removeLast()  | 移除尾元素并返回           |            | 尾元素   |
| push(E e)     | 将元素入栈(和addFirst相同) | 入栈元素   |          |
| pop()         | 出栈(和removeFirst相同)    |            | 出栈元素 |





##### ③Vector（了解）

继承的是`AbstractList`，是`1.0`版本的集合，同步的单线程，速度较慢







#### 2.Set接口

继承自`Collection`接口，包含`HashSet`、`LinkedHashSet `、`TreeSet`三种子类



遍历方式：foreach、迭代器

共性特点：

* 除了LinkedHashSet，其余都是无序
* 不允许重复元素
* 没有索引，不能用普通的for循环遍历





==下面三个子类都是实现了`Set`接口==

##### ①HashSet

继承自`AbstractSet`，底层实现是`HashMap`实例。而`HashMap`的实现是基于`Hash`表【数组+链表+（1.8红黑树）】。关于`Hash`表的内容参照3.3章节，`Hash`表的查询速度非常快。

元素不重复原理：`add()`方法 会调用 `hashCode()` 方法和`equals() `方法，只有两个方法都返回true的时候，才认为重复不存储元素。以此判断元素是否重复



所以，该方法存储的元素，==想要不重复，必须重写 `hashCode()` 方法和`equals() `方法！==





##### ②LinkedHashSet

继承自`HashSet`，底层也是哈希表+链表。不同之处在于，该集合用双向链表保证了元素有序，即按照元素插入到`set`中的顺序进行迭代







##### ③TreeSet（了解）

继承自`AbstractSet`，底层是二叉树，一般用于排序







#### 3.迭代器

Collection集合元素的通用获取方式，取之前需要判断集合中有没有元素，有则取出，再判断，以此直至取完。



```java
    public static void main(String[] args) {
        Collection a = new ArrayList();
        a.add(1);
        a.add("姚明");
        a.add(0.5f);

        Iterator iterator = a.iterator();	//通过集合的方法构造迭代器

        while (iterator.hasNext()){
            Object next = iterator.next();
            System.out.println(next);
        }
    }
```

 `a.iterator() `方法 ，在底层中是把索引指向 `集合元素-1` 的位置，`next()`方法取出元素，并推动指针前进



foreach循环内部原理也是迭代器，遍历集合的过程中不能对集合的元素增删



### 3.2 Collections类

Collections工具类的常用方法：

| 常用方法                                  | 描述                       | 参数                       | 返回值  |
| ----------------------------------------- | -------------------------- | -------------------------- | ------- |
| addAll(Collection<T> c,T..t)              | 向集合添加多个元素         | 1：集合对象；2：添加的元素 | boolean |
| shuffle(List<T> list)                     | 打乱集合的顺序             | List对象                   |         |
| sort(List<T> list)                        | 将集合的元素按默认规则排序 | List对象                   |         |
| sort(List<T> list，Comparator<? super T>) | 集合元素按制定规则排序     | 1：List对象；2：规则       |         |



关于`sort()`方法：

被排序的集合里面的元素，要么实现`Comparable`接口，重写`comparaTo`方法定义排序的规则

```java
public class Person implements Comparable<Person>{
    String name;
    int age;

    @Override
    public int compareTo(Person o) {
//        return 0;	//认为
        return this.age - o.age;    //年龄升序排序
    }
}
```

要么使用Comparator类，以匿名内部类定义排序规则：

```java
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        Collections.addAll(list, 4, 3, 2, 1);
        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        System.out.println(list.toString());//[1, 2, 3, 4]
    }
```









### 3.3 Map接口

以`key，value`的形式存储数据，`key`不能重复，`value`可以。为了保证`key`的不一致性，需要重写`key`的`hashCode()`和`equals()`方法。

`key，value`之间存在一个内部接口：`Entry<K,V>`，保存了键值的映射关系，通过`entry`对象`getKey()`和`getValue()`方法分别取出键和值。

常用方法：

| 常用方法         | 描述                                       | 参数         | 返回值                                       |
| ---------------- | ------------------------------------------ | ------------ | -------------------------------------------- |
| put(key,value)   | 向集合添加指定的键值对，key存在则替换value | 1：键；2：值 | key不重复时为null，重复时为value             |
| remove(key)      | 根据指定的键移除对应的值                   | 键           | key不存在返回null，存在返回被删除元素的value |
| get(key)         | 根据指定的键找对应的值                     | 键           | key不存在返回null，存在返回对应元素的value   |
| containsKey(key) | 判断集合中是否包含指定的键                 | 键           | 包含返回true，不包含返回false                |
| keySet()         | 获取所有的键，存储到Set集合                |              | Set对象                                      |
| entrySet()       | 获取到所有键值对对象的集合，存储到Set集合  |              | Set对象                                      |



#### 遍历方式：

1.使用`Map`集合的方法`keySet()`，把`Map`中的所有`key`取出，（迭代器或增强for）遍历`set`集合，通过`get(key)`方法找到对应的value

2.使用`entrySet()`方法把`Map`集合中的`entry`对象取出，（迭代器或增强for）遍历`set`集合，通过entry对象获得key或value



==实现了Map接口的子类：==

#### ①HashMap

继承自`AbstractMap`，存储数据采用哈希表结构，不是有序的集合，且允许使用`null`值，是非同步的。



#### ②LinkedHashMap

继承`HashMap`，采用哈希表+双向链表结构，通过链表结构保证元素有序性，



#### ③Hashtable（了解）

继承自`Dictionary`，底层是哈希表，不允许使用`null`值，是同步的（单线程，速度慢）。现多被`HashMap`所替代，但其子类`Properties`仍然活跃，也是唯一一个和IO流相结合的集合。









### 3.4 哈希

哈希值是一个十进制的整数，由系统随机给出，代表对象的逻辑地址值（非物理地址）

可以用`object`的`hashCode()`方法取得

```java
    public static void main(String[] args) {
        Object o=new Object();
        int i = o.hashCode();		
        System.out.println(i);  //475266352，打印出@的地址值时，就是该值的十六进制表示
    }
```





`JDK1.8`前，哈希表底层是数组+链表。`1.8`后为数组+链表+红黑树，提高了查询速度。

初始大小为16，把相同哈希值的元素分为一组，由链表/红黑树进行连接。链表长度>8时，转化成红黑树，小于6时，转换会链表。（链表是同一个组下面挂的那些）

![image-20210904203801501](JAVA%E6%9F%A5%E6%BC%8F%E8%A1%A5%E7%BC%BA.assets/image-20210904203801501.png)



##### 为什么需要转换？

因为链表查找性能是O(n)，而树结构能将查找性能提升到O(log(n))。当链表长度很小的时候，即使遍历，速度也非常快，但是当链表长度不断变长，肯定会对查询性能有一定的影响，所以才需要转成树。



##### 为什么阈值是8？

阈值8是时间和空间的权衡，是根据概率统计决定的。

在hashCode离散性很好的情况下，树型bin（桶，即bucket，HashMap中hashCode值一样的元素保存的地方）用到的概率非常小，因为数据均匀分布在每个bin中，几乎不会有bin中链表长度会达到阈值。事实上，在随机hashCode的情况下，在bin中节点的分布频率遵循泊松分布。



### 3.5 JDK9新特性

List、Set、Map接口中新增了一个静态的方法of，使用前提：元素个数已经确定，不再改变



注意事项：

1. 只适用于三个接口，不适用于实现类
2. 返回值是不可改变的集合，不能在使用add、put方法添加元素，会抛出异常
3. Set接口和Map接口不能添加重复元素，否则抛出异常











## 4.泛型

泛型就是不确定的类型，可以使用任何类型的东西

格式：`<T>`，T可以换成任意字母，但通常有以下约定：

​			

| 使用地方 | 字母 |
| -------- | ---- |
| 类       | T    |
| 方法     | M    |
| 集合元素 | E    |
| 接口     | I    |

​	 



### 4.1 泛型的使用

类使用泛型，<>写在类名的后面

```java
public class Zi<T> {
    String name;
    T t;
}
```



方法使用泛型：<>写在返回值之前

```java
public interface Method {
    <M> void add(M m);		
}
```





### 4.2 泛型的通配符

使用泛型传递数据的时候，因为泛型的类型不确定，可以使用`<?>`表示，==但使用后只能用`Object`类中的共性方法，集合元素自身的方法无法使用==

```java
    public static void main(String[] args) {
      Collection<String> collection1=new ArrayList<>();
      getElement(collection1);	//问号换成Object这里报错
    }

    public static void getElement(Collection<?> collection){
        collection.add("1"); //错误，会报错，
    }
```



如果不写<?>则可以使用

```java
    public static void main(String[] args) {
        Collection<String> collection1=new ArrayList<>();
        getElement(collection1);
        System.out.println(collection1.toString());
        
        Collection<Integer> collection2=new ArrayList<>();
        getElement(collection2);
        System.out.println(collection2);
    }

    public static void getElement(Collection collection){
        collection.add("1");	//正确
    }
```



（个人理解）所以，`?` 应该是用来指定一个泛型的上限或下限的

上限：`<? extends Number>`，Number及其子类

下限：`<? super Number>`，	Number及其父类

```java
    public static void main(String[] args) {
        //继承关系Integer extends Number extends Object
        //String extends Object
        Collection<Integer> collection1=new ArrayList<>();
        Collection<String> collection2=new ArrayList<>();
        Collection<Number> collection3=new ArrayList<>();
        Collection<Object> collection4=new ArrayList<>();
        
        getElement1(collection1);
        getElement1(collection2);   //报错
        getElement1(collection3);
        getElement1(collection4);   //报错
        
        getElement2(collection1);   //报错
        getElement2(collection2);   //报错
        getElement2(collection3);
        getElement2(collection4);
    }

    public static void getElement1(Collection<? extends Number> collection){    }
    public static void getElement2(Collection<? super Number> collection){    }
```



## 5.可变参数

用于已经确定了参数的类型，不确定个数的情况。

实际传递进来的，是一个数组，大小根据传入的参数个数确定。

```java
		//计算N个整数的合
		public static void main(String[] args) {
        int result = add(10, 20, 20);
        System.out.println(result);
    }

    public static int add(int... arr) {
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        return sum;
    }
```

注意：

一个方法的参数列表只能有一个可变参数，如果参数有多个，可变参数必须在列表末尾。



**终极写法**：传入 `Object…arr`，因为所有的父类都是`Object`



## 6.异常处理

`throw`用作主动发现异常情况，`throws`则是往上抛，直至给`JVM`处理（中断程序）

```java
    public static void main(String[] args) {
        int[] a = null;
        if (a == null) {
            throw new NullPointerException();
        }
    }
```



`finially`中的代码，无论程序是否异常都会被处理，如果有`return`，永远返回`finally`中的`return`



### 继承中的异常处理：

子类重写父类的方法时：

* 抛出和父类相同的异常，或异常的子类，或不抛出异常

* 父类没有异常，子类也不能抛出。产生异常时只能捕获，不能抛出



### 自定义异常

一般类名以`Exception`结尾，说明该类是一个异常类

自定义的异常类，必须继承`Exception`或`RuntimeException`

`Exception`：编译期异常，如果方法内部抛出异常就必须处理，`throws`或`try……catch`

`RuntimeException`：运行期异常，无需处理，交给`JVM`中端处理



```java
public class RegisterException extends Exception {
    public RegisterException() {
    }

    public RegisterException(String message) {
        super(message);
    }
}
```





## 7.多线程



### 7.1 基本概念

* **并发**：多个事件**同一个时间段内**发生
* **并行**：多个事件**同一时刻**发生

![image-20210905104208980](JAVA%E6%9F%A5%E6%BC%8F%E8%A1%A5%E7%BC%BA.assets/image-20210905104208980.png)

目前电脑市场上说的多核` CPU`，便是多核处理器，核 越多，并行处理的程序越多，能大大的提高电脑运行的效率。



* **进程**：是指一个内存中运行的应用程序，每个进程都有一个独立的内存空间，一个应用程序可以同时运行多个进程；进程也是程序的一次执行过程，是系统运行程序的基本单位；系统运行一个程序即是一个进程从创建、运行到消亡的过程。

* **线程**：线程是进程中的一个执行单元，负责当前进程中程序的执行，一个进程中至少有一个线程。含有多个线程的应用程序称之为多线程程序。 

  简而言之：一个程序运行后至少有一个进程，一个进程中可以包含多个线程 



**线程调度:**

- 分时调度

  所有线程轮流使用` CPU `的使用权，平均分配每个线程占用 `CPU `的时间。

- 抢占式调度

  `CPU`使用抢占式调度模式在多个线程间进行着高速的切换。对于`CPU`的一个核而言，某个时刻，只能执行一个线程，而 `CPU`的在多个线程间切换速度相对我们的感觉要快，看上去就是在同一时刻运行。

  优先让优先级高的线程使用 `CPU`，如果线程的优先级相同，那么会随机选择一个(线程随机性)，`Java`使用的为抢占式调度。

  



其实，多线程程序提高的是程序运行效率，而非代码执行的速度，让`CPU`的使用率更高。

加载程序时，程序由`ROM`加载进`RAM`中，占用一些内存执行，进入内存的程序就叫进程，结束进程时从内存中清楚。





### 7.2 线程的启动

`run() `方法中设置线程的任务，`start() `方法开辟一个新的栈空间，执行线程，`JVM `调用该线程的 `run() `方法。

如果调用`run()`方法，则认为是在主线程中执行`run()`中的任务，不会开启新的线程。

多次启动一个线程是非法的，尤其是线程已经结束执行后，不能重新启动。

![image-20210905113234079](JAVA%E6%9F%A5%E6%BC%8F%E8%A1%A5%E7%BC%BA.assets/image-20210905113234079.png)

`Runnable`接口创建线程的步骤为：

实现类实现`Runnable`接口，重写`run()`方法，讲接口的实现类对象new出来，扔进Thread构造方法中。







### 7.3 常用方法

Thread类的构造：

| 构造参数                | 参数描述                     | 作用描述                               |
| ----------------------- | ---------------------------- | -------------------------------------- |
| Thread()                |                              | 分配一个新的线程                       |
| Thread(String name)     | 线程名                       | 指定线程名字                           |
| Thread(Runnable target) | 带指定目标（即runnable对象） | 使用runnable的匿名内部类形式，指派任务 |
| Thread(runnable,name)   | 1：run对象；2：线程名        | 综合2和3                               |



Thread类的对象常用方法：

| 构造参数             | 描述                       | 参数   | 返回值    |
| -------------------- | -------------------------- | ------ | --------- |
| getName()            | 获取当前线程的名称         |        | String    |
| setName(String name) | 设置线程名                 | 线程名 |           |
| sleep(long millis)   | 让当前的线程暂停millis毫秒 | 毫秒值 |           |
| currentThread()      | 返回当前正在执行的线程对象 |        | Thrad对象 |





### 7.4 线程安全问题

多个线程访问共享数据的时候，就会产生线程的安全问题。

产生的原理：

![image-20210905191430163](JAVA%E6%9F%A5%E6%BC%8F%E8%A1%A5%E7%BC%BA.assets/image-20210905191430163.png)



解决的核心思想：

让一个线程访问共享数据的时候，其他的线程只能等待，无论是否失去了CPU的执行权。等待当前线程执行结束后，其余线程再执行和访问。



解决方法：

#### 同步代码块

```java
    static class RunnableImpl implements Runnable {
        private int ticket = 100;
        Object object = new Object();//锁对象

        @Override
        public void run() {
            while (true) {
                synchronized (object) {	//使用synchronized代码块，把锁对象传入
                    if (ticket > 0) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + "卖第" + ticket + "张票");
                        ticket--;
                    }
                }
            }
        }
    }
```



原理：

`object锁对象`也叫同步锁、对象锁、对象监视器

线程在抢到CPU的执行权时，遇到sychronized代码块，这时会检查是否有锁对象，有则获取到锁对象并进入到同步中执行。没有执行完毕不会释放锁。

其他线程抢到CPU的执行权时，检查锁对象是否存在，不存在则该线程进入到阻塞状态，等待锁对象的归还，拿到锁对象后再进入到同步中执行。

注意：同步保证了线程的安全性，但频繁地判断锁、释放锁、程序的效率会降低





#### 同步方法

```java
    static class RunnableImpl implements Runnable {
        private int ticket = 100;

        @Override
        public void run() {
            while (true) {
                payTicket();
            }
        }

      	//返回值前加一个synchronized
        public synchronized void payTicket() {
						//原来synchronized代码块中的方法
        }
    }
```

同步方法的锁对是线程的实现类对象，也就是this。如果是静态的方法，则锁对象是本类的class文件对象







#### 锁机制

Lock接口中提供了`lock()获取锁`和`unlock()释放锁`的方法，使用多态创建其子类对象

```java
    static class RunnableImpl implements Runnable {
        private int ticket = 100;
        Lock lock=new ReentrantLock();//新建锁对象
       
        @Override
        public void run() {
            while (true) {
                lock.lock();
               	//放原来的if语句
                lock.unlock();//也可在finally代码块中执行锁的释放
            }
        }
    }
```





### 7.5 线程的状态

![image-20210905211656434](JAVA%E6%9F%A5%E6%BC%8F%E8%A1%A5%E7%BC%BA.assets/image-20210905211656434.png)



注意：

使线程进入到`wait`状态的，是锁对象。（  `notify()`  或  `notifyAll()`  ）唤醒也是用对应的锁对象。

并且，线程进入到`wait set`中时，不会浪费CPU的资源，也不参与锁的竞争。被唤醒之后重新进入到调度队列中，如果有锁对象，优先给唤醒的线程使用，没有锁则等待锁的释放。

```java
    public static void main(String[] args) {
       Object obj=new Object();//锁对象

        new Thread(()->{
            synchronized (obj){
                System.out.println("老板，我要一个包子");
                try {
                    obj.wait(); //锁对象等待，传入long则作用和sleep相同
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("拿到包子");
            }
        }).start();

        new Thread(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (obj){
                System.out.println("包子做好了");
                obj.notify();      //使用锁对象唤醒其余使用锁对象的线程
            }
        }).start();
    }
```



### 7.6 线程池

容纳线程的容器，可以反复使用其中的线程，省去了频繁创建线程对象的操作。**JDK1.5**后能直接使用封装好的线程池。

内部原理：是一个队列，每次弹出一个顶部的线程，用完后归还回来，加入队列底部。无可用线程时，进行等待。

优点：

1. 减低资源消耗
2. 提高响应速度
3. 提高线程的可管理性（根据系统的承受能力调整线程数目）





```java
    public static void main(String[] args) {
      	//创建线程池，参数表示线程池中的线程数
        ExecutorService executorService= Executors.newFixedThreadPool(2); 
      	//递交线程任务（实现类对象），并开启
        executorService.submit(new RunnableImpl());
     		//销毁线程池
        executorService.shutdown();
    }

    static class RunnableImpl implements Runnable{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());   //pool-1-thread-1
        }
    }
```











# 三、数据结构



## 1.栈

继承`Vector`；先进后出，入口出口在同一侧



```java
Stack stack=new Stack();//构造
```



API：

| 方法名           | 描述                                                         | 参数         | 返回值  |
| ---------------- | ------------------------------------------------------------ | ------------ | ------- |
| empty()          | 判断栈是否为空                                               |              | boolean |
| peek()           | 取栈顶的值，但不出栈                                         |              | Object  |
| push(Object o)   | 压栈                                                         | 压栈对象     | Object  |
| pop()            | 出栈                                                         |              | Object  |
| search(Obejct o) | 找该对象所处的第一个栈内位置（栈顶到栈底，1开始，没有返回-1） | 需要找的对象 | int     |







## 2.队列

先进先出，出口出口各站一侧，

实现类有`LinkedList`和`PriorityQueue`。

| 方法名    | 描述           | 参数     | 返回值   |
| --------- | -------------- | -------- | -------- |
| add(E e)  | 队尾压入元素   | 压入对象 | 压入对象 |
| remove()  | 移出队头的元素 |          | 移出对象 |
| element() | 获取队头元素   |          | 队头元素 |







## 3.数组

查询快：查询地址连续，通过数组的首地址可以找到，通过索引可以快速查找某一个元素

增删慢：长度固定，增删元素时必须创建一个新的数组，把原数组的数据复制过来，原数组在内存中会被销毁（垃圾回收）





## 4.链表

查询慢：地址不连续，每次都重头开始查询

增删快：删除一个元素，对整体结构没有影响，秩序修改相应节点的指针



链表的一个元素也称为一个节点，一个节点包含了一个数据源，两个指针域

<img src="JAVA%E6%9F%A5%E6%BC%8F%E8%A1%A5%E7%BC%BA.assets/image-20210904172238703.png" alt="image-20210904172238703" style="zoom: 67%;" />





单向链表：只有一条链子，不能保证元素的顺序（存取顺序可能不一致）

双向链表：两条链子，一条专门记录元素的顺序，保证存取顺序一致



## 5.树

![image-20210904173003135](JAVA%E6%9F%A5%E6%BC%8F%E8%A1%A5%E7%BC%BA.assets/image-20210904173003135.png)











# 四、函数式编程

## 1. lambda表达式

函数式编程思想，就是有输入、输出的一套计算方案，强调做什么，而不是以什么形式做，省略了面向对象的“必须通过对象的形式来做“这种思想。



lambda表达式的格式

```java
(参数类型 参数1,参数类型 参数2) -> { 代码语句 }
```



### 1.使用前提

1. 使用`Lambda`必须具有接口，且要求==接口中有且仅有一个抽象方法==。
2. 使用`Lambda`必须具有**上下文推断**。
   也就是方法的参数或局部变量类型必须为`Lambda`对应接口的类型，才能使用`Lambda`作为该接口的实例。

> 备注：有且仅有一个抽象方法的接口，称为“**函数式接口**”。





### 2.省略格式

能够根据上下文推导得出的信息，都可以省略

1. 小括号内参数的类型可以省略；
2. 如果小括号内**有且仅有一个参**，则小括号可以省略；
3. 如果大括号内**有且仅有一个语句**，则无论是否有返回值，都可以省略大括号、return关键字及语句分号。





### 3.例子



#### 1.1无参无返回值

```java
public interface Cook {
    void makefood();
}
```

```java
public static void cook_diner(Cook cook){
    cook.makefood();
}
```

```java
cook_diner(()->{
    System.out.println("开饭了");
});
```





#### 1.2 有参无返回值

对一个Person的数组，将其中的每个对象按照年龄升序排序

```java
 Person[] array = { new Person("古力娜扎", 19),
                new Person("迪丽热巴", 18),
                new Person("马尔扎哈", 20) };

 Arrays.sort(array, (Person a, Person b) -> {
       return a.getAge() - b.getAge();
 });

 for (Person person : array) {
      System.out.println(person);
 }
```



#### 1.3 有参有返回值

```java
public interface Caculator {
    int calc(int a,int b);
}
```

```java
public static void calc_num(int a,int b,Caculator caculator){
    System.out.println(caculator.calc(a,b));
}
```

```java
calc_num(10,20,(int a,int b)->{
    return a+b;
});
```



### 4.冲突问题

如果方法重载，两个接口均为无参无返回值，且都可以用lambda表达式，此时编译器会强制要求指定接口

```java
  public static void main(String[] args) {
        cook_diner((Caculator) ()->{	//第一个括号指定类型
            System.out.println("我从哪里来");
        });
    }


    public static void cook_diner(Cook cook) {
        cook.makefood();
    }

    public static void cook_diner(Caculator caculator) {
        caculator.calc();
    }
```





### 5.方法引用

如果lambda表达式的对象已经存在，方法也是存在的，就可以使用`::`引用该方法作为lambda的替代









## 2.函数式接口

函数式接口是指接口中==有且只有一个抽象方法==，Lambda是函数式编程的体现。因为只有一个抽象方法，lambda才能顺利推导所需类型。

语法糖是指使用更加方便，但原理不变的代码语法，如for-each循环。



函数式接口，能在编译的时候减少一个class文件的产生（匿名内部类产生的文件）。此外，还具有延迟执行的特点，比如一个方法，接收一个整形和函数式的接口，如果整形不满足条件，则函数式接口就不执行，能减少代码量的执行，提升程序效率。



@FunctionalInterface：该注解用于检测接口是否是函数式接口，即检测抽象方法是否唯一



常用的函数式接口：Supplier（生产型接口），Consumer（消费型接口），Predicate（判断型接口），Function（转换类型接口）

Supplier：不接受参数，返回一个指定的类型数据

Consumer：接收一个指定类型的参数，对其做处理，返回值为空

Predicate：接收一个指定类型的参数，判断是否满足自定义的规则

Function：接受一个指定类型的参数，转化为另一种类型（用于map集合）



## 3.Stream流

得益于Lambda的概念，JDK1.8中引入了一个新的Stream流的概念，用于解决集合类库的弊端。流并不会存储集合元素，只是对集合元素进行过滤和映射。

特征：

1. 流的每个中间操作都会**返回流本身**，方便对操作进行优化，也可以使用链式编程。
2. 内部迭代，直接在内部进行遍历



构造：

| 方法名          | 描述                                                         |
| --------------- | ------------------------------------------------------------ |
| list.stream()   | 把集合对象转换为stream流（map集合不能直接获取，只能获取键、值、EntrySet的流） |
| Stream.of(list) | 使用静态方法获取，也能获取数组对象                           |

常用方法：

| 方法名                 | 描述                                                 | 参数              | 返回值     |
| ---------------------- | ---------------------------------------------------- | ----------------- | ---------- |
| filter()               | 过滤集合对象，筛选出符合条件的对象                   | Predicate接口对象 | stream对象 |
| map()                  | 转换集合对象类型                                     | Function接口对象  | stream对象 |
| limit(int i)           | 对集合进行截取，集合长度小于i时不操作                | 截取个数          | stream对象 |
| skip(int i)            | 跳过集合的前几个元素，集合长度小于i时返回空的流      | 跳过个数          | stream对象 |
| concat(stream1,steam2) | 两个流合并成一个                                     | 两个stream对象    | stream对象 |
| foreach()              | 遍历集合对象【终结方法，不会再返回流本身】           | Consumer接口对象  |            |
| count()                | 返回流中的集合元素个数【终结方法，不会再返回流本身】 |                   | long       |



### 范例1：filter()

遍历集合，输出名字为张开头的集合

```java
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("张三");
        list.add("李四");
        list.add("王五");
        list.add("张三风");
        list.add("张益达");
        list.add("王二麻子");
        
        //使用流的思想过滤和遍历集合
        list.stream().filter(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.startsWith("张");
            }
        }).forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
        
        //使用lambda表达式简化其写法
        list.stream().filter(s -> s.startsWith("张")).forEach(s -> System.out.println(s));
        
        //还以把长度为3的加入到新的集合中去
        ArrayList<String> newList = new ArrayList<>();
        list.stream().filter(s -> s.startsWith("张")).filter(s -> s.length() == 3).forEach(s -> newList.add(s));
        System.out.println(newList.toString());
    }
```

==注意==：每次使用完流以后，不论最后是否调用的是【终结方法】，流都会自己关闭，无法再次调用。



### 范例2：map()

`Function接口`的使用

```java
    public static void main(String[] args) {
        Stream<String> stringStream = Stream.of("1", "2", "3", "520");
        stringStream.map(s -> Integer.parseInt(s)).forEach(i -> System.out.println(i));
    }
```





### 范例3：limit()和skip()

```java
    public static void main(String[] args) {
        Stream<String> stringStream = Stream.of("1", "2", "3", "520");
        stringStream.skip(2).forEach(s-> System.out.println(s));    //3 520
		//不能接着用，流被关闭了
        stringStream.limit(3).forEach(s-> System.out.println(s)); //1 2 3
    }
```



### 范例4：筛选指定对象

```java
    public static void main(String[] args) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Person("张三", 18));
        arrayList.add(new Person("李四", 20));
        arrayList.add(new Person("张益达", 18));
        arrayList.add(new Person("王五", 50));
        arrayList.add(new Person("李世明", 18));

        //筛选姓张的
        arrayList.stream().filter(o -> {
            Person person = (Person) o;
            return person.name.startsWith("张");
        }).forEach(person -> System.out.println(person.toString()));

        //筛选年龄大于18的
        arrayList.stream().filter(o -> {
            Person person = (Person) o;
            return person.age>18;
        }).forEach(person -> System.out.println(person.toString()));
    }
```













# 五、File类

它是电脑中文件和目录路径名的抽象表示，可以使用该类对文件和文件夹进行操作



构造方法可以传入：

| 方法名                           | 参数                         |
| -------------------------------- | ---------------------------- |
| File(String filename)            | 绝对路径的字符串             |
| File(String parent,String child) | 父路径和子文件名             |
| File(String child)               | 相对路径下创建一个子文件     |
| File(File file,String child)     | 在父文件路径下创建一个子文件 |



## 1.该类的静态变量

| 变量名                 | 描述                                                   | 类型   |
| ---------------------- | ------------------------------------------------------ | ------ |
| File.pathSeparator     | 路径分隔符，不同操作系统不同。(win是分号，linux是冒号) | 字符串 |
| File.pathSeparatorChar |                                                        | 字符   |
| File.separator         | 文件名称分隔符（win是\，linux是/）                     | 字符串 |
| File.separatorChar     |                                                        | 字符   |

相对路径是从当前项目的根目录开始计算，绝对路径是从盘符开始计算。

路径不区分大小写，名称分隔符需要使用转义字符`(\\)`





## 2.常用方法

### 2.1 获取文件的参数

| 方法名            | 描述                                                         | 参数 | 返回值 |
| ----------------- | ------------------------------------------------------------ | ---- | ------ |
| getAbsolutePath() | 获取此文件的绝对路径（字符串形式）                           |      | String |
| getPath()         | 将此file转为路径名字符串（原来是绝对路径就返回绝对路径，原来是相对返回相对） |      | String |
| getName()         | 返回此file表示的文件或目录的名称                             |      | String |
| length()          | 返回此file表示的文件长度，字节为单位，文件不存在返回0        |      | long   |





### 2.2 判断文件的方法

| 方法名        | 描述               | 参数 | 返回值  |
| ------------- | ------------------ | ---- | ------- |
| exists()      | file对象是否存在   |      | boolean |
| isDirectory() | file对象是否是目录 |      | boolean |
| isFile()      | file对象是否是文件 |      | boolean |



### 2.3 创建删除的方法

| 方法名                         | 描述                                                         | 参数     | 返回值  |
| ------------------------------ | ------------------------------------------------------------ | -------- | ------- |
| createNewFile(String filename) | 该名称的文件不存在时，创建一个新的空文件（创建文件夹或路径不存在时出IO异常） | 文件名   | boolean |
| delete()                       | 删除此file表示的文件或目录，（文件不存在/文件夹有内容时返回false） |          | boolean |
| mkdir(String dirName)          | 创建此file表示的目录，不能创建多级文件夹                     | 文件夹名 | boolean |
| mkdirs(String path)            | 创建此file表示的目录，包括不存在的父目录                     | 文件路径 | boolean |



### 2.4 遍历目录

以下方法，file不存在，或file对象不是目录，均会抛出空指针异常

| 方法名                           | 描述                                         | 参数                   | 返回值    |
| -------------------------------- | -------------------------------------------- | ---------------------- | --------- |
| list()                           | 获取该file目录中的所有子文件或目录，包括隐藏 |                        | String [] |
| listFiles()                      | 获取该file目录中的所有子文件或目录，包括隐藏 |                        | File []   |
| listFiles(FileFilter filter)     | 获取并过滤出想要的文件                       | FileFilter接口对象     | File []   |
| listFiles(FilenameFilter filter) | 获取并过滤出想要的文件                       | FilenameFilter接口对象 | File []   |



```java
    @org.junit.Test
    public void test() {
        File file=new File("Z:\\02_test_demo\\test_demo1");
        getAllFile(file);
    }

    public static void getAllFile(File dir){
        //添加匿名的文件过滤器，指定过滤目录和.java文件
        File[] files = dir.listFiles( file-> {
                if (file.isDirectory()||file.getName().endsWith(".java")) {
                    return true;
                }
                return false;
        });
		//遍历过滤出的文件数组，如果是目录继续遍历，并打印文件名
        for (File file : files) {
            if (file.isDirectory()){
                getAllFile(file);
            }else {
                System.out.println(file);
            }
        }
    }
```

```java
	//第二种接口的写法
    //添加匿名的文件过滤器，此时dir1为目录，name为文件名
    File[] files = dir.listFiles((dir1, name) -> {
       if (new File(dir1, name).isDirectory() || name.endsWith(".java")) {
            return true;
       }
       return false;
    });

	//简化写法
  File[] files = dir.listFiles((dir1, name) -> new File(dir1, name).isDirectory() || name.endsWith(".java"));
```



## 3.IO流

流可分为字节流和字符流，1个字符=2个字节=16个二进制位



四个顶级抽象父类

|        | 输入流      | 输出流       |
| ------ | ----------- | ------------ |
| 字节流 | InputStream | OutputStream |
| 字符流 | Reader      | Writer       |



### 3.1 字节输出流

写入的原理：

java程序`——>`JVM`——>`OS（操作系统）`——>`OS调用写数据的方法写入文件

假如`fos.write(97)`，`97`转化为二进制是`110001`（最底层的记录），文本编辑器在打开文件时，查询编码表，把字节转化为字符表示。**0-127：查询ASCII表（97对应a），其他值查询系统默认编码表（GBK）。**





常用方法：

| 方法名                            | 具体功能                                                     | 参数                                | 返回值 |
| --------------------------------- | ------------------------------------------------------------ | ----------------------------------- | ------ |
| close()                           | 关闭流并释放相关资源，会调用一次flush()，但流不能再使用      |                                     |        |
| flush()                           | 刷新此输出流并强制写出缓冲区的内容                           |                                     |        |
| write(byte[] b)                   | 将 b.length字节从指定的字节数组写入此输出流                  | 比特数组对象                        |        |
| write(byte[] b, int off, int len) | 从指定的字节数组写入 len字节，从偏移量 off开始输出到此输出流 | 1：比特数组对象；2：起始值；3：长度 |        |
| write(int b)                      | 写入一个字节（负数会把两个字节拼起来，组成一个中文显示）     | 一个字节                            |        |



子类的构造：

FileOutputStream

| 方法名                        | 具体内容              | 参数          |
| ----------------------------- | --------------------- | ------------- |
| FileOutputStream(File file)   | 写入由指定的 File对象 | File对象      |
| FileOutputStream(String name) | 写入指定的名称文件    | 1：文件路径； |

> 还可以增加一个boolean参数，ture表示追加写入，默认flase



```java
    public static void main(String[] args) {
        try {
            FileOutputStream fos=new FileOutputStream("a.txt");
            fos.write("\r\n".getBytes());   //换行写
            fos.write("abc".getBytes());	//字符可以通过getbytes()转变字节数组
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```







### 3.2 字节输入流

子类FileInputStream构造与输出流相同

如果没有该文件，会抛出`FileNotFoundException` 



读取的原理：

java程序`——>`JVM`——>`OS（操作系统）`——>`OS调用写读取的方法读取文件。结束标记-1是JVM给的



常用方法：

| 方法名         | 具体功能                        | 参数       | 返回值       |
| -------------- | ------------------------------- | ---------- | ------------ |
| read()         | 读取下一个字节，读完为返回-1    |            | int          |
| read(byte[] b) | 读取b.length个字节，并存储在b中 | byte[]对象 | int，b的长度 |



```java
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("a.txt");
            int len = 0;//记录读到的字节
            while ((len = fis.read()) != -1) {
              	//读取的是字节，要转换成字符
                System.out.println((char)len);  //  abc
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```

```java
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("a.txt");
            byte[] bytes = new byte[1024];
            int len = 0;//bytes的有效长度
            while ((len = fis.read(bytes)) != -1) {
                System.out.println(new String(bytes,0,len));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```







### 3.3 字符输出流

字符输出流是把数据写入到内存缓冲区中（进行字符转变为字节的过程），所以必须要调用`flush()`



使用方法和构造与字节输出流完全相同，不用再转换为`byte[]`

```java
    public static void main(String[] args) {
        try {
            FileWriter writer = new FileWriter("a.txt");
            writer.write("abc123ded你好fe#$fgaf");	//写入数据
            writer.flush();	//清空缓冲区
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```











### 3.4 字符输入流

字节流读取中文文件时，会涉及到编码问题，一个中文在GBK编码下占两个字节，UTF-8的编码下占三个字节，会出现乱码现象。

**字符输入流专门用于读取文本数据（不论中文英文还是特殊符号）**，不会出现乱码的问题。



使用方法和构造与字节输入流完全相同，只需要把子类对象改为`FileReader`，数组改为`char[]`类型

```java
    public static void main(String[] args) {
        try {
            FileReader reader = new FileReader("a.txt");
            char[] chars = new char[1024];
            int len = 0;
            while ((len = reader.read(chars)) != -1) {
                System.out.println(new String(chars,0,len));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```





### 3.5 JDK1.7后的处理

因为总要释放流资源，所以`1.7`前写在`finally `代码块中，但因需要把流对象申明为全局变量，且需要加入很多空指针异常等处理。

于是有了`1.7`后的高级写法，将流放在`try()`的括号中，作用域就仅限于`try`的代码块，且不用关闭和`flush()`

```java
    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter("a.txt");) {
            writer.write("aasdgagqadawd a f");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```







## 4.Properties（流存储）

`Hashtable`的子类，持久的属性集，能保存在流中或从流中加载，属性列表的每个键和值都是字符串。也是唯一和`IO`流结合的集合，可以把集合的临时数据，持久化存储；也可以把硬盘中的文件读到集合中使用。



文件的注释一般用#，读取时会跳过；键值对默认都是字符串，不用加引号；键值对的连接符可u



常用方法

| 方法名                                | 具体内容                 | 参数说明                                     | 返回值       |
| ------------------------------------- | ------------------------ | -------------------------------------------- | ------------ |
| setProperty(String key, String value) | 保存一对属性             |                                              | Object       |
| getProperty(String key)               | 根据键搜索值             |                                              | String value |
| stringPropertyNames()                 | 遍历所有键的名称         |                                              | Set集合对象  |
| store(stream,String comments)         | 把集合数据持久化存储     | 1：流对象；2：注释内容，一般用""，不能存中文 |              |
| load(stream)                          | 从字节输入流中读取键值对 |                                              |              |



存示例：

```java
    public static void main(String[] args) {
        Properties properties=new Properties();
        properties.setProperty("张三","18");
        properties.setProperty("李四","20");
        properties.setProperty("王五","52");
        properties.setProperty("赵六", "899");

        try {
            properties.store(new FileWriter("b.txt"),"保存的数据");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
```

取示例：

```java
    public static void main(String[] args) {
        Properties properties=new Properties();
        try {
            properties.load(new FileReader("b.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<String> set = properties.stringPropertyNames();
      	//遍历
        for (String key : set) {
            String value = properties.getProperty(key);
            System.out.println(key+":"+value);
        }
    }
```







## 5.缓冲流（增强的IO）

IO流是每次读取单个字节，缓冲流则是读取一个缓冲区的数组，默认大小8192个字节。

构造对象为一个IO流，且可以指定数组的大小。其余方法一致，使用`JDK1.7`的新特性创建



练习：

```java
    public static void main(String[] args) {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("c.txt"));) {
            bos.write("我存了数据".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```

```java
    public static void main(String[] args) {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream("c.txt"));) {
            byte[] bytes = new byte[1024];
            while ((bis.read(bytes)) != -1) {
                System.out.println(new String(bytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```



字符缓冲流的特有方法：

| 方法名     | 描述                                           | 返回值 |
| ---------- | ---------------------------------------------- | ------ |
| newLine()  | 特有的换行符，根据操作系统改变                 |        |
| readLine() | 读一行，不包含终止符换行符，到达文件为返回null | String |

练习：

```java
    public static void main(String[] args) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("d.txt"));) {
            for (int i = 0; i < 5; i++) {
                bw.write("我输入了一行");
                bw.newLine();//特有的换行，根据操作系统改变
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```

```java
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("d.txt"));) {
            char[] chars = new char[1024];
            while ((br.read(chars)) != -1) {
                System.out.println(chars);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```





## 6.转换流（转编码）

用于转换字符编码引起的问题



OutputStreamWirter和InputStreamReader，构造参数接收一个IO流和字符串的编码。

输入流注意编码表的名称要和文件编码相同，否则出现乱码。





## 7.序列化流（存储对象）

用于存储对象，存储的对象需要实现`Serializable`接口（标记型接口，无实际意义），并定义一个`serialVersionUID`参数（否则只要修改了`Person`类，就会出现序列化冲突的异常）。

```java
public class Person implements Serializable{
    private static final long serialVersionUID = 1L;	//序列号，JVM根据序列号找对应的类文件
    transient String name;	//transient表示该值不被序列化，使用默认值
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
```

构造参数接收一个IO流



特殊方法：

| 方法名               | 描述           | 返回值 |
| -------------------- | -------------- | ------ |
| writeObject(Obj obj) | 将对象写入文件 |        |
| readObject()         | 读取文件       | object |

演示：

```java
    public static void main(String[] args) {
        //写
        try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("1.txt"));) {
            oos.writeObject(new Person("李四",20));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //读
        try( ObjectInputStream ois=new ObjectInputStream(new FileInputStream("1.txt"))) {
            Object o = ois.readObject();
            System.out.println(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```





## 8.终极打印流（写数据专用）

示例：可以写任何数据，和`sout`一样使用，任何东西都能打印

```java
    public static void main(String[] args) {
        try ( PrintStream ps=new PrintStream("2.txt");){
           ps.println("mmm");
           ps.println(97);
           ps.println(98);
           ps.println('b');
           ps.println(true);
           ps.println(8.7);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```



还可以改变`sout`的输出位置

```java
    public static void main(String[] args) {
        try ( PrintStream ps=new PrintStream("2.txt");){
            System.out.println("我在控制台");
            System.setOut(ps);
            System.out.println("我在文件中");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```





# 六、单元测试

```java
public class TestDemo {
    @Test	//使用该注解表示这是一个测试程序，import org.junit.Test;
    public void testAdd(){
        int result = add(1, 2);
        Assert.assertEquals(2,result);	//对结果使用断言判断，如果错误，则会报如下的形式
    }

    public int add(int a, int b) {
        return a + b;
    }
}
```

![image-20210908222947174](JAVA%E6%9F%A5%E6%BC%8F%E8%A1%A5%E7%BC%BA.assets/image-20210908222947174.png)



如果在测试方法之前需要初始化资源，则可以用@before注解

```java
public class TestDemo {
    int a;

    @Before
    public void init() {
        a = 10;
    }

    @After
    public void close(){
        System.out.println("测试结束");		//方法结束后被打印
    }

    @Test
    public void testAdd() {
        int result = add(1, 2);
        System.out.println(a); //10
        Assert.assertEquals(2, result);
    }

    public int add(int a, int b) {
        return a + b;
    }
}
```











# 七、反射

概念：将类的各个组成部分封装为其他对象。成员变量封装为`Filed[] fileds`，构造方法封装为了`Constuctor[] cons`，成员方法封装为了`Method[] methods`

优点：

1. 可以在程序运行的过程中操作这些对象
2. 解耦，提高程序的可扩展性和灵活性



缺点：

1. 传递的是Object类型，基本类型需要装箱拆箱，其他类型需要强转，会产生额外的内存开销
2. 按名检索类和方法，有一定的时间开销
3. 容易破坏类的结构，干扰原有的内部逻辑



## 1.获取类对象

```java
    public static void main(String[] args) throws ClassNotFoundException {
        Class cls1 = Class.forName("Person");//全类名获取
        Class cls2 = Person.class;	//类名.class获取
        Person p=new Person();
        Class cls3 = p.getClass();		//通过对象获取
    }
```

获取类对象后也能获取全类名：

```java
    public static void main(String[] args) throws Exception {
        Class studentClass = Student.class;
        System.out.println(studentClass.getName()); //com.example.test_rv.Student
    }
```



## 2.获取成员变量

常用方法：（方法的对象名是class对象）

| 方法名                        | 描述                                           | 返回值     |
| ----------------------------- | ---------------------------------------------- | ---------- |
| getFields()                   | 获取所有的pulic成员对象数组                    | Field[]    |
| getField(String name)         | 读取pulic修饰的name成员对象                    | Field 对象 |
| getDeclaredFields()           | 获取所有的pulic成员对象数组                    | Field[]    |
| getDeclaredField(String name) | 读取name成员对象，不论修饰符                   | Field 对象 |
| name.get/set                  | 获取/设置name的属性值，如果是private则暴力反射 |            |
| name.setAccessible(true)      | 暴力反射，忽略安全检查                         |            |

```java
    public static void main(String[] args) throws Exception {
      	//创建Class对象
        Class personClass = Person.class;
      
        //获取所有的public成员变量
        Field[] fields = personClass.getFields();
        Arrays.stream(fields).forEach(o -> System.out.println(o.toString()));
        //public java.lang.String Person.name

        //获取所有的成员变量
        Field[] declaredFields = personClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField);
            //public java.lang.String Person.name
            //int Person.age
        }

        Person person = new Person("张三", 18);

        //获取和设置指定public成员变量的值
        Field name = personClass.getField("name");
        name.set(person, "李四");     //设置值
        System.out.println(person.toString());  //Person{name='李四', age=18}
        System.out.println(name.get(person));   //李四
       

        //暴力反射，获取和指定成员变量的值（无论public）
        Field name = personClass.getDeclaredField("age");
        name.setAccessible(true);   //忽略安全检查
        name.set(person, 22);
        System.out.println(person.toString());  //Person{name='李四', age=22}
    } 
```





## 3.获取构造器

| 方法名                             | 描述               | 参数                                  | 返回值          |
| ---------------------------------- | ------------------ | ------------------------------------- | --------------- |
| getConstructor(Arr_arr)            | 获取指定构造       | 对应构造方法中的类型.class(int.class) | Constructor对象 |
| getConstructors()                  | 获取所有构造       |                                       | Constructor[]   |
| personClass.newInstance()          | 创建无参构造的对象 |                                       | Object对象      |
| constructor.newInstance("张三",20) | 创建对象           | 构造器需要的参数                      | Object对象      |

当然，也可用暴力反射获取private构造。



```java
    public static void main(String[] args) throws Exception {
        Class personClass = Person.class;
        //获取带有两个参数的构造器，一个参数是String类型，一个是int类型
        Constructor constructor = personClass.getConstructor(String.class, int.class);
        System.out.println(constructor);    //public com.example.test_rv.Person(java.lang.String,int)

        Constructor[] constructors = personClass.getConstructors();
        for (Constructor constructor1 : constructors) {
            System.out.println(constructor1);
            //public com.example.test_rv.Person()
            //public com.example.test_rv.Person(java.lang.String,int)
        }

        System.out.println("===============");

        //利用构造器创建对象
        //对象参数不匹配时会报异常 IllegalArgumentException
        Object person = constructor.newInstance("张三",20);
        System.out.println(person.toString());

        //也能这样创建空参构造，但类中必须有空参的构造
        Person p = (Person) personClass.newInstance();
        p.name="王五";
        System.out.println(p.toString());
    }
```





## 4.获取方法

| 方法名                    | 描述                                           | 参数                             | 返回值     |
| ------------------------- | ---------------------------------------------- | -------------------------------- | ---------- |
| getMethod("eat")          | 获取指定成员方法                               | 方法名称，后面可跟参数类型.class | Method对象 |
| eat.invoke(Person person) | 执行方法                                       | 执行方法的对象                   |            |
| getMethods()              | 获取所有的成员方法，包括Object方法和父类的方法 |                                  | Method[]   |
| method.getName()          | 获取方法名称，可用于获取所有方法后的遍历       |                                  | String     |





## 5.简易的框架

首先需要一个配置文件`pro.properties`（如果不是定义在当前目录下，需要定位一下文件的位置），记录下要加载的类名和方法。

```java
className=Person		//全类名
methodName=eat
```

```java
   public static void main(String[] args) throws Exception {
        //1.加载配置文件
     		//1.1获取当前类的类加载器
        ClassLoader classLoader = Main.class.getClassLoader();
     		//1.2用类加载器加载配置文件并获取流数据
        InputStream is = classLoader.getResourceAsStream("pro.properties");
     		//1.3用文件流读取文件的键值对数据
        Properties properties = new Properties();
        properties.load(is);

        //2.获取文件流中读取的信息（即配置文件的数据）
        String className = properties.getProperty("className");
        String methodName = properties.getProperty("methodName");

        //3.获取该类
        Class cls = Class.forName(className);
        //4.创建对象
        Object o = cls.newInstance();
        //5.获取方法
        Method method = cls.getMethod(methodName);

        //执行
        method.invoke(o);
    }
```













# 八、枚举



使用理解：类的对象只有有限个，确定的，用于需要定义一组常量时。比如：星期、季节、支付方式、性别等等。

如果枚举类中只有一个对象，可以使用单例模式。

多个对象用逗号隔开，对象的最后用分号结束。



`JDK 1.5`新增特性，特殊的数据类型，比类多了很多约束，这些约束造就了枚举类型的简洁性、安全性、便捷性



## 1.创建

枚举无法被继承，可以理解为一个final Day类继承了Enum类

枚举中的每个常量成员（大写的字母)，都可以理解为一个该类型的对象

```java
public enum Day {
    MONDAY("星期一", 1),		//成员
    TUESDAY("星期二", 2),
    WEDNESDAY("星期三", 3),
    THURSDAY("星期四", 4),
    FRIDAY("星期五", 5),
    SATURDAY("星期六", 6),
    SUNDAY("星期七", 7);

    private String name;
    private int index;
    
    //构造
    Day(String name, int index) {
        this.name = name;
        this.index = index;
    }
}
```

```java
Day day=Day.MONDAY; 	//引用
```







## 2.部常见方法



### 2.1 enum 中常量的方法：

具体对象的方法

| 方法名            | 返回值  | 说明                                                         |
| ----------------- | ------- | ------------------------------------------------------------ |
| ordinal           | int     | 返回该枚举常量的序数（从0开始，依次排列）                    |
| toString          | String  | 不重写时直接打印出来为该常量本身的形式                       |
| name              | String  | 和不重写的toString相同                                       |
| compareTo         | int     | 比较与指定对象的顺序，返回差值（MONDAY和WEDNESDAY的差值为2） |
| equals            | boolean | 是否相等，比较的是ordinal                                    |
| getDeclaringClass | Class<> | 返回相应的Class对象                                          |

### 2.2 枚举类的方法：

类.方法

| 方法名               | 返回值 | 说明                                                      |
| -------------------- | ------ | --------------------------------------------------------- |
| values               | 数组   | 返回所有的常量数组，直接打印为地址值，需要Arrays.toString |
| valueOf(String name) | 对象   | 根据name返回对应的常量对象                                |



### 2.3 类对象的方法：

```java
//根据枚举常量获取到对应类
Day day=Day.MONDAY;
Class<? extends Day> dayClass = day.getClass();
//根据类获取到所有的枚举实例
Day[] enumConstants = dayClass.getEnumConstants();
System.out.println(Arrays.toString(enumConstants));

//判断一个类是不是枚举
dayClass.isEnum();
```





### 2.4 抽象方法和接口

```java
public enum EnumDemo3 {
    FIRST{
        @Override
        public String getInfo() {
            return "FISET TIME";
        }
    };
    
    public abstract String getInfo();
}
```

通过这样的方式，就可以让每个实例，具有不同的行为方式





Enum类可以实现接口，接口的方法需要在 【每个成员对象的实例】 或 【整个类】中被实现一次：

```java
public enum EnumDemo3 implements Food{
    FIRST{
     
    },
    SECOND{
        @Override
        public void eat() {
            System.out.println("SECOND EAT");
        }
    };

    @Override
    public void eat() {
        System.out.println("ENUM EAT");
    }
}
```



## 3.单例模式

单例模式：确保某个类只有一个实例，自行实例化并向整个系统提供

线程池、缓存、日至对象、对话框常被设计成单例模式







### 3.1 常规的单例模式

- 饿汉式（基于classloder机制避免了多线程的同步问题）

无法做到延迟创建对象，该单例设计资源较多时，创建耗时

```java
public class SingletonHungry {

    private static SingletonHungry instance = new SingletonHungry();

    private SingletonHungry() {
    }

    public static SingletonHungry getInstance() {
        return instance;
    }
}
```



- 懒汉式单例模式（适合多线程安全）

多线程中能很好地避免同步问题，同时具备懒加载机制，但由于synchronized，效率很低，单线程时完全可以去掉

```java
public class SingletonLazy {

    private static volatile SingletonLazy instance;

    private SingletonLazy() {
    }

    public static synchronized SingletonLazy getInstance() {
        if (instance == null) {
            instance = new SingletonLazy();
        }
        return instance;
    }
}
```



- 双重检查锁

```java
public class Singleton {
    private static volatile Singleton singleton = null;

    private Singleton(){}

    public static Singleton getSingleton(){
        if(singleton == null){  //第一次检查
            synchronized (Singleton.class){
                if(singleton == null){	//第二次检查
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }    
}
```



volatile：可见性，禁止指令重排序优化

可见性是指在一个线程中对该变量的修改会马上由工作内存写回主内存，所以其它线程会马上读取到已修改的值，关于工作内存和主内存可简单理解为高速缓存（直接与CPU打交道）和主存（日常所说的内存条），注意工作内存是线程独享的，主存是线程共享的。

我们写的代码（特别是多线程代码），由于编译器优化，在实际执行的时候可能与我们编写的顺序不同。编译器只保证程序执行结果与源代码相同，却不保证实际指令的顺序与源代码相同，这在单线程并没什么问题，然而一旦引入多线程环境，这种乱序就可能导致严重问题。volatile关键字就可以从语义上解决这个问题，值得关注的是volatile的禁止指令重排序优化功能在Java 1.5后才得以实现，因此1.5前的版本仍然是不安全的，即使使用了volatile关键字。






后面还有几种方式，再说我听不懂的话，这里就先不展开了。







### 3.2 枚举单例

枚举单例的写法，不用考虑序列化和反射的问题。枚举序列化是由JVM保证的，每一个枚举类型和定义的枚举变量在JVM中都是唯一的，在枚举类型的序列化和反序列化上，Java做了特殊的规定：在序列化时Java仅仅是将枚举对象的name属性输出到结果中，反序列化的时候则是通过java.lang.Enum的valueOf方法来根据名字查找枚举对象。同时，编译器是不允许任何对这种序列化机制的定制的并禁用了writeObject、readObject、readObjectNoData、writeReplace和readResolve等方法，从而保证了枚举实例的唯一性。

```java
public enum  SingletonEnum {
    INSTANCE;	//单例
    
    private String name;
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
}
```

使用时直接使用类名.INSTANCE，接下来就可以调用其中的各种方法

