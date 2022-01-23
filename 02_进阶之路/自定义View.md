# 一、关于动画



## 1、Android 的三种动画：

### 1.补间动画

- View Animation
  - 只改变了View的绘制效果，属性值不变。AnimationSet类和Animation的子类。通常在res/anim下定义
  - 这种动画属于普通动画，View移动了，但是View本身还在原地，点击原坐标仍然可以触发点击事件

```xml
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:fillAfter="true">	<!-- android:fillAfter="true"表示动画结束后不回到原位-->
    <translate
        android:duration="1000"
        android:fromXDelta="0"
        android:toXDelta="300" />
</set>
```

```java
 btn.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate));
```



```java
//纯代码的方式：
int startDegress = 0;
int endDegress = 90;

text.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        RotateAnimation animation = new RotateAnimation(startDegress, endDegress, v.getWidth() / 2, v.getHeight());
        animation.setDuration(500);
        animation.setFillAfter(true);	//保持动画结束的位置
        animation.setStartOffset(500);	//延迟执行时间
        v.startAnimation(animation);

        startDegress = endDegress;
        endDegress += 90;
    }
});
```



### 2.帧动画

- Drawable Animation
  - 加载一系列Drawble资源，使用AnimationDrawable类，在res/drawable 下定义



### 3.属性动画

- Property Animation（3.0后引入）

  - 动画的对象由View改为了Object，动画后Object的值被改变了，任何时候View属性的改变，都能自动调用invalidate() 来刷新




#### ①平移

```java
final TextView text = findViewById(R.id.text);
text.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //translationX就是补间动画的setxxx的那部分，所以也可以用来制作平移等动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationX", 0, v.getWidth());  
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(v, "translationY", 0, v.getHeight());
        AnimatorSet set = new AnimatorSet(); //设置一个动画集合一起播放
        set.playTogether(animator, animator2);
        set.setDuration(500);
        set.start();
    }
});
```

或者这样的写法：

```java
final TextView text = findViewById(R.id.text);
text.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        v.animate()
            .translationXBy(v.getWidth())
            .translationYBy(v.getHeight())
            .setDuration(2000)
            //在结束位置带有动画效果，弹几下
            .setInterpolator(new BounceInterpolator()) 
            .start();
    }
});
```

> translationX  也可以换成X， 表示最终的位置



#### ②旋转

围绕中心旋转

```java
btn.setOnClickListener(v -> {
    ObjectAnimator animator = ObjectAnimator.ofFloat(btn, "rotation", btn.getRotation()+180);
    //……
});
```

> rotationX ，rotationY 围绕X/Y轴旋转



#### ③透明度

```java
ObjectAnimator animator = ObjectAnimator.ofFloat(btn, "alpha",0,0.5f);
```



#### ④数值改变动画

```JAVA
private void changeTextValue(TextView textView){
    ValueAnimator valueAnimator=ValueAnimator.ofFloat(0,100);
    valueAnimator.setTarget(textView);
    valueAnimator.setDuration(2000).start();
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float value= (float) animation.getAnimatedValue();
            textView.setText((int)value+"");
        }
    });
}
```





#### ⑤注意事项

要操作的属性必须有get /set 方法，不然属性动画无法生效，如果没有可以通过自定义一个属性类或包装类间接地给它增加get和set方法。



```java
public class MyView {
    private View mTarget;

    public MyView(View mTarget) {
        this.mTarget = mTarget;
    }

    public int getWidth() {
        return mTarget.getLayoutParams().width;
    }

    public void setWidth(int width) {
        mTarget.getLayoutParams().width = width;
        mTarget.requestLayout();
    }

    public int getHeight() {
        return mTarget.getLayoutParams().height;
    }

    public void setHeight(int height) {
        mTarget.getLayoutParams().height = height;
        mTarget.requestLayout();
    }
}
```

```java
ImageView imageView=findViewById(R.id.img);
MyView myView=new MyView(imageView);
ObjectAnimator.ofInt(btn,"width",500).setDuration(2000).start();
ObjectAnimator.ofInt(btn,"height",500).setDuration(2000).start();
```





