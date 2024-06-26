plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.addzara"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.addzara"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    lintOptions {
        warning ("deprecation")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

        tasks.withType<JavaCompile> {
            options.compilerArgs.add("-parameters")
        }

        buildFeatures {
            viewBinding = true
        }
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.10")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
        implementation("com.google.firebase:firebase-analytics")
        implementation("com.google.android.material:material:1.11.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        implementation("com.google.firebase:firebase-auth:22.3.1")
        implementation("com.google.firebase:firebase-firestore:24.11.1")
        implementation("com.google.firebase:firebase-storage:20.3.0")
        implementation("androidx.core:core-ktx:1.13.0")
        implementation("androidx.annotation:annotation:1.7.1")
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
        implementation("com.squareup.picasso:picasso:2.71828")
        implementation("com.squareup.picasso:picasso:2.71828")
        implementation ("com.google.android.material:material:1.4.0")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    }
}
dependencies {
    implementation("androidx.recyclerview:recyclerview:1.3.2")
}
