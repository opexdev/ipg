package co.nilin.opex.payment.dao

import co.nilin.opex.payment.model.Invoice
import com.opex.payment.core.model.InvoiceStatus
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Repository
interface InvoiceRepository : ReactiveCrudRepository<Invoice, Long> {

    fun findAllByUserId(userId: String): Flux<Invoice>

    fun findByReference(reference: String): Mono<Invoice?>

    @Query("SELECT * FROM invoice WHERE status = 'Open' AND update_date < :time")
    fun findAllOpenOlderThan(time: LocalDateTime): Flux<Invoice>

    @Query("SELECT * FROM invoice WHERE status = 'Done' AND is_notified = false AND update_date < :time")
    fun findAllDoneButNotNotifiedOlderThan(time: LocalDateTime): Flux<Invoice>

    fun findByUserIdAndStatus(userId: String, status: InvoiceStatus): Flux<Invoice>
}