### 4.动画监听

动画有四个过程：Start、Repeat、End、Cancel

```java 
ObjectAnimator animator=ObjectAnimator.ofFloat(btn,"alpha",1.5f);
animator.addListener(new Animator.AnimatorListener() {
    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
});
```

 也可以用适配器选择必要地事件进行监听：

```java
ObjectAnimator animator=ObjectAnimator.ofFloat(btn,"alpha",1.5f);
animator.addListener(new AnimatorListenerAdapter() {
    @Override
    public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
    }
});
```









## 2、ScrollTo和ScrollBy

如果在ViewGroup中使用，则移动的是其所有的子view。

> 如果直接对一个控件（如Button），使用btn.scrollTo()，则移动的是Button中的字体
>
> 它是移动屏幕，而不是控件，所以X,Y的值为负数是右下移动，正数则是左上移动
>
> 它是瞬间完成的动作

| 方法名          | 具体功能                         |
| --------------- | -------------------------------- |
| scrollTo(x,y)   | 移动到一个具体的坐标             |
| scrollBy(dx,dy) | 移动增量（最终也是调用scrollTo） |

例如：

```java
//使btn的父布局整个向下偏移300，300个像素
btn.setOnClickListener(v -> {
    ((View)btn.getParent()).scrollTo(-300,-300);
});
```



如果想要有过渡的滑动，可以使用Scroller配合完成

```java
public class CustomView extends View {
    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context); //初始化mScroller
    }

    private Scroller mScroller;

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            //不断获取当前的滑动值，并用invalidate不断重新绘制
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    //暴露给外部的移动动画
    public void smoothScrollTo(int destX,int dextY){
        int scrollX=getScrollX();
        int scroolY=getScrollY();
        mScroller.startScroll(scrollX,scroolY,destX-scrollX,dextY-scroolY,2000);
        invalidate();
    }
}
```

```java
customView.smoothScrollTo(-400,-400);
```





















# 二、自定义View的点击事件



## 1、View被覆盖

可能是外层的view覆盖了内层的view，内层的View点击事件就会失效。

> 布局中，写在上面的控件优先加载，所以注意嵌套关系。



## 2、点击和触摸同时存在

因为事件会被消费，点击和触摸同时存在时，优先触发触摸事件。

> 如果必须都有，则可以使用全局变量判断是触发何种事件。
>
> 在MotionEvent.ACTION_DOWN 中，设置为点击事件可用
>
> 在MotionEvent.ACTION_MOVE 中，根据实际情况的变量，设置不可用





## 3、setEnabled(false) 后仍可点击

view.setEnabled(false) 后，仍然可以点击的原因：可能是该view继承自viewgroup，代码设置了viewgroup不能够点击，但其中的view子类仍然能够点击。

所以，需要遍历其子项，禁用所有的子view：

```java
for (int i = 0; i < viewGroup.getChildCount(); i++) {
    View child=viewGroup.getChildAt(i);
    child.setEnabled(false);
}
```







#    三、自定义View



## 0、重要方法：

① 三个构造参数

context：用于在Activity直接new对象

context ,attrs ：用于在xml中实例化对象

context ,attrs ,style ：用于在xml中实例化对象，并带有style样式

> 为什么能在XML中通过全类名找到对应的类文件？原理就是反射



②关于坐标和布局

| 方法名                                                     | 作用                           |
| ---------------------------------------------------------- | ------------------------------ |
| getX                                                       | 相对容器内的X距离              |
| getRowX                                                    | 相对屏幕内的X距离              |
|                                                            |                                |
| invalidate();                                              | 刷新view                       |
| layout(left,top,right,bottom);                             | 根据四个参数重新放置view的坐标 |
| offsetLeftAndRight (offsetX) / offsetTopAndBottom(offsetY) | 偏移横坐标 /纵坐标             |
|                                                            |                                |



③关于控件自身

