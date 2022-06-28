# Introduce Expert

😀 처음에는 요리를 하다가 실시간으로 전문가에게 모르는 것을 질문할 수 있는 서비스가 있으면 좋겠다고 생각헀습니다. 그러다가 네이버에 Expert 서비스가 있다는것을 알고 이 서비스를 참고하여 기초기능 + 추가기능을 구현하기로 결정했습니다.

## Backend

일반 유저는 `ROLE_USER` 등급이며 `/api`에 접근 가능합니다.

Expert 유저는 `ROLE_EXPERT` 등급이며 `/expertOnly`, `/api`에 접근 가능합니다.

인증은 Jwt토큰 기반으로 하며, 개발시에 api 테스트를 편하게 하기위해 swagger를 이용했습니다.

### 사용 기술

- Spring Boot
- Spring Security, Jwt
- H2, MySQL
- Redis
- Swagger
- AWS, Jenkins (예정)

## API 목록

로그인 및 회원가입 API (로그아웃한 유저)

1. `POST /api/kakaoLogin/{provider}`
2. `POST /api/kakaoRegister/{provider}`
3. `POST /api/login`
4. `POST /api/register`

로그인된 유저만 쓸 수 있는 API

5. `GET /api/users`
6. `GET /api/user`
7. `PUT /api/user`
8. `DELETE /api/user/{accountId}`

엑스퍼트 클래스

9. `GET /api/expertClass/all`
10. `GET /api/expertClass/review/{reviewId}`
11. `GET /api/expertClass/reviews/{expertClassTitle}`
12. `GET /api/expertClass/{expertClassTitle}`
13. `POST /api/expertClass/{expertClassTitle}`
14. `PUT /api/expertClass/review/{reviewId}`
15. `DELETE /api/expertClass/review/{reviewId}`

엑스퍼트만 들어갈 수 있는 페이지

16. `POST /expertOnly/generate`
17. `POST /expertOnly/register`

예외처리

18. `GET /exception/access-denied`
19. `GET /exception/entrypoint`

TODO: API 이름 정리&물갈이

---

## Frontend

HOC를 이용해 라우팅 처리를 하고, redux를 이용해 각 컴포넌트마다 자주쓰는 값들을 store에 저장해놓습니다.

### 사용 기술

- React
- Redux
