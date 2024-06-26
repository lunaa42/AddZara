buildscript {
    val kotlin_version by extra("1.9.20-RC2")
    dependencies {
        classpath ("com.android.tools.build:gradle:8.3.2")
        classpath ("com.google.gms:google-services:4.4.0")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
    repositories {
        mavenCentral()
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.google.gms.google-services") version "4.4.0" apply false
    id("com.android.application") version "8.3.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20-RC2" apply false
}
