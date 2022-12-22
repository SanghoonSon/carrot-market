package com.carrot.market

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CarrotMarketApplication

fun main(args: Array<String>) {
    runApplication<CarrotMarketApplication>(*args)
}
