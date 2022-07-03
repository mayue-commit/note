

# 一、自定义View

关于paint和canvas：

paint 可以设置画笔的颜色，抗锯齿，文字大小，样式等等

canvas中封装了不同的方法，可以画bitmap，各类形状，文字等。另外，canvas是分层的，并且能够裁剪。

```java
canvas.save();
//这之间的代码在一个图层
canvas.restore();

canvas.clipRect(rect); //裁剪一个形状，只显示形状内的内容
canvas.clipOutRect(rect); //挖一个洞，显示区域外的内容
```





## 0、重要函数列表

View的函数：

| 函数名                                  | 作用                         |
| --------------------------------------- | ---------------------------- |
| int  getWidth（） /  int  getHeight（） | 获取宽/高                    |
| layout（L,T,R,B）                       | 重新放置View                 |
| invalidate()                            | 让View调用ondraw导致重新绘制 |



ViewGroup的函数

| 函数名                                 | 作用                             |
| -------------------------------------- | -------------------------------- |
| dispatchTouchEvent（MotionEvent e）    | 事件分发                         |
| onInterceptTouchEvent（MotionEvent e） | 事件的拦截，在上个方法中调用     |
| onTouchEvent（MotionEvent e）          | 处理点击事件，在第一个方法中调用 |











## 1、View基础

### 1、坐标系

<img src="%E8%87%AA%E5%AE%9A%E4%B9%89View.assets/image-20220511232117608.png" alt="image-20220511232117608" style="zoom:67%;" />

触摸点位通过 event 事件来获取 View 的坐标



### 2、View的滑动

①` layout（L,T,R,B）`

​	计算出需要滑动的距离，再通过`layout() `重新放置	

② `offsetLeftAndRight(OffsetX)`  和  ` offsetTopAndButtom(OffsetY)`

​    根据`offsetX`和`Y`的值移动`View`

③`LayoutParams` 和 `ViewGroup.MarginLayoutParams`

```java
//使用LayoutParams，由于父布局是LinearLayout，所以采用LinearLayout.LayoutParams
LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) getLayoutParams();
layoutParams.leftMargin = getLeft() + offsetX;
layoutParams.topMargin = getTop() + offsetY;
setLayoutParams(layoutParams);
```

```java
//使用MarginLayoutParams，后续一致
ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
```

④属性动画

```java
ObjectAnimator.ofFloat(view, "translationX", 200, 500)
    .setDuration(500)
    .start();
```



⑤ `scrollTo(offsetX,offsetY)` 和 `scrllBy(offsetX,offsetY)`

​	1.移动`View`的内容，如果是在`ViewGroup`中，则移动的是所有子`View`。

​	2.它的参考系为画布，所以和正常移动的值相反（左正，右负，上正，下负）。

​	3.瞬间完成

```
 ((View)getParent()).scrollBy(-offsetX,-offsetY);
```

想要达到逐步绘制的效果，就需要借助Scroller完成。案例：eg1







## 2、详解属性动画

动画的组合：

```java
AnimatorSet set = new AnimatorSet(); //设置一个动画集合一起播放
set.playTogether(animator, animator2);
//set.play(animator).with(animator2).after(animator3);
set.setDuration(500);
set.start();
```







使用属性动画，==要操作的属性必须有get和set方法==，否则无法生效。但可以自定义一个包装类，增加get和set方法。

```java
public  class MyView {
    private View targetView;

    public MyView(View targetView) {
        this.targetView = targetView;
    }

    //自定义的get/set
    public int  getWidth(){
        return targetView.getLayoutParams().width;
    }

    public void setWidth(int width){
        targetView.getLayoutParams().width=width;
        targetView.requestLayout();
    }
}
```

```java
CustomView customView = findViewById(R.id.customview);
MyView myView=new MyView(customView);

//width属性
ObjectAnimator.ofInt(myView, "width", 200, 500)
    .setDuration(500)
    .start();
```





### ①动画插值器：

可以知道当前动画改变的数值，从而更新UI

```java
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
```



### ②动画过程和组合

动画有四个过程：Start、Repeat、End、Cancel，可以选择事件进行监听

