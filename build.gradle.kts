plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.json:json:20240303")
    implementation("org.postgresql:postgresql:42.3.0")
    implementation("commons-codec:commons-codec:1.17.1")
    implementation("io.github.cdimascio:dotenv-java:3.1.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version,
            "Main-Class" to "org.example.Main" // Replace with your main class
        )
    }

    archiveBaseName.set("${project.name}-all")
    from({
        configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
    })
    with(tasks.jar.get() as CopySpec)

}