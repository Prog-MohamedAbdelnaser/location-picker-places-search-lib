package com.softtech.android_structure.data.exceptions

class APIException(var code: String, override var message: String) : RuntimeException()