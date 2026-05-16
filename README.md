# Event Management System

A web application for managing campus events. Students can browse events, book seats, and manage a wishlist. Admins and co-admins can create events, manage users, and view reports.

Built for the CS5054NP (Advanced Programming and Technologies) module.

## Tech Stack

- Java 17 (Servlets, JSP)
- Maven (WAR packaging)
- Apache Tomcat 9
- MySQL
- JSTL
- BCrypt (password hashing)

## Features

### Authentication and accounts

- User registration with profile image upload (optional; default avatar if none)
- Login with contact number or email plus password
- Account approval workflow: new students start as `pending`; admin must approve before login
- Role-based redirect after login (admin/co-admin to admin dashboard, student to user dashboard)
- Logout (session invalidated)
- BCrypt password hashing on registration; login supports BCrypt and legacy plain-text passwords

### Student

- User dashboard with booking summary (confirmed vs cancelled counts)
- Browse upcoming events
- View past events (events before today)
- Search and filter upcoming events by:
  - Keyword (name, description, location)
  - Category
  - Date range (from / to)
- See live booking count and capacity per event on listings
- Book an event (blocked if already booked or event is full)
- View all personal bookings with event details
- Cancel a booking (status set to `CANCELLED`)
- Session-based wishlist: add events, view list, remove items (stored in session, not database)
- Profile page: update name, contact, email, and profile image
- Client-side form validation on registration (JavaScript)

### Admin

- Admin dashboard listing all events
- Summary stats on dashboard: total users, total events, total bookings, pending users
- Add new event (name, description, date, time, location, capacity, category, image)
- Edit existing event (including status and image replacement)
- Delete event
- User management page listing all users (pending and approved)
- Approve pending student accounts
- Delete users (cannot delete own account)
- Promote student to co-admin
- Demote co-admin to student (admin only; cannot demote self)
- Reports page:
  - Attendance per event (bookings vs capacity)
  - Events per category breakdown
  - Summary statistics

### Co-Admin

- Access admin dashboard and view stats
- View add-event form (GET only; submitting new events requires admin role in code)
- User management: approve users, delete students only, promote students to co-admin
- View reports
- Cannot add events (POST), edit events, delete events, or demote co-admins (admin-only actions)

### Public pages (no login required)

- Contact form (name, email, message saved to database)
- About page
- Static assets (CSS, JS, images)

### Images and file uploads

- Event images uploaded to `~/event-uploads/` (served via `/event-uploads/*`)
- Profile images uploaded to `~/profile-uploads/` (served via `/profile-uploads/*`)
- Accepted formats: JPG, JPEG, PNG
- Default images used when no file is uploaded
- Old images deleted when replaced on profile or event update

### Validation

- Server-side: required fields, email format, phone (7–15 digits), alphabetic names, minimum password length (6)
- Duplicate email and contact checks on registration
- Client-side validation on registration form (`static/js/validation.js`)

### Security and access control

- `AuthFilter` on all routes except login, register, contact, about, static files, and upload paths
- `AdminFilter` on `/admin/*` for admin and co-admin only
- Student-only access to `/user-dashboard`
- Access denied page for wrong role
- Custom 404 and 500 error pages
- HTTP session timeout: 30 minutes
- Success and failure flash messages via session attributes

## User Roles

| Role      | Access                                      |
|-----------|---------------------------------------------|
| student   | User dashboard, events, bookings, wishlist  |
| co-admin  | Same as admin for event and user management |
| admin     | Full admin access                           |

New registrations are created with role `student` and status `pending`. An admin must approve the account before login works.

## Project Structure

```
EventManagementSystem/
├── pom.xml                          # Maven build file
├── sql/
│   └── campus_event_db.sql          # Database dump (partial; see Database Setup)
└── src/main/
    ├── java/com/college/eventms/
    │   ├── controller/              # Servlets (HTTP handlers)
    │   ├── dao/                     # Database access
    │   ├── entity/                  # Data models
    │   ├── filter/                  # Auth and admin filters
    │   ├── service/                 # Business logic
    │   └── util/                    # DB connection, validation, images, passwords
    └── webapp/
        ├── index.jsp                # Redirects to login
        ├── static/                  # CSS, JS, default images
        └── WEB-INF/
            ├── web.xml
            ├── templates/           # Header, footer, navigation
            └── views/               # JSP pages (auth, admin, user)
```

## Prerequisites

