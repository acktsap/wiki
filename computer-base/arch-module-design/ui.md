# UI Architecture patterns

- [UI Architecture patterns](#ui-architecture-patterns)
  - [Goal](#goal)
  - [Model View Controller](#model-view-controller)
    - [Origin](#origin)
    - [201x](#201x)
  - [Flux](#flux)
  - [SOC on view layer](#soc-on-view-layer)
  - [References](#references)

## Goal

Separate viewer and business logic & make it to flexible!

eg.

```text
single input -> apply to multiple view
multiple input -> apply to single view
multiple input -> apply to multiple view
```

## Model View Controller

### Origin

!["no mvc origin img"](./img/mvc_origin.png)

It's been misunderstood. Everyone say their own opinions. Real is

- Model : represent knowledge.
- View : a visual representation of its model
- Controller : a link between a user and the system. It provides the user with input by arranging for relevant views to present themselves in appropriate places on the screen. It provides means for user output by presenting the user with menus or other means of giving commands and data. The controller receives such user output, translates it into the appropriate messages and passes these messages on to one or more of the views.

According to Martin Fowler, the primary benefit of this original version of the MVC pattern is **Separated Presentation** which he defines like this:

Ensure that any code that manipulates presentation only manipulates presentation, pushing all domain and data source logic into clearly separated areas of the program

or more easily

Make a clear division between domain objects that model our perception of the real world, and presentation objects that are the GUI elements we see on the screen

MVC was originally used in GUI. In GUI, MVC means

- Model : A particular piece of data represented by an application. For example, weather station temperature reading.
- View : One representation of data from the model. The same model might have multiple views associated with it. For example, a temperature reading might be represented by both a label and a bar chart. The views are associated with a particular model through the Observer relationship.
- Controller : Collects user input and modifies the model. For example, the controller might collect mouse clicks and keystrokes and update the Model

Note that in the original one, controller never directly interact with view.

> 원래 주장한 개념은 Model: 값을 담고 있음. View: Model에 근거해서 뭔가를 보여줌. Controller: user로부터 input을 받아서 model을 갱신. view를 바꾸진 않음.

### 201x

!["mvc 201x img"](./img/mvc_201x.png)

- Model : Business logic plus one or more data sources such as a relational database.
- View : The user interface that displays information about the model to the user.
- Controller : The flow-control mechanism means by which the user interacts with the application.

Here, controller directly interact with view. This is **necessary evil**. Since in the web, view cannot be updated without http response which is processed by application (mostly by controller in application).

> Web에서는 Controller가 user로부터 input을 받는 것을 같은데 controller가 view를 선택함! web이라서 어쩔 수 없음.

## Flux

![flux-architecture](./img/flux-architecture.png)

- View : 사용자 Input을 받고 action을 발생시킴
- Action : action타입과 action에 대한 payload값을 가지고 있음
- Dispatcher : action을 받아서 store에 dispath해줌
- Store : action을 받아서 model을 update하고 view를 update함

Facebook이 제안한 구조로 현대 web mvc에서 bidirectional을 없애버림. unidirectional하게 바꿔버림. 그냥 전통적인 MVC라고 볼 수 있음.

## SOC on view layer

- Fetch raw data
- Refine raw value
- Display (convert if necessary)

## References

- [mvc original paper](https://heim.ifi.uio.no/~trygver/2007/MVC_Originals.pdf)
- [evolution of mvc](https://stephenwalther.com/archive/2008/08/24/the-evolution-of-mvc)
- [MVC vs Flux](https://code.i-harness.com/ko-kr/q/1fe5f1e)
- [flux official docs](https://haruair.github.io/flux/docs/overview.html#content)