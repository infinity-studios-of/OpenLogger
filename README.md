
# Open Logger

### Open Logger is a library for convenient and easy logging of your code

#### Screenshot:

![ImageAlt](https://sun9-9.userapi.com/impg/Xs5DAxiWoBnavK7na8x4XMW4tjFwNqHrwnbuoA/RXE5XI2PgYU.jpg?size=662x176&quality=96&sign=43456506be2671aa8deb34458c79ae0e&type=album)



## Installation

Install Open Logger with Gradle

build.gradle (project)
```gradle
allprojects {
    repositories {
        ...
        maven {
            url 'https://jitpack.io'
        }
    }
}
```

build.gradle (app)
```gradle
dependencies {
    ...
    implementation 'com.github.infinity-studios-of:OpenLogger:Tag'
}
```


## Usage/Examples

### initialization:

Before using the open logger, it must be initialized:

```java
Log.init("logs folder")
```



### Logging:
```java
Log.i("Info", "some info");
Log.e("Error", "some info");
Log.d("Process", "some info");
Log.ce("CriticalError", "some info"); //logger stop when log critical error
```

### Trace logs
Trace logs needed to track the progress of code execution. Open Logger allows you to implement a function call tracking system, also displaying in which thread they were called

Example:
```java
class TestClass{
    public void foo(String arg){
        Log.trace(Thread.currentThread(), "TestClass", "foo", new String[]{"arg", arg});
        //do somthig
      }
}
```

### Memory Controll

You can track useing memory with Open logger automatically, to do this, you just need to enable memory usage tracking:

```java
Log.enableMemReporter();
```

