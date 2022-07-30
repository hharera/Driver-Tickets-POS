package io.philippeboisney.common_test.rules

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
class CoroutinesMainDispatcherRule : TestWatcher() {

    private val singleThreadExecutor = Executors.newSingleThreadExecutor()

    override fun starting(description: Description?) {
        super.starting(description)
//        CoroutineDispatcher.s.setMain(singleThreadExecutor.asCoroutineDispatcher())
    }

    override fun finished(description: Description?) {
        super.finished(description)
        singleThreadExecutor.shutdownNow()
//        Dispatchers.resetMain()
    }
}