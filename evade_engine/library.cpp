#include <iostream>

#if defined(_WIN32)
    #define EXPORT __declspec(dllexport)
#else
    #define EXPORT __attribute__((visibility("default")))
#endif

extern "C" {
// Обязательно используем C-ABI для корректного связывания с Java
EXPORT void renderFrame(float cameraX, float cameraY, float cameraZ) {
    // В будущем здесь будет вызов Vulkan / GLFW
    std::cout << "[C++ Native] Кадр отрисован. Камера: X="
              << cameraX << ", Y=" << cameraY << ", Z=" << cameraZ << std::endl;
}
}