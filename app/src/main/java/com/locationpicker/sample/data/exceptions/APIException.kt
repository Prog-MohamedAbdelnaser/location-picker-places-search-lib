package com.locationpicker.sample.data.exceptions

class APIException(var code: String, override var message: String) : RuntimeException()