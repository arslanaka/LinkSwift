# URL Shortener

## Overview
This is a simple URL shortener application that allows users to convert long URLs into shorter, more manageable ones. It's built using Java and Spring Boot, with data persistence handled by MySQL.

## Features
- Shorten long URLs into shorter, more user-friendly ones
- Store original and shortened URLs along with creation timestamps
- Retrieve original URLs by providing the corresponding shortened URL
- View creation timestamps for each shortened URL

## Technologies Used
- Java
- Spring Boot
- MySQL
- Hibernate

## Setup
1. Clone the repository: `git clone https://github.com/your-username/url-shortener.git`
2. Navigate to the project directory: `cd url-shortener`
3. Configure your MySQL database connection in `application.properties`
4. Build the project: `./mvnw clean install`
5. Run the application: `./mvnw spring-boot:run`

## Usage
1. Access the application at `http://localhost:8081`
2. Enter a long URL in the provided input field and click "Shorten"
3. Copy the shortened URL provided
4. To redirect to the original URL, visit the shortened URL in your browser

## Contributing
Contributions are welcome! If you'd like to contribute to this project, please fork the repository and submit a pull request with your changes.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
