# 🛒 DMart – Java Swing Shopping System  

DMart is a **Java Swing desktop shopping application** that simulates a real supermarket experience.  
Users can register, browse product categories, add items to their cart, make payments, and generate a detailed bill with live order tracking.

---

## 🚀 Features
- 🧍 **User Registration & Profile Editing**
  - Collects name, contact, address, gender, and age.
  - Validates all fields (email format, name alphabets-only, phone digits, etc.)
  - Option to edit profile later.

- 🛍️ **Shopping Experience**
  - Organized **categories** and **subcategories** of products.
  - Select quantity, view live subtotal, and add to cart.
  - Persistent cart data even after switching between panels.

- 🧾 **Cart Management**
  - Add, remove, or update quantities directly in the cart.
  - “Clear Selection” button resets only unadded items, not the cart.
  - Dynamic total calculation across all categories.

- 💳 **Payment & Delivery**
  - Multiple payment methods: Card, UPI, Net Banking, Wallet, Cash on Delivery.
  - Card validation with masked card number in bill.
  - Smart delivery system with random ETA between 10–30 minutes.

- 📦 **Bill Generation & Order History**
  - Auto-generated Order ID with timestamp.
  - Displays delivery ETA and countdown timer.
  - All previous orders are stored and viewable in “Order History”.
  - Option to print bill using `javax.print`.

- 🌙 **Dark Mode**
  - Fully applied theme across all panels, buttons, and components.

- ⚙️ **Robust Exception Handling**
  - Input validation for all registration fields.
  - Prevents UI–data desynchronization issues.

---

## 🧠 Known Issue / Collaboration Request
There’s currently a **state synchronization issue**:  
> When switching between categories and pressing the “Clear” button, totals reset incorrectly across panels even if items are still in the cart.

If you’d like to **collaborate or help fix this**, feel free to fork the repo or open a pull request!  
You can also create an issue titled:
> “Cart state desynchronization when clearing items across categories”

and describe your approach to fixing it.

---

## 🧩 Technologies Used
- **Java SE 21**
- **Swing (JFrame, JPanel, JButton, JLabel, etc.)**
- **Layouts:** `BorderLayout`, `GridLayout`, `CardLayout`
- **Timer & Threading:** for live order countdown
- **Data Handling:** `DMartDatabase.java` for product and pricing data
- **Printing:** `javax.print` API for bill printing

---

## ⚙️ Installation & Running
1. Clone the repository:
   ```bash
   git clone https://github.com/Aaryan2R/DMart-Java-Swing-Shopping-System.git
