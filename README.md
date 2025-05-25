# 🔐 Secure Mapping API

A Spring Boot-based RESTful API to securely **encode** and **decode** data using custom encryption (AES with UUID/Base64-based transformation). Useful for secure transmission of sensitive payloads.

---

## 🚀 Features

* Custom AES encryption/decryption with a centralized utility (`SecureUtils`)
* RESTful API for encoding and decoding structured payloads
* Uses Jackson custom deserializers for automatic field-level decryption/encryption
* Extensible service-oriented architecture

---

## 🏗️ Project Structure

```
com.sidhant
├── common
│   └── SecureUtils.java            # Encryption/Decryption utility
│   └── serializer/
│       ├── DecoderDeserializer.java
│       └── EncoderDeserializer.java
├── controller/
│   └── SecureMappingController.java
├── dto/
│   ├── request/
│   │   ├── EncodedRequest.java
│   │   └── DecodedRequest.java
│   └── response/
│       ├── EncodedResponse.java
│       └── DecodedResponse.java
├── service/
│   ├── SecureMappingService.java
│   └── impl/SecureMappingServiceImpl.java
```

---

## 📦 Dependencies

Ensure your project has the following dependencies in `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
```

---

## ⚙️ Configuration

Set the encryption key in `application.properties`:

```properties
secure.key=YourSuperSecretKey123
```

---

## 📡 API Endpoints

### 🔒 `POST /api/secure/encode`

**Purpose:** Encrypt a payload and return the encoded version.

#### Request Body (`EncodedRequest`)

```json
{
  "data": "Sidhant Gupta"
}
```

#### Response (`EncodedResponse`)

```json
{
   "data": "2d0f2278-499c-f181-bdae-99d74ef573e4"
}
```

---

### 🔓 `POST /api/secure/decode`

**Purpose:** Decrypt an encoded payload and return the original values.

#### Request Body (`DecodedRequest`)

```json
{
   "data": "2d0f2278-499c-f181-bdae-99d74ef573e4"
}
```

#### Response (`DecodedResponse`)

```json
{
   "data": "Sidhant Gupta"
}
```

---

## 🔧 Internal Logic

### `SecureUtils`

A utility class handling:

* AES encryption (`encode`)
* AES decryption (`decode`)
* Key generation from a configured string

### `EncoderDeserializer` and `DecoderDeserializer`

Custom Jackson deserializers that:

* Automatically encode/decode fields when mapped via `ObjectMapper` or annotations
* Log and handle errors gracefully

### `SecureMappingServiceImpl`

Service layer converting DTOs and invoking logic for serialization.

---

## 🧪 Example Usage (Controller Flow)

1. `POST /api/secure/encode`

    * Accepts an `EncodedRequest` DTO.
    * Service layer uses Jackson to transform the request into an encrypted version.
    * Returns `EncodedResponse`.

2. `POST /api/secure/decode`

    * Accepts a `DecodedRequest` with encrypted UUIDs.
    * Decrypts using `SecureUtils` and returns original values.

---

## 🛡️ Security Notes

* **ECB Mode is insecure**; use `AES/CBC` or `AES/GCM` in production.
* UUID format encryption has size and randomness limitations. Base64 is safer.
* Always secure the API using SSL/TLS and authorization.

---

## 💻 Run the Project

### ✅ Prerequisites

| Tool  | Version   |
| ----- | --------- |
| Java  | 1.8+       |
| Maven | 3.6+      |
| Git   | Installed |

### 🖥️ Run with Terminal

Navigate to the project root and run:

```bash
mvn spring-boot:run
```

Or build and run manually:

```bash
mvn clean install
java -jar target/your-app-name.jar
```

Replace `your-app-name.jar` with the actual file name in the `target/` folder.

### ▶️ Run in IDE (IntelliJ, Eclipse, VS Code)

1. Open your project
2. Run the main class:

   ```
   com.sidhant.YourApplication
   ```

---

## ⬇️ Download from GitHub

To clone this project:

```bash
git clone https://github.com/your-username/your-repo-name.git
cd your-repo-name
```

Replace the URL with your actual GitHub repository.

---

## 🧪 Test With curl

```bash
curl --location 'http://localhost:8080/secure/api/secure/encode' \
--header 'Content-Type: application/json' \
--data '{"data":"Sidhant"}'

curl --location 'http://localhost:8080/secure/api/secure/decode' \
--header 'Content-Type: application/json' \
--data '
{"data":"2d0f2278-499c-f181-bdae-99d74ef573e4"}'
```

---

## 🧠 Future Improvements

* Use AES-CBC/GCM instead of ECB for better security
* Add validation & integrity checks (e.g., HMAC)
* Integrate JWT/authorization
* Add Swagger for documentation

---

## 🧑‍💻 Author

**Sidhant**
*Secure data processing made simple.*
