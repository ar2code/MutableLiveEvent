package ru.ar2code.mutableliveevent

import androidx.annotation.MainThread
import androidx.collection.ArraySet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class MutableLiveEvent<T : EventArgs<Any>> : MutableLiveData<T>() {

    internal val observers = ArraySet<PendingObserver<in T>>()

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        val wrapper = PendingObserver(observer)
        observers.add(wrapper)

        super.observe(owner, wrapper)
    }

    override fun observeForever(observer: Observer<in T>) {
        val wrapper = PendingObserver(observer)
        observers.add(wrapper)

        super.observeForever(wrapper)
    }

    @MainThread
    override fun removeObserver(observer: Observer<in T>) {

        when (observer) {
            is PendingObserver -> {
                observers.remove(observer)
                super.removeObserver(observer)
            }
            else -> {
                val pendingObserver = observers.firstOrNull { it.wrappedObserver == observer }
                if (pendingObserver != null) {
                    observers.remove(pendingObserver)
                    super.removeObserver(pendingObserver)
                }
            }
        }
    }

    @MainThread
    override fun setValue(event: T?) {
        observers.forEach { it.awaitValue() }
        super.setValue(event)
    }
}