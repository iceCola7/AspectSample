# AspectSample

> 一个基于 AspectJ 实现 android 面向切面编程的案例。

## AspectJ 简介

> AspectJ 意思就是 Java 的 Aspect。它就是一个代码编译器，在Java编译器的基础上增加了一些它自己的关键字识别和编译方法。

> AspectJ编译器在编译期对所切点所在的目标类进行了重构，在编译层将AspectJ程序与目标程序进行双向关联，生成新的目标字节码，即将AspectJ的切点和其余辅助的信息类段插入目标方法和目标类中，同时也传回了目标类以及其实例引用，这样便能够在AspectJ程序里对目标程序进行监听甚至操控。

**AspectJ 的优点**

- 非侵入式监控： 可以在不修监控目标的情况下监控其运行，截获某类方法，甚至可以修改其参数和运行轨迹！
- 学习成本低： 它就是Java，只要会Java就可以用它。
- 功能强大，可拓展性高： 它就是一个编译器+一个库，可以让开发者最大限度的发挥，实现形形色色的AOP程序！


## 官方库集成配置

[AspectJ Weaver 仓库地址](https://mvnrepository.com/artifact/org.aspectj/aspectjweaver)

[AspectJ Tools 仓库地址 ](https://mvnrepository.com/artifact/org.aspectj/aspectjtools)

[AspectJ 官网下载](https://www.eclipse.org/aspectj/downloads.php)

**已知问题**
	
- 不支持kotlin
- 不能拦截jar包中的类
- 拦截规则不能写在jar包中
- 需要在每一个module都配置脚本

### 配置

1、在 【project】 的 build.gradle 中配置：

```
buildscript {
    repositories {
        google()
        jcenter() 
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.3.1'
        // aspectj
        classpath 'org.aspectj:aspectjtools:1.8.13'
        classpath 'org.aspectj:aspectjweaver:1.8.13'
    }
}
```

2、在 【project】 的 build.gradle 最后面添加脚本： 

**注意，直接粘贴到 build.gradle 文件的末尾即可，不要嵌套在别的指令中**

```
// AOP需要执行的脚本，每一个application和library都需要
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main

def aop(variants) {
    variants.all { variant ->
        JavaCompile javaCompile = variant.javaCompile
        String[] args;
        javaCompile.doFirst {
            args = ["-showWeaveInfo",
                    "-1.8",
                    "-inpath", javaCompile.destinationDir.toString(),
                    "-aspectpath", javaCompile.classpath.asPath,
                    "-d", javaCompile.destinationDir.toString(),
                    "-classpath", path,
                    "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator)]
        }

        javaCompile.doLast {
            new Main().run(args, new MessageHandler(false));
        }
    }
}
```

3、在 【module】 的 build.gradle 中添加依赖： 

**注意，只在基础module中使用api方式依赖即可**

```
api 'org.aspectj:aspectjrt:1.8.13'//只在基础module中使用api方式依赖
```

4、在 【module】 的 build.gradle 中添加编译脚本： 

**注意，直接粘贴到build.gradle文件的开始位置或末尾即可，不要嵌套在别的指令中**
 
如果是 【application】

```
rootProject.aop(project.android.applicationVariants)
```

如果是【library】

```
rootProject.aop(project.android.libraryVariants)
```

## AspectJ 使用案例

[项目地址](https://github.com/iceCola7/AspectSample)

#### 定义拦截 Activity 生命周期方法的逻辑

```
@Aspect  
public class TreeAspect {
    private static final String TAG = "cxz----->>";
    /**
     * 切入点：Activity 中以 on 开头的所有方法执行之前
     */
    @Before("execution(* android.app.Activity.on*(..))")
    public void onActivityMethodBefore(JoinPoint joinPoint) {
        String key = joinPoint.getSignature().toString();
        Log.e(TAG, "onActivityMethodBefore: 切面的方法执行了--" + key);
    }
}
```

#### 定义拦截自定义注解的逻辑

定义自定义注解

```
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface DebugTree {
}
```

定义拦截规则(注意要更改为正确的包名)

```
@Aspect
public class AnnotationAspect {
    private static final String TAG = "cxz----->>";
    // 切入点： DebugTree 注解
    @Pointcut("execution(@com.cxz.aspect.sample.DebugTree * * (..))")
    public void DebugTraceMethod() {
    }
    @Before("DebugTraceMethod()")
    public void beforeDebugTraceMethod(JoinPoint joinPoint) {
        String key = joinPoint.getSignature().toString();
        Log.e(TAG, "注解方法之前执行: " + key);
    }
    @After("DebugTraceMethod()")
    public void afterDebugTraceMethod(JoinPoint joinPoint) {
        String key = joinPoint.getSignature().toString();
        Log.e(TAG, "注解方法之后执行: " + key);
    }
}
```

#### 定义拦截点击事件的逻辑（案例：处理防止连续点击的逻辑）

拦截所有 Vie w或其子类的 【onClick方法】 ，添加防止连续点击的逻辑

```
@Aspect
public class ClickListenerAspect {
    private static final String TAG = "cxz----->>";
    private static Long lastClick = 0L;
    private static final Long FILTER_TIME = 1000L;
    /**
     * 切入点：View 中 OnClickListener 接口的 onClick 方法
     */
    @Around("execution(* android.view.View.OnClickListener.onClick(..))")
    public void clickFilter(ProceedingJoinPoint proceedingJoinPoint) {
        if (System.currentTimeMillis() - lastClick >= FILTER_TIME) {
            lastClick = System.currentTimeMillis();
            try {
                proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            Log.e(TAG, "重复点击已过滤...");
        }
    }
}
```