| 方法名                        | 作用                                           |
| ----------------------------- | ---------------------------------------------- |
| getLayoutParams();            | 获取控件的参数，可以是本身的，也可以是父控件的 |
| setLayoutParams(layoutParams) | 设置控件的参数                                 |

```java
//本身的
ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
layoutParams.leftMargin = getLeft() + offsetX;
layoutParams.topMargin = getLeft() + offsetY;
setLayoutParams(layoutParams);
//父控件的，注意要和父控件的类型一致ConstraintLayout
ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) getLayoutParams();
layoutParams.leftMargin = getLeft() + offsetX;
layoutParams.topMargin = getTop() + offsetY;
setLayoutParams(layoutParams);
```



**小技巧：**

① 自定义View中用来加载位图图片的方法

```java
Bitmap  backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
canvas.drawBitmap(backgroundBitmap, 0, 0, paint); 
```



**重要的方法：**



②onMeasure ：测量视图的宽高

​		通常，自定义ViewGroup中，需要使用循环测量子View，测量的时候测量多次，参数widthMeasureSpec是父View给当前视图的宽高和模式。

​		系统中onMeasure：先根据widthMeasureSpec求得父View的宽高和模式，根据自身宽度和padding 计算子View可以拥有的宽高，根据结果求得子view的宽高和模式（MeasureSpec.makeMeasureSpec(newsize,newMode)），用新的MeasureSpec计算子View。

​		如果父容器的模式为wrapcontent，则会优先计算子view的大小，其余模式则优先计算父容器的大小。

③onLayout：指定控件的位置

​		一般View不用写这个方法，ViewGroup的时候才需要，一般View不需要重写该方法

④onDraw ：根据上面两个方法的参数绘制图形











## 1、PopupWindow

```java
public class Main2Activity extends AppCompatActivity {
    PopupWindow popupWindow;
    private ListView listView;
    private ArrayList<String> msgs = new ArrayList<>();
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //触发popwindow的点击事件
        final TextView textView = findViewById(R.id.textview);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null) {
                    popupWindow = new PopupWindow();
                    popupWindow.setWidth(textView.getWidth());
                    popupWindow.setHeight(1000);
                    popupWindow.setContentView(listView);
                    popupWindow.setFocusable(true);
                }
                //popWindow展示在哪个控件上，偏移量多少
                popupWindow.showAsDropDown(textView, 0, 0); 
            }
        });

        //准备listview的数据
        for (int i = 0; i < 100; i++) {
            msgs.add(i + "--a--" + i);
        }
        myAdapter = new MyAdapter();
        listView = new ListView(this);
        listView.setAdapter(myAdapter);
        //子项的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String msg = msgs.get(position);
                textView.setText(msg);
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return msgs.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            //加载布局
            if (convertView == null) {
                convertView = View.inflate(Main2Activity.this, R.layout.item, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_msg = convertView.findViewById(R.id.tv_msg);
                viewHolder.iv_delete = convertView.findViewById(R.id.iv_delete);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //根据位置加载数据
            final String msg = msgs.get(position);
            viewHolder.tv_msg.setText(msg);
            viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    msgs.remove(msg);
                    myAdapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }

    static class ViewHolder {
        TextView tv_msg;
        ImageView iv_delete;
    }
}
```









## 2、滑块

