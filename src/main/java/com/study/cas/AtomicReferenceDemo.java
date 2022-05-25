package com.study.cas;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicReferenceDemo {

    static class User{

        private String userName;

        private int age;

        public User(String userName, int age) {
            this.userName = userName;
            this.age = age;
        }

        public User() {
        }

        @Override
        public String toString() {
            return "User{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                '}';
        }
    }

    public static void main(String[] args) {

        User zhangsan = new User("zhangsan", 22);
        User lisi = new User("lisi", 25);

        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(zhangsan);
        //  true: User{userName='lisi', age=25} 比较替换成功 张三已经换成了 lisi
        System.out.println(atomicReference.compareAndSet(zhangsan , lisi) + ": " + atomicReference.get().toString());
        // false: User{userName='lisi', age=25} 比较替换失败了 还是李四
        System.out.println(atomicReference.compareAndSet(zhangsan , lisi) + ": " + atomicReference.get().toString());
    }


}
