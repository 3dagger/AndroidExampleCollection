import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("com.google.dagger.hilt.android")
	id("kotlin-parcelize")
	kotlin("kapt")
}

fun getLocalProperties(propName: String): String {
	return gradleLocalProperties(rootDir).getProperty(propName)
}

android {
	namespace = "kr.dagger.stateflowexample"
	compileSdk = 33

	defaultConfig {
		applicationId = "kr.dagger.stateflowexample"
		minSdk = 24
		targetSdk = 33
		versionCode = 1
		versionName = "1.0"
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		buildConfigField("String", "TMDB_KEY", getLocalProperties("TMDB_KEY"))
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
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	kotlinOptions {
		jvmTarget = JavaVersion.VERSION_17.toString()
	}

	buildFeatures {
		dataBinding = true
		viewBinding = true
		buildConfig = true
	}
}

dependencies {

	implementation("androidx.core:core-ktx:1.9.0")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.material:material:1.8.0")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")
	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

	implementation("androidx.fragment:fragment-ktx:1.6.1")
	implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

	// Navigation
	implementation("androidx.navigation:navigation-ui-ktx:2.5.1")
	implementation("androidx.navigation:navigation-fragment-ktx:2.5.1")

	// Coroutine
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.2")

	// Retrofit
	implementation("com.squareup.retrofit2:retrofit:2.9.0")
	implementation("com.squareup.retrofit2:converter-gson:2.9.0")

	// Okhttp3 BOM
	implementation(platform("com.squareup.okhttp3:okhttp-bom:4.11.0"))
	implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

	// Hilt
	implementation("com.google.dagger:hilt-android:2.46.1")
	kapt("com.google.dagger:hilt-android-compiler:2.46.1")

	// Glide
	implementation("com.github.bumptech.glide:glide:4.16.0")
}