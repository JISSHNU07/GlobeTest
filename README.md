# GlobeTrotter - Personalized Travel Planning Application

GlobeTrotter is a comprehensive travel planning platform that empowers users to create, manage, and share multi-city travel itineraries with ease.

## Features

- **User Authentication**: Secure login and signup with JWT tokens
- **Trip Management**: Create, edit, and delete trips with dates and descriptions
- **Multi-City Itineraries**: Add multiple stops to your trip with arrival/departure dates
- **City Search**: Discover cities worldwide with cost indices and popularity scores
- **Activity Search**: Find activities by city, type, and cost range
- **Budget Tracking**: View cost breakdowns for accommodation, transport, activities, and meals
- **Calendar View**: Visual timeline of your trip with daily activities
- **Shared Itineraries**: Share your trips publicly with unique share tokens
- **User Profile**: Manage your profile and saved destinations

## Tech Stack

### Backend
- **Java Spring Boot 3.2.0**
- **Spring Security** with JWT authentication
- **Spring Data JPA** for database operations
- **H2 Database** (can be switched to PostgreSQL)
- **Maven** for dependency management

### Frontend
- **React 18.2**
- **React Router** for navigation
- **Axios** for API calls
- **CSS3** for styling

## Getting Started

### Prerequisites
- Java 17 or higher
- Node.js 14 or higher
- Maven 3.6 or higher

### Backend Setup

1. Navigate to the backend directory:
```bash
cd backend
```

2. Build the project:
```bash
mvn clean install
```

3. Run the Spring Boot application:
```bash
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### Frontend Setup

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm start
```

The frontend will start on `http://localhost:3000`

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/signup` - User registration

### Trips
- `GET /api/trips` - Get user's trips
- `POST /api/trips` - Create a new trip
- `GET /api/trips/{id}` - Get trip details
- `PUT /api/trips/{id}` - Update trip
- `DELETE /api/trips/{id}` - Delete trip
- `GET /api/trips/public` - Get public trips
- `GET /api/trips/shared/{shareToken}` - Get shared trip

### Stops
- `GET /api/trips/{tripId}/stops` - Get stops for a trip
- `POST /api/trips/{tripId}/stops` - Add a stop
- `PUT /api/trips/{tripId}/stops/{id}` - Update stop
- `DELETE /api/trips/{tripId}/stops/{id}` - Delete stop

### Cities
- `GET /api/cities` - Search cities
- `GET /api/cities/popular` - Get popular cities
- `GET /api/cities/{id}` - Get city details

### Activities
- `GET /api/activities/city/{cityId}` - Get activities for a city
- `GET /api/activities/{id}` - Get activity details

### Users
- `GET /api/users/me` - Get current user
- `PUT /api/users/me` - Update current user

## Database

The application uses H2 in-memory database by default. Sample data (cities and activities) is automatically initialized on startup.

To switch to PostgreSQL, update `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/globetrotter
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

## Project Structure

```
globetrotter/
├── backend/
│   ├── src/main/java/com/globetrotter/
│   │   ├── config/          # Configuration classes
│   │   ├── controller/      # REST controllers
│   │   ├── dto/            # Data transfer objects
│   │   ├── model/          # Entity models
│   │   ├── repository/     # JPA repositories
│   │   ├── security/       # Security configuration
│   │   └── service/        # Business logic
│   └── pom.xml
├── frontend/
│   ├── public/
│   └── src/
│       ├── components/     # React components
│       ├── context/        # Context providers
│       ├── pages/          # Page components
│       ├── services/       # API services
│       └── App.js
└── README.md
```

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is created for educational purposes.