```java
ObjectAnimator animator=ObjectAnimator.ofFloat(btn,"alpha",1.5f);
animator.addListener(new AnimatorListenerAdapter() {
    @Override
    public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
    }
});
```



### ③其他各类属性动画

平移：

```java
ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationX", 0, v.getWidth()); 
```



旋转：

```java
ObjectAnimator animator = ObjectAnimator.ofFloat(btn, "rotation", btn.getRotation()+180);
```



透明度：

```
ObjectAnimator animator = ObjectAnimator.ofFloat(btn, "alpha",0,0.5f);
```









## 3、事件分发

![image-20211205214727738](%E8%87%AA%E5%AE%9A%E4%B9%89View.assets/image-20211205214727738-16235945002822.png)

当所有函数不做处理时，事件的传递：

<img src="%E8%87%AA%E5%AE%9A%E4%B9%89View.assets/image-20211205220011333-16235945002821.png" alt="image-20211205220011333" style="zoom:50%;" />

当子View的onTouchEvent 返回true时： 向上传递的down事件消失

<img src="%E8%87%AA%E5%AE%9A%E4%B9%89View.assets/image-20211205220429680-16235945002913.png" alt="image-20211205220429680" style="zoom:50%;" />





## 4、自定义属性讲解

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
                   myName = typedArray.getString(index); //获取自定义属性的值
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







## 5、自定义View

MeasureSpec：它是当前视图的宽高和模式。前2个字节表示模式，后30位表示大小。

onMeasure：先根据widthMeasureSpec求得父View的宽高和模式，根据自身宽度和padding 计算子View可以拥有的宽高，根据结果求得子view的宽高和模式（MeasureSpec.makeMeasureSpec(newsize,newMode)），用新的MeasureSpec计算子View。

> 如果父容器的模式为wrapcontent，则会优先计算子view的大小，其余模式则优先计算父容器的大小。







1、直接继承自View的自定义View，在源码中getRootMeasureSpec方法里，可以看到 WRAP_CONTENT 的 的MeasureSpec 是AT_MOST，所以自定义View的时候，需要在onMeasure 中单独处理 WRAP_CONTENT  属性值。

2、自定义View，如果在没有在onDraw方法里处理 padding ，则XML设置padding不会生效

```java
public class RectView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mColor=Color.RED;
    
    public RectView(Context context) {
        super(context);
        initDraw();
    }

    public RectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDraw();
    }

    public RectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDraw();
    }

    private void initDraw() {
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth((float) 1.5);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize=MeasureSpec.getSize(heightMeasureSpec);
       
        //处理 WRAP_CONTENT 的默认值为600px
       if(widthSpecMode==MeasureSpec.AT_MOST&&heightSpecMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(600,600);
        }else if(widthSpecMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(600,heightSpecSize);
        }else if(heightSpecMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize,600);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        //计算padding
        canvas.drawRect(0 + paddingLeft, 0 + paddingTop, width + paddingLeft, height + paddingTop, mPaint);
    }
}
```





### 1.点击事件可能不生效的原因：



#### ①View被覆盖

可能是外层的view覆盖了内层的view，内层的View点击事件就会失效。

> 布局中，写在上面的控件优先加载，所以注意嵌套关系。



#### ②点击和触摸同时存在

因为事件会被消费，点击和触摸同时存在时，优先触发触摸事件。

> 如果必须都有，则可以使用全局变量判断是触发何种事件。
>
> 在MotionEvent.ACTION_DOWN 中，设置为点击事件可用
>
> 在MotionEvent.ACTION_MOVE 中，根据实际情况的变量，设置不可用





#### ③setEnabled(false) 后仍可点击

view.setEnabled(false) 后，仍然可以点击的原因：可能是该view继承自viewgroup，代码设置了viewgroup不能够点击，但其中的view子类仍然能够点击。

所以，需要遍历其子项，禁用所有的子view：

```java
for (int i = 0; i < viewGroup.getChildCount(); i++) {
    View child=viewGroup.getChildAt(i);
    child.setEnabled(false);
}
```



### 2.加载位图图片的方法

```java
Bitmap  backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
canvas.drawBitmap(backgroundBitmap, 0, 0, paint); 
```

















# 二、经典案例

## eg1、逐帧绘制动画



Scroller 和 scrollTo 完成逐帧的动画效果

