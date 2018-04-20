# ActivityResult
startActivityForResult 获取返回值的封装，实现链式调用
### github   [https://github.com/lwugang/ActivityResult](https://github.com/lwugang/ActivityResult)
```gradle
allprojects {
	repositories {
		maven { url 'https://www.jitpack.io' }
	}
}
dependencies {
	compile 'com.github.lwugang:ActivityResult:59b23e3682'
}
```
## 大家都知道在一个APP中，Activity之间的数据交互是经常的，
####传统方式获取Activity返回值,这种方式需要操作多个方法，可读性很差
```java
 startActivityForResult(new Intent(),1);
```
```java
@Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    //获取数据
  }
```
####ActivityResult方式 只需关注一个方法，实现方法的链式调用，可读性强，对代码无侵入，只需修改打开方式，另一个界面的逻辑无需特殊处理
```java
ActivityResult.of(this)
        .className(TestActivity.class)//目标Activity类名
        //绿色通道，不走任何拦截器，直接打开目标
        .greenChannel()
        //transition 动画
        .options(ActivityOptionsCompat.makeScaleUpAnimation(v,(int)v.getX(),(int)v.getY(),
            v.getWidth()/2,v.getHeight()/2).toBundle())
        //拦截器，在startActivity之前执行
        .intercept(new Intercept() {
          @Override public boolean onIntercept(Activity activity, ActivityResult activityResult) {
            Log.e(TAG, "onIntercept: test" );
            //拦截器，return true 表示拦截，之后的拦截器都不会执行
            //activityResult.onContinue(); 继续执行之后的逻辑
            return false;
          }
        }).forResult(new ActivityResultListener() {
      @Override public void onReceiveResult(int resultCode, Intent data) {
          // 结果处理，也就是onActivityResult方法一样
      }
    });
```
####Application 中提前加入拦截器(可选)
```java
//注册拦截器
ActivityResultManager.get()
        .registerIntercept(new Intercept() {
          @Override public boolean onIntercept(Activity activity,final ActivityResult activityResult) {
            Log.e(TAG, "onIntercept: " );
            new Thread(){
              @Override public void run() {
                super.run();
                SystemClock.sleep(2000);
                activityResult.onContinue();
              }
            }.start();
            return true;
          }
        // true 表示使用一次之后就自动移除，下一次不会在执行此拦截器
        },true).registerIntercept(new Intercept() {
      @Override public boolean onIntercept(Activity activity, ActivityResult activityResult) {
        //可以判断是否登录成功，返回对应的值
        Log.e(TAG, "onIntercept2222222222: " );
        return false;
      }
    },true);
```
#### 简书博客地址 [https://www.jianshu.com/p/ca1573f7b35c]