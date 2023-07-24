plugins {
    id("java")
}

group = "org.db"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.zaxxer:HikariCP:4.0.3")
    implementation("mysql:mysql-connector-java:8.0.28")
}

tasks.test {
    useJUnitPlatform()
}