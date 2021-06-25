package br.com.code.produtor

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic
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
        return HttpResponse.ok("$key e o $value")
    }
}

@KafkaClient
interface ProductClient {
    @Topic("my-products")
    fun sendProduct(@KafkaKey key: String, value: String)
}