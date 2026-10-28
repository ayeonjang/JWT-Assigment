# [영화 리뷰 게시판]

웹서비스설계 JWT 게시판 과제입니다. 이 파일을 제출 저장소의 루트 `README.md`로 복사한 뒤 대괄호 안내를 실제 내용으로 바꾸세요. 선택하지 않은 기술의 설명과 작성 안내는 삭제합니다.

## 작성자와 서비스 소개

- 학번 / 이름: [202323016 / 장아연]
- 서비스 목적과 대상 사용자: [본 서비스는 사용자가 영화 리뷰를 자유롭게 작성하고 공유할 수 있는 웹 기반 게시판 플랫폼입니다. 영화 정보 탐색과 소통을 원하는 모든 이용자를 대상으로 하며, 비로그인 방문자도 게시글 목록과 상세 내용을 자유롭게 조회할 수 있습니다. JWT 기반 인증 방식을 사용하여 로그인한 사용자에게만 작성/수정/삭제 권한을 부여함으로써 데이터의 안전한 관리와 작성자 권한 보호를 목적으로 합니다.]
- 선택한 기술: [Vue] + [Spring Boot]
- 주요 기능: 
[공개 조회: 비로그인 상태에서 전체 게시글 목록 및 상세 내용 조회

인증 및 세션 관리: 이메일/비밀번호 기반 JWT 발급, 메모리 기반 안전한 토큰 보관 및 401 발생 시 자동 로그아웃 처리

내 정보 조회: 검증된 JWT를 통한 본인 공개 프로필 정보(비밀번호 제외) 반환

게시글 작성/수정/삭제: 서버 측 토큰 검증을 통한 작성자 본인 확인 및 권한(403) 제어

예외 처리 및 입력 검증: 인증 실패(401), 권한 부족(403), 존재하지 않는 리소스(404), 입력 값 규칙 위반(400)에 대한 명확한 예외 상태 코드 반환

데이터 지속성: SQLite DB를 통한 서버 재시작 및 새로고침 후에도 게시글 데이터 영구 유지]

## 보고서와 테스트 증거

- [구현 보고서 PDF](docs/report.pdf)

보고서 PDF를 위 경로에 추가하세요. 테스트 증거를 보고서에만 포함한 경우 별도 폴더 링크를 삭제하고 해당 보고서 페이지를 안내하세요. 경로를 바꿨다면 링크도 수정합니다. GitHub에서 링크가 실제로 열리는지 확인하세요.

## 프로젝트 구조

```text
.
├── README.md
├── .gitignore
├── frontend/          # 선택한 프론트 소스와 의존성 파일
├── backend/           # 선택한 백엔드 소스와 의존성 파일
└── docs/
    └── report.pdf
```

[실제 제출 폴더 이름에 맞게 수정]

## 실행 환경

| 항목 | 사용 버전 |
|---|---|
| 운영체제 | [Window 11] |
| 프론트 런타임 Node.js | [v20.x Node.js LTS] |
| 백엔드 런타임 | [OpenJDK 26] |
| 프레임워크 | [Spring Boot 3.5.16 / Vue 3] |
| 데이터 저장 | SQLite 3 |

## 설치와 실행

본인 PC와 교수자 PC에서 로컬로 재현할 수 있도록 실제 명령을 적으세요. 설치·빌드·실행·종료를 구분하고 각 명령을 실행할 폴더를 명시합니다. GitHub는 코드와 문서 제출용이며 외부 배포 URL은 필요하지 않습니다.

### 1 저장소 복제

```sh
git clone [https://github.com/ayeonjang/JWT-Assigment.git]
cd [cd JWT-Assigment]
```

### 2 백엔드 준비와 실행

[선택한 백엔드의 설치·환경설정·실행 명령 작성]