```java
public class CustomView extends View {
    private int lastX;
    private int lastY;
    private Scroller mScroller;

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    public CustomView(Context context) {
        super(context);
    }


    public boolean onTouchEvent(MotionEvent event) {
        //获取到手指处的横坐标和纵坐标
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算移动的距离
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                //使用scrollBy 完成移动
                ((View)getParent()).scrollBy(-offsetX,-offsetY);
                break;
        }
        return true;
    }

    ///重写computeScroll，并使用Scroller对象完成逐帧的绘制
    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            ((View) getParent()).scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            //通过不断的重绘不断的调用computeScroll方法
            invalidate();
        }

    }
}
```



invalidate  ->  ondraw   ->  computeScroll  ->  scrollTo





















## eg2、自定义流式布局

```java
public class FlowLayout extends ViewGroup {
    private static final String TAG = "FlowLayout";
    private int mHorizontalSpacing = dp2px(16); //每个item横向间距
    private int mVerticalSpacing = dp2px(8); //每个item横向间距

    private List<List<View>> allLines = new ArrayList<>(); // 记录所有的行，一行一行的存储，用于layout
    List<Integer> lineHeights = new ArrayList<>(); // 记录每一行的行高，用于layout


    public FlowLayout(Context context) {
        super(context);
//        initMeasureParams();
    }

    //反射
    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
//        initMeasureParams();
    }

    //主题style
    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        initMeasureParams();
    }
    //四个参数 自定义属性

    private void clearMeasureParams() {
        allLines.clear();
        lineHeights.clear();
    }

    //度量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //TODO：0.初始化工作
        clearMeasureParams();//内存 抖动

        //先度量孩子
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        //解析父亲给我的宽高
        int selfWidth = MeasureSpec.getSize(widthMeasureSpec);  //ViewGroup解析的父亲给我的宽度
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec); // ViewGroup解析的父亲给我的高度

        List<View> lineViews = new ArrayList<>(); //保存一行中的所有的view
        int lineWidthUsed = 0; //记录这行已经使用了多宽的size
        int lineHeight = 0; // 一行的行高

        int parentNeededWidth = 0;  // measure过程中，子View要求的父ViewGroup的宽
        int parentNeededHeight = 0; // measure过程中，子View要求的父ViewGroup的高

        //TODO: 1:度量孩子 ，viewpager是先度量自己
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

            LayoutParams childLP = childView.getLayoutParams();
            if (childView.getVisibility() != View.GONE) {
                //1.1、measure测量孩子的宽高
                //将layoutParams转变成为 measureSpec
                int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight,
                        childLP.width);
                int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom,
                        childLP.height);
                childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);

                //2.2、获取子view的度量宽高，判断是否超过父亲的宽度，超过则换行
                int childMesauredWidth = childView.getMeasuredWidth();
                int childMeasuredHeight = childView.getMeasuredHeight();

                //2.2.2 行满需要换行
                if (childMesauredWidth + lineWidthUsed + mHorizontalSpacing > selfWidth) {

                    //一旦换行，我们就可以判断当前行需要的宽和高了，所以此时要记录下来
                    allLines.add(lineViews);
                    lineHeights.add(lineHeight);


                    parentNeededHeight = parentNeededHeight + lineHeight + mVerticalSpacing;
                    parentNeededWidth = Math.max(parentNeededWidth, lineWidthUsed + mHorizontalSpacing);

                    lineViews = new ArrayList<>();
                    lineWidthUsed = 0;
                    lineHeight = 0;
                }

                //2.2.1 不换行
                // view 是分行layout的，所以要记录每一行有哪些view，这样可以方便layout布局
                lineViews.add(childView);
                //每行都会有自己的宽和高，计算本行中的宽度和高度
                lineWidthUsed = lineWidthUsed + childMesauredWidth + mHorizontalSpacing;
                lineHeight = Math.max(lineHeight, childMeasuredHeight);

                //2.2.3、处理最后一行数据
                if (i == childCount - 1) {
                    allLines.add(lineViews);
                    lineHeights.add(lineHeight);
                    parentNeededHeight = parentNeededHeight + lineHeight + mVerticalSpacing;
                    parentNeededWidth = Math.max(parentNeededWidth, lineWidthUsed + mHorizontalSpacing);
                }

            }
        }

        //TODO 2:再度量自己,保存
        //根据子View的度量结果，来重新度量自己ViewGroup
        // 作为一个ViewGroup，它自己也是一个View,它的大小也需要根据它的父亲给它提供的宽高来度量
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int realWidth = (widthMode == MeasureSpec.EXACTLY) ? selfWidth: parentNeededWidth;
        int realHeight = (heightMode == MeasureSpec.EXACTLY) ?selfHeight: parentNeededHeight;
        setMeasuredDimension(realWidth, realHeight);
    }

    //布局，ViewGroup当中才需要布局
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int lineCount = allLines.size();

        int curL = getPaddingLeft();
        int curT = getPaddingTop();

        //遍历行
        for (int i = 0; i < lineCount; i++){
            //获取到每一行的View
            List<View> lineViews = allLines.get(i);
            int lineHeight = lineHeights.get(i);

            //遍历列
            for (int j = 0; j < lineViews.size(); j++){
                View view = lineViews.get(j);
                //当前View的左上坐标
                int left = curL;
                int top =  curT;
                //当前View的右下坐标
                 int right = left + view.getMeasuredWidth();
                 int bottom = top + view.getMeasuredHeight();
                 //放置这个View
                 view.layout(left,top,right,bottom);
                 //下个View得左边的要加上间隙
                 curL = right + mHorizontalSpacing;
            }

            //遍历完当前行的所有列后，计算下一行的 左上角坐标
            curT = curT + lineHeight + mVerticalSpacing;
            curL = getPaddingLeft();
        }

    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//    }

    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

}
```









