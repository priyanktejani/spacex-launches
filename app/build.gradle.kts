plugins {
    id(Plugins.ANDROID_APPLICATION)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.DAGGER_HILT)
}

android {
    namespace = "com.spacex.launches"
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = "com.spacex.launches"
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures {
        viewBinding = true
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

    implementation(project(":feature:rocketlaunches"))
    implementation(project(":feature:rocketlaunch_details"))

    implementation(Libs.core)
    implementation(Libs.appcompat)
    implementation(Libs.material)

    implementation(Libs.Navigation.navigationFragment)
    implementation(Libs.Navigation.navigationUI)

    implementation(Libs.DaggerHilt.hiltAndroid)
    kapt(Libs.DaggerHilt.hiltAndroidCompiler)
    kapt(Libs.DaggerHilt.hiltCompiler)

    testImplementation(Libs.TestImplementation.junit)

    androidTestImplementation(Libs.AndroidTestImplementation.junitExt)
    androidTestImplementation(Libs.AndroidTestImplementation.espressoCore)
    androidTestImplementation(Libs.AndroidTestImplementation.navigationTesting)

}
