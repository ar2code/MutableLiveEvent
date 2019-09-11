package ru.ar2code.mutableliveevent

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PendingObserverTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var realObserver: Observer<EventArgs<Int>>

    @Test
    fun `User real observer changed if pending observer awaits value and changed`() {

        val event = EventArgs(0)
        val pendingObserver = PendingObserver(realObserver)

        pendingObserver.awaitValue()
        pendingObserver.onChanged(event)

        verify(realObserver).onChanged(event)
    }

    @Test
    fun `User real observer not changed if pending observer not await value`() {

        val event = EventArgs(0)
        val pendingObserver = PendingObserver(realObserver)

        pendingObserver.onChanged(event)

        verifyZeroInteractions(realObserver)
    }

    @Test
    fun `User real observer changes count equals awaitValue invoke counts`() {

        val event = EventArgs(0)
        val pendingObserver = PendingObserver(realObserver)

        pendingObserver.awaitValue()
        pendingObserver.onChanged(event)

        pendingObserver.awaitValue()
        pendingObserver.onChanged(event)

        pendingObserver.onChanged(event)
        pendingObserver.onChanged(event)
        pendingObserver.onChanged(event)

        verify(realObserver, times(2)).onChanged(event)
    }
}