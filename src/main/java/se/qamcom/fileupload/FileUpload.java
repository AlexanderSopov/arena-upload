package se.qamcom.fileupload;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;

public class FileUpload{

    private static String arenaSessionId;
    private static final String email = "alexander.sopov@qamcom.se";
    private static final String password = "IAmARobot2301";
	private static final String BASE_URL = "https://api.arenasolutions.com/v1";

	public static void main(String[] args) throws Exception{
		login();
		uploadFile();
		logout();
	}



	private static void login() {
        HttpsURLConnection con = null;
        
        try {
            URL url = new URL(BASE_URL + "/login");
            con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            String jsonMessage = String.format("{\"email\":\"%s\", \"password\":\"%s\" }", email, password);
            
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(jsonMessage);
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            
            JSONObject jsonResponse = new JSONObject(response.toString());
            arenaSessionId = jsonResponse.getString("arenaSessionId");
            System.out.println("Login: " + arenaSessionId);

        }
        catch (Exception e) {
            System.out.println(e.toString());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    private static int logout() {
        HttpsURLConnection con = null;
        
        try {
            URL url = new URL(BASE_URL + "/logout");
            con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("arena_session_id", arenaSessionId);
            
            return con.getResponseCode();
        }
        catch (Exception e) {
            return 0;
        }
        finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    private static void uploadFile(){

        String charset = "UTF-8";
        File uploadFile = new File("C:\\Users\\alexander.sopov\\Documents\\qamcom\\confluence-scripts\\Arena-api-docs\\arena_api_developer_guide.pdf");

 
        try {
            MultipartUtility multipart = new MultipartUtility(BASE_URL + "/items/5N7QVL9NRYHYHWOPNZHJ/files", charset, arenaSessionId);
             
            // multipart.addHeaderField("User-Agent", "CodeJava");

	        multipart.addFormField("file.location", "H:\\TeddyBear\\Qamcom\\arenaUpload\\file\\hej.pdf");
	        multipart.addFormField("file.category.guid", "9RBUZPDRV2LVEX9GR7DT");
	        multipart.addFormField("file.storageMethod", "9RBUZPDRV2LVEX9GR7DT");
	        multipart.addFormField("file.title", "Hej Pdf");
	        multipart.addFormField("file.edition", "1");
	        multipart.addFormField("file.author.fullName", "Alexander Sopov");
	        multipart.addFormField("file.format", "pdf");
	        multipart.addFilePart("fileContent", uploadFile);
            List<String> response = multipart.finish();
             
            System.out.println("SERVER REPLIED:");
             
            for (String line : response) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

    }

}
