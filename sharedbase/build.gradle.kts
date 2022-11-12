plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id(Plugins.sqlDelight)
}

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "sharedbase"
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {

                //Coroutines
                implementation(Dependencies.KotlinX.coroutines)

                // Ktor
                with(Dependencies.Ktor){
                    implementation(ktorCore)
                    implementation(ktorLogging)
                    implementation(ktorSerialization)
                    implementation(ktorSerializationJson)
                    implementation(ktorNegotiation)
                }

                // Koin
                with(Dependencies.Koin){
                    api(core)
                    api(test)
                }

                // SQLDelight
                implementation(Dependencies.SQLDelight.runtime)

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Dependencies.Ktor.ktorClientAndroid)
                implementation(Dependencies.SQLDelight.androidDriver)
                implementation(Dependencies.Koin.android)
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

android {
    namespace = "com.example.munchkinlevelcounter"
    compileSdk = 32
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
}