

# 一、自定义View

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



























