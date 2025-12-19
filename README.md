# Authentication & Authorization Service

A centralized authentication and authorization microservice for managing user access across multiple projects and APIs.

## 📋 Introduction

This service will be used by all the other services to authenticate and authorize users to use the APIs, and contains API key and secret to also authenticate the API.

## 🛠️ Technical Stack

- **Language**: Java
- **Build Tool**: Maven
- **Authentication**: JWT, LDAP, Password-based
- **Security**: Multi-layer authorization

## 📖 Documentation

### User Roles

- **Admin**: Has access to all projects
- **Manager**: Has access to manager inspection project
- **Inspector**: Has access to inspection project
- **Police**: Has access to police project
- **Other roles**: Has access to specific project

### Authentication Types by User Role

- **LDAP**: Directory-based authentication
- **PASSWORD**: Traditional password authentication
- **UAEPASS**: Not implemented yet

### Security Levels (Database)

- JWT Token for each event (login, OTP, Change password)
- API authorization
- Menu access control

### Token Expiry Configuration

- **Change password token**: 15 minutes
- **Access token**: 2 hours
- **Refresh token**: 24 hours

## ✨ Project Features

- User can authenticate using LDAP or PASSWORD
- Optional 2-factor authentication by user role
- Role-based menu access control
- API key and secret authentication
- Multi-project authorization support

## 🔧 Role Management Workflow

### Initial Setup
Development team should fill the `menu` and `menu_authorization` tables by default.

### When Role Created or Updated

1. Insert/update role
2. Delete/insert in `menu_role` table
3. For loop through menu list:
   - Get `menu_authorization` table by `menu_auth_id`
   - Check permissions: GET / POST / UPDATE / DELETE / CONFIGURATION
4. Insert into `authorization` table all the allowed `menu_authorization` list

## 📋 Prerequisites

- Java 8 or higher
- Maven 3.6+
- Database (MySQL/PostgreSQL)
- LDAP server (if using LDAP authentication)

## 🚀 Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/touficSl/auth.git
   cd auth
   ```

2. **Build the project**
   ```bash
   ./mvnw clean install
   ```
   
   On Windows:
   ```bash
   mvnw.cmd clean install
   ```

3. **Configure the application**
   - Update database connection in `application.properties`
   - Configure LDAP settings if using LDAP authentication
   - Set JWT secret keys
   - Configure token expiry times

4. **Run the service**
   ```bash
   ./mvnw spring-boot:run
   ```

## 📁 Database Schema

### Key Tables

- `menu`: Contains application menu items
- `menu_authorization`: Defines permissions for menu items
- `menu_role`: Maps roles to menu items
- `authorization`: Stores user authorization permissions
- `users`: User accounts
- `roles`: User roles

## 🔐 API Authentication

The service provides API keys and secrets for authenticating API requests from other services.

### Token Types

1. **Access Token**: Short-lived token for API access (2 hours)
2. **Refresh Token**: Long-lived token for obtaining new access tokens (24 hours)
3. **Password Reset Token**: Time-limited token for password changes (15 minutes)

## 📦 Integration

Other services should integrate with this authentication service by:

1. Obtaining API keys and secrets
2. Validating user tokens through the authentication endpoints
3. Checking user permissions for specific operations
4. Refreshing tokens when they expire

## 👤 Author

**Toufic SL**
- GitHub: [@touficSl](https://github.com/touficSl)
- LinkedIn: [toufic-sleiman](https://www.linkedin.com/in/toufic-sleiman)

## 📝 License

This project is open source and available under the MIT License.