## eg3、将文字绘制到中心

如何将文字绘制到一个View的中心？

```java
//绘制文字
Paint paint = new Paint();
paint.setTextSize(80);
//文字居中对其显示
paint.setTextAlign(Paint.Align.CENTER);
//通过xy坐标设置位置
canvas.drawText(mText,getWidth()/2,getHeight()/2,paint);
```

但此时文字并没在view的正中心，这是因为baseLine。baseline 是文字的基准线，大概是四线三格中的第三条线的位置。drawText的Y坐标，是baseLine 的Y 坐标

![image-20220614225317626](%E8%87%AA%E5%AE%9A%E4%B9%89View.assets/image-20220614225317626.png)



想要改变，这个baseLine，则需要用到`Paint.FontMetrics`，它记载了四个参数：

```java
public float top;  //从baseline到文字最顶端的高度
public float bottom; 
public float ascent; //从baseline到文字的真实高度
public float descent;
public float leading;//额外行间距，上下相邻的两行，top和bottom的之间的距离
```

![image-20220613232624267](%E8%87%AA%E5%AE%9A%E4%B9%89View.assets/image-20220613232624267.png)

由于android 坐标系，上负下正，文字的高度为 `(descent-ascent )`

如果以文字的Y轴中心点绘制，文字的Y坐标为：getHeight() / 2 +(descent-ascent )/2

以中心点计算文字高度和以baseline计算文字高度，相差了descent长度，需要向上移动  descent 个单位， 因此Y的真正坐标为：

==getHeight() / 2 - (descent-ascent )/2-descent==

```java
private void drawCenterText(final Canvas canvas) {
    canvas.save();
    Paint paint = new Paint();
    paint.setTextSize(80);
    float X = getWidth() / 2;
    paint.setTextAlign(Paint.Align.CENTER);
    Paint.FontMetrics fontMetrics = paint.getFontMetrics();
    //计算真实Y坐标
    float Y = getHeight() / 2 - (fontMetrics.descent + fontMetrics.ascent) / 2;
    canvas.drawText(mText, X, Y, paint);
    canvas.restore();
}
```





## eg4、文字渐变效果

有了eg3的知识点，就能做到绘制文字的渐变效果。注意：

1、尽量不要绘制一个像素点，绘制次数过多时，加载可能出现卡顿

