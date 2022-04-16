package co.nilin.opex.payment.config

import co.nilin.opex.payment.utils.logger.CustomLogger
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

@Configuration
class WebClientConfig {

    @Bean
    fun webClient(): WebClient {
        val logger = CustomLogger(HttpClient::class.java)
        return WebClient.builder()
            .clientConnector(
                ReactorClientHttpConnector(
                    HttpClient
                        .create()
                        .doOnRequest { _, connection ->
                            connection.addHandlerFirst(logger)
                        }
                )
            )
            .build()
    }

    @Bean
    fun mapper(): ObjectMapper {
        return ObjectMapper().registerKotlinModule()
    }

}
