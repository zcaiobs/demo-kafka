package br.com.code.demo

import io.micronaut.configuration.kafka.annotation.*
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import javax.inject.Inject

@Controller("/")
class ProdutorController {

    @Inject
    lateinit var client: ProductClient

    @Post
    fun enviarMensagem(key: String, value: String): HttpResponse<Any> {
        client.sendProduct(key, value)
        return HttpResponse.ok("$key, $value")
    }
}

@KafkaClient
interface ProductClient {
    @Topic("my-products")
    fun sendProduct(@KafkaKey key: String, value: String)
}

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
class ConsumerListener {
    @Topic("my-products")
    fun receiveMesssage(@KafkaKey key: String, value: String) {
        println("Result key: $key, value: $value")
    }
}