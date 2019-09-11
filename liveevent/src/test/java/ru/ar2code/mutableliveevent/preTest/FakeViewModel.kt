package ru.ar2code.mutableliveevent.preTest

import androidx.lifecycle.Observer
import ru.ar2code.mutableliveevent.EventArgs
import ru.ar2code.mutableliveevent.MutableLiveEvent

class FakeViewModel() {

    private val intLiveEvent =
        MutableLiveEvent<EventArgs<Int>>()

    private val lifecycleOwner = FakeLifecycleOwner()

    fun observeEvent(
        observer: Observer<EventArgs<Int>>
    ) {
        intLiveEvent.observe(
            lifecycleOwner,
            observer
        )
    }

    fun observeEventForever(
        observer: Observer<EventArgs<Int>>
    ) {
        intLiveEvent.observeForever(observer)
    }

    fun sendEvent(eventArgs: EventArgs<Int>) {
        intLiveEvent.value = eventArgs
    }

    fun removeAllObservers() {
        intLiveEvent.removeObservers(lifecycleOwner)
    }

    fun removeObserver(observer: Observer<EventArgs<Int>>) {
        intLiveEvent.removeObserver(observer)
    }

    fun getObserversCount() = intLiveEvent.observers.size
}
