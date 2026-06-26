

# Architecture
## ERD
[snapshot.json](./erd-snapshot.json)
![img_1.png](img_1.png)

## API Specs

### GET /schedules/:id

#### Parameters

* id : 조회할 일정 ID

#### Response

```json
{
  "createdAt": "2026-06-26T12:00:00",
  "updatedAt": "2026-06-26T12:00:00",
  "title": "일정 제목",
  "content": "일정 내용",
  "createdBy": "작성자",
  "comments": [
    {
      "createdAt": "2026-06-26T12:00:00",
      "updatedAt": "2026-06-26T12:00:00",
      "content": "댓글 내용",
      "createdBy": "댓글 작성자"
    }
  ]
}
```

---

### PUT /schedules/:id

#### Parameters

* id : 수정할 일정 ID

```jsonc
{
  "title": "string", // 일정 제목
  "content": "string", // 일정 내용
  "createdBy": "string", // 작성자
  "password": "string" // 비밀번호
}
```

#### Response

```json
{
  "createdAt": "2026-06-26T12:00:00",
  "updatedAt": "2026-06-26T12:10:00",
  "title": "수정된 일정 제목",
  "content": "수정된 일정 내용",
  "createdBy": "작성자",
  "comments": [
    {
      "createdAt": "2026-06-26T12:00:00",
      "updatedAt": "2026-06-26T12:00:00",
      "content": "댓글 내용",
      "createdBy": "댓글 작성자"
    }
  ]
}
```

---

### DELETE /schedules/:id

#### Parameters

* id : 삭제할 일정 ID

```jsonc
{
  "password": "string" // 비밀번호
}
```

#### Response

```json
{}
```

---

### GET /schedules

#### Parameters

* createdBy : 작성자 이름으로 일정 목록 필터링, 선택값

#### Response

```json
[
  {
    "createdAt": "2026-06-26T12:00:00",
    "updatedAt": "2026-06-26T12:00:00",
    "title": "일정 제목",
    "content": "일정 내용",
    "createdBy": "작성자"
  }
]
```

---

### POST /schedules

#### Parameters

```jsonc
{
  "title": "string", // 일정 제목
  "content": "string", // 일정 내용
  "createdBy": "string", // 작성자
  "password": "string" // 비밀번호
}
```

#### Response

```json
{
  "createdAt": "2026-06-26T12:00:00",
  "updatedAt": "2026-06-26T12:00:00",
  "title": "일정 제목",
  "content": "일정 내용",
  "createdBy": "작성자",
  "comments": []
}
```

---

### POST /schedules/:scheduleId/comments

#### Parameters

* scheduleId : 댓글을 작성할 일정 ID

```jsonc
{
  "content": "string", // 댓글 내용
  "createdBy": "string", // 댓글 작성자
  "password": "string" // 댓글 비밀번호
}
```

#### Response

```json
{
  "createdAt": "2026-06-26T12:00:00",
  "updatedAt": "2026-06-26T12:00:00",
  "content": "댓글 내용",
  "createdBy": "댓글 작성자"
}
```
