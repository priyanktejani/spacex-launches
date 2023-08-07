plugins {
    id(Plugins.ANDROID_LIBRARY)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.DAGGER_HILT)
}

android {
    namespace = "com.spacex.launches.core.network"
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = CompileOptions.sourceCompatibility
        targetCompatibility = CompileOptions.targetCompatibility
    }
    kotlinOptions {
        jvmTarget = CompileOptions.KotlinOptions.jvmTarget
    }
}

dependencies {

    implementation(Libs.Retrofit.retrofit)
    implementation(Libs.Retrofit.moshiKotlin)
    implementation(Libs.Retrofit.retrofitConverterMoshi)

    implementation(Libs.DaggerHilt.hiltAndroid)
    kapt(Libs.DaggerHilt.hiltAndroidCompiler)
    kapt(Libs.DaggerHilt.hiltCompiler)

    implementation(Libs.AndroidTestImplementation.squareupOkHttp)

    testImplementation(Libs.TestImplementation.junit)
    androidTestImplementation(Libs.AndroidTestImplementation.junitExt)
    androidTestImplementation(Libs.AndroidTestImplementation.espressoCore)
}