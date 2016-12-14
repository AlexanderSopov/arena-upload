package se.qamcom.fileupload.api;

/**
 * Created by alexander.sopov on 2016-12-14.
 */
public class LoginResponse {
    public String arenaSessionId;
    public int workspaceId;
    public String workspaceName;
    public int workspaceRequestLimit;

    public LoginResponse(
            String arenaSessionId,
            int workspaceId,
            String workspaceName,
            int workspaceRequestLimit){
        this.arenaSessionId=arenaSessionId;
        this.workspaceId=workspaceId;
        this.workspaceName=workspaceName;
        this.workspaceRequestLimit=workspaceRequestLimit;
    }
}