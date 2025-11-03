import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Use 'object' for a thread-safe singleton instance
object RetrofitClient {

    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    // Define the OkHttpClient as a lazy property so it's only created once when first accessed
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(::apiKeyAsQuery) // Add the interceptor here
            .build()
    }

    // Define the Retrofit instance, also as a lazy property
    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Use the configured OkHttpClient
            .addConverterFactory(GsonConverterFactory.create()) // Example converter
            .build()
    }

    /**
     * Interceptor to add an API key as a query parameter to every request.
     */
    private fun apiKeyAsQuery(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newUrl = originalRequest.url.newBuilder()
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}
