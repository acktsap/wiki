# Maven

## Scope

- compile (default) : available on the classpath of the project and the dependent projects
  ```xml
  <dependency>
      <groupId>commons-lang</groupId>
      <artifactId>commons-lang</artifactId>
      <version>2.6</version>
  </dependency>
  ```
- provided : available on the classpath of the project
  ```xml
  <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>servlet-api</artifactId>
      <version>2.5</version>
      <scope>provided</scope>
  </dependency>
  ```
- runtime : available at runtime of the project and test classpath
  ```xml
  <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>6.0.6</version>
      <scope>runtime</scope>
  </dependency>
  ```
- test : only present for test and execution classpaths
  ```xml
  <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>test</scope>
  </dependency>
  ```
- system : system requires us to directly point to a specific jar on the system.
  ```xml
  <dependency>
      <groupId>com.baeldung</groupId>
      <artifactId>custom-dependency</artifactId>
      <version>1.3.2</version>
      <scope>system</scope>
      <systemPath>${project.basedir}/libs/custom-dependency-1.3.2.jar</systemPath>
  </dependency>
  ```
- import : only available for the dependency type pom
  ```xml
  <dependency>
      <groupId>com.baeldung</groupId>
      <artifactId>custom-project</artifactId>
      <version>1.3.2</version>
      <type>pom</type>
      <scope>import</scope>
  </dependency>
  ```

[maven dependency scope](https://www.baeldung.com/maven-dependency-scopes)
