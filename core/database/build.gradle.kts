plugins {
    id(Plugins.ANDROID_LIBRARY)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.DAGGER_HILT)
}

android {
    namespace = "com.spacex.launches.core.database"
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

    implementation(Libs.core)
    implementation(Libs.appcompat)
    implementation(Libs.material)

    implementation(Libs.Room.room)
    kapt(Libs.Room.roomCompiler)
    implementation(Libs.Room.roomPaging)

    implementation(Libs.DaggerHilt.hiltAndroid)
    kapt(Libs.DaggerHilt.hiltAndroidCompiler)
    kapt(Libs.DaggerHilt.hiltCompiler)

    testImplementation(Libs.TestImplementation.junit)

    androidTestImplementation(Libs.AndroidTestImplementation.junitExt)
    androidTestImplementation(Libs.AndroidTestImplementation.espressoCore)
    androidTestImplementation(Libs.AndroidTestImplementation.archCoreTesting)
}