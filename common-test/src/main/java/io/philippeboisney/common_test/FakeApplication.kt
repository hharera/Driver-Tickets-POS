package io.philippeboisney.common_test

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.loadKoinModules

/**
 * We use a separate [Application] for tests to prevent initializing release modules.
 * On the contrary, we will provide inside our tests custom [Module] directly.
 */
class FakeApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            loadKoinModules(emptyList())
        }
    }
}