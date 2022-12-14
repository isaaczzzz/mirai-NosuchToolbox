plugins {
    val kotlinVersion = "1.7.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.13.0"
}

group = "cc.nozuch"
version = "0.1.1"

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}

dependencies {
    implementation("com.alibaba:fastjson:2.0.19")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("commons-io:commons-io:2.11.0")
}