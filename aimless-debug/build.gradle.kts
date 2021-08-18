repositories {
    mavenLocal()
}

val api = project(":aimless-api")

dependencies {
    implementation(api)
}

tasks {
    processResources {
        filesMatching("*.yml") {
            expand(project.properties)
        }
    }

    create<Jar>("debugMojangJar") {
        archiveBaseName.set("Aimless")
        archiveVersion.set("")
        archiveClassifier.set("DEBUG")
        archiveAppendix.set("MOJANG")

        (listOf(project(":aimless-api"), project) + project(":aimless-core").let { listOf(it) + it.subprojects }).forEach {
            from(it.sourceSets["main"].output)
        }

        doLast {
            copy {
                from(archiveFile)
                val plugins = File(rootDir, ".debug-mojang/plugins/")
                into(if (File(plugins, archiveFileName.get()).exists()) File(plugins, "update") else plugins)
            }
        }
    }

    create<Jar>("debugPaperJar") {
        archiveBaseName.set("Aimless")
        archiveVersion.set("")
        archiveClassifier.set("DEBUG")
        archiveAppendix.set("PAPER")

        from(api.sourceSets["main"].output)
        from(project.sourceSets["main"].output)

        (project(":aimless-core").tasks.named("paperJar").get() as Jar).let { paperJar ->
            dependsOn(paperJar)
            from(zipTree(paperJar.archiveFile))
        }

        doLast {
            copy {
                from(archiveFile)
                val plugins = File(rootDir, ".debug-paper/plugins/")
                into(if (File(plugins, archiveFileName.get()).exists()) File(plugins, "update") else plugins)
            }
        }
    }
}