package woowacourse.auth.domain;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class Password {

    private static final String REGEX = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^*])[a-zA-Z0-9!@#$%^*]{8,20}$";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    private final String value;

    public Password(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        Matcher matcher = PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("비밀번호는 알파벳, 숫자, 특수문자로 구성되어야 합니다. (8~20글자)");
        }
    }

    public EncryptedPassword toEncrypted() {
        String randomlyHashedPassword = BCrypt.hashpw(value, BCrypt.gensalt());
        return new EncryptedPassword(randomlyHashedPassword);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Password password = (Password) o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
