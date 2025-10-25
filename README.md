🛒 DMart – Java Swing Shopping System

DMart is a Java Swing desktop shopping application that simulates a real supermarket experience.
Users can register, browse categories and subcategories, add items to a cart, choose payment and delivery options, and generate a detailed bill — all within a sleek desktop interface.

✨ Features

User Registration – Capture personal details, contact info, and delivery address.

Dynamic Categories & Subcategories – Organized browsing with fast switching.

Cart Management – Add, remove, and update item quantities with real-time totals.

Smart Payment Panel – Choose from Card, UPI, Net Banking, Wallet, or COD with proper input validation.

Delivery Scheduler – Randomized delivery times (10–30 mins) with live countdown tracking.

Bill & Order History – Generate detailed bills and revisit previous orders anytime.

Dark Mode – Modern, eye-friendly theme with full UI coverage.

Data Handling – All products, subcategories, and pricing stored in DMartDatabase.java.

🧠 Current Issue / Collaboration Needed

⚠️ Issue:
When switching between categories or subcategories, pressing the Clear Selection button incorrectly resets totals and quantities even for items already present in the cart.
This is a state synchronization bug between the shopping panel and the shared cart data.

🧩 Looking For:
Contributors who can help fix this UI–data desynchronization issue by improving how the cart and category panels sync their item states.

If you’d like to collaborate, feel free to fork the repository, create a pull request, or open an issue with your proposed fix or debugging approach.

⚙️ Installation & Running

Clone or download this repository.

git clone https://github.com/Aaryan2R/DMart-Java-Swing-Shopping-System.git


Ensure the dMartData package (containing DMartDatabase.java) is included.

Open the project in your preferred Java IDE (IntelliJ IDEA, Eclipse, or VS Code).

Compile all Java files.

javac DMart.java


Run the application:

java DMart

💻 Technologies Used

Language: Java SE

GUI Framework: Java Swing

Layouts: BorderLayout, GridLayout, CardLayout

Database: dMartData.DMartDatabase (static arrays)

Time Handling: LocalDateTime and DateTimeFormatter

🧑‍💻 Author

Aaryan Tibrewal
Developer & Project Designer
📍 Malad West, Mumbai
📧 [YourEmailHere@gmail.com
] (optional)
