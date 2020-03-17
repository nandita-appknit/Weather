package com.example.weather

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainPresenter {
    lateinit var mainv:MainView
    lateinit var retrofit:Retrofit
    lateinit var weather:weatherApi
    lateinit var weatherResponse:WeatherResponse
    lateinit var stringBuilder:String
    val BaseUrl:String="http://api.openweathermap.org/"
    val API:String="04d51d94b6603269028898f672008f50"
     fun display() {
        retrofit= Retrofit.Builder()
                   .baseUrl(BaseUrl)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build()
         weather = retrofit.create(weatherApi::class.java)
         var call :Call<WeatherResponse>
         call= weather.getCurrentWeatherData(mainv.GetCityName(),API)
         call.enqueue( object:Callback<WeatherResponse>{
             override fun onResponse(call: Call<WeatherResponse>?, response: Response<WeatherResponse>?) {
                 if (response?.code()==200){
                     weatherResponse=response.body()
                     assert(weatherResponse!=null)

                     stringBuilder= "Country: "+
                             weatherResponse.sys.country+
                             "\n "+
                             "Temperature: "+
                             weatherResponse.main.temp+
                             "\n "+
                             "Temperature(Min): "+
                             weatherResponse.main.temp_min+
                             "\n "+
                             "Humidity: "+
                             weatherResponse.main.humidity+
                             "\n "+
                             "Pressure: "+
                             weatherResponse.main.pressure

                     mainv.UpdateText(stringBuilder)

                 }
                 else
                 {
                     mainv.UpdateText(response?.message())
                 }
             }

             override fun onFailure(call: Call<WeatherResponse>?, t: Throwable?) {
                 mainv.UpdateText(t?.message)
             }


         })


     }
}