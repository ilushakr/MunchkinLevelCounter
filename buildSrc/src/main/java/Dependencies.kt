object Dependencies {

    object Ktor {
        private const val ktorVersion = "2.1.1"

        const val ktorCore = "io.ktor:ktor-client-core:$ktorVersion"
        const val ktorLogging = "io.ktor:ktor-client-logging:$ktorVersion"
        const val ktorSerialization = "io.ktor:ktor-client-serialization:$ktorVersion"
        const val ktorNegotiation = "io.ktor:ktor-client-content-negotiation:$ktorVersion"
        const val ktorSerializationJson = "io.ktor:ktor-serialization-kotlinx-json:$ktorVersion"

        const val ktorClientAndroid = "io.ktor:ktor-client-okhttp:$ktorVersion"
        const val ktorClientIos = "io.ktor:ktor-client-darwin:$ktorVersion"
    }

    object KotlinX {
        private const val serializationVersion = "1.4.0"
        const val serialization =
            "org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion"

        private const val coroutinesVersion = "1.6.4"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
    }

    object Koin {
        private const val koin = "3.2.0"
        const val core = "io.insert-koin:koin-core:${koin}"
        const val test = "io.insert-koin:koin-test:${koin}"
        const val android = "io.insert-koin:koin-android:${koin}"
    }

    object SQLDelight {
        private const val sqlDelightVersion = "1.5.3"
        const val runtime = "com.squareup.sqldelight:runtime:$sqlDelightVersion"
        const val androidDriver = "com.squareup.sqldelight:android-driver:$sqlDelightVersion"
        const val nativeDriver = "com.squareup.sqldelight:native-driver:$sqlDelightVersion"
    }

    object Compose {
        private const val navVersion = "2.5.2"

        const val navigation = "androidx.navigation:navigation-compose:$navVersion"

        const val composeVersion = "1.2.1"
        const val icons = "androidx.compose.material:material-icons-extended:$composeVersion"

        const val graphics = "androidx.compose.ui:ui-graphics:$composeVersion"
    }

    object Serialization {
        private const val gsonVersion = "2.9.1"
        const val gson = "com.google.code.gson:gson:$gsonVersion"
    }

}