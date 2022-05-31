package woowacourse.shoppingcart.dto.request;

public class UpdateMeRequest {

    private String username;
    private String nickname;
    private Integer age ;

    public UpdateMeRequest() {
    }

    public UpdateMeRequest(String username, String nickname, int age) {
        this.username = username;
        this.nickname = nickname;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public int getAge() {
        return age;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setAge(int age) {
        this.age = age;
    }
}