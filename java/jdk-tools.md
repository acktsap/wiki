# Jdk Tools

- [Jdk Tools](#jdk-tools)
  - [Jar](#jar)
  - [References](#references)

## Jar

- Zip file기반으로 class파일 + resources들 + META-INF를 모아놓은 것
  - META-INF : packages와 extension에 대한 정보를 담고 있음
    - MANIFEST.MF : package와 extension에 대한 정보를 담고 있음
      - Manifest-Version : 버전 정보
      - Created-By : Manifest파일을 생성한 vender
      - Signature-Version : 서명 정보
      - Class-Path : classpath 정보
      - Main-Class : Stand-alone application으로 실행할 때 실행할 main class 정보
    - INDEX.LIST : 그냥 index. class같은 거에 대한 위치 정보를 담고 있어서 class loader의 속도를 향상시켜줌
    - *.SF, *.DSA : Jar 파일에 대한 서명정보
    - services/
      - 미리 정의된 interface나 추상클래스인 service에 대한 구현체인 service provider에 대한 정보를 담고 있음
      - eg. "javax.script.ScriptEngineFactory" holds "org.luaj.vm2.script.LuaScriptEngineFactory"

## References

Jar spec

https://docs.oracle.com/javase/8/docs/technotes/guides/jar/jar.html
