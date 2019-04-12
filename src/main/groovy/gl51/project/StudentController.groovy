package projet.gl51

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.HttpStatus

import java.util.List


@Controller("/student")
class StudentController {

    @Get("/")
    List<Student> index() {
        [new Student(firstName: "Larry", lastName: "Golade"),
         new Student(firstName: "José", lastName: "Paldire"),
         new Student(firstName: "Sandra", lastName: "Geffroi"),
         new Student(firstName: "Jésus", lastName: "Perpeur"),
         new Student(firstName: "Lauri", lastName: "Kulèr")
        ]
    }
}