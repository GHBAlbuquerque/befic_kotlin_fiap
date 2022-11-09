import com.fiap.befic.service.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BeficBackendFactory {

    val URL: String = "http://192.168.15.135:8080/"

    val beficBackendFactory = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun usuarioBeficBackendService(): UsuarioBeficBackendService {
        return beficBackendFactory.create(UsuarioBeficBackendService::class.java)
    }

    fun historiaBeficBackendService(): HistoriaBeficBackendService {
        return beficBackendFactory.create(HistoriaBeficBackendService::class.java)
    }

    fun capituloBeficBackendService(): CapituloBeficBackendService {
        return beficBackendFactory.create(CapituloBeficBackendService::class.java)
    }

    fun loginBeficBackendService(): LoginBeficBackendService {
        return beficBackendFactory.create(LoginBeficBackendService::class.java)
    }

    fun seguidoresBeficBackendService(): SeguidoresBeficBackendService {
        return beficBackendFactory.create(SeguidoresBeficBackendService::class.java)
    }

    fun seguindoBeficBackendService(): SeguindoBeficBackendService {
        return beficBackendFactory.create(SeguindoBeficBackendService::class.java)
    }

}