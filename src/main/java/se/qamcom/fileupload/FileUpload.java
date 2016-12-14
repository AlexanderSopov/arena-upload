package se.qamcom.fileupload;

import com.sun.org.apache.xpath.internal.SourceTree;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.qamcom.fileupload.api.EndPoints;
import se.qamcom.fileupload.api.LoginCredentials;
import se.qamcom.fileupload.api.LoginResponse;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileUpload{

    private EndPoints api;
    private Retrofit retrofit;
    private final String email = "alexander.sopov@qamcom.se";
    private final String password = "IAmARobot2301";
	private final String BASE_URL = "https://api.arenasolutions.com/v1/";
    private LoginResponse loginSession;



	public static void main(String[] args){

        FileUpload upload = new FileUpload();
        upload.retrofit = new Retrofit.Builder()
                .baseUrl(upload.BASE_URL)
                .addConverterFactory((GsonConverterFactory.create()))
                .build();

        upload.api = upload.retrofit.create(EndPoints.class);

        try {
            upload.login();
            upload.uploadFile("5N7QVL9NRYHYHWOPNZHJ",
                    "C:\\Users\\alexander.sopov\\Documents\\qamcom\\confluence-scripts\\PDF-export\\workingDir\\test.pdf",
                    "9RBUZPDRV2LVEX9GR7DT",
                    "0",
                    "Hej",
                    "1",
                    "Alexander Sopov",
                    "application/pdf");
            upload.logout();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



	private void login() throws IOException {
        Call<LoginResponse> call = api.login(new LoginCredentials(email, password));
        Response<LoginResponse> res = call.execute();
        loginSession = res.body();
        System.out.println(loginSession.arenaSessionId);
    }

    private void logout() throws IOException {
        Call<ResponseBody> call = api.logout(loginSession.arenaSessionId);
        Response<ResponseBody> res = call.execute();
        ResponseBody response = res.body();
        printStatus("Logout", res.code());
    }

    private void uploadFile(String guid, String location, String categoryGuid, String storageMethod, String title,
                            String edition, String fullname, String format) throws IOException {
        MediaType MEDIA_TYPE_PDF = MediaType.parse(format);
        File file = new File(location);
        RequestBody req = RequestBody.create(MEDIA_TYPE_PDF, file);
        Call<ResponseBody> call = api.addFile(guid,
                loginSession.arenaSessionId,
                location, categoryGuid,
                storageMethod,
                title,
                edition,
                fullname,
                format,
                req);

        System.out.println(call.request().toString());
        System.out.println(call.request().body().toString());



        Response<ResponseBody> res = call.execute();
        printStatus("add file", res.code());
    }

    private void printStatus(String method, int code) {
        System.out.println(method + " generated status code: " + code);
    }

}
