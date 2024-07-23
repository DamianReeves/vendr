import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

group = "me.user"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    macosX64()
    macosArm64()
    linuxX64()
    mingwX64()
    jvm().mainRun {
        mainClass.set("MainKt")
    }

    targets.withType<KotlinNativeTarget> {
        binaries {
            executable {
                baseName = "vendr"
                entryPoint = "main"
                runTask?.run {
                    val args = providers.gradleProperty("runArgs")
                    argumentProviders.add(CommandLineArgumentProvider {
                        args.orNull?.split(' ') ?: emptyList()
                    })
                }
            }
        }
    }
}
