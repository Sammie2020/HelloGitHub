//package org.example.springboot.reactor9;
//
//import java.util.concurrent.Flow;
//
//public class Demo {
//    public static void main(String[] args) {
//        Flow.Publisher<String> publisher = subscriber -> {
//            subscriber.onNext("1");//1
//            subscriber.onNext("2");//2
//            subscriber.onError(new RuntimeException("出错"));
////            subscriber.onComplete();
//        };
//        publisher.subscribe(new Flow.Subscriber<String>() {
//            @Override
//            public void onSubscribe(Flow.Subscription subscription) {
//                subscription.cancel();
//            }
//
//            @Override
//            public void onNext(String item) {
//                System.out.println(item);
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                System.out.println(throwable);
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//    }
//}
