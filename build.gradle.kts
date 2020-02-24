buildscript {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    checkstyle
}

subprojects {
    group = "com.openosrs.externals"

    project.extra["PluginProvider"] = "OpenOSRS"
    project.extra["ProjectUrl"] = "https://discord.gg/OpenOSRS"
    project.extra["PluginLicense"] = "3-Clause BSD License"

    repositories {
        jcenter()
        mavenLocal()
        maven(url = "https://repo.runelite.net")
        maven(url = "https://jitpack.io")
    }

    apply<JavaLibraryPlugin>()
    apply(plugin = "checkstyle")

    checkstyle {
        maxWarnings = 0
        toolVersion = "8.25"
        isShowViolations = true
        isIgnoreFailures = false
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks {
        withType<JavaCompile> {
            options.encoding = "UTF-8"
        }

        withType<Jar> {
            doLast {
                copy {
                    from("./build/libs/")
                    into("../release/")
                }
            }
        }

        withType<AbstractArchiveTask> {
            isPreserveFileTimestamps = false
            isReproducibleFileOrder = true
            dirMode = 493
            fileMode = 420
        }

        withType<Checkstyle> {
            group = "verification"
        }
    }
}