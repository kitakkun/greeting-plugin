package greeting.test

import greeting.annotations.Greetable
import kotlin.test.Test

@Greetable
class LovelyClass

class GreetingTest {
    @Test
    fun test() {
        val lovelyClass = LovelyClass()
        lovelyClass.greet(name = "世界")
    }
}
