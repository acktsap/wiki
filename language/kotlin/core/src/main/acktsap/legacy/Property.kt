package legacy

// no field in kotlin, only property

//class Person {
//  // generate getter
//  val name: String = "I'm name"
//
//  var name2: String = ""
//    // custom getter, setter (like cache)
//    get() {
//      return name
//    }
//    set(name: String) {
//      field = name
//    }
//
//  @JvmField // not to generate default getter, setter
//  val name3: String = "I'm name"
//
//  // anti pattern (불완전한 property)
//  val r: String
//    get() = UUID.randomUUID().toString()
//}
//
//// 2. allocate child
//open class Parent {
//  open var name: Int = 2
//
//  // 3. init parent
//  init {
//    // print 0
//    println("I am parent; $name")
//    // actually
//    // println("I am parent; $getName")
//  }
//}
//
//// 1. allocate child
//class Child : Parent() {
//  override var name: Int = 4
//
//  // 4. init child
//  init {
//    // print 4
//    println("I am child; $name")
//  }
//}
//
//class Accessor {
//  private var weight: Double = 0.0
//  public var name: String = ""
//  // accessable only in same package (compile unit only), name changes everytime compile
//  internal var secret: String = ""
//  protected var aaa: String = ""
//}
//
//interface HumanLike {
//  val name: String
//}
//
//class NewMan : HumanLike {
//  override var name: String = "name"
//}
//
//class Bag {
//  var content: Any? = null
//}
//
//open class Bagger {
//  open var bag = Bag()
//}
//
//// make it override
//class NewBagger : Bagger() {
//  override var bag = Bag()
//}
//
//// expand property
//val Person.height: Int
//  get() = 0
//
//// lazy property
//class LazyProp {
//  var name: String = ""
//  open val bag: Bag by lazy { Bag().apply { content = name } }
//  lateinit var bag2: Bag
//
//  fun f() {
//    if (this::bag2.isInitialized) {
//      // init
//    }
//  }
//}
//
//fun main() {
//  Person()
//    .apply { this.name2 = "this is name" } // in java, this.setName2("...")
//    .also { println(it.name) }
//
//  Child()
//
//  // smart casting, error
////  if (b.bag.content is String) {
////    b.bag.content.substring(0, 5)
////  }
//
//  // resolve, val to Bag, Bagger
//  // error when newBagger2 is present (override) even with val
//
//  // pretty resolve, good
//  val b = Bagger()
//  val c = b.bag.content
//  if (c is String) {
//    c.substring(0, 5)
//  }
//
//  // expand property
//  val p = Person()
//  p.height
//
//  // lazy init
//  val l = LazyProp()
//
//  l.name = "lazy bag"
//  println(l.bag?.content)
//
//  // error
////  println(l.bag2)
//  // no error
//  l.bag2 = Bag().apply { content = "Im new bag" }
//  println(l.bag2.content)
//}

