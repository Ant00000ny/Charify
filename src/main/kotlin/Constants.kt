package com.ant00000ny

import java.nio.file.Paths

val USER_HOME = System.getProperty("user.home")
    .let { Paths.get(it) }
    ?: throw Exception("user.home is null")
