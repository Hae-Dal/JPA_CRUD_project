# 숫자야구게임 만들기
feat.내일배움캠프

---
1. [API 명세서](#api-명세서)
   - [사용자 인증 및 관리](#사용자-인증-및-관리)
     - [회원가입](#회원가입)
     - [로그인](#로그인)
     - [모든 사용자 조회](#모든-사용자-조회)
     - [사용자 삭제](#사용자-삭제)
   - [스케줄 관리](#스케줄-관리)
     - [스케줄 생성](#스케줄-생성)
     - [모든 스케줄 조회](#모든-스케줄-조회)
     - [스케줄 수정](#스케줄-수정)
     - [스케줄 삭제](#스케줄-삭제)
   - [예외 처리](#예외-처리) 
2. [클래스 다이어그램](#클래스-다이어그램)
---

# API 명세서

## 사용자 인증 및 관리

### 회원가입

- **URL**: `/api/users/register`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "userName": "string",
    "email": "string",
    "password": "string",
    "role": "ADMIN or USER"
  }
- **Response**: JWT 토큰 (String)

### 로그인

- **URL**: `/api/users/login`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "email": "string",
    "password": "string"
  }
- Response: JWT 토큰 (String)

### 모든 사용자 조회

- **URL**: `/api/users`
- **Method**: `GET`
- **Headers**: `Authorization: Bearer {token}`
  ```json
  [
    {
      "id": "number",
      "userName": "string",
      "email": "string",
      "createdDate": "datetime",
      "updatedDate": "datetime",
      "role": "ADMIN or USER"
    }
  ]
- **Response**: JWT 토큰 (String)

### 사용자 삭제

- **URL**: `/api/users/{id}`
- **Method**: `GET`
- **Headers**: `Authorization: Bearer {token}`
- **Response**: `204 No Content`

## 스케줄 관리

### 스케줄 생성

- **URL**: /api/schedules
- **Method**: POST
- **Headers**: Authorization: Bearer {token}
- **Request Body**:
  ```json
  [
    {
      "id": "number",
      "userName": "string",
      "email": "string",
      "createdDate": "datetime",
      "updatedDate": "datetime",
      "role": "ADMIN or USER"
    }
  ]
- **Response**:
  ```json
  [
    {
      "id": "number",
      "title": "string",
      "content": "string",
      "weather": "string",
      "authorName": "string",
      "createdDate": "datetime",
      "updatedDate": "datetime"
    }
  ]

### 모든 스케줄 조회

- **URL**: /api/schedules
- **Method**: GET
- **Headers**: Authorization: Bearer {token}
- **Response**:
  ```json
  [
    {
      "id": "number",
      "title": "string",
      "content": "string",
      "weather": "string",
      "authorName": "string",
      "createdDate": "datetime",
      "updatedDate": "datetime"
    }
  ]

### 스케줄 수정

- **URL**: /api/schedules/{id}
- **Method**: PUT
- **Headers**: Authorization: Bearer {token}
- **Request Body**:
  ```json
  {
    "title": "string",
    "content": "string"
  }
- **Response Body**:
  ```json
  {
    "id": "number",
    "title": "string",
    "content": "string",
    "weather": "string",
    "authorName": "string",
    "createdDate": "datetime",
    "updatedDate": "datetime"
  }

### 스케줄 삭제

- **URL**: /api/schedules/{id}
- **Method**: DELETE
- **Headers**: Authorization: Bearer {token}
- **Response**: 204 No Content

## 예외 처리
- **401 Unauthorized**: 유효하지 않은 토큰 또는 로그인 실패 시
- **403 Forbidden**: 권한 없는 사용자가 일정 수정 또는 삭제 시도 시
- **400 Bad Request**: 요청에 필수 정보가 누락된 경우

# 클래스 다이어그램

1. **User**
    - **Attributes:**
        - `Long id`
        - `String userName`
        - `String email`
        - `String password`
        - `Role role`
        - `LocalDateTime createdDate`
        - `LocalDateTime updatedDate`
    - **Relationships:**
        - `Set<Schedule> schedules` (1:N 관계, 작성한 스케줄)
        - `Set<Schedule> assignedSchedules` (N:M 관계, 할당된 스케줄)

2. **Role**
    - **Enum Values:**
        - `ADMIN`
        - `USER`

3. **Schedule**
    - **Attributes:**
        - `Long id`
        - `String title`
        - `String content`
        - `String weather`
        - `LocalDateTime createdDate`
        - `LocalDateTime updatedDate`
    - **Relationships:**
        - `User author` (N:1 관계, 작성자)
        - `Set<Comment> comments` (1:N 관계, 스케줄에 달린 댓글)

4. **Comment**
    - **Attributes:**
        - `Long id`
        - `String content`
        - `String userName`
        - `LocalDateTime createdDate`
        - `LocalDateTime updatedDate`
    - **Relationships:**
        - `Schedule schedule` (N:1 관계, 속한 스케줄)

5. **WeatherService**
    - **Methods:**
        - `String getTodayWeather()`

6. **UserService**
    - **Methods:**
        - `String registerUser(UserRequestDto userRequestDto)`
        - `UserResponseDto createUser(UserRequestDto userRequestDto)`
        - `List<UserResponseDto> getAllUsers()`
        - `void deleteUser(Long id)`

7. **ScheduleService**
    - **Methods:**
        - `ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto)`
        - `List<ScheduleResponseDto> getAllSchedules()`
        - `Page<ScheduleResponseDto> getSchedulesPage(int page, int size)`
        - `ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto)`
        - `void deleteSchedule(Long id)`

8. **CommentService**
    - **Methods:**
        - `CommentResponseDto createComment(Long scheduleId, CommentRequestDto commentRequestDto)`
        - `List<CommentResponseDto> getCommentsByScheduleId(Long scheduleId)`
        - `CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto)`
        - `void deleteComment(Long id)`

9. **JwtUtil**
    - **Methods:**
        - `String generateToken(String username, String role)`
        - `String extractUsername(String token)`
        - `String extractUserRole(String token)`
        - `boolean isTokenExpired(String token)`
        - `boolean validateToken(String token, String username)`

10. **UserController**
    - **Methods:**
        - `ResponseEntity<String> registerUser(UserRequestDto userRequestDto)`
        - `ResponseEntity<String> login(LoginRequestDto loginRequestDto)`
        - `ResponseEntity<List<UserResponseDto>> getAllUsers()`
        - `ResponseEntity<Void> deleteUser(Long id)`

11. **ScheduleController**
    - **Methods:**
        - `ResponseEntity<ScheduleResponseDto> createSchedule(ScheduleRequestDto scheduleRequestDto)`
        - `ResponseEntity<List<ScheduleResponseDto>> getAllSchedules()`
        - `ResponseEntity<ScheduleResponseDto> updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto)`
        - `ResponseEntity<Void> deleteSchedule(Long id)`

12. **CommentController**
    - **Methods:**
        - `ResponseEntity<CommentResponseDto> createComment(Long scheduleId, CommentRequestDto commentRequestDto)`
        - `ResponseEntity<List<CommentResponseDto>> getCommentsByScheduleId(Long scheduleId)`
        - `ResponseEntity<CommentResponseDto> updateComment(Long id, CommentRequestDto commentRequestDto)`
        - `ResponseEntity<Void> deleteComment(Long id)`

13. **JwtAuthenticationFilter**
    - **Methods:**
        - `void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)`

14. **Repositories**
    - **UserRepository**
        - `Methods: findByEmail(String email)`
    - **ScheduleRepository**
        - `Methods: JpaRepository methods`
    - **CommentRepository**
        - `Methods: JpaRepository methods`

15. **DTO**
    - **UserRequestDto**
        - **Attributes:** `String userName`, `String email`, `String password`, `Role role`
    - **UserResponseDto**
        - **Attributes:** `Long id`, `String userName`, `String email`, `LocalDateTime createdDate`, `LocalDateTime updatedDate`, `Role role`
    - **ScheduleRequestDto**
        - **Attributes:** `String title`, `String content`, `Long userId`
    - **ScheduleResponseDto**
        - **Attributes:** `Long id`, `String title`, `String content`, `String weather`, `LocalDateTime createdDate`, `LocalDateTime updatedDate`, `String authorName`
    - **CommentRequestDto**
        - **Attributes:** `String content`, `String userName`
    - **CommentResponseDto**
        - **Attributes:** `Long id`, `String content`, `String userName`, `LocalDateTime createdDate`, `LocalDateTime updatedDate`, `Long scheduleId`
    - **LoginRequestDto**
        - **Attributes:** `String email`, `String password`