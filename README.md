# BSIT1_Java_Project_Alcoriza-Angeles-Lu
# clu1-302/BSIT1_Java_Project_Alcoriza-Angeles-Lu

# Project title: Kindness App

Purpose and problem being solved:
The Kindness App is designed to encourage people to practice kindness daily by providing simple and actionable ideas. In a fast-paced environment, many people value kindness but often forget, feel unmotivated, or don’t know how to express it in everyday situations.

The app solves the gap between intention and action by giving users clear suggestions they can immediately follow. It removes uncertainty and helps turn kindness into a consistent habit rather than just an idea.

It also promotes both kindness toward others and self-kindness, supporting emotional well-being and stronger relationships. Overall, the app aims to create a positive ripple effect where small, repeated acts of kindness contribute to a more supportive and caring community.

Features: Registry that saves user and a dashboard with a 10 by 3 grid, which generates a random task  and receives feedback if the project is completed, then saves it as files.

Instructions on how to run:
https://docs.google.com/document/d/1fDcmD9Hv0-teEDKmGue-EHYq2sLzhIugRgVMbUuRd4I/edit?usp=sharing

Mapping of the 5 lessons used (Lesson → Where Used → Purpose)

__1. Functions with 1D Arrays__

Where Used:

private final String[] tasks (DashboardController)
generateTask() method → tasks[random.nextInt(tasks.length)]

Purpose:
The 1D array stores all the kindness tasks in a single list. The function generateTask() accesses this array randomly to display a task to the user. This allows easy management and reuse of multiple task options without hardcoding each one separately.

__2. 2D Arrays__

Where Used:

private Label[][] cells = new Label[3][10]; (DashboardController)
Used in:
initialize() (grid creation)
updateGridCell()
loadProgress()
resetProgress()

Purpose:
The 2D array represents the 30-day grid (3 rows × 10 columns). Each cell corresponds to a day’s result (Completed/Failed). It helps organize and visually map progress in a structured table format.

__3. File Handling (CRUD)__

Where Used:

Create: handleRegister() → creates users.txt
Read: handleLogin(), loadProgress()
Update: saveProgress() (adds new progress)
Delete: resetProgress() (deletes progress file), removeLastLineFromFile()

Purpose:
File handling is used to store and manage user data and progress:

Saves registered users
Stores task results per user
Retrieves progress when reopening the app
Allows undo and reset features

This ensures data persistence, meaning progress is not lost after closing the app.

__4. CSV File Reading__

Where Used:

handleLogin() → String[] parts = line.split(",");

Purpose:
The users.txt file is structured like a CSV (username,password).
CSV reading is used to:

Separate username and password
Validate login credentials

This makes it easier to parse structured data from a text file.

__5. Input Handling and Validation__

Where Used:

handleLogin()
handleRegister()
completeTask() / failTask()
undoTask()

Examples:

if (username.isEmpty() || password.isEmpty())
if (!hasActiveTask)
if (day >= 30)
if (day <= 0)

__Purpose:__
Ensures the program runs correctly and avoids errors by:

Preventing empty inputs
Making sure tasks are generated before completing/failing
Avoiding overflow (more than 30 days)
Preventing invalid undo actions

This improves user experience and program reliability.

All members listed in the repository
