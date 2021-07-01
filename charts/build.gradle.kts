import com.vanniktech.maven.publish.SonatypeHost

plugins {
  id("com.android.library")
  kotlin("android")
  id("com.vanniktech.maven.publish")
  id("maven-publish")
}

android {
  compileSdkVersion(Config.targetSdk)
  buildToolsVersion = Config.buildTools

  defaultConfig {
    minSdkVersion(Config.minSdk)
    targetSdkVersion(Config.targetSdk)
  }

  buildFeatures {
    compose = true
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  kotlinOptions {
    jvmTarget = "1.8"
    useIR = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = Dependencies.Compose.version
  }
}

dependencies {
  arrayOf(
    Dependencies.Compose.runtime,
    Dependencies.Compose.ui,
    Dependencies.Compose.foundation,
    Dependencies.Compose.layout,
    Dependencies.Compose.material,
    Dependencies.Compose.iconsExtended,
    Dependencies.Compose.animation,
    Dependencies.Compose.tooling,
    Dependencies.Compose.util,
    Dependencies.Accompanist.flow
  ).forEach {
    implementation(it)
  }
}

mavenPublish {
  sonatypeHost = SonatypeHost.S01
}

afterEvaluate {
  publishing {
    publications {
      // Creates a Maven publication called "release".
      create<MavenPublication>("release") {
        // Applies the component for the release build variant.
        from(components.getByName("release"))

        // You can then customize attributes of the publication as shown below.
        groupId = project.property("GROUP") as String
        artifactId = project.property("POM_ARTIFACT_ID") as String
        version = project.property("VERSION_NAME") as String
      }
    }
  }
}

publishing {
  repositories {
    maven {
      name = "AgensNexus"
      url = uri("http://repo.agens.no:8081/nexus/content/repositories/oss-releases/")
      isAllowInsecureProtocol = true
      credentials {
        username = project.properties["nexus_deploy_user"] as String? ?: ""
        password = project.properties["nexus_deploy_pwd"] as String? ?: ""
      }
    }
  }
}


