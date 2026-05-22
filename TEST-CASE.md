# Book REST API Test Cases
Base URL:
```text
http://localhost:8080/api/books
```

## GET /api/books

### Test: Get All Books

#### Request

```http
GET /api/books
```

#### Expected Result

- Status: `200 OK`
- Returns all books

### Test: Filter By Category

#### Request

```http
GET /api/books?category=Music
```

#### Expected Result

- Status: `200 OK`
- Returns only Music books

### Test: Filter By Author

#### Request

```http
GET /api/books?author=somchai
```

#### Expected Result

- Status: `200 OK`
- Case-insensitive filtering works

### Test: Filter By Title

#### Request

```http
GET /api/books?title=guitar
```

#### Expected Result

- Status: `200 OK`
- Returns books containing "guitar"

### Test: Combined Filters

#### Request

```http
GET /api/books?category=Music&author=nattapong
```

#### Expected Result

- Status: `200 OK`
- Returns matching books only

### Test: SQL Injection Attempt

#### Request

```http
GET /api/books?author=' OR 1=1 --
```

#### Expected Result

- Status: `200 OK`
- API does not crash
- No unexpected behavior

## GET /api/books/{id}

### Test: Existing ID

#### Request

```http
GET /api/books/1
```

#### Expected Result

- Status: `200 OK`
- Returns book with ID 1

### Test: Non-existing ID

#### Request

```http
GET /api/books/999
```

#### Expected Result

- Status: `404 NOT FOUND`

#### Example Response

```json
{
  "status": 404,
  "message": "Book not found - 999",
  "timestamp": 1710000000000
}
```

### Test: Invalid ID

#### Request

```http
GET /api/books/0
```

#### Expected Result

- Status: `400 BAD REQUEST`

## POST /api/books

### Test: Valid Request

#### Request

```http
POST /api/books
Content-Type: application/json
```

#### Request Body

```json
{
  "title": "Spring Security Basics",
  "author": "Yuma",
  "category": "Programming",
  "rating": 5
}
```

#### Expected Result

- Status: `201 CREATED`
- Book successfully created

### Test: Empty Title

#### Request Body

```json
{
  "title": "",
  "author": "Yuma",
  "category": "Programming",
  "rating": 5
}
```

#### Expected Result

- Status: `400 BAD REQUEST`

### Test: Blank Title

#### Request Body

```json
{
  "title": "     ",
  "author": "Yuma",
  "category": "Programming",
  "rating": 5
}
```

#### Expected Result

- Status: `400 BAD REQUEST`

### Test: Null Title

#### Request Body

```json
{
  "title": null,
  "author": "Yuma",
  "category": "Programming",
  "rating": 5
}
```

#### Expected Result

- Status: `400 BAD REQUEST`

### Test: Missing Title Field

#### Request Body

```json
{
  "author": "Yuma",
  "category": "Programming",
  "rating": 5
}
```

#### Expected Result

- Status: `400 BAD REQUEST`

### Test: Rating Too High

#### Request Body

```json
{
  "title": "Java",
  "author": "Yuma",
  "category": "Programming",
  "rating": 10
}
```

#### Expected Result

- Status: `400 BAD REQUEST`

### Test: Rating Too Low

#### Request Body

```json
{
  "title": "Java",
  "author": "Yuma",
  "category": "Programming",
  "rating": 0
}
```

#### Expected Result

- Status: `400 BAD REQUEST`

### Test: Wrong JSON Type

#### Request Body

```json
{
  "title": "Java",
  "author": "Yuma",
  "category": "Programming",
  "rating": "five"
}
```

#### Expected Result

- Status: `400 BAD REQUEST`

### Test: Malformed JSON

#### Request Body

```json
{
  "title": "Java"
}
```

#### Expected Result

- Status: `400 BAD REQUEST`

### Test: Wrong Content Type (Postman/cURL)

#### Request

```http
POST /api/books
Content-Type: text/plain
```

#### Expected Result

- Status: `415 UNSUPPORTED MEDIA TYPE`

## PUT /api/books/{id}

### Test: Valid Update

#### Request

```http
PUT /api/books/1
Content-Type: application/json
```

#### Request Body

```json
{
  "title": "Updated Book",
  "author": "Updated Author",
  "category": "Updated",
  "rating": 4
}
```

#### Expected Result

- Status: `200 OK`
- Book successfully updated

### Test: Update Non-existing Book

#### Request

```http
PUT /api/books/999
Content-Type: application/json
```

#### Expected Result

- Status: `404 NOT FOUND`

### Test: Invalid Rating

#### Request Body

```json
{
  "title": "Java",
  "author": "Yuma",
  "category": "Programming",
  "rating": -1
}
```

#### Expected Result

- Status: `400 BAD REQUEST`

## DELETE /api/books/{id}

### Test: Delete Existing Book

#### Request

```http
DELETE /api/books/1
```

#### Expected Result

- Status: `204 NO CONTENT`

### Test: Delete Non-existing Book

#### Request

```http
DELETE /api/books/999
```

#### Expected Result

- Status: `404 NOT FOUND`

### Test: Invalid Delete ID

#### Request

```http
DELETE /api/books/0
```

#### Expected Result

- Status: `400 BAD REQUEST`

## Swagger UI Testing

### Swagger UI URL

```text
http://localhost:8080/swagger-ui/index.html
```

### Manual Testing Steps

1. Open Swagger UI
2. Select endpoint
3. Click "Try it out"
4. Enter parameters or request body
5. Click "Execute"
6. Verify:
   - Status code
   - Response body
   - Validation errors