- JDK 17
- Apache Maven
- MySQL (or MariaDB)
- Apache Tomcat 9 (or use the embedded Tomcat via Maven Cargo plugin)

The project was originally built on JDK 8 and is now configured for JDK 17 in `pom.xml` (`maven-compiler-plugin` source/target 17).

## Database Setup

1. Start MySQL.
2. Create the database:

```sql
CREATE DATABASE campus_event_db;
```

3. Import the provided dump:

```bash
mysql -u root -p campus_event_db < sql/campus_event_db.sql
```

**Note:** The file `sql/campus_event_db.sql` only defines `users` and `events` with a limited column set. The application code also uses these tables and columns:

- `users`: `profile_image` (and optionally `dob`, `created_at`)
- `events`: `image`, `capacity`, `category_id`, `status`, `created_by`
- `bookings`: `booking_id`, `user_id`, `event_id`, `booking_date`, `status`
- `categories`: `category_id`, `name`, `description`
- `contact_messages`: `name`, `email`, `message`, `submitted_at`

If features fail after import, update the schema to match what the DAO classes expect, or replace the dump with a full schema that includes all required tables and columns.

## Configuration

Edit database credentials in:

`src/main/java/com/college/eventms/util/DBConnection.java`

Default values:

| Setting  | Default                              |
|----------|--------------------------------------|
| URL      | `jdbc:mysql://localhost:3306/campus_event_db` |
| User     | `root`                               |
| Password | empty string                         |

Test the connection:

```bash
cd src/main/java
javac -cp "../../../target/event-management-system/WEB-INF/lib/mysql-connector-java-8.0.32.jar" com/college/eventms/util/DBConnection.java
java -cp ".:../../../target/event-management-system/WEB-INF/lib/mysql-connector-java-8.0.32.jar" com.college.eventms.util.DBConnection
```

Or run `DBConnection.main()` from your IDE after building the project.

## Build and Run

### Build the WAR file

```bash
mvn clean package
```

Output: `target/event-management-system.war`

### Run with embedded Tomcat (Cargo plugin)

```bash
mvn cargo:run
```

The app runs on port **9090**:

```
http://localhost:9090/event-management-system/
```

### Deploy to Tomcat manually

1. Copy `target/event-management-system.war` to Tomcat's `webapps/` folder.
2. Start Tomcat.
3. Open:

```
http://localhost:8080/event-management-system/
```

(Port depends on your Tomcat configuration.)

## Main URLs

| Path               | Description                    |
|--------------------|--------------------------------|
| `/login`           | Login page                     |
| `/register`        | Student registration           |
| `/user-dashboard`  | Student home (students only)   |
| `/events`          | Browse upcoming events         |
| `/events/past`     | Past events                    |
| `/my-bookings`     | Student bookings               |
| `/wishlist`        | Saved events (session)         |
| `/profile`         | User profile                   |
| `/contact`         | Contact form (public)          |
| `/about`           | About page (public)            |
| `/admin/dashboard` | Admin home                     |
| `/admin/add-event`   | Create event (POST: admin only) |
| `/admin/edit-event`  | Edit event (admin only)         |
| `/admin/delete-event`| Delete event (admin only)       |
| `/admin/users`       | User management                 |
| `/admin/reports`     | Reports                         |
| `/event-uploads/*`   | Serve uploaded event images     |
| `/profile-uploads/*` | Serve uploaded profile images   |
| `/logout`            | End session                     |

## Test Accounts

After importing `sql/campus_event_db.sql`, you can log in with these accounts. Use the email or the contact number in the login field.

**Local URL:** `http://localhost:8082` (or your Tomcat/Cargo port and context path, e.g. `http://localhost:9090/event-management-system/`)

| Role     | Email                      | Password     | Contact    | Status   |
|----------|----------------------------|--------------|------------|----------|
| Admin    | admin@cems.com             | Admin@123    | 9999999999 | approved |
| Co-Admin | coadmin@cems.com           | Coadmin@123  | 88888888   | approved |
| Student  | firststudent@cems.com      | student@123  | 7777777777 | approved |
| Student  | studentwithimage@cems.com  | student@123  | 6666666666 | approved |
| Student  | secondstudent@cems.com     | student@123  | 5555555555 | approved |

Passwords are stored as BCrypt hashes in the database. New registrations use the same hashing; the login code also supports legacy plain-text passwords if any old rows exist.

To add more users, register at `/register` and approve the account from **Admin → Users**, or insert a row in the `users` table with `status = 'approved'`.

## License

Academic coursework project. No license file is included.
