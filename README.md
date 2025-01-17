# 장바구니 미션 (협업)

## API 명세

애플리케이션 실행 후 `http://localhost:8080/swagger-ui/#/` 페이지에서 API 확인 및 상호작용 가능.

### 로그인한 상태의 요청

- Authorization 헤더에 Bearer 토큰을 포함하여 요청
- 토큰에 문제가 있는 경우 인증 단계에서의 문제이므로 401

### 예외 응답

- 모든 예외는 JSON 형식의 바디를 반환. message 필드에 한 줄의 예외메시지 포함.
- request body, request parameter 등에 포함되어야 하는 정보가 누락된 경우 400.
    - 누락된 입력값에 대한 정보들은 예외메시지에서 개별적으로 명시.

### 사용자(user)와 고객(customer)

- admin 계정의 도입 가능성으로 인해 고객은 사용자의 한 종류로 간주

#### 유효성 검증

고객/사용자의 정보가 잘못 입력된 경우 400

- 아이디(username)
    - 영문자와 숫자로 구성. 4~20글자.
        - REGEX : `^[a-zA-Z0-9]{4,20}$`
- 비밀번호(password)
    - 영문자, 숫자, 특수문자로 구성. 8~20글자. 영문자, 숫자, 특수문자 모두 적어도 하나씩 필요.
        - REGEX : `^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^*])[a-zA-Z0-9!@#$%^*]{8,20}$`
- 닉네임(nickname)
    - 한글, 영문자, 숫자로 구성. 1~10글자.
        - REGEX : `^[가-힣a-zA-Z0-9]{1,10}$`
- 나이(age)
    - 0 이상 200 이하의 정수
