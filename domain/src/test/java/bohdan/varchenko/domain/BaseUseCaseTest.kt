package bohdan.varchenko.domain

import com.nhaarman.mockitokotlin2.withSettings
import org.mockito.Mockito
import java.lang.reflect.Type
import kotlin.reflect.jvm.javaType

internal abstract class BaseUseCaseTest {

    protected inline fun <reified DTO> createUseCase(): DTO {
        if (!DTO::class.isData) throw IllegalArgumentException("DTO is not valid")
        val useCaseDtoField = DTO::class.members.find { it.name == "useCase" }
            ?: throw IllegalArgumentException("DTO should contain 'useCase' field")
        val useCaseClass = DTO::class.java.declaredFields?.find { it.name == "useCase" }?.type
            ?: throw RuntimeException("")
        val useCaseConstructor = useCaseClass.constructors.first()

        val dtoConstructor = DTO::class.java.constructors.first()
        val dtoParameters = mutableMapOf<Type, Any>()

        val useCaseParameters = useCaseConstructor.parameters.map { p ->
            Mockito.mock(p.type, withSettings())
                .also { m ->
                    DTO::class.members.find { it.returnType.javaType == p.parameterizedType }
                        ?.run { dtoParameters[p.parameterizedType] = m }
                }
        }
        dtoParameters[useCaseDtoField.returnType.javaType] =
            useCaseConstructor.newInstance(*useCaseParameters.toTypedArray())

        val sortedParameters = dtoConstructor.parameters.map { constructorParameter ->
            dtoParameters[constructorParameter.parameterizedType]
        }
            .toTypedArray()
        return dtoConstructor.newInstance(*sortedParameters) as DTO
    }

    protected inline fun <reified T> block(block: T.() -> Unit) {
        val useCase = createUseCase<T>()
        useCase.block()
    }
}