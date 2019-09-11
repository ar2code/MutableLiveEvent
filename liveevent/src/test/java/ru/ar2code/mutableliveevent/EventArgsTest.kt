package ru.ar2code.mutableliveevent

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class EventArgsTest {

    @Test
    fun `Event data always available while is not handled`() {

        val eventValue = 1
        val intEvent = EventArgs(eventValue)

        assertThat(intEvent.data).isEqualTo(eventValue)
        assertThat(intEvent.data).isEqualTo(eventValue)
    }

    @Test
    fun `Event data becomes null after was handled`() {

        val eventValue = 1
        val intEvent = EventArgs(eventValue)

        assertThat(intEvent.data).isEqualTo(eventValue)

        intEvent.handled = true

        assertThat(intEvent.data).isNull()
    }

    @Test
    fun `Event data can takes null`() {
        val intEvent = EventArgs<Int>(null)
        assertThat(intEvent.data).isNull()
    }
}