```java
public class SimpleColorChangeTextView extends AppCompatTextView {
    private String mText = "ABgI";//成员变量
    private float mPercent = 0.5f;
    Paint paint;
    private float textX; //文字左X坐标
    private float textY; //文字左Y坐标
    private float textWidth;
    Paint.FontMetrics fontMetrics;
    int bgColor = Color.BLACK;
    int foreColor = Color.RED;
    
    public SimpleColorChangeTextView(Context context) {
        super(context);
        initPaint();
    }

    public SimpleColorChangeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public SimpleColorChangeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setTextSize(80);
        paint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //计算文字的宽度，起始位置
        textWidth = paint.measureText(mText);
        fontMetrics = paint.getFontMetrics();
        textX = getMeasuredWidth() / 2 - textWidth / 2;
        textY = getMeasuredHeight() / 2 - (fontMetrics.descent + fontMetrics.ascent) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawEndArea(canvas);
        drawPainArea(canvas);
    }

    //绘制前景色
    private void drawPainArea(final Canvas canvas) {
        canvas.save();
        paint.setColor(foreColor);
        float endX = textX + textWidth * mPercent;
        Rect rect = new Rect((int) textX, 0, (int) endX, getHeight());
        canvas.clipRect(rect);
        canvas.drawText(mText, textX, textY, paint);
        canvas.restore();
    }

    //绘制背景色
    private void drawEndArea(final Canvas canvas) {
        canvas.save();
        //裁剪区域 起时X坐标
        float startX = textX + textWidth * mPercent;
        paint.setColor(bgColor);
        Rect rect = new Rect((int) startX, 0, getWidth(), getHeight());
        canvas.clipRect(rect);
        canvas.drawText(mText, textX, textY, paint);
        canvas.restore();
    }

   
	//设置相关属性
    public void setPercent(float mPercent) {
        this.mPercent = mPercent;
    }


    public void setTextStyle(int bgColor, int foreColor, int size) {
        this.bgColor = bgColor;
        this.foreColor = foreColor;
        paint.setTextSize(size);
    }

    public void setText(String text) {
        this.mText = text;
    }
}
```

















# 三、RV.Adapter

## 一、分割线

可以继承RecyclerView.ItemDecoration，自定义想要的分割线样式



重写方法讲解：

getItemOffsets：预留出每个子Item之间的空隙

```java
 @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getAdapter() instanceof StarAdapter) {
            StarAdapter adapter = (StarAdapter) parent.getAdapter();
            int position = parent.getChildLayoutPosition(view);
            // 怎么判断 自定义方法，判断是否是头部
            boolean isGroupHeader = adapter.isGourpHeader(position);
            if (isGroupHeader) {
                // 如果是头部，预留更大的地方
                outRect.set(0, groupHeaderHeight, 0, 0);
            } else {
                // 1像素
                outRect.set(0, 4, 0, 0);
            }
        }
    }
```

onDraw：绘制分割线的样式，会被Item挡住

```java
 @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (parent.getAdapter() instanceof StarAdapter) {
            StarAdapter adapter = (StarAdapter) parent.getAdapter();
            // 当前屏幕的item个数
            int count = parent.getChildCount();
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            for (int i = 0; i < count; i++) {
                // 获取对应i的View
                View view = parent.getChildAt(i);
                // 获取View的布局位置
                int position = parent.getChildLayoutPosition(view);
                // 是否是头部
                boolean isGroupHeader = adapter.isGourpHeader(position);
                if (isGroupHeader && view.getTop() - groupHeaderHeight - parent.getPaddingTop() >= 0) {
                    c.drawRect(left, view.getTop() - groupHeaderHeight, right, view.getTop(), headPaint);
                    String groupName = adapter.getGroupName(position);
                    textPaint.getTextBounds(groupName, 0, groupName.length(), textRect);
                    c.drawText(groupName, left + 20, view.getTop() -
                            groupHeaderHeight / 2 + textRect.height() / 2, textPaint);
                } else if (view.getTop() - groupHeaderHeight - parent.getPaddingTop() >= 0) {
                    // 分割线
                    c.drawRect(left, view.getTop() - 4, right, view.getTop(), headPaint);
                }
            }
        }
    }
```

onDrawOver：超出部分会覆盖Item的区域

