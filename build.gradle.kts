import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension

buildscript {
  repositories {
    google()
    mavenCentral()
    jcenter()
    maven(url = "https://plugins.gradle.org/m2/")
  }
  dependencies {
    classpath(Dependencies.Ktlint.plugin)
    classpath(Dependencies.Android.gradlePlugin)
    classpath(Dependencies.Kotlin.gradlePlugin)
  }
}

allprojects {
  repositories {
    google()
    mavenCentral()
    jcenter()
    maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
    maven(url = "https://jitpack.io")
  }

  tasks.withType<KotlinCompile> {
    kotlinOptions {
      // Allow warnings when running from IDE, makes it easier to experiment.
      // allWarningsAsErrors = true

      freeCompilerArgs = freeCompilerArgs + listOf(
        "-Xallow-jvm-ir-dependencies",
        "-Xopt-in=kotlin.RequiresOptIn"
      )
      jvmTarget = "1.8"
    }
  }
}

subprojects {
  apply(plugin = "org.jlleitschuh.gradle.ktlint")

  configure<KtlintExtension> {
    debug.set(false)
  }

  configurations.all {
    if (!name.startsWith("ktlint")) {
      resolutionStrategy {
        eachDependency {
          // Force Kotlin to our version
          if (requested.group == "org.jetbrains.kotlin") {
            useVersion("1.4.31")
          }
        }
      }
    }
  }
}

tasks {
  val clean by registering(Delete::class) {
    delete(buildDir)
  }
}
