# Egg Production Feed Tracker

This application is designed to track the components and composition of chicken feed mixtures, specifically for optimizing chicken productivity. The app allows users to create and save custom feed mixtures that include various components (grains, vitamins, etc.) and track how these affect egg production. It also plans to track dependencies based on weather conditions.

## Features:
- Add new feed mixtures (including components, vitamins, and additives)
- Maintain a history of feed mixtures with dates and compositions
- Track the correlation between feed composition and chicken productivity
- Save data to a database for further analysis
- Add new feed components and vitamins
- Planned integration with weather API to track the influence of weather conditions on productivity
- Integration with a **Telegram Bot** for remote tracking and statistics retrieval

## Architecture:
The application consists of three main parts:
1. **Desktop Application (JavaFX)** – for data entry and display.
2. **Web Application (Spring Boot)** – for database management and API integration.
3. **Telegram Bot** – for retrieving statistics and generating reports via Telegram.
4. **Database (PostgreSQL)** – for storing information about feed mixtures, components, and vitamins.
5. **Weather Integration (API)** – for weather data integration to track the influence of weather conditions on chicken productivity.

## Technologies:
- **JavaFX** – for the graphical user interface.
- **Spring Boot** – for the backend, API, and database interactions (Not Implemented yet).
- **JPA (Java Persistence API)** – for database management.
- **PostgreSQL** – for storing feed mixture data.
- **Maven** – for dependency management.
- **OpenWeatherMap API** (or another weather API) – for weather data integration.
- **Telegram Bot API** – for integrating with Telegram to send reports and statistics.

## Setup Instructions:

### 1. Clone the Repository:
```bash
git clone https://github.com/mazurmaksim/statistic.git
git clone https://github.com/mazurmaksim/telegram_bot_eggs.git
```
