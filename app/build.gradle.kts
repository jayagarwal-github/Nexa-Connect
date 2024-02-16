plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.nexaconnect"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.nexaconnect"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


                 packaging{
                resources {
                    excludes.add("META-INF/DEPENDENCIES.txt")
                    excludes.add("META-INF/DEPENDENCIES")
                    excludes.add("META-INF/LICENSE")
                    excludes.add("META-INF/LICENSE.txt")
                    // add other files to exclude as needed
                }
                jniLibs {
                    // use this block to exclude .so file patterns
                }
            }




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
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-firestore:24.10.2")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("androidx.annotation:annotation:1.7.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.test.uiautomator:uiautomator:2.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //noinspection BomWithoutPlatform
    implementation ("com.google.firebase:firebase-bom:32.7.2")
    implementation("com.google.firebase:firebase-analytics:21.5.1")


    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.14.2")
    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.3.0-alpha01")
    implementation ("com.google.auth:google-auth-library-oauth2-http:1.2.1")
    implementation ("com.google.firebase:firebase-database:20.3.0")
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    implementation ("com.firebaseui:firebase-ui-firestore:8.0.2")
    implementation ("com.firebaseui:firebase-ui-database:8.0.2")
    implementation ("com.squareup.picasso:picasso:2.8")
    implementation ("com.google.firebase:firebase-core:21.1.1")
    implementation ("com.firebaseui:firebase-ui-database")
}