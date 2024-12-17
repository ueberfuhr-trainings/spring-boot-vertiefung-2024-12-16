package de.samples;

import java.lang.reflect.Proxy;

public class ProxySample {

  interface HelloWorldSayer {
    String sayHello();
  }

  public static void main(String[] args) {
    final HelloWorldSayer helloWorldSayer = () -> "Hello Franziska";

    System.out.println(helloWorldSayer.sayHello());

    final HelloWorldSayer helloWorldProxy = (HelloWorldSayer) Proxy.newProxyInstance(
      HelloWorldSayer.class.getClassLoader(),
      new Class[]{HelloWorldSayer.class},
      (proxy, method, args1) -> {
        if (method.getName().equals("sayHello")) {
          return helloWorldSayer.sayHello() + " from Proxy";
        }
        return method.invoke(helloWorldSayer, args1);
      }
    );

    System.out.println(helloWorldProxy.sayHello());
    System.out.println(helloWorldProxy.hashCode());


  }

}
