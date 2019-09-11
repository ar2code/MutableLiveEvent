package ru.ar2code.mutableliveevent

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import ru.ar2code.mutableliveevent.preTest.FakeViewModel

@RunWith(MockitoJUnitRunner::class)
class MutableLiveEventTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var firstObserver: Observer<EventArgs<Int>>

    @Mock
    lateinit var secondObserver: Observer<EventArgs<Int>>

    @Test
    fun `Send an event to all observers`() {

        val vm = FakeViewModel()

        vm.observeEvent(firstObserver)
        vm.observeEvent(secondObserver)

        val event = EventArgs(0)
        vm.sendEvent(event)

        verify(firstObserver).onChanged(event)
        verify(secondObserver).onChanged(event)
    }

    @Test
    fun `Stop event chain if it was handled`() {

        val vm = FakeViewModel()

        val handledObserver = Observer<EventArgs<Int>> {
            it.handled = true
        }

        vm.observeEvent(handledObserver)
        vm.observeEvent(secondObserver)

        val event = EventArgs(0)
        vm.sendEvent(event)

        verifyZeroInteractions(secondObserver)
    }

    @Test
    fun `Observer won't invoke if liveEvent had data before subscription`() {
        val vm = FakeViewModel()

        val event = EventArgs(0)
        vm.sendEvent(event)

        vm.observeEvent(firstObserver)

        verifyZeroInteractions(firstObserver)
    }

    @Test
    fun `LiveEvent has appropriate number of observers`() {
        val vm = FakeViewModel()

        vm.observeEventForever(firstObserver)
        vm.observeEvent(secondObserver)

        Truth.assertThat(vm.getObserversCount()).isEqualTo(2)
    }

    @Test
    fun `LiveEvent has appropriate number of observers after remove one`() {
        val vm = FakeViewModel()

        vm.observeEvent(firstObserver)
        vm.observeEvent(secondObserver)

        Truth.assertThat(vm.getObserversCount()).isEqualTo(2)

        vm.removeObserver(secondObserver)

        Truth.assertThat(vm.getObserversCount()).isEqualTo(1)
    }

    @Test
    fun `LiveEvent has appropriate number of observers when remove all`() {
        val vm = FakeViewModel()

        vm.observeEvent(firstObserver)
        vm.observeEvent(secondObserver)

        Truth.assertThat(vm.getObserversCount()).isEqualTo(2)

        vm.removeAllObservers()

        Truth.assertThat(vm.getObserversCount()).isEqualTo(0)
    }
}