```java
 @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (parent.getAdapter() instanceof StarAdapter) {
            StarAdapter adapter = (StarAdapter) parent.getAdapter();
            // 返回可见区域内的第一个item的position
            int position = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
            // 获取对应position的View
            View itemView = parent.findViewHolderForAdapterPosition(position).itemView;
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            int top = parent.getPaddingTop();
            // 当第二个是组的头部的时候
            boolean isGroupHeader = adapter.isGourpHeader(position + 1);
            if (isGroupHeader) {
                int bottom = Math.min(groupHeaderHeight, itemView.getBottom() - parent.getPaddingTop());

                c.drawRect(left, top, right, top + bottom, headPaint);
                String groupName = adapter.getGroupName(position);
                textPaint.getTextBounds(groupName, 0, groupName.length(), textRect);

                // 绘制文字的高度不能超出区域
                c.clipRect(left, top, right, top + bottom);

                c.drawText(groupName, left + 20, top + bottom
                        - groupHeaderHeight / 2 + textRect.height() / 2, textPaint);
            } else {
                c.drawRect(left, top, right, top + groupHeaderHeight, headPaint);
                String groupName = adapter.getGroupName(position);
                textPaint.getTextBounds(groupName, 0, groupName.length(), textRect);
                c.drawText(groupName, left + 20, top + groupHeaderHeight / 2 + textRect.height() / 2, textPaint);
            }

        }
    }
```









# 四、CoordinatorLayout



## 一、常用的嵌套布局

用于实现嵌套滑动的最佳方案，可以在非侵入的方式实现相应的交互

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.coordinatorlayout.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".coordinator.CoordinatorActivity">

    <com.google.android.material.appbar.AppBarLayout
        android:id="@+id/appbar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:fitsSystemWindows="true">

        <com.google.android.material.appbar.CollapsingToolbarLayout
            android:id="@+id/collapsing_toolbar_layout"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:layout_scrollFlags="scroll|exitUntilCollapsed|snap"
            app:statusBarScrim="@android:color/transparent">
			
            <!--这里放入需要滑动的头部布局-->
           
            <ImageView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:fitsSystemWindows="true"
                android:scaleType="centerCrop"
                android:src="@drawable/images" />

        </com.google.android.material.appbar.CollapsingToolbarLayout>

        <!--这里放入不需要滑动的头部布局-->

    </com.google.android.material.appbar.AppBarLayout>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_behavior="@string/appbar_scrolling_view_behavior" />

    
<!--这里可以加入任意节目上的元素，默认左上角，和framelayout相似-->
       
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```





### 1、AppBarLayout

它的子view上设置**app:layout_scrollFlags**属性或者是在代码中调用`setScrollFlags()`才能实现子View的滚动效果

- scroll：所有想滚动出屏幕的view都需要设置这个flag， 没有设置这个flag的view将被固定在屏幕顶部。
- enterAlways：这个flag让任意向下的滚动都会导致该view变为可见，启用快速“返回模式”。
- enterAlwaysCollapsed：假设你定义了一个最小高度（minHeight）同时enterAlways也定义了，那么view将在到达这个最小高度的时候开始显示，并且从这个时候开始慢慢展开，当滚动到顶部的时候展开完。
- exitUntilCollapsed：当你定义了一个minHeight，此布局将在滚动到达这个最小高度的时候折叠。
- snap：当一个滚动事件结束，如果视图是部分可见的，那么它将被滚动到收缩或展开。例如，如果视图只有底部25%显示，它将折叠。相反，如果它的底部75%可见，那么它将完全展开。



### 2、CollapsingToolbarLayout

通过**app:contentScrim**设置折叠时工具栏布局的颜色，默认contentScrim是colorPrimary的色值

通过**app:statusBarScrim**设置折叠时状态栏的颜色。默认statusBarScrim是colorPrimaryDark的色值。

CollapsingToolbarLayout的子布局有3种折叠模式（Toolbar中设置的**app:layout_collapseMode**）

- off：默认属性，布局将正常显示，无折叠行为。
- pin：CollapsingToolbarLayout折叠后，此布局将固定在顶部。
- parallax：CollapsingToolbarLayout折叠时，此布局也会有视差折叠效果。

> 当CollapsingToolbarLayout的子布局设置了parallax模式时，我们还可以通过app:layout_collapseParallaxMultiplier设置视差滚动因子，值为：0~1。





## 二、Behavior

CoordinatorLayout中子View的交互行为



只要将Behavior绑定到CoordinatorLayout的直接子元素上，就能对触摸事件（touch events）、window insets、measurement、layout以及嵌套滚动（nested scrolling）等动作进行拦截。

绑定方式：**App:layout_behavior**=”@string/appbar_scrolling_view_behavior”

> 这种是RV上的属性，表示RV位于AppBarLayout下方，并共享滑动事件、



XML中处理，指定当前的View的行为：

```xml
app:layout_behavior="com.example.testrun.coordinator.bean.MyBehaivor"
```



自定义一个和CoordinatorLayout 中具有交互行为的View：（这里是下拉和上滑，View对应的隐藏和显示处理）

```java
public class MyBehaivor extends CoordinatorLayout.Behavior<View> {