- 환경설정 예시 파일: [backend-spring/src/main/resources/application.properties]
- JWT_SECRET 설정 방법: [application.properties 의 jwt.secret 키에 HS256 알고리즘의 Base64 비밀키를 설정합니다. 실제 환경변수 주입 시 JWT_SECRET 사용]
- TOKEN_TTL: [기본값 3600초 (1시간). 만료 테스트 시 TOKEN_TTL=3으로 설정하여 서버를 실행합니다.]
- 백엔드 주소: [http://127.0.0.1:8000]
- 정상 실행 확인 방법: [서버 실행 후 http://127.0.0.1:8000/api/posts GET 요청 시 200 OK 응답 확인]

### 3 프론트 빌드와 로컬 실행

백엔드를 켜 두고 새 터미널에서 실행합니다. 아래 폴더명과 명령을 실제 제출 내용에 맞게 수정하세요.

```sh
cd frontend
npm ci
npm run build
npm run preview
```

- 최종 확인 주소: http://127.0.0.1:4173
- 개발 중 실행: `npm run dev`, http://127.0.0.1:5173
- API 연결 방식과 포트 변경 방법: [ Vite의 vite.config.js 프록시 설정을 통해 /api 요청을 http://127.0.0.1:8000 백엔드로 전달합니다.]
- 종료: 프론트와 백엔드를 실행한 각 터미널에서 Ctrl+C
- 코드 변경 후 재실행: [백엔드: 터미널에서 Ctrl + C 후 .\mvnw.cmd spring-boot:run 재시작 / 프론트엔드: npm run build 후 npm run preview 재실행]

### 4 테스트 계정과 DB

| 사용자 | 이메일 | 실습용 비밀번호 |
|---|---|---|
| Alice | alice@class.test | Classroom!2026 |
| Bob | bob@class.test | Classroom!2026 |

- SQLite 파일 위치와 자동 생성 방식: [backend-spring/data/board.sqlite (서버 최초 실행 시 자동으로 DB 파일 및 초기 테이블/테스트 계정 생성)]
- DB 초기화 방법: [백엔드 서버를 종료한 후 backend-spring/data/board.sqlite 파일 삭제 후 서버를 재시작합니다.]
- 서버 재시작 후 데이터 유지 확인 결과: [게시글 작성 후 백엔드 서버를 재시작하고 GET /api/posts 재조회 시 작성된 데이터가 정상적으로 유지됨을 확인하였습니다.]

위 계정은 과제 전용 테스트 계정입니다. 실제 서비스 계정 정보나 서버 비밀 키를 기록하지 않습니다.

## 구현 완료 범위

| 기능 | 완료 여부 | 주요 파일 또는 함수 |
|---|---|---|
| 공개 목록과 상세 | [완료] | [PostController.java (getPosts, getPost), App.vue] |
| 로그인과 JWT 발급 | [완료] | [AuthController.java (login), JwtProvider.java (generateToken)] |
| JWT 검증과 내 정보 조회 | [완료] | [JwtAuthenticationFilter.java, UserController.java (getMe)] |
| 글 작성과 작성자 저장 | [완료] | [PostController.java (createPost), PostService.java] |
| 본인 글 수정·삭제 | [완료] | [PostController.java (updatePost, deletePost), PostService.java] |
| 토큰 만료와 로그아웃 | [완료] | [JwtProvider.java (validateToken), api.js (401 발생 시 세션 초기화)] |
| 입력 오류와 권한 오류 처리 | [완료] | [GlobalExceptionHandler.java, PostService.java (400/401/403/404 예외)] |

알려진 문제와 미완성 기능: [없음]

## 테스트 방법과 결과 요약

- 실행한 검사 명령과 실행 위치: [python3 tools/check_api.py --mode final]
- Alice/Bob 권한 구분 확인 결과: [Alice로 작성한 글을 Bob의 토큰으로 수정/삭제 API 직접 요청 시 403 FORBIDDEN 반환 확인]
- HTTP 상태와 오류 code 확인: [200·201·204 / 400·401·403·404의 실제 결과, 보고서 링크]
- 응답 헤더와 본문 확인: [201 CREATED: Location 헤더 포함 / 401 UNAUTHORIZED: WWW-Authenticate: Bearer 헤더 포함 / 204 NO_CONTENT: Response Body 빈 값]
- 토큰 없음·변조·만료 확인 결과: [토큰 미첨부 요청: 401 TOKEN_REQUIRED / 토큰 서명 변조 또는 만료 후 요청: 401 TOKEN_EXPIRED]
- 전체 T01~T17 결과와 증거: [보고서의 해당 절 또는 상대 링크]

직접 수행한 결과만 기록합니다. 자동 검사 도구를 저장소에 포함하지 않았다면 존재하지 않는 실행 경로를 안내하지 말고 재현 가능한 수동 절차를 적으세요.

## 핵심 구현 설명

[로그인 및 토큰 발급: /api/auth/login 요청 시 사용자 검증 후 JwtProvider에서 HS256 알고리즘으로 서명된 JWT를 생성하여 클라이언트에 반환합니다.

토큰 보관 및 전송: 프론트엔드(src/api.js)는 발급받은 토큰을 JS 메모리 세션에 보관하고, 인증이 필요한 API 요청 시 Authorization: Bearer <token> 헤더를 자동으로 첨부합니다.

서버 검증 (Stateless): 백엔드의 JwtAuthenticationFilter가 모든 요청을 가로채 토큰의 위변조 및 만료 여부를 검증하고, 유효한 경우 SecurityContext에 인증 정보를 등록합니다.

작성자 권한 검사: PostService에서 게시글 수정/삭제 요청 시, 토큰에서 추출한 사용자 ID와 DB 게시글의 authorId를 비교하여 불일치 시 403 FORBIDDEN 예외를 발생시킵니다.]

## AI 활용 요약과 참고 자료

- AI 사용 여부: [사용]
- 도구와 사용 목적: [제미나이 / 프론트엔드 API 연동 모듈 및 예외 상태 코드 처리 흐름 검증, 전체적인 코드 문법 오류 해결 사용]
- 반영 위치와 직접 수정·검증한 내용: [rontend-vue/src/api.js의 fetch 래퍼 함수 작성 및 401 Unauthorized 수신 시 자동 세션 초기화 로직 구현 반영, 백엔드 GlobalExceptionHandler 응답 규격 검증]
- 상세 활용 기록: [보고서 해당 절]
- 참고한 수업 자료·외부 코드·공식 문서: []

AI 미사용이면 미사용을 명시하고 참고 자료만 기록하세요. 사용량 자체로 가감점하지 않으며 기록과 검증의 명확성을 평가합니다.

## 제출 확인

- [v] README의 안내 문구를 실제 내용으로 교체했다.
- [v] 새 폴더에 clone하여 README만으로 설치·환경설정·빌드·로컬 실행했다.
- [v] 4173번 빌드 화면에서 API 연결과 전체 필수 기능을 확인했다.
- [v] GitHub에서 보고서와 증거 링크가 열린다.
- [v] 교수자가 저장소를 열람·복제할 수 있다.
- [v] 비밀 키·실제 토큰·DB·node_modules·.venv·target을 커밋하지 않았다.
- [v] 최종 push 후 저장소 URL과 제출 기준 커밋 SHA를 LMS에 제출했다.
