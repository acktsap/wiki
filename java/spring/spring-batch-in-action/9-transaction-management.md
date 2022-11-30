# 9. Transaction management

- [9. Transaction management](#9-transaction-management)
  - [9.1. A transaction primer](#91-a-transaction-primer)
  - [9.2. Transaction management in Spring Batch components](#92-transaction-management-in-spring-batch-components)
    - [9.2.1. Transaction management in tasklets](#921-transaction-management-in-tasklets)
    - [9.2.2. Transaction management in chunk-oriented steps](#922-transaction-management-in-chunk-oriented-steps)
    - [9.2.3. Transaction management in listeners](#923-transaction-management-in-listeners)
  - [9.3. Transaction Configuration](#93-transaction-configuration)
    - [9.3.1. Transaction attributes](#931-transaction-attributes)
    - [9.3.2. Common pitfalls with declarative transactions](#932-common-pitfalls-with-declarative-transactions)
    - [9.3.3. Transactional reader and processor](#933-transactional-reader-and-processor)
    - [9.3.4. To roll back or not to roll back](#934-to-roll-back-or-not-to-roll-back)
  - [9.4. Transaction management patterns](#94-transaction-management-patterns)
    - [9.4.1. Transactions spanning multiple resources: global transactions](#941-transactions-spanning-multiple-resources-global-transactions)
    - [9.4.2. The shared resource transaction pattern](#942-the-shared-resource-transaction-pattern)

## 9.1. A transaction primer

- 데이터의 신뢰성을 보장하기 위해서 Transaction은 필요함.
- Transaction은 batch job의 robustness와 performance에 영향을 끼침.
- 일반적인 web request의 tx와는 달리 batch job의 tx는 복잡함.

## 9.2. Transaction management in Spring Batch components

- spring batch는 step level에서 tx를 관리.

### 9.2.1. Transaction management in tasklets

- Custom processing이 필요할 때 read-process-write 이 아닌 tasklet을 사용 (eg. zip풀기, ftp server에 업로드, ...).
- 기본적으로 `Tasklet::execute`는 tx안에서 실행됨.
- `Tasklet::execute`가 `RepeatStatus.CONTINUABLE`를 return하면 계속 실행되는데 이때도 각각의 tx에서 실행됨.

### 9.2.2. Transaction management in chunk-oriented steps

- Chunk-oriented step은 기본적으로 read-process-write 으로 실행.
- Chunk 단위로 tx가 실행. processor, writer에서 exception던져지면 roll back. reader는 ㄴㄴ.
  - 장점
    - Efficient : 전체에 tx거는거 보다 chunk단위로 tx거는게 나음.
    - Robust : 실패해도 chunk단위로 영향.

### 9.2.3. Transaction management in listeners

- Listener가 실행되는 곳이 tx context에 있는지 아닌지는 룰이 없음. listener별로 다름. docs봐라.

## 9.3. Transaction Configuration

### 9.3.1. Transaction attributes

- Step chunk단위로 transaction attribute 지정 가능.
- Default transaction attributes는 (propagation level = REQUIRED, isolation level = READ_COMMITED).
- 보통 Default transaction attributes로 충분하니 꼭 필요할때만 overriding해라.

```xml
<job id="importProductsJob">
  <step id="importProductsStep">
    <tasklet>
      <chunk reader="reader" writer="writer" commit-interval="100" />
      <transaction-attributes isolation="READ_UNCOMMITTED" /> <!-- 이렇게 지정가능 -->
    </tasklet>
  </step>
</job>
```

### 9.3.2. Common pitfalls with declarative transactions

- Spring의 `@Transactional`로 제공되는 선언적 tx와 Spring batch에서 암시적으로 제공하는 tx와 충돌이 날 수 있음.
- `@Transactional` 쓰면 Spring batch에서 `@Transactional`로 감싸져있는 method를 호출하게 됨.
- 가능하면 다음의 지침을 따르는게 좋음
  - Spring의 `@Transactional`를 사용하지 말라
  - Spring의 `@Transactional`를 사용할 때는 조심. REQUIRES_NEW 같은거 썼다가는 sping batch tx랑 따로놀게됨.

### 9.3.3. Transactional reader and processor

TODO

### 9.3.4. To roll back or not to roll back

- Chunk-oriented step에서 chunk tx가 실패하면 데이터 정합성을 위해 roll back함.
- 특정 exception 한에서는 tx결과에 문제가 없는 경우가 있음. 이때 tx rollback 기능을 끌 수 잇음.

```xml
<job id="importProductsJob">
  <step id="importProductsStep">
    <tasklet>
      <chunk reader="reader" writer="writer" commit-interval="100" skip-limit="5">
        <skippable-exception-classes>
          <include class="org.springframework.batch.item.validator.ValidationException" />
        </skippable-exception-classes>
      </chunk>
      <!-- 이렇게 특정 exception에 대해 tx rollback 기능을 끌 수 있음 -->
      <no-rollback-exception-classes>
        <include class="org.springframework.batch.item.validator.ValidationException" />
      </no-rollback-exception-classes>
    </tasklet>
  </step>
</job>
```

## 9.4. Transaction management patterns

### 9.4.1. Transactions spanning multiple resources: global transactions

- Local transaction에서는 한개의 resource랑만 상호작용해서 ACID 지키기 simple.
- Resource가 여러개인 global transaction에서는 XA protocol에 기반한 transaction manager를 사용해야 함.
- 다음의 것들이 필요.
  - JTA (Java Transaction API) transaction manager
    - JTA spec을 구현한 tx managaer. JTA spec은 XA protocal구현을 강제.
    - Spring이 제공해주는 `JtaTransactionManager`는 bridge역할만 함. 구현 없음.
  - XA-aware driver
    - resource와 XA protocol로 상호작용할 수 있는 driver.
- Structure
  - Application 1-> JTA tx manager *-> XA driver1, XA driver2, ...

### 9.4.2. The shared resource transaction pattern

TODO