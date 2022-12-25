/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CA1;

/**
 *
 * @author leong
 */
public class Users {
    private String memberID;
    private String name;
    private String permissionLevel;
    
    public Users(String newMemberID, String newName,String newPermissionLevel){
        this.memberID = newMemberID;
        this.name = newName;
        this.permissionLevel = newPermissionLevel;
    }
    
    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(String permissionLevel) {
        this.permissionLevel = permissionLevel;
    }
    
    
}
