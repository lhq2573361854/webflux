#java8 高级特性
## 一 . 函数式编程 
+ 函数式编程的意义: 不在关注操作而是关注 告诉“机器”你想要的是什么(what) 
+ 命令式编程：命令“机器”如何去做事情(how)，这样不管你想要的是什么(what)，它都会按照你的命令实现。
+ 声明式编程：告诉“机器”你想要的是什么(what)，让机器想出如何去做(how)。
### 1. 总体
#### 1.1 Supplier
 ```java
@FunctionalInterface
public interface Supplier<T> {
    T get();
}
```
1. 简介 

   其简洁的声明，会让人以为不是函数。这个抽象方法的声明，同Consumer相反，
   
   是一个只声明了返回值，不需要参数的函数（这还叫函数？）。也就是说Supplier
   
   其实表达的不是从一个参数空间到结果空间的映射能力，而是表达一种生成能力
   
2. Supplier<String> supplier = String::new;
#### 1.2 Consumer
```java
@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);
    default Consumer<T> andThen(Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }
}
```
1. 简介 
 
   这个接口声明太重要了，应用场景太多了。因为需要返回值的我们用Function，

   不需要返回值的，我们用它就可 andThen可以实现消费两次。消费一次后，继续消费一次

 2. 使用
 
 Consumer c = System.out::println;
#### 1.3 Predicate
```java
@FunctionalInterface
public interface Predicate<T> {

    boolean test(T t);
    
    default Predicate<T> and(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) && other.test(t);
    }
    default Predicate<T> negate() {
        return (t) -> !test(t);
    }
    default Predicate<T> or(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) || other.test(t);
    }

    static <T> Predicate<T> isEqual(Object targetRef) {
        return (null == targetRef)
                ? Objects::isNull
                : object -> targetRef.equals(object);
    }

    @SuppressWarnings("unchecked")
    static <T> Predicate<T> not(Predicate<? super T> target) {
        Objects.requireNonNull(target);
        return (Predicate<T>)target.negate();
    }
}
```
> 1. 断言接口，有点意思了。其默认方法也封装了and、or和negate逻辑 和一个静态方法isEqual。
#### 1.4 Function
```java
@FunctionalInterface
public interface Function<T, R> {
    
    R apply(T t);
   
    default <V> Function<V, R> compose(Function<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }
    
    default <V> Function<T, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    
    static <T> Function<T, T> identity() {
        return t -> t;
    }
}
```
1. 这个接口非常非常总要。是很上层的一个抽象。除了一个抽象方法apply外，
    
    其默认实现了3个default方法，分别是compose、andThen和identity。
    
####1.5 Operator
```java
@FunctionalInterface
public interface UnaryOperator<T> extends Function<T, T> {
    static <T> UnaryOperator<T> identity() {
        return t -> t;
    }
}
```
        它包含UnaryOperator和BinaryOperator。分别对应单元算子和二元算子。

 1. Operator其实就是Function，函数有时候也叫作算子。算子在Java8中接口描述更像是函数的补充，和上面的很多类型映射型函数类似。
        UnaryOperator<Integer> unaryOperator = x -> x + 10;
       
        BinaryOperator<Integer> binaryOperator = (x, y) -> x + y;
       
        System.out.println(unaryOperator.apply(10)); //20
       
        System.out.println(.apply(5, 10)); //15
       
        //继续看看BinaryOperator提供的两个静态方法   也挺好用的
       
        BinaryOperator<Integer> min = BinaryOperator.minBy(Integer::compare);
       
        BinaryOperator<Integer> max = BinaryOperator.maxBy(Integer::compareTo);
       
        System.out.println(min.apply(10, 20)); //10
       
        System.out.println(max.apply(10, 20)); //20
        
####1.6 变量引用 java  中是值传递不是引用传递

####1.7 类型推断

1. 变量类型定义
    
    IMath iMath = (x, y) -> x + y;

    IMath[] iMaths = {(x, y) -> x + y};
    
    (Interface)(x, y) -> x + y;
    
####1.8 级联表达和柯里化
1. 定义
    把多个参数的函数转化成一个参数的函数
    Function<Integer,Function<Integer,Integer>> func =  x ->y-> x + y;
            
    Integer apply = func.apply(1).apply(2);
    
    System.out.println("apply = " + apply);
## 二 . 流编程 
> 不关注数据本身的得到只关注如何操作 惰性求值

### 1. 总体

#### 1.1 创建
+ 集合流 、 数字流 、数组流、自己创建
![stream](image/java8/java8stream创建.jpg)
#### 1.2 中间操作
![stream](image/java8/java8stream中间操作.jpg)
![stream](image/java8/java8stream中间操作1.jpg)
    IntStream 和 LongStream 不是 stream 的子 flatMap()
    
#### 1.3 终止操作
![stream](image/java8/java8stream中间操作2.jpg)
#### 1.4 parallel

#### 1.5 收集器collect
![stream](image/java8/java8stream终止操作1.jpg)
![stream](image/java8/java8stream终止操作2.jpg)

#### 1.6 原理分析
![stream](image/java8/java8stream原理.jpg)
                
                
                
                
                
                
                
                
                
                