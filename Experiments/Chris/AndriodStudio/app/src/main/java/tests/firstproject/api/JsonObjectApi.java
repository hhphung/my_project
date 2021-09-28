package tests.firstproject.api;

import retrofit2.Call;
import retrofit2.http.GET;
import tests.firstproject.model.JsonObject;

public interface JsonObjectApi {
    @GET("/JsonObject")
    Call<JsonObject> getJsonObject();
}
