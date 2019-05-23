package gl51.project.store

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*

import javax.inject.Inject

@Controller("/product")
class ProductController {

    @Inject
    ProductStorage productStorage

    @Get("{id}")
    Product getByID(String id) {
        try {
            productStorage.getByID(id)
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    @Get("/")
    List<Product> all() {
        try {
            productStorage.all()
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    @Post("/")
    String create(@Body Product p) {
        try {
            productStorage.save(p)
        } catch (Exception e) {
            e.printStackTrace()
            HttpStatus.INTERNAL_SERVER_ERROR
        }
    }

    @Put("/{id}")
    HttpStatus update(String id, @Body Product p) {
        try {
            productStorage.update(id, p)
            HttpStatus.OK
        } catch (Exception e) {
            e.printStackTrace()
            HttpStatus.NOT_FOUND
        }
    }

    @Delete("/{id}")
    void delete(String id) {
        try {
            productStorage.delete(id)
            HttpStatus.OK
        } catch (Exception e) {
            e.printStackTrace()
            HttpStatus.NOT_FOUND
        }
    }
}