    private boolean isVisible = true;
    private boolean isAnimating = false;
    private static final int MIN_SCROLL_DISTANCE = 15;

    public MyBehaivor(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 依赖哪个被观察的View，
     * @param parent
     * @param child 被动变化的View
     * @param dependency 依赖的View
     * @return true 时为响应其变化
     */
    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }

    /**
     * 当CoordinatorLayout开始滑动时会调用这个方法，任意与CoordinatorLayout通过behavior关联的控件都要响应此方法并返回true
     * 如果返回false，控件将不会响应CoordinatorLayout的滑动事件。
     *
     * @param coordinatorLayout
     * @param child             与CoordinatorLayout通过behavior关联的控件，即自己
     * @param directTargetChild 直接目标，即滑动的控件
     * @param target            依赖的View
     * @param axes              CoordinatorLayout滑动方向，ViewCompat.SCROLL_AXIS_VERTICAL表示纵向滑动
     *                          ViewCompat.SCROLL_AXIS_HORIZONTAL表示横向滑动
     * @return
     */
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        //响应CoordinatorLayout的纵向滑动
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    /**
     * CoordinatorLayout滑动状态更新时调用此方法
     *
     * @param coordinatorLayout
     * @param child             与CoordinatorLayout通过behavior关联的控件，即自身
     * @param target            依赖的View
     * @param dx                手指水平方向滑动的距离，左滑dx>0 右滑dx<0
     * @param dy                手指竖直方法滑动的距离，上滑dy>0 下滑dy<0
     * @param consumed          实际已滑动的距离，consumed[0]表示水平距离，consumed[1]表示竖直距离；
     *                          实际已滑动的距离总是小于或等于手指滑动的距离
     */
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (dy > MIN_SCROLL_DISTANCE && isVisible && !isAnimating) {    //如果向上滑动，则隐藏底部控件
            hideBottomView(child);
        } else if (dy < -MIN_SCROLL_DISTANCE && !isVisible && !isAnimating) {  //如果向下滑动，则显示底部控件
            showBottomView(child);
        }
    }


    /**
     * 滑动结束的回调事件
     * @param coordinatorLayout
     * @param child         与CoordinatorLayout通过behavior关联的控件，即自身
     * @param target        依赖的View
     * @param dxConsumed    当没有滚动到顶部或者底部的时候，x/y轴的滚动距离
     * @param dyConsumed
     * @param dxUnconsumed  当滚动到顶部或者底部的时候，x/y轴的滚动距离
     * @param dyUnconsumed
     * @param type
     * @param consumed
     */
    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed);
    }


    /**
     * 滑动的速度
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param velocityX X轴滑动的速度
     * @param velocityY Y轴滑动的速度
     * @return
     */
    @Override
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    /**
     * 重新摆放子View的位置
     * @param parent 
     * @param child
     * @param layoutDirection
     * @return
     */
    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull View child, int layoutDirection) {
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    /*自定义的空间处理*/
    private void showBottomView(final View view) {
        view.animate().translationY(0).setDuration(200).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                isAnimating = true;
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isAnimating = false;
                isVisible = true;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                isAnimating = false;
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).start();
    }

    private void hideBottomView(final View view) {
        view.animate().translationY(view.getHeight()).setDuration(200).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                isAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isAnimating = false;
                view.setVisibility(View.INVISIBLE);
                isVisible = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                isAnimating = false;
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).start();
    }
}
```





