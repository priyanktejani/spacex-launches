plugins {
    id(Plugins.ANDROID_LIBRARY)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.DAGGER_HILT)
    id(Plugins.NAVIGATION_SAFEARGS)
}

android {
    namespace = "com.spacex.launches.feature.rocketlaunch_details"
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

    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:data"))

    implementation(Libs.core)
    implementation(Libs.appcompat)
    implementation(Libs.material)

    implementation(Libs.Navigation.navigationFragment)

    implementation(Libs.DaggerHilt.hiltAndroid)
    kapt(Libs.DaggerHilt.hiltAndroidCompiler)
    kapt(Libs.DaggerHilt.hiltCompiler)

    implementation(Libs.ScalableSizeUnit.sdp)
    implementation(Libs.ScalableSizeUnit.ssp)

    implementation(Libs.Lottie.lottie)

    testImplementation(Libs.TestImplementation.kotlinJunit)
    testImplementation(Libs.TestImplementation.kotlinCoroutinesTest)
    testImplementation(Libs.TestImplementation.junit)

    androidTestImplementation(Libs.AndroidTestImplementation.junitExt)
    androidTestImplementation(Libs.AndroidTestImplementation.espressoCore)
}