```java
public class MyToggleButton extends View implements View.OnClickListener {

    private Bitmap backgroundBitmap; //背景开关图
    private Bitmap slidingBitmap;   //滑块图
    private int slidLeftMax;    //距离左边的最大距离，为【背景宽度-滑块宽度】
    private Paint paint;
    private int slideLeft;  //滑动距离左边的距离

    private float startX;   //滑动时的起始坐标
    private float lastX;    //滑动的终止坐标

    private boolean isOpen = false; //开关状态

    //一参构造用于在Activity直接new对象
    public MyToggleButton(Context context) {
        super(context);
    }

    //二参构和三参构造造用于在xml中实例化对象，三参带style属性
    public MyToggleButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
        //Bitmap加载位图信息
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
        slidingBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);
        //初始化左边最大距离
        slidLeftMax = backgroundBitmap.getWidth() - slidingBitmap.getWidth();
        setOnClickListener(this);
    }


    /**
     * true:点击事件生效，滑动事件不生效
     * false:点击事件不生效，滑动事件生效
     */
    private boolean isEnableClick = true;
    
    /**
     * 点击事件中根据状态判断左滑的距离
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (isEnableClick) {
            isOpen = !isOpen;
            flushView();
        }
    }


    /**
     * 测量视图的宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(backgroundBitmap.getWidth(), backgroundBitmap.getHeight());
    }


    /**
     * 绘制图形
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(backgroundBitmap, 0, 0, paint); //画出背景图
        canvas.drawBitmap(slidingBitmap, slideLeft, 0, paint);  //画出滑块图
    }

    
    //getX 是相对容器内的X距离
    //getRowX 是相对屏幕内的X距离
    //手指按下时，记录初始的X坐标，并禁止点击事件
    //手指移动时，计算偏移量（滑块现在的距离）【防止越界】，并刷新数据
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);//执行父类的方法
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //1.记录按下的坐标
                lastX = startX = event.getX();
                isEnableClick = true;
                break;
            case MotionEvent.ACTION_MOVE:
                //2.计算结束值
                float endX = event.getX();
                //3.计算偏移量
                float distanceX = endX - startX;
//                slideLeft = (int) (slideLeft + distanceX);
                slideLeft += distanceX;
                //4.屏蔽非法值
                if (slideLeft < 0) {
                    slideLeft = 0;
                } else if (slideLeft > slidLeftMax) {
                    slideLeft = slidLeftMax;
                }
                //5.刷新
                invalidate();

                //6.数据还原
                startX = event.getX();
                //只要距离大于5，就认为是滑动事件
                if (Math.abs(endX - lastX) > 5) {
                    isEnableClick = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isEnableClick) {
                    //超过一半距离松手的话，直接滑到另一边
                    if (slideLeft > slidLeftMax / 2) {
                        //显示按钮开
                        isOpen = true;
                    } else {
                        isOpen = false;
                    }
                    flushView();
                }
                break;
        }
        return true;
    }


    private void flushView() {
        if (isOpen) {
            slideLeft = slidLeftMax;
        } else {
            slideLeft = 0;
        }
        invalidate();
    }
}
```







## 3、自定义属性

类似在XML布局文件中引用的，app命名空间的，都是自定义的属性：

```xml
  xmlns:app="http://schemas.android.com/apk/res-auto"
```



比如此时我自定义了一个控件，引入了app命名空间的my_age，my_bg ，my_name等属性

```xml
 <com.atguigu.autoattribute.MyAttributeView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:my_age="100"
        app:my_bg="@drawable/jtx"
        app:my_name="android0220"/>
```



此时，需要在res/attr中创建一个属性集合，定义申明的自定义属性：

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!--定义名字叫MyAttributeView属性集合-->
    <declare-styleable name="MyAttributeView">
        
        <!--定义一个名字叫my_name并且类型是string的属性-->
        <attr name="my_name" format="string"/>
        
        <!--定义一个名字叫my_age并且类型是integer的属性-->
        <attr name="my_age" format="integer"/>
        
        <!--定义一个名字叫my_bg并且类型是reference|color的属性-->
        <attr name="my_bg" format="reference|color"/>
    </declare-styleable>
</resources>
```



之后，就可以在自定义的View中，获取该属性并绘制相应的内容：

```java
public class MyAttributeView extends View {
    private int myAge;
    private String myName;
    private Bitmap myBg;

    public MyAttributeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //获取属性三种方式
        //1.用命名空间取获取
        String age = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","my_age");
        String name = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","my_name");
        String bg = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","my_bg");
//        System.out.println("age=="+age+",name=="+name+",bg==="+bg);

