# MutableLiveEvent
LiveEvent based on Android LiveData

LiveData is a good way to store the state of your view model in the context of a fragment or activity life cycle.

LiveData is a great help to restore state when you change the configuration, for example, when changing orientation. But sometimes when changing the configuration, we do not need to restore the previous state. In other words, we need to send an event that exists within the life cycle of a fragment or activity.
This is what MutableLiveEvent does.

MutableLiveEvent has the following properties:

1.	Retrieves data only with an active subscription. If you subscribe to the event after it was sent, then the observer will not handle event.
2.	You can set handled to true for the event data. It means that event was handled and you do not want this event to be handled by any other observers.

## Add to your project

To start using this library, add these lines to the build.gradle of your project:

```xml
repositories {
    jcenter()
}

dependencies {
    implementation 'ru.ar2code.mutableliveevent:mutableliveevent:1.0.1'
}
```

## How to use

```kotlin
//Create EventArgs class to store your types of data
class MyIntEventArgs(data: Int) : EventArgs<Int>(data)

//Create usual viewModel
class MainViewModel : ViewModel() {

    private val myEventMutable = MutableLiveEvent<MyIntEventArgs>()
    val myEvent = myEventMutable as LiveData<MyIntEventArgs>

    fun sendEvent(data: Int) {
        myEventMutable.value = MyIntEventArgs(data)
    }
}

val vm = ViewModelProviders.of(this).get(MainViewModel::class.java)

//Observe LiveEvent as usual LiveData
vm.myEvent.observe(this, Observer {
   //handle event here
   /*
   * If the event is handled and you do not want this event to be handled by any other observers.
   */
   it.handled = true
})      
```

## License
  [MIT](https://github.com/ar2code/MutableLiveEvent/blob/master/LICENSE)
