plugins {
    java
    application
}

group = "com.evade"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Математика для 3D графики (матрицы камеры, векторы 3D)
    implementation("org.joml:joml:1.10.9")
}

java {
    toolchain {
        // Требуется Java 22 или новее для стабильного FFM API
        languageVersion.set(JavaLanguageVersion.of(27))
    }
}

application {
    mainClass.set("com.evade.Main")
}

// Флаг JVM, разрешающий доступ к нативной памяти C++
tasks.withType<JavaExec> {
    jvmArgs("--enable-native-access=ALL-UNNAMED")
}
// Указываем компилятору использовать UTF-8
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

// Задаем кодировку вывода в консоль при запуске
tasks.withType<JavaExec> {
    jvmArgs(
        "--enable-native-access=ALL-UNNAMED",
        "-Dfile.encoding=UTF-8",
        "-Dsun.stdout.encoding=UTF-8",
        "-Dsun.stderr.encoding=UTF-8"
    )
}
tasks.withType<JavaExec> {
    jvmArgs("--enable-native-access=ALL-UNNAMED")
    // Указывает Java искать .dll/.so файлы в корне проекта
    systemProperty("java.library.path", projectDir.absolutePath)
}