plugins {
    kotlin("plugin.allopen")
    kotlin("plugin.noarg")
    kotlin("plugin.jpa")
}

noArg {
    annotations(
        "javax.persistence.Entity",
        "javax.persistence.MappedSuperclass",
        "javax.persistence.Embeddable"
    )
}

allOpen {
    annotations(
        "javax.persistence.Entity",
        "javax.persistence.MappedSuperclass",
        "javax.persistence.Embeddable"
    )
}

dependencies {
    api(project(":application-infrastructure"))

    val queryDslVersion: String by project
    api("org.springframework.boot:spring-boot-starter-data-jpa")

    // querydsl
    implementation("com.querydsl:querydsl-jpa")
    kapt("com.querydsl:querydsl-apt:$queryDslVersion:jpa")
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                counter = "BRANCH"
                minimum = "0.005".toBigDecimal()
            }

            limit {
                counter = "LINE"
                minimum = "0.005".toBigDecimal()
            }
        }
    }
    classDirectories.setFrom(
        fileTree(project.buildDir) {
            exclude(
                "**/Q*.*",
                "**/*Test.*"
            )
            include(
                "**/classes/**/main/**"
            )
        }
    )
}
