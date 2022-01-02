buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Libs.gradleTools)
        classpath(Libs.kotlinPlugin)
        classpath(Libs.navigationSafeArgsPlugin)
        classpath(Libs.hiltPlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.20")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}