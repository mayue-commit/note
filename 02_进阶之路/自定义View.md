# 一、关于动画



## Android 的三种动画：

- View Animation（补间动画）
  - 只改变了View的绘制效果，属性值不变。AnimationSet类和Animation的子类。通常在res/anim下定义



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

> 这种动画属于普通动画，View移动了，但是View本身还在原地，点击原坐标仍然可以触发点击事件



- Drawable Animation（帧动画）
  - 加载一系列Drawble资源，使用AnimationDrawable类，在res/drawable 下定义





- Property Animation（属性动画，3.0后引入的）

  - 动画的对象由View改为了Object，动画后Object的值被改变了，任何时候View属性的改变，都能自动调用invalidate() 来刷新

  

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
                        .setInterpolator(new BounceInterpolator()) //这个是带有动画效果
                        .start();
            }
        });
```















# 二、点击事件分析

点击事件不起作用的原因：可能是外层的view覆盖了内层的view，布局中，写在上面的控件优先加载，所以注意嵌套关系。



view.setEnabled(false) 后，仍然可以点击的原因：可能是该view继承自viewgroup，代码设置了viewgroup不能够点击，但其中的view子类仍然能够点击。

所以，需要遍历其子项，禁用所有的子view：

```java
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child=viewGroup.getChildAt(i);
            child.setEnabled(false);
        }
```























