package team.keepBurning;

public class Comidas {

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Comidas(String userId, String name, String age, String email, String pass, String gender, String phone){
        this.userId=userId;
        this.name= name;
        this.age= age;
        this.email = email;
        this.pass= pass;
        this.gender= gender;
        this.phone= phone;




    }

    public Comidas(String userId, String name, String email, String pass, String age){
        this.userId=userId;

        this.email = email;
        this.pass= pass;
        this.pass= age;

    }
    public Comidas(String userId, String name, String email, String pass){
        this.userId=userId;

        this.email = email;
        this.pass= pass;

    }

    String userId;
    String name;
    String age;
    String email;
    String pass;
    String gender;
    String phone;

}
