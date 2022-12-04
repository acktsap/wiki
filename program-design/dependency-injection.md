# Dependency Injection

- [Concept](#concept)
- [Form of DI](#form-of-di)
  - [Constructor Based](#constructor-based)
  - [Setter Based](#setter-based)
  - [Interface Based](#interface-based)
  - [Service Locator](#service-locator)
- [See also](#see-also)

## Concept

- Inversion of Control의 일부로 의존성을 맺는 과정을 역전해서 해준다는 거임.
- Spring이 Inversion of Control을 해줘서 좋다는 말은 모호한 말임. 정확히는 Spring이 큰 개념에서 IoC인 Dependency Injection을 해줘서 좋다고 하는게 맞음.

```java
interface MovieFinder {
    List findAll();
}

class MovieLister {
    // 이거 이렇게 직접 생성해서 의존하지 않게 하는거임
    private MovieFinder finder = new ColonDelimitedMovieFinder("movies1.txt"); 

    public Movie[] moviesDirectedBy(String arg) {
        List allMovies = finder.findAll();
        for (Iterator it = allMovies.iterator(); it.hasNext();) {
            Movie movie = (Movie) it.next();
            if (!movie.getDirector().equals(arg)) it.remove();
        }
        return (Movie[]) allMovies.toArray(new Movie[allMovies.size()]);
    }
}
```
## Form of DI

- 의존하는 객체를 interface를 통해 사용 실 구현체를 넣어줄 공간을 만들어 둠. 그러면 Assembler가 적절한 실 구현체를 주입해줌.

### Constructor Based

### Setter Based

### Interface Based

### Service Locator

## See also

- [Inversion of Control Containers and the Dependency Injection pattern](https://martinfowler.com/articles/injection.html)