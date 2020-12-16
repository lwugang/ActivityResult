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
	compile 'com.github.lwugang:ActivityResult:v2.0.1'
}
```
## 大家都知道在一个APP中，Activity之间的数据交互是经常的，
#### 传统方式获取Activity返回值,这种方式需要操作多个方法，可读性很差
```java
 startActivityForResult(new Intent(),1);
```
```java
@Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    //获取数据
  }
```
#### ActivityResult方式 只需关注一个方法，实现方法的链式调用，可读性强，对代码无侵入，只需修改打开方式，另一个界面的逻辑无需特殊处理
```java
AResult.of(this)
        .className(TestActivity.class)//目标Activity类名
        //transition 动画
        .options(ActivityOptionsCompat.makeScaleUpAnimation(v,(int)v.getX(),(int)v.getY(),
            v.getWidth()/2,v.getHeight()/2).toBundle()).forResult(new AResultListener() {
      @Override public void onReceiveResult(Intent data) {
          // 结果处理，也就是onActivityResult方法一样,只有当 setResult(Activity.RESULT_OK)才会执行
      }
    });
```

#### 更新记录
- 1.v2.0.1
    - 1.1 移除拦截器概念，简化操作
    - 1.2 增强IntentBuilder实现
    - 1.3 新增BundleCompat 快速创建Bundle

#### 简书博客地址 [https://www.jianshu.com/p/ca1573f7b35c]