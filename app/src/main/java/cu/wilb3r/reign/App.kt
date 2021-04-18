package cu.wilb3r.reign

import android.annotation.SuppressLint
import android.app.Application
import cu.wilb3r.reign.di.dataBaseModule
import cu.wilb3r.reign.di.networkModule
import cu.wilb3r.reign.di.repositoryModule
import cu.wilb3r.reign.di.uiModule
import org.acra.ACRA
import org.acra.ReportingInteractionMode
import org.acra.annotation.ReportsCrashes
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@SuppressLint("NonConstantResourceId")
/*@ReportsCrashes(
    mailTo = "wilberjasonmarlon@gmail.com",
    mode = ReportingInteractionMode.TOAST,
    resToastText = R.string.crash_text
)*/

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val appComponent = listOf(
            uiModule,
            networkModule,
            repositoryModule,
            dataBaseModule
        )
        startKoin {
            androidContext(this@App)
            modules(appComponent)
        }
        //ACRA.init(this)
    }
}