# Google Contacts/People API Integration

Spring Boot application that integrates with Google Contacts (People) API to perform CRUD operations on contacts using a Thymeleaf-based user interface.

## Overview

This application allows users to:
- Authenticate with their Google account
- View their Google contacts list
- Add new contacts
- Edit existing contacts
- Delete contacts

## Showcase

https://github.com/user-attachments/assets/50523e53-2b48-45cb-bc60-cf034231b4ef

## Prerequisites

- Java 17 or higher
- Maven
- Google Cloud account
- Google Cloud project with People API enabled

## Google API Setup

1. Create a Google Cloud Project:
   - Go to [Google Cloud Console](https://console.cloud.google.com)
   - Click "Create Project" and follow the steps
   - Note down your Project ID

2. Enable the People API:
   - In your project, go to "APIs & Services" > "Library"
   - Search for "Google People API"
   - Click "Enable"

3. Configure OAuth 2.0 Credentials:
   - Go to "APIs & Services" > "Credentials"
   - Click "Create Credentials" > "OAuth 2.0 Client ID"
   - Select "Web Application"
   - Set Authorized redirect URIs to include:
     - `http://localhost:8080/login/oauth2/code/google`
   - Copy the Client ID and Client Secret

4. Configure OAuth Consent Screen:
   - Go to "APIs & Services" > "OAuth consent screen"
   - Choose "External" user type
   - Fill in the required information
   - Add the following scopes:
     - `https://www.googleapis.com/auth/contacts`
     - `https://www.googleapis.com/auth/contacts.readonly`

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── tuban/
│   │           └── midterm/
│   │               ├── config/
│   │               │   └── SecurityConfig.java
│   │               ├── controller/
│   │               │   └── ContactController.java
│   │               ├── model/
│   │               │   └── Contact.java
│   │               ├── service/
│   │               │   └── GooglePeopleService.java
│   │               └── MidtermApplication.java
│   └── resources/
│       ├── templates/
│       │   ├── contacts.html
│       │   └── login.html
│       └── application.properties
```

## Technologies Used

- Spring Boot
- Spring Security with OAuth2
- Google People API
- Thymeleaf
- Maven
- Java 17

## Features

1. **Authentication**
   - OAuth2 integration with Google
   - Secure login system
   - Session management

2. **Contact Management**
   - List all contacts
   - Add new contacts
   - Edit existing contacts
   - Delete contacts

3. **User Interface**
   - Responsive Thymeleaf templates
   - User-friendly forms for CRUD operations
   - Error handling and validation

## Configuration

1. Update `application.properties`:
```properties
spring.security.oauth2.client.registration.google.client-id=your-client-id
spring.security.oauth2.client.registration.google.client-secret=your-client-secret
spring.security.oauth2.client.registration.google.scope=https://www.googleapis.com/auth/contacts,https://www.googleapis.com/auth/contacts.readonly
```

## Running the Application

1. Clone the repository
2. Configure Google API credentials in `application.properties`
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```
5. Access the application at `http://localhost:8080`

## Security Considerations

- OAuth2 security configuration
- CSRF protection enabled
- Secure session management
- Scope-based authorization

## Error Handling

The application includes comprehensive error handling for:
- API rate limiting
- Network issues
- Authentication failures
- Invalid contact data

## Implementation Challenges

The main challenges encountered during development were:

### Update Operation Challenges
- Handling resource versioning required by Google People API
- Managing partial updates while preserving existing contact data
- Synchronizing local state with Google's server state
- Dealing with concurrent modifications
- Preserving field metadata during updates
- Implementing proper error handling for failed updates

### Delete Operation Challenges
- Implementing proper error handling for non-existent contacts
- Managing batch delete operations
- Handling deletion confirmation flows
- Ensuring proper cleanup of related contact data

## Development Process

1. **Initial Setup**
   - Project creation with Spring Initializr
   - Configuration of OAuth2 security
   - Integration with Google People API

2. **Backend Development**
   - Implementation of service layer
   - Controller development
   - API integration

3. **Frontend Development**
   - Thymeleaf template creation
   - Form handling
   - Error display

4. **Testing and Refinement**
   - Unit testing
   - Integration testing
   - UI/UX improvements
