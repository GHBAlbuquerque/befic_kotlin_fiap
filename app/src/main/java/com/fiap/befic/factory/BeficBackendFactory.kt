import com.fiap.befic.service.UsuarioBeficBackendService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BeficBackendFactory {

    val URL: String = "https://localhost:8080/"

    val beficBackendFactory = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun beficBackendService(): UsuarioBeficBackendService {
        return beficBackendFactory.create(UsuarioBeficBackendService::class.java)
    }
}