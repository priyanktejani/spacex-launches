import org.gradle.api.JavaVersion

object Version {
    const val core = "1.10.1"
    const val appcompat = "1.6.1"
    const val material = "1.9.0"
    const val constraint_layout = "1.9.0"
    const val nav = "2.6.0"
    const val room = "2.5.2"
    const val retrofit = "2.9.0"
    const val moshi = "2.9.0"
    const val moshi_kotlin = "1.15.0"
    const val junit = "4.13.2"
    const val junit_ext = "1.1.5"
    const val espresso_core = "3.5.1"
    const val dagger_hilt = "2.46.1"
    const val hilt_compiler = "1.0.0"
    const val archCoreTesting = "2.2.0"
    const val squareupOkHttp = "4.11.0"
    const val kotlinJunit = "1.9.0"
    const val kotlinCoroutinesTest = "1.7.2"
    const val sdp = "1.0.6"
    const val ssp = "1.0.6"
    const val lottie = "6.0.1"
}

object Libs {

    const val core = "androidx.core:core-ktx:${Version.core}"
    const val appcompat = "androidx.appcompat:appcompat:${Version.appcompat}"
    const val material = "com.google.android.material:material:${Version.material}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Version.constraint_layout}"

    object TestImplementation {
        const val junit = "junit:junit:${Version.junit}"
        const val kotlinJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Version.kotlinJunit}"
        const val kotlinCoroutinesTest =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.kotlinCoroutinesTest}"
    }

    object AndroidTestImplementation {
        const val junitExt = "androidx.test.ext:junit:${Version.junit_ext}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Version.espresso_core}"
        const val navigationTesting = "androidx.navigation:navigation-testing:${Version.nav}"
        const val archCoreTesting = "androidx.arch.core:core-testing:${Version.archCoreTesting}"
        const val squareupOkHttp = "com.squareup.okhttp3:mockwebserver:${Version.squareupOkHttp}"
    }

    object Navigation {
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Version.nav}"
        const val navigationUI = "androidx.navigation:navigation-ui-ktx:${Version.nav}"
        const val navigationDynamicFragment =
            "androidx.navigation:navigation-dynamic-features-fragment:${Version.nav}"
    }

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
        const val retrofitConverterMoshi = "com.squareup.retrofit2:converter-moshi:${Version.moshi}"
        const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Version.moshi_kotlin}"
    }

    object DaggerHilt {
        const val hiltAndroid = "com.google.dagger:hilt-android:${Version.dagger_hilt}"
        const val hiltAndroidCompiler =
            "com.google.dagger:hilt-android-compiler:${Version.dagger_hilt}"
        const val hiltCompiler = "androidx.hilt:hilt-compiler:${Version.hilt_compiler}"
    }

    object Room {
        const val room = "androidx.room:room-ktx:${Version.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Version.room}"
        const val roomPaging = "androidx.room:room-paging:${Version.room}"
    }

    object ScalableSizeUnit {
        const val sdp = "com.intuit.sdp:sdp-android:${Version.ssp}"
        const val ssp = "com.intuit.ssp:ssp-android:${Version.ssp}"
    }

    object Lottie {
        const val lottie = "com.airbnb.android:lottie:${Version.lottie}"
    }
}


object Plugins {
    const val ANDROID_APPLICATION = "com.android.application"
    const val ANDROID_LIBRARY = "com.android.library"
    const val KOTLIN_ANDROID = "android"
    const val KOTLIN_KAPT = "kapt"
    const val KOTLIN_PARCELIZE = "kotlin-parcelize"
    const val DAGGER_HILT = "dagger.hilt.android.plugin"
    const val NAVIGATION_SAFEARGS = "androidx.navigation.safeargs.kotlin"
}

object ProjectConfig {
    const val compileSdk = 33
    const val minSdk = 27
    const val targetSdk = 33
    const val versionCode = 1
    const val versionName = "1.0"
}

object CompileOptions {
    val sourceCompatibility = JavaVersion.VERSION_17
    val targetCompatibility = JavaVersion.VERSION_17

    object KotlinOptions {
        const val jvmTarget = "17"
    }
}