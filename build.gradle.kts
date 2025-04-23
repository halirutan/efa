import org.gradle.api.tasks.bundling.Jar

plugins {
    java
    application
}

group = "de.nmichael.efa"
version = "2.0.0"

repositories {
    mavenCentral()
    flatDir {
        dirs("plugins/flatlaf", "plugins/ftp", "plugins/help", "plugins/jsuntimes", "plugins/mail", "plugins/pdf", "plugins/weather")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

// Configure source sets for non-standard project structure
sourceSets {
    main {
        java {
            srcDirs(".")
            exclude("build/**")
        }
        resources {
            srcDirs(".")
            exclude("**/*.java")
            exclude("build/**")
        }
    }
}

application {
    mainClass.set("de.nmichael.efa.base.Main")
}

dependencies {
    // JavaFX dependencies
    implementation("org.openjfx:javafx-controls:11.0.2")
    implementation("org.openjfx:javafx-swing:11.0.2")
    implementation("org.openjfx:javafx-graphics:11.0.2")
    implementation("org.openjfx:javafx-base:11.0.2")

    // Local JAR files from plugins directory
    // FlatLaf
    implementation(files("plugins/flatlaf/flatlaf-3.2.5.jar"))

    // FTP Plugin
    implementation(files("plugins/ftp/edtftpj.jar"))
    implementation(files("plugins/ftp/jsch-0.1.55.jar"))

    // Mail Plugin
    implementation(files("plugins/mail/javax.mail.jar"))
    implementation(files("plugins/mail/activation.jar"))

    // JSUNTIMES Plugin
    implementation(files("plugins/jsuntimes/jsuntimes.jar"))

    // PDF Plugin
    implementation(files("plugins/pdf/avalon-framework.jar"))
    implementation(files("plugins/pdf/batik-all.jar"))
    implementation(files("plugins/pdf/commons-io.jar"))
    implementation(files("plugins/pdf/commons-logging.jar"))
    implementation(files("plugins/pdf/fop.jar"))
    implementation(files("plugins/pdf/xmlgraphics-commons.jar"))

    // Weather Plugin
    implementation(files("plugins/weather/commons-codec.jar"))
    implementation(files("plugins/weather/signpost-core.jar"))

    // Help Plugin
    implementation(files("plugins/help/jh.jar"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.register<Jar>("fatJar") {
    archiveClassifier.set("fat")

    manifest {
        attributes["Main-Class"] = "de.nmichael.efa.base.Main"
    }

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.register<Copy>("copyResources") {
    from("help") {
        into("help")
    }
    from("fmt") {
        into("fmt")
    }
    from("eou") {
        into("eou")
    }
    from("cfg") {
        into("cfg")
    }
    from(fileTree(".") {
        include("*.properties")
        exclude("efa_*.properties")
    })
    from(fileTree(".") {
        include("efa_*.properties")
    }) {
        into("program")
    }
    into("build/resources/main")
}

tasks.named("processResources") {
    dependsOn("copyResources")
}
