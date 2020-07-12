# java9 
## Reactive Stream
#### 背压 
1. Publisher 发布者
2. Subscriber 订阅者
3. Subscription 相当于合同的意思
4. Processor 过滤作用
#### 案例
```java
public class WebFlux {

    public static void main(String[] args) {

        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

        MyProcessor myProcessor = new MyProcessor();

        publisher.subscribe(myProcessor);

        Subscriber<String> subscriber = new Subscriber<>(){
            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                this.subscription.request(1);
            }

            @Override
            public void onNext(String item) {
                System.out.println("item = " + item);
                this.subscription.request(1 );
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                this.subscription.cancel();
            }

            @Override
            public void onComplete() {
                System.out.println("处理完了");
            }
        };

        myProcessor.subscribe(subscriber);

        publisher.submit(-111);
        publisher.submit(222);
        publisher.submit(333);
        publisher.close();
        try {
            Thread.currentThread().join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}

class MyProcessor extends SubmissionPublisher<String> implements Processor<Integer,String>{

    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(Integer item) {
        System.out.println("接收到的数据 " + item);
        if(item > 0 ){
            this.submit("转化后的数据"+item);
        }
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        this.subscription.cancel();
    }

    @Override
    public void onComplete() {
        System.out.println("转化完成");
    }
}
```
#### 原理 使用一个256的缓存和一个阻塞的方法
