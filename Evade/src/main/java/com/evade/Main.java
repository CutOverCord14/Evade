package com.evade;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public class Main {

    private static MethodHandle renderFrameHandle;

    public static void main(String[] args) {
        System.out.println("[Java Engine] Инициализация системы...");

        // 1. Загрузка C++ библиотеки (RenderEngine.dll для Windows или libRenderEngine.so для Linux)
        // Файл .dll должен лежать в корне проекта
        try {
            System.load("C:\\Users\\MEGALODON LIME PEPE)\\IdeaProjects\\Evade\\EvadeEngine.dll");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Ошибка: Не найдена скомпилированная C++ библиотека рендера EvadeEngine!");
            e.printStackTrace();
            return;
        }

        // 2. Связывание Java с C++ функцией через FFM API
        Linker linker = Linker.nativeLinker();
        SymbolLookup lookup = SymbolLookup.loaderLookup();

        // Поиск нативной функции renderFrame
        MemorySegment renderSymbol = lookup.find("renderFrame")
                .orElseThrow(() -> new RuntimeException("Функция 'renderFrame' не найдена в C++ библиотеке!"));

        // Описание сигнатуры: void renderFrame(float cameraX, float cameraY, float cameraZ)
        FunctionDescriptor descriptor = FunctionDescriptor.ofVoid(
                ValueLayout.JAVA_FLOAT,
                ValueLayout.JAVA_FLOAT,
                ValueLayout.JAVA_FLOAT
        );

        renderFrameHandle = linker.downcallHandle(renderSymbol, descriptor);

        // 3. Игровой цикл (Game Loop)
        boolean isRunning = true;
        float cameraX = 0.0f, cameraY = 0.0f, cameraZ = 5.0f;

        System.out.println("[Java Engine] Игровой цикл запущен!");

        while (isRunning) {
            // Имитация перемещения камеры логикой Java
            cameraX += 0.01f;

            // Передача новых координат в C++ рендерер
            try {
                renderFrameHandle.invokeExact(cameraX, cameraY, cameraZ);
            } catch (Throwable e) {
                e.printStackTrace();
                break;
            }

            // Ограничение частоты кадров (~60 FPS)
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}