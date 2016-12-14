package se.qamcom.fileupload.api;

import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by alexander.sopov on 2016-12-14.
 */
public interface EndPoints {

    @POST("login")
    Call<LoginResponse> login(@Body LoginCredentials login);

    @Headers({"Content-Type: application/json"})
    @GET("logout")
    Call<ResponseBody> logout(@Header("arena_session_id") String session_id);

    @Multipart
    @POST("items/{guid}/files")
    Call<ResponseBody> addFile(@Path("guid") String guid, @Header("arena_session_id") String session,
                               @Part("file.location") String location,
                               @Part("file.category.guid") String categoryGuid,
                               @Part("file.storageMethod") String storage,
                               @Part("file.title") String title,
                               @Part("file.edition") String edition,
                               @Part("file.author.fullName") String fullName,
                               @Part("file.format") String format,
                               @Part("fileContent")RequestBody file);


}
