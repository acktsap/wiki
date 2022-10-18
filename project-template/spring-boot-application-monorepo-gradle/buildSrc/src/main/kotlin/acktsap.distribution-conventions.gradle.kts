plugins {
    distribution
}

tasks.named<Zip>("distZip") {
    dependsOn("jar") // generate jar file
    enabled = false
}

tasks.named<Tar>("distTar") {
    dependsOn("jar") // generate jar file
    compression = Compression.GZIP
    archiveExtension.set("tar.gz")
    enabled = true
}

tasks.named<Sync>("installDist") {
    dependsOn("jar") // generate jar file
}

distributions {
    main {
        contents {
            from("entrypoint.sh")
            from("prestop.sh")
            into("lib") {
                from(project.buildDir.absolutePath + "/libs")
                from(project.configurations.findByName("runtimeClasspath"))
            }
        }
    }
}
