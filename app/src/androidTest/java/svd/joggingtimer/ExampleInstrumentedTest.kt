package svd.joggingtimer

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import svd.joggingtimer.util.toHHMMSS

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        val test1 = 1000L
        val test2 = 999L

        assertEquals("00:00:01", test1.toHHMMSS())
        assertEquals("00:00:00", test2.toHHMMSS())
    }
}
