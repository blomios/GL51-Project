package gl51.project

import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class StudentControllerSpec extends Specification {
    @Shared @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)
    @Shared @AutoCleanup RxHttpClient client = embeddedServer.applicationContext.createBean(RxHttpClient, embeddedServer.getURL())

    void "test index"() {
        given:
        def response = client.toBlocking().exchange("/student", Argument.listOf(Student).type)

        expect:
        response.status == HttpStatus.OK
        response.body()[0].firstName == 'José'
        response.body()[0].lastName == 'Paldire'
    }
}