        //2.遍历属性集合
        for(int i=0;i<attrs.getAttributeCount();i++){
            System.out.println(attrs.getAttributeName(i)+"====="+attrs.getAttributeValue(i));
        }

        //3.使用系统工具，获取属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.MyAttributeView);
       for(int i=0;i<typedArray.getIndexCount();i++){
          int index =  typedArray.getIndex(i);

           switch (index){
               case R.styleable.MyAttributeView_my_age:
                   myAge = typedArray.getInt(index,0);
                   break;
               case R.styleable.MyAttributeView_my_name:
                   myName = typedArray.getString(index);
                   break;
               case R.styleable.MyAttributeView_my_bg:
                   Drawable drawable = typedArray.getDrawable(index);
                   //BitmapDrawable是Drawable子类
                   BitmapDrawable drawable1 = (BitmapDrawable) drawable;
                   myBg = drawable1.getBitmap();
                   break;
           }
       }
        // 记得回收
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        canvas.drawText(myName+"---"+myAge,50,50,paint);
        canvas.drawBitmap(myBg,50,50,paint);
    }
}
```





## 4、实现手势操作

```java
//1.定义变量 
private MyScroller scroller;
 
 private void initView(final Context context) {
        //2.实例化手势识别器
        detector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                Toast.makeText(context,"长按",Toast.LENGTH_SHORT).show();
            }

            /**
             *
             * @param e1
             * @param e2
             * @param distanceX 在X轴滑动了的距离
             * @param distanceY 在Y轴滑动了的距离
             * @return
             */
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                /**
                 *x:要在X轴移动的距离，正为右移
                 *y:要在Y轴移动的距离，正为下移
                 */
                scrollBy((int)distanceX,0);
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Toast.makeText(context,"双击",Toast.LENGTH_SHORT).show();
                return super.onDoubleTap(e);
            }
        });
    }

//3.在onTouchEvent()把事件传递给手势识别器
      @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        //3.把事件传递给手势识别器
        detector.onTouchEvent(event);
        return true;
    }
```









# 四、ViewGroup的事件传递和消费

![image-20211205214727738](%E8%87%AA%E5%AE%9A%E4%B9%89View.assets/image-20211205214727738.png)

dispathTouchEvent 用于事件分发，所有事件都必须经过这个方法的分发，然后决定是自身消费当前事件还是继续往下分发给子控件处理。true表示不继续分发，事件没有被消费。viewGroup中分发给**onInterceptTouchEvent**判断是否拦截该事件，true表示拦截当前事件，不继续往下分发，交给onTouchEvent处理。**onTouchEvent 返回true表示消费当前事件，false则交给子控件继续分发**



当所有函数不做处理时，事件的传递：

<img src="%E8%87%AA%E5%AE%9A%E4%B9%89View.assets/image-20211205220011333.png" alt="image-20211205220011333" style="zoom:50%;" />

当子View的onTouchEvent 返回true时： 向上传递的down事件消失

<img src="%E8%87%AA%E5%AE%9A%E4%B9%89View.assets/image-20211205220429680.png" alt="image-20211205220429680" style="zoom:50%;" />





比如，现在自定义了一个ViewPager，其中的某一页存在上下拖动的ScrollView，那么在ScrollView上左右滑动的时候，就不会切换ViewPager的页面，因为滑动事件被ScrollView拦截消费了。

此时，就需要在自定义的Viewpager中，判断是否继续向下传递水平滑动的事件。

```java
    float startX, startY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
        boolean result = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = ev.getX();
                float endY = ev.getY();
                //差值计算
                float distanceX = Math.abs(endX - startX);
                float distanceY = Math.abs(endY - startY);
                //distanceX>5是防止误触
                if (distanceX > distanceY && distanceX>5) {
                    result = true;
                }
                break;
        }
        return result;
    }
```

> 注意，如果自定义View中还传递了手势，需要把ev事件继续往下传，detector.onTouchEvent(ev)，否则会出现手势识别器，不能识别MotionEvent.ACTION_DOWN的情况

















































