# Polyglot Programming

- [Ployglot이란](#ployglot이란)
- [Polyglot Programming](#polyglot-programming)
- [Truffle language implementation framework.](#truffle-language-implementation-framework)
- [Polyglot Embedded Code Example](#polyglot-embedded-code-example)
  - [HelloPolyglot.java](#hellopolyglotjava)
  - [Guest Language 함수 정의](#guest-language-함수-정의)
  - [Java에서 직접 Guest언어 접근하기](#java에서-직접-guest언어-접근하기)
  - [Guest Language에서 Java 접근하기](#guest-language에서-java-접근하기)
  - [Guest Language에서 Java Type 접근하기](#guest-language에서-java-type-접근하기)
  - [Polyglot Proxy를 사용해보기](#polyglot-proxy를-사용해보기)
  - [Host 함수에 Access하는 권한 관리](#host-함수에-access하는-권한-관리)
- [Ployglot Native Application](#ployglot-native-application)
  - [Native Image Build Exmaple](#native-image-build-exmaple)
- [Polyglot 실행](#polyglot-실행)
  - [JavaScript Example](#javascript-example)
  - [Ployglot shell](#ployglot-shell)
- [See also](#see-also)

Polygrop API를 사용해서 polyglot programming을 하는 법을 다룸.

## Ployglot이란

- A polyglot is a computer program or script written in a valid form of multiple programming languages or file formats.
- 여러 언어의 syntax로 작성된 프로그램. 그런데 그 프로그램의 형태는 valid함.
- [Polyglot (computing)](https://en.wikipedia.org/wiki/Polyglot_(computing))

## Polyglot Programming

- Ployglot API를 사용하면 guest language를 JVM 위에서 돌릴 수 있음.

## Truffle language implementation framework.

- Graalvm 내부에 [Truffle language implementation framework](https://www.graalvm.org/22.3/graalvm-as-a-platform/language-implementation-framework/)가 있음.
  - self-modifying AST를 하는 interpreter를 작성할 때 유용한 library.
  - [Self-modifying code](https://en.wikipedia.org/wiki/Self-modifying_code)란 성능 향상 같은 이유로 수행중인 instruction을 바꾸는 code.
- Java on Truffle은 이 Truffle Framework을 이용해서 Java spec을 interperter로 구현한 것. 미니 JVM이라고 볼 수 있음.
- Truffle은 원래 interperter를 작성할 때 유용한 library임. Graalvm에서 다른 언어 interpreter로 구현해놓음.
  - Java on Truffle
  - JavaScript runtime
  - Python
  - R
  - Ruby
- Truffle로 도는 언어들을 guest language라고 함.
- Memory space를 통해 truffle로 도는 언어와 host language (Java) 가 상호작용할 수 있음.
  - polyglot interoperability protocol 라는 규약을 통해 상호작용.

## Polyglot Embedded Code Example

### HelloPolyglot.java

```java
import org.graalvm.polyglot.*; // import polyglot api
import org.graalvm.polyglot.proxy.*; // import polyglot proxy api;

public class HelloPolyglot {
    public static void main(String[] args) {
        System.out.println("Hello Java!");
        // Context : guest language에 대한 실행 환경을 제공
        // 이거도 close 해야 해서 try-with-resource 사용
        try (Context context = Context.create()) {
            context.eval("js", "print('Hello JavaScript!');");
        }
    }
}
```

실행 (Graalvm을 사용해서)

```sh
javac HelloPolyglot.java
java HelloPolyglot
```

그냥 openjdk로 실행도 해보기 (실패해야함)

### Guest Language 함수 정의

```java
import org.graalvm.polyglot.*; // import polyglot api
import org.graalvm.polyglot.proxy.*; // import polyglot proxy api;

public class GuestLanguageFunction {
    public static void main(String[] args) {
        try (Context context = Context.create()) {
            // Value: function에 대한 java reference
            Value function = context.eval("js", "x => x+1");
            System.out.println("canExecute: " + function.canExecute());
            int x = function.execute(41).asInt(); // execute js function
            assert x == 42;
            System.out.printf("x is %d%n", x);
        }
    }
}
```

실행 (Graalvm을 사용해서)

```sh
javac GuestLanguageFunction.java
java GuestLanguageFunction
```

### Java에서 직접 Guest언어 접근하기

```java
import org.graalvm.polyglot.*; // import polyglot api
import org.graalvm.polyglot.proxy.*; // import polyglot proxy api;

public class AccessGuestLanguageDirectlyFromJava {
    public static void main(String[] args) {
        try (Context context = Context.create()) {
            Value result = context.eval("js", 
                            "({ "                   +
                                "id   : 42, "       +
                                "text : '42', "     +
                                "arr  : [1,42,3] "  +
                            "})");
            System.out.println("hasMembers: " + result.hasMembers());

            int id = result.getMember("id").asInt();
            System.out.println("member.id: " + id);
            assert id == 42;

            String text = result.getMember("text").asString();
            System.out.println("member.text: " + text);
            assert text.equals("42");

            Value array = result.getMember("arr");
            System.out.println("member.array: " + array);
            assert array.hasArrayElements();
            assert array.getArraySize() == 3;
            assert array.getArrayElement(1).asInt() == 42;
        }
    }
}
```

실행 (Graalvm을 사용해서)

```sh
javac AccessGuestLanguageDirectlyFromJava.java
java AccessGuestLanguageDirectlyFromJava
```

### Guest Language에서 Java 접근하기

```java
import java.util.concurrent.Callable;
import org.graalvm.polyglot.*; // import polyglot api
import org.graalvm.polyglot.proxy.*; // import polyglot proxy api;

public class AccessJavaFromGuestLanguage {
    public static class MyClass {
        public int               id    = 42;
        public String            text  = "42";
        public int[]             arr   = new int[]{1, 42, 3};
        public Callable<Integer> ret42 = () -> 42;
    }

    public static void main(String[] args) {
        try (Context context = Context.newBuilder()
                               .allowAllAccess(true) // guest language에 java 접근 허가 설정
                           .build()) {

            context.getBindings("js").putMember("javaObj", new MyClass());
            boolean result = context.eval("js",
                "    javaObj.id         == 42"          +
                " && javaObj.text       == '42'"        +
                " && javaObj.arr[1]     == 42"          +
                " && javaObj.ret42()    == 42")
            .asBoolean();
            System.out.println("eval result: " + result);
            assert result == true;
        }
    }
}
```

실행 (Graalvm을 사용해서)

```sh
javac AccessJavaFromGuestLanguage.java
java AccessJavaFromGuestLanguage
```

### Guest Language에서 Java Type 접근하기

```java
import org.graalvm.polyglot.*; // import polyglot api
import org.graalvm.polyglot.proxy.*; // import polyglot proxy api;

public class LookupJavaTypeFromGuestLanguage {
    public static void main(String[] args) {
        try (Context context = Context.newBuilder()
                           .allowAllAccess(true) // Guest language에 접근 허용
                       .build()) {
            java.math.BigDecimal v = context.eval("js",
                    "var BigDecimal = Java.type('java.math.BigDecimal');" +
                    "BigDecimal.valueOf(10).pow(20)")
                .asHostObject();
            System.out.println("v: " + v);
            assert v.toString().equals("100000000000000000000");
        }
    }
}
```

실행 (Graalvm을 사용해서)

```sh
javac LookupJavaTypeFromGuestLanguage.java
java LookupJavaTypeFromGuestLanguage
```

### Polyglot Proxy를 사용해보기

Java랑 상호작용할 때 proxy로 장난질 칠 수 있는 api 제공.

```java
```

실행 (Graalvm을 사용해서)

```sh
javac todo.java
java todo
```

### Host 함수에 Access하는 권한 관리

allowAllAccess를 true로 안하면 기본적으로 다 막혀있음. 이걸 annotation 같은걸 사용하면 허가할 수 있음.

```java
```

실행 (Graalvm을 사용해서)

```sh
javac todo.java
java todo
```

[자세한 설정 참고](https://www.graalvm.org/22.3/reference-manual/embed-languages/#access-privilege-configuration)

## Ployglot Native Application

### Native Image Build Exmaple

[HelloPolyglot.java](#hellopolyglotjava) 사용.

```sh
javac HelloPolyglot.java
$GRAALVM_HOME/bin/native-image --language:js -cp . HelloPolyglot
./hellopolyglot 

# 용량 확인 (js를 위해 Trupple interpreter, Graal compiler 포함해서 엄청남)
ls -lh helloPolyglot
```

Graal compiler 제외해보기 (참고: [DefaultTruffleRuntime](https://github.com/oracle/graal/blob/master/truffle/src/com.oracle.truffle.api/src/com/oracle/truffle/api/impl/DefaultTruffleRuntime.java), jvm상에 적당한거 없으면 적용되는 친구인듯)

```sh
$GRAALVM_HOME/bin/native-image --language:js -Dtruffle.TruffleRuntime=com.oracle.truffle.api.impl.DefaultTruffleRuntime -cp .  HelloPolyglot

# 용량 확인 (이전보다 줄음)
ls -lh helloPolyglot
```

## Polyglot 실행

js runtime 설치

```sh
$GRAALVM_HOME/bin/gu install js
```

### JavaScript Example

```js
var array = new (Java.type("int[]"))(4);
array[2] = 42;
console.log(array[2])
```

Java on Truffle로 실행.

```sh
$GRAALVM_HOME/bin/js  --polyglot --jvm polyglot.js
```

Ployglot Launcher로 실행 (experimantal featrure)

```sh
$GRAALVM_HOME/bin/polyglot --jvm polyglot.js
```

### Ployglot shell 

```sh
$GRAALVM_HOME/bin/polyglot --jvm --shell

var a = 3
a + 3
null == undefined # true
null > 0          # false
null == 0         # false
null >= 0         # true
```

[각종 옵션들](https://www.graalvm.org/22.1/reference-manual/polyglot-programming/#polyglot-options)

## See also

- [Embedding Languages](https://www.graalvm.org/22.3/reference-manual/embed-languages)
- [Polyglot Programming](https://www.graalvm.org/22.3/reference-manual/polyglot-programming/)