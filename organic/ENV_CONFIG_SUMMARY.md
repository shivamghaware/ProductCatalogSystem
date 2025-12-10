# 🔐 Environment Configuration Guide

Hey there! 👋 Just a quick guide on how we handle configuration and secrets in this project. 

Instead of hardcoding sensitive stuff like database passwords directly into the Java code (which is a big security no-no), I've set up the project to use a **`.env` file**. This keeps our secrets safe and makes it super easy to switch settings between your local machine and production.

---

## 🤔 How it Works

1. **The `.env` File**: This file holds your actual secrets. **It is ignored by Git** so you never accidentally commit your password.
2. **The `.env.example` File**: This is a template. It shows you what variables you *need* to set, but with safe dummy values.

## 🛠️ Setup Instructions

### Step 1: Create your Config
In the root folder of the project, duplicate the `.env.example` file and rename it to `.env`.

### Step 2: Fill it in
Open your new `.env` file and update the values. It usually looks something like this:

```properties
# Database Stuff
DB_URL=jdbc:postgresql://localhost:5432/productdb
DB_USERNAME=postgres
DB_PASSWORD=your_secure_password

# Admin Access
ADMIN_USERNAME=admin
ADMIN_PASSWORD=secret_password
```

### Step 3: Run!
That's it. When you start the Spring Boot app, it automatically reads this file and configures the Database and Security settings for you.

---

## 🧐 What Variables Can I Change?

Here is a cheat sheet of the available configurations:

| Variable | Usage | Default Fallback (if file missing) |
|----------|-------|-------------------|
| `DB_URL` | Connection string for your DB | `jdbc:postgresql://localhost:5432/productdb` |
| `DB_USERNAME` | Who is logging into the DB? | `adminuser` |
| `DB_PASSWORD` | The DB password | `mypassword` |
| `ADMIN_USERNAME` | Username to login to the Admin Dashboard | `user` |
| `ADMIN_PASSWORD` | Password for the Admin Dashboard | `password` |

> **Pro Tip**: If you delete the `.env` file, the app tries to fall back to the defaults listed above, or it might check your actual System Environment Variables. This is great for deployment!

## 🔐 Why did I do this?

- **Security**: Your passwords stay on your machine, not in the GitHub repo.
- **Flexibility**: You can have `DB_URL=localhost` on your laptop and `DB_URL=production-db.aws...` on the server without changing a single line of code.
- **Teamwork**: Your teammate can have their own credentials without fighting over `application.properties` changes.

Happy coding! 🚀
