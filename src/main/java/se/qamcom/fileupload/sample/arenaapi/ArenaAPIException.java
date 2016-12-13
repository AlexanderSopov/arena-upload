package se.qamcom.fileupload.sample.arenaapi;

import se.qamcom.fileupload.sample.json.*;

public class ArenaAPIException extends Exception {

    public int statusCode;
    public JSONObject errors;

    public ArenaAPIException(int statusCode, JSONObject errors) {
        this.statusCode = statusCode;
        this.errors = errors;
    }
}
