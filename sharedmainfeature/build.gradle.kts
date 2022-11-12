import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id(Plugins.sqlDelight)
}

kotlin {
    android()
    
    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "sharedmainfeature"
            xcf.add(this)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {

                implementation(project(":sharedbase"))

                //Coroutines
                implementation(Dependencies.KotlinX.coroutines)

                // Koin
                with(Dependencies.Koin){
                    api(core)
                    api(test)
                }

                implementation(Dependencies.SQLDelight.runtime)

                // Ktor
                with(Dependencies.Ktor){
                    implementation(ktorCore)
                    implementation(ktorLogging)
                    implementation(ktorSerialization)
                    implementation(ktorSerializationJson)
                    implementation(ktorNegotiation)
                }

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting{
            dependencies{
                implementation(Dependencies.Ktor.ktorClientAndroid)
                implementation(Dependencies.SQLDelight.androidDriver)
                implementation(Dependencies.Koin.android)

                implementation(Dependencies.Compose.graphics)
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation(Dependencies.Ktor.ktorClientIos)

                implementation(Dependencies.SQLDelight.nativeDriver)
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

kotlin.targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java) {
    binaries.all {
        binaryOptions["memoryModel"] = "experimental"
    }
}


android {
    namespace = "com.example.sharedmainfeature"
    compileSdk = 32
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
}

sqldelight {
    database("MunchkinDatabase") {
        packageName = "com.example.sharedmainfeature.shared.cache"
    }
}