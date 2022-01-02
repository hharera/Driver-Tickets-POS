object TestDi {
    const val truth = "com.google.truth:truth:1.1.3"

    const val hiltTesting = "com.google.dagger:hilt-android-testing:${Versions.HILT_VERSION}"

    const val testRunner = "androidx.test:runner:${Versions.TEST_RUNNER_VERSION}"
    const val androidCoreTesting = "androidx.arch.core:core-testing:${Versions.ANDROIDX_CORE_Testing_VERSION}"

    const val junit4 = "junit:junit:${Versions.TEST_JUNIT_VERSION}"
    const val junitExt = "androidx.test.ext:junit:1.1.2"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}"

    const val fragmentTest =
        "androidx.fragment:fragment-testing:${Versions.ANDROIDX_FRAGMENT_TESTING_VERSION}"
    const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES_VERSION}"
}