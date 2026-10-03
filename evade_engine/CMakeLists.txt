cmake_minimum_required(VERSION 4.3)
project(evade_engine)

set(CMAKE_CXX_STANDARD 26)
# Оставляем только безопасную статическую линковку C++ без поломки потоков
set(CMAKE_SHARED_LINKER_FLAGS "-static-libgcc -static-libstdc++")

# Заставляем CLion выдать точное имя EvadeEngine.dll без приставки "lib"

add_library(evade_engine SHARED library.cpp)
set_target_properties(evade_engine PROPERTIES PREFIX "" OUTPUT_NAME "EvadeEngine")