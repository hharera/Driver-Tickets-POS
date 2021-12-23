import java.net.URI

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Libs.gradleTools)
        classpath(Libs.kotlinPlugin)
        classpath(Libs.navigationSafeArgsPlugin)
        classpath(Libs.googleServices)
        classpath(Libs.hiltPlugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}